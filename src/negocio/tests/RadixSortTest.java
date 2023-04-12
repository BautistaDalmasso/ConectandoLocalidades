package negocio.tests;

import org.junit.Assert;
import org.junit.Test;

import radixsort.RadixSort;


public class RadixSortTest {
	@Test
	public void arregloEsOrdenadoTest() {
		Integer[] arregloInput = {180, 10, 20, 1, 3, 564, 7, 34};
		Integer[] arregloEsperado = {1, 3, 7, 10, 20, 34, 180, 564};
		
		(new RadixSort(arregloInput, 564)).ordenar();
		
		Assert.assertArrayEquals(arregloEsperado, arregloInput);
 	}
}
