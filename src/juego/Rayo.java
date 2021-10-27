package juego;
import entorno.Entorno;
import entorno.Herramientas;

public class Rayo {
	private double x;
	private double y;
	private double velocidad;
	
	private boolean estaCaminandoHaciaLaDerecha;
	private boolean estaEnPantalla;

	public Rayo(double x, double y, double velocidad, boolean estaCaminandoHaciaLaDerecha) {
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;

		this.estaCaminandoHaciaLaDerecha = estaCaminandoHaciaLaDerecha;
		this.estaEnPantalla = false;
	}

	public void mover(Entorno e) { 
		if (x > e.ancho() || x < 0) {
			estaEnPantalla = false;
			return;
		} else {
			estaEnPantalla = true;
		}
		if (estaCaminandoHaciaLaDerecha) {
			x += velocidad;
		} else {
			x -= velocidad; 
		}
	}

	public void dibujar(Entorno e) { 
		if (estaCaminandoHaciaLaDerecha) {
			e.dibujarImagen(Herramientas.cargarImagen("rayo-der.png"), x, y, 0, 1);
		} else {
			e.dibujarImagen(Herramientas.cargarImagen("rayo-izq.png"), x, y, 0, 1);
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

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public boolean getEstaEnPantalla() {
		return estaEnPantalla;
	}
}
