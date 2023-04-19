package tests;

import org.junit.Assert;
import org.junit.Test;

import unionfind.UnionFind;

public class UnionFindTest {
	@Test(expected = IllegalArgumentException.class)
	public void inicializarUnionFindConValorInvalidoTest() {
		new UnionFind(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void raizDeVerticeNegativoTest() {
		UnionFind uf = new UnionFind(1);

		uf.find(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void raizDeVerticeInexistenteTest() {
		UnionFind uf = new UnionFind(1);

		uf.find(1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void findPrimerArgumentoInvalidoTest() {
		UnionFind uf = new UnionFind(1);

		uf.compartenComponenteConexa(1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void findSegundoArgumentoInvalidoTest() {
		UnionFind uf = new UnionFind(1);

		uf.compartenComponenteConexa(0, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void unionPrimerArgumentoInvalidoTest() {
		UnionFind uf = new UnionFind(1);

		uf.union(1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void unionSegundoArgumentoInvalidoTest() {
		UnionFind uf = new UnionFind(1);

		uf.union(0, 1);
	}

	@Test
	public void raizEnUnionFindNuevoTest() {
		UnionFind uf = new UnionFind(1);

		Assert.assertEquals((Integer) 0, uf.find(0).getRaiz());
	}

	@Test
	public void findEnUnionFindNuevoTest() {
		UnionFind uf = new UnionFind(2);

		Assert.assertFalse(uf.compartenComponenteConexa(0, 1));
	}

	@Test
	public void findLuegoDeUnion() {
		UnionFind uf = new UnionFind(2);

		uf.union(0, 1);

		Assert.assertTrue(uf.compartenComponenteConexa(0, 1));
	}

	@Test
	public void findLuegoDeCadenaDeUnions() {
		UnionFind uf = new UnionFind(5);

		uf.union(0, 1);
		uf.union(1, 2);
		uf.union(2, 3);
		uf.union(3, 4);

		Assert.assertTrue(uf.compartenComponenteConexa(0, 4));
	}

	@Test
	public void arbolDeMenorProfundidadEsPadre() {
		UnionFind uf = new UnionFind(6);

		// Arbol 1.
		uf.union(0, 1);
		uf.union(2, 1);
		uf.union(3, 2);

		// Arbol 2.
		uf.union(4, 5);

		// Union arboles.
		uf.union(0, 5);

		Assert.assertEquals((Integer) 5, uf.find(0).getRaiz());
	}

	@Test
	public void caminoSeComprime() {
		UnionFind uf = new UnionFind(5);

		uf.union(0, 1);
		uf.union(1, 2);
		uf.union(2, 3);
		uf.union(3, 4);

		uf.find(0); // Camino se comprime luego de un find.

		Assert.assertEquals(1, uf.find(0).getProfundidadArbol());
	}
}
