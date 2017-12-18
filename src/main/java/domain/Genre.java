package domain;


public enum Genre {
	HOMBRE("MAN", "Hombre", "Man"), 
	MUJER("WOMAN", "Mujer", "Woman");

	private final String 	constante;
	private final String	nombre;
	private final String	nombreIngles;


	private Genre(String constante, String nombre, String nombreIngles) {
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
