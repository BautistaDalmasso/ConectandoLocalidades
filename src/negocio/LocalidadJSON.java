package negocio;

public class LocalidadJSON {

	public String localidad;
	public String provincia;
	public double latitud;
	public double longitud;

	public LocalidadJSON(Localidad l)
	{
		localidad = l.getNombre();
		provincia = l.getProvincia();
		latitud = l.getPosicion().getLatitud();
		longitud = l.getPosicion().getLongitud();
	}

	public LocalidadJSON()
	{ } // constructor requerido por GSON
	
	public String getNombre() {
		return localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public double getLatitud() {
		return latitud;
	}

	public double getLongitud() {
		return longitud;
	}
	
}
