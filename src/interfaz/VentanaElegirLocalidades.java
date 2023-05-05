package interfaz;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;

import negocio.ArchivoLocalidades;
import negocio.Localidad;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.SwingConstants;

import javax.swing.table.JTableHeader;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class VentanaElegirLocalidades extends JFrame{

	private static final long serialVersionUID = 1L;
	private static String[] localidadesDeProvinciaSeleccionada;
	private JFrame ventana;
	private ArchivoLocalidades archivo;
	
	private static JComboBox<String> comboBoxLocalidades;
	private static JTable panelLocalidadesElegidas;
	private static JScrollPane panelScroll;
	private JPanel jpLocElegidas;
	private String dataLocElegidas[][];
	private String colLocElegidas[];
	private JTableHeader encabezado;
	
	private static String provinciaElegida;
	private static JLabel localidadElegida;
	private static String locElegida;
	private static JButton aceptarLocalidad;
	private static JButton borrarLocalidad;
	private static HashMap<String, String> provinciasIATA;
	
	private ArrayList<Localidad> localidadesElegidas;
	
	private static Mapa mapa;
	
	public void launch() 
	{			
		if (archivo==null) throw new IllegalArgumentException(
			"Debe instanciar las localidades (.setLocalidadesElegibles) antes de lanzar.");
		
		cargarCodigosIataProvincias();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {		
					ventana.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
		initialize();
	}
	
	public ArrayList<Localidad> getLocalidadesElegidas()
	{
		return localidadesElegidas;
	}

	public VentanaElegirLocalidades(Mapa mapa)
	{	
		ventana = new JFrame();
		VentanaElegirLocalidades.mapa = mapa;
	}
	
	public void setLocalidadesElegibles(ArchivoLocalidades a)
	{
		archivo = a;
	}
	
	private void initialize() {
		ventana.setBounds(500, 100, 550, 700);
		ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		ventana.setResizable(false);
		ventana.getContentPane().setLayout(null);
		
		setearPanelLocalidadesElegidas();
	
		comboBoxLocalidades = new JComboBox<String>();
		localidadesElegidas = new ArrayList<Localidad>();
		localidadElegida = nuevaJLabel("", 20, 147, 490, 18);
		localidadElegida.setOpaque(true);
		aceptarLocalidad = nuevoJButton("Aceptar", 155, 185, 120, 21);
		aceptarLocalidad.setEnabled(false);
		borrarLocalidad = nuevoJButton("Borrar", 275, 185, 120, 21);
		borrarLocalidad.setEnabled(false);
		
		nuevaJLabel("Ingrese las localidades deseadas", 135, 15, 250, 18);
		nuevaJLabel("Seleccione provincia:", 0, 67, 213, 18);
		nuevaJLabel("Seleccione localidad:", 0, 107, 213, 18);

		JComboBox<String> comboBoxProvincias = new JComboBox<String>();
		String[] p = ordenar(listaProvincias());
		
		comboBoxProvincias.setModel(new DefaultComboBoxModel<String>(p));
		comboBoxProvincias.setBounds(249, 67, 245, 23);
		ventana.getContentPane().add(comboBoxProvincias);
		
		comboBoxProvincias.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarLocalidadElegida();
				borrarLocalidad.setEnabled(false);
				provinciaElegida = (String) comboBoxProvincias.getSelectedItem();
				fetchListaLocalidades(provinciaElegida);
				habilitarBotonLocalidades();
		}
			});
		
		aceptarLocalidad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (agregarLocalidadActual())
				{
					avisarEleccionExitosa();
				} else {
					avisarEleccionRechazada();
				}
			}
		});
		
		borrarLocalidad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (borrarLocalidadActual())
				{
					avisarBorradoExitoso();
				} else {
					avisarBorradoRechazado();
				}
				
				borrarLocalidad.setEnabled(false);
			}
			
		});
	}
	

	private void setearPanelLocalidadesElegidas()
	{
		jpLocElegidas = new JPanel();
		dataLocElegidas = new String[][]{};
		colLocElegidas= new String[] {"Localidad", "Provincia", "Latitud", "Longitud"};
		panelLocalidadesElegidas = new JTable(dataLocElegidas, colLocElegidas);
		panelLocalidadesElegidas.setFont(new java.awt.Font("Tahoma", 0, 10));
		
		encabezado = panelLocalidadesElegidas.getTableHeader();
		encabezado.setBackground(Color.yellow);
		panelScroll = new JScrollPane(panelLocalidadesElegidas);
		panelLocalidadesElegidas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jpLocElegidas.add(panelScroll);
		jpLocElegidas.setBounds(20,230,500,360);
		ventana.add(jpLocElegidas);
	}
	
	private void avisarEleccionExitosa()
	{
		Localidad localidadActual = obtenerLocalidadActual();
		localidadElegida.setText("Usted Eligió: " + localidadActual.getNombre());
		localidadElegida.setBackground(Color.green);
	}
	
	private void avisarEleccionRechazada()
	{
		localidadElegida.setBackground(Color.red);
		localidadElegida.setForeground(Color.white);
		localidadElegida.setText("Localidad ya ingresada");
	}
	
	private void avisarBorradoExitoso()
	{
		Localidad localidadActual = obtenerLocalidadActual();
		localidadElegida.setText(localidadActual.getNombre() + " eliminada de lista");
		localidadElegida.setBackground(Color.green);
	}

	private void avisarBorradoRechazado()
	{
		localidadElegida.setBackground(Color.red);
		localidadElegida.setForeground(Color.white);
		localidadElegida.setText("Localidad no se encontraba en el grafo");
	}
		
	private boolean agregarLocalidadActual()
	{
		Localidad localidadActual = obtenerLocalidadActual();
		
		boolean entroAlGrafo = mapa.agregarLocalidad(localidadActual);
		if (entroAlGrafo)
		{	
			addLocalidadATablaLocalidades(localidadActual);
			return true;
		}

		return false;
	}
	
	private boolean borrarLocalidadActual() {		
		Localidad localidadActual = obtenerLocalidadActual();
		
		boolean localidadEliminada = mapa.eliminarLocalidad(localidadActual);
		if (localidadEliminada) {			
			localidadesElegidas.remove(localidadActual);
			refrescarTablaLocElegidas();
			return true;
		}
		
		return false;
	}
	
	private Localidad obtenerLocalidadActual() {
		HashSet<Localidad> locsProvEspecifica = archivo.getLocalidadesDeUnaProvincia(provinciaElegida);
		
		for (Localidad localidad: locsProvEspecifica) {
			if (localidad.getNombre().equals(locElegida)) {
				return localidad;
			}
		}
		return null;
	}
	
	
	private void cargarCodigosIataProvincias()
	{
		String [] abreviaturasIATA = {"CABA", "BA", "CA", "CH", "CT", "CB", "CR", 
				   "ER", "FO", "JY", "LP", "LR", "MZ", "MI", "NQN", "RN", 
				   "SA", "SJ", "SL", "SC", "SF", "SE", "TF", "TU"};
		String [] provincias = {"Ciudad Autónoma de Buenos Aires", "Buenos Aires", "Catamarca", "Chaco", "Chubut", 
				"Córdoba", "Corrientes", "Entre Ríos", "Formosa", "Jujuy", 
				"La Pampa", "La Rioja", "Mendoza", "Misiones", "Neuquén", 
				"Río Negro", "Salta", "San Juan", "San Luis", "Santa Cruz", 
				"Santa Fe", "Santiago del Estero", 
				"Tierra del Fuego, Antártida e Islas del Atlántico Sur", "Tucumán"};
		provinciasIATA = new HashMap<String, String>();
		for (int i = 0; i < 23; i++)
		{
			provinciasIATA.put(provincias[i], abreviaturasIATA[i]);
		}
	}
	
	private void addLocalidadATablaLocalidades(Localidad localidad)
	{
		localidadesElegidas.add(localidad);
		refrescarTablaLocElegidas();
	}
	
	public void limpiarVentana()
	{
		localidadesElegidas = new ArrayList<Localidad>();
		refrescarTablaLocElegidas();
	}
	
	private void refrescarTablaLocElegidas()
	{
		String data[][] = new String[localidadesElegidas.size()][4];
		String col[] = {"Localidad", "Provincia", "Latitud", "Longitud"};
		
		for (int i = 0; i < localidadesElegidas.size(); i++)
		{
			data[i][0] = localidadesElegidas.get(i).getNombre();
			data[i][1] = localidadesElegidas.get(i).getProvincia();
			data[i][2] = "" + localidadesElegidas.get(i).getPosicion().getLatitud();
			data[i][3] = "" + localidadesElegidas.get(i).getPosicion().getLongitud();
		}
			
		panelLocalidadesElegidas = new JTable(data, col);
		JPanel jpLocElegidas = new JPanel();
		JTableHeader header = panelLocalidadesElegidas.getTableHeader();
		header.setBackground(Color.yellow);
		panelScroll = new JScrollPane(panelLocalidadesElegidas);
		panelLocalidadesElegidas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jpLocElegidas.add(panelScroll);
		jpLocElegidas.setBounds(20,230,500,310);
		ventana.add(jpLocElegidas);
	}

	private void habilitarBotonLocalidades()
	{
		comboBoxLocalidades.setBounds(249, 107, 245, 23);
		comboBoxLocalidades.setModel(new DefaultComboBoxModel<String>(ordenar(localidadesDeProvinciaSeleccionada)));
		ventana.getContentPane().add(comboBoxLocalidades);
		
		comboBoxLocalidades.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String localidadSeleccionada = (String) comboBoxLocalidades.getSelectedItem();
				borrarLocalidad.setEnabled(true);
				habilitarBotonAceptar(localidadSeleccionada);
			}
		});
	}
	
	private void habilitarBotonAceptar(String localidad) {
		locElegida = localidad;
		aceptarLocalidad.setEnabled(true);
		localidadElegida.setHorizontalAlignment(SwingConstants.CENTER);
		localidadElegida.setForeground(Color.black);
		localidadElegida.setBackground(Color.orange);
		localidadElegida.setText(localidad + ", " + provinciaElegida);
	}
	
	private void limpiarLocalidadElegida()
	{
		aceptarLocalidad.setEnabled(false);
		localidadElegida.setBackground(Color.lightGray);
		localidadElegida.setForeground(Color.black);
		localidadElegida.setText("");
	}
	
	private void fetchListaLocalidades(String provincia) {
		HashSet<Localidad> localidades = archivo.getLocalidadesDeUnaProvincia(provincia);
		localidadesDeProvinciaSeleccionada = new String[localidades.size()];
		
		int i = 0;
		for (Localidad localidad: localidades)
		{
			String nuevaLoc = localidad.getNombre();
			if (verificarSiEsNombreRepetido(nuevaLoc)) {
				nuevaLoc = agregarNumeroAlNombre(nuevaLoc);
				System.out.println(nuevaLoc);
			}
			localidadesDeProvinciaSeleccionada[i] = nuevaLoc;
			i++;
		}
	}
	
	private boolean verificarSiEsNombreRepetido(String n)
	{
		return Arrays.stream(localidadesDeProvinciaSeleccionada).anyMatch(n::equals);
	}
	
	private String agregarNumeroAlNombre(String localidad)
	{
		int cont = 1;
		while (Arrays.stream(localidadesDeProvinciaSeleccionada).anyMatch((localidad + "(" + cont + ")")::equals))
		{
			cont++;
		}
		return localidad + "(" + cont + ")";
	}
	
	private static String[] ordenar(String[] lista)
	{
		List<String> localidad = Arrays.asList(lista);
		localidad.sort(null);		
		return localidad.toArray(new String[localidad.size()]);
	}

	private String[] listaProvincias() {
		Set<String> p = archivo.getProvincias();	
		String[] provinciasLista = p.toArray(new String[p.size()]);
		return provinciasLista;
	}
	
	private JLabel nuevaJLabel(String texto, int x, int y, int ancho, int alto)
	{
		JLabel e = new JLabel(texto);
		e.setHorizontalAlignment(SwingConstants.RIGHT);
		e.setFont(new Font("Arial", Font.PLAIN, 15));
		e.setBounds(x, y, ancho, alto);
		ventana.getContentPane().add(e);
		e.setVisible(true);
		return e;
	}
	
	private JButton nuevoJButton(String texto, int x, int y, int ancho, int alto)
	{
		JButton b = new JButton(texto);
		b.setHorizontalAlignment(SwingConstants.CENTER);
		b.setFont(new Font("Arial", Font.PLAIN, 15));
		b.setBounds(x, y, ancho, alto);
		ventana.getContentPane().add(b);
		b.setVisible(true);
		return b;
	}
}

