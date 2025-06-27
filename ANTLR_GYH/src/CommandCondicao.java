import java.util.List;


public class CommandCondicao extends Command {
	 private String condicao;
	 private List<Command> comandos;
	 private List<Command> senaoComandos;
	 
	 public List<Command> getListaVerdadeira() {
		    return comandos;
		}
	
		public List<Command> getListaFalsa() {
		    return senaoComandos;
		}

	// Construtor
	 public CommandCondicao(String condicao, List<Command> comandos, List<Command> senaoComandos) {
	     this.condicao = condicao;
	     this.comandos = comandos;
	     this.senaoComandos = senaoComandos;
	 }

 
	 @Override
	 public String generateCode() {
		 
	     String condicaoCorrigida = condicao.replace("E", "&&").replace("OU", "||");
	     StringBuilder str = new StringBuilder();
	     str.append("\n if (" + condicaoCorrigida + ") {\n");
	
	     for (Command cmd : comandos) {
	         str.append("    " + cmd.generateCode());
	     }
	     
	     str.append("}\n");
	     if (senaoComandos != null && !senaoComandos.isEmpty()) {
	         str.append("\n else {\n");
	         for (Command cmd : senaoComandos) {
	             str.append("    " + cmd.generateCode());
	         }
	         str.append("}\n");
	     }
	     return str.toString();
 }




}