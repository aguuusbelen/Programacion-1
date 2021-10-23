package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Piso {

	private double x;
	private double y;
	private Image img;
	private int ancho;
	private int alto;
	private String name;

	public Piso(double x, double y, String img , String name) {
		this.x = x;
		this.y = y;
		this.img = Herramientas.cargarImagen("pisoSuperiores.png");
		this.alto = 24;
		this.ancho = 800;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	
	public void dibujar(Entorno e) {
		e.dibujarImagen(img, x, y, 0, 1.26);
	}

	public void dibujarColision(Entorno e) {
		e.dibujarRectangulo(x, y, ancho, alto, 0, Color.BLUE);
	}

	// Getters
	// ----------------------------------------------------------------------------
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
	
	
	public double getPosYBase() {
		return y + (alto/2);
	}
	
	public double[] getPosColision() {
		double [] posColision = {x - ancho / 2,x + ancho / 2,(y + (alto/2)),y - (alto/2)}; 
		return posColision;  // {Xinicial, Xfinal, Ybase, Ytop}
	}
	
	
}
