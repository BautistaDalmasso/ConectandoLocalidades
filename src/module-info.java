/**
 * 
 */
/**
 * @author Bautista
 *
 */
module ConectandoLocalidades {
	requires junit;
	requires java.desktop;
	requires java.sql;
	requires JMapViewer;
	requires gson;

	opens negocio to gson;
}