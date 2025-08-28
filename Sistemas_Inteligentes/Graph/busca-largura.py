class Estado:
    def __init__(estadoAtual):
        this.estadoAtual = estadoAtual


def main():
    inicial = [
            ["2", "8", "3"],  # Primeira linha
            ["1", "6", "4"],  # Segunda linha
            ["7", "*", "5"]   # Terceira linha
            ]
    final = [
            ["1", "2", "3"],  # Primeira linha
            ["8", "*", "4"],  # Segunda linha
            ["7", "6", "5"]   # Terceira linha
            ]
    solucao = soluciona(inicial=inicial, final=final)


def soluciona(inicial, final) -> int:
    abertos = []
    abertos.append(inicial)
    deadEnd = []
    movimentos = 0

    while abertos:
        x = abertos.pop(0)
        if x == final:
            return movimentos
        else:

if __name__ =="__main__":
    main()