package negocio;

import unionfind.UnionFind;

public class ConstructorAGM {
	private GrafoCompletoLocalidades grafoOriginal;
	private int indiceProximaConexion;

	public ConstructorAGM(GrafoCompletoLocalidades grafoOriginal) {
		this.grafoOriginal = grafoOriginal;
	}
	
	public void construir() {
		if (grafoOriginal.getTamanio() == 0) {
			return;
		}
		
		int n = grafoOriginal.getTamanio();
		ConexionLocalidades[] conexionesOrdenadas = grafoOriginal.getConexionesOrdenadas();
		UnionFind unionfind = new UnionFind(n);
		indiceProximaConexion = 0;

		for (int i = 1; i <= n - 1; i++) {
			ConexionLocalidades seleccionada = seleccionarConexionAciclica(unionfind, conexionesOrdenadas);
			
			unirLocalidades(unionfind, seleccionada);
			grafoOriginal.getArbolGeneradorMinimo().agregarConexion(seleccionada);
		}
	}

	private ConexionLocalidades seleccionarConexionAciclica(UnionFind unionfind, ConexionLocalidades[] conexionesOrdenadas) {
		ConexionLocalidades conexionSiendoEvaluada = conexionesOrdenadas[indiceProximaConexion];
		while (conexionGeneraCiclo(unionfind, conexionSiendoEvaluada)) {
			indiceProximaConexion++;
			conexionSiendoEvaluada = conexionesOrdenadas[indiceProximaConexion];
		}
		indiceProximaConexion++;
		return conexionSiendoEvaluada;
	}

	private boolean conexionGeneraCiclo(UnionFind uf, ConexionLocalidades conexion) {
		Integer indiceLocalidadA = grafoOriginal.indiceLocalidad(conexion.getLocalidadA());
		Integer indiceLocalidadB = grafoOriginal.indiceLocalidad(conexion.getLocalidadB());

		return uf.compartenComponenteConexa(indiceLocalidadA, indiceLocalidadB);
	}
	
	private void unirLocalidades(UnionFind uf, ConexionLocalidades conexion) {
		Integer indiceLocalidadA = grafoOriginal.indiceLocalidad(conexion.getLocalidadA());
		Integer indiceLocalidadB = grafoOriginal.indiceLocalidad(conexion.getLocalidadB());
		
		uf.union(indiceLocalidadA, indiceLocalidadB);
	}
}
