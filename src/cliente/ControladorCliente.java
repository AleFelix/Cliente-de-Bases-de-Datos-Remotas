package cliente;

import java.util.List;

public class ControladorCliente {
	
	public static final String ARCHIVO_CONEXION = "./XML/ConexionCliente.xml";
	public static final String BACKUP_CONEXION = "/XML/ConexionCliente.xml";

	VistaVentanaCliente v;
	ModeloCliente m;

	public ControladorCliente() {
		v = new VistaVentanaCliente(this);
		m = new ModeloCliente(this);
	}

	public void enviarDatos(String db, String query) {
		m.enviarConsulta(db, query);
	}

	public void pasarRespuestaALaVista(List<String> respuesta) {
		v.mostrarRespuesta(respuesta);
	}

	public void avisarCierreDeSocket() {
		m.cerrarSocket();
	}
}
