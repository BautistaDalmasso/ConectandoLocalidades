package negocio;

import java.io.*;
import java.util.LinkedList;


public class Archivo {
	private String nombre;
	private File listaLocalidades;
	private LinkedList<Localidad> localidades;
	
	private static String localidad, provincia;
	private static double latitud, longitud;

	public Archivo(String nombreArchivo)
	{
		nombre = nombreArchivo + ".txt";
		listaLocalidades = new File(nombre);
		localidades = new LinkedList<Localidad>();
	}
	
	private LinkedList<Localidad> leer()
	{
		try {
			FileReader lectorFile = new FileReader(listaLocalidades);
			BufferedReader lectorBuffer = new BufferedReader(lectorFile);
			
			while (true)
			{
				String linea = lectorBuffer.readLine();
				if (linea != null)
				{
					fetchParametros(linea);
					localidades.add(new Localidad(localidad, provincia, latitud, longitud));
				}
				else
				{
					lectorFile.close();
					lectorBuffer.close();
					break;
				}
			}
			
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
		return localidades;
	}	
	
	public void grabar() {
		
		throw new RuntimeException("grabar(): No terminado de implementar");
		
//		try {
//			FileWriter fw = new FileWriter(nombre);
//			String [] loc = {"Giardino#Córdoba#23#32", "La Falda#Córdoba#22#31"};
//		
//			for (int i = 0; i < loc.length; i++)
//			{
//				fw.write(loc[i] + "\n");
//			}
//			
//			fw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}		
	}

	private static void fetchParametros(String linea)
	{		
		String [] datos = linea.split("#");
		localidad = datos[0];
		provincia = datos[1];
		latitud = Double.parseDouble(datos[2]);
		longitud = Double.parseDouble(datos[3]);	
	}
	
	public LinkedList<Localidad> fetchLocalidades()
	{
		leer();
		return localidades;
	}
	
	@Override
	public String toString()
	{
		String encabezado = lenTexto("Localidad", 25) +
							lenTexto("Provincia", 25) +
							lenTexto("Latitud", 18) +
							lenTexto("Longitud", 18) + "\n";
		
		StringBuilder s = new StringBuilder(encabezado);
		
		for (Localidad l: localidades)
		{
			s.append(l.toString() + "\n");
		}
		
		return s.toString();
	}
	
	public static String lenTexto(String texto, int anchoColumna)
	{
		if (texto.length() > anchoColumna) // trunca texto si se pasa de medida
		{
			texto = texto.substring(0, anchoColumna - 1);
		}
		return texto + " ".repeat(anchoColumna - texto.length());
	}

}
