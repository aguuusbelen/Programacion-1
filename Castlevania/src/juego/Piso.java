package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Piso {

	private double x;
	private double y;
	private Image img;
	private int ancho;
	private int alto;

	public Piso(double x, double y, String img) {
		this.x = x;
		this.y = y;
		this.img = Herramientas.cargarImagen(img);
		this.alto = 24;
		this.ancho = 800;
	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(img, x, y, 0, 1.26);
		
		
		
	}

	public void dibujarColision(Entorno e) {
		e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
	}

	// Getters
	// ----------------------------------------------------------------------------
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
