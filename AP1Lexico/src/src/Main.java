package src;
import java.io.FileNotFoundException;

public class Main {


		public static void main(String[] args) throws FileNotFoundException{

			AnalisadorLexico objeto = new AnalisadorLexico("input/arq.gyh");

			Token t = objeto.ProximoToken();

			while(t!=null) {

				System.out.println(t.toString());

				t= objeto.ProximoToken();

			}//while

		}//main dentro


}//main fora
