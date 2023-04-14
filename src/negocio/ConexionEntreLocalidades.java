package negocio;

public class ConexionEntreLocalidades implements AristaPesada {
	public static final int COSTO_POR_KILOMETRO = 1000;
	public static final double AUMENTO_POR_SUPERAR_300_KM = 0.8;
	public static final int COSTO_POR_INVOLUCRAR_2_PROVINCIAS = 20000;
	
	private Localidad localidadA;
	private Localidad localidadB;
	private Integer costoDeLaConexion;
	
	public ConexionEntreLocalidades(Localidad localidadA, Localidad localidadB) {
		validarLocalidades(localidadA, localidadB);
		this.localidadA = localidadA;
		this.localidadB = localidadB;
		
		calcularCostoDeConexion();
	}
	
	private void calcularCostoDeConexion() {
		Double distancia = localidadA.distanciaEnKilometros(localidadB);
		Double costo = distancia * COSTO_POR_KILOMETRO;
		
		if (distancia > 300) {
			costo += costo * AUMENTO_POR_SUPERAR_300_KM;
		}
		if (!localidadA.getProvincia().equals(localidadB.getProvincia())) {
			costo += COSTO_POR_INVOLUCRAR_2_PROVINCIAS;
		}
		
		this.costoDeLaConexion = (int) Math.round(costo);
	}
	
	private void validarLocalidades(Localidad localidadA, Localidad localidadB) {
		if (localidadA == null) {
			throw new IllegalArgumentException("Localidad A no puede ser null.");
		}
		if (localidadB == null) {
			throw new IllegalArgumentException("Localidad B no puede ser null.");
		}
		if (localidadA == localidadB) {
			throw new IllegalArgumentException(
					"Localidad A y localidad B no pueden ser las mismas: <" + localidadA + "," + localidadB + ">");
		}
	}
	
	@Override
	public Integer getPeso() {
		return costoDeLaConexion;
	}
	
}
