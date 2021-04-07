package fr.ixipios.pccontroler;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

public class ClientProcessor implements Runnable {

	private Socket sock;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;

	public ClientProcessor(Socket pSock) {
		sock = pSock;
	}

	public void run() {
		System.err.println("Lancement du traitement de la connexion du client");

		boolean closeConnexion = false;
		String clientName = "Anonyme";
		try {
			writer = new PrintWriter(sock.getOutputStream());
			reader = new BufferedInputStream(sock.getInputStream());
			clientName = read();
			Main.ts.pseudos.put(clientName, sock);
			System.out.println("Connexion au serveur de : " + clientName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (!sock.isClosed()) {
			try {
				String response = read();

				if (response.startsWith("/")) {
					String[] params = response.substring(1).split(" ");
					//debug(Arrays.toString(params));
					switch (params[0]) {
					case "stop":
						write("stopCo");
						closeConnexion = true;
						break;
					case "mp":
						if(params.length > 2) {
							String[] msg = Arrays.copyOfRange(params, 2, params.length);
							Main.ts.getChatManager().sendTo(String.join(" ", msg), clientName, Main.ts.pseudos.get(params[1]));
						}
						else {
							error("Pas assez d'arguments. Il en faut 2.");
						}
						break;
//				case "shutdown13795":
//					try {
//						Process process = Runtime.getRuntime().exec("cmd /c shutdown /s");
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					break;
					default:
						write("err Commande inconnu");
						break;
					}
				} else {
					System.out.println("CHAT : " + clientName + " : " + response);
					Main.ts.getChatManager().send(response, sock, clientName);
					write("received");
				}
				if (closeConnexion) {
					System.err.println("Commande stop reçu de " + clientName + " : arret de la connexion");
					Main.ts.clients.remove(sock);
					writer = null;
					reader = null;
					sock.close();
				}
			} catch (SocketException e) {
				if (!closeConnexion) {
					System.err.println("CONNEXION AVEC " + clientName + " INTERROMPUE !");
				}
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String read() throws IOException {
		String response = "";
		int stream;
		byte[] b = new byte[4096];
		stream = reader.read(b);
		response = new String(b, 0, stream);
		return response;
	}

	public void write(String msg) {
		writer.write(msg);
		writer.flush();
	}
	
	private void debug(String txt) {
		if(SocketServer.debug) {
			System.out.println("DEBUG : " + txt);
		}
	}
	
	public void error(String txt) {
		write("err " + txt);
	}
}