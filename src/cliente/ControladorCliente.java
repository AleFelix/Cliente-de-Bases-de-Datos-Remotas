package cliente;

import java.util.List;

public class ControladorCliente {

	VistaCliente v;
	ModeloCliente m;
	
	public ControladorCliente() {
		v = new VistaCliente(this);
		m = new ModeloCliente(this);
		v.menuPrincipal();
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
