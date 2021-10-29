package juego;
import entorno.Entorno;
import entorno.Herramientas;

public class Velociraptor {

	private double x;
	private double y;
	private double ancho;
	private double alto;
	private double velocidad;
	
	private boolean estaCaminandoHaciaLaDerecha; 
	private boolean estaEnMovimiento;
	private boolean estaEnElAire; 
	private boolean estaDisparando;

	private int pisoActual; //Numero del piso donde se encuentra el personaje.

	public Velociraptor(double x, double y, double velocidad) {
		this.x = x;
		this.y = y;
		this.ancho = 36;
		this.alto = 60;
		this.velocidad = velocidad;
		
		this.estaEnElAire = false;
		this.estaCaminandoHaciaLaDerecha = false;
		this.estaEnMovimiento = false;
		this.estaDisparando = false;

		this.pisoActual = 0; 
	}

	public void dibujar(Entorno e) {
		if (!estaEnElAire) {
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

	public boolean tocaPersonaje(Barbarianna personaje) {
		return (((x - ancho / 2 <= personaje.getX() + personaje.getAncho() / 2
				&& x - ancho / 2 >= personaje.getX() - personaje.getAncho() / 2)
				|| (x + ancho / 2 >= personaje.getX() - personaje.getAncho() / 2
						&& x + ancho / 2 <= personaje.getX() + personaje.getAncho() / 2))
				&& (y + 15 > personaje.getY() - personaje.getAlto() / 2
						&& y + 15 < personaje.getY() + personaje.getAlto() / 2));
	}

	public void dispararRayo() {
		estaDisparando = true;
	}

	public void terminoDeDisparar() {
		estaDisparando = false;
	}

	public boolean tocaLaPared(Entorno e) {
		return ((x - ancho / 2 < 0) || (x + ancho / 2 > e.ancho()));
	}

	public void caminar() {
		if (estaCaminandoHaciaLaDerecha) {
			moverHaciaDerecha();
		} else {
			moverHaciaIzquierda();
		}
	}

	public void girar() {
		estaCaminandoHaciaLaDerecha = !estaCaminandoHaciaLaDerecha;
	}

	public void moverHaciaIzquierda() {
		if (x > ancho / 2) {
			x -= velocidad;
		}
		estaCaminandoHaciaLaDerecha = false;
		estaEnMovimiento = true;
	}

	public void moverHaciaDerecha() {
		if (x < 800 - ancho / 2) {
			x += velocidad;
		}
		estaCaminandoHaciaLaDerecha = true;
		estaEnMovimiento = true;
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
			if ((((x - ancho / 2) >= pisos[i].getSuperposicion()[0] && (x <= pisos[i].getSuperposicion()[1]))
					|| (x >= pisos[i].getSuperposicion()[0]) && (x + ancho / 2) <= pisos[i].getSuperposicion()[1])
					&& (y + alto / 2) == pisos[i].getSuperposicion()[2]) {
				pisoActual = i;
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

//	public boolean getEstaEnElAire() {
//		return estaEnElAire;
//	}
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
//	public boolean getDireccion() {
//		return estaCaminandoHaciaLaDerecha;
//	}
//	public boolean getDisparando() {
//		return estaDisparando;
//	}
//	public int getPisoDondeEstaParado() {
//		return pisoActual;
//	}
	
}
