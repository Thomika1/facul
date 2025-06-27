import java.util.ArrayList;
import java.util.List;

public class AnalisadorSemanticoListener extends GyhLangBaseListener {
    private TabSimb tabela = new TabSimb();
    private List<Command> comandos = new ArrayList<>();
    private List<Command> comandosAtuais = comandos;
    private CommandCondicao comandoCondicaoAtual = null;
    private CommandRepeticao comandoRepeticaoAtual = null;
    private boolean dentroCondicao = false;
    private boolean dentroSenao = false;

    public List<Command> getComandos() {
        return comandos;
    }

    // Tratamento de declaracoes de variaveis
    @Override
    public void enterDeclaracao(GyhLangParser.DeclaracaoContext ctx) {
        String nomeVar = ctx.Var().getText();
        String tipoTexto = ctx.tipoVar().getText();
        int tipo = tipoTexto.equals("INT") ? Simbolos.INT : Simbolos.REAL;

        if (tabela.existe(nomeVar)) {
            System.err.println("Erro Semantico: Variavel '" + nomeVar + "' ja foi declarada.");
        } else {
            tabela.add(new Simbolos(nomeVar, tipo, null));
        }
    }

    // Verificacao de atribuicoes
    @Override
    public void enterComandoAtribuicao(GyhLangParser.ComandoAtribuicaoContext ctx) {
        String nomeVar = ctx.Var().getText();
        String expressao = ctx.expressaoAritmetica().getText();

        if (!tabela.existe(nomeVar)) {
            System.err.println("Erro Semantico: Variavel '" + nomeVar + "' nao foi declarada.");
            return;
        }

        Simbolos simbolo = tabela.get(nomeVar);
        
        // Verificaco de divisao por zero
        if (expressao.contains("/0")) {
            System.err.println("Erro Semantico: Divisao por zero detectada.");
        }

        // Verificaçao de tipos
        if (simbolo.getTipo() == Simbolos.INT && expressao.matches(".*[.]\\d+.*")) {
            System.err.println("Erro Semantico: Atribuicao de REAL em variavel INT.");
        }

        comandosAtuais.add(new CommandAtribuicao(nomeVar, expressao));
    }

    // Analise de expressoes aritmeticas
    @Override
    public void enterExpressaoAritmetica(GyhLangParser.ExpressaoAritmeticaContext ctx) {
        String expr = ctx.getText().replaceAll("\\s+", "");
        String[] tokens = expr.split("[+\\-*/]");
        
        for (String token : tokens) {
            if (token.isEmpty()) continue;
            
            // Verifica se eh uma variavel declarada
            if (token.matches("[a-zA-Z_][a-zA-Z0-9_]*") && !tabela.existe(token)) {
                System.err.println("Erro Semantico: Variavel '" + token + "' nao declarada.");
            }
        }
    }

    // Tratamento de entrada/saida
    @Override
    public void enterComandoEntrada(GyhLangParser.ComandoEntradaContext ctx) {
        String nomeVar = ctx.Var().getText();
        if (!tabela.existe(nomeVar)) {
            System.err.println("Erro Semantico: Variavel '" + nomeVar + "' nao declarada.");
            return;
        }
        comandosAtuais.add(new CommandLeitura(nomeVar));
    }

    @Override
    public void enterComandoSaida(GyhLangParser.ComandoSaidaContext ctx) {
        String saida = ctx.comandoSaidaAux().getText();
        
        // Se nao for string literal, verifica se é variavel valida
        if (!saida.startsWith("\"")) {
            if (!tabela.existe(saida)) {
                System.err.println("Erro Semantico: Variavel '" + saida + "' nao declarada.");
            }
        }
        comandosAtuais.add(new CommandEscrita(saida));
    }

    // Controle de condicionais
    @Override
    public void enterComandoCondicao(GyhLangParser.ComandoCondicaoContext ctx) {
        String condicao = ctx.expressaoRelacional().getText();
        comandoCondicaoAtual = new CommandCondicao(condicao, new ArrayList<>(), new ArrayList<>());
        comandosAtuais.add(comandoCondicaoAtual);
        comandosAtuais = comandoCondicaoAtual.getListaVerdadeira();
        dentroCondicao = true;
    }

    @Override 
    public void exitComandoCondicao(GyhLangParser.ComandoCondicaoContext ctx) {
        comandosAtuais = comandos;
        comandoCondicaoAtual = null;
        dentroCondicao = false;
    }

    // Controle de repeticao
    @Override
    public void enterComandoRepeticao(GyhLangParser.ComandoRepeticaoContext ctx) {
        String condicao = ctx.expressaoRelacional().getText();
        comandoRepeticaoAtual = new CommandRepeticao(condicao, new ArrayList<>());
        comandosAtuais.add(comandoRepeticaoAtual);
        comandosAtuais = comandoRepeticaoAtual.getCorpo();
    }

    @Override
    public void exitComandoRepeticao(GyhLangParser.ComandoRepeticaoContext ctx) {
        comandosAtuais = comandos;
        comandoRepeticaoAtual = null;
    }

    public TabSimb getTabelaSimbolos() {
        return tabela;
    }
}