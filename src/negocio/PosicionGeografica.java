package negocio;

import java.util.Objects;

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
	
	public PosicionGeografica() {} //constructor vacío requerido por Gson
	
	private static void validarLatitudYLongitud(double latitud, double longitud) {
		if (latitud < -90 || latitud > 90) {
			throw new IllegalArgumentException("Latitud debe estar en el rango [-90, 90], se recibió latitud=" + latitud);
		}
		if (longitud < -180 || longitud > 180) {
			throw new IllegalArgumentException("Longitud debe estar en el rango [-180, 180], se recibió longitud=" + longitud);
		}
	}
	
	public static Double distanciaEnKilometros(PosicionGeografica posicionSalida, PosicionGeografica posicionLlegada) {
		// Calculada con el algoritmo de Harvensine.
		// Esta formula tiene un margen de error de alrededor del 0.5% debido a que asume que la Tierra es una esfera perfecta.
		Double diferenciaLatitudes = gradosARadianes(posicionLlegada.latitud-posicionSalida.latitud);
		Double diferenciaLongitudes = gradosARadianes(posicionLlegada.longitud-posicionSalida.longitud);
		
		Double a = Math.sin(diferenciaLatitudes / 2) * Math.sin(diferenciaLatitudes / 2) + 
		Math.cos(gradosARadianes(posicionSalida.latitud)) * Math.cos(gradosARadianes(posicionLlegada.latitud)) * 
		Math.sin(diferenciaLongitudes / 2) * Math.sin(diferenciaLongitudes / 2);
		
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		Double distancia = RADIO_TIERRA_KM * c;
		
		return distancia;
	}
	
	static double gradosARadianes(double grados) {
		return RADIANES_POR_GRADO * grados;
	}

	@Override
	public int hashCode() {
		return Objects.hash(latitud, longitud);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PosicionGeografica))
			return false;
		PosicionGeografica other = (PosicionGeografica) obj;
		return Double.doubleToLongBits(latitud) == Double.doubleToLongBits(other.latitud)
				&& Double.doubleToLongBits(longitud) == Double.doubleToLongBits(other.longitud);
	}
	
	public double getLatitud()
	{
		return latitud;
	}
	
	public double getLongitud()
	{
		return longitud;
	}
	
	@Override
	public String toString()
	{
		return "<" + latitud + ", " + longitud + ">";
	}
}
