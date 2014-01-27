package othello;

/* 
 * Variante del Juego Othello
 * 
 * @author Alejandro
 * @author Jefferson
 */

public class Othello {
	
	public static void main(String[] args) {
		Entrenamiento entrenamientoOthello = new Entrenamiento();
		Juego othello = new Juego();
		othello.start();
		entrenamientoOthello.start();
	}
	
}
