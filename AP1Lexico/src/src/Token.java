package src;

public class Token {

	String lexema;
	TipoToken padrao;
	// lembrem-se da linha onde aparece lexema

	public Token(String lexema, TipoToken padrao) {
		this.lexema = lexema;
		this.padrao = padrao;
	}// Token dentro

	@Override
	public String toString() {
		return "<" + lexema + "," + padrao + ">";
	}// toString

	public String getLexema() {
		return lexema;
	}// getLexema

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}// setLexema

	public TipoToken getPadrao() {
		return padrao;
	}// getPadrao

	public void setPadrao(TipoToken padrao) {
		this.padrao = padrao;
	}// setPadrao

}// Token fora
