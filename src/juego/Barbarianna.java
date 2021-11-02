package juego;

import java.awt.Color;
import entorno.Entorno;
import entorno.Herramientas;

public class Barbarianna {
	private double x;
	private double y;
	private double auxPosY;
	private double ancho;
	private double alto;
	private double velocidad;

	private boolean estaCaminandoHaciaLaDerecha;
	private boolean meEstoyMoviendo;
	private boolean estaEnElAire;
	private boolean estaAgachada;
	private boolean estaCayendo;
	private boolean meEstoyCayendo;

	private int distanciaDelPisoCuandoSalta;
	private int altoOriginal;
	private int alturaAgachada;
	private int pisoActual;
	private boolean estaSubiendoUnPisoIzq;
	private boolean estaSubiendoUnPisoDer;
	private Piso pisoAbajoDeBarbarianna;
	private Piso pisoActualDeBarbarianna;
	private Piso pisoArribaDeBarbarianna;

	public Barbarianna(double x, double y, double velocidad, Piso[] pisos) {
		this.x = x;
		this.y = y;
		this.auxPosY = y;
		this.ancho = 30; //30
		this.alto = 48;  //48
		this.altoOriginal = 48;
		this.alturaAgachada = 30;
		this.velocidad = velocidad;

		this.estaCaminandoHaciaLaDerecha = true;
		this.meEstoyMoviendo = false;
		this.estaEnElAire = false;
		this.estaAgachada = false;
		this.estaCayendo = false;
		this.distanciaDelPisoCuandoSalta = 0;

		this.pisoActual = 0;

		this.estaSubiendoUnPisoIzq = false;
		this.estaSubiendoUnPisoDer = false;
		this.meEstoyMoviendo = false;
		this.meEstoyCayendo = false;
		actualizarPisos(pisos);

	}

	public void Actualizar(Entorno e) {
		if (pisoActualDeBarbarianna.getX() < e.ancho() / 2
				&& x > pisoActualDeBarbarianna.getX() + pisoActualDeBarbarianna.getAncho() / 2
				&& estaEnElAire == false) {
			meEstoyCayendo = true;
		} else if (pisoActualDeBarbarianna.getX() > e.ancho() / 2
				&& x < pisoActualDeBarbarianna.getX() - pisoActualDeBarbarianna.getAncho() / 2
				&& estaEnElAire == false) {
			meEstoyCayendo = true;
		}
		if (meEstoyCayendo == true) {
			caer();
			if (pisoAbajoDeBarbarianna.getY() - pisoAbajoDeBarbarianna.getAlto() / 2 <= y + alto / 2) {
				y = pisoAbajoDeBarbarianna.getY() - pisoAbajoDeBarbarianna.getAlto() / 2 - alto / 2;
				meEstoyCayendo = false;
				auxPosY = y;
				pisoArribaDeBarbarianna = pisoActualDeBarbarianna;
				pisoActualDeBarbarianna = pisoAbajoDeBarbarianna;
				pisoAbajoDeBarbarianna = null;
			}
		}
	}

