package unionfind;

public class UnionFind {
	private Integer[] componentesConexas;

	public UnionFind(int cantidadDeVertices) {
		if (cantidadDeVertices <= 0) {
			throw new IllegalArgumentException("La cantidad de vertices debe ser mayor a 0");
		}

		this.componentesConexas = new Integer[cantidadDeVertices];
	}

	public ResultadoFind find(Integer i) {
		chequearVerticeValido(i);

		return findConPathCompression(i);
	}

	private ResultadoFind findConPathCompression(Integer i) {
		setearComoRaiz(i);
		Integer padre = componentesConexas[i];

		if (padre.equals(i)) {
			return new ResultadoFind(i);
		} else {
			ResultadoFind resultado = findConPathCompression(padre);
			resultado.aumentarProfundidad();

			componentesConexas[i] = resultado.getRaiz();

			return resultado;
		}
	}

	private void setearComoRaiz(Integer i) {
		if (componentesConexas[i] == null) {
			componentesConexas[i] = i;
		}
	}

	public boolean compartenComponenteConexa(Integer i, Integer j) {
		chequearVerticeValido(i);
		chequearVerticeValido(j);

		return find(i).getRaiz().equals(find(j).getRaiz());
	}

	public void union(Integer i, Integer j) {
		chequearVerticeValido(i);
		chequearVerticeValido(j);

		ResultadoFind raiz_i = find(i);
		ResultadoFind raiz_j = find(j);

		if (raiz_i.getProfundidadArbol() < raiz_j.getProfundidadArbol()) {
			componentesConexas[raiz_j.getRaiz()] = raiz_i.getRaiz();
		} else {
			componentesConexas[raiz_i.getRaiz()] = raiz_j.getRaiz();
		}
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
