package juego;

//
import java.awt.Color;
import entorno.Entorno;
import entorno.Herramientas;

//
public class Barbarianna {
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private double velocidad;
//
	private boolean estaCaminandoHaciaLaDerecha;
	private boolean estaEnMovimiento;
	private boolean estaEnElAire;
	private boolean estaAgachada;
	private boolean estaSuperSaltando;
	private boolean estaDisparando;
	private boolean estaCayendo;
	
	
	private boolean estaTocandoElPiso;
	private boolean estaTocandoElTecho;
	private boolean estaChocandoConElBordeDelPiso;
	private int distanciaDelPisoCuandoSalta;
	private int altoOriginal;
	private double ultimaPosY1;
	private int pisoActual;

//
	public Barbarianna(double x, double y, double velocidad) {

		this.x = x;
		this.y = y;
		this.ancho = 30;
		this.alto = 48;
		this.velocidad = velocidad;
//
		this.estaCaminandoHaciaLaDerecha = true;
		this.estaEnMovimiento = false;
		this.estaEnElAire = false;
		this.estaAgachada = false;
		this.estaSuperSaltando = false;
		this.estaDisparando = false;
		this.estaCayendo = false;
		this.estaTocandoElPiso = false;
		this.estaTocandoElTecho = false;
		this.estaChocandoConElBordeDelPiso = false;
		
		
		
		
		this.distanciaDelPisoCuandoSalta = 0;
		this.altoOriginal = 48;
		this.pisoActual = 0;
		this.ultimaPosY1 = 0;
	}

//
	public void dibujar(Entorno e) {
		
		System.out.println(distanciaDelPisoCuandoSalta);
		if (!estaEnElAire) {
			if (estaAgachada) {
				if (estaCaminandoHaciaLaDerecha) {
					e.dibujarImagen(Herramientas.cargarImagen("Personaje_abajoDer.png"), x, y - 15, 0, 0.65);
				} else {
					e.dibujarImagen(Herramientas.cargarImagen("Personaje_abajoIzq.png"), x, y - 15, 0, 0.65);
				}
				return;
			}
			if (estaEnMovimiento) {
				if (estaCaminandoHaciaLaDerecha) {
					e.dibujarImagen(Herramientas.cargarImagen("PersonajeDer().png"), x, y - 5, 0, 0.65);
				} else {
					e.dibujarImagen(Herramientas.cargarImagen("PersonajeIzq().png"), x, y - 5, 0, 0.65);
				}
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("PersonajeQuieto().png"), x, y - 5, 0, 0.65);
			}
		} else {
			if (estaCaminandoHaciaLaDerecha) {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaDer.png"), x, y, 0, 0.65);
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaIzq.png"), x, y, 0, 0.65);
			}
		}
	}
//
//	public void dibujarColision(Entorno e) {
//		e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
//	}
//
	public Rayo dispararRayo() {
		boolean direccionDeDisparo = estaCaminandoHaciaLaDerecha;
		return new Rayo(x, y, direccionDeDisparo);
	}
//
//	public void terminoDeDisparar() {
//		estaDisparando = false;
//	}
//
	public void moverHaciaIzquierda(Entorno e, Piso[] pisos) {
		if (estaChocandoConElBordeDelPiso(pisos)) {
			return;
		}
		if (x > ancho / 2) {
			x -= velocidad;
		}
		enMovimiento();
		estaCaminandoHaciaLaDerecha = false;

	}
//
	public void moverHaciaDerecha(Entorno e, Piso[] pisos) {
		if (estaChocandoConElBordeDelPiso(pisos)) {
			return;
		}
		if (x < e.ancho() - ancho / 2) {
			x += velocidad;
		}
		enMovimiento();
		estaCaminandoHaciaLaDerecha = true;
		
	}

	private void enMovimiento() {
		estaEnMovimiento = true;
		if (estaAgachada) {
			levantarse();
		}
	}
	
	
	public void quieta(Piso[] pisos) {
		if (!estaTocandoElPiso(pisos) && !estaEnElAire) {
			caer(pisos);
		}
		
		if (estaAgachada) {
			levantarse();
		}
		estaEnMovimiento = false;
	}
//
	
	public boolean chocasteCon(Velociraptor[] velociraptors, Rayo[] rayoDeVelociraptors) {

		for (Velociraptor v : velociraptors) {
			if(v != null) {

				return ((x + ancho / 2 >= v.getX() - v.getAncho() / 2 && x - ancho / 2 <= v.getX() - v.getAncho() / 2) || (x - ancho / 2 <= v.getX() + v.getAncho() / 2 && x + ancho / 2 >= v.getX() + v.getAncho() / 2)) && (y - alto / 2 < v.getY() + 15 && y + alto/2 > v.getY() + 15) ;
			}
		}
		
		for (Rayo r : rayoDeVelociraptors) {
			if(r != null) {

				return ((x - ancho / 2 <= r.getX() && x + ancho / 2 >= r.getX()) || (x - ancho / 2 >= r.getX() && x + ancho / 2 <= r.getX())) && (y - alto / 2 < r.getY() && y + alto/2 > r.getY()) ;
			}
		}
		return false;
		
	}

	//public boolean tocaPersonaje(Barbarianna barbariana) {
	//return (((x - ancho / 2 <= personaje.getX() + personaje.getAncho() / 2
