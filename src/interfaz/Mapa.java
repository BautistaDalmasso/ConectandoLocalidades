package interfaz;

import java.awt.Color;
import java.awt.EventQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.interfaces.*;

import negocio.*;

public class Mapa {

	private JFrame frame;
	private static JMapViewer mapa;
	private static GrafoCompletoLocalidades grafoCompleto;
	private static GrafoLocalidades arbolMinimo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mapa window = new Mapa();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}


	/**
	 * Create the application.
	 */
	public Mapa() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Mapa de conexiones telefonicas");
		
		cargarGrafoDesdeArchivo("archivoBairesChico");
		// pone mapa en pantalla
		mapa = new JMapViewer();
		setearPosicionYZoom();
		frame.getContentPane().add(mapa);
		
		
		// carga el archivo y construye el grafo completo	
		// FALTA IMPLEMENTAR: Pregunta al usuario por el archivo / localidades
		
		
		// Calcula el árbol generador mínimo y dibuja el grafo resultante
		
		
		grafoCompleto.construirArbolGeneradorMinimo();
		arbolMinimo = grafoCompleto.getArbolGeneradorMinimo();
		dibujarGrafo();
		
		
		// Agrega botones MODIFICAR LOCALIDADES, GUARDAR PROYECTO, SALIR
		// FALTA IMPLEMENTAR **********************************


	}
	
	public static Coordinate getCoordenadas(Localidad loc)
	{
		return new Coordinate(loc.getPosicion().getLatitud(), 
				   loc.getPosicion().getLongitud());
	}
	
	public static void trazarArista(Localidad loc1, Localidad loc2)
	{
		Coordinate uno = getCoordenadas(loc1);
		Coordinate dos = getCoordenadas(loc2);
		
		List<Coordinate> route = new ArrayList<Coordinate>(Arrays.asList(uno, dos, dos));
		mapa.addMapPolygon(new MapPolygonImpl(route));
	}
	
	private void cargarGrafoDesdeArchivo(String listaLocalidades) {
		Archivo zona = new Archivo(listaLocalidades);
		LinkedList<Localidad> pueblos = zona.fetchLocalidades();
		grafoCompleto = new GrafoCompletoLocalidades();	
		for (Localidad loc: pueblos)
		{
			grafoCompleto.agregarLocalidad(loc);
		}	
			
//		System.out.println(zona.toString());
//		System.out.println("Distancia entre pueblo 5 y 11: " + pueblos.get(5).distanciaEnKilometros(pueblos.get(11)));
	}
	
	private void dibujarGrafo()
	{
		Set <Localidad> puntosDelMapa = arbolMinimo.getLocalidades();
		for (Localidad loc: puntosDelMapa)
		{
			pintarPunto(loc);		
			Set <ConexionLocalidades> conexiones = arbolMinimo.obtenerConexiones(loc);		
			for (ConexionLocalidades c: conexiones)
			{
				trazarArista(c.getLocalidadA(), c.getLocalidadB());
				System.out.println("Costo entre " + c.getLocalidadA().getNombre() 
										  + " y " + c.getLocalidadB().getNombre()
										 + " es " + c.getCostoDeLaConexion());
			}
		}
	}

	private void pintarPunto(Localidad loc) {
		MapMarker m = new MapMarkerDot(loc.getNombre(), getCoordenadas(loc));	
		m.getStyle().setBackColor(Color.red);
		m.getStyle().setColor(Color.red);
		mapa.addMapMarker(m);
	}
	
	private void setearPosicionYZoom() {
		Set<Localidad> localidades = grafoCompleto.getLocalidades();
		double latMinima = 89;
		double latMaxima = -89;
		double longMinima = 189;
		double longMaxima = -189;
		for(Localidad localidad: localidades) {
			PosicionGeografica coordenadas = localidad.getPosicion();
			double latActual = coordenadas.getLatitud();
			double longActual = coordenadas.getLongitud();
			if(latActual < latMinima) {
				latMinima = latActual;
			}
			if(latActual > latMaxima) {
				latMaxima = latActual;
			}
			if(longActual < longMinima) {
				longMinima = longActual;
			}
			if(longActual > longMaxima) {
				longMaxima = longActual;
			}
		}
		Coordinate coordenada = new Coordinate((latMinima + latMaxima)/2,(longMinima + longMaxima)/2);
		mapa.setDisplayPosition(coordenada, 6);
	}
}
