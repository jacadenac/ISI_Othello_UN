package core;

public enum Jugador {
	BLACK(EstadoCasilla.BLACK),
	WHITE(EstadoCasilla.WHITE);
	private EstadoCasilla color;
	private int puntajeNegro;
	private int puntajeBlanco;
	
	private Jugador(EstadoCasilla color) {
		this.color = color;
	}

	public Jugador oponente() {
		return this == BLACK ? WHITE : BLACK;
	}

	public EstadoCasilla color() {
		return color;
	}
	
	public void setPuntajeNegro(int puntaje) {
		this.puntajeNegro = puntaje;
	}
	
	public void setPuntajeBlanco(int puntaje) {
		this.puntajeBlanco = puntaje;
	}
	
	public int getPuntajeNegro() {
		return this.puntajeNegro;
	}
	
	public int getPuntajeBlanco() {
		return this.puntajeBlanco;
	}
	
}
