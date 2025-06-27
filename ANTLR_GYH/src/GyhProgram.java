import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GyhProgram {
    private List<Command> comandos;
    private TabSimb tabela;

    public GyhProgram(TabSimb tabela) {
        this.tabela = tabela;
        
        
        try {
            Files.createDirectories(Paths.get("output"));
        } catch (IOException e) {
            System.err.println("Erro ao criar diretório output: " + e.getMessage());
        }
    }

    public void setComandos(List<Command> comandos) {
        this.comandos = comandos;
    }

    public void generateTarget() {
        StringBuilder codigoC = new StringBuilder();
        
        // Adiciona cabeçalho padrao para Linux
        codigoC.append("// Gerado automaticamente para Linux\n");
        codigoC.append("#include <stdio.h>\n");
        codigoC.append("#include <stdlib.h>\n\n");

        codigoC.append("int main() {\n");
        
        // Variaveis
        for(Simbolos simbolos: tabela.getAll()) {
            codigoC.append("    " + simbolos.generateCode());
        }

        // Comanndos
        for (Command cmd : comandos) {
            codigoC.append("    " + cmd.generateCode());
        }

        codigoC.append("    return 0;\n}\n");

        // Cria o arquivo e pasta output
        try {
            Files.write(Paths.get("output/programa.c"), codigoC.toString().getBytes());
            System.out.println("Código C gerado com sucesso em output/programa.c");
            
        } catch (IOException e) {
            System.err.println("Erro ao gerar código C: " + e.getMessage());
        }
    }
}