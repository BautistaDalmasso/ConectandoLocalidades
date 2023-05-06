package negocio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrafoCompletoLocalidades extends GrafoLocalidades {
	private List<ConexionLocalidades> conexiones;
	private Map<Localidad, Integer> localidadesConIndice;
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
		localidadesConIndice = new HashMap<Localidad, Integer>();
		cantidadDeLocalidades = 0;
		costoConexionMaxima = 0;
		constructorAGM = new ConstructorAGM(this);
		
		algoritmoDeOrdenamientoSeleccionado = AlgoritmoDeOrdenamiento.NO_SELECCIONADO;
	}

	public void agregarLocalidad(String nombreLocalidad, String provinciaLocalidad, double latitud, double longitud) {
		Localidad nuevaLocalidad = new Localidad(nombreLocalidad, provinciaLocalidad, latitud, longitud);
		
		agregarLocalidad(nuevaLocalidad);
	}
	
	@Override
	public void agregarLocalidad(Localidad localidad) {
		super.agregarLocalidad(localidad);
		
		arbolGeneradorMinimo.agregarLocalidad(localidad);

		completarGrafoConNuevaLocalidad(localidad);
		actualizarLocalidadesConIndice(localidad);
	}
	
	private void completarGrafoConNuevaLocalidad(Localidad nuevaLocalidad) {
		for (Localidad localidad : getLocalidades()) {
			if (localidad != nuevaLocalidad) {
				agregarConexion(nuevaLocalidad, localidad);
			}
		}
	}

	@Override
	public ConexionLocalidades agregarConexion(Localidad nuevaLocalidad, Localidad localidad) {
		ConexionLocalidades nuevaConexion = super.agregarConexion(nuevaLocalidad, localidad);
		conexiones.add(nuevaConexion);
		
		setCostoConexionMaxima(nuevaConexion);
		
		return nuevaConexion;
	}

	private void setCostoConexionMaxima(ConexionLocalidades nuevaConexion) {
		if (nuevaConexion.getPeso().compareTo(costoConexionMaxima) > 0) {
			costoConexionMaxima = nuevaConexion.getPeso();
		}
	}

	private void actualizarLocalidadesConIndice(Localidad localidad) {
		localidadesConIndice.put(localidad, cantidadDeLocalidades);
		cantidadDeLocalidades++;
	}
	
	@Override
	public void eliminarLocalidad(Localidad localidad) {
		verificarLocalidadValidaExistente(localidad);
		
		eliminarDeConexiones(localidad);
		eliminarDeIndices(localidad);
		arbolGeneradorMinimo.eliminarLocalidad(localidad);
		
		super.eliminarLocalidad(localidad);
		cantidadDeLocalidades--;
	}
	
	private void eliminarDeConexiones(Localidad localidad) {
		for (ConexionLocalidades conexion : obtenerConexiones(localidad)) {
			conexiones.remove(conexion);
		}
	}

	private void eliminarDeIndices(Localidad localidad) {
		Integer indiceLocalidad = localidadesConIndice.get(localidad);

		reducirIndices(indiceLocalidad);
		
		localidadesConIndice.remove(localidad);
	}
	
	private void reducirIndices(Integer pisoReduccion) {
		Integer indiceLocalidad;
		
		for (Localidad loc : getLocalidades()) {
			indiceLocalidad = localidadesConIndice.get(loc);
			
			if (indiceLocalidad.compareTo(pisoReduccion) > 0) {
				localidadesConIndice.put(loc, indiceLocalidad - 1);
			}
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
		if (elegibleParaRadixSort()) {
			setAlgoritmoDeOrdenamiento(AlgoritmoDeOrdenamiento.RADIX_SORT);
		}
		else {
			setAlgoritmoDeOrdenamiento(AlgoritmoDeOrdenamiento.ARRAYS_SORT);
		}
	}
	
	@SuppressWarnings("unused")
	private boolean elegibleParaRadixSort() {
		// El stress test arrojo que RadixSort es más lento que Arrays.Sort en casi todos los casos.
		// Mantenemos esta función como un ejemplo de selección "inteligente" de algoritmo que podría usarse si
		// nuestra implementación de RadixSort fuera más eficiente en ciertos casos (actualmente siempre devuelve false).
		return false && (conexiones.size() - RadixSort.cantidadDeDigitos(costoConexionMaxima) > 5);
	}
	
	public Integer getIndiceLocalidad(Localidad localidad) {
		if (localidad == null) {
			throw new IllegalArgumentException("Localidad no puede ser null.");
		}
		
		return localidadesConIndice.get(localidad);
	}
	
	public GrafoLocalidades getArbolGeneradorMinimo() {
		return arbolGeneradorMinimo;
	}
	
	public void setAlgoritmoDeOrdenamiento(AlgoritmoDeOrdenamiento algoritmo) {
		algoritmoDeOrdenamientoSeleccionado = algoritmo;
	}
	public void cargarListaLocalidades(ArrayList<Localidad> localidadesElegidas) {
		for (Localidad l: localidadesElegidas)
		{
			this.agregarLocalidad(l);
		}
	}

	public int getCantidadDeLocalidades() {
		return cantidadDeLocalidades;
	}
}
