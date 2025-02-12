# Lê um autômato finito não determinístico (AFND) de um arquivo
def ler_afnd(caminho_arquivo):
    with open(caminho_arquivo, 'r') as arquivo:
        estados = arquivo.readline().strip().split()
        estado_inicial = arquivo.readline().strip()
        estados_finais = set(arquivo.readline().strip().split())
        transicoes = [linha.strip().split() for linha in arquivo]
    return estados, estado_inicial, estados_finais, transicoes