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
	private boolean meEstoyCayendo;
	private boolean llegueAlFinalDelCamino;

	private Piso pisoAbajoDeVelociraptor;
	private Piso pisoActualDeVelociraptor;

	private boolean estaVivo;

	public Velociraptor(double x, double y, double velocidad, Piso[] pisos) {
		this.x = x;
		this.y = y;
		this.ancho = 80;
		this.alto = 60;
		this.velocidad = velocidad;

		
		this.estaCaminandoHaciaLaDerecha = false;
		this.meEstoyCayendo = false;
		this.llegueAlFinalDelCamino = false;

		actualizarPisos(pisos);

		this.estaVivo = true;
	}

	public void dibujar(Entorno e) {
		if (estaCaminandoHaciaLaDerecha) {
			e.dibujarImagen(Herramientas.cargarImagen("velociraptor_derecha.png"), x, y - 5, 0, 0.60);
		} else {
			e.dibujarImagen(Herramientas.cargarImagen("velociraptor_izquierda.png"), x, y - 5, 0, 0.60);
		}
	}


	public void actualizar(Entorno e, Piso[] pisos) {
		if (estaCaminandoHaciaLaDerecha) {
			moverHaciaDerecha();
		} else {
			moverHaciaIzquierda();
		}

		if (pisoActualDeVelociraptor.getX() > e.ancho() / 2
				&& x < pisoActualDeVelociraptor.getX() - pisoActualDeVelociraptor.getAncho() / 2) {
			meEstoyCayendo = true;
		} else if (pisoActualDeVelociraptor.getX() < e.ancho() / 2
				&& x > pisoActualDeVelociraptor.getX() + pisoActualDeVelociraptor.getAncho() / 2) {
			meEstoyCayendo = true;
		}

		if (meEstoyCayendo == true) {
			caer();
			if (pisoAbajoDeVelociraptor.getY() - pisoAbajoDeVelociraptor.getAlto() / 2 <= y + alto / 2) {
				y = pisoAbajoDeVelociraptor.getY() - pisoAbajoDeVelociraptor.getAlto() / 2 - alto / 2;
				meEstoyCayendo = false;
				actualizarPisos(pisos);
			}
		}

		if (pisoActualDeVelociraptor.getX() == 540 && x < ancho / 2) {
			llegueAlFinalDelCamino = true;
		}
	}

	public void girar() {
		estaCaminandoHaciaLaDerecha = !estaCaminandoHaciaLaDerecha;
	}

	public void moverHaciaIzquierda() {
		if (x > ancho / 2) {
			x -= velocidad;
		} else {
			if (pisoAbajoDeVelociraptor == null) {
				llegueAlFinalDelCamino = true;
			} else {
				girar();
			}
		}
	}

	public void moverHaciaDerecha() {
		if (x < 800 - ancho / 2) {
			x += velocidad;
		} else {
			girar();
		}
	}
	
	public void caer() {
		y = y + 4;
	}

	
	public void actualizarPisos(Piso[] pisos) {
		pisoActualDeVelociraptor = pisoAbajoDeVelociraptor;
		pisoAbajoDeVelociraptor = null;
		for (Piso piso : pisos) {
			if (piso.getY() > y
					&& (pisoActualDeVelociraptor == null || pisoActualDeVelociraptor.getY() > piso.getY())) {
				pisoAbajoDeVelociraptor = pisoActualDeVelociraptor;
				pisoActualDeVelociraptor = piso;
			} else if (piso.getY() > y && piso.getY() != pisoActualDeVelociraptor.getY()
					&& (pisoAbajoDeVelociraptor == null
							|| pisoActualDeVelociraptor.getY() == pisoAbajoDeVelociraptor.getY()
							|| pisoAbajoDeVelociraptor.getY() > piso.getY())) {
				pisoAbajoDeVelociraptor = piso;
			}
		}
	}

	public boolean llegueAlFinalDelCamino() {
		return llegueAlFinalDelCamino;
	}


	public Rayo dispararRayo() {
		boolean direccionDeDisparo = estaCaminandoHaciaLaDerecha;
		return new Rayo(x, y, direccionDeDisparo);
	}


	public boolean meChocoElRayo(Rayo rayo) {
		return (x + ancho / 2 >= rayo.getX() - rayo.getAlto() / 2)
				&& (x - ancho / 2 <= rayo.getX() + rayo.getAlto() / 2)
				&& (y >= rayo.getY() - rayo.getAncho() / 2 && y <= rayo.getY() + rayo.getAlto() / 2);
	}

	public boolean getEstaVivo() {
		return estaVivo;
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
