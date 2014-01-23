package logica.ia.evaluacion;

import core.Jugador;
import core.Tablero;

/**
 * Interfaz de la funci�n de evaluaci�n. Cada m�todo de evaluaci�n debe implementar esta
 * 
 * @author Alejandro
 */

public interface Evaluacion {
	public int evaluar(final Tablero tablero, final Jugador jugador);
}
