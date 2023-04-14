package tests;

import org.junit.Assert;
import org.junit.Test;

import negocio.ConexionEntreLocalidades;
import negocio.Localidad;

public class ConexionEntreLocalidadesTest {
	@Test(expected = IllegalArgumentException.class)
	public void localidadAEsNullTest() {
		new ConexionEntreLocalidades(null, new Localidad("La Plata", "Buenos Aires", 0, 0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void localidadBEsNullTest() {
		new ConexionEntreLocalidades(new Localidad("La Plata", "Buenos Aires", 0, 0), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void localidadesSonLasMismasTest() {
		Localidad laPlata = new Localidad("La Plata", "Buenos Aires", 0, 0);

		new ConexionEntreLocalidades(laPlata, laPlata);
	}

	@Test
	public void localidadesEnLaMismaProvinciaAMenosDe300KMTest() {
		Localidad alberti = new Localidad("Alberti", "Buenos Aires", -35.0330734347841, -60.2806197287099);
		Localidad almiranteBrown = new Localidad("Almirante Brown", "Buenos Aires", -34.8044759080477,
				-58.3447825531042);

		ConexionEntreLocalidades conexion = new ConexionEntreLocalidades(alberti, almiranteBrown);

		Integer costoEsperado = (int) Math
				.round((alberti.distanciaEnKilometros(almiranteBrown)) * ConexionEntreLocalidades.COSTO_POR_KILOMETRO);

		Assert.assertEquals(costoEsperado, conexion.getPeso());
	}

	@Test
	public void localidadesEnLaMismaProvinciaAMasDe300KMTest() {
		Localidad alberti = new Localidad("Alberti", "Buenos Aires", -35.0330734347841, -60.2806197287099);
		Localidad invento = new Localidad("invento", "Buenos Aires", 30.0, 60.0);

		ConexionEntreLocalidades conexion = new ConexionEntreLocalidades(alberti, invento);

		Double costoDistancia = alberti.distanciaEnKilometros(invento) * ConexionEntreLocalidades.COSTO_POR_KILOMETRO;
		Integer costoEsperado = (int) Math
				.round(costoDistancia + costoDistancia * ConexionEntreLocalidades.AUMENTO_POR_SUPERAR_300_KM);

		Assert.assertEquals(costoEsperado, conexion.getPeso());
	}

	@Test
	public void localidadesEnDistintasProvinciasAMenosDe300KMTest() {
		Localidad alberti = new Localidad("Alberti", "Buenos Aires", -35.0330734347841, -60.2806197287099);
		Localidad almiranteBrownPampeano = new Localidad("Almirante Brown", "La Pampa", -34.8044759080477,
				-58.3447825531042);

		ConexionEntreLocalidades conexion = new ConexionEntreLocalidades(alberti, almiranteBrownPampeano);

		Double costoDistancia = alberti.distanciaEnKilometros(almiranteBrownPampeano)
				* ConexionEntreLocalidades.COSTO_POR_KILOMETRO;
		Integer costoEsperado = (int) Math
				.round(costoDistancia + ConexionEntreLocalidades.COSTO_POR_INVOLUCRAR_2_PROVINCIAS);

		Assert.assertEquals(costoEsperado, conexion.getPeso());
	}

	@Test
	public void localidadesEnDistintasProvinciasAMasDe300KMTest() {
		Localidad alberti = new Localidad("Alberti", "Buenos Aires", -35.0330734347841, -60.2806197287099);
		Localidad invento = new Localidad("invento", "La Pampa", 30.0, 60.0);

		ConexionEntreLocalidades conexion = new ConexionEntreLocalidades(alberti, invento);

		Double costoDistancia = alberti.distanciaEnKilometros(invento) * ConexionEntreLocalidades.COSTO_POR_KILOMETRO;
		Integer costoEsperado = (int) Math
				.round(costoDistancia + costoDistancia * ConexionEntreLocalidades.AUMENTO_POR_SUPERAR_300_KM
						+ ConexionEntreLocalidades.COSTO_POR_INVOLUCRAR_2_PROVINCIAS);

		Assert.assertEquals(costoEsperado, conexion.getPeso());
	}
}
