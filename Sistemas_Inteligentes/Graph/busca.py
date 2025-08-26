class Estados:
    def __init__(self, estadosAponta, nome):
        self.nome = nome
        self.estadosAponta = estadosAponta
    def __repr__(self):
        return f"Estado(nome='{self.nome}', aponta_para={self.estadosAponta})"


def bestPath(graph, startState, finalState) -> list:
    IT = 0
    EC = startState
    LE = [startState]
    LNE = [startState]
    BSS = []

    while LNE:
        if EC == finalState:
            return LE
        





def main():
    graph = []
    graph = [Estados(nome="A", estadosAponta={"B":100, "C":125,"D":100,"E":75}),
             Estados(nome="B", estadosAponta={"A":100, "C":50, "D":75, "E":125}),
             Estados(nome="C", estadosAponta={"A":125, "B":50, "D":100, "E":125}),
             Estados(nome="D", estadosAponta={"A":100, "B":75, "C":100, "E":50}),
             Estados(nome="E", estadosAponta={"A":75, "B":125, "C":125, "D":50})]
    
    #print(graph[0].estadosAponta["B"])
    bestPath(graph=graph,startState=graph[0], finalState=graph[4])
    


if __name__ =="__main__":
    main()
