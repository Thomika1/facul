

class Estados:
    def __init__(self, estadosAponta, nome):
        self.nome = nome
        self.estadosAponta = estadosAponta
    def __repr__(self):
        return f"Estado(nome='{self.nome}', aponta_para={self.estadosAponta})"



def main():
    graph = []
    graph = [Estados(nome="A", estadosAponta={"B":100, "C":125,"D":100,"E":75})]

    estado_a = graph[0]
    print(estado_a)


if __name__ =="__main__":
    main()
