package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Computadora {
	private double x;
	private double y;
	private double ancho;
	private double alto;

	private Image img;

	public Computadora(double x, double y) {
		this.x = x;
		this.y = y;
		this.ancho = 60;
		this.alto = 60;
		this.img = Herramientas.cargarImagen("compu.png");
	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(img, x, y, 0, 0.15);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAncho() {
		return ancho;
	}

	public double getAlto() {
		return alto;
	}
}
