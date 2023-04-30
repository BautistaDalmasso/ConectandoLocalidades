package negocio;

public class ConexionLocalidades implements AristaPesada, Comparable<ConexionLocalidades> {
	public static final int COSTO_POR_KILOMETRO = 1000;
	public static final double AUMENTO_POR_SUPERAR_300_KM = 0.8;
	public static final int COSTO_POR_INVOLUCRAR_2_PROVINCIAS = 20000;

	private Localidad localidadA;
	private Localidad localidadB;
	private Integer costoDeLaConexion;

	public ConexionLocalidades(Localidad localidadA, Localidad localidadB) {
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
	
	public Integer getCostoDeLaConexion()
	{
		return costoDeLaConexion;
	}
	
	@Override
	public Integer getPeso() {
		return costoDeLaConexion;
	}

	
	@Override
	public int hashCode() {
		// Bitwise XOR es una operacion conmutativa que nos asegura que el hash code va
		// a ser igual sin importar el orden en el que se agreguen las localidades:
		// es decir: conexionAB.hashCode() == conexionBA.hashCode()
		return localidadA.hashCode() ^ localidadB.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ConexionLocalidades))
			return false;
		ConexionLocalidades other = (ConexionLocalidades) obj;
		// El orden de las localidades no importa:
		// conexionAB.equals(conexionBA) debe ser true.
		return (localidadA.equals(other.localidadA) && localidadB.equals(other.localidadB))
				|| (localidadA.equals(other.localidadB) && localidadB.equals(other.localidadA));
	}

	
	@Override
	public String toString() {
		return "ConexionLocalidades [<" 
				+ localidadA.getNombre() + ", " + localidadB.getNombre() + ">, "
				+ costoDeLaConexion +"]";
	}

	public Localidad getLocalidadA() {
		return localidadA;
	}

	public Localidad getLocalidadB() {
		return localidadB;
	}

	@Override
	public int compareTo(ConexionLocalidades o) {
		return costoDeLaConexion.compareTo(o.costoDeLaConexion);
	}
}
