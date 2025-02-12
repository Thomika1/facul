# Converte um AFND para um AFD
def afnd_para_afd(estados, estado_inicial, estados_finais, transicoes):
    transicoes_afd = []

    # Função auxiliar para obter o fechamento epsilon de um conjunto de estados
    def fechamento_epsilon(estado_atual):
        fechamento = set(estado_atual)
        fila = list(estado_atual)

        while fila:
            estado = fila.pop()
            for origem, rotulo, destino in transicoes:
                if estado == origem and rotulo == 'e' and destino not in fechamento:
                    fechamento.add(destino)
                    fila.append(destino)

        return frozenset(fechamento)

    mapeamento_estados = {}  # Usar um dicionário para mapear conjuntos de estados para estados AFD
    novos_estados = [fechamento_epsilon({estado_inicial})]
    mapeamento_estados[novos_estados[0]] = "Q0"

    while novos_estados:
        estado_atual = novos_estados.pop()

        for simbolo in '01':
            proximo_estado = set()

            for estado in estado_atual:
                for origem, rotulo, destino in transicoes:
                    if estado == origem and rotulo == simbolo:
                        proximo_estado.add(destino)

            if proximo_estado:
                fechamento_proximo = fechamento_epsilon(proximo_estado)
                if fechamento_proximo not in mapeamento_estados:
                    novo_estado_afd = f"Q{len(mapeamento_estados)}"
                    mapeamento_estados[fechamento_proximo] = novo_estado_afd
                    novos_estados.append(fechamento_proximo)

                transicoes_afd.append(
                    [mapeamento_estados[estado_atual], simbolo, mapeamento_estados[fechamento_proximo]])

    estados_afd = list(mapeamento_estados.values())
    estado_inicial_afd = mapeamento_estados[fechamento_epsilon({estado_inicial})]
    estados_finais_afd = {mapeamento_estados[estado] for estado in mapeamento_estados if estado & estados_finais}

    return estados_afd, estado_inicial_afd, estados_finais_afd, transicoes_afd