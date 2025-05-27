package src;

import java.util.List;

public class AnalisadorSintatico {
    private List<Token> tokens;     // Lista de tokens gerada pelo analisador lexico
    private int posicaoAtual;       // proximo token a ser consumido

    public AnalisadorSintatico(List<Token> tokens) {
        this.tokens = tokens;
        this.posicaoAtual = 0;
    }
     
     //Retorna o token atual; se ultrapassar o fim, retorna um token EOF. 
    private Token tokenAtual() {
        if (posicaoAtual >= tokens.size()) {
            // Token artificial de fim de arquivo
            return new Token("EOF", TipoToken.EOF, -1);
        }
        return tokens.get(posicaoAtual);
    }

    
    // Consome um token do tipo esperado; 
    // caso contrário, lança excecao de erro sintatico.
 
    private void consumir(TipoToken tipoEsperado) {
        Token atual = tokenAtual();
        if (atual.getPadrao() == tipoEsperado) {
            posicaoAtual++;
        } else {
            erro("Esperado: " + tipoEsperado + ", encontrado: " + atual.getPadrao(), atual);
        }
    }
    
    // func para formatar erro
    private void erro(String msg, Token token) {
        throw new RuntimeException("Erro sintático na linha " 
                + token.getLineNumber() + ": " + msg);
    }

    // Verifica o fim da lista
    private boolean chegouAoFim() {
        return tokenAtual().getPadrao() == TipoToken.EOF;
    }

    // Inicio do analisaddor da linguagem

    public void analisarPrograma() {
        consumir(TipoToken.Delim);           
        consumir(TipoToken.PCDec);           

        analisarListaDeclaracoes();          

        consumir(TipoToken.Delim);           
        consumir(TipoToken.PCProg);          

        analisarListaComandos();             

        // Apos consumir todos os tokens, verifica EOF
        if (!chegouAoFim()) {
            erro("Esperado fim de arquivo", tokenAtual());
        }
    }


    private void analisarListaDeclaracoes() {
        while (tokenAtual().getPadrao() == TipoToken.Var) {
            analisarDeclaracao();
        }
    }

    
    private void analisarDeclaracao() {
        consumir(TipoToken.Var);             
        consumir(TipoToken.Delim);           
        analisarTipoVar();                   
    }

    
    private void analisarTipoVar() {
        Token t = tokenAtual();
        if (t.getPadrao() == TipoToken.PCInt || t.getPadrao() == TipoToken.PCReal) {
            consumir(t.getPadrao());
        } else {
            erro("Esperado tipo INT ou REAL", t);
        }
    }


    private void analisarListaComandos() {
        while (ehInicioComando(tokenAtual())) {
            analisarComando();
        }
    }

    
    private boolean ehInicioComando(Token token) {
        return switch (token.getPadrao()) {
            case Var, PCLer, PCImprimir, PCSe, PCEnqto, PCIni -> true;
            default -> false;
        };
    }

   
    private void analisarComando() {
        switch (tokenAtual().getPadrao()) {
            case Var        -> analisarComandoAtribuicao();
            case PCLer      -> analisarComandoEntrada();
            case PCImprimir -> analisarComandoSaida();
            case PCSe       -> analisarComandoCondicao();
            case PCEnqto    -> analisarComandoRepeticao();
            case PCIni      -> analisarSubAlgoritmo();
            default         -> erro("Comando inválido", tokenAtual());
        }
    }

    
    private void analisarComandoAtribuicao() {
        consumir(TipoToken.Var);
        consumir(TipoToken.Atrib);           
        analisarExpressaoAritmetica();
    }

  
    private void analisarComandoEntrada() {
        consumir(TipoToken.PCLer);
        consumir(TipoToken.Var);
    }


    private void analisarComandoSaida() {
        consumir(TipoToken.PCImprimir);
        Token t = tokenAtual();
        if (t.getPadrao() == TipoToken.Var || t.getPadrao() == TipoToken.Cadeia) {
            consumir(t.getPadrao());
        } else {
            erro("Esperado VARIAVEL ou CADEIA após IMPRIMIR", t);
        }
    }

 
    private void analisarComandoCondicao() {
        consumir(TipoToken.PCSe);
        analisarExpressaoRelacional();
        consumir(TipoToken.PCEntao);
        analisarComando();

        if (tokenAtual().getPadrao() == TipoToken.PCSenao) {
            consumir(TipoToken.PCSenao);
            analisarComando();
        }
    }

    
    private void analisarComandoRepeticao() {
        consumir(TipoToken.PCEnqto);
        analisarExpressaoRelacional();
        analisarComando();
    }

   
    private void analisarSubAlgoritmo() {
        consumir(TipoToken.PCIni);
        analisarListaComandos();
        consumir(TipoToken.PCFim);
    }

 
    private void analisarExpressaoAritmetica() {
        analisarTermoAritmetico();
        while (tokenAtual().getPadrao() == TipoToken.OpAritSoma ||
               tokenAtual().getPadrao() == TipoToken.OpAritSub) {
            consumir(tokenAtual().getPadrao());
            analisarTermoAritmetico();
        }
    }

    private void analisarTermoAritmetico() {
        analisarFatorAritmetico();
        while (tokenAtual().getPadrao() == TipoToken.OpAritMult ||
               tokenAtual().getPadrao() == TipoToken.OpAritDiv) {
            consumir(tokenAtual().getPadrao());
            analisarFatorAritmetico();
        }
    }

 
    private void analisarFatorAritmetico() {
        Token t = tokenAtual();
        switch (t.getPadrao()) {
            case NumInt, NumReal, Var -> consumir(t.getPadrao());
            case AbrePar -> {
                consumir(TipoToken.AbrePar);
                analisarExpressaoAritmetica();
                consumir(TipoToken.FechaPar);
            }
            default -> erro("Fator aritmético inválido", t);
        }
    }

 
    private void analisarExpressaoRelacional() {
        analisarTermoRelacional();
        while (tokenAtual().getPadrao() == TipoToken.OpBoolE ||
               tokenAtual().getPadrao() == TipoToken.OpBoolOu) {
            consumir(tokenAtual().getPadrao());
            analisarTermoRelacional();
        }
    }

 
    private void analisarTermoRelacional() {
        if (tokenAtual().getPadrao() == TipoToken.AbrePar) {
            consumir(TipoToken.AbrePar);
            analisarExpressaoRelacional();
            consumir(TipoToken.FechaPar);
        } else {
            analisarExpressaoAritmetica();
            if (ehOperadorRelacional(tokenAtual().getPadrao())) {
                consumir(tokenAtual().getPadrao());
                analisarExpressaoAritmetica();
            } else {
                erro("Esperado operador relacional", tokenAtual());
            }
        }
    }

    
    private boolean ehOperadorRelacional(TipoToken tipo) {
        return switch (tipo) {
            case OpRelMenor, OpRelMenorIgual,
                 OpRelMaior, OpRelMaiorIgual,
                 OpRelIgual, OpRelDif -> true;
            default -> false;
        };
    }
}

