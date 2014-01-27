package othello;

import org.jgap.InvalidConfigurationException;

import logica.ia.algoritmosGeneticos.Estrategia;

public class Entrenamiento extends Thread{
	
	public void run() {
		entrenar();
	    System.out.println("Terminó de entrenar:"+this.getName());
	}
	
	private void entrenar(){
		/*int[] estrategia1 = {0,1000,0,0};
		int[] estrategia2 = {1000,0,0,0};
		int[] estrategia1 = {400,300,100,200};*/
		
		/*int[] estrategia1 = {0,1000,0,0};
		int[][] estrategiasCompetidoras = {{250,500,50 ,200, 0 },
										   {100,100,700,100, 1 },
										   {200,100,600,100, 2 },
										   {100,50 ,50 ,800, 3 },
										   {300,100,300,300, 4 },
										   {100,400,100,400, 5 },
										   {1000,0 ,0  ,0  , 6 },
										   {50 ,900,10 ,40 , 7 },
										   {0  ,100,400,500, 8 },
										   {700,0  ,100,200, 9 },
										   {250,250,250,250, 10}};
		Torneo torneo = new Torneo(estrategia1, estrategiasCompetidoras, TipoTablero.OCTOGONAL,10);
		System.out.println("");
		System.out.println("Estrategia 1 obtuvo " + torneo.getCantidadVictorias() 
				+" de un toral de " + estrategiasCompetidoras.length +" partidas.");*/
		//EventQueue.invokeLater(torneo);
		
		try {
			Estrategia.calcularMejorEstrategia();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
