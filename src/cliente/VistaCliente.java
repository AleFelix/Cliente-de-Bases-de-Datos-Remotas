package cliente;

import java.util.List;
import java.util.Scanner;

import json.QueryManager;

public class VistaCliente {

	private Scanner entrada;
	private ControladorCliente controlador;

	public VistaCliente(ControladorCliente c) {
		controlador = c;
		entrada = new Scanner(System.in);
	}

	public void menuPrincipal() {
		mostrar("Bienvenido al sistema de administracion remota de Bases de datos");
		linea();
		mostrar("Por favor, ingrese el nombre de la base de datos a consultar");
		String db = entrada.nextLine();
		while (db == null) {
			mostrar("No se ha ingresado el nombre de la base de datos, intentelo nuevamente");
			db = entrada.nextLine();
		}
		mostrar("Por favor, ingrese la consulta a realizar a la base de datos");
		String query = entrada.nextLine();
		while (!chequearQuery(query)) {
			mostrar("Consulta invalida, por favor intentelo de nuevo");
			query = entrada.nextLine();
		}
		mostrar("Datos obtenidos con exito, espere por favor...");
		controlador.enviarDatos(db, query);
	}

	public boolean chequearQuery(String sql) {
		boolean resultado = false;
		int indice = sql.indexOf(' ');
		String tipo = sql.substring(0, indice).toLowerCase();
		if (tipo.equals("select") || tipo.equals("insert")
				|| tipo.equals("update") || tipo.equals("delete"))
			resultado = true;
		return resultado;
	}

	public void linea() {
		System.out
				.println("-----------------------------------------------------------------");
	}

	public void mostrar(Object o) {
		System.out.println(o);
	}

	public void mostrarRespuesta(List<String> respuesta) {
		if (respuesta == null)
			mostrar("Respuesta nula, hubo un error en el procesamiento de la consulta");
		else if (respuesta.get(0).equals("0"))
			mostrar("No se ha obtenido ninguna tupla");
		else if (respuesta.get(0).equals(QueryManager.ERROR)) {
			System.out.println("La consulta ha retornado el siguiente error:");
			System.out.println(respuesta.get(1));
		} else if (respuesta.get(0).equals(QueryManager.CANTFILAS))
			if (respuesta.get(1).equals("0"))
				System.out
						.println("La consulta no ha actualizado ninguna fila");
			else
				System.out.println("La consulta ha actualizado "
						+ respuesta.get(1) + " filas");
		else {
			int cantColumnas = Integer.valueOf((String) respuesta.get(0));
			int cantFilas = (respuesta.size() - 1) / cantColumnas;
			System.out
					.println("DEBUG: Tamaño de respuesta:" + respuesta.size());
			System.out.println("DEBUG: NºColumnas:" + cantColumnas
					+ " NºFilas:" + cantFilas);
			for (int i = 0; i < cantFilas; i++) {
				for (int j = 1; j <= cantColumnas; j++) {
					System.out.print(respuesta.get(j + (i * cantColumnas))
							+ "   ");
				}
				System.out.println("");
			}
		}
		controlador.avisarCierreDeSocket();
		mostrar("Proceso finalizado.");
	}
}
