package fr.ixipios.pccontroler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

public class SocketServer {
	
	private int port = 14120;
	private String host = "127.0.0.1";
	private ServerSocket server = null;
	private boolean isRunning = true;
	public ArrayList<Socket> clients = new ArrayList<Socket>();
	public HashMap<String, Socket> pseudos = new HashMap<String, Socket>();
	private ChatManager chatManager = null;
	public boolean clientConnected = false;
	
	public static boolean debug = true;
	
	//Chat Manager variables
	public static boolean update = false;
	public static String toSend = "sqcsxw";
	public static Socket sender = null;

	public SocketServer(String pHost, int pPort) {
		host = pHost;
		port = pPort;
		try {
			server = new ServerSocket(port, 0, InetAddress.getByName(host));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void open() {
		// Lancement du chat manager
		chatManager = new ChatManager();
//		Thread t1 = new Thread(new ChatManager());
//		t1.start();
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				
				// boucle pour accepter les connexions clientes
				while (isRunning == true) {

					try {
						// accepte connexion client
						Socket client = server.accept();
						System.out.println("Connexion cliente reçue.");
						clients.add(client);
						// lancement d'un thread de traitement de la connexion cliente
						Thread t = new Thread(new ClientProcessor(client));
						t.start();
						clientConnected = true;

					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				// pour toujours fermer le socket à le fin
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
					server = null;
				}
			}
		});

		t.start();
	}
	
	public static void send(String msg, Socket s) {
		System.out.println("DEBUG : DEBUT FONCTION SEND "  + update);
		System.out.println("DEBUG : Client co = " + Main.ts.clientConnected);
		System.out.println("DEBUG : Running = " + Main.ts.isRunning());
		toSend = msg;
		update = true;
		sender = s;
		System.out.println("DEBUG : FIN FONCTION SEND "  + update);
	}

	public void close() {
		isRunning = false;
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	public ChatManager getChatManager() {
		return chatManager;
	}

	public void setChatManager(ChatManager chatManager) {
		this.chatManager = chatManager;
	}
}
