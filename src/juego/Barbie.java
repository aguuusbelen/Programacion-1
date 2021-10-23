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
	private boolean caminaDerecha;

	// salto (para esquivar disparo)
	private boolean estaSaltando;
	private int contSalto;
	
	//agacharse
	private boolean estaAgachado;
	private int altoAgachado;
	private int altoOriginal;
	private double auxPosY;
	
	//saltopiso
	private int contSaltoPiso;

	public Barbie(double x, double y, double velocidad) {

		this.x = x;
		this.y = y;
		this.auxPosY = y;
		this.ancho = 36;
		this.alto = 60;
		this.altoOriginal = 60;
		this.altoAgachado = 40;
		this.velocidad = velocidad;
		this.estaSaltando = false;
		this.estaAgachado = false;
		this.contSalto = 0;
		this.contSaltoPiso = 0;
		this.caminaDerecha = true;
	}

	public void dibujar(Entorno e) {
		if (estaAgachado) {
			revertirAgachar();
		}
		if (!estaSaltando) { // para que no se superpongan las imagenes, solo dibujo si NO esta
								// saltando
			e.dibujarImagen(Herramientas.cargarImagen("PersonajeQuieto().png"), x, y, 0, 0.75);
			//e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
		}
	}

	public void dibujarIzquierda(Entorno e) {
		e.dibujarImagen(Herramientas.cargarImagen("PersonajeIzq().png"), x, y, 0, 0.75);
		//e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
		return;
	}

	public void moverHaciaIzquierda(Entorno e) {
		if (estaAgachado) {
			revertirAgachar();
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
			revertirAgachar();
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
			alto = altoAgachado;
			y = auxPosY + (alto - altoAgachado) / 2;
			if (caminaDerecha == false) {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_abajoIzq.png"), x, y, 0, 0.75);
				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_abajoDer.png"), x, y, 0, 0.75);
				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			}
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
			if (caminaDerecha == true) {
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

	public boolean EstaAgachado() {
		return estaAgachado;
	}

	public boolean EstaSaltando() {
		return estaSaltando;
	}
	
	public boolean isCaminaDerecha() {
		return caminaDerecha;
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
