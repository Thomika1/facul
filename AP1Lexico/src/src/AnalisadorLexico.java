package src;

import java.io.IOException;

public class AnalisadorLexico {
	public LeitorArquivo ldat;
	public Integer counter;
	public Integer pos;
	public String line;
	
	public AnalisadorLexico(String nome) throws IOException{
		 ldat=new LeitorArquivo(nome);
		 counter = 1;
		 pos=0;
		 line=ldat.lerProxLinha();	
	}
	
	public Token proximoToken() throws IOException {
				
		//int pos=0;
		
//		System.out.print("Tamanho da linha");
//		System.out.print(line.length());
//		System.out.print("     acessando o 3o caractere:");
//		System.out.println(line.charAt(2));
		
		//while((line=ldat.lerProxLinha())!=null) {
		//System.out.println("Dentro do metodo ler prox token");
		//System.out.println("linha:"+counter+" pos:"+pos+" tamanho:"+line.length());
		
		while(pos<=line.length()) {
			if(pos==line.length()) {
				line=ldat.lerProxLinha();
				counter++;
				//System.out.println("Zerou pos");
				pos=0;
				
				if(line==null) {
					System.out.println("Fim arq");
					break;
				}
			}
			//System.out.println(line+" |Linha:"+counter);
			
			// ABORDAGENS
			// transformar a linha em um array antes de envia-la para o analisador.
			// se a linha eh tratada como array eu consigo identificar por meio de cadeias de condicional? ficaria muito
			// extenso????
			//
			// como ficaria a ogranizacao entre ler a linha e o switch case para captar os tokens???
			// tratar como uma string diretamente aqui seria viavel/elegante?
			
			//for (int i = pos; i < line.length(); i++){
			for ( ; pos < line.length();){
				
			    char c = line.charAt(pos);  
			    pos++;
				switch(c) {
					case '\n': break;
					case '\t': break;
					case '+': return  new Token("+", TipoToken.OpAritSoma, counter);
					case '-': return  new Token("-", TipoToken.OpAritSub, counter);
					case '>': return  new Token(">", TipoToken.OpRelMaior, counter);
					case '<': return  new Token("<", TipoToken.OpRelMenor, counter);
				
				} // switch 
			} // for
			
//			switch(symbol) {
//				case '\n': break;
//				case '\t': break;
//				case '+': return  new Token("+", TipoToken.OpAritSoma);
//				case '-': return  new Token("-", TipoToken.OpAritSub);
//				case '>': return  new Token(">", TipoToken.OpRelMaior);
//				case '<': return  new Token("<", TipoToken.OpRelMenor);
				//....
//			}
		} // while
		return null;
	} //  Proximo Token
}
