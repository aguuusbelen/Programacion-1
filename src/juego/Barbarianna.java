package juego;

import java.awt.Color;

import entorno.Entorno;
import entorno.Herramientas;

public class Barbarianna {

	private double x;
	private double y;

	private double ancho;
	private double alto;

	private double velocidad;
	private boolean estaCaminandoHaciaLaDerecha;
	private boolean meEstoyMoviendo;

//	private RayoBarbie rayo;

	// salto (para esquivar disparo)
	private boolean estaSaltando; // fijense el nombre
	private int distanciaDelPisoCuandoSalta;

	// agacharse
	private boolean estaAgachada;

	private int altoOriginal;
	private boolean subiendo;
	private boolean estaSuperSaltando;

	private boolean cayendo;

	private double ultimaPosY1;

	private boolean disparando;
	private int pisoActual; 
	
	
	public Barbarianna(double x, double y, double velocidad) {
		this.x = x;
		this.y = y;

		this.ancho = 36;
		this.alto = 60;
		this.altoOriginal = 60;

		this.velocidad = velocidad;
		this.estaSaltando = false;
		this.estaAgachada = false;
		this.distanciaDelPisoCuandoSalta = 0;

		this.estaSuperSaltando = false;

		this.estaCaminandoHaciaLaDerecha = true;
//		this.rayo = null;
		this.meEstoyMoviendo = false;
		this.subiendo = false;

		this.cayendo = false;
		this.ultimaPosY1 = 0;
		this.disparando = false;
		this.pisoActual = 0;
	}

	public void dibujar(Entorno e) {
		if (!estaSaltando) {
			if (estaAgachada) {
				if (estaCaminandoHaciaLaDerecha) {
					e.dibujarImagen(Herramientas.cargarImagen("Personaje_abajoDer.png"), x, y - 20, 0, 0.75);
				} else {
					e.dibujarImagen(Herramientas.cargarImagen("Personaje_abajoIzq.png"), x, y - 20, 0, 0.75);
				}
				return;
			}
			if (meEstoyMoviendo) {
				if (estaCaminandoHaciaLaDerecha) {
					e.dibujarImagen(Herramientas.cargarImagen("PersonajeDer().png"), x, y - 5, 0, 0.75);
				} else {
					e.dibujarImagen(Herramientas.cargarImagen("PersonajeIzq().png"), x, y - 5, 0, 0.75);
				}
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("PersonajeQuieto().png"), x, y - 5, 0, 0.75);
			}
		} else {
			if (estaCaminandoHaciaLaDerecha) {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaDer.png"), x, y, 0, 0.75);
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaIzq.png"), x, y, 0, 0.75);
			}
		}
	}

	public void dibujarColision(Entorno e) {
		e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
	}
//	public void avanzarDisparo() {
//		if (rayo != null) {
//			rayo.avanzar();
//			if (rayo.getX() > 800 || rayo.getX() < 0) {
//				rayo = null;
//			}
//		}
//	}

	// está encaminado, pero tienen que repensarlo
	public void dispararRayo() {
		disparando = true;
	}
		
	public void terminoDeDisparar() {
		disparando = false;
	}
