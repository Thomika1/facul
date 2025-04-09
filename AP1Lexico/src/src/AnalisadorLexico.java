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
			//for (int i = pos; i < line.length(); i++){
			for (; pos < line.length();){
				
			    char c = line.charAt(pos);  
			    pos++;
				switch(c) {
					case '\n': break;
					case '\t': break;
					case '+': return  new Token("+", TipoToken.OpAritSoma, counter);
					case '-': return  new Token("-", TipoToken.OpAritSub, counter);
					case '>': return  new Token(">", TipoToken.OpRelMaior, counter);
					case '<': return  new Token("<", TipoToken.OpRelMenor, counter);
					case ':':
						if(pos + 1 < line.length() && line.charAt(pos) == '=') {
							pos++;
							return new Token(":=", TipoToken.Atrib, counter);
						}else {
							
							return  new Token(":", TipoToken.Delim, counter);							
						}
				
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
