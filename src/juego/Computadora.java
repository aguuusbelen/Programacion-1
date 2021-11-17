package juego;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Computadora {
	
	private double x;
	private double y;

	private Image img;

	public Computadora(double x, double y) {
		this.x = x;
		this.y = y;
		this.img = Herramientas.cargarImagen("computadora.png");
	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(img, x, y, 0, 0.15);
	}

	public double getX() {
		return x;
	}

//	public double getY() {
//		return y;
//	}
//
//	public double getAlto() {
//		return alto;
//	}
	
}
