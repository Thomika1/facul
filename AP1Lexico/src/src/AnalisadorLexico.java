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
		Integer counter = 0;
		
		while((line=ldat.lerProxLinha())!=null) {
					
			System.out.println(line+" |Linha:"+counter);
			counter++;
			
			// ABORDAGENS
			// transformar a linha em um array antes de envia-la para o analisador.
			// se a linha eh tratada como array eu consigo identificar por meio de cadeias de condicional? ficaria muito
			// extenso????
			//
			// como ficaria a ogranizacao entre ler a linha e o switch case para captar os tokens???
			// tratar como uma string diretamente aqui seria viavel/elegante?
			
			
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
