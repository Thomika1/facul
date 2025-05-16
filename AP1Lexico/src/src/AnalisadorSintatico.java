package src;

import java.util.List;

public class AnalisadorSintatico {
    private List<Token> tokens;     // Lista de tokens gerada pelo analisador léxico
    private int posicaoAtual;       // Índice do próximo token a ser consumido

    public AnalisadorSintatico(List<Token> tokens) {
        this.tokens = tokens;
        this.posicaoAtual = 0;
    }

    // === Funções auxiliares ===

    /** 
     * Retorna o token atual; se ultrapassar o fim, retorna um token EOF.
     */
    private Token tokenAtual() {
        if (posicaoAtual >= tokens.size()) {
            // Token artificial de fim de arquivo
            return new Token("EOF", TipoToken.EOF, -1);
        }
        return tokens.get(posicaoAtual);
    }

    /**
     * Consome um token do tipo esperado; 
     * caso contrário, lança exceção de erro sintático.
     */
    private void consumir(TipoToken tipoEsperado) {
        Token atual = tokenAtual();
        if (atual.getPadrao() == tipoEsperado) {
            posicaoAtual++;
        } else {
            erro("Esperado: " + tipoEsperado + ", encontrado: " + atual.getPadrao(), atual);
        }
    }

    /**
     * Lança um RuntimeException indicando erro sintático,
     * incluindo linha e mensagem.
     */
    private void erro(String msg, Token token) {
        throw new RuntimeException("Erro sintático na linha " 
                + token.getLineNumber() + ": " + msg);
    }

    // Verifica se chegamos ao fim da lista de tokens
    private boolean chegouAoFim() {
        return tokenAtual().getPadrao() == TipoToken.EOF;
    }

    // === Método inicial ===

    /**
     * Regra: Programa = ':' 'DEC' ListaDeclaracoes ':' 'PROG' ListaComandos EOF
     */
    public void analisarPrograma() {
        consumir(TipoToken.Delim);           // ':'
        consumir(TipoToken.PCDec);           // DEC

        analisarListaDeclaracoes();          // ListaDeclaracoes

        consumir(TipoToken.Delim);           // ':'
        consumir(TipoToken.PCProg);          // PROG

        analisarListaComandos();             // ListaComandos

        // Após consumir todos os comandos, só deve restar EOF
        if (!chegouAoFim()) {
            erro("Esperado fim de arquivo", tokenAtual());
        }
    }

    // === Declarações ===

    /**
     * ListaDeclaracoes = { Declaracao }
     * (uma ou mais declarações, cada uma inicia com Var)
     */
    private void analisarListaDeclaracoes() {
        while (tokenAtual().getPadrao() == TipoToken.Var) {
            analisarDeclaracao();
        }
    }

    /** Declaracao = VARIAVEL ':' TipoVar */
    private void analisarDeclaracao() {
        consumir(TipoToken.Var);             // variável (lexema minúsculo)
        consumir(TipoToken.Delim);           // ':'
        analisarTipoVar();                   // INT ou REAL
    }

    /** TipoVar = 'INT' | 'REAL' */
    private void analisarTipoVar() {
        Token t = tokenAtual();
        if (t.getPadrao() == TipoToken.PCInt || t.getPadrao() == TipoToken.PCReal) {
            consumir(t.getPadrao());
        } else {
            erro("Esperado tipo INT ou REAL", t);
        }
    }

    // === Comandos ===

    /**
     * ListaComandos = { Comando }
     * Cada comando começa com Var, LER, IMPRIMIR, SE, ENQTO ou INI
     */
    private void analisarListaComandos() {
        while (ehInicioComando(tokenAtual())) {
            analisarComando();
        }
    }

    /** Identifica início de comando pelo tipo do token atual */
    private boolean ehInicioComando(Token token) {
        return switch (token.getPadrao()) {
            case Var, PCLer, PCImprimir, PCSe, PCEnqto, PCIni -> true;
            default -> false;
        };
    }

