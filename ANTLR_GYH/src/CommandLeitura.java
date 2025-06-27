public class CommandLeitura extends Command {
	private String id;

	// Construtor
	public CommandLeitura(String id) {
		this.id = id;
	}

	@Override
	public String generateCode() {
		return "scanf(\"%d\", &" + id + ");\n";
	}
}