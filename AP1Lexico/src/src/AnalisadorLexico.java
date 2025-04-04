package src;

import java.io.FileNotFoundException;

public class AnalisadorLexico {

	public LeitorArquivo ldat;
	public AnalisadorLexico(String nome) throws FileNotFoundException {
		ldat = new LeitorArquivo(nome);
	}// AnalisadorLexico dentro

	public Token ProximoToken() {
		int c;
		while ((c = ldat.lerProxCaracter()) != -1) {
			char caractere = (char) c;
			switch (caractere) {
			case '\n':
				break;
			case '\t':
				break;
			case '+':
				return new Token("+", TipoToken.OpAritSoma);
			case '-':
				return new Token("-", TipoToken.OpAritSub);
			case '>':
				return new Token(">", TipoToken.OpRelMaior);
			case '<':
				return new Token("<", TipoToken.OpRelMenor);
			}// switch
		} // while
		return null;
	}// ProximoToken

}// AnalisadorLexico fora
