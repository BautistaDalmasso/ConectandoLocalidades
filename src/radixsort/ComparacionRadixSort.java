package radixsort;

import java.util.Arrays;
import java.util.Random;

import negocio.PosicionGeografica;

public class ComparacionRadixSort {
	private static final PosicionGeografica EXTREMO_NORTE_ARGENTINA = new PosicionGeografica(21 + 46 / 60,
			53 + 38 / 60);
	private static final PosicionGeografica EXTREMO_SUR_ARGENTINA = new PosicionGeografica(55 + 03 / 60, 66 + 31 / 60);
	private static final int LINEA_MAS_LARGA_ARGENTINA = (int) Math
			.round(PosicionGeografica.distanciaEnKilometros(EXTREMO_NORTE_ARGENTINA, EXTREMO_SUR_ARGENTINA));

	private static final int CANTIDAD_LOCALIDADES_ARGENTINAS = 20563;

	public static void main(String[] args) {
		compararConArreglosChicosValoresChicos();
		compararConArreglosChicosValoresGrandes();
		compararConArreglosGrandesValoresChicos();
		compararConArreglosGrandesValoresGrandes();
	}

	public static void compararConArreglosChicosValoresChicos() {
		int victoriasArraysSort = 0;
		int victoriasRadixSort = 0;

		ResultadoCreacionArreglo arregloChicoValoresChicos;

		for (int i = 0; i < 1000; i++) {
			// Arrays sort.
			Long tiempoInicioArraysSort = System.nanoTime();

			arregloChicoValoresChicos = new ResultadoCreacionArreglo(10, 999);
			Arrays.sort(arregloChicoValoresChicos.arreglo);

			Long tiempoFinalArraysSort = System.nanoTime() - tiempoInicioArraysSort;

			// Radix sort.
			Long tiempoInicioRadixSort = System.nanoTime();

			arregloChicoValoresChicos = new ResultadoCreacionArreglo(10, 999);
			RadixSort.ordenar(arregloChicoValoresChicos.arreglo, arregloChicoValoresChicos.valorMaximo);

			Long tiempoFinalRadixSort = System.nanoTime() - tiempoInicioRadixSort;

			if (tiempoFinalRadixSort < tiempoFinalArraysSort) {
				victoriasRadixSort++;
			} else {
				victoriasArraysSort++;
			}
		}

		String ganador = victoriasArraysSort > victoriasRadixSort ? "Arrays.Sort" : "RadixSort";
		System.out.println("El ganador para arreglos CHICOS con valores CHICOS es: " + ganador);
	}

	public static void compararConArreglosChicosValoresGrandes() {
		int victoriasArraysSort = 0;
		int victoriasRadixSort = 0;

		ResultadoCreacionArreglo arregloChicoValoresGrandes;

		for (int i = 0; i < 1000; i++) {
			// Arrays sort.
			Long tiempoInicioArraysSort = System.nanoTime();

			arregloChicoValoresGrandes = new ResultadoCreacionArreglo(10, LINEA_MAS_LARGA_ARGENTINA);
			Arrays.sort(arregloChicoValoresGrandes.arreglo);

			Long tiempoFinalArraysSort = System.nanoTime() - tiempoInicioArraysSort;

			// Radix sort.
			Long tiempoInicioRadixSort = System.nanoTime();

			arregloChicoValoresGrandes = new ResultadoCreacionArreglo(10, LINEA_MAS_LARGA_ARGENTINA);
			RadixSort.ordenar(arregloChicoValoresGrandes.arreglo, arregloChicoValoresGrandes.valorMaximo);
			
			Long tiempoFinalRadixSort = System.nanoTime() - tiempoInicioRadixSort;

			if (tiempoFinalRadixSort < tiempoFinalArraysSort) {
				victoriasRadixSort++;
			} else {
				victoriasArraysSort++;
			}
		}

		String ganador = victoriasArraysSort > victoriasRadixSort ? "Arrays.Sort" : "RadixSort";
		System.out.println("El ganador para arreglos CHICOS con valores GRANDES es: " + ganador);
	}

