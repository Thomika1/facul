# Escreve um autômato finito determinístico (AFD) em um arquivo
def escrever_afd(caminho_arquivo, estados, estado_inicial, estados_finais, transicoes):
    with open(caminho_arquivo, 'w') as arquivo:
        arquivo.write(' '.join(estados) + '\n')
        arquivo.write(estado_inicial + '\n')
        arquivo.write(' '.join(estados_finais) + '\n')
        for transicao in transicoes:
            arquivo.write(' '.join(transicao) + '\n')