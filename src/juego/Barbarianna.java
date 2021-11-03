package juego;

import entorno.Entorno;
import entorno.Herramientas;

public class Barbarianna {
	
	private double x;
	private double y;
	private double auxY; // ?
	private double ancho;
	private double alto;
	private double velocidad;

	private boolean estoyCaminandoHaciaLaDerecha;
	private boolean meEstoyMoviendo;
	private boolean estoyEnElAire;
	private boolean estoyAgachada;
	private boolean meEstoyCayendo;

	private int distanciaDelPisoCuandoSalta;
	private int altoOriginal;
	private int alturaAgachada;
	private int pisoActual;
	private boolean estoySubiendoUnPisoIzq;
	private boolean estoySubiendoUnPisoDer;
	private boolean tengoQueActualizarPisos; // ?
	
	private Piso pisoAbajoDeBarbarianna;
	private Piso pisoActualDeBarbarianna;
	private Piso pisoArribaDeBarbarianna;

	public Barbarianna(double x, double y, double velocidad) {
		this.x = x;
		this.y = y;
		this.auxY = y;
		this.ancho = 30; // 30
		this.alto = 48; // 48
		this.altoOriginal = 48;
		this.alturaAgachada = 30;
		this.velocidad = velocidad;

		this.estoyCaminandoHaciaLaDerecha = true;
		this.meEstoyMoviendo = false;
		this.estoyEnElAire = false;
		this.estoyAgachada = false;
		this.distanciaDelPisoCuandoSalta = 0;

		this.pisoActual = 0;

		this.estoySubiendoUnPisoIzq = false;
		this.estoySubiendoUnPisoDer = false;
		this.meEstoyMoviendo = false;
		this.meEstoyCayendo = false;
		this.tengoQueActualizarPisos = true;
	}

	public void Actualizar(Entorno e) {
		if (pisoActualDeBarbarianna != null && pisoActualDeBarbarianna.getX() < e.ancho() / 2
				&& x > pisoActualDeBarbarianna.getX() + pisoActualDeBarbarianna.getAncho() / 2
				&& estoyEnElAire == false) {
			meEstoyCayendo = true;
		} else if (pisoActualDeBarbarianna != null && pisoActualDeBarbarianna.getX() > e.ancho() / 2
				&& x < pisoActualDeBarbarianna.getX() - pisoActualDeBarbarianna.getAncho() / 2
				&& estoyEnElAire == false) {
			meEstoyCayendo = true;
		}
		if (meEstoyCayendo == true) {
			caer();
			if (pisoAbajoDeBarbarianna.getY() - pisoAbajoDeBarbarianna.getAlto() / 2 <= y + alto / 2) {
				y = pisoAbajoDeBarbarianna.getY() - pisoAbajoDeBarbarianna.getAlto() / 2 - alto / 2;
				meEstoyCayendo = false;
				auxY = y;
				tengoQueActualizarPisos = true;
			}
		}
		if (estoyEnElAire == true) {
			saltar();
		}
	}

