package src;
import java.io.IOException;

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
        Token t = lex.proximoToken();
        
        while(t != null) {
            System.out.println(t.toString());
            t = lex.proximoToken();
        }
    }
}