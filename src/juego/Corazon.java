package juego;

import entorno.Entorno;
import entorno.Herramientas;

public class Corazon {

	private double x;
	private double y;

	public Corazon(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(Herramientas.cargarImagen("corazon.png"), x, y, 0,0.8);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
}