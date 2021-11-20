package juego;

import entorno.Entorno;
import entorno.Herramientas;

public class Rayo {
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private double velocidad;
	private boolean direccion;

	public Rayo(double x, double y, boolean direccion) {
		this.x = x;
		this.y = y;
		this.ancho = 40;
		this.alto = 10;
		this.velocidad = 4;
		this.direccion = direccion;
	}

	public void mover() {
		if (direccion) {
			x += velocidad;
		} else {
			x -= velocidad;
		}
	}

	public void dibujar(Entorno e) {
		if (direccion) {
			e.dibujarImagen(Herramientas.cargarImagen("rayo_der.png"), x, y, 0, 0.85);
		} else {
			e.dibujarImagen(Herramientas.cargarImagen("rayo_izq.png"), x, y, 0, 0.85);
		}
	}

	public boolean salioDeLosBordes(Entorno e) {
		if (this.x > e.ancho() || this.x < 0) {
			return true;
		}
		return false;
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
