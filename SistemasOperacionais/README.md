# Projeto Final de SO: Driver de dispositivo usb

## Requisitos

Certifique-se que seu compilador gcc esta atualizado, a linha `CC := /usr/bin/gcc` é o caminho do seu gcc. Se sua distro é alguma diferente do ubuntu ou mint talvez será necessário corrigir esse caminho no arquivo.
O kernel do linux tambem deve estar atualizado pois algumas funções tiveram seus parâmetros alterados em versões mais recentes.

* Rode os seguintes comandos:

```
sudo apt update
sudo apt upgrade
```
nota: apt eh o gerenciador de pacotes debian, talvez seja necesário checar o equivalente na sua distro.

## Como rodar:
* O comando padrão do makefile é o `make run` que configura o arquivo .ko do kernel automaticamente. O comando run está settado como default, então é possível apenas usar `make` para rodar o programa.\
* Após fazer qualquer alteração no código é recomendável usar o comando `make clean` antes de rodar novamente para limpar os arquivos gerados anteriormente.

## Uso
* Ao abrir o programa é possível ver 3 comandos básicos e uma tela que exibe os logs do kernel.
Para usar corretamente o driver copie o caminho do seu dispositivo pen drive no formato padrao do linux `/[seu caminho]/[pendrive]`.
nota: é possível ver o caminho do seu pen drive no seu gerenciador de arquivos e usando o comando pwd ao navegar ate o pendrive no terminal.\
* Após preencher o caminho do pen drive já é possível listar os arquivos presentes no pendrive, eles serão exibidos nos logs do kernel.
* Com o caminho do pendrive preenchido agora é possível digitar o nome do arquivo com o seu tipo para deletar-lo `exemplo.txt`.
* A implementação da funcionalidade de mover arquivos ainda está em andamento, porém para utiliza-la futuramente é preciso preencher o caminho do destino no formato padrão do linux.
*  

