from graphviz import Digraph

# Cria um gráfico representando o autômato
def criar_grafico(caminho_arquivo, estados, estado_inicial, estados_finais, transicoes):
    grafico = Digraph()
    for estado in estados:
        grafico.node(estado, shape='doublecircle' if estado in estados_finais else None)
    grafico.node(estado_inicial, color='red')
    for origem, rotulo, destino in transicoes:
        grafico.edge(origem, destino, label=rotulo)
    grafico.render(caminho_arquivo)