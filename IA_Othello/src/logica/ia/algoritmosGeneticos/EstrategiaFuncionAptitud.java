package logica.ia.algoritmosGeneticos;

import logica.Torneo;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import core.Tablero.TipoTablero;

public class EstrategiaFuncionAptitud extends FitnessFunction{
	private static final long serialVersionUID = 1L;
	// Valor que debe resultar de la suma de todos los genes
	//private static final int SUMA_PESO_TOTAL = 1000;
	private static IChromosome[] estrategiasCompetidoras;
	
	public EstrategiaFuncionAptitud(IChromosome[] poblacion) {
		estrategiasCompetidoras = poblacion;
	}

	public EstrategiaFuncionAptitud() {
		estrategiasCompetidoras = null;
	}

	/**
	 * El m�todo evaluate es el metodo que se debe sobrecargar para que
	 * devuelva el valor de aptitud asociado al cromosoma que se recibe por
	 * par�metro.
	 * 
	 * @param cromosoma
	 * El cromosoma a evaluar
	 * 
	 * @return El valor de aptitud de ese cromosoma
	 * @author Alejandro
	 */
	@Override
	protected double evaluate(IChromosome cromosoma) {
		double fitness=0;
		int[] estrategia1 = obtenerValoresCromosoma(cromosoma);
		//Verifica que el peso total sume 1000
		/*
		if( (estrategia[0]+estrategia[1]+estrategia[2]+estrategia[3]) != SUMA_PESO_TOTAL){
			System.out.println(estrategia[0]+estrategia[1]+estrategia[2]+estrategia[3]);
			throw new IllegalArgumentException("La suma debe ser de 1000");
		}*/
		
		if (estrategiasCompetidoras == null) {
			System.out.println("No hay competidores!");
		} else {
			int[][] competidores = new int[estrategiasCompetidoras.length][5]; //4 es la id
			for(int i=0;i<estrategiasCompetidoras.length;i++){
				competidores[i][4]=i;
				for(int j=0;j<4;j++){
					competidores[i][j]=getPesoDeGen(estrategiasCompetidoras[i], j);
				}
			}
			
			Torneo torneo = new Torneo(estrategia1, competidores, TipoTablero.OCTOGONAL,10);
			
			/**///System.out.println("\n"+"/*-----------TORNEO "+Torneo.numeroTablerosCreados+"----------*"+"\n");
			/**///System.out.print("Estrategia 1 obtuvo " + torneo.getCantidadVictorias() 
				//	+" de un total de " + competidores.length +" partidas.\n\n");
			
			fitness = (torneo.getCantidadVictorias());
			if(torneo.getCantidadVictorias() == competidores.length){
				fitness += 10;
			}
		}
		
		return fitness;
	}

	private int[] obtenerValoresCromosoma(IChromosome cromosoma) {
		int[] valores = new int[4];
		for (int i=0; i<4; i++){
			valores[i]=getPesoDeGen(cromosoma, i);
		}
		return valores;
	}

	public static int getPesoPuntosGen(IChromosome cromosoma) {
		return getPesoDeGen(cromosoma, 0);
	}

	public static int getPesoEsquinasGen(IChromosome cromosoma) {
		return getPesoDeGen(cromosoma, 1);
	}

	public static int getPesoLibertadesRivalGen(IChromosome cromosoma) {
		return getPesoDeGen(cromosoma, 2);
	}

	public static int getPesoCasillasCentralesGen(IChromosome cromosoma) {
		return getPesoDeGen(cromosoma, 3);
	}

	private static int getPesoDeGen(IChromosome cromosoma, int i) {
		Integer peso = (Integer) cromosoma.getGene(i).getAllele();
		return peso;
	}

	public static IChromosome[] getEstrategiasCompetidoras() {
		return estrategiasCompetidoras;
	}

	public static void setEstrategiasCompetidoras(IChromosome[] estrategiasCompetidoras) {
		EstrategiaFuncionAptitud.estrategiasCompetidoras = estrategiasCompetidoras;
	}

	
	
}