package unionfind;

public class UnionFind {
	private Integer[] componentesConexas;
	
	public UnionFind(int cantidadDeVertices) {
		if (cantidadDeVertices <= 0) {
			throw new IllegalArgumentException("La cantidad de vertices debe ser mayor a 0");
		}
		
		this.componentesConexas = new Integer[cantidadDeVertices];
	}
	
	public int raiz(Integer i) {
		chequearVerticeValido(i);
		
		while (componentesConexas[i] != i) {
			setearComoRaiz(i);
			
			i = componentesConexas[i];
		}
		
		return i;
	}

	private void setearComoRaiz(Integer i) {
		if (componentesConexas[i] == null) {
			componentesConexas[i] = i;
		}
	}
	
	public boolean find(Integer i, Integer j) {
		chequearVerticeValido(i);
		chequearVerticeValido(j);
		
		return raiz(i) == raiz(j);
	}
	
	public void union(Integer i, Integer j) {
		chequearVerticeValido(i);
		chequearVerticeValido(j);

		
		int raiz_i = raiz(i);
		int raiz_j = raiz(j);
		
		componentesConexas[raiz_i] = raiz_j;
	}
	
	private void chequearVerticeValido(Integer i) {
		if (i < 0) {
			throw new IllegalArgumentException("Los vertices deben ser mayores o iguales a cero. Se recibió: " + i);
		}
		if (i >= componentesConexas.length) {
			throw new IllegalArgumentException("El vertice <" + i + "> no se encuentra entre los vertices posibles.");
		}
	}
}
