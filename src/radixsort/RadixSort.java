package radixsort;

public class RadixSort {
	private static final int RANGO = 10; // Base 10.

	private Integer[] arregloAOrdenar;
	private Integer valorMaximo;

	public RadixSort(Integer[] arregloAOrdenar, Integer valorMaximo) {
		this.arregloAOrdenar = arregloAOrdenar;
		this.valorMaximo = valorMaximo;
	}

	static void countSort(Integer arregloAOrdenar[], Integer posicionSiendoOrdenada) {
		Integer arregloOrdenado[] = new Integer[arregloAOrdenar.length];
		int[] frecuenciaDigitos = calcularFrecuenciaDigitos(arregloAOrdenar, posicionSiendoOrdenada);

		for (int i = arregloAOrdenar.length - 1; i >= 0; i--) {
			int digito = (arregloAOrdenar[i] / posicionSiendoOrdenada) % RANGO;
			arregloOrdenado[frecuenciaDigitos[digito] - 1] = arregloAOrdenar[i];
			frecuenciaDigitos[digito]--;
		}

		
		System.arraycopy(arregloOrdenado, 0, arregloAOrdenar, 0, arregloAOrdenar.length);
	}

	private static int[] calcularFrecuenciaDigitos(Integer[] arregloAOrdenar, Integer posicionSiendoOrdenada) {
		int[] frecuenciaDigitos = new int[RANGO];

		for (int i = 0; i < arregloAOrdenar.length; i++) {
			int digito = (arregloAOrdenar[i] / posicionSiendoOrdenada) % RANGO;
			frecuenciaDigitos[digito]++;
		}

		for (int i = 1; i < RANGO; i++) {			
			frecuenciaDigitos[i] += frecuenciaDigitos[i - 1];
		}
		
		return frecuenciaDigitos;
	}

	public void ordenar() {
		int auxValorMaximo = valorMaximo;

		for (int posicionSiendoOrdenada = 1; auxValorMaximo / posicionSiendoOrdenada > 0; posicionSiendoOrdenada *= RANGO)
			countSort(arregloAOrdenar, posicionSiendoOrdenada);
	}
}
