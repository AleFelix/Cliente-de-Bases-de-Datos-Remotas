package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientListener implements Runnable {

	@Override
	public void run() {
		try {
			@SuppressWarnings("resource")
			ServerSocket socketServidor = new ServerSocket(1111);
			while (true) {
				Socket socketCliente = socketServidor.accept();
				System.out.println("DEBUG: Cliente encontrado!");
				Runnable handler = new ClientHandler(socketCliente);
		        Thread hilo = new Thread(handler);
		        hilo.start();
		        System.out.println("DEBUG: Hilo de cliente iniciado!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
