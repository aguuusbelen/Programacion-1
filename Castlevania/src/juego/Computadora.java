package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Computadora {
	private double x;
	private double y;

	private double ancho;
	private double alto;
	private Color color;
	private Image img;


	public Computadora(double x, double y) {
		
		this.x = x;
		this.y = y;
		this.ancho = 60;
		this.alto = 60;
		this.img = Herramientas.cargarImagen("compu.png");
		
	}

	public void dibujar (Entorno e) {
		e.dibujarRectangulo(x,y,ancho,alto,0,color.CYAN);
		e.dibujarImagen(img, x, y, 0, 0.15);
	

}
}
