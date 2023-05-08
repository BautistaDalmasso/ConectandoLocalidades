package interfaz;

import java.awt.Color;

import javax.swing.JFrame;

import negocio.ArchivoLocalidades;
import negocio.Localidad;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingConstants;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentanaElegirLocalidades extends JFrame {

	private static final long serialVersionUID = 1L;
	private String[] localidadesDeProvinciaSeleccionada;
	private ArchivoLocalidades archivo;

	private JComboBox<String> comboBoxLocalidades;
	private JTable tablaLocalidadesElegidas;
	private JScrollPane panelScroll;
	private JPanel jpLocalidadesElegidas;
	private String filasLocalidadesElegidas[][];
	private String columnasLocalidadesElegidas[];

	private JLabel resultadoLocalidadElegida;
	private String nombreProvinciaElegida;
	private String nombreLocalidadElegida;

	private JButton aceptarLocalidad;

	private Mapa mapa;

	public VentanaElegirLocalidades(Mapa mapa, ArchivoLocalidades archivo) {
		this.mapa = mapa;
		this.archivo = archivo;
		initialize();
	}

	private void initialize() {
		inicializarDimensionesFrame();

		inicializarPanelLocalidadesElegidas();

		crearSeleccionDeLocalidades();

		crearBotonAceptarLocalidad();
	}

	private void inicializarDimensionesFrame() {
		setBounds(500, 100, 550, 700);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		setLocation(0, 0);
	}


	private void inicializarPanelLocalidadesElegidas() {
		filasLocalidadesElegidas = new String[][] {};

		setearPanelLocalidadesElegidas();
	}

	private void crearSeleccionDeLocalidades() {

		nuevaJLabel("Ingrese las localidades deseadas", 135, 15, 250, 18);

		crearComboBoxProvincias();
		crearComboBoxLocalidades();

		crearLabelResultado();
	}

	private void crearComboBoxProvincias() {
		nuevaJLabel("Seleccione provincia:", 0, 67, 213, 18);

		JComboBox<String> comboBoxProvincias = new JComboBox<String>();

		comboBoxProvincias.setModel(new DefaultComboBoxModel<String>(getProvinciasOrdenadas()));

		comboBoxProvincias.setBounds(249, 67, 245, 23);
		getContentPane().add(comboBoxProvincias);

		agregarActionListenerCBProvincias(comboBoxProvincias);
	}

	private void agregarActionListenerCBProvincias(JComboBox<String> comboBoxProvincias) {

		comboBoxProvincias.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarLocalidadElegida();
				nombreProvinciaElegida = (String) comboBoxProvincias.getSelectedItem();
				fetchListaLocalidades(nombreProvinciaElegida);
				habilitarBotonLocalidades();
				habilitarBotonAceptar();
			}
		});
	}

	private void crearComboBoxLocalidades() {
		nuevaJLabel("Seleccione localidad:", 0, 107, 213, 18);
		comboBoxLocalidades = new JComboBox<String>();
	}

	private void crearLabelResultado() {
		resultadoLocalidadElegida = nuevaJLabel("", 20, 147, 490, 18);
		resultadoLocalidadElegida.setOpaque(true);
	}

	private void crearBotonAceptarLocalidad() {
		aceptarLocalidad = nuevoJButton("Aceptar", 215, 185, 120, 21);
		aceptarLocalidad.setEnabled(false);
		agregarActionListenerAceptarLocalidad();
	}

	private void agregarActionListenerAceptarLocalidad() {
		aceptarLocalidad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (agregarLocalidadActual()) {
					avisarEleccionExitosa();
				} else {
					avisarEleccionRechazada();
				}
			}
		});
	}

	private void setearPanelLocalidadesElegidas() {
		jpLocalidadesElegidas = new JPanel();
		columnasLocalidadesElegidas = new String[] { "Localidad", "Provincia", "Latitud", "Longitud" };

		tablaLocalidadesElegidas = new JTable(filasLocalidadesElegidas, columnasLocalidadesElegidas);
		tablaLocalidadesElegidas.setFont(new java.awt.Font("Tahoma", 0, 10));
		tablaLocalidadesElegidas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		tablaLocalidadesElegidas.getTableHeader().setBackground(Color.yellow);

		panelScroll = new JScrollPane(tablaLocalidadesElegidas);
		jpLocalidadesElegidas.add(panelScroll);
		jpLocalidadesElegidas.setBounds(20, 230, 500, 360);
		add(jpLocalidadesElegidas);
	}

	private void avisarEleccionExitosa() {
		Localidad localidadActual = obtenerLocalidadActual();
		resultadoLocalidadElegida.setBackground(Color.green);
		resultadoLocalidadElegida.setForeground(Color.black);
		resultadoLocalidadElegida.setText("Usted Eligió: " + localidadActual.getNombre());
	}

	private void avisarEleccionRechazada() {
		resultadoLocalidadElegida.setBackground(Color.red);
		resultadoLocalidadElegida.setForeground(Color.white);
		resultadoLocalidadElegida.setText("Localidad ya ingresada");
	}

	private boolean agregarLocalidadActual() {
		Localidad localidadActual = obtenerLocalidadActual();

		try {
			mapa.agregarLocalidad(localidadActual);
			addLocalidadATablaLocalidades(localidadActual);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	private Localidad obtenerLocalidadActual() {
		HashSet<Localidad> locsProvEspecifica = archivo.getLocalidadesDeUnaProvincia(nombreProvinciaElegida);

		for (Localidad localidad : locsProvEspecifica) {
			if (localidad.getNombre().equals(nombreLocalidadElegida)) {
				return localidad;
			}
		}
		return null;
	}

	private void addLocalidadATablaLocalidades(Localidad localidad) {
		refrescarTablaLocalidadesElegidas();
	}

	public void refrescarVentana() {
		refrescarTablaLocalidadesElegidas();
	}

	private void refrescarTablaLocalidadesElegidas() {
		filasLocalidadesElegidas = new String[mapa.getLocalidadesElegidas().size()][4];

		popularTablaLocalidades();

		setearPanelLocalidadesElegidas();
	}

	private void popularTablaLocalidades() {
		int fila = 0;
		for (Localidad localidad : mapa.getLocalidadesElegidas()) {
			popularFilaLocalidad(fila, localidad);
			fila++;
		}
	}

	private void popularFilaLocalidad(int fila, Localidad localidad) {
		filasLocalidadesElegidas[fila][0] = localidad.getNombre();
		filasLocalidadesElegidas[fila][1] = localidad.getProvincia();
		filasLocalidadesElegidas[fila][2] = "" + localidad.getPosicion().getLatitud();
		filasLocalidadesElegidas[fila][3] = "" + localidad.getPosicion().getLongitud();
	}

	private void habilitarBotonLocalidades() {
		comboBoxLocalidades.setBounds(249, 107, 245, 23);
		Arrays.sort(localidadesDeProvinciaSeleccionada);
		comboBoxLocalidades.setModel(new DefaultComboBoxModel<String>(localidadesDeProvinciaSeleccionada));
		getContentPane().add(comboBoxLocalidades);

		comboBoxLocalidades.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				habilitarBotonAceptar();
			}
		});
	}

	private void habilitarBotonAceptar() {
		nombreLocalidadElegida = (String) comboBoxLocalidades.getSelectedItem();
		aceptarLocalidad.setEnabled(true);

		resultadoLocalidadElegida.setHorizontalAlignment(SwingConstants.CENTER);
		resultadoLocalidadElegida.setForeground(Color.black);
		resultadoLocalidadElegida.setBackground(Color.orange);
		resultadoLocalidadElegida.setText(nombreLocalidadElegida + ", " + nombreProvinciaElegida);
	}

	private void limpiarLocalidadElegida() {
		aceptarLocalidad.setEnabled(false);
		resultadoLocalidadElegida.setBackground(Color.lightGray);
		resultadoLocalidadElegida.setForeground(Color.black);
		resultadoLocalidadElegida.setText("");
	}

	private void fetchListaLocalidades(String provincia) {
		HashSet<Localidad> localidades = archivo.getLocalidadesDeUnaProvincia(provincia);
		localidadesDeProvinciaSeleccionada = new String[localidades.size()];

		int i = 0;
		for (Localidad localidad : localidades) {
			localidadesDeProvinciaSeleccionada[i] = localidad.getNombre();
			i++;
		}
	}

	private String[] getProvinciasOrdenadas() {
		Set<String> p = archivo.getProvincias();
		String[] provinciasLista = archivo.getProvincias().toArray(new String[p.size()]);
		Arrays.sort(provinciasLista);
		return provinciasLista;
	}

	private JLabel nuevaJLabel(String texto, int x, int y, int ancho, int alto) {
		JLabel e = new JLabel(texto);
		e.setHorizontalAlignment(SwingConstants.RIGHT);
		e.setFont(new Font("Arial", Font.PLAIN, 15));
		e.setBounds(x, y, ancho, alto);
		getContentPane().add(e);
		return e;
	}

	private JButton nuevoJButton(String texto, int x, int y, int ancho, int alto) {
		JButton b = new JButton(texto);
		b.setHorizontalAlignment(SwingConstants.CENTER);
		b.setFont(new Font("Arial", Font.PLAIN, 15));
		b.setBounds(x, y, ancho, alto);
		getContentPane().add(b);
		return b;
	}
}