//		if (rayo == null) {
//			if (caminaHaciaLaDerecha) {
//				rayo = new RayoBarbie(x + ancho / 2, y, 4, caminaHaciaLaDerecha);
//			} else {
//				rayo = new RayoBarbie(x - ancho / 2, y, 4, caminaHaciaLaDerecha);
//			}
//		}



	public void moverHaciaIzquierda(Entorno e, Piso[] pisos) {
		if (estaAgachada) {
			levantar();
		}
		if (tocandoElCostado(pisos)) {
			return;
		}
		if (x > ancho / 2) {
			x -= velocidad;
		}
		estaCaminandoHaciaLaDerecha = false;
		meEstoyMoviendo = true;
	}

	public void moverHaciaDerecha(Entorno e, Piso[] pisos) {
		if (estaAgachada) {
			levantar();
		}
		if (tocandoElCostado(pisos)) {
			return;
		}
		if (x < e.ancho() - ancho / 2) {
			x += velocidad;
		}
		estaCaminandoHaciaLaDerecha = true;
		meEstoyMoviendo = true;
	}

	public void estaQuieto() {
		meEstoyMoviendo = false;
	}

	public void saltar(Piso[] pisos) {
		if ((estaSaltando == true && !cayendo) && (!tocandoElTecho(pisos) && distanciaDelPisoCuandoSalta <= 25)) {
			y = y - 2;
			distanciaDelPisoCuandoSalta++;
		} else if (estaSaltando == true && (tocandoElTecho(pisos) || distanciaDelPisoCuandoSalta == 26)) {
			distanciaDelPisoCuandoSalta++;
			y = y + 2;
			cayendo = true;
		} else if (estaSaltando && cayendo) {
			y = y + 2;
			if (tocandoElPiso(pisos)) {
				estaSaltando = false;
				cayendo = false;
				distanciaDelPisoCuandoSalta = 0;
			}
		} else {
			if (estaAgachada) {
				levantar();
			}
			estaSaltando = true;
		}

	}

	public void saltar2(Piso[] pisos) {
		if ((estaSuperSaltando == true && !cayendo) && (!tocandoElTecho(pisos) && distanciaDelPisoCuandoSalta <= 55)) {
			y = y - 2;
			distanciaDelPisoCuandoSalta++;
		} else if (estaSuperSaltando == true && (tocandoElTecho(pisos) || distanciaDelPisoCuandoSalta == 56)) {
			distanciaDelPisoCuandoSalta++;
			y = y + 2;
			cayendo = true;
		} else if (estaSuperSaltando && cayendo) {
			y = y + 2;
			if (tocandoElPiso(pisos)) {
				estaSuperSaltando = false;
				estaSaltando = false;
				cayendo = false;
				distanciaDelPisoCuandoSalta = 0;
			}
		} else {
			if (estaAgachada) {
				levantar();
			}
			estaSuperSaltando = true;
		}
	}

	public void agacharse() { // agachar()
		if (!estaSaltando && estaAgachada == false) {

			ultimaPosY1 = (y + alto / 2);

			alto = alto / 2;
			// y = y - alto /2;
			y = y + alto / 2;
			estaAgachada = true;

		}
	}

	public void levantar() { // levantar() pararseDerecho()
		estaAgachada = false;
		y = ultimaPosY1 - alto;
		alto = altoOriginal;
	}

	public void caer(Piso[] pisos) {
		if (!tocandoElPiso(pisos) && tocandoElCostado(pisos)) {
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

	public boolean tocandoElPiso(Piso[] pisos) {
		for (int i = 0; i < pisos.length; i++) {
			if ((((x - ancho / 2) >= pisos[i].getPosColision()[0] && (x <= pisos[i].getPosColision()[1]))
					|| (x >= pisos[i].getPosColision()[0]) && (x + ancho / 2) <= pisos[i].getPosColision()[1])
					&& (y + alto / 2) == pisos[i].getPosColision()[2]) {
				pisoActual = i;
				return true;
			}
		}
		return false;
	}

	private boolean tocandoElTecho(Piso[] pisos) {
		for (int i = 0; i < pisos.length; i++) {
			if (((((x - ancho / 2) >= pisos[i].getPosColision()[0] && ((x - ancho / 2) <= pisos[i].getPosColision()[1]))
					|| ((x + ancho / 2) >= pisos[i].getPosColision()[0]
							&& (x + ancho / 2) <= pisos[i].getPosColision()[1]))
					&& (y - alto / 2) == pisos[i].getPosColision()[3]) || ((y - alto / 2) <= 0)) {
				return true;
			}
		}
		return false;
	}

	private boolean tocandoElCostado(Piso[] pisos) {
		for (int i = 0; i < pisos.length; i++) {
			if (i != pisos.length - 1) {
				if ((((x - ancho / 2) <= pisos[i + 1].getPosColision()[1]
						&& (x + ancho / 2) >= pisos[i + 1].getPosColision()[1])
						|| ((x + ancho / 2) >= pisos[i + 1].getPosColision()[0]
								&& (x - ancho / 2) <= pisos[i + 1].getPosColision()[0]))
						&& (((y - alto / 2) < pisos[i + 1].getPosColision()[3]
								&& (y + alto / 2) > pisos[i + 1].getPosColision()[3])
								|| ((y + alto / 2) > pisos[i + 1].getPosColision()[2]
										&& (y - alto / 2) < pisos[i + 1].getPosColision()[2]))) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean estaEnElAire() {
		return estaSaltando;
	}

	public boolean estaEnElAireSuperSaltando() {
		return estaSuperSaltando;
	}

	public boolean estaSubiendo() {
		return subiendo;
	}

	public boolean estaAgachada() {
		return estaAgachada;
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
	
	public boolean getDireccion() {
		return estaCaminandoHaciaLaDerecha;
	}
	
	public boolean getDisparando() {
		return disparando;
	}
	
	public int getPisoDondeEstaParado() {
		return pisoActual;
	}
	
}
//x0 = (x - ancho / 2)  
//x1 = (x + ancho / 2)
//y0 = (y - alto / 2)
//y1 = (y + alto / 2)
