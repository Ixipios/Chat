package fr.ixipios.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatManager {

	private boolean update = true;
//	private String toSend = "sqcsxw";
	private PrintWriter writer = null;
//	private Socket sender = null;
	private String cmdSend = "chatUpdate";

//	public ChatManager() {
//	}
	
//	public void run() {
//		while(true) {
//			if(SocketServer.update == true) {
//				System.out.println("DEBUG : UPDATE CHAT EN COURS " + SocketServer.update);
//				SocketServer.update = false;
//			}
//		}
//		while(true) {
//			if(SocketServer.update == true) {
//				System.out.println("DEBUG : UPDATE CHAT EN COURS " + SocketServer.update);
//				for (Socket sock : Main.ts.clients) {
//					if (sock != SocketServer.sender) {
//						try {
//							writer = new PrintWriter(sock.getOutputStream());
//							write(cmdSend + " " + SocketServer.toSend);
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//				SocketServer.update = false;
//				System.out.println("DEBUG : FIN UPDATE " + SocketServer.update);
//			}
//		}
//		System.out.println("DEBUG : CHAT MANAGER FIN : Running = " + Main.ts.isRunning());
//	}

	// Envoi d'un message à tous le monde
	public void send(String toSend, Socket sender, String pseudo) {
		for (Socket sock : Main.ts.clients) {
			if (sock != sender) {
				try {
					writer = new PrintWriter(sock.getOutputStream());
					write(cmdSend + " " + pseudo + " " + toSend);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		writer = null;
	}
	
	// MP : envoi d'un message à une personne en particulier
	public void sendTo(String toSend, String pseudo, Socket receiver) {
		try {
			writer = new PrintWriter(receiver.getOutputStream());
			// envoi le pseudo et le message de l'envoyeur au destinataire (receiver)
			write(cmdSend + " " + pseudo + " " + toSend);
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer = null;
	}

	public void write(String msg) {
		writer.write(msg);
		writer.flush();
	}
	
	public void debug(String msg) {
		System.out.println("DEBUG : " + msg);
	}
}