	public void dibujar(Entorno e) {
		if (!estoyEnElAire && !estoySubiendoUnPisoDer && !estoySubiendoUnPisoIzq) {
			if (estoyAgachada == true) {
				if (estoyCaminandoHaciaLaDerecha == true) {
					e.dibujarImagen(Herramientas.cargarImagen("barbarianna_abajo_derecha.png"), x, y, 0, 0.65);
				} else {
					e.dibujarImagen(Herramientas.cargarImagen("barbarianna_abajo_izquierda.png"), x, y, 0, 0.65);
				}
			} else if (meEstoyMoviendo == true && estoyCaminandoHaciaLaDerecha == true) {
				e.dibujarImagen(Herramientas.cargarImagen("barbarianna_derecha.png"), x, y, 0, 0.65);
			} else if (meEstoyMoviendo == true && estoyCaminandoHaciaLaDerecha == false) {
				e.dibujarImagen(Herramientas.cargarImagen("barbarianna_izquierda.png"), x, y, 0, 0.65);
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("barbarianna_quieta.png"), x, y, 0, 0.65);
			}
		} else {
			if (estoySubiendoUnPisoDer == true) {
				e.dibujarImagen(Herramientas.cargarImagen("barbarianna_salto_derecha.png"), x, y, 0, 0.65);
			} else if (estoySubiendoUnPisoIzq == true) {
				e.dibujarImagen(Herramientas.cargarImagen("barbarianna_salto_izquierda.png"), x, y, 0, 0.65);
			} else if (estoyCaminandoHaciaLaDerecha == true) {
				e.dibujarImagen(Herramientas.cargarImagen("barbarianna_arriba_derecha.png"), x, y, 0, 0.65);
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("barbarianna_arriba_izquierda.png"), x, y, 0, 0.65);
			}
		}
	}

	
	// public void caer(Piso[] pisos)
	public void caer() {
		y = y + 4;
	}

	public void moverHaciaIzquierda(Entorno e) {
		if (estoySubiendoUnPisoDer == false && estoySubiendoUnPisoIzq == false) {
			if (estoyAgachada) {
				levantar();
			}
		}
		if (x > ancho / 2) {
			x -= velocidad;
		}
		estoyCaminandoHaciaLaDerecha = false;
		meEstoyMoviendo = true;
	}

	public void moverHaciaDerecha(Entorno e) {
		if (estoySubiendoUnPisoDer == false && estoySubiendoUnPisoIzq == false) {
			if (estoyAgachada) {
				levantar();
			}
			if (x < e.ancho() - ancho / 2) {
				x += velocidad;
			}

			estoyCaminandoHaciaLaDerecha = true;
			meEstoyMoviendo = true;
		}
	}

	public void estaQuieta() {
		meEstoyMoviendo = false;
		if (estoyAgachada) {
			levantar();
		}
	}

	public void levantar() {
		estoyAgachada = false;
		alto = altoOriginal;
		y = auxY;
	}

	public void agachar() {
		if (estoyEnElAire == false && estoySubiendoUnPisoDer == false && estoySubiendoUnPisoIzq == false) {
			estoyAgachada = true;
			y = auxY + (altoOriginal - alturaAgachada) / 2;
			alto = alturaAgachada;
		}
	}

	public void saltar() {
		if (meEstoyCayendo == false && estoySubiendoUnPisoDer == false && estoySubiendoUnPisoIzq == false) {
			if (estoyEnElAire == true && distanciaDelPisoCuandoSalta <= alto / 2) {
				y = y - 2;
				distanciaDelPisoCuandoSalta++;
			} else if (estoyEnElAire == true && distanciaDelPisoCuandoSalta > alto / 2) {
				y = y + 2;
				distanciaDelPisoCuandoSalta++;
				if (distanciaDelPisoCuandoSalta == alto + 2) {
					estoyEnElAire = false;
					distanciaDelPisoCuandoSalta = 0;
				}
			} else {
				if (estoyAgachada) {
					levantar();
				}
				estoyEnElAire = true;
			}
		}
	}

	// public Piso pisoEnElQueEstoyParada(Piso[] pisos) 
	public void actualizarPisos(Piso[] pisos) {
		if (tengoQueActualizarPisos == true) {
			tengoQueActualizarPisos = false;
			pisoAbajoDeBarbarianna = null;
			pisoActualDeBarbarianna = null;
			pisoArribaDeBarbarianna = null;
			for (Piso piso : pisos) {
				if (piso.getY() < y
						&& (pisoArribaDeBarbarianna == null || piso.getY() > pisoArribaDeBarbarianna.getY())) {
					pisoArribaDeBarbarianna = piso;
				} else if (piso.getY() > y
						&& (pisoActualDeBarbarianna == null || piso.getY() < pisoActualDeBarbarianna.getY())) {
					pisoAbajoDeBarbarianna = pisoActualDeBarbarianna;
					pisoActualDeBarbarianna = piso;
				} else if (piso.getY() > y && piso.getY() != pisoActualDeBarbarianna.getY()
						&& (pisoAbajoDeBarbarianna == null
								|| pisoActualDeBarbarianna.getY() == pisoAbajoDeBarbarianna.getY()
								|| pisoAbajoDeBarbarianna.getY() > piso.getY())) {
					pisoAbajoDeBarbarianna = piso;
				}
			}
		}
	}

	// public void subirDePiso(Piso[] pisos)
	
	public void cuandoSubirUnPiso(Entorno e) {
		if (pisoArribaDeBarbarianna.getX() < e.ancho() / 2) {
			estoySubiendoUnPisoIzq = true;
		} else {
			estoySubiendoUnPisoDer = true;
		}
	}

	public boolean estaHabilitadoParaSubirDePiso(Entorno e) {
		if (meEstoyCayendo == false && estoyEnElAire == false) {
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
		} else {
			return false;
		}
	}

	public void saltarUnPiso(Entorno e, Piso[] pisos) {
		if (estoyAgachada == true) {
			levantar();
		}
		if (y > pisoArribaDeBarbarianna.getY() - pisoArribaDeBarbarianna.getAlto() / 2 - alto / 2) {
			y = y - 3.5;
		} else {
			y = pisoArribaDeBarbarianna.getY() - pisoArribaDeBarbarianna.getAlto() / 2 - alto / 2;
			estoySubiendoUnPisoIzq = false;
			estoySubiendoUnPisoDer = false;
			tengoQueActualizarPisos = true;
			auxY = y;
		}
		if (estoySubiendoUnPisoIzq == true) {
			x = x - 2;
		} else if (estoySubiendoUnPisoDer == true) {
			x = x + 2;
		}
	}

	public boolean estaSubiendoUnPiso() {
		return estoySubiendoUnPisoDer || estoySubiendoUnPisoIzq;
	}

	public boolean chocasteConVelociraptor(Velociraptor[] velociraptors) {
		for (Velociraptor v : velociraptors) {
			if (v != null
					&& ((x + ancho / 2 >= v.getX() - v.getAncho() / 2 && x - ancho / 2 <= v.getX() - v.getAncho() / 2)
							|| (x - ancho / 2 <= v.getX() + v.getAncho() / 2
									&& x + ancho / 2 >= v.getX() + v.getAncho() / 2))
					&& (y - alto / 2 < v.getY() + 15 && y + alto / 2 > v.getY() + 15)) {
				return true;
			}
		}
		return false;
	}

	public boolean chocasteConRayo(Rayo[] rayoDeVelociraptors) {
		for (Rayo r : rayoDeVelociraptors) {
			if (r != null
					&& ((x - ancho / 2 <= r.getX() && x + ancho / 2 >= r.getX())
							|| (x - ancho / 2 >= r.getX() && x + ancho / 2 <= r.getX()))
					&& (y - alto / 2 < r.getY() && y + alto / 2 > r.getY())) {
				return true;
			}
		}
		return false;
	}

	public Rayo dispararRayo() {
		boolean direccionDeDisparo = estoyCaminandoHaciaLaDerecha;
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

	public int getPisoDondeEstaParado() {
		return pisoActual;
	}
	
}
