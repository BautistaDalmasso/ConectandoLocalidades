package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import radixsort.AristaPesadaParaTests;
import radixsort.RadixSort;

public class RadixSortTest {
	@Test
	public void arregloOrdenadoNoCambiaTest() {
		AristaPesadaParaTests[] arregloInput = crearArregloDeAristas(new int[] { 9, 99, 999, 1000 });

		AristaPesadaParaTests[] arregloEsperado = crearArregloDeAristas(new int[] { 9, 99, 999, 1000 });

		RadixSort.ordenar(arregloInput, 1000);

		assertArrayEquals(arregloInput, arregloEsperado);
	}

	@Test
	public void arregloInvertidoEsOrdenadoTest() {
		AristaPesadaParaTests[] arregloInput = crearArregloDeAristas(new int[] { 1000, 999, 99, 9 });

		AristaPesadaParaTests[] arregloEsperado = crearArregloDeAristas(new int[] { 9, 99, 999, 1000 });

		RadixSort.ordenar(arregloInput, 1000);

		assertArrayEquals(arregloInput, arregloEsperado);
	}

	@Test
	public void arregloEsOrdenadoTest() {
		AristaPesadaParaTests[] arregloInput = crearArregloDeAristas(new int[] { 180, 10, 20, 1, 3, 564, 7, 109, 34 });

		AristaPesadaParaTests[] arregloEsperado = crearArregloDeAristas(
				new int[] { 1, 3, 7, 10, 20, 34, 109, 180, 564 });

		RadixSort.ordenar(arregloInput, 564);

		assertArrayEquals(arregloEsperado, arregloInput);
	}

	private AristaPesadaParaTests[] crearArregloDeAristas(int[] pesos) {
		AristaPesadaParaTests[] ret = new AristaPesadaParaTests[pesos.length];

		for (int i = 0; i < pesos.length; i++) {
			ret[i] = new AristaPesadaParaTests(pesos[i]);
		}

		return ret;
	}
}
