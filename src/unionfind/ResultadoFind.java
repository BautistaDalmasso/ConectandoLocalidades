package unionfind;

public class ResultadoFind {
	private Integer raiz;
	private int profundidadArbol;

	public ResultadoFind(Integer raiz) {
		this.raiz = raiz;
		this.profundidadArbol = 0;
	}

	public void aumentarProfundidad() {
		profundidadArbol++;
	}

	public Integer getRaiz() {
		return raiz;
	}

	public int getProfundidadArbol() {
		return profundidadArbol;
	}
}