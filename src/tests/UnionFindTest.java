package tests;

import org.junit.Assert;
import org.junit.Test;

import unionfind.UnionFind;

public class UnionFindTest {
	@Test (expected = IllegalArgumentException.class)
	public void inicializarUnionFindConValorInvalidoTest() {
		new UnionFind(0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void raizDeVerticeNegativoTest() {
		UnionFind uf = new UnionFind(1);
		
		uf.raiz(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void raizDeVerticeInexistenteTest() {
		UnionFind uf = new UnionFind(1);
		
		uf.raiz(1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void findPrimerArgumentoInvalidoTest() {
		UnionFind uf = new UnionFind(1);
		
		uf.find(1, 0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void findSegundoArgumentoInvalidoTest() {
		UnionFind uf = new UnionFind(1);
		
		uf.find(0, 1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void unionPrimerArgumentoInvalidoTest() {
		UnionFind uf = new UnionFind(1);
		
		uf.union(1, 0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void unionSegundoArgumentoInvalidoTest() {
		UnionFind uf = new UnionFind(1);
		
		uf.union(0, 1);
	}
	
	@Test
	public void raizEnUnionFindNuevoTest() {
		UnionFind uf = new UnionFind(1);
		
		Assert.assertEquals(0, uf.raiz(0));
	}
	
	@Test
	public void findEnUnionFindNuevoTest() {
		UnionFind uf = new UnionFind(2);
	
		Assert.assertFalse(uf.find(0, 1));
	}
	
	@Test
	public void findLuegoDeUnion() {
		UnionFind uf = new UnionFind(2);
		
		uf.union(0, 1);
		
		Assert.assertTrue(uf.find(0, 1));
	}
}
