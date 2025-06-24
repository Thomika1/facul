import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		CharStream cs = CharStreams.fromFileName("input/arq.gyh");
		
		GyhLangLexer lexer = new GyhLangLexer(cs);
//		Token t;
//		while((t=lexer.nextToken()).getType()!=Token.EOF) {
//			System.out.println("<" + GyhLangParser.VOCABULARY.getDisplayName(t.getType()) + "," + t.getText() + ">");
//		}
		
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		GyhLangParser parser = new GyhLangParser(tokens);
		parser.programa();		
		
		
		
	}

}
