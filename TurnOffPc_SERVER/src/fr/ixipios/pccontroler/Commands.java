package fr.ixipios.pccontroler;

public class Commands {
	
	
	// Internal commands (commands executed by the server's operator)
	
	@Command(name = "help", description = "List of all commands", usage = "/help", local = true)
	public static void help()  {
		
	}
	
	@Command(name = "list", local = true)
	public static void list() {
		
	}
	
	
	// Server commands (commands executed by the clients)
	
	@Command(name = "mp", description = "Send a message to one user", usage = "/mp [userName] [message]" ,local = false)
	public static void mp() {
		
	}
}
