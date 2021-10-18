package juego;

public class Rayo {
	
	private double x;
	private double y;
	
	private double tamanio;
	private double ancho;
	private double alto;
	
	private double velocidad;

	public Rayo(double x, double y, double velocidad) {
		
		this.x = x;
		this.y = y;
		this.ancho = 15;
		this.alto = 5;
		this.velocidad = velocidad;
	}

}
