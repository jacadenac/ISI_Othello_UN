package logica.ia.algoritmosGeneticos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.GeneticOperator;
import org.jgap.Genotype;
import org.jgap.Population;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.data.DataTreeBuilder;
import org.jgap.data.IDataCreators;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.xml.XMLDocumentBuilder;
import org.jgap.xml.XMLManager;
import org.w3c.dom.Document;

/**
 * @autores: Alejandro Cadena, Jefferson Cortes
 */

public class Estrategia {

	/**
	 *  N�mero total de veces que permitira evolucionar la poblaci�n
	 */
	private static final int MAX_EVOLUCIONES_PERMITIDAS = 3000;
	private static final int RANGO_GEN = 1000;
	private static final File directorio = new File("setup/generations");
	public static int numeroEvolucion=0;
	public static String cadena="";
	private static String datosGeneraciones="";
	public static int numeroCromosomasCreados=1;
	public static Configuration conf = new DefaultConfiguration();

	/**
	 * Calcula utilizando algoritmos gen�ticos la soluci�n al problema
	 * y la la retorna.
	 * @throws InvalidConfigurationException 
	 */
	
	@SuppressWarnings("deprecation")
	public static void calcularMejorEstrategia() throws InvalidConfigurationException{
		// se crea una configuracion con valores predeterminados
		// -------------------------------------------------------------------
			/*Configuration conf = new DefaultConfiguration();*/
		// Se indica en la conficuracion que el elemento m�s apto siempre pase
		// a la pr�xima generacion
		// -------------------------------------------------------------------
		conf.setPreservFittestIndividual(true);	
		// Se crea la funcion de aptitud y se setea en la configuraci�n
		// -------------------------------------------------------------------
		FitnessFunction myFunc = new EstrategiaFuncionAptitud(/*poblacion.getChromosomes()*/);
		conf.setFitnessFunction(myFunc);
		

		// Se define el operador gen�tico: c�mo ser� la reproducci�n
		// -------------------------------------------------------------------
		//List m_geneticOperators;
		//m_geneticOperators = new Vector();
		 
		
		@SuppressWarnings("serial")
		GeneticOperator operadorGeneticoMutacion = new GeneticOperator() {
			@SuppressWarnings({ "unchecked" })
			@Override
			public void operate(Population actual, @SuppressWarnings("rawtypes") List nueva) {
				List<IChromosome> cromosomas = actual.getChromosomes();
				List<IChromosome> temporal = new ArrayList<IChromosome>();
				
				for(IChromosome cromosoma: cromosomas){
					Gene[] genes = new Gene[4];
					for(int j=0 ; j<genes.length ; j++){
						try {
							genes[j] = new IntegerGene(conf, 0, RANGO_GEN);
						} catch (InvalidConfigurationException e) {
							System.out.println("Error al crear los Genes en GeneticOperator");
						}
						
						int valorAleatorio = (int) Math.floor(Math.random()*(10-80+1)+80);  // Valor entre 80 y 10, ambos incluidos.
						int signo = (int)Math.signum((Math.random()*2)-1);
						
						int AleloActual = (Integer)cromosoma.getGenes()[j].getAllele();
						int AleloNuevo;
						if (AleloActual  + (signo*valorAleatorio) < 0 || AleloActual  + (signo*valorAleatorio) > RANGO_GEN) { 
							AleloNuevo = AleloActual +(-1)*(signo*valorAleatorio);
						} else {
							AleloNuevo = AleloActual +(-1)*(signo*valorAleatorio);
						}
						genes[j].setAllele(AleloNuevo);
					}		
					try {
						IChromosome cromosomaNuevo = new Chromosome(conf, genes);
						temporal.add(cromosomaNuevo);
						//System.out.println("size="+temporal.size());
					} catch (InvalidConfigurationException e) {
						System.out.println("Error al a�adir a Cromosoma temporal en GeneticOperator");
					}
				}
				int cantPoblacionAMutar = (int)(conf.getPopulationSize()*0.4);
				for(int i=0; i < cantPoblacionAMutar; i++){
					if(temporal !=null){
						nueva.add(temporal.get(i));
					}
				}
			}
		};
		//A�ade el Operador Gen�tico de mutaci�n creado anteriormente
		conf.addGeneticOperator(operadorGeneticoMutacion);
		//conf.setNaturalSelector(null);
		/*operadorGenetico.operate(poblacionActual, cromosomasSeleccionados);*/
		
		// Ahora se indica c�mo ser�n los cromosomas: en este caso tendr�n 8
		// genes (uno para cada coeficiente de la Evaluaci�nEstrategica)
		// con un valor entero que representa el peso de la variable que 
		// acompa�ar�.
		// Se debe crear un cromosoma de ejemplo y cargarlo en la configuraci�n.
		// Cada gen tendr� un valor m�ximo y m�nimo que debe setearse.
		// -------------------------------------------------------------------
		Gene[] sampleGenes = new Gene[4];
		sampleGenes[0] = new IntegerGene(conf, 0, RANGO_GEN); // Importancia puntos
		sampleGenes[1] = new IntegerGene(conf, 0, RANGO_GEN); // Importancia esquinas
		sampleGenes[2] = new IntegerGene(conf, 0, RANGO_GEN); // Importancia libertades rival
		sampleGenes[3] = new IntegerGene(conf, 0, RANGO_GEN); // Importancia casillas centrales
		
		IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
		conf.setSampleChromosome(sampleChromosome);
		// Por ultimo se debe indicar el tama�o de la poblacion en la
		// configuraci�n
		// -------------------------------------------------------------------
		conf.setPopulationSize(cantidadCompetidores());
		//conf.setPopulationSize(200);
		
		// El framework permite obtener la poblaci�n inicial de archivos xml
		// pero para este caso se crea una poblacion aleatoria, para ello se utiliza
		// el metodo randomInitialGenotype que devuelve la poblaci�n random creada.
		Genotype poblacion = obtenerPoblacion(conf);
		EstrategiaFuncionAptitud.setEstrategiasCompetidoras(poblacion.getChromosomes());
		//poblacion = Genotype.randomInitialGenotype(conf);		
		
		// La poblaci�n debe evolucionar para obtener resultados m�s aptos
		// -------------------------------------------------------------------
		long tiempoComienzo = System.currentTimeMillis();
		
		for (int i = 0; i < MAX_EVOLUCIONES_PERMITIDAS; i++){
			datosGeneraciones += "\r\n------------------ GENERACI�N "+numeroEvolucion+" ----------------- \r\n";
			System.out.println("\n------------------GENERACI�N "+numeroEvolucion+"----------------- \n");
			IChromosome[] cromosomas = poblacion.getPopulation().toChromosomes();
			datosGeneraciones += "\r\n"+poblacionToString(cromosomas)+"\r\n";
			
			//Obtiene los dos individuos con mejor aptitud
			@SuppressWarnings("unchecked")
			List<IChromosome> mejores2 = poblacion.getFittestChromosomes(2);
			
			int[] mejor1 = obtenerValoresCromosoma((IChromosome) mejores2.get(0));
			int[] mejor2 = obtenerValoresCromosoma((IChromosome) mejores2.get(1));
			
			String dosMejoresCromosomas = "";
			dosMejoresCromosomas +=  mejor1[0]+","+mejor1[1]+","+mejor1[2]+","+mejor1[3]+"\r\n"+
									 mejor2[0]+","+mejor2[1]+","+mejor2[2]+","+mejor2[3]+"\r\n";
			guardarDatosEvolucion(dosMejoresCromosomas,"dosMejores.txt");
			
			//Evoluciona la poblaci�n
			poblacion.evolve();
			
			//Actualiza las nuevas estrategias competidoras en la funci�n de aptitud
			EstrategiaFuncionAptitud.setEstrategiasCompetidoras(poblacion.getChromosomes());
			
			datosGeneraciones += "Mejores:\r\n"+ dosMejoresCromosomas+"\r\n\r\n";
			guardarDatosEvolucion(datosGeneraciones,"evolucionGeneraciones.txt");
			numeroEvolucion++;
		}
		long tiempoFin = System.currentTimeMillis();
		cadena += "Tiempo total de evoluci�n: "+ (tiempoFin - tiempoComienzo) + " ms";
		// Una vez que la poblacion evoluciono es necesario obtener el cromosoma
		// m�s apto para mostrarlo como soluci�n al problema planteado para ello
		// se utiliza el m�todo getFittestChromosome
		IChromosome cromosomaMasApto = poblacion.getFittestChromosome();
		cadena+="\n"+"El cromosoma m�s apto encontrado tiene un valor de aptitud de: " + cromosomaMasApto.getFitnessValue();
		cadena+="\n"+"Y esta formado por la siguiente distribuci�n de pesos: ";
		cadena+="\n"+"\t"+ EstrategiaFuncionAptitud.getPesoPuntosGen(cromosomaMasApto) + " Importancia de centrarse en obtener m�s puntos que el rival";
		cadena+="\n"+"\t"+ EstrategiaFuncionAptitud.getPesoEsquinasGen(cromosomaMasApto) + " Importancia de obtener las esquinas";
		cadena+="\n"+"\t"+ EstrategiaFuncionAptitud.getPesoLibertadesRivalGen(cromosomaMasApto) + "Importancia de dejar sin jugadas al rival";
		cadena+="\n"+"\t"+ EstrategiaFuncionAptitud.getPesoCasillasCentralesGen(cromosomaMasApto) + "Importancia de obtener las casillas centrales";
		cadena+="\n"+"Para obtener un peso total de+"+RANGO_GEN; 
		
	}
	
