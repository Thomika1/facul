package src;

public class Token {
	
	String lexema;
	TipoToken padrao;
	//lembrem-se da linha onde aparece o lexema, hein?!
	
	public Token(String lexema, TipoToken padrao) {
		this.lexema=lexema;
		this.padrao=padrao;
	}
	
	@Override
	public String toString() {
		return "<"+lexema+","+padrao+">";
	}
	
	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

	public TipoToken getPadrao() {
		return padrao;
	}

	public void setPadrao(TipoToken padrao) {
		this.padrao = padrao;
	}
}