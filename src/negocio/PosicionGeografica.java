package negocio;

public class PosicionGeografica {
	private static final int RADIO_TIERRA_KM = 6371;
	private static final double RADIANES_POR_GRADO = Math.PI / 180;
	
	private double latitud;
	private double longitud;
	
	
	public PosicionGeografica(double latitud, double longitud) {
		validarLatitudYLongitud(latitud, longitud);
		
		this.latitud = latitud;
		this.longitud = longitud;
	}
	
	private static void validarLatitudYLongitud(double latitud, double longitud) {
		if (latitud < -90 || latitud > 90) {
			throw new IllegalArgumentException("Latitud debe estar en el rango [-90, 90], se recibió latitud=" + latitud);
		}
		if (longitud < -180 || longitud > 180) {
			throw new IllegalArgumentException("Longitud debe estar en el rango [-180, 180], se recibió longitud=" + longitud);
		}
	}
	
	static double gradosARadianes(double grados) {
		return RADIANES_POR_GRADO * grados;
	}
}
