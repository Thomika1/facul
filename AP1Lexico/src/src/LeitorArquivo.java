package src;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class LeitorArquivo {
	public InputStream is;
	public BufferedReader reader;

	public LeitorArquivo(String nome) throws FileNotFoundException {
		is=new FileInputStream(nome);
		reader = new BufferedReader(new FileReader(nome));
	}
	
	
	
	
	public String lerProxLinha() throws IOException {
		String line;
		
		line = reader.readLine();
		if(line == null) {
			return null;
		}else {
			return line;
		}
	}	
	
}
