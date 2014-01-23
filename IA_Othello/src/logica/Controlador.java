package logica;

import java.awt.Point;
import java.util.Set;

import logica.ia.buscadores.BuscadorAbstracto;
import logica.ia.evaluacion.Evaluacion;
import logica.ia.evaluacion.EvaluacionDifPuntaje;
import logica.ia.buscadores.NegaMax;

import core.EstadoCasilla;
import core.Jugador;
import core.Tablero;

public final class Controlador {
	
	private Tablero tablero;
	private Jugador jugador;
	public static final int PROFUNDIDAD_POR_DEFECTO = 4;
	private static int profundidad = PROFUNDIDAD_POR_DEFECTO;
	/* 0: todos bien , 1: uno no puede mover , 2: ninguno puede mover */
	private final short PUEDEMOVER = 0, NOPUEDEMOVER = 2;
	private short puedeMover = PUEDEMOVER;
	
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
	
	public Set<Point> hacerMovimiento(Point movida) {
		return tablero.hacerMovimiento(movida, jugador.color());
	}
	/*
	private int calcularPuntaje(EstadoCasilla estado) {
		return tablero.contar(estado);
	}
	*/
	public int obtenerPuntajeNegro() {
		return tablero.contar(EstadoCasilla.BLACK);
	}
	
	public int obtenerPuntajeBlanco() {
		return tablero.contar(EstadoCasilla.WHITE);
	}
	
	public Jugador obtenerGanador() {
		return obtenerPuntajeNegro() < obtenerPuntajeBlanco() ? Jugador.WHITE : Jugador.BLACK;
	}
	
	public boolean esEmpate() {
		return obtenerPuntajeNegro() == obtenerPuntajeBlanco();
	}
	
	/**
	 * El juego termina si: <br/>
	 * <ol>
	 * <li> el tablero está lleno</li>
	 * <li> un jugador tiene puntaje = 0</li>
	 * <li> ninguno tiene una siguiente movida válida</li>
	 * </ol>
	 *
	 * @return si el juego está terminado
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
	}
	
	public void inicializar(int largoTablero, int anchoTablero) {
		this.tablero = 	new Tablero(largoTablero,anchoTablero);
		tablero.inicializar();
		jugador = Jugador.BLACK;
		puedeMover = PUEDEMOVER;
	}
		
	/* este método es el que se utilizará 
	 * para que el computador evalue la mejor jugada
	 * ese debe utilizar mini max y algoritmos genéticos.
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
