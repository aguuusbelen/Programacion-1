package juego;

import java.awt.Color;

import entorno.Entorno;
import entorno.Herramientas;

public class Velociraptor {

	private double x;
	private double y;
	private double ancho;
	private double alto;
	private double velocidad;
	private boolean estaCaminandoHaciaLaDerecha;

	private int pisoActual; // Numero del piso donde se encuentra el personaje.
	private boolean estaVivo;

	public Velociraptor(double x, double y, double velocidad) {
		this.x = x;
		this.y = y;
		this.ancho = 80;
		this.alto = 60;
		this.velocidad = velocidad;

		this.estaCaminandoHaciaLaDerecha = false;

		this.pisoActual = -1;
		this.estaVivo = true;

	}

	public void dibujar(Entorno e) {
		//e.dibujarRectangulo(x, y, ancho, alto, 0, Color.YELLOW);
		if (estaCaminandoHaciaLaDerecha) {
			e.dibujarImagen(Herramientas.cargarImagen("velociraptor_derecha.png"), x, y - 5, 0, 0.60);
		} else {
			e.dibujarImagen(Herramientas.cargarImagen("velociraptor_izquierda.png"), x, y - 5, 0, 0.60);
		}
	}

	public void mover(Entorno e, Piso[] pisos) {
		if (!estaSobreElPiso(pisos))
			caer(pisos);
		if (estaCaminandoHaciaLaDerecha && estaSobreElPiso(pisos))
			moverHaciaDerecha();
		if (!estaCaminandoHaciaLaDerecha && estaSobreElPiso(pisos))
			moverHaciaIzquierda();
		if (pisoActual == 4 || pisoActual == 2 || pisoActual == 0)
			estaCaminandoHaciaLaDerecha = false;
		if (pisoActual == 1 || pisoActual == 3)
			estaCaminandoHaciaLaDerecha = true;
		if (pisoActual == 0 && x < ancho / 2)
			estaVivo = false;
	}

	public void moverHaciaIzquierda() {
		if (x > ancho / 2) {
			x -= velocidad;
		}
		estaCaminandoHaciaLaDerecha = false;
	}

	public void moverHaciaDerecha() {
		if (x < 800 - ancho / 2) {
			x += velocidad;
		}
		estaCaminandoHaciaLaDerecha = true;
	}

	public void caer(Piso[] pisos) {
		if (estaSobreElBordeDelPiso(pisos)) {
			if (estaCaminandoHaciaLaDerecha) {
				x = x + 1;
			} else {
				x = x - 1;
			}
			y = y + 2;
		} else {
			y = y + 2;
		}
	}

	public Rayo dispararRayo() {
		boolean direccionDeDisparo = estaCaminandoHaciaLaDerecha;
		return new Rayo(x, y, direccionDeDisparo);
	}

	private boolean estaSobreElPiso(Piso[] pisos) {
		for (int i = 0; i < pisos.length; i++) {
			if ((((x - ancho / 2) >= pisos[i].getDimensiones()[0] && (x <= pisos[i].getDimensiones()[1]))
					|| (x >= pisos[i].getDimensiones()[0]) && (x + ancho / 2) <= pisos[i].getDimensiones()[1])
					&& (y + alto / 2) == pisos[i].getDimensiones()[2]) {
				pisoActual = i;
				return true;
			}
		}
		return false;
	}

	private boolean estaSobreElBordeDelPiso(Piso[] pisos) {
		for (int i = 0; i < pisos.length; i++) {
			if (i != pisos.length - 1) {
				if ((((x - ancho / 2) <= pisos[i + 1].getDimensiones()[1]
						&& (x + ancho / 2) >= pisos[i + 1].getDimensiones()[1])
						|| ((x + ancho / 2) >= pisos[i + 1].getDimensiones()[0]
								&& (x - ancho / 2) <= pisos[i + 1].getDimensiones()[0]))
						&& (((y - alto / 2) < pisos[i + 1].getDimensiones()[3]
								&& (y + alto / 2) > pisos[i + 1].getDimensiones()[3])
								|| ((y + alto / 2) > pisos[i + 1].getDimensiones()[2]
										&& (y - alto / 2) < pisos[i + 1].getDimensiones()[2]))) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean meChocoElRayo(Rayo rayo) {
		return (x + ancho / 2 >= rayo.getX() - rayo.getAlto() / 2) && (x - ancho / 2 <= rayo.getX() + rayo.getAlto() / 2)
				&& (y >= rayo.getY() - rayo.getAncho() / 2 && y <= rayo.getY() + rayo.getAlto() / 2);
	}



	public boolean getEstaVivo() {
		return estaVivo;
	}

	public boolean getFueHallada(Barbarianna barbarianna) {
		return (pisoActual == barbarianna.getPisoDondeEstaParado());
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
