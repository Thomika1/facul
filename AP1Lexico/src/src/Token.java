package src;

public class Token {
	
	String lexema;
	TipoToken padrao;
	Integer lineNumber;
	
	
	public Token(String lexema, TipoToken padrao, Integer lineNumber) {
		this.lexema=lexema;
		this.padrao=padrao;
		this.lineNumber = lineNumber;
	
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
	
	public Integer getLineNumber() {
		return lineNumber;
	}
	
	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}
}