	public static void compararConArreglosGrandesValoresChicos() {
		int victoriasArraysSort = 0;
		int victoriasRadixSort = 0;

		ResultadoCreacionArreglo arregloGrandeValoresChicos;

		for (int i = 0; i < 1000; i++) {
			// Arrays sort.
			Long tiempoInicioArraysSort = System.nanoTime();

			arregloGrandeValoresChicos = new ResultadoCreacionArreglo(CANTIDAD_LOCALIDADES_ARGENTINAS, 999);
			Arrays.sort(arregloGrandeValoresChicos.arreglo);

			Long tiempoFinalArraysSort = System.nanoTime() - tiempoInicioArraysSort;

			// Radix sort.
			Long tiempoInicioRadixSort = System.nanoTime();

			arregloGrandeValoresChicos = new ResultadoCreacionArreglo(CANTIDAD_LOCALIDADES_ARGENTINAS, 999);
			RadixSort.ordenar(arregloGrandeValoresChicos.arreglo, arregloGrandeValoresChicos.valorMaximo);

			Long tiempoFinalRadixSort = System.nanoTime() - tiempoInicioRadixSort;

			if (tiempoFinalRadixSort < tiempoFinalArraysSort) {
				victoriasRadixSort++;
			} else {
				victoriasArraysSort++;
			}
		}

		String ganador = victoriasArraysSort > victoriasRadixSort ? "Arrays.Sort" : "RadixSort";
		System.out.println("El ganador para arreglos GRANDES con valores CHICOS es: " + ganador);
	}

	public static void compararConArreglosGrandesValoresGrandes() {
		int victoriasArraysSort = 0;
		int victoriasRadixSort = 0;

		ResultadoCreacionArreglo arregloGrandeValoresGrandes;

		for (int i = 0; i < 1000; i++) {
			// Arrays sort.
			Long tiempoInicioArraysSort = System.nanoTime();

			arregloGrandeValoresGrandes = new ResultadoCreacionArreglo(CANTIDAD_LOCALIDADES_ARGENTINAS,
					LINEA_MAS_LARGA_ARGENTINA);
			Arrays.sort(arregloGrandeValoresGrandes.arreglo);

			Long tiempoFinalArraysSort = System.nanoTime() - tiempoInicioArraysSort;

			// Radix sort.
			Long tiempoInicioRadixSort = System.nanoTime();

			arregloGrandeValoresGrandes = new ResultadoCreacionArreglo(CANTIDAD_LOCALIDADES_ARGENTINAS,
					LINEA_MAS_LARGA_ARGENTINA);
			RadixSort.ordenar(arregloGrandeValoresGrandes.arreglo, arregloGrandeValoresGrandes.valorMaximo);

			Long tiempoFinalRadixSort = System.nanoTime() - tiempoInicioRadixSort;

			if (tiempoFinalRadixSort < tiempoFinalArraysSort) {
				victoriasRadixSort++;
			} else {
				victoriasArraysSort++;
			}
		}

		String ganador = victoriasArraysSort > victoriasRadixSort ? "Arrays.Sort" : "RadixSort";
		System.out.println("El ganador para arreglos GRANDES con valores GRANDES es: " + ganador);
	}
}

class ResultadoCreacionArreglo {
	private static Random r = new Random();

	public int valorMaximo;
	public AristaPesadaParaTests[] arreglo;

	public ResultadoCreacionArreglo(int tamanioArreglo, int valorMaximoPosible) {

		arreglo = new AristaPesadaParaTests[tamanioArreglo];

		for (int i = 0; i < arreglo.length; i++) {
			Integer valor = r.nextInt(0, valorMaximoPosible);
			
			arreglo[i] = new AristaPesadaParaTests(valor);

			valorMaximo = valorMaximo > valor ? valorMaximo : valor;
		}
	}
}
