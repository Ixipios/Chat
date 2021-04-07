package fr.ixipios.pccontroler;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerConnexion implements Runnable {

	private Socket connexion = null;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	private String pseudo = "";
	private boolean debug = true;

	public ServerConnexion(String host, int port, String pseudo) {
		this.pseudo = pseudo;
		try {
			connexion = new Socket(host, port);
			run();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		Scanner sc = new Scanner(System.in);
		try {
			writer = new PrintWriter(connexion.getOutputStream(), true);
			reader = new BufferedInputStream(connexion.getInputStream());

			// envoi du pseudo au serveur
			write(pseudo);
			debug("Pseudo : " + pseudo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String commande = "";
		// nouveau thread pour lire les envois du serveur
		Thread t = new Thread(new Reader(reader));
		t.start();

		// boucle pour envoyer les commandes au serveur
		while (!commande.equalsIgnoreCase("stop")) {
			// lecture de la commande
			commande = sc.nextLine();
			
			// regarde si c'est une commande à envoyer au serveur ou pas
			if (commande.startsWith("!")) {
				String[] params = commande.substring(1).split(" ");
				// switch pour exécuter la commande
				switch (params[0]) {
				case "debug":
					debug = Boolean.parseBoolean(params[1]);
					print("messages de debug masqués");
					break;
				default:
					print("Commande interne inconnue");
					break;
				}
				continue;
			}
			
			// envoi au serveur de la commande ou du msg chat
			write(commande);
			debug("Commande envoyé au serveur : " + commande);

			if (commande.equalsIgnoreCase("stop")) {
				break;
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

	private void write(String cmd) {
		// envoi de la commande
		writer.write(cmd);
		writer.flush();
	}

	private void debug(String txt) {
		if (debug) {
			System.out.println("DEBUG : " + txt);
		}
	}
	
	private void print(String txt) {
		System.out.println("Programme : " + txt);
	}
}