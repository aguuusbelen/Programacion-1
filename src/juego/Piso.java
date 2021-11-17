package juego;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Piso {
	
	private double x;
	private double y;
	private int ancho;
	private int alto;

	private Image img;

	public Piso(double x, double y) {
		this.x = x;
		this.y = y;
		this.alto = 24;
		this.ancho = 800;
		this.img = Herramientas.cargarImagen("pisoSuperiores.png");
	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(img, x, y, 0, 1.26);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

}