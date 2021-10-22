package juego;

public class RayoEnemigo {
	
	private double x;
	private double y;
	
	private double tamanio;
	private double ancho;
	private double alto;
	
	private double velocidad;

	public RayoEnemigo(double x, double y, double velocidad) {
		
		this.x = x;
		this.y = y;
		this.ancho = 150;
		this.alto = 30;
		this.velocidad = velocidad;
	}

}
