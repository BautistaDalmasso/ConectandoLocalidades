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

		String nombreLocalidad = localidad.getNombre();

		getLocalidadesConSusVecinos().put(nombreLocalidad, new HashSet<ConexionLocalidades>());

		getLocalidades().add(localidad);
	}

	protected void verificarLocalidad(Localidad localidad) {
		if (localidad == null) {
			throw new IllegalArgumentException("localidad no puede ser null.");
		}
		if (localidadExiste(localidad)) {
			throw new IllegalArgumentException("La localidad <" + localidad + "> ya fue agregada.");
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
		String nombreLocalidadA = conexion.getLocalidadA().getNombre();
		String nombreLocalidadB = conexion.getLocalidadB().getNombre();
		Set<ConexionLocalidades> conexionesLocalidadA = localidadesConSusVecinos.get(nombreLocalidadA);
		Set<ConexionLocalidades> conexionesLocalidadB = localidadesConSusVecinos.get(nombreLocalidadB);
		
		conexionesLocalidadA.add(conexion);
		conexionesLocalidadB.add(conexion);
		
		return conexion;
	}

	public Set<ConexionLocalidades> obtenerConexiones(Localidad localidad) {
		return localidadesConSusVecinos.get(localidad.getNombre());
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
