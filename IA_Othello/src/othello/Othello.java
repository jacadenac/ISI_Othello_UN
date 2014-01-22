package othello;

public class Othello {

	private static final String HELP_OPT = "help"; //ayuda
	private static final String CLI_OPT = "cli"; //nuevo juego
	private static final String GUI_OPT = "gui"; //interfaz juego
	
	public static void main(String[] args) {
		
		Juego othello = null;
		if (args.length == 0 || args[0].equals(GUI_OPT)) {
			othello = new GuiJuego();
		} else if (args[0].equals(CLI_OPT)){
			othello = new CliJuego();
		} else if (args[0].equals(HELP_OPT)){
			printUsage();
			System.exit(0);
		} else {
			System.err.printf("Argumento erróneo: %s\n", args[0]);
			printUsage();
			System.exit(1);
		}
		othello.jugar();
	}
	
	private static void printUsage() {
		System.err.printf("Uso: %s [options]\n"
				  + "\n\tLas opcions son:\n"
				  + "\t %s\tModo de interfaz comando de línea\n"
				  + "\t %s\tMode de interfaz gráfica [default]\n"
				  + "\t %s\tEste mensaje de ayuda\n",
				  Othello.class.getSimpleName(), CLI_OPT, GUI_OPT, HELP_OPT);
	}

}
