def verificar_palavras_afd(caminho_arquivo_afd, caminho_arquivo_palavras, caminho_arquivo_saida):
    # Lê o AFD do arquivo
    with open(caminho_arquivo_afd, 'r') as arquivo_afd:
        estados_afd = arquivo_afd.readline().strip().split()
        estado_inicial_afd = arquivo_afd.readline().strip()
        estados_finais_afd = set(arquivo_afd.readline().strip().split())
        transicoes_afd = [linha.strip().split() for linha in arquivo_afd]

    # Função para verificar se uma palavra é aceita pelo AFD
    def verificar_palavra(palavra):
        estado_atual = estado_inicial_afd
        for simbolo in palavra:
            proximo_estado = None
            for transicao in transicoes_afd:
                if transicao[0] == estado_atual and transicao[1] == simbolo:
                    proximo_estado = transicao[2]
                    break
            if proximo_estado is None:
                return "não aceito"
            estado_atual = proximo_estado
        if estado_atual in estados_finais_afd:
            return "aceito"
        else:
            return "não aceito"

    # Verifica as palavras e escreve o resultado no arquivo de saída
    with open(caminho_arquivo_palavras, 'r') as arquivo_palavras, open(caminho_arquivo_saida, 'w') as arquivo_saida:
        for palavra in arquivo_palavras:
            palavra = palavra.strip()
            resultado = verificar_palavra(palavra)
            arquivo_saida.write(f"{palavra} {resultado}\n")