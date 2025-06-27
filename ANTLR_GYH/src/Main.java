// Guilherme Alves
// Thomaz Maldonado
// https://youtu.be/m22qIbrh5p4
import java.io.IOException;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		CharStream input = CharStreams.fromFileName("input/arq.gyh"); 
        GyhLangLexer lexer = new GyhLangLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GyhLangParser parser = new GyhLangParser(tokens);
        ParseTree tree = parser.programa();

        // Analise sematica
        ParseTreeWalker walker = new ParseTreeWalker();
        AnalisadorSemanticoListener analisador = new AnalisadorSemanticoListener();
        walker.walk(analisador, tree);

        // Gera o codigo em c
        GyhProgram programa = new GyhProgram(analisador.getTabelaSimbolos());
        programa.setComandos(analisador.getComandos()); 
        programa.generateTarget();	
	}

}
