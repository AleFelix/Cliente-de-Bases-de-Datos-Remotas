package servidor;

public class ControladorServidor {

	public static final String ARCHIVO_FIREBIRD = "./XML/ConexionFirebird.xml";
	public static final String ARCHIVO_POSTGRESQL = "./XML/ConexionPostgreSQL.xml";
	public static final String BACKUP_FIREBIRD = "/XML/ConexionFirebird.xml";
	public static final String BACKUP_POSTGRESQL = "/XML/ConexionPostgreSQL.xml";
	public static final String BD_PERSONAL = "personal";
	public static final String BD_FACTURACION = "facturacion";

	private Runnable listener;

	public void iniciarConexion() {
		listener = new ClientListener();
		Thread hilo = new Thread(listener);
		hilo.start();
	}
}
