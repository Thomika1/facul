
public class Simbolos {

    private String nome;
    private int tipo;
    private String valor;

    public static final int REAL = 0;
    public static final int INT = 1;

    // Construtor
    public Simbolos(String nome, int tipo, String valor) {
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Simbolos = [nome: " + this.nome + ", tipo: " + this.tipo + ", valor: " + this.valor + "]";
    }

    // gerador de codigo
    public String generateCode() {
        String str = "";
        if (tipo == INT) {
            str = "  int " + nome + ";\n"; 
        } else if (tipo == REAL) {
            str = " float " + nome + ";\n"; 
        }
        return str;
    }
}