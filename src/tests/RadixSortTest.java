package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import negocio.RadixSort;

public class RadixSortTest {
	@Test
	public void arregloVacioTest() {
		AristaPesadaParaTests[] arregloInput = {};
		AristaPesadaParaTests[] arregloEsperado = {};
		
		RadixSort.ordenar(arregloInput, 0);
		
		assertArrayEquals(arregloInput, arregloEsperado);
	}
	
	@Test
	public void arregloUnElementoTest() {
		AristaPesadaParaTests[] arregloInput = {new AristaPesadaParaTests(1)};
		AristaPesadaParaTests[] arregloEsperado = {new AristaPesadaParaTests(1)};
		
		RadixSort.ordenar(arregloInput, 1);
		
		assertArrayEquals(arregloInput, arregloEsperado);
	}
	
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
	
	@Test(expected = IllegalArgumentException.class)
	public void cantidadDeDigitosNullTest() {
		RadixSort.cantidadDeDigitos(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void cantidadDeDigitosNegativoTest() {
		RadixSort.cantidadDeDigitos(-1);
	}
	
	@Test
	public void cantidadDeDigitosCeroTest() {
		assertEquals(1, RadixSort.cantidadDeDigitos(0));
	}
	
	@Test
	public void cantidadDeDigitosNumeroUnDigitoTest() {
		assertEquals(1, RadixSort.cantidadDeDigitos(9));
	}
	
	@Test
	public void cantidadDeDigitosNumeroTresDigitosTest() {
		assertEquals(3, RadixSort.cantidadDeDigitos(793));
	}
	
	private AristaPesadaParaTests[] crearArregloDeAristas(int[] pesos) {
		AristaPesadaParaTests[] ret = new AristaPesadaParaTests[pesos.length];

		for (int i = 0; i < pesos.length; i++) {
			ret[i] = new AristaPesadaParaTests(pesos[i]);
		}

		return ret;
	}
}
