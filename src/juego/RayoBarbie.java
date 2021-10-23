package juego;

import java.awt.Color;

import entorno.Entorno;

public class RayoBarbie {

	private double x;
	private double y;

	private double ancho;
	private double alto;

	private double velocidad;
	private boolean estaMirandoDerecha;

	public RayoBarbie(double x, double y, double velocidad, boolean estaMirandoDerecha) {


		this.x = x;
		this.y = y;

		this.velocidad = velocidad;
		this.estaMirandoDerecha = estaMirandoDerecha;
		this.ancho = 10;
		this.alto = 3;
	}

	public double getX() {
		return x;
	}

	public void avanzar(Entorno e) {
		if (estaMirandoDerecha == true) {
			x = x + velocidad;
		} else {
			x = x - velocidad;
		}
		e.dibujarRectangulo(x, y, ancho, alto, alto, Color.GREEN);
	}

	public void avanzar(Entorno e) {
		if (estaMirandoDerecha) {
			x += velocidad;
		} else {
			x += -velocidad;
		}
		e.dibujarRectangulo(x, y, ancho, alto, 0, Color.GREEN);
	}

	public double getX() {
		return x;
	}

	
}
