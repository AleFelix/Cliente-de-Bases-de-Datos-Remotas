package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import xml.ParseadorDeXML;
import json.QueryManager;

public class ModeloCliente {

	private ControladorCliente controlador;
	private Socket socketConServidor;
	private String servidor;
	private int puerto;

	public ModeloCliente(ControladorCliente c) {
		controlador = c;
		ParseadorDeXML parser = new ParseadorDeXML();
		if (!parser.chequearSiExisteXML(ControladorCliente.ARCHIVO_CONEXION))
			parser.crearXML(ControladorCliente.BACKUP_CONEXION, ControladorCliente.ARCHIVO_CONEXION);
		parser.parsearXML(ControladorCliente.ARCHIVO_CONEXION);
		servidor = parser.obtenerValor(ParseadorDeXML.SERVIDOR);
		puerto = Integer.valueOf(parser.obtenerValor(ParseadorDeXML.PUERTO));
	}

	public void enviarConsulta(String db, String query) {
		String queryJSON = QueryManager.crearQuery(db, query);
		try {
			socketConServidor = new Socket(servidor, puerto);
			DataInputStream ois = new DataInputStream(
					socketConServidor.getInputStream());
			DataOutputStream oos = new DataOutputStream(
					socketConServidor.getOutputStream());
			oos.writeUTF(queryJSON);
			System.out.println("DEBUG: CONSULTA" + queryJSON + "ENVIADA");
			String respuestaJSON = ois.readUTF();
			System.out.println("DEBUG: RESPUESTA" + respuestaJSON + "RECIBIDA");
			List<String> respuestaEnLista;
			if (QueryManager.chequearError(respuestaJSON))
				respuestaEnLista = QueryManager.decodificarError(respuestaJSON);
			else if (QueryManager.chequearActualizacion(respuestaJSON))
				respuestaEnLista = QueryManager
						.decodificarActualizacion(respuestaJSON);
			else
				respuestaEnLista = QueryManager
						.decodificarRespuesta(respuestaJSON);
			controlador.pasarRespuestaALaVista(respuestaEnLista);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cerrarSocket() {
		try {
			socketConServidor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
