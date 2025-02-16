import tkinter as tk
from tkinter import filedialog, messagebox
import threading
import subprocess

DEVICE_FILE = "/dev/pendrive_driver"  # Caminho do dispositivo
pendrive_path = ""  # Variável global para armazenar o caminho do pendrive
placeholder_text = "Nome do arquivo com tipo"

def mandar_pasta_para_driver():
    """Função para enviar o caminho selecionado ao driver."""
    global pendrive_path
    path = filedialog.askdirectory(title="Selecione o diretório do pendrive")
    if path:
        pendrive_path = path  # Atualiza o caminho global
        try:
            with open(DEVICE_FILE, "w") as device:
                device.write(path)
            messagebox.showinfo("Sucesso", f"Path enviado ao driver:\n{path}")
        except Exception as e:
            messagebox.showerror("Erro", f"Erro ao enviar path: {e}")

def listar_arquivos_do_driver():
    """Função para solicitar listagem de arquivos ao driver."""
    try:
        with open(DEVICE_FILE, "w") as device:
            device.write("LIST_FILES")  # Comando específico para listar arquivos
        messagebox.showinfo("Sucesso", "Comando LIST_FILES enviado ao driver!")
    except Exception as e:
        messagebox.showerror("Erro", f"Erro ao listar arquivos: {e}")

def deletar_arquivo():
    """Função para deletar um arquivo do pendrive."""
    global pendrive_path
    if not pendrive_path:
        messagebox.showerror("Erro", "Selecione primeiro o diretório do pendrive!")
        return

    arquivo_nome = entry_nome_arquivo.get()
    if not arquivo_nome:
        messagebox.showerror("Erro", "Digite o nome do arquivo a ser deletado!")
        return

    caminho_completo = f"{pendrive_path}/{arquivo_nome}"
    try:
        with open(DEVICE_FILE, "w") as device:
            device.write(f"DELETE_FILE:{caminho_completo}")  # Envia caminho completo
        messagebox.showinfo("Sucesso", f"Comando DELETE_FILE enviado para: {caminho_completo}")
    except Exception as e:
        messagebox.showerror("Erro", f"Erro ao deletar arquivo: {e}")

def on_entry_click(event):
    """Remove o placeholder quando o usuário clica na Entry."""
    if entry_nome_arquivo.get() == placeholder_text:
        entry_nome_arquivo.delete(0, tk.END)
        entry_nome_arquivo.config(fg="black")  # Muda a cor do texto para preto

def on_focus_out(event):
    """Restaura o placeholder se o usuário sair da Entry sem digitar nada."""
    if not entry_nome_arquivo.get():
        entry_nome_arquivo.insert(0, placeholder_text)
        entry_nome_arquivo.config(fg="grey")  # Muda a cor do texto para cinza

def monitorar_logs():
    """Monitora os logs do kernel em tempo real."""
    process = subprocess.Popen(["tail", "-f", "/var/log/syslog"], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
    for line in iter(process.stdout.readline, ""):
        text_widget.insert(tk.END, line)
        text_widget.see(tk.END) 


# Interface gráfica
root = tk.Tk()
root.title("UTFPR DRIVER")
root.geometry("{0}x{1}+0+0".format(root.winfo_screenwidth(), root.winfo_screenheight()))
root.configure(background='white')

label = tk.Label(root, text="Selecione uma Ação para o Driver de Dispositivo", bg="white", font=("Arial", 20, 'bold'))
label.pack(pady=10)

btn_select_path = tk.Button(root, text="Selecionar Pendrive", font=("Arial", 22), command=mandar_pasta_para_driver, width=20, height=1, activebackground="Green", bg="yellow")
btn_select_path.pack(pady=10)

btn_list_files = tk.Button(root, text="Listar Arquivos", font=("Arial", 22), command=listar_arquivos_do_driver, width=20, height=1, activebackground="Green", bg="yellow")
btn_list_files.pack(pady=20)

entry_nome_arquivo = tk.Entry(root, font=("Arial", 16), width=30, fg="grey")
entry_nome_arquivo.insert(0, placeholder_text)  # Adiciona o texto inicial
entry_nome_arquivo.bind("<FocusIn>", on_entry_click)  # Remove o placeholder ao clicar
entry_nome_arquivo.bind("<FocusOut>", on_focus_out)  # Restaura o placeholder ao sair
entry_nome_arquivo.pack(pady=10)

btn_deletar_arquivo = tk.Button(root, text="Deletar Arquivo", font=("Arial", 22), command=deletar_arquivo, width=20, height=1, activebackground="Green", bg="yellow")
btn_deletar_arquivo.pack(pady=10)

text_widget = tk.Text(root, wrap="word", height=25, width=100, font=("Courier", 10))
text_widget.pack(padx=10, pady=10, fill="x", expand=True)

# Cria uma thread para atualziar os logs
threading.Thread(target=monitorar_logs, daemon=True).start()

btn_exit = tk.Button(root, text="Sair", fg="white", font=("Arial", 42), command=root.quit, width=5, height=1, activebackground="Green", bg="black")
btn_exit.pack(pady=30)

root.mainloop()
