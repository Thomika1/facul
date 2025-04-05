package src;
import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		AnalisadorLexico lex= new AnalisadorLexico("input/arq.gyh");
		Token t=lex.proximoToken();
		while(t!=null) {
			System.out.println(t.toString());
			t=lex.proximoToken();
		}
		
	}

}

