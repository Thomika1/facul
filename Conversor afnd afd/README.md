# Conversor AFND para AFD minimizado
Entradas devem ser escritas em um arquivo .txt com o nome "entrada.txt" no formato especificado a seguir:

* Primeira linha: Todos os estados
* Seguda linha: Estado inicial
* Terceira Linha: Estados finais
* Quarta linha em diante: As transições no formato x y z, onde x é o estado de saída, y o símbo0lo de transição e z o estado de chegada.

> 0 1 2 3 4 5 6 7 8\
> 0\
> 7 8\
> 0 e 1\
> 0 e 2\
> 1 e 3\
> 1 1 4\
> 2 0 4\
> 3 1 5\
> 4 1 4\
> 4 0 6\
> 4 0 3\
> 5 0 5\
> 5 1 7\
> 5 1 4\
> 6 0 8\

O arquivo "palavras.txt" deve receber as palavras para serem testadas pelo afd e deve possuir uma palavra por linha, sem espaços.
