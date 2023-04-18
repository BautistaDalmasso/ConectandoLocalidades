package negocio;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GrafoCompletoLocalidades {
	private Set<Localidad> localidades;
	private Set<ConexionEntreLocalidades> conexiones;
	private Map<Localidad, Set<ConexionEntreLocalidades>> localidadesConSusVecinos;

	public GrafoCompletoLocalidades() {
		localidades = new HashSet<Localidad>();
		conexiones = new HashSet<ConexionEntreLocalidades>();
		localidadesConSusVecinos = new HashMap<Localidad, Set<ConexionEntreLocalidades>>();
	}

	public void agregarLocalidad(Localidad localidad) {
		verificarLocalidad(localidad);
		localidadesConSusVecinos.put(localidad, new HashSet<ConexionEntreLocalidades>());

		completarGrafoConNuevaLocalidad(localidad);

		localidades.add(localidad);
	}

	private void completarGrafoConNuevaLocalidad(Localidad nuevaLocalidad) {
		Set<ConexionEntreLocalidades> conexionesNuevaLocalidad = localidadesConSusVecinos.get(nuevaLocalidad);

		for (Localidad localidad : localidades) {
			Set<ConexionEntreLocalidades> conexionesLocalidad = localidadesConSusVecinos.get(localidad);
			ConexionEntreLocalidades nuevaConexion = new ConexionEntreLocalidades(localidad, nuevaLocalidad);

			conexiones.add(nuevaConexion);
			conexionesNuevaLocalidad.add(nuevaConexion);
			conexionesLocalidad.add(nuevaConexion);
		}
	}

	public boolean localidadExiste(Localidad localidad) {
		return localidades.contains(localidad);
	}
	

	public Set<ConexionEntreLocalidades> getConexionesDeUnaLocalidad(Localidad loc)
	{	
		return localidadesConSusVecinos.get(loc);
	}
	
	
	public Set<ConexionEntreLocalidades> obtenerConexiones(Localidad localidad) {
		return localidadesConSusVecinos.get(localidad);
	}

	private void verificarLocalidad(Localidad localidad) {
		if (localidad == null) {
			throw new IllegalArgumentException("localidad no puede ser null.");
		}
		if (localidades.contains(localidad)) {
			throw new IllegalArgumentException("La localidad <" + localidad + "> ya fue agregada.");
		}
	}
	
	public Set<Localidad> getLocalidades()
	{
		return localidades;
	}
}
