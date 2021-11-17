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

	private boolean estoySubiendoUnPisoIzq;
	private boolean estoySubiendoUnPisoDer;

	public Barbarianna(double x, double y) {
		this.x = x;
		this.y = y;
		this.auxY = y;
		this.ancho = 30; // 30
		this.alto = 48; // 48
		this.altoOriginal = 48;
		this.alturaAgachada = 30;
		this.velocidad = 2.5;

		this.estoyCaminandoHaciaLaDerecha = true;
		this.meEstoyMoviendo = false;
		this.estoyEnElAire = false;
		this.estoyAgachada = false;
		this.distanciaDelPisoCuandoSalta = 0;

		this.estoySubiendoUnPisoIzq = false;
		this.estoySubiendoUnPisoDer = false;
		this.meEstoyCayendo = false;
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
				System.out.println("entro al segundo");
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
	
	public void caer(Entorno e, Piso[] pisos) {
		Piso pisoActualDeBarbarianna = pisoEnElQueEstoyParada(pisos);
		if (meEstoyCayendo == false) {
			if (pisoActualDeBarbarianna != null && pisoActualDeBarbarianna.getX() < e.ancho() / 2
					&& x > pisoActualDeBarbarianna.getX() + pisoActualDeBarbarianna.getAncho() / 2) {
				meEstoyCayendo = true;
				y = y + 4;
			} else if (pisoActualDeBarbarianna != null && pisoActualDeBarbarianna.getX() > e.ancho() / 2
					&& x < pisoActualDeBarbarianna.getX() - pisoActualDeBarbarianna.getAncho() / 2) {
				meEstoyCayendo = true;
				y = y + 4;
			}
		} else {
			if (pisoActualDeBarbarianna != null
					&& pisoActualDeBarbarianna.getY() - pisoActualDeBarbarianna.getAlto() / 2 <= y + alto / 2) {
				meEstoyCayendo = false;
			} else {
				y = y + 4;
			}
		}
	}

	public Piso pisoEnElQueEstoyParada(Piso[] pisos) {
		for (Piso piso : pisos) {
			if (piso.getY() - piso.getAlto() / 2 == y + alto / 2) {
				return piso;
			}
		}
		return null;
	}
	
	public void subirDePiso(Entorno e, Piso[] pisos) {
		if(meEstoyCayendo == false) {
			Piso pisoActualDeBarbarianna = pisoEnElQueEstoyParada(pisos);
			if(pisoActualDeBarbarianna != null && pisoActualDeBarbarianna.getX() == e.ancho() / 2 && (x > e.ancho() - 164 && x < e.ancho())) {
				//piso con agujero a la derecha
				estoySubiendoUnPisoIzq = true;
				pisoActualDeBarbarianna = null;
			}
			else if(pisoActualDeBarbarianna != null && pisoActualDeBarbarianna.getX() < e.ancho() / 2 && (x <  164 && x > 0)) {
				//piso con agujero a la derecha
				estoySubiendoUnPisoDer = true;
				pisoActualDeBarbarianna = null;
			} else if(pisoActualDeBarbarianna != null && pisoActualDeBarbarianna.getX() > e.ancho() / 2 && (x > e.ancho() - 164 && x < e.ancho())) {
				//piso con agujero a la izquierda
				estoySubiendoUnPisoIzq = true;
				pisoActualDeBarbarianna = null;
			}
			if(estoySubiendoUnPisoDer == true || estoySubiendoUnPisoIzq == true) {
				y = y - 2;
				if (estoyAgachada == true) {
					levantar();
				}
				if (pisoActualDeBarbarianna != null) {
					y = y + 2;
					estoySubiendoUnPisoIzq = false;
					estoySubiendoUnPisoDer = false;
					auxY = y;
				}
				if (estoySubiendoUnPisoIzq == true) {
					x = x - 2;
				} else if (estoySubiendoUnPisoDer == true) {
					x = x + 2;
				}
			}
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

	public boolean estaTocandoLaComputadora(Computadora computadora, Piso ultimoPiso) {
		return (x > computadora.getX() && y < ultimoPiso.getY());
				//(y > computadora.getY() - computadora.getAlto() / 2 && y < computadora.getY() + computadora.getAlto() / 2));
	}
	
	public boolean estoySaltando() {
		return estoyEnElAire;
	}
}
