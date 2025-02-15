import tkinter as tk
from tkinter import filedialog, messagebox
import os

DEVICE_FILE = "/dev/pendrive_driver"  # Caminho do dispositivo

def mandar_pasta_para_driver():
    """Função para enviar o caminho selecionado ao driver."""
    path = filedialog.askdirectory(title="Selecione o diretório do pendrive")
    if path:
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
    try:
        with open(DEVICE_FILE, "w") as device:
            device.write("DELETE_FILE")
        messagebox.showinfo("Sucesso", "Comando DELETE_FILE enviado ao driver!")
    except Exception as e:
        messagebox.showerror("Erro", f"Erro ao deletar arquivo: {e}")



# Interface gráfica
root = tk.Tk()
root.title("UTFPR DRIVER")
root.geometry("1000x450")
root.configure(background='white')

label = tk.Label(root, text="Selecione uma Ação para o Driver de Dispositivo",bg = "white", font=("Arial", 20, 'bold'))
label.pack(pady=10)



btn_select_path = tk.Button(root, text="Selecionar Pendrive",font=("Arial", 22), command=mandar_pasta_para_driver, width=20, height=1,activebackground = "Green", bg = "yellow")
btn_select_path.pack(pady=10)

btn_list_files = tk.Button(root, text="Listar Arquivos",font=("Arial", 22), command=listar_arquivos_do_driver, width=20, height=1,activebackground = "Green", bg = "yellow")
btn_list_files.pack(pady=10)

btn_list_files = tk.Button(root, text="Deletar Arquivo",font=("Arial", 22), command=deletar_arquivo, width=20, height=1,activebackground = "Green", bg = "yellow")
btn_list_files.pack(pady=10)

btn_exit = tk.Button(root, text="Sair",fg = "white",font=("Arial", 42),command=root.quit, width=5, height=1,activebackground = "Green", bg = "black")
btn_exit.pack(pady=30)

root.mainloop()

