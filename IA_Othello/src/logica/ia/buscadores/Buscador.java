package logica.ia.buscadores;

import logica.ia.evaluacion.Evaluacion;
import core.Jugador;
import core.Tablero;

interface Buscador {
	ResultadoBusqueda busqueda(final Tablero tablero, final Jugador jugador, int alfa,
			int beta, final int profundidad, final Evaluacion funcion);
}
