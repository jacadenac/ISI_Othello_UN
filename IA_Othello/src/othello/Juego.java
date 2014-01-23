package othello;

import java.awt.EventQueue;

public class Juego{
	public void jugar(){
		EventQueue.invokeLater(new UIJuego());
	}
	/*public void jugar(int largoTablero, int anchoTablero){
		EventQueue.invokeLater(new UIJuego(largoTablero, anchoTablero));
	}*/
	
}
