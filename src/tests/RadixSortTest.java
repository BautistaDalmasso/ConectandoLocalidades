package tests;

import org.junit.Assert;
import org.junit.Test;

import radixsort.AristaPesadaParaTests;
import radixsort.RadixSort;


public class RadixSortTest {
	@Test
	public void arregloOrdenadoNoCambiaTest() {
		AristaPesadaParaTests[] arregloInput = {
				new AristaPesadaParaTests(9),
				new AristaPesadaParaTests(99),
				new AristaPesadaParaTests(999),
				new AristaPesadaParaTests(1000),
		};
		AristaPesadaParaTests[] arregloEsperado = {
				new AristaPesadaParaTests(9),
				new AristaPesadaParaTests(99),
				new AristaPesadaParaTests(999),
				new AristaPesadaParaTests(1000),
		};
		
		RadixSort.ordenar(arregloInput, 1000);
		
		Assert.assertArrayEquals(arregloInput, arregloEsperado);
	}
	
	@Test
	public void arregloInvertidoEsOrdenadoTest() {
		AristaPesadaParaTests[] arregloInput = {
				new AristaPesadaParaTests(1000),
				new AristaPesadaParaTests(999),
				new AristaPesadaParaTests(99),
				new AristaPesadaParaTests(9),
		};
		AristaPesadaParaTests[] arregloEsperado = {
				new AristaPesadaParaTests(9),
				new AristaPesadaParaTests(99),
				new AristaPesadaParaTests(999),
				new AristaPesadaParaTests(1000),
		};
		
		RadixSort.ordenar(arregloInput, 1000);
		
		Assert.assertArrayEquals(arregloInput, arregloEsperado);
	}
	
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
		
		RadixSort.ordenar(arregloInput, 564);
		
		Assert.assertArrayEquals(arregloEsperado, arregloInput);
 	}
}
