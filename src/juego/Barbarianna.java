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
	private boolean estaEnMovimiento;
	private boolean estaEnElAire;
	private boolean estaAgachada;
	private boolean estaSuperSaltando;
	private boolean estaDisparando;
	private boolean estaCayendo;
		
	private int distanciaDelPisoCuandoSalta;
	private int altoOriginal;
	private double ultimaPosY1;
	private int pisoActual; 
	
	public Barbarianna(double x, double y, double velocidad) {
		this.x = x;
		this.y = y;
		this.ancho = 36;
		this.alto = 60;
		this.velocidad = velocidad;

		this.estaCaminandoHaciaLaDerecha = true;
		this.estaEnMovimiento = false;
		this.estaEnElAire = false;
		this.estaAgachada = false;
		this.estaSuperSaltando = false;
		this.estaDisparando = false;
		this.estaCayendo = false;
		this.distanciaDelPisoCuandoSalta = 0;
		this.altoOriginal = 60; 
		this.pisoActual = 0;
		this.ultimaPosY1 = 0;
	}

	public void dibujar(Entorno e) {
		if (!estaEnElAire) {
			if (estaAgachada) {
				if (estaCaminandoHaciaLaDerecha) {
					e.dibujarImagen(Herramientas.cargarImagen("Personaje_abajoDer.png"), x, y - 20, 0, 0.75);
				} else {
					e.dibujarImagen(Herramientas.cargarImagen("Personaje_abajoIzq.png"), x, y - 20, 0, 0.75);
				}
				return;
			}
			if (estaEnMovimiento) {
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

	public void dispararRayo() {
		estaDisparando = true;
	}
		
	public void terminoDeDisparar() {
		estaDisparando = false;
	}

	public void moverHaciaIzquierda(Entorno e, Piso[] pisos) {
		if (estaAgachada) {
			levantarse();
		}
		if (tocandoElCostado(pisos)) {
			return;
		}
		if (x > ancho / 2) {
			x -= velocidad;
		}
		estaCaminandoHaciaLaDerecha = false;
		estaEnMovimiento = true;
	}

	public void moverHaciaDerecha(Entorno e, Piso[] pisos) {
		if (estaAgachada) {
			levantarse();
		}
		if (tocandoElCostado(pisos)) {
			return;
		}
		if (x < e.ancho() - ancho / 2) {
			x += velocidad;
		}
		estaCaminandoHaciaLaDerecha = true;
		estaEnMovimiento = true;
	}

	public void estaInmovil() {
		estaEnMovimiento = false;
	}

	public void saltar(Piso[] pisos) {
		if ((estaEnElAire == true && !estaCayendo) && (!tocandoElTecho(pisos) && distanciaDelPisoCuandoSalta <= 25)) {
			y = y - 2;
			distanciaDelPisoCuandoSalta++;
		} else if (estaEnElAire == true && (tocandoElTecho(pisos) || distanciaDelPisoCuandoSalta == 26)) {
			distanciaDelPisoCuandoSalta++;
			y = y + 2;
			estaCayendo = true;
		} else if (estaEnElAire && estaCayendo) {
			y = y + 2;
			if (tocandoElPiso(pisos)) {
				estaEnElAire = false;
				estaCayendo = false;
				distanciaDelPisoCuandoSalta = 0;
			}
		} else {
			if (estaAgachada) {
				levantarse();
			}
			estaEnElAire = true;
		}

	}

	public void superSalto(Piso[] pisos) {
		if ((estaSuperSaltando == true && !estaCayendo) && (!tocandoElTecho(pisos) && distanciaDelPisoCuandoSalta <= 55)) {
			y = y - 2;
			distanciaDelPisoCuandoSalta++;
		} else if (estaSuperSaltando == true && (tocandoElTecho(pisos) || distanciaDelPisoCuandoSalta == 56)) {
			distanciaDelPisoCuandoSalta++;
			y = y + 2;
			estaCayendo = true;
		} else if (estaSuperSaltando && estaCayendo) {
			y = y + 2;
			if (tocandoElPiso(pisos)) {
				estaSuperSaltando = false;
				estaEnElAire = false;
				estaCayendo = false;
				distanciaDelPisoCuandoSalta = 0;
			}
		} else {
			if (estaAgachada) {
				levantarse();
			}
			estaSuperSaltando = true;
		}
	}

	public void agachar() {
		if (!estaEnElAire && estaAgachada == false) {
			ultimaPosY1 = (y + alto / 2);
			alto = alto / 2;
			y = y + alto / 2;
			estaAgachada = true;
		}
	}

	public void levantarse () { 
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
	
	public boolean tocaLaPC(Computadora compu) {
		return (x > compu.getX() && (y > compu.getY() - compu.getAncho() / 2 && y < compu.getY() + compu.getAncho() / 2  ) );
	}

	public boolean tocandoElPiso(Piso[] pisos) {
		for (int i = 0; i < pisos.length; i++) {
			if ((((x - ancho / 2) >= pisos[i].getSuperposicion()[0] && (x <= pisos[i].getSuperposicion()[1]))
					|| (x >= pisos[i].getSuperposicion()[0]) && (x + ancho / 2) <= pisos[i].getSuperposicion()[1])
					&& (y + alto / 2) == pisos[i].getSuperposicion()[2]) {
				pisoActual = i;
				return true;
			}
		}
		return false;
	}

	private boolean tocandoElTecho(Piso[] pisos) {
		for (int i = 0; i < pisos.length; i++) {
			if (((((x - ancho / 2) >= pisos[i].getSuperposicion()[0] && ((x - ancho / 2) <= pisos[i].getSuperposicion()[1]))
					|| ((x + ancho / 2) >= pisos[i].getSuperposicion()[0]
							&& (x + ancho / 2) <= pisos[i].getSuperposicion()[1]))
					&& (y - alto / 2) == pisos[i].getSuperposicion()[3]) || ((y - alto / 2) <= 0)) {
				return true;
			}
		}
		return false;
	}

	private boolean tocandoElCostado(Piso[] pisos) {
		for (int i = 0; i < pisos.length; i++) {
			if (i != pisos.length - 1) {
				if ((((x - ancho / 2) <= pisos[i + 1].getSuperposicion()[1]
						&& (x + ancho / 2) >= pisos[i + 1].getSuperposicion()[1])
						|| ((x + ancho / 2) >= pisos[i + 1].getSuperposicion()[0]
								&& (x - ancho / 2) <= pisos[i + 1].getSuperposicion()[0]))
						&& (((y - alto / 2) < pisos[i + 1].getSuperposicion()[3]
								&& (y + alto / 2) > pisos[i + 1].getSuperposicion()[3])
								|| ((y + alto / 2) > pisos[i + 1].getSuperposicion()[2]
										&& (y - alto / 2) < pisos[i + 1].getSuperposicion()[2]))) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean estaEnElAire() {
		return estaEnElAire;
	}

	public boolean estaEnElAireSuperSaltando() {
		return estaSuperSaltando;
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
		return estaDisparando;
	}
	
	public int getPisoDondeEstaParado() {
		return pisoActual;
	}	
}
