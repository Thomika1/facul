public class CommandEscrita extends Command {
	private String id;

	// Construtor
	public CommandEscrita(String id) {
		this.id = id;
	}

	@Override
	public String generateCode() {
		if (id.startsWith("\"") && id.endsWith("\"")) {
			return "printf(" + id + ");\n";
		} else {
			return "printf(\"%d\\n\", " + id + ");\n";
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}