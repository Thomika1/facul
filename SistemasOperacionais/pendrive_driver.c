#include <linux/module.h>
#include <linux/fs.h>
#include <linux/uaccess.h>
#include <linux/slab.h>
#include <linux/device.h>
#include <linux/dcache.h>
#include <linux/namei.h>
#include <linux/file.h>
#include <linux/string.h>
#include <linux/mnt_idmapping.h>

#define DEVICE_NAME "pendrive_driver"  //Nome do driver
#define CLASS_NAME "pendrive"
#define BUFFER_SIZE 512   //Tamanho do bufferzinho para receber os comandos pelo python


static int major_number;
static struct class *driver_class = NULL;
static struct device *driver_device = NULL;

char *path;


// Protótipos
static int device_open(struct inode *, struct file *);
static int device_release(struct inode *, struct file *);
static ssize_t device_write(struct file *, const char __user *, size_t, loff_t *);
static int listar_arquivos_da_pasta(const char *path);
static int deletar_arquivo(const char *path);
/*
Essas são declarações de funções que implementam operações do driver de caractere, como abrir, 
liberar e escrever no dispositivo. Elas serão associadas a um conjunto de operações de arquivo 
(file_operations), permitindo que o kernel saiba como interagir com o dispositivo.
*/



static struct file_operations fops = {
    .open = device_open,
    .release = device_release,
    .write = device_write,
    
};

/*
	A estrutura file_operations define as funções de callback que serão chamadas para operações no driver. Cada campo aponta para uma função correspondente. Aqui:

		.open: Aponta para device_open, que será chamada quando o dispositivo for aberto.
		.release: Aponta para device_release, chamada quando o dispositivo for fechado.
		.write: Aponta para device_write, chamada quando dados forem enviados para o dispositivo.
	Essa estrutura é usada ao registrar o driver no kernel, ligando o dispositivo às funções definidas. 
*/



// Estrutura para armazenar o contexto do diretório
struct contexto_ {
    struct dir_context context; // Estrutura padrão do kernel para diretórios
};
/*
struct contexto_ é uma estrutura personalizada que encapsula struct dir_context, usada pelo kernel para manter o contexto ao iterar sobre um diretório.

	ctx: A estrutura padrão dir_context do kernel, que inclui informações como a posição no diretório e um ponteiro para a função de callback.
	
*/



// Callback chamado para cada entrada do diretório, qo qual o parâmetro é o própria struct do diretório
static bool meu_callback(struct dir_context *ctx, const char *name, int namelen, loff_t offset, u64 ino, unsigned int d_type) {
    pr_info("File: %.*s\n", namelen, name);
    return true; // Retorna true para continuar listando
}
/*
Essa função é um callback chamado para cada entrada encontrada em um diretório.

Parâmetros:
	name: Nome do arquivo ou diretório.
	namelen: Comprimento do nome.
	offset: Posição do arquivo/diretório no fluxo do diretório.
	ino: Número do inode da entrada.
	d_type: Tipo da entrada (arquivo, diretório, etc.).

Funcionamento:

A função imprime o nome de cada entrada no log do kernel (pr_info).
Retorna true para continuar iterando no diretório.
*/



static int deletar_arquivo(const char *path) {
    struct path file_path;
    struct mnt_idmap *idmap;
    int ret;

    // Resolve o caminho do arquivo
    ret = kern_path(path, LOOKUP_FOLLOW, &file_path);
    if (ret) {
        pr_err("Erro ao resolver o caminho do arquivo: %s\n", path);
        return ret;
    }

    // Obtém o mnt_idmap do mount point
    idmap = mnt_idmap(file_path.mnt);

    // Deleta o arquivo
    ret = vfs_unlink(idmap, d_inode(file_path.dentry->d_parent), file_path.dentry, NULL);
    if (ret) {
        pr_err("Erro ao deletar o arquivo: %s\n", path);
    } else {
        pr_info("Arquivo deletado com sucesso: %s\n", path);
    }

    // Libera o path
    path_put(&file_path);

    return ret;
}
// Função principal para listar arquivos em um diretório
static int listar_arquivos_da_pasta(const char *path) {
    struct file *dir_file;
    struct contexto_ contexto_da_pasta = {
         .context.actor = meu_callback,
    };  
    /*
      Quanto ao actor:
         Um membro da estrutura dir_context. Ele é um ponteiro para função, ou seja, uma função callback. 
         Essa função será chamada para cada entrada durante a iteração de diretórios
    */

    // Abre o diretório
    dir_file = filp_open(path, O_RDONLY | O_DIRECTORY, 0);
    if (IS_ERR(dir_file)) {    	
        pr_err("Escolha um Dispositivo Válido Animal\n");
        return PTR_ERR(dir_file);
    }
    
    // A função filp_open abre o diretório em modo somente leitura (O_RDONLY) e verifica se houve erro (ex.: o diretório não existe).       

    // Itera sobre o diretório e chama o callback para cada entrada
    iterate_dir(dir_file, &contexto_da_pasta.context);

    // Fecha o diretório
    filp_close(dir_file, NULL);
    return 0;
}