	public void dibujar(Entorno e) {
		//e.dibujarRectangulo(x, y, ancho, alto, 0, Color.BLUE);
		if (!estaEnElAire && !estaSubiendoUnPisoDer && !estaSubiendoUnPisoIzq) {
			if (estaAgachada == true) {
				if (estaCaminandoHaciaLaDerecha == true) {
					e.dibujarImagen(Herramientas.cargarImagen("barbarianna_abajo_derecha.png"), x, y, 0, 0.65);
				} else {
					e.dibujarImagen(Herramientas.cargarImagen("barbarianna_abajo_izquierda.png"), x, y, 0, 0.65);
				}
			} else if (meEstoyMoviendo == true && estaCaminandoHaciaLaDerecha == true) {
				e.dibujarImagen(Herramientas.cargarImagen("barbarianna_derecha.png"), x, y, 0, 0.65);
			} else if (meEstoyMoviendo == true && estaCaminandoHaciaLaDerecha == false) {
				e.dibujarImagen(Herramientas.cargarImagen("barbarianna_izquierda.png"), x, y, 0, 0.65);
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("barbarianna_quieta.png"), x, y, 0, 0.65);
			}
		} else {

			if (estaSubiendoUnPisoDer == true) {
				e.dibujarImagen(Herramientas.cargarImagen("barbarianna_salto_derecha.png"), x, y, 0, 0.65);
			} else if (estaSubiendoUnPisoIzq == true) {
				e.dibujarImagen(Herramientas.cargarImagen("barbarianna_salto_izquierda.png"), x, y, 0, 0.65);
			} else if (estaCaminandoHaciaLaDerecha == true) {
				e.dibujarImagen(Herramientas.cargarImagen("barbarianna_arriba_derecha.png"), x, y, 0, 0.65);
				

			} else {
				e.dibujarImagen(Herramientas.cargarImagen("barbarianna_arriba_izquierda.png"), x, y, 0, 0.65);
				
			}
		}
	}

	public void caer() {
		y = y + 4;
	}

	public void moverHaciaIzquierda(Entorno e, Piso[] pisos) {
		if (estaSubiendoUnPisoDer == false && estaSubiendoUnPisoIzq == false) {
			if (estaAgachada) {
				levantar();
			}
		}
		if (x > ancho / 2) {
			x -= velocidad;
		}
		estaCaminandoHaciaLaDerecha = false;
		meEstoyMoviendo = true;

	}

	public void moverHaciaDerecha(Entorno e, Piso[] pisos) {

		if (estaSubiendoUnPisoDer == false && estaSubiendoUnPisoIzq == false) {
			if (estaAgachada) {
				levantar();
			}
			if (x < e.ancho() - ancho / 2) {
				x += velocidad;
			}

			estaCaminandoHaciaLaDerecha = true;
			meEstoyMoviendo = true;
		}
	}

	public void estaQuieta() {
		meEstoyMoviendo = false;
		if (estaAgachada) {
			levantar();
		}
	}

	public void levantar() {
		estaAgachada = false;
		alto = altoOriginal;
		y = auxPosY;
	}

	public void agachar() {

		if (estaEnElAire == false && estaSubiendoUnPisoDer == false && estaSubiendoUnPisoIzq == false) {
			estaAgachada = true;
			y = auxPosY + (altoOriginal - alturaAgachada) / 2;			
			alto = alturaAgachada;
		}

	}

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
				levantar();
			}
			estaEnElAire = true;
		}

	}

	public void actualizarPisos(Piso[] pisos) {
		pisoAbajoDeBarbarianna = pisoActualDeBarbarianna;
		pisoActualDeBarbarianna = null;
		pisoArribaDeBarbarianna = null;
		for (Piso piso : pisos) {
			if (piso.getY() < y && (pisoArribaDeBarbarianna == null || piso.getY() > pisoArribaDeBarbarianna.getY())) {
				pisoArribaDeBarbarianna = piso;
			} else if (piso.getY() > y
					&& (pisoActualDeBarbarianna == null || piso.getY() < pisoActualDeBarbarianna.getY())) {
				pisoActualDeBarbarianna = piso;
			}
		}
	}

	public void cuandoSubirUnPiso(Entorno e) {
		if (pisoArribaDeBarbarianna.getX() < e.ancho() / 2) {
			estaSubiendoUnPisoIzq = true;
		} else {
			estaSubiendoUnPisoDer = true;
		}
	}

	public boolean estaHabilitadoParaSubirDePiso(Entorno e) {
		if (pisoArribaDeBarbarianna != null && pisoArribaDeBarbarianna.getX() < e.ancho() / 2
				&& x > pisoArribaDeBarbarianna.getX() + pisoArribaDeBarbarianna.getAncho() / 2
				&& meEstoyCayendo == false) {
			return true;
		} else if (pisoArribaDeBarbarianna != null && pisoArribaDeBarbarianna.getX() > e.ancho() / 2
				&& x < pisoArribaDeBarbarianna.getX() - pisoArribaDeBarbarianna.getAncho() / 2
				&& meEstoyCayendo == false) {
			return true;
		} else {
			return false;
		}
	}

	public void saltarUnPiso(Entorno e, Piso[] pisos) {
		if (y > pisoArribaDeBarbarianna.getY() - pisoArribaDeBarbarianna.getAlto() / 2 - alto / 2) {
			y = y - 3.5;
		} else {
			y = pisoArribaDeBarbarianna.getY() - pisoArribaDeBarbarianna.getAlto() / 2 - alto / 2;
			estaSubiendoUnPisoIzq = false;
			estaSubiendoUnPisoDer = false;
			actualizarPisos(pisos);
			auxPosY = y;
		}
		if (estaSubiendoUnPisoIzq == true) {
			x = x - 2;
		} else if (estaSubiendoUnPisoDer == true) {
			x = x + 2;
		}
	}

	public boolean estaSubiendoUnPiso() {
		return estaSubiendoUnPisoDer || estaSubiendoUnPisoIzq;
	}

	public boolean chocasteConVelociraptor(Velociraptor[] velociraptors) {
		for (Velociraptor v : velociraptors) {
			if (v != null) {
				return ((x + ancho / 2 >= v.getX() - v.getAncho() / 2 && x - ancho / 2 <= v.getX() - v.getAncho() / 2)
						|| (x - ancho / 2 <= v.getX() + v.getAncho() / 2
								&& x + ancho / 2 >= v.getX() + v.getAncho() / 2))
						&& (y - alto / 2 < v.getY() + 15 && y + alto / 2 > v.getY() + 15);
			}
		}
		return false;
	}

	public boolean chocasteConRayo(Rayo[] rayoDeVelociraptors) {
		for (Rayo r : rayoDeVelociraptors) {
			if (r != null) {
				return ((x - ancho / 2 <= r.getX() && x + ancho / 2 >= r.getX())
						|| (x - ancho / 2 >= r.getX() && x + ancho / 2 <= r.getX()))
						&& (y - alto / 2 < r.getY() && y + alto / 2 > r.getY());
			}
		}
		return false;
	}

	public Rayo dispararRayo() {
		boolean direccionDeDisparo = estaCaminandoHaciaLaDerecha;
		return new Rayo(x, y, direccionDeDisparo);
	}

	public boolean estaTocandoLaComputadora(Computadora computadora) {
		return (x > computadora.getX() && (y > computadora.getY() - computadora.getAncho() / 2
				&& y < computadora.getY() + computadora.getAncho() / 2));
	}

	public boolean estaTocandoElPiso(Piso[] pisos) {
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

	public boolean estaTocandoElTecho(Piso[] pisos) {
		for (int i = 0; i < pisos.length; i++) {
			if (((((x - ancho / 2) >= pisos[i].getDimensiones()[0] && ((x - ancho / 2) <= pisos[i].getDimensiones()[1]))
					|| ((x + ancho / 2) >= pisos[i].getDimensiones()[0]
							&& (x + ancho / 2) <= pisos[i].getDimensiones()[1]))
					&& (y - alto / 2) == pisos[i].getDimensiones()[3]) || ((y - alto / 2) <= 0)) {
				return true;
			}
		}
		return false;
	}

	public boolean estaEnElAire() {
		return estaEnElAire;
	}

	public int getPisoDondeEstaParado() {
		return pisoActual;
	}

}
