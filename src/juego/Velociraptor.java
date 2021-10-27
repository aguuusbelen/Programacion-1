package juego;

import java.awt.Color;

import entorno.Entorno;

public class Velociraptor {

	private double x;
	private double y;

	private double ancho;
	private double alto;

	private double velocidad;

	private Rayo rayo;
	private boolean caminaHaciaLaDerecha;

	public Velociraptor(double x, double y, double velocidad) {
		this.x = x;
		this.y = y;
		this.ancho = 50;
		this.alto = 20;
		this.velocidad = velocidad;
		
		this.rayo = null;
		this.caminaHaciaLaDerecha = true;

	}

	public void dibujar(Entorno e) {
		e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
		if (rayo != null) {
			rayo.dibujar(e);
		}
	}
	
	public void dispararRayo() {

		if (rayo == null) {
			if (caminaHaciaLaDerecha) {
				rayo = new Rayo(x + ancho / 2, y, 4, caminaHaciaLaDerecha);
			} else {
				rayo = new Rayo(x - ancho / 2, y, 4, caminaHaciaLaDerecha);
			}
		}
	}
	
	public void avanzarDisparo() {
		if (rayo != null) {
			rayo.mover();
			if (rayo.getX() > 800 || rayo.getX() < 0) {
				rayo = null;
			}
		}
	}


	public double getX() {
		return x;
	}

	public double getAncho() {
		return ancho;
	}

}
