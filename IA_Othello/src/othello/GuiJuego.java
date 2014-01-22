package othello;

import java.awt.EventQueue;

public class GuiJuego implements Juego {
	
	@Override
	public void jugar(){
		EventQueue.invokeLater(new UIJuego());
	}
}