	// -------------------------------------------------------------------
	// Este metodo permite guardar en un xml la �ltima poblaci�n calculada
	// -------------------------------------------------------------------
	
	public static void guardarPoblacion(Genotype Poblacion) throws Exception {
		DataTreeBuilder builder = DataTreeBuilder.getInstance();
		IDataCreators doc2 = builder.representGenotypeAsDocument(Poblacion);
		// Create XML document from generated tree
		XMLDocumentBuilder docbuilder = new XMLDocumentBuilder();
		Document xmlDoc = (Document) docbuilder.buildDocument(doc2);
		XMLManager.writeFile(xmlDoc, new File("PoblacionEstrategia.xml"));
		
	}
	
	// -------------------------------------------------------------------
	// Este metodo permite obtiene la poblaci�n a partir de un archivo
	// -------------------------------------------------------------------
	
	public static Genotype obtenerPoblacion(Configuration conf) throws InvalidConfigurationException{
		int[][] estrategiasCompetidoras = leerPoblacion();//obtenerCompetidores();
		Chromosome[] chromosomas= new Chromosome[cantidadCompetidores()];
		for(int i=0 ; i<chromosomas.length ; i++){
			Gene[] genes = new Gene[4];
			for(int j=0 ; j<genes.length ; j++){
				genes[j] = new IntegerGene(conf, 0, RANGO_GEN);
				genes[j].setAllele((Integer)(estrategiasCompetidoras[i][j]));
			}		
			chromosomas[i] = new Chromosome(conf, genes);	
			//int[] cromosoma = obtenerValoresCromosoma(chromosomas[i]);
			/**///System.out.println(cromosoma[0]+","+cromosoma[1]+","+cromosoma[2]+","+cromosoma[3]);
		}
		Population population = new Population(conf,chromosomas);
		return new Genotype(conf,population);
	}
	
