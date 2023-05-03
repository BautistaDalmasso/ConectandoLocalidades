package negocio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ArchivoLocalidades
{
	private ArrayList<Localidad> localidadesDisponibles;
	private HashMap<String, HashSet<Localidad>> localidadesPorProvincia;

	public ArchivoLocalidades(String nombreArchivoACargar)
	{	
		localidadesDisponibles = new ArrayList<Localidad>();
		localidadesPorProvincia = new HashMap<String, HashSet<Localidad>>() ;
		cargarArchivoJSON(nombreArchivoACargar);
	}
	
	public ArchivoLocalidades()
	{ // (constructor vacío requerido por JSON)
	}

	public void guardarEnDisco(String nombreArchivoAGuardar)
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(this);
		try
		{
			FileWriter writer = new FileWriter(nombreArchivoAGuardar);
			writer.write(json);
			writer.close();
		}
		catch(Exception e) {}
	}
	
	public void cargarArchivoJSON(String archivo)
	{		
		try {  
            JsonArray arrayJSON = leerLocalidades(archivo);
            asignaLocalidadesASusProvincias(arrayJSON);
            
	        } catch (Exception e) {
	            e.printStackTrace();
        }
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
		    Localidad l = traerLocalidadDesdeArchivo(arrayJSON, gson, i);	    
		    addLocalidadASuProvincia(l);
		}
	}

	private Localidad traerLocalidadDesdeArchivo(JsonArray arrayJSON, Gson gson, int i) {
		JsonObject datoLocalidad = arrayJSON.get(i).getAsJsonObject();
		LocalidadJSON localidadJSON = gson.fromJson(datoLocalidad, LocalidadJSON.class);	
		Localidad l = new Localidad(localidadJSON.getNombre(), localidadJSON.getProvincia(),
				localidadJSON.getLatitud(), localidadJSON.getLongitud());
		return l;
	}

	private JsonArray obtenerArrayLocalidades(JsonObject objetoJSON) {
		JsonArray arrayJSON = objetoJSON.getAsJsonArray("JsonFileLocalidades");
		return arrayJSON;
	}

	private void addLocalidadASuProvincia(Localidad l) {
		String provinciaActual = l.getProvincia();
		if (localidadesPorProvincia.containsKey(provinciaActual))
			agregaLocalidadAProvinciaExistente(l, provinciaActual);
		 else 
			agregaLocalidadAProvinciaNueva(l, provinciaActual);
	}

	private void agregaLocalidadAProvinciaNueva(Localidad l, String provinciaActual) {
		HashSet<Localidad> localidadProvinciaNueva = new HashSet<Localidad>();
		localidadProvinciaNueva.add(l);
		localidadesPorProvincia.put(provinciaActual, localidadProvinciaNueva);
	}

	private void agregaLocalidadAProvinciaExistente(Localidad l, String provinciaActual) {
		HashSet<Localidad> localidadesProvinciaActual = localidadesPorProvincia.get(provinciaActual);
		localidadesProvinciaActual.add(l);
		localidadesPorProvincia.put(provinciaActual, localidadesProvinciaActual);
	}

	public ArrayList<Localidad> getLocalidadesDisponibles() {
		return localidadesDisponibles;
	}
	
	public HashMap<String, HashSet<Localidad>> getLocalidadesPorProvincia() {
		return localidadesPorProvincia;
	}

//	public void imprimirLocalidadesPorProvincia() // método usado para pruebas durante la programación 
//	{
//		Set<String> provincias = getProvincias();
//		for (String p: provincias)
//		{
//			System.out.println("\nProvincia de " + p);
//			HashSet <Localidad> localidades = localidadesPorProvincia.get(p);
//			for (Localidad l: localidades)
//			{
//				System.out.println("   " + l.getNombre());
//			}
//		}
//	}
	
	public Set<String> getProvincias()
	{
		return localidadesPorProvincia.keySet();
	}

	public HashSet<Localidad> getLocalidadesDeUnaProvincia(String provincia)
	{
		return localidadesPorProvincia.get(provincia);
	}
	
}
