package juego;

import entorno.Entorno;
import entorno.Herramientas;

public class Rayo {
	private double x;
	private double y;

	private double ancho;
	private double alto;

	private double velocidad;

	private boolean fueDisparadoHaciaLaDerecha;

	public Rayo(double x, double y, boolean direccionDeDisparo) {
		this.x = x;
		this.y = y;
		this.ancho = 40;
		this.alto = 10;
		this.velocidad = 4;
		this.fueDisparadoHaciaLaDerecha = direccionDeDisparo;
	}

	public void mover() {
		if (fueDisparadoHaciaLaDerecha) {
			x += velocidad;
		} else {
			x -= velocidad;
		}
	}

	public void dibujar(Entorno e) {
		if (fueDisparadoHaciaLaDerecha) {
			e.dibujarImagen(Herramientas.cargarImagen("rayo-der.png"), x, y, 0, 0.85);
		} else {
			e.dibujarImagen(Herramientas.cargarImagen("rayo-izq.png"), x, y, 0, 0.85);
		}
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
