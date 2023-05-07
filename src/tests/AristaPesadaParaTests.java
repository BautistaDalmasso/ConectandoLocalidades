package tests;

import java.util.Objects;

import negocio.AristaPesada;

public class AristaPesadaParaTests implements AristaPesada, Comparable<AristaPesadaParaTests>{
	private Integer peso;
	
	public AristaPesadaParaTests(Integer peso) {
		this.peso = peso;
	}
	
	@Override
	public Integer getPeso() {
		return peso;
	}

	@Override
	public int hashCode() {
		return Objects.hash(peso);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AristaPesadaParaTests other = (AristaPesadaParaTests) obj;
		return Objects.equals(peso, other.peso);
	}

	@Override
	public int compareTo(AristaPesadaParaTests o) {
		return peso.compareTo(o.peso);
	}
	
	@Override
	public String toString() {
		return Integer.toString(peso);
	}
}
