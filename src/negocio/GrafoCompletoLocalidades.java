package negocio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import radixsort.RadixSort;

public class GrafoCompletoLocalidades extends GrafoLocalidades {
	private List<ConexionLocalidades> conexiones;
	private Map<String, Integer> localidadesConIndice;
	private GrafoLocalidades arbolGeneradorMinimo;
	private int cantidadDeLocalidades;
	private Integer costoConexionMaxima;

	public GrafoCompletoLocalidades() {
		super();

		arbolGeneradorMinimo = new GrafoLocalidades();
		conexiones = new ArrayList<ConexionLocalidades>();
		localidadesConIndice = new HashMap<String, Integer>();
		cantidadDeLocalidades = 0;
	}

	@Override
	public void agregarLocalidad(Localidad localidad) {
		verificarLocalidad(localidad);
		
		arbolGeneradorMinimo.agregarLocalidad(localidad);

		String nombreLocalidad = localidad.getNombre();

		getLocalidadesConSusVecinos().put(nombreLocalidad, new HashSet<ConexionLocalidades>());

		completarGrafoConNuevaLocalidad(localidad);
		getLocalidades().add(localidad);

		localidadesConIndice.put(nombreLocalidad, cantidadDeLocalidades);
		cantidadDeLocalidades++;
	}

	private void completarGrafoConNuevaLocalidad(Localidad nuevaLocalidad) {
		for (Localidad localidad : getLocalidades()) {
			ConexionLocalidades nuevaConexion = agregarConexion(nuevaLocalidad, localidad);
			conexiones.add(nuevaConexion);
			
			setCostoConexionMaxima(nuevaConexion);
		}
	}

	private void setCostoConexionMaxima(ConexionLocalidades nuevaConexion) {
		if (costoConexionMaxima == null || nuevaConexion.getPeso().compareTo(costoConexionMaxima) < 0) {
			costoConexionMaxima = nuevaConexion.getPeso();
		}
	}

	
	public void construirArbolGeneradorMinimo() {
		ConexionLocalidades[] conexiones =  this.conexiones.toArray(ConexionLocalidades[]::new);
		RadixSort.ordenar(conexiones, costoConexionMaxima);
		
		ArbolGeneradorMinimo.construir(this, conexiones);
	}
	
	public Integer indiceLocalidad(Localidad localidad) {
		if (localidad == null) {
			throw new IllegalArgumentException("Localidad no puede ser null.");
		}
		
		return localidadesConIndice.get(localidad.getNombre());
	}
	
	public GrafoLocalidades getArbolGeneradorMinimo() {
		return arbolGeneradorMinimo;
	}
	
	public Integer getCostoConexionMaxima() {
		return costoConexionMaxima;
	}
}
