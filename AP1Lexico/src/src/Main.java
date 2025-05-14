// Thomaz Maldonado Bonfim - RA: 2408864

package src;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        // Verifica se foi passado algum argumento
        if (args.length == 0) {
            System.out.println("Por favor, informe o caminho do arquivo como argumento.");
            System.out.println("Exemplo: java -jar seuPrograma.jar input/arq.gyh");
            return;
        }
        
        // Usa o primeiro argumento como caminho do arquivo
        String filePath = args[0];
        AnalisadorLexico lex = new AnalisadorLexico(filePath);
        
        // Lista para armazenar todos os tokens
        List<Token> tokens = new ArrayList<>();
        
        Token t = lex.proximoToken();
        
        while(t != null) {
            // Imprime o token na tela
            System.out.println(t.toString());
            // Adiciona o token à lista
            tokens.add(t);
            t = lex.proximoToken();
        }
        
        // Agora você pode passar a lista de tokens para o analisador sintático
        AnalisadorSintatico sintatico = new AnalisadorSintatico(tokens);
       // sintatico.analisar();
    }
}