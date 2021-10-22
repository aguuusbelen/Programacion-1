package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Background {
	private double ancho;
	private double alto;

	private Image img;
	
	
	public Background(double ancho, double alto ,String img) {
		this.ancho = ancho;
		this.alto = alto;
		this.img = Herramientas.cargarImagen(img);
	}
	
	public void dibujar(Entorno e) {
		e.dibujarImagen(img, ancho, alto, 0);
	}
	
}
