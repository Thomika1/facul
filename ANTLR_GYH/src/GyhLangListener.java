// Generated from GyhLang.g4 by ANTLR 4.7.2

    import java.util.ArrayList;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GyhLangParser}.
 */
public interface GyhLangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#programa}.
	 * @param ctx the parse tree
	 */
	void enterPrograma(GyhLangParser.ProgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#programa}.
	 * @param ctx the parse tree
	 */
	void exitPrograma(GyhLangParser.ProgramaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#listaDeclaracoes}.
	 * @param ctx the parse tree
	 */
	void enterListaDeclaracoes(GyhLangParser.ListaDeclaracoesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#listaDeclaracoes}.
	 * @param ctx the parse tree
	 */
	void exitListaDeclaracoes(GyhLangParser.ListaDeclaracoesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#listaComandos}.
	 * @param ctx the parse tree
	 */
	void enterListaComandos(GyhLangParser.ListaComandosContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#listaComandos}.
	 * @param ctx the parse tree
	 */
	void exitListaComandos(GyhLangParser.ListaComandosContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#tabelaDeclaracoes}.
	 * @param ctx the parse tree
	 */
	void enterTabelaDeclaracoes(GyhLangParser.TabelaDeclaracoesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#tabelaDeclaracoes}.
	 * @param ctx the parse tree
	 */
	void exitTabelaDeclaracoes(GyhLangParser.TabelaDeclaracoesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#declaracao}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracao(GyhLangParser.DeclaracaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#declaracao}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracao(GyhLangParser.DeclaracaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#tipoVar}.
	 * @param ctx the parse tree
	 */
	void enterTipoVar(GyhLangParser.TipoVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#tipoVar}.
	 * @param ctx the parse tree
	 */
	void exitTipoVar(GyhLangParser.TipoVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#expressaoAritmetica}.
	 * @param ctx the parse tree
	 */
	void enterExpressaoAritmetica(GyhLangParser.ExpressaoAritmeticaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#expressaoAritmetica}.
	 * @param ctx the parse tree
	 */
	void exitExpressaoAritmetica(GyhLangParser.ExpressaoAritmeticaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#expressaoAritmeticaAux}.
	 * @param ctx the parse tree
	 */
	void enterExpressaoAritmeticaAux(GyhLangParser.ExpressaoAritmeticaAuxContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#expressaoAritmeticaAux}.
	 * @param ctx the parse tree
	 */
	void exitExpressaoAritmeticaAux(GyhLangParser.ExpressaoAritmeticaAuxContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#termoAritmetico}.
	 * @param ctx the parse tree
	 */
	void enterTermoAritmetico(GyhLangParser.TermoAritmeticoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#termoAritmetico}.
	 * @param ctx the parse tree
	 */
	void exitTermoAritmetico(GyhLangParser.TermoAritmeticoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#termoAritmeticoAux}.
	 * @param ctx the parse tree
	 */
	void enterTermoAritmeticoAux(GyhLangParser.TermoAritmeticoAuxContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#termoAritmeticoAux}.
	 * @param ctx the parse tree
	 */
	void exitTermoAritmeticoAux(GyhLangParser.TermoAritmeticoAuxContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#fatorAritmetico}.
	 * @param ctx the parse tree
	 */
	void enterFatorAritmetico(GyhLangParser.FatorAritmeticoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#fatorAritmetico}.
	 * @param ctx the parse tree
	 */
	void exitFatorAritmetico(GyhLangParser.FatorAritmeticoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#expressaoRelacional}.
	 * @param ctx the parse tree
	 */
	void enterExpressaoRelacional(GyhLangParser.ExpressaoRelacionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#expressaoRelacional}.
	 * @param ctx the parse tree
	 */
	void exitExpressaoRelacional(GyhLangParser.ExpressaoRelacionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#expressaoRelacionalAux}.
	 * @param ctx the parse tree
	 */
	void enterExpressaoRelacionalAux(GyhLangParser.ExpressaoRelacionalAuxContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#expressaoRelacionalAux}.
	 * @param ctx the parse tree
	 */
	void exitExpressaoRelacionalAux(GyhLangParser.ExpressaoRelacionalAuxContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#termoRelacional}.
	 * @param ctx the parse tree
	 */
	void enterTermoRelacional(GyhLangParser.TermoRelacionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#termoRelacional}.
	 * @param ctx the parse tree
	 */
	void exitTermoRelacional(GyhLangParser.TermoRelacionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#operadorBooleano}.
	 * @param ctx the parse tree
	 */
	void enterOperadorBooleano(GyhLangParser.OperadorBooleanoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#operadorBooleano}.
	 * @param ctx the parse tree
	 */
	void exitOperadorBooleano(GyhLangParser.OperadorBooleanoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#tabelaComandos}.
	 * @param ctx the parse tree
	 */
	void enterTabelaComandos(GyhLangParser.TabelaComandosContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#tabelaComandos}.
	 * @param ctx the parse tree
	 */
	void exitTabelaComandos(GyhLangParser.TabelaComandosContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#comando}.
	 * @param ctx the parse tree
	 */
	void enterComando(GyhLangParser.ComandoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#comando}.
	 * @param ctx the parse tree
	 */
	void exitComando(GyhLangParser.ComandoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#comandoAtribuicao}.
	 * @param ctx the parse tree
	 */
	void enterComandoAtribuicao(GyhLangParser.ComandoAtribuicaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#comandoAtribuicao}.
	 * @param ctx the parse tree
	 */
	void exitComandoAtribuicao(GyhLangParser.ComandoAtribuicaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#comandoEntrada}.
	 * @param ctx the parse tree
	 */
	void enterComandoEntrada(GyhLangParser.ComandoEntradaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#comandoEntrada}.
	 * @param ctx the parse tree
	 */
	void exitComandoEntrada(GyhLangParser.ComandoEntradaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#comandoSaidaAux}.
	 * @param ctx the parse tree
	 */
	void enterComandoSaidaAux(GyhLangParser.ComandoSaidaAuxContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#comandoSaidaAux}.
	 * @param ctx the parse tree
	 */
	void exitComandoSaidaAux(GyhLangParser.ComandoSaidaAuxContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#comandoSaida}.
	 * @param ctx the parse tree
	 */
	void enterComandoSaida(GyhLangParser.ComandoSaidaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#comandoSaida}.
	 * @param ctx the parse tree
	 */
	void exitComandoSaida(GyhLangParser.ComandoSaidaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#comandoCondicao}.
	 * @param ctx the parse tree
	 */
	void enterComandoCondicao(GyhLangParser.ComandoCondicaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#comandoCondicao}.
	 * @param ctx the parse tree
	 */
	void exitComandoCondicao(GyhLangParser.ComandoCondicaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#comandoCondicaoAux}.
	 * @param ctx the parse tree
	 */
	void enterComandoCondicaoAux(GyhLangParser.ComandoCondicaoAuxContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#comandoCondicaoAux}.
	 * @param ctx the parse tree
	 */
	void exitComandoCondicaoAux(GyhLangParser.ComandoCondicaoAuxContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#comandoRepeticao}.
	 * @param ctx the parse tree
	 */
	void enterComandoRepeticao(GyhLangParser.ComandoRepeticaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#comandoRepeticao}.
	 * @param ctx the parse tree
	 */
	void exitComandoRepeticao(GyhLangParser.ComandoRepeticaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GyhLangParser#subAlgoritmo}.
	 * @param ctx the parse tree
	 */
	void enterSubAlgoritmo(GyhLangParser.SubAlgoritmoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GyhLangParser#subAlgoritmo}.
	 * @param ctx the parse tree
	 */
	void exitSubAlgoritmo(GyhLangParser.SubAlgoritmoContext ctx);
}