    /** Comando = Atribuicao | Entrada | Saida | Condicao | Repeticao | SubAlgoritmo */
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

    /** ComandoAtribuicao = VARIAVEL ':=' ExpressaoAritmetica */
    private void analisarComandoAtribuicao() {
        consumir(TipoToken.Var);
        consumir(TipoToken.Atrib);           // ':='
        analisarExpressaoAritmetica();
    }

    /** ComandoEntrada = 'LER' VARIAVEL */
    private void analisarComandoEntrada() {
        consumir(TipoToken.PCLer);
        consumir(TipoToken.Var);
    }

    /** ComandoSaida = 'IMPRIMIR' (VARIAVEL | CADEIA) */
    private void analisarComandoSaida() {
        consumir(TipoToken.PCImprimir);
        Token t = tokenAtual();
        if (t.getPadrao() == TipoToken.Var || t.getPadrao() == TipoToken.Cadeia) {
            consumir(t.getPadrao());
        } else {
            erro("Esperado VARIAVEL ou CADEIA após IMPRIMIR", t);
        }
    }

    /**
     * ComandoCondicao = 'SE' ExpressaoRelacional 
     *                   'ENTAO' Comando [ 'SENAO' Comando ]
     */
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

    /** ComandoRepeticao = 'ENQTO' ExpressaoRelacional Comando */
    private void analisarComandoRepeticao() {
        consumir(TipoToken.PCEnqto);
        analisarExpressaoRelacional();
        analisarComando();
    }

    /** SubAlgoritmo = 'INI' ListaComandos 'FIM' */
    private void analisarSubAlgoritmo() {
        consumir(TipoToken.PCIni);
        analisarListaComandos();
        consumir(TipoToken.PCFim);
    }

    // === Expressões Aritméticas ===

    /**
     * ExpressaoAritmetica = TermoAritmetico { ('+'|'-') TermoAritmetico }
     */
    private void analisarExpressaoAritmetica() {
        analisarTermoAritmetico();
        while (tokenAtual().getPadrao() == TipoToken.OpAritSoma ||
               tokenAtual().getPadrao() == TipoToken.OpAritSub) {
            consumir(tokenAtual().getPadrao());
            analisarTermoAritmetico();
        }
    }

    /**
     * TermoAritmetico = FatorAritmetico { ('*'|'/') FatorAritmetico }
     */
    private void analisarTermoAritmetico() {
        analisarFatorAritmetico();
        while (tokenAtual().getPadrao() == TipoToken.OpAritMult ||
               tokenAtual().getPadrao() == TipoToken.OpAritDiv) {
            consumir(tokenAtual().getPadrao());
            analisarFatorAritmetico();
        }
    }

    /**
     * FatorAritmetico = NUMINT | NUMREAL | VARIAVEL | '(' ExpressaoAritmetica ')'
     */
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

    // === Expressões Relacionais ===

    /**
     * ExpressaoRelacional = TermoRelacional { ('E'|'OU') TermoRelacional }
     */
    private void analisarExpressaoRelacional() {
        analisarTermoRelacional();
        while (tokenAtual().getPadrao() == TipoToken.OpBoolE ||
               tokenAtual().getPadrao() == TipoToken.OpBoolOu) {
            consumir(tokenAtual().getPadrao());
            analisarTermoRelacional();
        }
    }

    /**
     * TermoRelacional = '(' ExpressaoRelacional ')' 
     *                  | ExpressaoAritmetica OP_REL ExpressaoAritmetica
     */
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

    /** Verifica se um TipoToken é um operador relacional */
    private boolean ehOperadorRelacional(TipoToken tipo) {
        return switch (tipo) {
            case OpRelMenor, OpRelMenorIgual,
                 OpRelMaior, OpRelMaiorIgual,
                 OpRelIgual, OpRelDif -> true;
            default -> false;
        };
    }
}

