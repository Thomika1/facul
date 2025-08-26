class Estados:
    def __init__(self, estadosAponta, nome):
        self.nome = nome
        self.estadosAponta = estadosAponta
    def __repr__(self):
        return f"Estado(nome='{self.nome}', aponta_para={self.estadosAponta})"


def bestPath(graph, nome_inicio, nome_fim):
    mapa_estados = {estado.nome: estado for estado in graph}
    startState = mapa_estados[nome_inicio]
    
    LNE = [([startState], 0)]
    BSS = [] 
    
    melhor_caminho = None
    melhor_custo = float('inf')

    while LNE:
        LE, custo_atual = LNE.pop()
        EC = LE[-1]

        if custo_atual >= melhor_custo:
            continue

        if EC.nome == nome_fim:
            melhor_custo = custo_atual
            melhor_caminho = LE
            continue 

        for nome_vizinho, custo_aresta in EC.estadosAponta.items():
            vizinho_obj = mapa_estados[nome_vizinho]
            
            if vizinho_obj not in LE:
                novo_custo = custo_atual + custo_aresta
                novo_caminho = LE + [vizinho_obj]
                LNE.append((novo_caminho, novo_custo))
    
    if melhor_caminho:
        return [estado.nome for estado in melhor_caminho], melhor_custo
    else:
        return None, float('inf') 

def main():
    graph = []
    graph = [Estados(nome="A", estadosAponta={"B":100, "C":125,"D":100,"E":75}),
             Estados(nome="B", estadosAponta={"A":100, "C":50, "D":75, "E":125}),
             Estados(nome="C", estadosAponta={"A":125, "B":50, "D":100, "E":125}),
             Estados(nome="D", estadosAponta={"A":100, "B":75, "C":100, "E":50}),
             Estados(nome="E", estadosAponta={"A":75, "B":125, "C":125, "D":50})]
    
    #print(graph[0].estadosAponta["B"])
    path=bestPath(graph=graph,nome_inicio="A", nome_fim="C")
    print(path)
if __name__ =="__main__":
    main()
