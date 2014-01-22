package othello;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

import core.EstadoCasilla;
import core.Jugador;

import logica.Controlador;

import ui.ComponenteImagen;
import ui.FabricaImagenCasilla.TipoCasilla;
import ui.TableroUI;
import utilidades.Transform;
//import logic.Controller;

public class UIJuego implements Runnable{

	private Controlador controlador = Controlador.obtenerInstancia();
	private TableroUI tableroUI;
	private Set<Point> movidasPosibles;
	
	public UIJuego() {
		super();
		this.controlador.inicializar();
		iniciarTableroUI();
	}
	
	private void iniciarTableroUI(){
		tableroUI = new TableroUI();
		tableroUI.setVisible(true);
		tableroUI.obtenerItemNuevoJuego().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableroUI.dispose();
				controlador.inicializar();
				iniciarTableroUI();
				run();
			}
		});
		
		//se omite dificultad

	}

	@Override
	public void run() {
		if (controlador.finDelJuego()) {
			juegoTerminado();
		} else {
			movidasPosibles = marcarMovidasPosibles();
			if (movidasPosibles.isEmpty()) {
				pasar();
				run();
			}
			if (controlador.jugadorActual() != tableroUI.obtenerJugadorSeleccionado()
					&& tableroUI.obtenerOponentes() == tableroUI.HUM_ROB) {
				tableroUI.desmarcarMovidasPosibles(movidasPosibles);
				//Point movidaComputador = controlador.evaluarMovida();
				//hacerMovida(movidaComputador);
				//despuesDeMovida();
			}
		}
		
	}

	
	private Set<Point> marcarMovidasPosibles() {
		Set<Point> movidas = controlador.marcarMovidasPosibles();
		controlador.desmarcarMovidasPosibles();
		if (!movidas.isEmpty()){
			TipoCasilla color = controlador.jugadorActual().color() == EstadoCasilla.WHITE
					? TipoCasilla.PSSBLWHT : TipoCasilla.PSSBLBLK;
			tableroUI.marcarMovidasPosibles(movidas, color);
		}
		ActualizarListeners();
		return movidas;
	}

	private void ActualizarListeners() {
		for (ComponenteImagen compImg : tableroUI.obtenerCasillas()){
			if (compImg.getMouseListeners().length != 0){
				continue;
			}
			compImg.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent evt){
					aSucedidoClick(evt.getComponent());
				}
			});
		}
		
	}

	private void aSucedidoClick(Component compImg) {
		int index = tableroUI.obtenerCasillas().indexOf(compImg);
		Point movidaSeleccionada = Transform.indexToPoint(index);
		if (movidasPosibles.contains(movidaSeleccionada)) {
			tableroUI.desmarcarMovidasPosibles(movidasPosibles);
			hacerMovida(movidaSeleccionada);
			despuesDeMovida();
		}
		
	}
	
	/*Falta añadir aSucedidoClick para añadir muros al tablero*/

	private void hacerMovida(Point movida) {
		TipoCasilla color = controlador.jugadorActual().color() == EstadoCasilla.WHITE
				? TipoCasilla.WHITE : TipoCasilla.BLACK;
		Set<Point> casillasPorCambiar = controlador.hacerMovimiento(movida);
		tableroUI.rellenar(casillasPorCambiar, color);
		
		
	}
	
	private void despuesDeMovida() {
		actualizarEstadisticas();
		cambiarTurno();
		run();
	}
	
	private void pasar() {
		turnoPerdido();
		actualizarEstadisticas();
	}

	private void turnoPerdido() {
		tableroUI.notificarTurnoPerdido(controlador.jugadorActual());
		cambiarTurno();
	}

	private void cambiarTurno() {
		controlador.cambiarTurno();
		tableroUI.actualizarTurno(controlador.jugadorActual().toString());
	}

	private void actualizarEstadisticas() {
		tableroUI.actualizarPuntaje(controlador.obtenerPuntajeNegro(), controlador.obtenerPuntajeBlanco());
	}

	private void juegoTerminado() {
		actualizarEstadisticas();
		if (controlador.esEmpate()) {
			tableroUI.declararEmpate();
		} else {
			String ganador = controlador.obtenerGanador() == Jugador.BLACK ? "Negras" : "Blancas";
			tableroUI.declararGanador(ganador);
			tableroUI.notificarVictoria(controlador.obtenerGanador());
		}
	}
	

}
