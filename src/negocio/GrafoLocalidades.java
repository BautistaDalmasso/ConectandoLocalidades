package negocio;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class GrafoLocalidades {
	private Set<Localidad> localidades;
	private Map<String, Set<ConexionLocalidades>> localidadesConSusVecinos;

	public GrafoLocalidades() {
		localidades = new HashSet<Localidad>();
		localidadesConSusVecinos = new HashMap<String, Set<ConexionLocalidades>>();
	}

	public void agregarLocalidad(Localidad localidad) {
		verificarLocalidad(localidad);

		String nombreUnico = localidad.getNombreUnico();

		getLocalidadesConSusVecinos().put(nombreUnico, new HashSet<ConexionLocalidades>());

		getLocalidades().add(localidad);
	}

	protected void verificarLocalidad(Localidad localidad) {
		if (localidad == null) {
			throw new IllegalArgumentException("localidad no puede ser null.");
		}
		if (localidadExiste(localidad)) {
			throw new IllegalArgumentException("La localidad <" + localidad.getNombreUnico() + "> ya fue agregada.");
		}
	}

	public boolean localidadExiste(Localidad localidad) {
		return localidadesConSusVecinos.containsKey(localidad.getNombreUnico());
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
		
		return conexion;
	}
	
	public void limpiarConexiones() {
		for (Localidad loc : localidades) {
			localidadesConSusVecinos.put(loc.getNombreUnico(), new HashSet<ConexionLocalidades>());
		}
	}

	public Set<ConexionLocalidades> obtenerConexiones(Localidad localidad) {
		return localidadesConSusVecinos.get(localidad.getNombreUnico());
	}
	
	public Set<Localidad> getLocalidades() {
		return localidades;
	}

	public Map<String, Set<ConexionLocalidades>> getLocalidadesConSusVecinos() {
		return localidadesConSusVecinos;
	}
	
	public int getTamanio() {
		return localidades.size();
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
