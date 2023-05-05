package interfaz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class Mapa extends JPanel{

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JMapViewer mapa;
	private GrafoCompletoLocalidades grafoCompleto;
	private JPanel panelMapa;
	private JPanel panelControl;
	private JSplitPane panelDivisor;

	private JTextField fieldNombreLocalidad;
	private JTextField fieldProvinciaLocalidad;
	private JTextField fieldLatitud;
	private JTextField fieldLongitud;
	private VentanaElegirLocalidades ventanaElegirLocalidades;
	
	private HashMap<Localidad, MapMarker> puntosDelMapa;

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

	public Mapa() {
		initialize();
	}

	private void initialize()
	{
		grafoCompleto = new GrafoCompletoLocalidades();
		ArchivoLocalidades file = cargarLocalidadesdesdeArchivo();
		
		ventanaElegirLocalidades = new VentanaElegirLocalidades(this);
		ventanaElegirLocalidades.setLocalidadesElegibles(file);
		ventanaElegirLocalidades.launch();
		
		puntosDelMapa = new HashMap<Localidad, MapMarker>();
		
		crearYSetearVentanaPrincipal();
		crearYSetearPanelMapa();
		crearPanelControl();
		crearPanelDivisor(panelControl, panelMapa);	
	}

	private ArchivoLocalidades cargarLocalidadesdesdeArchivo() {
		ArchivoLocalidades a = new ArchivoLocalidades("localidadesArgentinasJSON");
		return a;
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
//		setearPosicionYZoom();
		setearPosInicialMapa();
		mapa.setBounds(0, 0, 500, 500);
		panelMapa.add(mapa, BorderLayout.CENTER);
	}

	private void crearYSetearVentanaPrincipal() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 700);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
		panelControl.add(localidades);
	}


	private void crearComponentesParaAgregarLocalidad(JPanel botones) {
		JButton botonDibujarArbol = new JButton("Dibujar Arbol Generador Minimo");
		botonDibujarArbol.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				borrarMapa();
				grafoCompleto.construirArbolGeneradorMinimo();
				dibujarArbolMinimo();
				setearPosicionYZoom();
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
				setearPosicionYZoom();
			}
		});
		botones.add(botonAgregarLocalidad);
	}

	private void agregarLocalidadIngresada() {
		String nombreLocalidad = fieldNombreLocalidad.getText();
		String provinciaLocalidad = fieldProvinciaLocalidad.getText();
		double latitud = Double.parseDouble(fieldLatitud.getText());
		double longitud = Double.parseDouble(fieldLongitud.getText());
		Localidad nuevaLocalidad = new Localidad(nombreLocalidad, provinciaLocalidad, latitud, longitud);

		agregarLocalidad(nuevaLocalidad);
	}
	
	public boolean agregarLocalidad(Localidad localidad) {
		if (agregarLocalidadAlGrafo(localidad)) {			
			dibujarLocalidad(localidad);
			
			return true;
		}
		return false;
	}
	
	private boolean agregarLocalidadAlGrafo(Localidad localidad) {
		try {
			grafoCompleto.agregarLocalidad(localidad);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	public boolean eliminarLocalidad(Localidad localidad) {
		if (eliminarLocalidadDelGrafo(localidad)) {			
			eliminarLocalidadDelGrafo(localidad);
			borrarMapa();
			dibujarTodasLasLocalidades();
			
			return true;
		}
		return false;
	}
	
	private boolean eliminarLocalidadDelGrafo(Localidad localidad) {
		try {
			grafoCompleto.eliminarLocalidad(localidad);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	public static Coordinate getCoordenadas(Localidad loc)
	{
		return new Coordinate(loc.getPosicion().getLatitud(), 
				   loc.getPosicion().getLongitud());
	}
	
	public void trazarArista(Localidad loc1, Localidad loc2)
	{
		Coordinate uno = getCoordenadas(loc1);
		Coordinate dos = getCoordenadas(loc2);
		
		List<Coordinate> ruta = new ArrayList<Coordinate>(Arrays.asList(uno, dos, dos));
		mapa.addMapPolygon(new MapPolygonImpl(ruta));
	}
	
	private void dibujarArbolMinimo()
	{
		Set <Localidad> puntosDelMapa = grafoCompleto.getArbolGeneradorMinimo().getLocalidades();
		for (Localidad loc: puntosDelMapa)
		{
			dibujarLocalidad(loc);		
			Set <ConexionLocalidades> conexiones = grafoCompleto.getArbolGeneradorMinimo().obtenerConexiones(loc);		
			for (ConexionLocalidades conexion: conexiones)
			{
				trazarArista(conexion);
			}
		}
	}

	private void trazarArista(ConexionLocalidades conexion) {
		Coordinate partida = getCoordenadas(conexion.getLocalidadA());
		Coordinate llegada = getCoordenadas(conexion.getLocalidadB());
		
		List<Coordinate> route = new ArrayList<Coordinate>(Arrays.asList(partida, llegada, llegada));
		mapa.addMapPolygon(new MapPolygonImpl(route));
	}

	private void dibujarTodasLasLocalidades() {
		for (Localidad localidad : grafoCompleto.getLocalidades())  {
			dibujarLocalidad(localidad);
		}
	}
	
	private void dibujarLocalidad(Localidad localidad) {
		MapMarker m = new MapMarkerDot(localidad.getNombre(), getCoordenadas(localidad));	
		m.getStyle().setBackColor(Color.red);
		m.getStyle().setColor(Color.red);
		mapa.addMapMarker(m);
		puntosDelMapa.put(localidad, m);
	}
	
	private void setearPosInicialMapa() {
		Coordinate coordenada = new Coordinate(-34, -64);
		mapa.setDisplayPosition(coordenada, 5);
		
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
		mapa.setDisplayPosition(coordenada, 5);
	}
	
	 public void resetApp()
	 {
		 borrarMapa();
		 ventanaElegirLocalidades.limpiarVentana();
	 }

	private void borrarMapa() {
		mapa.removeAllMapMarkers();
		mapa.removeAllMapPolygons();
		repaint();
	}
}
