package juego;

import entorno.Entorno;
import entorno.Herramientas;

public class Velociraptor {

	private double x;
	private double y;
	private double ancho;
	private double alto;
	private double velocidad;
	private double velocidadDeCaida;

	private boolean direccionDerecha; 
	private boolean meEstoyCayendo;
	private boolean llegueAlFinalDelCamino;

	public Velociraptor(double x, double y, double velocidad) {
		this.x = x;
		this.y = y;
		this.ancho = 80;
		this.alto = 60;
		this.velocidad = velocidad;
		this.velocidadDeCaida = 4;

		this.direccionDerecha = false;
		this.meEstoyCayendo = false;
		this.llegueAlFinalDelCamino = false;
	}

	public void dibujar(Entorno e) {
		if (direccionDerecha) {
			e.dibujarImagen(Herramientas.cargarImagen("velociraptor_derecha.png"), x, y - 5, 0, 0.60);
		} else {
			e.dibujarImagen(Herramientas.cargarImagen("velociraptor_izquierda.png"), x, y - 5, 0, 0.60);
		}
	}

	public void mover(Entorno e) {
		if (direccionDerecha) {
			if (x < e.ancho() - ancho / 2) {
				x += velocidad;
			} else {
				direccionDerecha = !direccionDerecha;
			}
		} else {
			if (x > ancho / 2) {
				x -= velocidad;
			} else {
				direccionDerecha = !direccionDerecha;
			}

		}
	}

	public void caer(Entorno e, Piso[] pisos) {
		Piso pisoActualDeVelociraptor = pisoEnElQueEstoyParado(pisos);
		if (meEstoyCayendo == false) {
			if (pisoActualDeVelociraptor != null && pisoActualDeVelociraptor.getX() < e.ancho() / 2
					&& x > pisoActualDeVelociraptor.getX() + pisoActualDeVelociraptor.getAncho() / 2) {
				meEstoyCayendo = true;
				y = y + 4;
			} else if (pisoActualDeVelociraptor != null && pisoActualDeVelociraptor.getX() > e.ancho() / 2
					&& x < pisoActualDeVelociraptor.getX() - pisoActualDeVelociraptor.getAncho() / 2) {
				meEstoyCayendo = true;
				y = y + 4;
			} else if (pisoActualDeVelociraptor != null && pisoActualDeVelociraptor.getX() == e.ancho() / 2
					&& x < ancho / 2) {
				llegueAlFinalDelCamino = true;
			}
		} else {
			if (pisoActualDeVelociraptor != null
					&& pisoActualDeVelociraptor.getY() - pisoActualDeVelociraptor.getAlto() / 2 <= y + alto / 2) {
				meEstoyCayendo = false;
			} else {
				y = y + velocidadDeCaida;
			}
		}
	}

	public Piso pisoEnElQueEstoyParado(Piso[] pisos) {
		for (int p = 0; p < pisos.length; p++) {
			if (pisos[p].getY() - pisos[p].getAlto() / 2 == y + alto / 2) {
				return pisos[p];
			}
		}
		return null; 
	}

	public boolean llegueAlFinalDelCamino() {
		return llegueAlFinalDelCamino;
	}

	public Rayo dispararRayo() {
		boolean direccion = direccionDerecha;
		return new Rayo(x, y, direccion);
	}

	public boolean meChocoElRayo(Rayo rayo) {
		return (x + ancho / 2 >= rayo.getX() - rayo.getAncho() / 2
				&& x - ancho / 2 <= rayo.getX() + rayo.getAncho() / 2)
				&& (y + alto >= rayo.getY() - rayo.getAlto() / 2 && y - alto <= rayo.getY() + rayo.getAlto() / 2);
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
