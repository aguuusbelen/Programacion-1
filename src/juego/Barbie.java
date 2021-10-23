package juego;

import java.awt.Color;

import entorno.Entorno;
import entorno.Herramientas;

public class Barbie {
	// posicin
	private double x;
	private double y;

	// dimensiones
	private double ancho;
	private double alto;

	private double velocidad;
	private boolean caminaHaciaLaDerecha;

	private RayoBarbie rayo;

	// salto (para esquivar disparo)
	private boolean estaSaltando;
	private int contSalto;

	// agacharse
	private boolean estaAgachado;

	private int altoAgachada;
	private int altoOriginal;
	private double auxPosY;

	// saltopiso
	private int contSaltoPiso;

	public Barbie(double x, double y, double velocidad) {

		this.x = x;
		this.y = y;

		this.auxPosY = y;
		this.ancho = 36;
		this.alto = 60;
		this.altoOriginal = 60;
		this.altoAgachada = 40;

		this.velocidad = velocidad;
		this.estaSaltando = false;
		this.estaAgachado = false;
		this.contSalto = 0;
		this.contSaltoPiso = 0;
		this.caminaHaciaLaDerecha = true;
		this.rayo = null;
	}

	public void dibujar(Entorno e) {

		if (!estaSaltando) { // para que no se superpongan las imagenes, solo dibujo si NO esta
								// saltando
			e.dibujarImagen(Herramientas.cargarImagen("PersonajeQuieto().png"), x, y, 0, 0.75);
			// e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
		}
		if (estaAgachado == true) {
			if (caminaHaciaLaDerecha == false) {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_abajoIzq.png"), x, y, 0, 0.75);
				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_abajoDer.png"), x, y, 0, 0.75);
				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			}
		}
		
		if (rayo != null) {
			rayo.dibujarRayo(e);
		}
		
		if (estaAgachado) {
			revertirAgachar();
		}
	}

	public void avanzarDisparo() {
		if (rayo != null) {
			rayo.avanzar();
			if (rayo.getX() > 800 || rayo.getX() < 0) {
				rayo = null;
			}
		}
	}

	public void dispararRayo(Entorno e) {

		if (rayo == null) {
			if (caminaHaciaLaDerecha) {
				rayo = new RayoBarbie(x + ancho / 2, y, 4, caminaHaciaLaDerecha);
			} else {
				rayo = new RayoBarbie(x - ancho / 2, y, 4, caminaHaciaLaDerecha);
			}
		}
	}

	public void dibujarIzquierda(Entorno e) {
		e.dibujarImagen(Herramientas.cargarImagen("PersonajeIzq().png"), x, y, 0, 0.75);
		// e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
		return;

	}

	public void moverHaciaIzquierda(Entorno e) {
		if (estaAgachado) {
			revertirAgachar();
		}
		caminaHaciaLaDerecha = false;
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
			revertirAgachar();
		}
		caminaHaciaLaDerecha = true;
		if (x < e.ancho() - ancho / 2) {
			x += velocidad;
		}
		if (!estaSaltando) {
			dibujarDerecha(e);
		}
		caminaHaciaLaDerecha = true;
	}

	public void moverHaciaDerechaSaltando(Entorno e) {
		if (x < e.ancho() - ancho / 2) {
			x += velocidad;
			// dibujar(e, "Personaje_esquivarArribaDer.png");
			e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaDer.png"), x, y, 0, 0.75);
		}
		caminaHaciaLaDerecha = true;
	}

	public void saltar(Entorno e) {
		alto = 60;
		if (estaSaltando == true && contSalto <= alto / 2) {
			if (caminaHaciaLaDerecha == true) {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaDer.png"), x, y, 0, 0.75);
				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaIzq.png"), x, y, 0, 0.75);
				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);

			}
			y = y - 2;
			contSalto++;
		} else if (estaSaltando == true && contSalto > alto / 2) {
			if (caminaHaciaLaDerecha == true) {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaDer.png"), x, y, 0, 0.75);
				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaIzq.png"), x, y, 0, 0.75);
				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			}
			y = y + 2;
			contSalto++;
			if (contSalto == alto + 2) {
				estaSaltando = false;
				contSalto = 0;
			}
		} else {
			if (estaAgachado) {
				revertirAgachar();
			}
			estaSaltando = true;

		}

	}

	public void agacharse(Entorno e) {
		if (estaSaltando == false) {
			estaAgachado = true;
			alto = 40;
			y = auxPosY + 10;
			dibujar(e);
		}
	}

	public void revertirAgachar() {
		estaAgachado = false;
		alto = altoOriginal;
		y = auxPosY;
	}

	public void subirUnPiso(Entorno e) {
		if (contSaltoPiso <= 70) {
			y = y - 2;
			contSaltoPiso++;
			if (caminaHaciaLaDerecha == true) {
				x = x + 0.5;
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaDer.png"), x, y, 0, 0.75);
				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			} else {
				x = x - 0.5;
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaIzq.png"), x, y, 0, 0.75);
				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);

			}
		}
	}

	public boolean estaAgachado() {
		return estaAgachado;

	}

	public boolean estaSaltando() {
		return estaSaltando;
	}

	public boolean isCaminaDerecha() {
		return caminaHaciaLaDerecha;
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
