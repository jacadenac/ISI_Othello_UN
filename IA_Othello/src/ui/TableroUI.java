package ui;

import core.EstadoCasilla;
import core.Jugador;
import core.Tablero;
import core.Tablero.TipoTablero;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

import ui.FabricaImagenCasilla.TipoCasilla;
import utilidades.Transform;
import othello.Othello;
import othello.UIJuego;



public final class TableroUI extends JFrame {
	
	public static final int HUM_HUM = 0;
	public static final int HUM_ROB = 1;
	public static final int ROB_ROB = 2;
	
	public static int largoTablero = Tablero.TABLERO_LARGO;
	public static int anchoTablero = Tablero.TABLERO_LARGO;

	private final Image LOGO = new ImageIcon(getClass().getResource("/ui/imagenes/logo.jpg")).getImage();
	private static int oponentes = HUM_HUM;
	private static Jugador humano = Jugador.BLACK;
	private List<ComponenteImagen> casillas;
	private JPanel tablero;
	private JLabel estadisticasBlanco;
	private JLabel estadisticasNegro;
	private JLabel mostrarTurno;
	private JRadioButtonMenuItem[] diffbuttons;
	private JMenuItem juegoNuevo;
	private JMenuItem guardarJuego;
	private JMenuItem abrirJuego;
	private JRadioButtonMenuItem tableroClasico;
	private JRadioButtonMenuItem tableroOctogonal;
	private JRadioButtonMenuItem tableroPersonalizado;
	private JRadioButtonMenuItem tablero10x10;
	private JRadioButtonMenuItem tablero12x12;
	private JRadioButtonMenuItem tablero14x14;
	private Tablero tableroLogica;
	private JPanel barraEstado;
	private JPanel barraBoton;
	private Container pane = this.getContentPane();
	public JButton boton;
	TipoTablero tipoTablero;

	
	
	public TableroUI() throws HeadlessException {
		casillas = new ArrayList<ComponenteImagen>(largoTablero*anchoTablero);
		//iniciarComponentes(this.getContentPane());
		tipoTablero=TipoTablero.CLASICO;
		iniciarComponentes();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle(Othello.class.getSimpleName());
		this.setIconImage(LOGO);
		this.setResizable(false);
	}
	
