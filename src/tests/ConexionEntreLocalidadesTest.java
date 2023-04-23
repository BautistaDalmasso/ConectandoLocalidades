package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import negocio.ConexionLocalidades;
import negocio.Localidad;

public class ConexionEntreLocalidadesTest {
	Localidad laPlata;
	Localidad alberti;
	
	@Before
	public void setTEst() {
		laPlata = new Localidad("La Plata", "Buenos Aires", 0, 0);
		alberti = new Localidad("Alberti", "Buenos Aires", -35.0330734347841, -60.2806197287099);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void localidadAEsNullTest() {
		new ConexionLocalidades(null, new Localidad("La Plata", "Buenos Aires", 0, 0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void localidadBEsNullTest() {
		new ConexionLocalidades(new Localidad("La Plata", "Buenos Aires", 0, 0), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void localidadesSonLasMismasTest() {
		new ConexionLocalidades(laPlata, laPlata);
	}

	@Test
	public void conexionesDistintasNoSonEqualsTest() {
		ConexionLocalidades a = new ConexionLocalidades(new Localidad("a", "a", 90, 180),
				new Localidad("A", "a", 89, 179));
		ConexionLocalidades b = new ConexionLocalidades(new Localidad("b", "b", -90, -180),
				new Localidad("A", "a", -89, -179));

		Assert.assertFalse(a.equals(b));
	}

	@Test
	public void conexionEntreAyB_equals_ConexionByATest() {
		ConexionLocalidades ab = new ConexionLocalidades(laPlata, alberti);
		ConexionLocalidades ba = new ConexionLocalidades(alberti, laPlata);

		Assert.assertTrue(ab.equals(ba));
	}

	@Test
	public void hashCodeEntreAyB_esIgualA_HashCodeByATest() {
		ConexionLocalidades ab = new ConexionLocalidades(laPlata, alberti);
		ConexionLocalidades ba = new ConexionLocalidades(alberti, laPlata);

		Assert.assertTrue(ab.hashCode() == ba.hashCode());
	}

	@Test
	public void localidadesEnLaMismaProvinciaAMenosDe300KMTest() {
		Localidad almiranteBrown = new Localidad("Almirante Brown", "Buenos Aires", -34.8044759080477,
				-58.3447825531042);

		ConexionLocalidades conexion = new ConexionLocalidades(alberti, almiranteBrown);

		Integer costoEsperado = (int) Math
				.round((alberti.distanciaEnKilometros(almiranteBrown)) * ConexionLocalidades.COSTO_POR_KILOMETRO);

		Assert.assertEquals(costoEsperado, conexion.getPeso());
	}

	@Test
	public void localidadesEnLaMismaProvinciaAMasDe300KMTest() {
		Localidad invento = new Localidad("invento", "Buenos Aires", 30.0, 60.0);

		ConexionLocalidades conexion = new ConexionLocalidades(alberti, invento);

		Double costoDistancia = alberti.distanciaEnKilometros(invento) * ConexionLocalidades.COSTO_POR_KILOMETRO;
		Integer costoEsperado = (int) Math
				.round(costoDistancia + costoDistancia * ConexionLocalidades.AUMENTO_POR_SUPERAR_300_KM);

		Assert.assertEquals(costoEsperado, conexion.getPeso());
	}

	@Test
	public void localidadesEnDistintasProvinciasAMenosDe300KMTest() {
		Localidad almiranteBrownPampeano = new Localidad("Almirante Brown", "La Pampa", -34.8044759080477,
				-58.3447825531042);

		ConexionLocalidades conexion = new ConexionLocalidades(alberti, almiranteBrownPampeano);

		Double costoDistancia = alberti.distanciaEnKilometros(almiranteBrownPampeano)
				* ConexionLocalidades.COSTO_POR_KILOMETRO;
		Integer costoEsperado = (int) Math
				.round(costoDistancia + ConexionLocalidades.COSTO_POR_INVOLUCRAR_2_PROVINCIAS);

		Assert.assertEquals(costoEsperado, conexion.getPeso());
	}

	@Test
	public void localidadesEnDistintasProvinciasAMasDe300KMTest() {
		Localidad invento = new Localidad("invento", "La Pampa", 30.0, 60.0);

		ConexionLocalidades conexion = new ConexionLocalidades(alberti, invento);

		Double costoDistancia = alberti.distanciaEnKilometros(invento) * ConexionLocalidades.COSTO_POR_KILOMETRO;
		Integer costoEsperado = (int) Math
				.round(costoDistancia + costoDistancia * ConexionLocalidades.AUMENTO_POR_SUPERAR_300_KM
						+ ConexionLocalidades.COSTO_POR_INVOLUCRAR_2_PROVINCIAS);

		Assert.assertEquals(costoEsperado, conexion.getPeso());
	}
}
