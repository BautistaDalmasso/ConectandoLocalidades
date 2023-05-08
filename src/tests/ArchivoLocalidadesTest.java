package tests;

import org.junit.Test;

import negocio.ArchivoLocalidades;

public class ArchivoLocalidadesTest {
	@Test
	public void validarNombreArchivoValidoTest() {
		String nombre = "archivo1";
		ArchivoLocalidades a = new ArchivoLocalidades(nombre);
		a.validarNombreArchivo(nombre);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void validarNombreArchivoInvalidoTest() {
		String nombre = "archivo1<";
		@SuppressWarnings("unused")
		ArchivoLocalidades a = new ArchivoLocalidades(nombre);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void nombreArchivoNuloTest() {
		String nombre = new String();
		@SuppressWarnings("unused")
		ArchivoLocalidades a = new ArchivoLocalidades(nombre);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void nombreArchivoVacioTest() {
		String nombre = "";
		@SuppressWarnings("unused")
		ArchivoLocalidades a = new ArchivoLocalidades(nombre);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void envioCaracteresProhibidosTest() {
		String nombre = "<<**";
		ArchivoLocalidades a = new ArchivoLocalidades(nombre);
		a.validarStrNoCharsProhibidos(nombre);
	}
	
	@Test
	public void envioCaracteresPermitidosTest() {
		String nombre = "dkf543";
		ArchivoLocalidades a = new ArchivoLocalidades(nombre);
		a.validarStrNoCharsProhibidos(nombre);
	}

}
