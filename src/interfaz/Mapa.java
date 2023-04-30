package interfaz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class Mapa {

	private JFrame frame;
	private JMapViewer mapa;
	private GrafoCompletoLocalidades grafoCompleto;
	private GrafoLocalidades arbolMinimo;
	private JPanel panelMapa;
	private JPanel panelControl;
	private JSplitPane panelDivisor;

	private JTextField fieldNombreLocalidad;
	private JTextField fieldProvinciaLocalidad;
	private JTextField fieldLatitud;
	private JTextField fieldLongitud;
	
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
		arbolMinimo = grafoCompleto.getArbolGeneradorMinimo();
		
		pintarLocalidades();
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
		botonDibujarArbol.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapa.removeAllMapMarkers();
				mapa.removeAllMapPolygons();
				mapa.repaint();
				grafoCompleto.construirArbolGeneradorMinimo();
				dibujarGrafo();
			}
		});
		botones.add(botonDibujarArbol);
		
		JLabel preguntaNombre = new JLabel("     Ingrese el nombre de la Localidad     ");
		botones.add(preguntaNombre);
		fieldNombreLocalidad = new JTextField(15);
		botones.add(fieldNombreLocalidad);

		JLabel preguntaProv = new JLabel("      Ingrese el nombre de la provincia      ");
		botones.add(preguntaProv);		
		fieldProvinciaLocalidad = new JTextField(15);
		botones.add(fieldProvinciaLocalidad);
		
		JLabel preguntaCoord = new JLabel("          Ingrese las coordenadas          ");
		botones.add(preguntaCoord);
		
		JLabel lat = new JLabel("Latitud:");
		botones.add(lat);
		fieldLatitud = new JTextField(4);
		botones.add(fieldLatitud);
		
		JLabel longi = new JLabel("Longitud:");
		botones.add(longi);
		fieldLongitud = new JTextField(4);
		botones.add(fieldLongitud);
		
		JButton botonAgregarLocalidad = new JButton("     Agregar Localidad     ");
		botonAgregarLocalidad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarLocalidadIngresada();
			}
		});
		botones.add(botonAgregarLocalidad);
	}

	private void agregarLocalidadIngresada() {
		String nombreLocalidad = fieldNombreLocalidad.getText();
		String provinciaLocaliadad = fieldProvinciaLocalidad.getText();
		double latitud = Double.parseDouble(fieldLatitud.getText());
		double longitud = Double.parseDouble(fieldLongitud.getText());
		Localidad nuevaLocalidad = new Localidad(nombreLocalidad, provinciaLocaliadad, latitud, longitud);
		
		grafoCompleto.agregarLocalidad(nuevaLocalidad);
		pintarPunto(nuevaLocalidad);
	}
	
	private void mostrarLocalidades(JPanel panel) {
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
	
	// TODO: mover al negocio.
	private void cargarGrafoDesdeArchivo(String listaLocalidades) {
		Archivo zona = new Archivo(listaLocalidades);
		LinkedList<Localidad> pueblos = zona.fetchLocalidades();
		grafoCompleto = new GrafoCompletoLocalidades();	
		for (Localidad loc: pueblos)
		{
			grafoCompleto.agregarLocalidad(loc);
		}	
	}
	
	private void pintarLocalidades() {
		Set <Localidad> puntosDelMapa = arbolMinimo.getLocalidades();
		for (Localidad loc: puntosDelMapa)
		{
			pintarPunto(loc);
		}
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
			}
		}
	}
	
	private void pintarPunto(Localidad loc) {
		MapMarker m = new MapMarkerDot(loc.getNombre(), getCoordenadas(loc));	
		m.getStyle().setBackColor(Color.red);
		m.getStyle().setColor(Color.red);
		mapa.addMapMarker(m);
	}

	public void trazarArista(Localidad loc1, Localidad loc2)
	{
		Coordinate uno = getCoordenadas(loc1);
		Coordinate dos = getCoordenadas(loc2);
		
		List<Coordinate> route = new ArrayList<Coordinate>(Arrays.asList(uno, dos, dos));
		mapa.addMapPolygon(new MapPolygonImpl(route));
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
