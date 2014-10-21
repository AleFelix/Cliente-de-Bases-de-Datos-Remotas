package servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import xml.ParseadorDeXML;

public class ConnectionFactory {

	private String servidor;
	private String puerto;
	private String baseDeDatos;
	private String direccion ;
	private String usuario;
	private String password;
	
	public ConnectionFactory(String archivoXML) {
		ParseadorDeXML parser = new ParseadorDeXML();
		parser.parsearXML(archivoXML);
		servidor = parser.obtenerValor(ParseadorDeXML.SERVIDOR);
		puerto = parser.obtenerValor(ParseadorDeXML.PUERTO);
		baseDeDatos = parser.obtenerValor(ParseadorDeXML.BASE_DE_DATOS);
		direccion = parser.obtenerValor(ParseadorDeXML.DIRECCION);
		usuario = parser.obtenerValor(ParseadorDeXML.USUARIO);
		password = parser.obtenerValor(ParseadorDeXML.PASSWORD);
	}

	public Connection getConnection() {
		try {
			return DriverManager.getConnection(direccion, usuario, password);
		} catch (SQLException e) {
			System.out.println("Fallo la conexion con la base de datos");
			e.printStackTrace();
		}
		return null;
	}
}
