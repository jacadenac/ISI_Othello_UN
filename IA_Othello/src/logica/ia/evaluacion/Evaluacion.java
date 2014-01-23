package logica.ia.evaluacion;

import core.Jugador;
import core.Tablero;

/**
 * Interfaz de la función de evaluación. Cada método de evaluación debe implementar esta
 * 
 * @author Alejandro
 */

public interface Evaluacion {
	public int evaluar(final Tablero tablero, final Jugador jugador);
}
