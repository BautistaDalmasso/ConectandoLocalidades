package negocio;

import unionfind.UnionFind;

public class ArbolGeneradorMinimo {
	private static int indiceProximaConexion;

	public static void construir(GrafoCompletoLocalidades grafoOriginal, ConexionLocalidades[] conexionesOrdenadas) {
		int n = grafoOriginal.getTamanio();
		UnionFind unionfind = new UnionFind(n);
		indiceProximaConexion = 0;

		for (int i = 1; i <= n - 1; i++) {
			ConexionLocalidades seleccionada = seleccionarConexionAciclica(grafoOriginal, conexionesOrdenadas,
					unionfind);
			
			grafoOriginal.getArbolGeneradorMinimo().agregarConexion(seleccionada);
		}
	}

	private static ConexionLocalidades seleccionarConexionAciclica(GrafoCompletoLocalidades grafoOriginal,
			ConexionLocalidades[] conexionesOrdenadas, UnionFind unionfind) {
		ConexionLocalidades conexionSiendoEvaluada = conexionesOrdenadas[indiceProximaConexion];
		while (conexionGeneraCiclo(unionfind, grafoOriginal, conexionSiendoEvaluada)) {
			indiceProximaConexion++;
			conexionSiendoEvaluada = conexionesOrdenadas[indiceProximaConexion];
		}
		indiceProximaConexion++;
		return conexionSiendoEvaluada;
	}

	private static boolean conexionGeneraCiclo(UnionFind uf, GrafoCompletoLocalidades grafo,
			ConexionLocalidades conexion) {
		Integer indiceLocalidadA = grafo.indiceLocalidad(conexion.getLocalidadA());
		Integer indiceLocalidadB = grafo.indiceLocalidad(conexion.getLocalidadB());

		return uf.compartenComponenteConexa(indiceLocalidadA, indiceLocalidadB);
	}
}
