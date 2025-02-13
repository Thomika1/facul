def encontrar_conjunto(pai, e):
    if pai[e] != e:
        pai[e] = encontrar_conjunto(pai, pai[e])
    return pai[e]

def unir(pai, rank, e1, e2):
    raiz1, raiz2 = encontrar_conjunto(pai, e1), encontrar_conjunto(pai, e2)
    if raiz1 != raiz2:
        if rank[raiz1] > rank[raiz2]:
            pai[raiz2] = raiz1
        else:
            pai[raiz1] = raiz2
            if rank[raiz1] == rank[raiz2]:
                rank[raiz2] += 1

def obter_conjuntos(pai):
    grupos = {}
    for e in pai:
        raiz = encontrar_conjunto(pai, e)
        if raiz not in grupos:
            grupos[raiz] = set()
        grupos[raiz].add(e)
    return list(grupos.values())

def minimizar_afd(nome_arquivo):
    with open(nome_arquivo, "r") as f:
        linhas = f.read().strip().split("\n")
    
    estados = linhas[0].split()
    estado_inicial = linhas[1].strip()
    estados_finais = set(linhas[2].split())
    transicoes = {}
    terminais = set()
    
    for linha in linhas[3:]:
        estado, simbolo, proximo_estado = linha.split()
        transicoes[(estado, simbolo)] = proximo_estado
        terminais.add(simbolo)
    
    def ordenar_tupla(a, b):
        return (a, b) if a < b else (b, a)
    
    tabela = {}
    estados_ordenados = sorted(estados)
    
    for i, e1 in enumerate(estados_ordenados):
        for e2 in estados_ordenados[i+1:]:
            tabela[(e1, e2)] = (e1 in estados_finais) != (e2 in estados_finais)
    
    alterado = True
    while alterado:
        alterado = False
        for i, e1 in enumerate(estados_ordenados):
            for e2 in estados_ordenados[i+1:]:
                if tabela[(e1, e2)]:
                    continue
                for w in terminais:
                    t1 = transicoes.get((e1, w))
                    t2 = transicoes.get((e2, w))
                    if t1 is not None and t2 is not None and t1 != t2:
                        marcado = tabela[ordenar_tupla(t1, t2)]
                        alterado = alterado or marcado
                        tabela[(e1, e2)] = marcado
                        if marcado:
                            break
    
    pai = {e: e for e in estados}
    rank = {e: 0 for e in estados}
    
    for (e1, e2), marcado in tabela.items():
        if not marcado:
            unir(pai, rank, e1, e2)
    
    grupos_estados = obter_conjuntos(pai)
    novos_estados = [str(i + 1) for i in range(len(grupos_estados))]
    mapa_estados = {e: str(i + 1) for i, grupo in enumerate(grupos_estados) for e in grupo}
    
    estado_inicial = mapa_estados[estado_inicial]
    estados_finais = {mapa_estados[e] for e in estados_finais}
    
    novas_transicoes = {(mapa_estados[k[0]], k[1]): mapa_estados[v] for k, v in transicoes.items()}
    
    with open("output/saidaMin.txt", "w") as f:
        f.write(" ".join(["Q" + estado for estado in novos_estados]) + "\n")  # Adiciona "Q" na frente de cada estado
        f.write("Q" + estado_inicial + "\n")  # Adiciona "Q" na frente do estado inicial
        f.write(" ".join(["Q" + estado for estado in estados_finais]) + "\n")  # Adiciona "Q" na frente de cada estado final
        for (estado, simbolo), proximo_estado in novas_transicoes.items():
            f.write(f"Q{estado} {simbolo} Q{proximo_estado}\n")  # Adiciona "Q" na frente de cada estado de transição
