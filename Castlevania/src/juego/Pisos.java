package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Pisos {

	private double x;
	private double y;
	private Image img;
	private int ancho;
	private int alto;
	
	public Pisos(double x, double y, String img) {
		this.x = x;
		this.y = y;
		this.img = Herramientas.cargarImagen(img);
		this.alto = 20;
		this.ancho = 635;
	}
	
	public void dibujar(Entorno e) {
		e.dibujarImagen(img, x, y, 0);
	}
	
	public void dibujarColision(Entorno e) {
		e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
	}

	//Getters ----------------------------------------------------------------------------
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
