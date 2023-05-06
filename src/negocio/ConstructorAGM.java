package negocio;

public class ConstructorAGM {
	private GrafoCompletoLocalidades grafoOriginal;
	private int indiceProximaConexion;
	private UnionFind unionfind;
	private ConexionLocalidades[] conexionesOrdenadas;
	private int n;
	
	public ConstructorAGM(GrafoCompletoLocalidades grafoOriginal) {
		this.grafoOriginal = grafoOriginal;
	}
	
	public void construir() {
		if (grafoOriginal.getTamanio() == 0) {
			return;
		}
		
		inicializarConstructor();

		for (int i = 1; i <= n - 1; i++) {
			ConexionLocalidades seleccionada = seleccionarConexionAciclica();
			
			unirLocalidades(seleccionada);
			grafoOriginal.getArbolGeneradorMinimo().agregarConexion(seleccionada);
		}
	}

	private void inicializarConstructor() {
		n = grafoOriginal.getTamanio();
		conexionesOrdenadas = grafoOriginal.getConexionesOrdenadas();
		unionfind = new UnionFind(n);
		indiceProximaConexion = 0;
	}

	private ConexionLocalidades seleccionarConexionAciclica() {
		ConexionLocalidades conexionSiendoEvaluada = obtenerProximaConexion();
		while (conexionGeneraCiclo(conexionSiendoEvaluada)) {
			conexionSiendoEvaluada = obtenerProximaConexion();
		}
		return conexionSiendoEvaluada;
	}
	
	private ConexionLocalidades obtenerProximaConexion() {
		ConexionLocalidades proxima = conexionesOrdenadas[indiceProximaConexion];
		indiceProximaConexion++;
		return proxima;
	}

	private boolean conexionGeneraCiclo(ConexionLocalidades conexion) {
		Integer indiceLocalidadA = grafoOriginal.getIndiceLocalidad(conexion.getLocalidadA());
		Integer indiceLocalidadB = grafoOriginal.getIndiceLocalidad(conexion.getLocalidadB());

		return unionfind.compartenComponenteConexa(indiceLocalidadA, indiceLocalidadB);
	}
	
	private void unirLocalidades(ConexionLocalidades conexion) {
		Integer indiceLocalidadA = grafoOriginal.getIndiceLocalidad(conexion.getLocalidadA());
		Integer indiceLocalidadB = grafoOriginal.getIndiceLocalidad(conexion.getLocalidadB());
		
		unionfind.union(indiceLocalidadA, indiceLocalidadB);
	}
}
