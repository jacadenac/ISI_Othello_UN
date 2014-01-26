package logica.ia.evaluacion;

import core.EstadoCasilla;
import core.Tablero;
import core.Tablero.TipoTablero;

/**
 * Interfaz de la funci�n de evaluaci�n. Cada m�todo de evaluaci�n debe implementar esta
 * 
 * @author Alejandro
 */

public interface Evaluacion {
	public double evaluar(final Tablero tablero, TipoTablero tipoTablero, final EstadoCasilla colorJugador);
}
