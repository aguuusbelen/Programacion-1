package juego;

import java.awt.Color;

import entorno.Entorno;

public class Rayo {

	private double x;
	private double y;

	private double ancho;
	private double alto;

	private double velocidad;
	private boolean estaMirandoDerecha;
	 
	private boolean disparado;

	public Rayo(double x, double y, double velocidad, boolean estaMirandoDerecha) {
		this.x = x;
		this.y = y;

		this.velocidad = velocidad;
		this.estaMirandoDerecha = estaMirandoDerecha;
		this.ancho = 50;
		this.alto = 30;
		this.disparado = false;
	}

	public void mover() { // mover()
		if (x > 800 || x < 0) {
			disparado = false;
			return;
		}else {
			disparado = true;
		}
			if (estaMirandoDerecha) {
				x += velocidad;
			} else {
				x -= velocidad; // expliquenlo en el informe
			}	
	}
	
	
//	public void avanzarDisparo() {
//	if (rayo != null) {
//		rayo.avanzar();
//		if (rayo.getX() > 800 || rayo.getX() < 0) {
//			rayo = null;
//		}
//	}
//}
	
	public void dibujar(Entorno e) { // dibujar()
		e.dibujarRectangulo(x, y, ancho, alto, 0, Color.GREEN);
	}
	
	public boolean chocasteConVelociraptor(Velociraptor raptor) {
		return x > raptor.getX() - raptor.getAncho();
	}

	public double getX() {
		return x;
	}

	public boolean getDisparado() {
		return disparado;
	}
	
}
