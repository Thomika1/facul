package src;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AnalisadorLexico {
	public LeitorArquivo ldat;
	
	public AnalisadorLexico(String nome) throws FileNotFoundException{
		 ldat=new LeitorArquivo(nome);
	}
	
	public Token proximoToken() throws IOException {
		String line;		
		
		
		
		while((line=ldat.lerProxLinha())!=null) {
			
			System.out.println(line);
//			switch(symbol) {
//				case '\n': break;
//				case '\t': break;
//				case '+': return  new Token("+", TipoToken.OpAritSoma);
//				case '-': return  new Token("-", TipoToken.OpAritSub);
//				case '>': return  new Token(">", TipoToken.OpRelMaior);
//				case '<': return  new Token("<", TipoToken.OpRelMenor);
				//....
//			}
		}
		
		return null;
	}
}
