package logica.ia.evaluacion;

import core.Jugador;
import core.Tablero;

public class EvaluacionDifPuntaje implements Evaluacion{

	@Override
	public int evaluar(Tablero tablero, Jugador jugador) {
		return tablero.contar(jugador.color()) - tablero.contar(jugador.oponente().color());
	}

}