static ssize_t device_write(struct file *filep, const char __user *buffer, size_t len, loff_t *offset)
{
    	
    char *comando;
    char *nome_arquivo;

    if (len > PATH_MAX) {
        printk(KERN_ERR "Caminho muito longo\n");
        return -ENAMETOOLONG;
    }

    comando = kzalloc(len + 1, GFP_KERNEL); //GFP_KERNEL é uma flag utilizada durante alocações de memória dinâmicas no espaço do kernel
    
    if (!comando)
        return -ENOMEM;  //Erro: Sem memória disponível
    
    if (copy_from_user(comando, buffer, len)) { 
        //copy_from_user é uma função de kernel que serve para copiar dados do espaço do usuário para o espaço do kernel
        kfree(comando);
        return -EFAULT; //Erro: bad address
    }

    comando[len] = '\0'; // Finaliza a string
    
    if (strncmp(comando, "DELETE_FILE:", 12) == 0) {
        // Extrai o nome do arquivo após "DELETE_FILE:"
        nome_arquivo = comando + 12;
        printk(KERN_INFO "Deletando arquivo: %s\n", nome_arquivo);
        deletar_arquivo(nome_arquivo);
    } else if (strcmp(comando, "LIST_FILES") == 0) {
        printk(KERN_INFO "Listando arquivos da pasta %s\n\n", path);
        listar_arquivos_da_pasta(path);
    } else {
        strcpy(path, comando);
        printk(KERN_INFO "Caminho recebido: %s\n", path);
    }
	kfree(comando);
    return len;
}

static int device_open(struct inode *inodep, struct file *filep)
{
    printk(KERN_INFO "Device aberto %s\n",path);
    return 0;
}

static int device_release(struct inode *inodep, struct file *filep)
{
    printk(KERN_INFO "Device fechado\n");
    return 0;
}

static int __init pendrive_driver_init(void)
{
    printk(KERN_INFO "Inicializando o módulo %s\n", DEVICE_NAME);
    path = kzalloc(200, GFP_KERNEL);  
    // flag GFP_KERNEL usada para alocar memória no contexto do kernel    
     
     
    // Registrar número major
    major_number = register_chrdev(0, DEVICE_NAME, &fops);  // com o primeiro parâmetro 0 significa que o major é atribuído pelo OS
   

    // Criar classe do dispositivo
    driver_class = class_create(CLASS_NAME);
   

    // Criar dispositivo
    // MKDEV retorna o número do dispositivo
    driver_device = device_create(driver_class, NULL, MKDEV(major_number, 0), NULL, DEVICE_NAME);
    
    printk(KERN_INFO "Dispositivo %s criado com sucesso\n", DEVICE_NAME);
    
    
    
    return 0;
}

static void __exit pendrive_driver_exit(void)
{
    kfree(path);
    device_destroy(driver_class, MKDEV(major_number, 0));
    class_destroy(driver_class);
    unregister_chrdev(major_number, DEVICE_NAME);
    printk(KERN_INFO "Módulo %s descarregado\n", DEVICE_NAME);
}   

module_init(pendrive_driver_init);
module_exit(pendrive_driver_exit);


MODULE_LICENSE("GPL");
MODULE_AUTHOR("SOP NEW MODULE");
MODULE_DESCRIPTION("Driver de char device para listar arquivos de pendrive");
