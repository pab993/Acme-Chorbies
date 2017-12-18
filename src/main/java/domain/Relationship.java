package domain;


public enum Relationship {
	ACTIVITIES("ACTIVITIES", "Actividades", "Activities"), 
	FRIENSHIP("FRIENDSHIP", "Amistad", "Friendship"),
	LOVE("LOVE", "Amor", "Love");

	private final String 	constante;
	private final String	nombre;
	private final String	nombreIngles;


	private Relationship(String constante, String nombre, String nombreIngles) {
		this.constante = constante;
		this.nombre = nombre;
		this.nombreIngles = nombreIngles;
	}
	
	public String getConstante() {
		return constante;
	}

	public String getNombre() {
		return nombre;
	}

	public String getNombreIngles() {
		return nombreIngles;
	}
}
