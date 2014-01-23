package logica.ia.buscadores;

import core.Jugador;
import core.Tablero;
import logica.ia.evaluacion.Evaluacion;

interface BuscadorSimple {
	ResultadoBusqueda busquedaSimple(final Tablero tablero, final Jugador jugador,
			final int profundidad, final Evaluacion funcion);
}
