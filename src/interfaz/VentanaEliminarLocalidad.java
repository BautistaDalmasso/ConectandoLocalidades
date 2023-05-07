package interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import negocio.Localidad;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

public class VentanaEliminarLocalidad extends JFrame {
	private static final long serialVersionUID = 1432137512881983889L;

	private JPanel contentPane;

	private Mapa mapa;
	private Map<String, Localidad> mapaLocalidadesDisponibles;

	private JComboBox<String> cbLocalidadesAgregadas;
	
	
	public VentanaEliminarLocalidad(Mapa mapa) {
		this.mapa = mapa;
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 116);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setLocation(0, 0);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		popularMapaLocalidadesDisponibles();

		cbLocalidadesAgregadas = new JComboBox<String>();
		cbLocalidadesAgregadas.setBounds(165, 9, 259, 22);
		cbLocalidadesAgregadas.setModel(new DefaultComboBoxModel<String>(localidadesDisponiblesOrdenadas()));
		contentPane.add(cbLocalidadesAgregadas);
		
		JLabel lblSeleccionarLocalidad = new JLabel("Seleccione Una Localidad:");
		lblSeleccionarLocalidad.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccionarLocalidad.setBounds(10, 11, 155, 20);
		contentPane.add(lblSeleccionarLocalidad);
		
		crearBotonEliminar();
	}


	private void crearBotonEliminar() {
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapa.eliminarLocalidad(localidadSeleccionada());
			}
		});
		btnEliminar.setBounds(317, 43, 107, 23);
		contentPane.add(btnEliminar);
	}
	

	private Localidad localidadSeleccionada() {
		String nombreUnicoLocalidad = (String) this.cbLocalidadesAgregadas.getSelectedItem();
		return this.mapaLocalidadesDisponibles.get(nombreUnicoLocalidad);
	}


	private void popularMapaLocalidadesDisponibles() {
		mapaLocalidadesDisponibles = new HashMap<String, Localidad>();
		
		for (Localidad localidad : mapa.getLocalidadesElegidas()) {
			mapaLocalidadesDisponibles.put(localidad.getNombreUnico(), localidad);
		}
	}
	
	private String[] localidadesDisponiblesOrdenadas() {
		String[] ret = new String[mapa.getLocalidadesElegidas().size()];
		
		int i = 0;
		for (Localidad localidad : mapa.getLocalidadesElegidas()) {
			ret[i] = localidad.getNombreUnico();
			i++;
		}
		
		return ret;
	}
	
	public void refrescarVentana() {
		popularMapaLocalidadesDisponibles();
		cbLocalidadesAgregadas.setModel(new DefaultComboBoxModel<String>(localidadesDisponiblesOrdenadas()));
	}
}
