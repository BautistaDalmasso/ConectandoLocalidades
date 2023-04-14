package negocio;

public class Localidad {
	private String nombre;
	private String provincia;
	private PosicionGeografica posicion;
	
	
	public Localidad(String nombre, String provincia, double latitud, double longitud) {
		validarNombreYProvincia(nombre, provincia);
		this.nombre = nombre;
		this.provincia = provincia;
		this.posicion = new PosicionGeografica(latitud, longitud);
	}

	public Double distancia(Localidad otra) {
		return PosicionGeografica.distanciaEnKilometros(posicion, otra.posicion);
	}
	
	private void validarNombreYProvincia(String nombre, String provincia) {
		if (nombre == null) {
			throw new IllegalArgumentException("El nombre de la localidad no puede ser null.");
		}
		if (nombre.length() == 0) {
			throw new IllegalArgumentException("El nombre de la localidad no puede estar vacio");
		}
		if (provincia == null) {
			throw new IllegalArgumentException("El nombre de la provincia no puede ser null.");
		}
		if (provincia.length() == 0) {
			throw new IllegalArgumentException("El nombre de la provincia no puede estar vacio");
		}
	}


	public String getNombre() {
		return nombre;
	}


	public String getProvincia() {
		return provincia;
	}


	public PosicionGeografica getPosicion() {
		return posicion;
	}
}