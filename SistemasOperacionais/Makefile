obj-m += pendrive_driver.o

# Nome do módulo
MODULE_NAME := pendrive_driver

# Arquivo do módulo (com extensão .ko)
MODULE_FILE := $(MODULE_NAME).ko

# Diretório atual
PWD := $(shell pwd)

# Compilador a ser utilizado
CC := /usr/bin/gcc

# Diretório do build do kernel
KERNEL_BUILD := /lib/modules/$(shell uname -r)/build

default: run

# Alvo padrão (compilar o módulo)
all:
	$(MAKE) -C $(KERNEL_BUILD) M=$(PWD) CC=$(CC) modules

# Limpeza
clean:
	$(MAKE) -C $(KERNEL_BUILD) M=$(PWD) clean

# Remover o módulo
remove:
	@if lsmod | grep -q $(MODULE_NAME); then \
		echo "Removendo módulo $(MODULE_NAME)..."; \
		sudo rmmod $(MODULE_NAME); \
	else \
		echo "Módulo $(MODULE_NAME) não está carregado."; \
	fi

# Inserir o módulo
insert:
	@if [ -f $(MODULE_FILE) ]; then \
		echo "Inserindo módulo $(MODULE_FILE)..."; \
		sudo insmod $(MODULE_FILE); \
	else \
		echo "Arquivo $(MODULE_FILE) não encontrado!"; \
		exit 1; \
	fi

# Alterar permissões e executar echo
setup:
	@sudo chmod 666 /dev/$(MODULE_NAME)
	@echo "Configurando módulo com echo..."
	@echo "valor_para_escrever" | sudo tee /dev/$(MODULE_NAME) > /dev/null

interface:
	python3 gui_send_path.py

# Executar todas as etapas
run: all remove insert setup interface
	@echo "Módulo $(MODULE_NAME) compilado, removido, inserido e configurado com sucesso."