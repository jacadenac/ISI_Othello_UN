package logica;

import java.awt.Point;

public enum Direccion {
	/* ↑ */ NORTH(-1, 0),
	/* ↓ */ SOUTH(+1, 0),
	/* ← */ WEST(0, -1),
	/* → */ EAST(0, +1),
	/* ↖ */ NORTHWEST(-1, -1),
	/* ↘ */ SOUTHEAST(+1, +1),
	/* ↙ */ SOUTHWEST(+1, -1),
	/* ↗ */ NORTHEAST(-1, +1);
	private int pasoEnFila;
	private int pasoEnColumna;

	private Direccion(int pasoEnFila, int pasoEnColumna) {
		this.pasoEnFila = pasoEnFila;
		this.pasoEnColumna = pasoEnColumna;
	}

	public Point siguiente(Point point) {
		return new Point(point.x + pasoEnFila, point.y + pasoEnColumna);
	}
}
