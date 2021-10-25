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
		this.ancho = 50;
		this.alto = 30;
	}

	public void avanzar() { // mover()
		if (estaMirandoDerecha) {
			x += velocidad;
		} else {
			x += -velocidad; // expliquenlo en el informe
		}
	}
	
	public void dibujarRayo(Entorno e) { // dibujar()
		e.dibujarRectangulo(x, y, ancho, alto, 0, Color.GREEN);
	}
	
	public boolean chocasteConVelociraptor(Velociraptor raptor) {
		return x > raptor.getX() + raptor.getAncho()/2;
	}

	public double getX() {
		return x;
	}

	
}
