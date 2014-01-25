package logica;

import java.awt.Point;
import java.util.Map;
import java.util.Set;

import logica.ia.buscadores.BuscadorAbstracto;
import logica.ia.evaluacion.Evaluacion;
import logica.ia.evaluacion.EvaluacionDifPuntaje;
import logica.ia.buscadores.NegaMax;

import core.EstadoCasilla;
import core.Jugador;
import core.Tablero;
import core.Tablero.TipoTablero;

public final class Controlador {
	
	private Tablero tablero;
	private Jugador jugador;
	public static final int PROFUNDIDAD_POR_DEFECTO = 4;
	private static int profundidad = PROFUNDIDAD_POR_DEFECTO;
	/* 0: todos bien , 1: uno no puede mover , 2: ninguno puede mover */
	private final short PUEDEMOVER = 0, NOPUEDEMOVER = 2;
	private short puedeMover = PUEDEMOVER;
	public boolean estaJuegoTerminado;
	
	public Controlador() {
		super();
		inicializar();
	}
	
	public Set<Point> marcarMovidasPosibles() {
		Set<Point> movidas = tablero.obtenerMovidasPosibles(jugador);
		tablero.marcarMovidasPosibles(movidas);
		puedeMover = movidas.isEmpty() ? ++puedeMover : PUEDEMOVER;
		return movidas;
	}
	
	public void desmarcarMovidasPosibles() {
		tablero.desmarcarMovidasPosibles();
	}
	
	public void actualizarEstadoCasilla(Point punto, EstadoCasilla estadoCasilla) {
		tablero.actualizarEstadoCasilla(punto, estadoCasilla);
	}
	
	public Set<Point> hacerMovimiento(Point movida) {
		return tablero.hacerMovimiento(movida, jugador.color());
	}

	public int obtenerPuntajeNegro() {
		jugador.setPuntajeNegro(tablero.contar(EstadoCasilla.BLACK));
		return jugador.getPuntajeNegro();
	}
	
	public int obtenerPuntajeBlanco() {
		jugador.setPuntajeBlanco(tablero.contar(EstadoCasilla.WHITE));
		return jugador.getPuntajeBlanco();
	}
	
	public Jugador obtenerGanador() {
		return jugador.getPuntajeNegro() < jugador.getPuntajeBlanco() ? Jugador.WHITE : Jugador.BLACK;
	}
	
	public boolean esEmpate() {
		return jugador.getPuntajeNegro() == jugador.getPuntajeBlanco();
	}
	
	/**
	 * El juego termina si: <br/>
	 * <ol>
	 * <li> el tablero est� lleno</li>
	 * <li> un jugador tiene puntaje = 0</li>
	 * <li> ninguno tiene una siguiente movida v�lida</li>
	 * </ol>
	 *
	 * @return si el juego est� terminado
	 */
	
	public boolean finDelJuego() {	
		return tablero.estaLleno() || verificarPuntajeEnCero() || puedeMover == NOPUEDEMOVER;
	}
	
	private boolean verificarPuntajeEnCero() {
		return obtenerPuntajeNegro() == 0 || obtenerPuntajeBlanco() == 0;
	}
	
	public void cambiarTurno() {
		jugador = jugador.oponente();
	}
	
	public Jugador jugadorActual() {
		return jugador;
	}
	
	public String tableroConTurno() {
		return tablero.textoConEstadoTurno(jugador);
	}

	public void inicializar() {
		this.tablero = 	new Tablero();
		tablero.inicializar();
		jugador = Jugador.BLACK;
		puedeMover = PUEDEMOVER;
		
		estaJuegoTerminado=false;
	}
	
	public void inicializar(int largoTablero, int anchoTablero, TipoTablero tipoTablero) {
		this.tablero = 	new Tablero(largoTablero, anchoTablero, tipoTablero);
		tablero.inicializar();
		jugador = Jugador.BLACK;
		puedeMover = PUEDEMOVER;
	}
	
	public void inicializar(Map<Point, EstadoCasilla> tablero, EstadoCasilla colorJugador) {
		this.tablero = 	new Tablero(tablero);
		jugador = Jugador.BLACK;
		if (colorJugador == EstadoCasilla.WHITE) {
			jugador = Jugador.WHITE;
		} 		
		puedeMover = PUEDEMOVER;
	}
	
	public Tablero obtenerTableroLogica(){
		return tablero;
	}
		
	/* este m�todo es el que se utilizar� 
	 * para que el computador evalue la mejor jugada
	 * ese debe utilizar mini max y algoritmos gen�ticos.
	 * */
	public Point evaluarMovida() {
		BuscadorAbstracto buscador;
		Evaluacion funcionEvaluacion;
		buscador = new NegaMax();
		funcionEvaluacion = new EvaluacionDifPuntaje();
		return buscador.busquedaSimple(tablero, jugador, profundidad, funcionEvaluacion).obtenerPunto();
	}
	
	
	private static class soporteControlador {
		private static final Controlador INSTANCE = new Controlador();
	}
	
	public static Controlador obtenerInstancia() {
		return soporteControlador.INSTANCE;
	}
	
	/*public static Controlador obtenerInstanciaPersonalizada(int largoTablero, int anchoTablero) {
		final Controlador INSTANCE = new Controlador(largoTablero,anchoTablero);
		return INSTANCE;
	}*/
	
	

}
