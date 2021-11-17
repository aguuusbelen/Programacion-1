package juego;
import entorno.Entorno;
import entorno.Herramientas;

public class Rayo {
	
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private double velocidad;
	
	private boolean fueDisparadoHaciaLaDerecha; // direccion
	private boolean estaRenderizadoEnPantalla; // no vá

	public Rayo(double x , double y, boolean direccion) {
		this.x = x ;
		this.y = y ;
		this.ancho = 40;
		this.alto = 10;

		this.velocidad = 4;
		this.estaRenderizadoEnPantalla = false;
		this.fueDisparadoHaciaLaDerecha = direccion;		
	}

	public void mover() { 
		if (x > 800 || x < 0) {
			estaRenderizadoEnPantalla = false;
			return;
		} else {
			estaRenderizadoEnPantalla = true;
		}
		if (fueDisparadoHaciaLaDerecha && estaRenderizadoEnPantalla)
			x += velocidad;
		if (!fueDisparadoHaciaLaDerecha && estaRenderizadoEnPantalla)
			x -= velocidad;	
	}

	public void dibujar(Entorno e) { 
		if (fueDisparadoHaciaLaDerecha && estaRenderizadoEnPantalla) 
			e.dibujarImagen(Herramientas.cargarImagen("rayo-der.png"), x, y, 0, 1);
		if (!fueDisparadoHaciaLaDerecha && estaRenderizadoEnPantalla)
			e.dibujarImagen(Herramientas.cargarImagen("rayo-izq.png"), x, y, 0, 1);
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

//	public boolean getEstaRenderizadoEnPantalla() {
//		return estaRenderizadoEnPantalla;
//	}
	
}
