package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Barbie {
	// posición
	private double x;
	private double y;

	// dimensiones
	private double ancho;
	private double alto;

	private Image img;
	private double velocidad;

	// salto (para esquivar disparo)
	private boolean estaSaltando;
	private int contSalto;
	private boolean caminaDerecha;
	

	public Barbie(double x, double y, double velocidad) {

		this.x = x;
		this.y = y;
		this.ancho = 36;
		this.alto = 60;
		this.velocidad = velocidad;
		this.img = Herramientas.cargarImagen("PersonajeQuieto().png");
		this.estaSaltando = false;
		this.contSalto = 0;
		this.caminaDerecha = true;
	}

	public void dibujar(Entorno e) {
		// e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);

	
		if (!estaSaltando) { // para que no se superpongan las imagenes, solo dibujo si NO esta saltando
			e.dibujarImagen(img, x, y, 0, 0.75);
		}

		return;
	}

	public void dibujarIzquierda(Entorno e) {
		e.dibujarImagen(Herramientas.cargarImagen("PersonajeIzq().png"), x, y, 0, 0.75);
		return;
	}

	public void moverHaciaIzquierda(Entorno e) {
		caminaDerecha = false;
		if (x > ancho / 2) {
			x -= velocidad;
		}
		if (!estaSaltando) {
			dibujarIzquierda(e);
		}
	}

	public void dibujarDerecha(Entorno e) {
		e.dibujarImagen(Herramientas.cargarImagen("PersonajeDer().png"), x, y, 0, 0.75);
		return;
	}

	public void moverHaciaDerecha(Entorno e) {
		caminaDerecha = true;
		if (x < e.ancho() - ancho / 2) {
			x += velocidad;
		}
		if (!estaSaltando) {
			dibujarDerecha(e);
		}
	}

	public void saltar(Entorno e) {
		alto = 60;
		if (estaSaltando == true && contSalto <= alto / 2) {
			if (caminaDerecha == true) {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaDer.png"), x, y, 0, 0.75);
				// e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaIzq.png"), x, y, 0, 0.75);
				// e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			}
			y = y - 2;
			contSalto++;
		} else if (estaSaltando == true && contSalto > alto / 2) {
			if (caminaDerecha == true) {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaDer.png"), x, y, 0, 0.75);
				// e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaIzq.png"), x, y, 0, 0.75);
				// e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			}
			y = y + 2;
			contSalto++;
			if (contSalto == 62) {
				estaSaltando = false;
				contSalto = 0;
			}
		} else {
			estaSaltando = true;
		}
	}

	public boolean isEstaSaltando() {
		return estaSaltando;
	}

	public void agacharse(Entorno e) {
		alto = 40;
		if (caminaDerecha == false) {
			e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			e.dibujarImagen(Herramientas.cargarImagen("Personaje_abajoIzq.png"), x, y, 0, 0.75);
		} else {
			e.dibujarImagen(Herramientas.cargarImagen("Personaje_abajoDer.png"), x, y, 0, 0.75);
		}

	}

}
