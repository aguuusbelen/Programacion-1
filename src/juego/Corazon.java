package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Corazon {

	private double x;
	private double y;

	private Image img;

	public Corazon(double x, double y) {
		this.x = x;
		this.y = y;
		
		this.img = Herramientas.cargarImagen("corazon.png");
	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(img, x, y, 0,0.8);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
}