package logica.ia.evaluacion;

import core.EstadoCasilla;
import core.Tablero;
import core.Tablero.TipoTablero;

import java.awt.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import logica.ExploradorMovimientos;

public class EvaluacionEstrategica implements Evaluacion{
	int A,B,C,D;
	double x1;
	double x2;
	double x3;
	double x4;

	public EvaluacionEstrategica(int[] estrategia) {
		A = estrategia[0];
		B = estrategia[1];
		C = estrategia[2];
		D = estrategia[3];
	}

	@Override
	public double evaluar(Tablero tablero, TipoTablero tipoTablero, EstadoCasilla colorJugador) {
		x1 = porcentajePuntos(tablero, colorJugador);
		x2 = porcentajeEsquinas(tablero, tipoTablero, colorJugador);
		x3 = porcentajeJugadasPosiblesRival(tablero, colorJugador);
		x4 = porcentajeCasillasCentrales(tablero, colorJugador);
		return ((double)(A)*x1)+((double)(B)*x2)-((double)(C)*x3)+((double)(D)*x4);
		
	}
	
	private double porcentajePuntos(Tablero tablero, EstadoCasilla colorJugador) {
		
		/* Este sólo sirve para tablero Clásico */ 
		if (colorJugador == EstadoCasilla.BLACK){
			return (tablero.contar(colorJugador) - tablero.contar(EstadoCasilla.WHITE))/
					(Tablero.TABLERO_LARGO*Tablero.TABLERO_ANCHO);
		} else {
			return tablero.contar(colorJugador) - tablero.contar(EstadoCasilla.BLACK)/
					(Tablero.TABLERO_LARGO*Tablero.TABLERO_ANCHO);
		}
		
		/* Para otros tableros debe obtenerse el número total de casillas vacías inicialmente sumándole las 4 centrales */
	}
	
	private double porcentajeEsquinas(Tablero tablero, TipoTablero tipoTablero, EstadoCasilla colorJugador) {	
		int totalEsquinas;
		int esquinasJugador=0;
		int esquinasRival=0;
		EstadoCasilla colorRival = colorJugador == EstadoCasilla.BLACK ? EstadoCasilla.WHITE : EstadoCasilla.BLACK;
		List<Point> ubicacionEsquinas;
		totalEsquinas = 4;
		if (tipoTablero  == TipoTablero.OCTOGONAL) {
			totalEsquinas = 8;
		} else if (tipoTablero  == TipoTablero.PERSONALIZADO){ //si es personalizado debe detectar número de esquinas
			
		}
		ubicacionEsquinas = detectarEsquinas(tipoTablero);
		ListIterator<Point> iterador = ubicacionEsquinas.listIterator();
		while( iterador.hasNext() ) {
		       Point punto = (Point) iterador.next();
				if (tablero.obtenerTablero().get(punto) == colorJugador){
					esquinasJugador++;
				}else if (tablero.obtenerTablero().get(punto) == colorRival) {
					esquinasRival++;
				}
		       
		}
		/*System.out.println(colorJugador +" =" + esquinasJugador);
		System.out.println(colorRival +" =" + esquinasRival);*/
		return (double)(esquinasJugador-esquinasRival)/(double)totalEsquinas;
	}
	

	private double porcentajeJugadasPosiblesRival(Tablero tablero, EstadoCasilla colorJugador) {
		EstadoCasilla colorRival = colorJugador == EstadoCasilla.BLACK ? EstadoCasilla.WHITE : EstadoCasilla.BLACK;
		int movidasPosiblesRival=0;
		
		Set<Point> movidasPosibles = ExploradorMovimientos.explorar(tablero, colorJugador);
		if (movidasPosibles.isEmpty()) { /* se pierde turno - revisar siguiente jugador */
			movidasPosibles = ExploradorMovimientos.explorar(tablero, colorRival);
			movidasPosiblesRival = movidasPosibles.size();
		}else { /* revisar el puntaje despues de una movida */
			for (Point siguienteMovimientoPosible : movidasPosibles) {
				Tablero subTablero = tablero.clone();
				subTablero.hacerMovimiento(siguienteMovimientoPosible, colorJugador);
				movidasPosibles = ExploradorMovimientos.explorar(subTablero, colorRival);
				if(movidasPosibles.size() > movidasPosiblesRival){/*toma el peor de los casos (la mayor cantidad de libertades para el oponente)*/
					movidasPosiblesRival = movidasPosibles.size();
				}
				
			}
		}
		return (double)movidasPosiblesRival/(double)Tablero.casillasjugablesIniciales;  /*  /(double)totalCasillasJugables */
	}

