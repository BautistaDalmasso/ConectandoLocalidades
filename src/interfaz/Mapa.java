package interfaz;

import java.awt.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.interfaces.*;

import negocio.*;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Mapa {

	private JFrame frame;
	private static JMapViewer mapa;
	private static GrafoCompletoLocalidades grafoCompleto;
	private static GrafoLocalidades arbolMinimo;
	private static JPanel panelMapa;
	private static JPanel panelControl;
	private static JSplitPane panelDivisor;

	
	/**
	 * @wbp.nonvisual location=-48,259
	 */
	private final JPopupMenu popupMenu = new JPopupMenu();

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
		cargarGrafoDesdeArchivo("archivoBairesChico");
		crearYSetearVentanaPrincipal();
		crearYSetearPanelMapa();
		crearPanelControl();
		crearPanelDivisor(panelControl, panelMapa);
		
		
		// carga el archivo y construye el grafo completo	
		// FALTA IMPLEMENTAR: Pregunta al usuario por el archivo / localidades
		
		
		// Calcula el árbol generador mínimo y dibuja el grafo resultante
		
		
		grafoCompleto.construirArbolGeneradorMinimo();
		arbolMinimo = grafoCompleto.getArbolGeneradorMinimo();
		dibujarGrafo();
		
		
		// Agrega botones MODIFICAR LOCALIDADES, GUARDAR PROYECTO, SALIR
		// FALTA IMPLEMENTAR **********************************


	}


	private void crearPanelControl() {
		panelControl = new JPanel();
		panelControl.setBackground(Color.BLACK);
		panelControl.setLayout(new FlowLayout(FlowLayout.CENTER));
		crearInstanciaDeAgregadoDeLocalidades(panelControl);
		panelControl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
	}


	private void crearPanelDivisor(JPanel panelControl, JPanel panelMapa) {
		panelDivisor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelControl, panelMapa);
		panelDivisor.setDividerLocation(250);
		panelDivisor.setDividerSize(0);
		frame.getContentPane().add(panelDivisor,BorderLayout.CENTER);
	}


	private void crearYSetearPanelMapa() {
		panelMapa =new JPanel();
		panelMapa.setLayout(new BorderLayout());
		panelMapa.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		mapa = new JMapViewer();
		setearPosicionYZoom();
		mapa.setBounds(0, 0, 500, 500);
		panelMapa.add(mapa, BorderLayout.CENTER);
	}


	private void crearYSetearVentanaPrincipal() {
		frame = new JFrame();
		frame.setBounds(300, 150, 1200, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Mapa de conexiones telefonicas");
		frame.getContentPane().setLayout(new BorderLayout());
	}
	
	private void crearInstanciaDeAgregadoDeLocalidades(JPanel panelControl) {
		JPanel botones = new JPanel();
		botones.setBackground(Color.getHSBColor(233, 18, 97));
		botones.setLayout(new FlowLayout(1,7,15));		
		botones.setPreferredSize(new Dimension(250, 300));
		panelControl.add(botones);
		crearComponentesParaAgregarLocalidad(botones);
		
		JPanel localidades = new JPanel();
		localidades.setBackground(Color.getHSBColor(233, 18, 97));
		localidades.setPreferredSize(new Dimension(250, 350));
		localidades.setLayout(new FlowLayout(1,30,20));
		mostrarLocalidades(localidades);  //acá en realidad se deberian ir agregando
		panelControl.add(localidades);
		
		
	}


	private void crearComponentesParaAgregarLocalidad(JPanel botones) {
		JButton botonDibujarArbol = new JButton("Dibujar Arbol Generador Minimo");
		botones.add(botonDibujarArbol);
		JLabel preguntaNombre = new JLabel("     Ingrese el nombre de la Localidad     ");
		botones.add(preguntaNombre);
		JTextField nombreLoc = new JTextField(15);
		botones.add(nombreLoc);
		JLabel preguntaProv = new JLabel("      Ingrese el nombre de la provincia      ");
		botones.add(preguntaProv);		
		JTextField nombreProv = new JTextField(15);
		botones.add(nombreProv);		
		JLabel preguntaCoord = new JLabel("          Ingrese las coordenadas          ");
		botones.add(preguntaCoord);
		JLabel lat = new JLabel("Latitud:");
		botones.add(lat);
		JTextField latitud = new JTextField(4);
		botones.add(latitud);
		JLabel longi = new JLabel("Latitud:");
		botones.add(longi);
		JTextField longitud = new JTextField(4);
		botones.add(longitud);
		JButton botonAgregarLocalidad = new JButton("     Agregar Localidad     ");
		botones.add(botonAgregarLocalidad);
	}


	private static void mostrarLocalidades(JPanel panel) {
		Set<Localidad> localidades = grafoCompleto.getLocalidades();
		for(Localidad localidad: localidades) {
			JLabel label = new JLabel(localidad.getNombre()+", "+localidad.getProvincia());
			panel.add(label);
		}
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
