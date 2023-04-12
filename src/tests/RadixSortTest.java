package tests;

import org.junit.Assert;
import org.junit.Test;

import radixsort.AristaPesadaParaTests;
import radixsort.RadixSort;


public class RadixSortTest {
	@Test
	public void arregloEsOrdenadoTest() {
		AristaPesadaParaTests[] arregloInput = {
				new AristaPesadaParaTests(180),
				new AristaPesadaParaTests(10),
				new AristaPesadaParaTests(20),
				new AristaPesadaParaTests(1),
				new AristaPesadaParaTests(3),
				new AristaPesadaParaTests(564),
				new AristaPesadaParaTests(7),
				new AristaPesadaParaTests(34),
		};
		AristaPesadaParaTests[] arregloEsperado = {
				new AristaPesadaParaTests(1),
				new AristaPesadaParaTests(3),
				new AristaPesadaParaTests(7),
				new AristaPesadaParaTests(10),
				new AristaPesadaParaTests(20),
				new AristaPesadaParaTests(34),
				new AristaPesadaParaTests(180),
				new AristaPesadaParaTests(564),
		};
		
		(new RadixSort(arregloInput, 564)).ordenar();
		
		Assert.assertArrayEquals(arregloEsperado, arregloInput);
 	}
}
