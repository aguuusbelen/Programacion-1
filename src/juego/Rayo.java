package juego;

import java.awt.Color;

import entorno.Entorno;
import entorno.Herramientas;

public class Rayo {

	private double x;
	private double y;

	private double ancho;
	private double alto;

	private double velocidad;
	private boolean estaMirandoDerecha;

	private boolean disparado;

	public Rayo(double x, double y, double velocidad, boolean estaMirandoDerecha) {
		this.x = x;
		this.y = y;

		this.velocidad = velocidad;
		this.estaMirandoDerecha = estaMirandoDerecha;
		this.ancho = 50;
		this.alto = 20;
		this.disparado = false;
	}

	public void mover() { // mover()
		if (x > 800 || x < 0) {
			disparado = false;
			return;
		} else {
			disparado = true;
		}
		if (estaMirandoDerecha) {
			x += velocidad;
		} else {
			x -= velocidad; // expliquenlo en el informe
		}
	}

	public void dibujar(Entorno e) { // dibujar()
		// e.dibujarRectangulo(x, y, ancho, alto, 0, Color.GREEN);
		if (estaMirandoDerecha) {
			e.dibujarImagen(Herramientas.cargarImagen("rayo-der.png"), x, y, 0, 1);
			// e.dibujarRectangulo(x, y, 50, 20, 0, Color.GREEN);
		} else {
			e.dibujarImagen(Herramientas.cargarImagen("rayo-izq.png"), x, y, 0, 1);
			// e.dibujarRectangulo(x, y, 50, 20, 0, Color.GREEN);
		}
	}

	public boolean impactaEnemigo(Velociraptor enemigo) {
		return (((x >= enemigo.getX() - enemigo.getAncho() / 2 && x <= enemigo.getX() + enemigo.getAncho() / 2)
				|| (x <= enemigo.getX() - enemigo.getAncho() / 2 && x >= enemigo.getX() + enemigo.getAncho() / 2))
				&& (y > enemigo.getY() - enemigo.getAlto() / 2 && y < enemigo.getY() + enemigo.getAlto() / 2));

	}

	public boolean impactaPersonaje(Barbarianna personaje) {
		return (((x >= personaje.getX() - personaje.getAncho() / 2 && x <= personaje.getX() + personaje.getAncho() / 2)
				|| (x <= personaje.getX() - personaje.getAncho() / 2
						&& x >= personaje.getX() + personaje.getAncho() / 2))
				&& (y > personaje.getY() - personaje.getAlto() / 2 && y < personaje.getY() + personaje.getAlto() / 2));

	}

//	public boolean chocasteConVelociraptor(Velociraptor raptor) {
//		return x > raptor.getX() ;
//	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public boolean getDisparado() {
		return disparado;
	}

}
