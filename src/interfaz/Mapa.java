package interfaz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
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
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class Mapa extends JPanel {

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
	private ArchivoLocalidades archivoLocalidades;

	private List<Localidad> localidadesElegidas;
	private VentanaEliminarLocalidad ventanaEliminarLocalidad;
	private JLabel lblMensajeCostoTotal;
	private JLabel lblNumeroCostoTotal;
	private static Localidad localidadElegidaManualmente;

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

	private void initialize() {
		localidadesElegidas = new ArrayList<Localidad>();

		grafoCompleto = new GrafoCompletoLocalidades();
		cargarLocalidadesdesdeArchivo();

		puntosDelMapa = new HashMap<Localidad, MapMarker>();

		crearYSetearVentanaPrincipal();
		crearYSetearPanelMapa();
		crearPanelControl();
		crearPanelDivisor(panelControl, panelMapa);

		ventanaElegirLocalidades = new VentanaElegirLocalidades(this, archivoLocalidades);
		ventanaEliminarLocalidad = new VentanaEliminarLocalidad(this);
	}

	private void cargarLocalidadesdesdeArchivo() {
		archivoLocalidades = new ArchivoLocalidades("localidadesArgentinasJSON");
	}

	private void crearYSetearVentanaPrincipal() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 700);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Mapa de conexiones telefonicas");
		frame.getContentPane().setLayout(new BorderLayout());
	}

	private void crearYSetearPanelMapa() {
		panelMapa = new JPanel();
		panelMapa.setLayout(new BorderLayout());
		mapa = new JMapViewer();
		setearPosInicialMapa();
		mapa.setBounds(0, 0, 500, 500);
		panelMapa.add(mapa, BorderLayout.CENTER);
	}

	private void crearPanelDivisor(JPanel panelControl, JPanel panelMapa) {
		panelDivisor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelControl, panelMapa);
		panelDivisor.setDividerLocation(250);
		panelDivisor.setDividerSize(0);
		frame.getContentPane().add(panelDivisor, BorderLayout.CENTER);
	}

	private void crearPanelControl() {
		panelControl = new JPanel();
		panelControl.setBackground(Color.getHSBColor(233, 18, 97));
		panelControl.setLayout(new FlowLayout(FlowLayout.CENTER));

		crearComponentesPanelDeControl();
	}

	private void crearComponentesPanelDeControl() {
		agregarCamposParaIngresarLocalidad();

		agregarBotonEliminarLocalidad();
		
		agregarBotonLocalidadDesdeArchivo();

		agregarBotonDibujarConexiones();
		
		agregarBotonGuardarLocIngresadas();
		
		agregarBotonCargarLocGuardadas();
		
		agregarZonaCostoTotal();
	}

	private void agregarCamposParaIngresarLocalidad() {
		agregarCampoParaLocalidad();
		agregarCampoParaProvincia();
		agregarCamposParaCoordenadas();

		agregarBotonLocalidadIngresada();
	}

	private void agregarCampoParaLocalidad() {
		JLabel preguntaNombre = new JLabel("Ingrese el nombre de la Localidad");
		panelControl.add(preguntaNombre);
		fieldNombreLocalidad = new JTextField(15);
		panelControl.add(fieldNombreLocalidad);
	}

	private void agregarCampoParaProvincia() {
		JLabel preguntaProv = new JLabel("Ingrese el nombre de la provincia");
		panelControl.add(preguntaProv);
		fieldProvinciaLocalidad = new JTextField(15);
		panelControl.add(fieldProvinciaLocalidad);
	}

	private void agregarCamposParaCoordenadas() {
		JLabel preguntaCoord = new JLabel("Ingrese las coordenadas");
		panelControl.add(preguntaCoord);
		JLabel saltoDeLinea = new JLabel();
		saltoDeLinea.setPreferredSize(new Dimension(1000, 0));
		panelControl.add(saltoDeLinea);

		JLabel latitud = new JLabel("Latitud:");
		panelControl.add(latitud);
		fieldLatitud = new JTextField(4);
		panelControl.add(fieldLatitud);

		JLabel longitud = new JLabel("Longitud:");
		panelControl.add(longitud);
		fieldLongitud = new JTextField(4);
		panelControl.add(fieldLongitud);
	}

	private void agregarBotonLocalidadIngresada() {
		JButton botonAgregarLocalidad = new JButton("Agregar localidad ingresada");
		agregarActionListenerLocalidadIngresada(botonAgregarLocalidad);
		panelControl.add(botonAgregarLocalidad);
	}

	private void agregarActionListenerLocalidadIngresada(JButton botonAgregarLocalidad) {
		botonAgregarLocalidad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (leerLocalidadIngresadaManualmente())
				{
					agregarLocalidad(localidadElegidaManualmente);
					setearPosicionYZoom();
				}	
			}
		});
	}
	
	private void agregarBotonEliminarLocalidad() {
		JButton btnEliminarLocalidad = new JButton("Eliminar localidad");
		
		agregarActionListenerEliminarLocalidad(btnEliminarLocalidad);
		
		panelControl.add(btnEliminarLocalidad);
	}

	private void agregarActionListenerEliminarLocalidad(JButton btnEliminarLocalidad) {
		btnEliminarLocalidad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaEliminarLocalidad.setVisible(true);
				ventanaEliminarLocalidad.toFront();
			}
		});
	}

	private void agregarBotonLocalidadDesdeArchivo() {
		JButton agregarLocalidadDeArchivo = new JButton("Agregar localidad desde archivo");
		agregarActionListenerLocalidadDesdeArchivo(agregarLocalidadDeArchivo);
		panelControl.add(agregarLocalidadDeArchivo);
	}

	private void agregarActionListenerLocalidadDesdeArchivo(JButton agregarLocalidadDeArchivo) {
		agregarLocalidadDeArchivo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaElegirLocalidades.setVisible(true);
				ventanaElegirLocalidades.toFront();
			}
		});
	}
	
	private void agregarBotonDibujarConexiones() {
		JButton botonDibujarArbol = new JButton("Dibujar conexiones óptimas");
		agregarActionListenerDibujarConexiones(botonDibujarArbol);
		panelControl.add(botonDibujarArbol);
	}

	private void agregarActionListenerDibujarConexiones(JButton botonDibujarArbol) {
		botonDibujarArbol.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (puntosDelMapa.size() > 0)
				{
					redibujarConexionesOptimas();
				}
			}
		});
	}
	
	
	private void agregarBotonGuardarLocIngresadas() {
		JButton botonGuardarLocIngresadas = new JButton("Guardar localidades ingresadas");
		agregarActionListenerGuardarLocIngresadas(botonGuardarLocIngresadas);
		panelControl.add(botonGuardarLocIngresadas);
	}

	private void agregarActionListenerGuardarLocIngresadas(JButton botonGuardarLocIngresadas) {
		botonGuardarLocIngresadas.addActionListener(new ActionListener() {
			File selectedFile;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (puntosDelMapa.size() == 0)
				{
					JOptionPane.showMessageDialog(null, "No existen localidades ingresadas!");
				} else {
					
					JFileChooser chooser = new JFileChooser();
					if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					    selectedFile = chooser.getSelectedFile();
					}
					
					archivoLocalidades.guardarEnDisco(selectedFile, localidadesElegidas);
				}		
			}
		});
	}	
	
	
	private void agregarBotonCargarLocGuardadas() {
		JButton botonCargarLocGuardadas = new JButton("Cargar localidades desde archivo");
		agregarActionListenerCargarLocGuardadas(botonCargarLocGuardadas);
		panelControl.add(botonCargarLocGuardadas);
	}

	private void agregarActionListenerCargarLocGuardadas(JButton botonCargarLocGuardadas) {
		botonCargarLocGuardadas.addActionListener(new ActionListener() {
			File selectedFile;
			@Override
			public void actionPerformed(ActionEvent e) {			
					JFileChooser chooser = new JFileChooser();
					if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					    selectedFile = chooser.getSelectedFile();
					}
					else {
						return;
					}
					
					try {
						for (Localidad localidad: archivoLocalidades.cargarDelDisco(selectedFile))
						{
							agregarLocalidad(localidad);
						}
						redibujarConexionesOptimas();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}	
			}
		});
	}	
	

	private void agregarZonaCostoTotal() {
		lblMensajeCostoTotal = new JLabel("Costo total: ");
		panelControl.add(lblMensajeCostoTotal);
		lblMensajeCostoTotal.setVisible(false);
		
		lblNumeroCostoTotal = new JLabel("");
		panelControl.add(lblNumeroCostoTotal);
		lblNumeroCostoTotal.setVisible(false);
	}
	
	private void redibujarConexionesOptimas() {
		borrarMapa();
		grafoCompleto.construirArbolGeneradorMinimo();
		dibujarArbolMinimo();
		setearPosicionYZoom();
		mostrarCostoTotal();
	}

	private void mostrarCostoTotal() {
		lblMensajeCostoTotal.setVisible(true);
		
		lblNumeroCostoTotal.setText(this.grafoCompleto.getCostoTotalConexiones().toString() + "$");
		lblNumeroCostoTotal.setVisible(true);
	}
	
	private boolean leerLocalidadIngresadaManualmente()
	{
		try
		{
			String nombreLocalidad = fieldNombreLocalidad.getText();
			String provinciaLocalidad = fieldProvinciaLocalidad.getText();
			double latitud = Double.parseDouble(fieldLatitud.getText());
			double longitud = Double.parseDouble(fieldLongitud.getText());
			
			localidadElegidaManualmente = new Localidad(nombreLocalidad, provinciaLocalidad, latitud, longitud);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Datos de localidad incorrectos o incompletos!");
			return false;
		}
	}

	public void agregarLocalidad(Localidad localidad) {
		try
		{
			grafoCompleto.agregarLocalidad(localidad);
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Localidad ya ingresada!");
			return;
		}

		localidadesElegidas.add(localidad);

		ventanaElegirLocalidades.refrescarVentana();
		ventanaEliminarLocalidad.refrescarVentana();

		ocultarCostoTotal();
		dibujarLocalidad(localidad);
	}

	public void eliminarLocalidad(Localidad localidad) {
		grafoCompleto.eliminarLocalidad(localidad);

		localidadesElegidas.remove(localidad);

		ventanaElegirLocalidades.refrescarVentana();
		ventanaEliminarLocalidad.refrescarVentana();

		borrarMapa();
		ocultarCostoTotal();
		dibujarTodasLasLocalidades();
	}

	private void ocultarCostoTotal() {
		lblMensajeCostoTotal.setVisible(false);
		lblNumeroCostoTotal.setVisible(false);
	}
	
	public static Coordinate getCoordenadas(Localidad localidad) {
		return new Coordinate(localidad.getPosicion().getLatitud(), localidad.getPosicion().getLongitud());
	}

	public void trazarArista(Localidad loc1, Localidad loc2) {
		Coordinate uno = getCoordenadas(loc1);
		Coordinate dos = getCoordenadas(loc2);

		List<Coordinate> ruta = new ArrayList<Coordinate>(Arrays.asList(uno, dos, dos));
		mapa.addMapPolygon(new MapPolygonImpl(ruta));
	}

	private void dibujarArbolMinimo() {
		Set<Localidad> puntosDelMapa = grafoCompleto.getArbolGeneradorMinimo().getLocalidades();
		for (Localidad localidad : puntosDelMapa) {
			dibujarLocalidadConSusConexiones(localidad);
		}
	}

	private void dibujarLocalidadConSusConexiones(Localidad localidad) {
		dibujarLocalidad(localidad);
		Set<ConexionLocalidades> conexiones = grafoCompleto.getArbolGeneradorMinimo().obtenerConexiones(localidad);
		for (ConexionLocalidades conexion : conexiones) {
			trazarArista(conexion);
		}
	}

	private void trazarArista(ConexionLocalidades conexion) {
		Coordinate partida = getCoordenadas(conexion.getLocalidadA());
		Coordinate llegada = getCoordenadas(conexion.getLocalidadB());

		List<Coordinate> route = new ArrayList<Coordinate>(Arrays.asList(partida, llegada, llegada));
		mapa.addMapPolygon(new MapPolygonImpl(conexion.getCostoDeLaConexion().toString() + "$", route));
	}

	private void dibujarTodasLasLocalidades() {
		for (Localidad localidad : grafoCompleto.getLocalidades()) {
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
		for (Localidad localidad : localidades) {
			PosicionGeografica coordenadas = localidad.getPosicion();
			double latActual = coordenadas.getLatitud();
			double longActual = coordenadas.getLongitud();
			
			latMinima = Math.min(latMinima, latActual);
			latMaxima = Math.max(latMaxima, latActual);
			longMinima = Math.min(longMinima, longActual);
			longMaxima = Math.max(longMaxima, longActual);
		}
		Coordinate coordenada = new Coordinate((latMinima + latMaxima) / 2, (longMinima + longMaxima) / 2);
		mapa.setDisplayPosition(coordenada, 5);
	}

	private void borrarMapa() {
		mapa.removeAllMapMarkers();
		mapa.removeAllMapPolygons();
		repaint();
	}

	public List<Localidad> getLocalidadesElegidas() {
		return localidadesElegidas;
	}
}
