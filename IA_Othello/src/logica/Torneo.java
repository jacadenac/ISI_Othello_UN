package logica;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import core.Tablero.TipoTablero;

public class Torneo {

	private int cantidadVictorias;
	public static int numeroTablerosCreados=0;
	public String datosTorneo;
	private static final File directorio = new File("setup/tournament");
	
	public Torneo(int[] estrategia, int[][] estrategiasCompetidoras, TipoTablero tipoTablero, int tamTablero) {
		super();
		datosTorneo="";
		numeroTablerosCreados++;
		cantidadVictorias=0;
		iniciarTorneo(estrategia, estrategiasCompetidoras, tipoTablero, tamTablero);
	}

	private void iniciarTorneo(int[] estrategia1, int[][] estrategiasCompetidoras, TipoTablero tipoTablero, int tamTablero) {
		datosTorneo += "\r\n------------------TORNEO "+numeroTablerosCreados+"----------------- \r\n";
		datosTorneo += "\r\nTotal partidas jugadas: "+estrategiasCompetidoras.length+"\r\n\r\n";
		JuegoTorneo juego;
		for ( int i=0 ; i<estrategiasCompetidoras.length; i++){
			datosTorneo += "*Juego número "+(i+1)+":\r\n";
			int[] estrategia2 = new int[4];
			for( int j=0 ; j<estrategiasCompetidoras[i].length-1 ; j++){
				estrategia2[j] = estrategiasCompetidoras[i][j];
			}
			
			datosTorneo += "     Estrategia 1 = [ ";
			for(int j=0;j<4;j++){
				datosTorneo += ""+estrategia1[j];
				if (j<3) datosTorneo += ", ";
				else datosTorneo += " ]\r\n"; 
			}
			datosTorneo += "     Estrategia 2 = [ ";
			for(int j=0;j<4;j++){
				datosTorneo += ""+estrategia2[j];
				if (j<3) datosTorneo += ", ";
				else datosTorneo += " ]\r\n"; 
			}
			
			//alterna el color del jugador con estrategia1 de acuerdo al número del torneo
			if (numeroTablerosCreados%2 == 0) {
				juego = new JuegoTorneo(estrategia1, estrategia2, tipoTablero, tamTablero);
			} else {
				juego = new JuegoTorneo(estrategia2, estrategia1, tipoTablero, tamTablero);
			}
			juego.start();
			if(estrategia1 == juego.getGanador()){
				datosTorneo += "     Ganó estrategia 1\r\n";
				//System.out.println("Ganó estrategia 1");
				cantidadVictorias++;
			}else if(estrategia2 == juego.getGanador()){
				datosTorneo += "     Ganó estrategia 2\r\n";
				//System.out.println("Ganó estrategia 2");
			}else if(juego.getGanador() == null){
				datosTorneo += "No Ganó ninguan estrategia\r\n";
				//System.out.println("No Ganó ninguan estrategia");
			}
		}
		datosTorneo += "\r\n     Estrategia 1 obtuvo " + getCantidadVictorias() + " victorias de " 
					   + estrategiasCompetidoras.length + ".\r\n\r\n";
		guardarDatosTorneo(datosTorneo);
	}
	
	public int getCantidadVictorias() {
		return cantidadVictorias;
	}
	
	public void guardarDatosTorneo(String datos){
		String nombreFichero = "Torneos.txt";
		if (!directorio.exists()) {
            directorio.mkdirs();
        }
		try {
			FileWriter fichero = new FileWriter(directorio.getPath()+"/"+nombreFichero, true);
			PrintWriter writer;
			writer = new PrintWriter(fichero);
			writer.println(datos);
			writer.close();
		} catch (IOException e) {
			System.out.println("No se ha podido escribir en el archivo torneo.txt");
		}
	}
	
	public String getDatosTorneo(){
		return datosTorneo;
	}

}
