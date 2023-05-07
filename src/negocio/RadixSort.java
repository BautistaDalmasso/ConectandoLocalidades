package negocio;

public class RadixSort {
	private static final int BASE = 10;

	public static void ordenar(AristaPesada[] arregloAOrdenar, Integer valorMaximo) {
		int auxValorMaximo = valorMaximo;

		for (int posicionSiendoOrdenada = 1; auxValorMaximo / posicionSiendoOrdenada > 0; posicionSiendoOrdenada *= BASE)
			CountingSort.ordenar(arregloAOrdenar, posicionSiendoOrdenada);
	}
	
	public static int cantidadDeDigitos(Integer x) {
		comprobarEnteroPositivoOCero(x);
		if (x.equals(0)) {
			return 1;
		}
		return (int) Math.log10(x) + 1;
	}
	
	private static void comprobarEnteroPositivoOCero(Integer x) {
		if (x == null) {
			throw new IllegalArgumentException("Entero no puede ser null.");
		}
		if (x < 0) {
			throw new IllegalArgumentException("Entero debe ser mayor o igual a 0, se recibió: " + x);
		}
	}
}

class CountingSort {
	private static final int BASE = 10;
	
	static void ordenar(AristaPesada arregloAOrdenar[], int posicionSiendoOrdenada) {
		AristaPesada arregloOrdenado[] = new AristaPesada[arregloAOrdenar.length];
		int[] frecuenciaDigitos = calcularFrecuenciaDigitos(arregloAOrdenar, posicionSiendoOrdenada);

		for (int i = arregloAOrdenar.length - 1; i >= 0; i--) {
			int digito = (arregloAOrdenar[i].getPeso() / posicionSiendoOrdenada) % BASE;
			arregloOrdenado[frecuenciaDigitos[digito] - 1] = arregloAOrdenar[i];
			frecuenciaDigitos[digito]--;
		}

		
		System.arraycopy(arregloOrdenado, 0, arregloAOrdenar, 0, arregloAOrdenar.length);
	}
	
	private static int[] calcularFrecuenciaDigitos(AristaPesada[] arregloAOrdenar, Integer posicionSiendoOrdenada) {
		int[] frecuenciaDigitos = new int[BASE];

		for (int i = 0; i < arregloAOrdenar.length; i++) {
			int digito = (arregloAOrdenar[i].getPeso() / posicionSiendoOrdenada) % BASE;
			frecuenciaDigitos[digito]++;
		}

		setearDigitosALaPosicionQueOcuparan(frecuenciaDigitos);
		
		return frecuenciaDigitos;
	}

	private static void setearDigitosALaPosicionQueOcuparan(int[] frecuenciaDigitos) {
		for (int i = 1; i < BASE; i++) {			
			frecuenciaDigitos[i] += frecuenciaDigitos[i - 1];
		}
	}
}