	public TableroUI(int largo, int ancho, Tablero tableroLogica, TipoTablero tipoTablero) throws HeadlessException {
		largoTablero = largo;
		anchoTablero = ancho;
		this.tableroLogica = tableroLogica;
		this.tipoTablero=tipoTablero;
		casillas = new ArrayList<ComponenteImagen>(tableroLogica.obtenerTablero().size());
		iniciarComponentes();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle(Othello.class.getSimpleName());
		this.setIconImage(LOGO);
		this.setResizable(false);
	}
	
	
	private void iniciarComponentes(){
		pane.setLayout(new GridBagLayout());
		GridBagConstraints constrains = new GridBagConstraints();
		ButtonGroup buttongroup = new ButtonGroup();
		JRadioButtonMenuItem radiobutton;
		
		/* Dibujar la barra de men�*/
		JMenuBar barraMenu = new JMenuBar();
		
		/* A�adir men� e �tems*/
		
		/*
		 *  ARCHIVO
		 */
		JMenu menu = new JMenu("Archivo");
		barraMenu.add(menu);
		JMenuItem exit, about;
		
		/* -------- Juego Nuevo -------*/
		juegoNuevo = new JMenuItem("Juego Nuevo");
		menu.add(juegoNuevo);
		
		/* -------- Abrir Juego --------*/
		abrirJuego = new JMenuItem("Abrir");
		menu.add(abrirJuego);
		
		/* -------- Guardar Juego ------*/
		guardarJuego = new JMenuItem("Guardar");
		menu.add(guardarJuego);
		
		
		/* ------ Salir del juego -----*/
		exit = new JMenuItem("Salir");
		menu.add(exit);
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){System.exit(0);}
		});
		
		
		/*
		 *  EDICION
		 */
		menu = new JMenu("Edici�n");
		barraMenu.add(menu);
		
		/* ------ Elegir Color -----*/
		JMenu submenu = new JMenu("Color del Jugador");
		buttongroup = new ButtonGroup();
		radiobutton = new JRadioButtonMenuItem("Negro");
		radiobutton.setSelected(true);
		radiobutton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				humano = Jugador.BLACK;
			}
		}); 
		buttongroup.add(radiobutton);
		submenu.add(radiobutton);
		radiobutton = new JRadioButtonMenuItem("Blanco");
		radiobutton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				humano = Jugador.WHITE;
			}
		});
		buttongroup.add(radiobutton);
		submenu.add(radiobutton);
		menu.add(submenu);
		
		/* ------ Definir Oponentes -----*/
		submenu = new JMenu("Definir Oponentes");
		buttongroup = new ButtonGroup();
		radiobutton = new JRadioButtonMenuItem("Humano vs Humano");
		radiobutton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				oponentes = HUM_HUM;
			}
		});
		radiobutton.setSelected(true);
		buttongroup.add(radiobutton);
		submenu.add(radiobutton);
		
		radiobutton = new JRadioButtonMenuItem("Humano vs Robot");
		radiobutton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				oponentes = HUM_ROB;
			}
		});
		buttongroup.add(radiobutton);
		submenu.add(radiobutton);
		
		radiobutton = new JRadioButtonMenuItem("Robot vs Robot");
		radiobutton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				oponentes = ROB_ROB;
			}
		});
		buttongroup.add(radiobutton);
		submenu.add(radiobutton);
		menu.add(submenu);
		
		/* ------ Elegir Tablero -----*/
		submenu = new JMenu("Elegir tablero");
		buttongroup = new ButtonGroup();
		
		tableroClasico = new JRadioButtonMenuItem("CLASICO");
		if (tipoTablero == TipoTablero.CLASICO) {
			tableroClasico.setSelected(true);
		}
		buttongroup.add(tableroClasico);
		submenu.add(tableroClasico);
		
		tableroOctogonal = new JRadioButtonMenuItem("OCTOGONAL");
		if (tipoTablero == TipoTablero.OCTOGONAL) {
			tableroOctogonal.setSelected(true);
		}
		buttongroup.add(tableroOctogonal);
		submenu.add(tableroOctogonal);
		
		tableroPersonalizado = new JRadioButtonMenuItem("PERSONALIZADO");
		if (tipoTablero == TipoTablero.PERSONALIZADO) {
			tableroPersonalizado.setSelected(true);
		}
		buttongroup.add(tableroPersonalizado);
		submenu.add(tableroPersonalizado);
		
		menu.add(submenu);
		
		/* ------ Dimensiones -----*/
		submenu = new JMenu("Dimensiones");
		buttongroup = new ButtonGroup();
		
		tablero10x10 = new JRadioButtonMenuItem("10 x 10");
		if (largoTablero == 10 && anchoTablero == 10) {
			tablero10x10.setSelected(true);
		}
		buttongroup.add(tablero10x10);
		submenu.add(tablero10x10);
		
		tablero12x12 = new JRadioButtonMenuItem("12 x 12");
		if (largoTablero == 12 && anchoTablero == 12) {
			tablero12x12.setSelected(true);
		}
		buttongroup.add(tablero12x12);
		submenu.add(tablero12x12);
		
		tablero14x14 = new JRadioButtonMenuItem("14 x 14");
		if (largoTablero == 14 && anchoTablero == 14) {
			tablero14x14.setSelected(true);
		}
		buttongroup.add(tablero14x14);
		submenu.add(tablero14x14);
		
		menu.add(submenu);
		
		/*
		 *  AYUDA
		 */
		menu = new JMenu("Ayuda");
		barraMenu.add(menu);
		
		about = new JMenuItem("Acerca de Othello");
		menu.add(about);
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(tablero, "Othello es un juego de estrategia abtracta",
					      "About Othello", JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
		
		constrains.anchor = GridBagConstraints.PAGE_START;
		constrains.fill = GridBagConstraints.HORIZONTAL;
		constrains.gridwidth = 3;
		constrains.gridx = 0;
		constrains.gridy = 1;
		pane.add(barraMenu, constrains);
		constrains.gridwidth = 0;
		
		/* Pintar las casillas */
		/*
		tablero = new JPanel (new GridLayout(largoTablero, anchoTablero));
		for ( int fila = 0; fila < largoTablero; fila++){
			for (int columna = 0; columna < anchoTablero; columna++){
				//if (fila == 2 && columna == 3){
				//	ComponenteImagen casillaMuro = FabricaImagenCasilla.construirCasilla(TipoCasilla.WALL);
				//	tablero.add(casillaMuro);
				//	continue;
				//}
				if(fila == 0 ||columna ==0 || fila == largoTablero-1 || columna == anchoTablero-1){
					ComponenteImagen casillaMuro = FabricaImagenCasilla.construirCasilla(TipoCasilla.WALL);
					tablero.add(casillaMuro);
					casillas.add(casillaMuro);
				}
				else {
					ComponenteImagen casillaVacia = FabricaImagenCasilla.construirCasilla(TipoCasilla.EMPTY);
					tablero.add(casillaVacia);
					casillas.add(casillaVacia);
				}
			}
			
		}
		
		// Pintar puntos de partida 
		definirCasilla(new Point((int)(largoTablero/2)-1, (int)(anchoTablero/2)-1), TipoCasilla.WHITE);
		definirCasilla(new Point((int)(largoTablero/2)-1, (int)(anchoTablero/2)), TipoCasilla.BLACK);
		definirCasilla(new Point((int)(largoTablero/2), (int)(anchoTablero/2)-1), TipoCasilla.BLACK);
		definirCasilla(new Point((int)(largoTablero/2), (int)(anchoTablero/2)), TipoCasilla.WHITE);
		
		//tablero = new InterfazTablero();
		
		 */
		
		tablero = new JPanel (new GridLayout(largoTablero, anchoTablero));
		ComponenteImagen casillaImagen;
		
		for ( int fila = 0; fila < largoTablero; fila++){
			for (int columna = 0; columna < anchoTablero; columna++){
				switch(tableroLogica.obtenerTablero().get(new Point(fila, columna))){
				case EMPTY:
					casillaImagen = FabricaImagenCasilla.construirCasilla(TipoCasilla.EMPTY);
					break;
				case BLACK:
					casillaImagen = FabricaImagenCasilla.construirCasilla(TipoCasilla.BLACK);
					break;
				case WHITE:
					casillaImagen = FabricaImagenCasilla.construirCasilla(TipoCasilla.WHITE);
					break;
				case WALL:
					casillaImagen = FabricaImagenCasilla.construirCasilla(TipoCasilla.WALL);
					break;
				default:
					casillaImagen = FabricaImagenCasilla.construirCasilla(TipoCasilla.EMPTY);
					break;
				}
				tablero.add(casillaImagen);
				casillas.add(casillaImagen);
			}
		}	
		
		constrains.anchor = GridBagConstraints.CENTER;
		constrains.fill = GridBagConstraints.NONE;
		constrains.gridx = 1;
		constrains.gridy = 2;
		pane.add(tablero, constrains);
		
		/* Pintar la barra de estado */
		estadisticasBlanco = new JLabel("Blancas: ");
		estadisticasBlanco.setBorder(BorderFactory.createEtchedBorder());
		estadisticasBlanco.setBackground(Color.WHITE);
		estadisticasBlanco.setFont(estadisticasBlanco.getFont().deriveFont(Font.PLAIN));
		estadisticasBlanco.setHorizontalAlignment(JLabel.LEFT);
		
		mostrarTurno = new JLabel("�El juego ha iniciado!");
		mostrarTurno.setBorder(BorderFactory.createEtchedBorder());
		mostrarTurno.setBackground(Color.WHITE);
		mostrarTurno.setFont(estadisticasBlanco.getFont().deriveFont(Font.PLAIN));
		mostrarTurno.setHorizontalAlignment(JLabel.CENTER);
		
		estadisticasNegro = new JLabel("Negras: ");
		estadisticasNegro.setBorder(BorderFactory.createEtchedBorder());
		estadisticasNegro.setBackground(Color.WHITE);
		estadisticasNegro.setFont(estadisticasBlanco.getFont().deriveFont(Font.PLAIN));
		estadisticasNegro.setHorizontalAlignment(JLabel.RIGHT);
		
		barraEstado = new JPanel(new GridLayout());
		barraEstado.add(estadisticasBlanco);
		barraEstado.add(mostrarTurno);
		barraEstado.add(estadisticasNegro);
		constrains.anchor = GridBagConstraints.PAGE_END;
		constrains.fill = GridBagConstraints.HORIZONTAL;
		constrains.weightx = 1;
		constrains.gridx = 1;
		constrains.gridy = 4;
		pane.add(barraEstado, constrains);

		boton = new JButton("Salir de Edici�n");
		boton.setBorder(BorderFactory.createEtchedBorder());
		boton.setSize(20, 50);
		//boton.setBackground(new Color(0, 60, 0));
		barraBoton = new JPanel(new GridLayout());
		barraBoton.add(boton);
		constrains = new GridBagConstraints();
		constrains.fill = GridBagConstraints.NONE;
		constrains.weightx = 300;
		constrains.weighty = 100;
		constrains.gridx = 1;
		constrains.gridy = 20;
		pane.add(barraBoton, constrains);
		mostrarBarraEstado();
		
	}
	
	/*public void crearBotonSalidaEdicion(){
		GridBagConstraints constrains = new GridBagConstraints();
		boton = new JButton("Salir de Edici�n");
		boton.setBorder(BorderFactory.createEtchedBorder());
		boton.setSize(20, 50);
		//boton.setBackground(new Color(0, 60, 0));
		barraBoton = new JPanel(new GridLayout());
		barraBoton.add(boton);
		constrains.weightx = 300;
		constrains.weighty = 100;
		constrains.gridx = 1;
		constrains.gridy = 20;
		pane.add(barraBoton, constrains);
		mostrarBarraBoton();
	}*/
	
	public void mostrarBarraEstado(){
		barraBoton.setVisible(false);
		barraEstado.setVisible(true);
	}
	
	public void mostrarBarraBoton(){
		barraEstado.setVisible(false);
		barraBoton.setVisible(true);
	}
	

	
	public void definirCasilla(Point point, TipoCasilla tipoCasilla) {
		ComponenteImagen imgcomp = FabricaImagenCasilla.construirCasilla(tipoCasilla);
		int index = Transform.pointToIndex(point);
		casillas.set(index, imgcomp);
		tablero.remove(index);
		tablero.add(imgcomp, index);
		tablero.updateUI();
	}
	
	public void marcarMovidasPosibles(Collection<Point> movidasPosibles, TipoCasilla color){
		for (Point pssblPoint : movidasPosibles) {
			definirCasilla(pssblPoint, color);
			tablero.updateUI();
		}
		tablero.revalidate();
	}
	
	public void desmarcarMovidasPosibles(Collection<Point> movidasPosibles){
		for (Point pssblPoint : movidasPosibles) {
			definirCasilla(pssblPoint, TipoCasilla.EMPTY);	
			tablero.updateUI();
		}
		tablero.revalidate();
	}
	
	public void rellenar(Collection<Point> puntosConRelleno, TipoCasilla color){
		for (Point aRellenar : puntosConRelleno) {
			definirCasilla(aRellenar, color);
			tablero.updateUI();
		}
		tablero.revalidate();
	}
		
	public void actualizarPuntaje(int estadisticasNegro, int estadisticasBlanco) {
		this.estadisticasNegro.setText("Negras: " + estadisticasNegro);
		this.estadisticasBlanco.setText("Blancas: " + estadisticasBlanco);
	}
	
	public void actualizarTurno(String jugador){
		jugador = jugador == "BLACK" ? "Negras" : "Blancas";
		this.mostrarTurno.setText("Le toca a " + jugador);
	}
	
	public void declararEmpate(){
		this.mostrarTurno.setFont(mostrarTurno.getFont().deriveFont(Font.BOLD));
		this.mostrarTurno.setText("Empate !?");
		JOptionPane.showMessageDialog(this, "��Empate!! ", "�Buena Partida!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void declararGanador(String nombreGanador){
		this.mostrarTurno.setFont(mostrarTurno.getFont().deriveFont(Font.BOLD));
		this.mostrarTurno.setText("��" + nombreGanador + " gana!!");
	}
	
	public void notificarTurnoPerdido(Jugador jugador){
		String jug = jugador == Jugador.BLACK ? "Negras" : "Blancas";
		JOptionPane.showMessageDialog(this, "No hay movidas disponibles, " + jug
			    + " pierde su turno", "�Bien Jugado!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void notificarVictoria(Jugador jugador) {
		String ganador = jugador == Jugador.BLACK ? "Negras" : "Blancas";
		JOptionPane.showMessageDialog(this, "��" + ganador + " gana!! ", "�Buena Partida!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public List<ComponenteImagen> obtenerCasillas() {
		return casillas;
	}
	
	public JMenuItem obtenerItemNuevoJuego(){
		return juegoNuevo;
	}
	
	public JMenuItem obtenerItemGuardarJuego(){
		return guardarJuego;
	}
	
	public JMenuItem obtenerItemAbrirJuego(){
		return abrirJuego;
	}
	
	public JRadioButtonMenuItem obtenerTableroClasico(){
		return tableroClasico;
	}
	public JRadioButtonMenuItem obtenerTableroOctogonal(){
		return tableroOctogonal;
	}
	
	public JRadioButtonMenuItem obtenerTableroPersonalizado(){
		return tableroPersonalizado;
	}
	
	public JRadioButtonMenuItem obtenerTablero10x10(){
		return tablero10x10;
	}
	
	public JRadioButtonMenuItem obtenerTablero12x12(){
		return tablero12x12;
	}
	
	public JRadioButtonMenuItem obtenerTablero14x14(){
		return tablero14x14;
	}

	
	
	
	public JButton obtenerBoton(){
		return boton;
	}
	
	public Jugador obtenerJugadorSeleccionado(){
		return humano;
	}
	
	public int obtenerOponentes(){
		return oponentes;
	}
	

	
	/*MODIFICADOS!!*/
	/*private void definirCasilla(Point point, TipoCasilla tipoCasilla) {
		tablero.definirCasilla(point, tipoCasilla);
	}
	
	public void marcarMovidasPosibles(Collection<Point> movidasPosibles, TipoCasilla color){
		tablero.marcarMovidasPosibles(movidasPosibles, color);
	}
	
	public void desmarcarMovidasPosibles(Collection<Point> movidasPosibles){
		tablero.desmarcarMovidasPosibles(movidasPosibles);
	}
	
	public void rellenar(Collection<Point> puntosConRelleno, TipoCasilla color){
		tablero.rellenar(puntosConRelleno, color);

	}*/
}
