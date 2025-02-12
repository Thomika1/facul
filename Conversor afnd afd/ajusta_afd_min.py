def ajusta_afd_min(estados_min, estado_inicial_min, estados_finais_min, transicoes_min):
    
    for estado in estados_min:
        estado = "Q"+estado  

    estado_inicial_min = "Q" + estado_inicial_min 
    
    

    estados_finais_min = [f"Q{estado}" for estado in estados_finais_min]

    for transicao in transicoes_min:
        transicao[0] = "Q"+transicao[0]
        transicao[-1] = "Q"+transicao[-1]

    return estados_min, estado_inicial_min, estados_finais_min, transicoes_min