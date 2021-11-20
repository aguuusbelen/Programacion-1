package juego;

import java.awt.Image;

import entorno.Entorno;

public class Item {

	private double x;
	private double y;
	private String nombre;

	public Item(double x, double y, String nombre) {
		this.x = x;
		this.y = y;
		this.nombre=nombre;
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

	public String getNombre() {
		return nombre;
	}
	
}