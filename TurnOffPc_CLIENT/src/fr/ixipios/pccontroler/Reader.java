package fr.ixipios.pccontroler;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Reader implements Runnable {

	private BufferedInputStream reader = null;
	private boolean stopConnexion = false;

	public Reader(BufferedInputStream reader) {
		this.reader = reader;
	}

	public void run() {
		while (!stopConnexion) {
			try {
				String servMsg = read();
				String[] params = servMsg.split(" ");
				//System.out.println("DEBUG : " + servMsg);
				// System.out.println("Message du serveur: " + msg);
				switch (servMsg.split(" ")[0]) {
				case "StopCo":
					stopConnexion = true;
					break;
				case "chatUpdate":
					String[] msg = Arrays.copyOfRange(params, 2, params.length);
					System.out.println(params[1] + " : " + String.join(" ", msg));
					break;
				case "err":
					String[] err = Arrays.copyOfRange(params, 1, params.length);
					print("ERREUR : " + String.join(" ", err));
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String read() throws IOException {
		String response = "";
		int stream;
		byte[] b = new byte[4096];
		stream = reader.read(b);
		response = new String(b, 0, stream);
		return response;
	}
	
	private void print(String txt) {
		System.out.println("SERVER : " + txt);
	}
}
