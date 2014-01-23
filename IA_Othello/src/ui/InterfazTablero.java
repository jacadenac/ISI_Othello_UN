package ui;

import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;

import ui.FabricaImagenCasilla.TipoCasilla;
import utilidades.Transform;


public class InterfazTablero extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public enum TipoTablero {
		CLASICO,
		OCTOGONAL,
		PERSONALIZADO;
	}
	private TipoTablero tipoTablero;
	private List<ComponenteImagen> casillas;
	private int largoTablero;
	private int anchoTablero;

	public InterfazTablero() {
		this.largoTablero = 10;
		this.anchoTablero = 10;
		this.tipoTablero = TipoTablero.CLASICO;
		iniciarComponentes();
	}
	
	public InterfazTablero(TipoTablero tipoTablero, int largoTablero, int anchoTablero) {
		this.largoTablero = largoTablero;
		this.anchoTablero = anchoTablero;
		this.tipoTablero = tipoTablero;
		iniciarComponentes();
	}

	private void iniciarComponentes() {
		casillas = new ArrayList<ComponenteImagen>(largoTablero * anchoTablero);
		setLayout(new java.awt.GridLayout(largoTablero, anchoTablero));
		switch(tipoTablero){
			case CLASICO:
				dibujarTableroClasico();
				break;
			case OCTOGONAL:
				dibujarTableroOctogonal();
				break;
			case PERSONALIZADO:
				dibujarTableroPersonalizado();
				break;
		}

	}

	private void dibujarTableroClasico() {
		for ( int fila = 0; fila < largoTablero; fila++){
			for (int columna = 0; columna < anchoTablero; columna++){
				/*if (fila == 2 && columna == 3){
					ComponenteImagen casillaMuro = FabricaImagenCasilla.construirCasilla(TipoCasilla.WALL);
					this.add(casillaMuro);
					continue;
				}*/
				if(fila == 0 ||columna ==0 || fila == largoTablero-1 || columna == anchoTablero-1){
					ComponenteImagen casillaMuro = FabricaImagenCasilla.construirCasilla(TipoCasilla.WALL);
					this.add(casillaMuro);
					casillas.add(casillaMuro);
				}
				else {
					ComponenteImagen casillaVacia = FabricaImagenCasilla.construirCasilla(TipoCasilla.EMPTY);
					this.add(casillaVacia);
					casillas.add(casillaVacia);
				}
			}
			
		}
		
		/* Pintar puntos de partida */
		definirCasilla(new Point((int)(largoTablero/2)-1, (int)(anchoTablero/2)-1), TipoCasilla.WHITE);
		definirCasilla(new Point((int)(largoTablero/2)-1, (int)(anchoTablero/2)), TipoCasilla.BLACK);
		definirCasilla(new Point((int)(largoTablero/2), (int)(anchoTablero/2)-1), TipoCasilla.BLACK);
		definirCasilla(new Point((int)(largoTablero/2), (int)(anchoTablero/2)), TipoCasilla.WHITE);
		
	}
	
	private void dibujarTableroOctogonal() {
		// TODO Auto-generated method stub
		
	}
	
	private void dibujarTableroPersonalizado() {
		// TODO Auto-generated method stub
		
	}
	
	void definirCasilla(Point point, TipoCasilla tipoCasilla) {
		ComponenteImagen imgcomp = FabricaImagenCasilla.construirCasilla(tipoCasilla);
		int index = Transform.pointToIndex(point);
		casillas.set(index, imgcomp);
		this.remove(index);
		this.add(imgcomp, index);
	}
	
	void marcarMovidasPosibles(Collection<Point> movidasPosibles, TipoCasilla color){
		for (Point pssblPoint : movidasPosibles) {
			definirCasilla(pssblPoint, color);
		}
		this.revalidate();
	}
	
	void desmarcarMovidasPosibles(Collection<Point> movidasPosibles){
		for (Point pssblPoint : movidasPosibles) {
			definirCasilla(pssblPoint, TipoCasilla.EMPTY);
		}
		this.revalidate();
	}
	
	void rellenar(Collection<Point> puntosConRelleno, TipoCasilla color){
		for (Point aRellenar : puntosConRelleno) {
			definirCasilla(aRellenar, color);
		}
		this.revalidate();
	}
	
	
}
