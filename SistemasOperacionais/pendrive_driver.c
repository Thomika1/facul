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
#include <linux/string.h>

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
static int mover_arquivo(const char *origem, const char *destino);
static ssize_t device_read(struct file *filp, char __user *buf, size_t len, loff_t *offset);


/*
Essas são declarações de funções que implementam operações do driver de caractere, como abrir, 
liberar e escrever no dispositivo. Elas serão associadas a um conjunto de operações de arquivo 
(file_operations), permitindo que o kernel saiba como interagir com o dispositivo.
*/



static struct file_operations fops = {
    .open = device_open,
    .release = device_release,
    .write = device_write,
    .read = device_read,
    
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

// Função para mover um arquivo

static ssize_t device_read(struct file *filp, char __user *buffer, size_t len, loff_t *offset)
{
    ssize_t bytes_read;

    // Lê os dados do arquivo no dispositivo para o buffer
    bytes_read = kernel_read(filp, buffer, len, offset);
    if (bytes_read < 0) {
        return bytes_read;  // Retorna o erro de leitura
    }

    return bytes_read;  // Retorna o número de bytes lidos
}


/* Função para mover (copiar e remover) o arquivo, usando device_read para ler os dados */
static int mover_arquivo(const char *origem, const char *destino)
{
    struct file *fsrc = NULL, *fdst = NULL;
    char *buf = NULL;
    int ret = 0;
    ssize_t read_bytes, write_bytes;
    loff_t src_pos = 0, dst_pos = 0;
    struct dentry *parent = NULL;

    if (!origem || !destino) {
        pr_err("[pendrive_driver] Caminho de origem ou destino inválido\n");
        return -EINVAL;
    }

    /* Abre o arquivo de origem para leitura */
    fsrc = filp_open(origem, O_RDONLY, 0);
    if (IS_ERR(fsrc)) {
        pr_err("[pendrive_driver] Erro ao abrir arquivo de origem: %s (código: %ld)\n",
               origem, PTR_ERR(fsrc));
        ret = PTR_ERR(fsrc);
        goto out;
    }

    /* Abre ou cria o arquivo de destino para escrita */
    fdst = filp_open(destino, O_WRONLY | O_CREAT, 0644);
    if (IS_ERR(fdst)) {
        pr_err("[pendrive_driver] Erro ao criar/abrir arquivo de destino: %s (código: %ld)\n",
               destino, PTR_ERR(fdst));
        ret = PTR_ERR(fdst);
        goto out_close_src;
    }

    /* Aloca um buffer para a cópia */
    buf = kmalloc(PAGE_SIZE, GFP_KERNEL);
    if (!buf) {
        ret = -ENOMEM;
        goto out_close_dst;
    }

    /* Copia o conteúdo do arquivo de origem para o destino usando device_read */
    while ((read_bytes = device_read(fsrc, (char __user *)buf, PAGE_SIZE, &src_pos)) > 0) {
        write_bytes = kernel_write(fdst, buf, read_bytes, &dst_pos);
        if (write_bytes < 0) {
            pr_err("[pendrive_driver] Erro ao escrever no destino.\n");
            ret = write_bytes;
            goto out_free;
        }
    }
    if (read_bytes < 0) {
        pr_err("[pendrive_driver] Erro ao ler do arquivo de origem.\n");
        ret = read_bytes;
        goto out_free;
    }

    pr_info("[pendrive_driver] Arquivo copiado com sucesso de %s para %s.\n", origem, destino);

    /* Remove o arquivo de origem para completar a operação de "mover" */
    parent = dget_parent(fsrc->f_path.dentry);
    if (parent) {
        ret = vfs_unlink(mnt_idmap(fsrc->f_path.mnt), d_inode(parent), fsrc->f_path.dentry, NULL);
        if (ret)
            pr_err("[pendrive_driver] Erro ao remover arquivo de origem: %s (código: %d)\n", origem, ret);
        dput(parent);
    } else {
        pr_err("[pendrive_driver] Erro ao obter dentry pai para remover o arquivo de origem.\n");
        ret = -ENOENT;
    }

out_free:
    kfree(buf);
out_close_dst:
    filp_close(fdst, NULL);
out_close_src:
    filp_close(fsrc, NULL);
out:
    return ret;
}






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


static ssize_t device_write(struct file *filep, const char __user *buffer, size_t len, loff_t *offset) {
    char *comando;
    char *origem;
    char *destino;

    if (len > PATH_MAX) {
        printk(KERN_ERR "Caminho muito longo\n");
        return -ENAMETOOLONG;
    }

    comando = kzalloc(len + 1, GFP_KERNEL); // Aloca memória para o comando
    if (!comando)
        return -ENOMEM;  // Erro: Sem memória disponível

    if (copy_from_user(comando, buffer, len)) { 
        kfree(comando);
        return -EFAULT; // Erro: bad address
    }

    comando[len] = '\0'; // Finaliza a string

    if (strncmp(comando, "DELETE_FILE:", 12) == 0) {
        origem = comando + 12;
        printk(KERN_INFO "Deletando arquivo: %s\n", origem);
        deletar_arquivo(origem);
    } 
    else if (strncmp(comando, "MOVE_TO_PENDRIVE:", 17) == 0) {
        // Extrai origem e destino após "MOVE_TO_PENDRIVE:"
        origem = comando + 17;
        destino = strchr(origem, ':');
        if (!destino) {
            printk(KERN_ERR "Formato inválido para MOVE_TO_PENDRIVE\n");
            kfree(comando);
            return -EINVAL;
        }
        *destino = '\0'; // Separa origem e destino
        destino++;  

        printk(KERN_INFO "Movendo arquivo do PC %s para pendrive %s\n", origem, destino);
        
        int ret = mover_arquivo(origem, destino);
        if (ret) {
            printk(KERN_ERR "Erro ao mover o arquivo para o pendrive (código: %d)\n", ret);
            kfree(comando);
            return ret;
        }
    } 
    else if (strcmp(comando, "LIST_FILES") == 0) {
        printk(KERN_INFO "Listando arquivos da pasta %s\n", path);
        listar_arquivos_da_pasta(path);
    } 
    else {
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
