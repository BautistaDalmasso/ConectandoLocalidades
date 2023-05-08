package negocio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArchivoLocalidades {
	private ArrayList<Localidad> localidadesDisponibles;
	private HashMap<String, HashSet<Localidad>> localidadesPorProvincia;


	public ArchivoLocalidades(String nombreArchivoACargar)
	{	
		validarNombreArchivo(nombreArchivoACargar);
		
		localidadesDisponibles = new ArrayList<Localidad>();
		localidadesPorProvincia = new HashMap<String, HashSet<Localidad>>();
		cargarArchivoJSON(nombreArchivoACargar);
	}

	public ArchivoLocalidades() { // (constructor vacío requerido por JSON)
	}


	public boolean guardarEnDisco(File nombreArchivoAGuardar, List<Localidad> localidades)
	{	
		validarNombreArchivo(nombreArchivoAGuardar.getName());
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(localidades);
		try {
			FileWriter writer = new FileWriter(nombreArchivoAGuardar);
			writer.write(json);
			writer.close();
		}
		catch(IOException e) {
			return false;
		}
		return true;
	}

	
	public List<Localidad> cargarDelDisco(File nombreArchivoACargar) throws FileNotFoundException
	{
		
		JsonParser parser = new JsonParser();
		FileReader lectorArchivo = new FileReader(nombreArchivoACargar);
		JsonArray listaLocJSON = (JsonArray) parser.parse(lectorArchivo);
		
		List <Localidad> listaLoc = new ArrayList<Localidad>();

		Gson gson = new Gson();
		for (int i = 0; i < listaLocJSON.size(); i++) {
			
			JsonObject dato = listaLocJSON.get(i).getAsJsonObject();
			Localidad localidad = gson.fromJson(dato, Localidad.class);
			listaLoc.add(localidad);
		}
		return listaLoc;
	}
	

	public void validarNombreArchivo(String n) {
		validarStrNoNulo(n);
		validarStrNoVacio(n);
		validarStrNoCharsProhibidos(n);
	}

	public void validarStrNoCharsProhibidos(String n) {
		String caracteresProhibidos = " \\/:*?\"<>|";
		for (int i=0; i<n.length(); i++) {
			if (caracteresProhibidos.contains(""+n.charAt(i)))
				throw new IllegalArgumentException("El nombre no puede"
						+ "contener caracteres reservados: "
						+ caracteresProhibidos);
		}
	}

	private void validarStrNoVacio(String n) {
		if (n.equals("")) throw new IllegalArgumentException(
				"El nombre de archivo no puede ser cadena vacía.");
	}

	private void validarStrNoNulo(String n) {
		if (n == null) throw new IllegalArgumentException(
				"El nombre de archivo no puede ser null.");
	}

	
	public boolean cargarArchivoJSON(String archivo)
	{		
		try {  
            JsonArray arrayJSON = leerLocalidades(archivo);
            asignaLocalidadesASusProvincias(arrayJSON);
            
	        } catch (Exception e) {
	        	return false;
        }
		return true;
	}

	private JsonArray leerLocalidades(String archivo) throws FileNotFoundException {
		FileReader lectorArchivo = new FileReader(archivo);
		JsonParser parserJSON = new JsonParser();
		JsonObject objetoJSON = (JsonObject) parserJSON.parse(lectorArchivo);
		JsonArray arrayJSON = obtenerArrayLocalidades(objetoJSON);
		return arrayJSON;
	}

	private void asignaLocalidadesASusProvincias(JsonArray arrayJSON) {
		Gson gson = new Gson();
		for (int i = 0; i < arrayJSON.size(); i++) {
			Localidad localidad = traerLocalidadDesdeArchivo(arrayJSON, gson, i);
			addLocalidadASuProvincia(localidad);
		}
	}

	private Localidad traerLocalidadDesdeArchivo(JsonArray arrayJSON, Gson gson, int i) {
		JsonObject datoLocalidad = arrayJSON.get(i).getAsJsonObject();
		LocalidadJSON localidadJSON = gson.fromJson(datoLocalidad, LocalidadJSON.class);
		Localidad localidad = new Localidad(localidadJSON.getNombre(), localidadJSON.getProvincia(),
				localidadJSON.getLatitud(), localidadJSON.getLongitud());
		return localidad;
	}

	private JsonArray obtenerArrayLocalidades(JsonObject objetoJSON) {
		JsonArray arrayJSON = objetoJSON.getAsJsonArray("JsonFileLocalidades");
		return arrayJSON;
	}

	private void addLocalidadASuProvincia(Localidad localidad) {
		String provinciaActual = localidad.getProvincia();
		if (localidadesPorProvincia.containsKey(provinciaActual))
			agregaLocalidadAProvinciaExistente(localidad, provinciaActual);
		else
			agregaLocalidadAProvinciaNueva(localidad, provinciaActual);
	}

	private void agregaLocalidadAProvinciaNueva(Localidad localidad, String provinciaActual) {
		HashSet<Localidad> localidadProvinciaNueva = new HashSet<Localidad>();
		localidadProvinciaNueva.add(localidad);
		localidadesPorProvincia.put(provinciaActual, localidadProvinciaNueva);
	}

	private void agregaLocalidadAProvinciaExistente(Localidad localidad, String provinciaActual) {
		localidadesPorProvincia.get(provinciaActual).add(localidad);
	}

	public Localidad obtenerLocalidad(String nombreLocalidad, String provinciaLocalidad) {
		for (Localidad localidad : localidadesPorProvincia.get(provinciaLocalidad)) {
			if (localidad.getNombre().equals(nombreLocalidad)) {
				return localidad;
			}
		}
		
		return null;
	}
	
	public ArrayList<Localidad> getLocalidadesDisponibles() {
		return localidadesDisponibles;
	}

	public HashMap<String, HashSet<Localidad>> getLocalidadesPorProvincia() {
		return localidadesPorProvincia;
	}

	public Set<String> getProvincias() {
		return localidadesPorProvincia.keySet();
	}

	public HashSet<Localidad> getLocalidadesDeUnaProvincia(String provincia) {
		return localidadesPorProvincia.get(provincia);
	}

}
