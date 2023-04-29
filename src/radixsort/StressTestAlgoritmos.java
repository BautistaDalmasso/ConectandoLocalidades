package radixsort;

import java.nio.charset.Charset;
import java.util.Random;

import negocio.GrafoCompletoLocalidades;
import negocio.Localidad;

public class StressTestAlgoritmos {
	// La pagina de provincias de nuestro país incluye a CABA en su lista:
	// https://www.argentina.gob.ar/pais/provincias
	private static final String[] PROVINCIAS = {"Buenos Aires","Ciudad Autónoma de Buenos Aires","Catamarca","Chaco","Chubut","Córdoba","Corrientes","Entre Ríos","Formosa","Jujuy","La Pampa","La Rioja","Mendoza","Misiones","Neuquén","Río Negro","Salta","San Juan","San Luis","Santa Cruz","Santa Fe","Santiago del Estero","Tierra del Fuego, Antártida e Islas del Atlántico Sur","Tucumán"};
	
	private static final double LATITUD_EXTREMO_NORTE_ARGENTINA = 21.7666666667;
	private static final double LATITUD_EXTREMO_SUR_ARGENTINA = 55.05;
	private static final double LONGITUD_EXTREMO_ESTE_ARGENTINA = 53.6333333333;
	private static final double LONGITUD_EXTREMO_OESTE_ARGENTINA = 73.5666666667;

	private static Random r = new Random(0);
	
	public static void main(String[] args) {
		GrafoCompletoLocalidades grafo;
		
		StringBuilder s = new StringBuilder();
		for (int n = 0; n < 1000; n += 10) {
			grafo = generarGrafoLocalidades(n);
			
			double arraysSort = tiempoArraysSort(grafo);
			double radixSort = tiempoRadixSort(grafo);
			
			String ganador = radixSort < arraysSort ? "Radix Sort" : "Arrays Sort";
			
			s.append("n = " + n + ". El ganador es: " + ganador + "\n");
			s.append("(" + arraysSort / 1000 + " vs. " 
						+ radixSort / 1000
					+ ")\n");
		}

		System.out.println(s);
		
		for (int n = 5000; n < 10000; n += 10) {
			grafo = generarGrafoLocalidades(n);
			
			double arraysSort = tiempoArraysSort(grafo);
			double radixSort = tiempoRadixSort(grafo);
			
			String ganador = radixSort < arraysSort ? "Radix Sort" : "Arrays Sort";
			
			System.out.print("n = " + n + ". El ganador es: " + ganador + "\n");
			System.out.print("(" + arraysSort / 1000 + " vs. " 
						+ radixSort / 1000
					+ ")\n");
		}
	}
	
	private static double tiempoArraysSort(GrafoCompletoLocalidades g) {
		g.setAlgoritmoDeOrdenamiento(GrafoCompletoLocalidades.AlgoritmoDeOrdenamiento.ARRAYS_SORT);
		double tiempoInicio = System.currentTimeMillis();

		g.construirArbolGeneradorMinimo();
		
		return System.currentTimeMillis() - tiempoInicio;
	}
	
	private static double tiempoRadixSort(GrafoCompletoLocalidades g) {
		g.setAlgoritmoDeOrdenamiento(GrafoCompletoLocalidades.AlgoritmoDeOrdenamiento.RADIX_SORT);
		
		double tiempoInicio = System.currentTimeMillis();

		g.construirArbolGeneradorMinimo();
		
		return System.currentTimeMillis() - tiempoInicio;
	}
	
	private static GrafoCompletoLocalidades generarGrafoLocalidades(int tamanio) {
		GrafoCompletoLocalidades ret = new GrafoCompletoLocalidades();
		
		for (int i = 0; i < tamanio; i++) {
			try {
				ret.agregarLocalidad(generarLocalidadAleatoria());
			} catch (IllegalArgumentException e) {
				i--;
			}
		}
		
		return ret;
	}
	
	private static Localidad generarLocalidadAleatoria() {
		return new Localidad(stringAleatorio(), provinciaAleatoria(), latitudAleatoria(), longitudAleatoria());
	}
	
	private static String stringAleatorio() {
		byte[] arregloCaracteres = new byte[8]; 
	    r.nextBytes(arregloCaracteres);
	    return new String(arregloCaracteres, Charset.forName("UTF-8"));
	}
	
	private static String provinciaAleatoria() {
		return PROVINCIAS[r.nextInt(0, PROVINCIAS.length)];
	}
	
	private static double latitudAleatoria() {
		return r.nextDouble(LATITUD_EXTREMO_NORTE_ARGENTINA, LATITUD_EXTREMO_SUR_ARGENTINA);
	}
	
	private static double longitudAleatoria() {
		return r.nextDouble(LONGITUD_EXTREMO_ESTE_ARGENTINA, LONGITUD_EXTREMO_OESTE_ARGENTINA);
	}
}
