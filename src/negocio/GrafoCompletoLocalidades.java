package negocio;

import java.util.ArrayList;
import java.util.Arrays;
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
	private ConstructorAGM constructorAGM;

	public static enum AlgoritmoDeOrdenamiento { NO_SELECCIONADO, RADIX_SORT, ARRAYS_SORT };
	private AlgoritmoDeOrdenamiento algoritmoDeOrdenamientoSeleccionado;
	
	public GrafoCompletoLocalidades() {
		super();

		arbolGeneradorMinimo = new GrafoLocalidades();
		conexiones = new ArrayList<ConexionLocalidades>();
		localidadesConIndice = new HashMap<String, Integer>();
		cantidadDeLocalidades = 0;
		costoConexionMaxima = 0;
		constructorAGM = new ConstructorAGM(this);
		
		algoritmoDeOrdenamientoSeleccionado = AlgoritmoDeOrdenamiento.NO_SELECCIONADO;
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

	public void agregarLocalidad(String nombreLocalidad, String provinciaLocalidad, double latitud, double longitud) {
		Localidad nuevaLocalidad = new Localidad(nombreLocalidad, provinciaLocalidad, latitud, longitud);
		
		agregarLocalidad(nuevaLocalidad);
	}
	
	private void completarGrafoConNuevaLocalidad(Localidad nuevaLocalidad) {
		for (Localidad localidad : getLocalidades()) {
			ConexionLocalidades nuevaConexion = agregarConexion(nuevaLocalidad, localidad);
			conexiones.add(nuevaConexion);
			
			setCostoConexionMaxima(nuevaConexion);
		}
	}

	private void setCostoConexionMaxima(ConexionLocalidades nuevaConexion) {
		if (nuevaConexion.getPeso().compareTo(costoConexionMaxima) > 0) {
			costoConexionMaxima = nuevaConexion.getPeso();
		}
	}

	
	public void construirArbolGeneradorMinimo() {
		arbolGeneradorMinimo.limpiarConexiones();
		constructorAGM.construir();
	}
	
	public ConexionLocalidades[] getConexionesOrdenadas() {
		ConexionLocalidades[] conexiones =  this.conexiones.toArray(ConexionLocalidades[]::new);
		
		if (algoritmoDeOrdenamientoSeleccionado == AlgoritmoDeOrdenamiento.NO_SELECCIONADO) {
			seleccionarAlgoritmo();
		}
		
		if (algoritmoDeOrdenamientoSeleccionado == AlgoritmoDeOrdenamiento.RADIX_SORT) {			
			RadixSort.ordenar(conexiones, costoConexionMaxima);
		}
		else {
			Arrays.sort(conexiones);
		}
		
		return conexiones;
	}
	
	private void seleccionarAlgoritmo() {
		if (conexiones.size() - RadixSort.cantidadDeDigitos(costoConexionMaxima) > 5) {
			setAlgoritmoDeOrdenamiento(AlgoritmoDeOrdenamiento.RADIX_SORT);
		}
		else {
			setAlgoritmoDeOrdenamiento(AlgoritmoDeOrdenamiento.ARRAYS_SORT);
		}
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
	
	public void setAlgoritmoDeOrdenamiento(AlgoritmoDeOrdenamiento algoritmo) {
		algoritmoDeOrdenamientoSeleccionado = algoritmo;
	}
}
