package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Coraz�n {

	private double x;
	private double y;

	private Image img;

	public Coraz�n(double x, double y) {
		this.x = x;
		this.y = y;
		this.img = Herramientas.cargarImagen("coraz�n.png");
	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(img, x, y, 0,0.8);
	}

	public double getX() {
		return x;
	}
	
}