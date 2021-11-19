package juego;

import java.awt.Image;

import entorno.Entorno;

public class Escudo {

	private double x;
	private double y;

	
	public Escudo(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void dibujar(Entorno e, Image imagen) {
			e.dibujarImagen(imagen, x, y, 0, 0.8);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
