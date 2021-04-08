package fr.ixipios.pccontroler;

public @interface Command {

	String name();
	String description() default "This command doesn't have any description";
	String usage() default "No usage defined for this command";
	boolean local();
}
