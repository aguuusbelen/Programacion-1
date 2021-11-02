package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Piso {
	private double x;
	private double y;
	private int ancho;
	private int alto;

	private Image img;

	private double[] posicionesEnXY;

	public Piso(double x, double y, String img) {
		this.x = x;
		this.y = y;
		this.alto = 24;
		this.ancho = 800;
		this.img = Herramientas.cargarImagen("pisoSuperiores.png");
		this.posicionesEnXY = new double[4];
		generarDimensiones();
	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(img, x, y, 0, 1.26);
		//e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
	}

	private void generarDimensiones() {
		posicionesEnXY[0] = x - ancho / 2;
		posicionesEnXY[1] = x + ancho / 2;
		posicionesEnXY[2] = y - alto / 2;
		posicionesEnXY[3] = y + alto / 2;
	}

	public double[] getDimensiones() {
		return posicionesEnXY; // {Xinicial, Xfinal, YInicial, YFinal}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
	
	
}