	public static int cantidadCompetidores(){
		return leerPoblacion().length;
	}
	
	private static int[] obtenerValoresCromosoma(IChromosome cromosoma) {
		int[] valores = new int[4];
		for (int i=0; i<4; i++){
			valores[i]=getPesoDeGen(cromosoma, i);
		}
		return valores;
	}
	
	private static int getPesoDeGen(IChromosome cromosoma, int i) {
		Integer peso = (Integer) cromosoma.getGene(i).getAllele();
		return peso;
	}
	
	private static int[][] leerPoblacion() {
		    int[][] poblacion = new int[12][5];
			JFileChooser fileChooser = new JFileChooser();
			String nombreFichero = "poblacionInicial.txt";
			File fichero = new File(directorio.getPath(),nombreFichero);
			fileChooser.setCurrentDirectory(fichero);		
			try {
				BufferedReader reader = new BufferedReader(new FileReader(fichero));
				String linea = reader.readLine();
				int i = 0;
				while (linea != null) {
					String[] dimension = linea.split ("-");
					poblacion[i][4] = Integer.parseInt(dimension[0]);
					dimension = dimension[1].split(",");
					for (int j=0 ; j<dimension.length ; j++) {				
						poblacion[i][j]=Integer.parseInt(dimension[j]);
					}
					linea = reader.readLine();
					i++;
				}
				reader.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return poblacion;
	}
	
	public static String poblacionToString(IChromosome[] cromosomas){
		String poblacion = "";
		String nombreFichero = "ultimaGeneracion.txt";
		if (!directorio.exists()) {
            directorio.mkdirs();
        }
		try {
			FileWriter fichero = new FileWriter(directorio.getPath()+"/"+nombreFichero, false);
			PrintWriter writer;
			writer = new PrintWriter(fichero);
			for (int i = 0; i < cromosomas.length; i++) {
				int[] cromosoma = obtenerValoresCromosoma(cromosomas[i]);
				poblacion += numeroCromosomasCreados+"-"+cromosoma[0]+","+cromosoma[1]+","+cromosoma[2]+","+cromosoma[3]+"\r\n";
				numeroCromosomasCreados++;
			}
			writer.println(poblacion);
			writer.close();
		} catch (IOException e) {
			System.out.println("No se ha podido escribir en el archivo torneo.txt");
		}
		return poblacion;
	}
	
	public static void guardarDatosEvolucion(String datos,String nombreArchivo){
		String nombreFichero = nombreArchivo;
		if (!directorio.exists()) {
            directorio.mkdirs();
        }
		try {
			FileWriter fichero = new FileWriter(directorio.getPath()+"/"+nombreFichero, false);
			PrintWriter writer;
			writer = new PrintWriter(fichero);
			writer.println(datos);
			writer.close();
		} catch (IOException e) {
			System.out.println("No se ha podido escribir en el archivo torneo.txt");
		}
	}
	
}
