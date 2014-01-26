package logica.ia.buscadores;

import java.awt.Point;

/**
 * Un resultado de b�squeda consiste en el mapeo de un movimiento al 
 * resultado obtenido por el m�todo de evaluaci�n que se eligi�.
 * 
 * Alternativamente, podr�amos mantener una tabla hash movidas/llaves 
 * a resultados/valores, pero que en realidad no necesitamos almacenar 
 * m�s el resultado de la b�squeda con el mejor movimiento, al mismo 
 * tiempo. Ese mapa tendr�a siempre un tama�o de 1, pues si fu�ramos 
 * a insertar una nueva asignaci�n no necesitar�amos todas las anteriores. 
 */

public class ResultadoBusqueda {
	
	private Point punto;
	private double puntaje;
	
	public double obtenerPuntaje() {
		return puntaje;
	}
	
	public Point obtenerPunto() {
		return punto;
	}
	
	public ResultadoBusqueda negado() {
		return new ResultadoBusqueda(punto, -puntaje);
	}
	
	public ResultadoBusqueda(Point punto, double puntaje) {
		this.punto = punto;
		this.puntaje = puntaje;
	}
}
