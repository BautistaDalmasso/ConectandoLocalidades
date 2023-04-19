package negocio;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GrafoCompletoLocalidades {
	private Set<Localidad> localidades;
	private Set<ConexionEntreLocalidades> conexiones;
	private Map<String, Set<ConexionEntreLocalidades>> localidadesConSusVecinos;
	private Map<String, Integer> localidadesConIndice;
	private int cantidadDeLocalidades;

	public GrafoCompletoLocalidades() {
		localidades = new HashSet<Localidad>();
		conexiones = new HashSet<ConexionEntreLocalidades>();
		localidadesConSusVecinos = new HashMap<String, Set<ConexionEntreLocalidades>>();
		localidadesConIndice = new HashMap<String, Integer>();
		cantidadDeLocalidades = 0;
	}

	public void agregarLocalidad(Localidad localidad) {
		verificarLocalidad(localidad);
		
		String nombreLocalidad = localidad.getNombre();
		
		localidadesConSusVecinos.put(nombreLocalidad, new HashSet<ConexionEntreLocalidades>());

		completarGrafoConNuevaLocalidad(localidad);

		localidades.add(localidad);
		localidadesConIndice.put(nombreLocalidad, cantidadDeLocalidades-1);
		
		cantidadDeLocalidades++;
	}

	private void completarGrafoConNuevaLocalidad(Localidad nuevaLocalidad) {
		Set<ConexionEntreLocalidades> conexionesNuevaLocalidad = localidadesConSusVecinos
				.get(nuevaLocalidad.getNombre());

		for (Localidad localidad : localidades) {
			Set<ConexionEntreLocalidades> conexionesLocalidad = localidadesConSusVecinos.get(localidad.getNombre());
			ConexionEntreLocalidades nuevaConexion = new ConexionEntreLocalidades(localidad, nuevaLocalidad);

			conexiones.add(nuevaConexion);
			conexionesNuevaLocalidad.add(nuevaConexion);
			conexionesLocalidad.add(nuevaConexion);
		}
	}

	public boolean localidadExiste(Localidad localidad) {
		return localidades.contains(localidad);
	}

	public Set<ConexionEntreLocalidades> obtenerConexiones(Localidad localidad) {
		return localidadesConSusVecinos.get(localidad.getNombre());
	}

	private void verificarLocalidad(Localidad localidad) {
		if (localidad == null) {
			throw new IllegalArgumentException("localidad no puede ser null.");
		}
		if (localidades.contains(localidad)) {
			throw new IllegalArgumentException("La localidad <" + localidad + "> ya fue agregada.");
		}
	}
}
