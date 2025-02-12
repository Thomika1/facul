from verificar_palavras_afd import *
from ler_afnd import *
from escrever_afd import *
from afnd_para_afd import *
from criar_grafico import *
from minimiza_afd import *
from ajusta_afd_min import *

# Main 
if __name__ == "__main__":
    estados, estado_inicial, estados_finais, transicoes = ler_afnd('input/entrada.txt')

    estados_afd, estado_inicial_afd, estados_finais_afd, transicoes_afd = afnd_para_afd(estados, estado_inicial,
                                                                                        estados_finais, transicoes)
  


    escrever_afd('output/saida.txt', estados_afd, estado_inicial_afd, estados_finais_afd, transicoes_afd)

    minimize_dfa("output/saida.txt")



    estados_min, estado_inicial_min, estados_finais_min, transicoes_min = ler_afnd('output/saidaMin.txt')
    print("estados:"+str(estados_min))
    print("estado inicial:"+str(estado_inicial_min))
    print("estados finais:"+str(estados_finais_min))
    print("trans:"+str(transicoes_min))
  


    criar_grafico('output/afnd', estados, estado_inicial, estados_finais, transicoes)

    criar_grafico('output/afd', estados_afd, estado_inicial_afd, estados_finais_afd, transicoes_afd)

    criar_grafico('output/afd_min', estados_min, estado_inicial_min, estados_finais_min, transicoes_min)

    verificar_palavras_afd('output/saida.txt', 'input/palavras.txt', 'output/resultado.txt')