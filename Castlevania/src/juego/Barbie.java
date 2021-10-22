package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Barbie {
	// posición
	private double x;
	private double y;

	private double aux;

	// dimensiones
	private double ancho;
	private double alto;

	private Image img;
	private double velocidad;

	// salto (para esquivar disparo)
	private boolean estaSaltando;
	private boolean estaAgachado;
	private int contSalto;
	private int contSaltoPiso;
	private boolean caminaDerecha;

	public Barbie(double x, double y, double velocidad) {

		this.x = x;
		this.y = y;
		this.aux = y;
		this.ancho = 36;
		this.alto = 60;
		this.velocidad = velocidad;
		this.estaSaltando = false;
		this.estaAgachado = false;
		this.contSalto = 0;
		this.contSaltoPiso = 0;
		this.caminaDerecha = true;
	}

	public void dibujar(Entorno e) {
		if (estaAgachado) {
			revertir();
		}
		if (!estaSaltando) { // para que no se superpongan las imagenes, solo dibujo si NO esta
								// saltando
			e.dibujarImagen(Herramientas.cargarImagen("PersonajeQuieto().png"), x, y, 0, 0.75);
			e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
		}
	}

	public void dibujarIzquierda(Entorno e) {
		e.dibujarImagen(Herramientas.cargarImagen("PersonajeIzq().png"), x, y, 0, 0.75);
		e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
		return;
	}

	public void moverHaciaIzquierda(Entorno e) {
		if (estaAgachado) {
			revertir();
		}
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
		e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
		return;
	}

	public void moverHaciaDerecha(Entorno e) {
		if (estaAgachado) {
			revertir();
		}
		caminaDerecha = true;
		if (x < e.ancho() - ancho / 2) {
			x += velocidad;
		}
		if (!estaSaltando) {
			dibujarDerecha(e);
		}
	}

	public void saltar(Entorno e) {
		if (estaSaltando == true && contSalto <= alto / 2) {
			if (caminaDerecha == true) {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaDer.png"), x, y, 0, 0.75);
				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaIzq.png"), x, y, 0, 0.75);
				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			}
			y = y - 2;
			contSalto++;
		} else if (estaSaltando == true && contSalto > alto / 2) {
			if (caminaDerecha == true) {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaDer.png"), x, y, 0, 0.75);
				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaIzq.png"), x, y, 0, 0.75);
				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			}
			y = y + 2;
			contSalto++;
			if (contSalto == 62) {
				estaSaltando = false;
				contSalto = 0;
			}
		} else {
			if (estaAgachado) {
				revertir();
			}
			estaSaltando = true;

		}

	}

	public boolean EstaSaltando() {
		return estaSaltando;
	}

	public void agacharse(Entorno e) {
		if (estaSaltando == false) {
			estaAgachado = true;
			alto = 40;
			y = aux + 10;
			if (caminaDerecha == false) {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_abajoIzq.png"), x, y, 0, 0.75);
				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_abajoDer.png"), x, y, 0, 0.75);
				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			}
		}
	}

	public boolean EstaAgachado() {
		return estaAgachado;
	}

	public void revertir() {
		estaAgachado = false;
		alto = 60;
		y = aux;
	}

//	public void subirUnPiso(Entorno e) {
//		alto = 60;
//		if (contSaltoPiso <= 70) {
//			if (caminaDerecha == true) {
//				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaDer.png"), x, y, 0, 0.75);
//				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
//				x = x + 2;
//			} else {
//				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaIzq.png"), x, y, 0, 0.75);
//				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
//				x = x - 2;
//			}
//			y = y - 2;
//			contSaltoPiso++;
//		}

//	}
}
