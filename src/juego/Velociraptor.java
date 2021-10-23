package juego;

public class Velociraptor {

	// posici�n
	private double x;
	private double y;

	// dimensiones
	private double ancho;
	private double alto;
	//

	private double velocidad;

	public Velociraptor(double x, double y, double velocidad) {

		this.x = x;
		this.y = y;
		this.ancho = 50;
		this.alto = 20;
		this.velocidad = velocidad;

	}

	public double getX() {
		return x;
	}

	public double getAncho() {
		return ancho;
	}

}