//			&& x - ancho / 2 >= personaje.getX() - personaje.getAncho() / 2)
//			|| (x + ancho / 2 >= personaje.getX() - personaje.getAncho() / 2
//					&& x + ancho / 2 <= personaje.getX() + personaje.getAncho() / 2))
//			&& (y + 15 > personaje.getY() - personaje.getAlto() / 2
//					&& y + 15 < personaje.getY() + personaje.getAlto() / 2));
	//}

	
	public void saltar(Piso[] pisos) {
		if ((estaEnElAire && !estaCayendo) && (!estaTocandoElTecho(pisos) && distanciaDelPisoCuandoSalta <= 25)) {
			y = y - 2;
			distanciaDelPisoCuandoSalta++;
		} else if (estaEnElAire && (estaTocandoElTecho(pisos) || distanciaDelPisoCuandoSalta == 26)) {
			distanciaDelPisoCuandoSalta++;
			y = y + 2;
			estaCayendo = true;
		} else if (estaEnElAire && estaCayendo) {
			y = y + 2;
			if (estaTocandoElPiso(pisos)) {
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
		
		if ((estaSuperSaltando == true && !estaCayendo)
				&& (!estaTocandoElTecho(pisos) && distanciaDelPisoCuandoSalta <= 55)) {
			y = y - 2;
			distanciaDelPisoCuandoSalta++;
		} else if (estaSuperSaltando == true && (estaTocandoElTecho(pisos) || distanciaDelPisoCuandoSalta == 56)) {
			distanciaDelPisoCuandoSalta++;
			y = y + 2;
			estaCayendo = true;
		} else if (estaSuperSaltando && estaCayendo) {
			y = y + 2;
			if (estaTocandoElPiso(pisos)) {
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
//
	public void agachar() {
		if (!estaEnElAire && estaAgachada == false) {
			ultimaPosY1 = (y + alto / 2);
			alto = alto / 2;
			y = y + alto / 2;
			estaAgachada = true;
		}
	}
//
	public void levantarse() {
		estaAgachada = false;
		y = ultimaPosY1 - alto;
		alto = altoOriginal;
	}
//
	public void caer(Piso[] pisos) {
//		if (!estaTocandoElPiso && estaChocandoConElBordeDelPiso(pisos)) {
		if (!estaTocandoElPiso(pisos) && estaChocandoConElBordeDelPiso(pisos)) {
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
//
//	// isEmpty()
//	// isPeronista()
//	// isTocandoLaComputadora()
//	// estasTocandoLaComputadora()
	public boolean estasTocandoLaComputadora(Computadora computadora) {
		return (x > computadora.getX() && (y > computadora.getY() - computadora.getAncho() / 2 && y < computadora.getY() + computadora.getAncho() / 2));
	}
//
	public boolean estaTocandoElPiso(Piso[] pisos) {
		for (int i = 0; i < pisos.length; i++) {
			if ((((x - ancho / 2) >= pisos[i].getDimensiones()[0] && (x <= pisos[i].getDimensiones()[1]))
					|| (x >= pisos[i].getDimensiones()[0]) && (x + ancho / 2) <= pisos[i].getDimensiones()[1])
					&& (y + alto / 2) == pisos[i].getDimensiones()[2]) {
				pisoActual = i;
//				estaTocandoElPiso = true;
				return true;
			}
		}
		return false;
//		estaTocandoElPiso = false;
	}
//
	public boolean estaTocandoElTecho(Piso[] pisos) {
		for (int i = 0; i < pisos.length; i++) {
			if (((((x - ancho / 2) >= pisos[i].getDimensiones()[0]
					&& ((x - ancho / 2) <= pisos[i].getDimensiones()[1]))
					|| ((x + ancho / 2) >= pisos[i].getDimensiones()[0]
							&& (x + ancho / 2) <= pisos[i].getDimensiones()[1]))
					&& (y - alto / 2) == pisos[i].getDimensiones()[3]) || ((y - alto / 2) <= 0)) {
//				estaTocandoElTecho = true;

								return true;
			}
		}
		return false;
//		estaTocandoElTecho = false;
	}
//
	private boolean estaChocandoConElBordeDelPiso(Piso[] pisos) {
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
//					estaChocandoConElBordeDelPiso = true;
										return true;
				}
			}
		}
//		estaChocandoConElBordeDelPiso = false;
		return false;
	}
//
	public boolean estaEnElAire() {
		return estaEnElAire;
	}
//
	public boolean estaEnElAireSuperSaltando() {
		return estaSuperSaltando;
	}
//
//	public boolean estaAgachada() {
//		return estaAgachada;
//	}
//
////	public double getX() {
////		return x;
////	}
////	
////	public double getY() {
////		return y;
////	}
////	
////	public double getAncho() {
////		return ancho;
////	}
////	
////	public double getAlto() {
////		return alto;
////	}
////	
////	public boolean getDireccion() {
////		return estaCaminandoHaciaLaDerecha;
////	}
////	
////	public boolean getDisparando() {
////		return estaDisparando;
////	}
////	

	public int getPisoDondeEstaParado() {
		return pisoActual;
	}
}
