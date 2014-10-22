package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import json.QueryManager;

public class ClientHandler implements Runnable {

	private static String ERROR_DB = "Base de datos no encontrada";

	private Socket socketCliente;
	private boolean esConsulta;
	private int filasActualizadas;
	private Statement query;
	private boolean error;
	private String msgError;
	private ConnectionFactory factory;

	public ClientHandler(Socket cliente) {
		socketCliente = cliente;
		error = false;
	}

	public boolean crearConexion(String db) {
		boolean encontrado = false;
		if (db.equals(ControladorServidor.BD_PERSONAL)) {
			factory = new ConnectionFactory(
					ControladorServidor.ARCHIVO_POSTGRESQL);
			System.out.println("DEBUG: Creada connectionFactory con Postgres!");
			encontrado = true;
		} else if (db.equals(ControladorServidor.BD_FACTURACION)) {
			factory = new ConnectionFactory(
					ControladorServidor.ARCHIVO_FIREBIRD);
			System.out.println("DEBUG: Creada connectionFactory con Firebird!");
			encontrado = true;
		}
		return encontrado;
	}

	public ArrayList<ArrayList<String>> respuestaDeQueryAListas(ResultSet rs) {
		ArrayList<ArrayList<String>> arreglos = null;
		ArrayList<String> arregloColumnas = null;
		ArrayList<String> arregloValores = null;
		try {
			arreglos = new ArrayList<ArrayList<String>>();
			arregloColumnas = new ArrayList<String>();
			arregloValores = new ArrayList<String>();
			ResultSetMetaData metadata = rs.getMetaData();
			int numColumnas = metadata.getColumnCount();
			for (int i = 1; i <= numColumnas; i++) {
				arregloColumnas.add(metadata.getColumnName(i));
			}
			while (rs.next()) {
				int i = 1;
				while (i <= numColumnas) {
					arregloValores.add(rs.getString(i));
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		arreglos.add(arregloColumnas);
		arreglos.add(arregloValores);
		return arreglos;
	}

	public ResultSet enviarConsulta(String consulta) {
		ResultSet resultado = null;
		try {
			query = factory.getConnection().createStatement();
			System.out.println("DEBUG: Consulta: " + consulta);
			esConsulta = query.execute(consulta);
			if (esConsulta) {
				resultado = query.getResultSet();
			} else {
				filasActualizadas = query.getUpdateCount();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			error = true;
			msgError = e.getMessage();
			return resultado;
		}
		return resultado;
	}

	@Override
	public void run() {
		ArrayList<ArrayList<String>> arreglos = null;
		try {
			DataInputStream ois = new DataInputStream(
					socketCliente.getInputStream());
			DataOutputStream oos = new DataOutputStream(
					socketCliente.getOutputStream());
			System.out.println("DEBUG: A punto de leer la consulta!");
			String consultaJSON = ois.readUTF();
			System.out.println("DEBUG: Consulta leida!");
			List<String> listaDeConsulta = QueryManager
					.decodificarQuery(consultaJSON);
			System.out.println("DEBUG: Consulta decodificada!");
			String respuesta;
			if (crearConexion(listaDeConsulta.get(0))) {
				System.out.println("DEBUG: Conexion creada!");
				String consulta = listaDeConsulta.get(1);
				ResultSet resultado = enviarConsulta(consulta);
				if (resultado != null) {
					arreglos = respuestaDeQueryAListas(resultado);
				}
				if (error)
					respuesta = QueryManager.crearError(msgError);
				else if (esConsulta)
					respuesta = QueryManager.crearRespuesta(arreglos.get(0),
							arreglos.get(1));
				else
					respuesta = QueryManager
							.crearRespuestaDeActualizacion(String
									.valueOf(filasActualizadas));
			} else {
				respuesta = QueryManager.crearError(ERROR_DB);
			}
			oos.writeUTF(respuesta);
			ois.close();
			oos.close();
			if (query != null)
				query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
