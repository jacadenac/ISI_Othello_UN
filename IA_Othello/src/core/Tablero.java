package core;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import logica.ExploradorMovimientos;

public class Tablero {
	
	public static final int TABLERO_LARGO = 10;//14;
	public static final int TABLERO_ANCHO = 10;//14;
	private Map<Point, EstadoCasilla> tablero;
	
	public Tablero() {
		super();
		tablero = new HashMap<Point, EstadoCasilla>(TABLERO_LARGO * TABLERO_ANCHO);
		inicializar();
	}
	
	public Tablero(Map<Point, EstadoCasilla> tablero) {
		super();
		this.tablero = new HashMap<Point, EstadoCasilla>(tablero.size());
		for(Point point : tablero.keySet()) {
			this.tablero.put(new Point(point), tablero.get(point));
		}
	}

	public void inicializar() {
		Point point = new Point();
		for (point.x = 0; point.x < TABLERO_LARGO; point.x++) {
			for (point.y = 0; point.y < TABLERO_ANCHO; point.y++) {
				if(point.x == 0 || point.x == TABLERO_LARGO-1 || point.y == 0 || point.y == TABLERO_ANCHO-1){
					tablero.put(new Point(point), EstadoCasilla.WALL);
				}
				else {
					tablero.put(new Point(point), EstadoCasilla.EMPTY);
				}
			}
		}
		tablero.put(new Point((int)(Tablero.TABLERO_LARGO/2)-1, (int)(Tablero.TABLERO_LARGO/2)-1), EstadoCasilla.WHITE);
		tablero.put(new Point((int)(Tablero.TABLERO_LARGO/2)-1, (int)(Tablero.TABLERO_LARGO/2)), EstadoCasilla.BLACK);
		tablero.put(new Point((int)(Tablero.TABLERO_LARGO/2), (int)(Tablero.TABLERO_LARGO/2)-1), EstadoCasilla.BLACK);
		tablero.put(new Point((int)(Tablero.TABLERO_LARGO/2), (int)(Tablero.TABLERO_LARGO/2)), EstadoCasilla.WHITE);
	}
	
	public EstadoCasilla obtenerEstadoCasilla(Point point){
		return tablero.get(point);
	}
	
	public Set<Point> obtenerCasillas(EstadoCasilla estado){
		Set<Point> points = new HashSet<Point>();
		for (Point point : tablero.keySet()){
			if (tablero.get(point) == estado){
				points.add(point);
			}
		}
		return points;
	}
	
	public boolean estaLleno(){
		for (Point point : tablero.keySet()){
			if (tablero.get(point) == EstadoCasilla.EMPTY){
				return false;
			}
		}
		return true;
	}
	
	public int contar(EstadoCasilla estado) {
		int contar = 0;
		for (Point point : tablero.keySet()) {
			if (tablero.get(point) == estado) {
				contar++;
			}
		}
		return contar;
	}
	
	public Set<Point> obtenerMovidasPosibles(Jugador jugador) {
		return ExploradorMovimientos.explorar(this, jugador.color());
	}
	
	public void marcarMovidasPosibles(Set<Point> movidasPosibles) {
		for (Point point : movidasPosibles) {
			tablero.put(point, EstadoCasilla.PSSBL);
		}
	}
	
	public void desmarcarMovidasPosibles() {
		for (Point point : tablero.keySet()) {
			if (tablero.get(point) == EstadoCasilla.PSSBL) {
				tablero.put(point, EstadoCasilla.EMPTY);
			}
		}
	}
	
	public void marcarEstado(Set<Point> points, EstadoCasilla estado) {
		for (Point point : points) {
			tablero.put(point, estado);
		}
	}
	
	public Set<Point> hacerMovimiento(Point movida, EstadoCasilla estado) {
		tablero.put(movida, estado);
		Set<Point> casillasCambiadas = ExploradorMovimientos.casillasParaLlenar(this, movida);
		marcarEstado(casillasCambiadas, estado);
		casillasCambiadas.add(movida);
		return casillasCambiadas;
	}
	
	@Override
	public String toString() {
		Point point = new Point();
		StringBuilder sb = new StringBuilder();
		sb.append("  A B C D E F G H");
		for (point.x = 0; point.x < TABLERO_LARGO; point.x++) {
			sb.append('\n').append(point.x + 1);
			for (point.y = 0; point.y < TABLERO_ANCHO; point.y++) {
				sb.append(' ').append(tablero.get(point).symbol());
			}
		}
		sb.append('\n');
		return sb.toString();
	}
	
	@Override
	public Tablero clone() {
		return new Tablero(this.tablero);
	}
	
	public String textoConEstadoTurno(Jugador jugador){
		StringBuilder sb = new StringBuilder();
		String[] filas = toString().split("\n");
		for (int fila = 0; fila < filas.length; fila++) {
			sb.append('\n').append(filas[fila]);
			switch (fila) {
				case 2:
					sb.append('\t').append(EstadoCasilla.BLACK.symbol()).
						append(' ').append(Jugador.BLACK).
						append(": ").append(contar(EstadoCasilla.BLACK));
					break;
				case 4:
					sb.append('\t').append(EstadoCasilla.WHITE.symbol()).
						append(' ').append(Jugador.WHITE).
						append(": ").append(contar(EstadoCasilla.WHITE));
					break;
				case 6:
					sb.append('\t').append(jugador).append("su turno!");
					break;
			}
		}
		sb.append('\n');
		return sb.toString();
	}
	

}
