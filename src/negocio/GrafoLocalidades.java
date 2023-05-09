package negocio;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class GrafoLocalidades {
	private Set<Localidad> localidades;
	private Map<Localidad, Set<ConexionLocalidades>> localidadesConSusVecinos;
	private Integer costoTotalConexiones;

	public GrafoLocalidades() {
		localidades = new HashSet<Localidad>();
		localidadesConSusVecinos = new HashMap<Localidad, Set<ConexionLocalidades>>();
		costoTotalConexiones = 0;
	}

	public void agregarLocalidad(Localidad localidad) {
		verificarLocalidadValidaInexistente(localidad);

		getLocalidadesConSusVecinos().put(localidad, new HashSet<ConexionLocalidades>());

		getLocalidades().add(localidad);
	}

	protected void verificarLocalidadValidaInexistente(Localidad localidad) {
		verificarLocalidadNoNula(localidad);
		if (localidadExiste(localidad)) {
			throw new IllegalArgumentException("La localidad <" + localidad.getNombreUnico() + "> ya fue agregada.");
		}
	}

	public boolean localidadExiste(Localidad localidad) {
		return localidades.contains(localidad);
	}
	
	public ConexionLocalidades agregarConexion(Localidad localidadA, Localidad localidadB) {
		ConexionLocalidades nuevaConexion = new ConexionLocalidades(localidadA, localidadB);
		
		return agregarConexion(nuevaConexion);
	}
	
	public ConexionLocalidades agregarConexion(ConexionLocalidades conexion) {
		Set<ConexionLocalidades> conexionesLocalidadA = obtenerConexiones(conexion.getLocalidadA());
		Set<ConexionLocalidades> conexionesLocalidadB = obtenerConexiones(conexion.getLocalidadB());
		
		conexionesLocalidadA.add(conexion);
		conexionesLocalidadB.add(conexion);
		
		
		this.costoTotalConexiones += conexion.getCostoDeLaConexion();

		return conexion;
	}
	
	public void eliminarLocalidad(Localidad localidad) {
		verificarLocalidadValidaExistente(localidad);

		eliminarConexiones(localidad);
		
		localidadesConSusVecinos.remove(localidad);
		localidades.remove(localidad);
	}
	
	protected void verificarLocalidadValidaExistente(Localidad localidad) {
		verificarLocalidadNoNula(localidad);
		if (!localidadExiste(localidad)) {
			throw new IllegalArgumentException("La localidad <" + localidad.getNombreUnico() + "> no existe en el grafo.");
		}
	}
	
	private void verificarLocalidadNoNula(Localidad localidad) {
		if (localidad == null) {
			throw new IllegalArgumentException("localidad no puede ser null.");
		}
	}
	
	private void eliminarConexiones(Localidad localidad) {
		Set<ConexionLocalidades> conexiones = Set.copyOf(obtenerConexiones(localidad));
				
		for (ConexionLocalidades conexion : conexiones) {
			eliminarConexion(conexion);
		}
	}
	
	private void eliminarConexion(ConexionLocalidades conexion) {
		Set<ConexionLocalidades> conexionesLocalidadA = obtenerConexiones(conexion.getLocalidadA());
		Set<ConexionLocalidades> conexionesLocalidadB = obtenerConexiones(conexion.getLocalidadB());
		
		conexionesLocalidadA.remove(conexion);
		conexionesLocalidadB.remove(conexion);
		
		this.costoTotalConexiones -= conexion.getCostoDeLaConexion();
	}
	
	public void limpiarConexiones() {
		for (Localidad loc : localidades) {
			localidadesConSusVecinos.put(loc, new HashSet<ConexionLocalidades>());
		}
		
		costoTotalConexiones = 0;
	}

	public Set<ConexionLocalidades> obtenerConexiones(Localidad localidad) {
		return localidadesConSusVecinos.get(localidad);
	}
	
	public Set<Localidad> getLocalidades() {
		return localidades;
	}

	public Map<Localidad, Set<ConexionLocalidades>> getLocalidadesConSusVecinos() {
		return localidadesConSusVecinos;
	}
	
	public int getTamanio() {
		return localidades.size();
	}
	
	public Integer getCostoTotalConexiones() {
		return this.costoTotalConexiones;
	}

	@Override
	public int hashCode() {
		return Objects.hash(localidades, localidadesConSusVecinos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof GrafoLocalidades))
			return false;
		GrafoLocalidades other = (GrafoLocalidades) obj;
		return Objects.equals(localidades, other.localidades)
				&& Objects.equals(localidadesConSusVecinos, other.localidadesConSusVecinos);
	}
}
