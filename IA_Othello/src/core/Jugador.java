package core;

public enum Jugador {
	BLACK(EstadoCasilla.BLACK),
	WHITE(EstadoCasilla.WHITE);
	private EstadoCasilla color;
	
	private Jugador(EstadoCasilla color) {
		this.color = color;
	}

	public Jugador oponente() {
		return this == BLACK ? WHITE : BLACK;
	}

	public EstadoCasilla color() {
		return color;
	}
	
}