	private double porcentajeCasillasCentrales(Tablero tablero, EstadoCasilla colorJugador) {
		int totalCasillasCentrales = 4;
		int casillasCentralesJugador=0;
		int casillasCentralesRival=0;
		EstadoCasilla colorRival = colorJugador == EstadoCasilla.BLACK ? EstadoCasilla.WHITE : EstadoCasilla.BLACK;
		List<Point> centrales = new ArrayList<Point>(4);
		centrales.add(new Point((int)(Tablero.TABLERO_LARGO/2)-1, (int)(Tablero.TABLERO_LARGO/2)-1));
		centrales.add(new Point((int)(Tablero.TABLERO_LARGO/2)-1, (int)(Tablero.TABLERO_LARGO/2)));
		centrales.add(new Point((int)(Tablero.TABLERO_LARGO/2), (int)(Tablero.TABLERO_LARGO/2)-1));
		centrales.add(new Point((int)(Tablero.TABLERO_LARGO/2), (int)(Tablero.TABLERO_LARGO/2)));
		ListIterator<Point> iterador = centrales.listIterator();
		while( iterador.hasNext() ) {
		       Point punto = (Point) iterador.next();
				if (tablero.obtenerTablero().get(punto) == colorJugador){
					casillasCentralesJugador++;
				}else if (tablero.obtenerTablero().get(punto) == colorRival) {
					casillasCentralesRival++;
				}
		}
		
		/*System.out.println(colorJugador +" = " + casillasCentralesJugador);
		System.out.println(colorRival +" = " + casillasCentralesRival);
		System.out.println((double)(casillasCentralesJugador-casillasCentralesRival)/(double)totalCasillasCentrales);*/
		return (double)(casillasCentralesJugador-casillasCentralesRival)/(double)totalCasillasCentrales;
	}
	

	private List<Point> detectarEsquinas(TipoTablero tipoTablero) {
		List<Point> esquinas = null;
		if (tipoTablero == TipoTablero.CLASICO) {
			esquinas = new ArrayList<Point>(4);
			esquinas.add(new Point(1, 1));
			esquinas.add(new Point(Tablero.TABLERO_LARGO-2, 1));
			esquinas.add(new Point(1, Tablero.TABLERO_ANCHO-2));
			esquinas.add(new Point(Tablero.TABLERO_LARGO-2, Tablero.TABLERO_ANCHO-2));
		} if (tipoTablero == TipoTablero.OCTOGONAL) {
			esquinas = new ArrayList<Point>(8);
			esquinas.add(new Point( 0, (int)(Tablero.TABLERO_LARGO/2)-1) );
			esquinas.add(new Point( 0, (int)(Tablero.TABLERO_LARGO/2))   );
			esquinas.add(new Point( (int)((Tablero.TABLERO_ANCHO/2)-1), 0) );
			esquinas.add(new Point( (int)((Tablero.TABLERO_ANCHO/2)), 0) );
			esquinas.add(new Point( (int)((Tablero.TABLERO_ANCHO/2)-1), Tablero.TABLERO_LARGO-1) );
			esquinas.add(new Point( (int)((Tablero.TABLERO_ANCHO/2)), Tablero.TABLERO_LARGO-1) );
			esquinas.add(new Point( Tablero.TABLERO_LARGO-1, (int)(Tablero.TABLERO_LARGO/2)-1) );
			esquinas.add(new Point( Tablero.TABLERO_LARGO-1, (int)(Tablero.TABLERO_LARGO/2))   );
		} else { //si es personalizado debe detectar número de esquinas
			//detectarEsquinas();
			
			/* detectarEsquinas(Tablero tablero, TipoTablero tipoTablero) explorar el tablero para detectar las esquinas: deber retornar un ArrayList<Point>*/
			esquinas = new ArrayList<Point>(4);
			esquinas.add(new Point(1, 1));
			esquinas.add(new Point(Tablero.TABLERO_LARGO-2, 1));
			esquinas.add(new Point(1, Tablero.TABLERO_ANCHO-2));
			esquinas.add(new Point(Tablero.TABLERO_LARGO-2, Tablero.TABLERO_ANCHO-2));
		}
		return esquinas;
	}


}
