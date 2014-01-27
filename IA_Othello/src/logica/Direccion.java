package logica;

import java.awt.Point;

public enum Direccion {
	/* â†‘ */ NORTH(-1, 0),
	/* â†“ */ SOUTH(+1, 0),
	/* â†� */ WEST(0, -1),
	/* â†’ */ EAST(0, +1),
	/* â†– */ NORTHWEST(-1, -1),
	/* â†˜ */ SOUTHEAST(+1, +1),
	/* â†™ */ SOUTHWEST(+1, -1),
	/* â†— */ NORTHEAST(-1, +1);
	private int pasoEnFila;
	private int pasoEnColumna;

	private Direccion(int pasoEnFila, int pasoEnColumna) {
		this.pasoEnFila = pasoEnFila;
		this.pasoEnColumna = pasoEnColumna;
	}

	public Point siguiente(Point point) {
		/* */
		if(point == null){
			System.out.println("El punto es nulooooo!!!");
		}
		return new Point(point.x + pasoEnFila, point.y + pasoEnColumna);
	}
}
