package src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class LeitorArquivo {

	public InputStream is;
	public LeitorArquivo(String nome) throws FileNotFoundException {

		is = new FileInputStream(nome);

	}// LeitorArquivo 

	public int lerProxCaracter() {
		int c = -1;
		try {
			c = is.read();
		} catch (IOException e) {
			e.printStackTrace();
		} // try
		return c;
	}// lerProxCaracter()

}// LeitorArquivo 
