package main;

import servidor.ControladorServidor;
import cliente.ControladorCliente;

public class Main {
	
	public static String MODO_CLIENTE = "c";
	public static String MODO_SERVIDOR = "s";

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Argumentos insuficientes para ejecutar la aplicación.");
		} else {
			if (args[0].toLowerCase().equals(MODO_CLIENTE)) {
				ControladorCliente c = new ControladorCliente();
			}
			else if (args[0].toLowerCase().equals(MODO_SERVIDOR)) {
				ControladorServidor s = new ControladorServidor();
				s.iniciarConexion();
			}
			else {
				System.out.println("Argumento erroneo, ingrese 'c' para cliente o 's' para servidor.");
			}
		}
	}

}
