package juego;
import entorno.Entorno;
import entorno.Herramientas;

public class Rayo {
	private double x;
	private double y;
	private double velocidad;
	
	private boolean fueDisparadoHaciaLaDerecha;
	private boolean estaRenderizadoEnPantalla;

	public Rayo(double x , double y, boolean direccionDeDisparo) {
		this.x = x ;
		this.y = y ;
		this.velocidad = 4;
		this.estaRenderizadoEnPantalla = false;
		this.fueDisparadoHaciaLaDerecha = direccionDeDisparo;
		
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

//	public boolean impactaEnemigo(Velociraptor enemigo) {
//		return (((x >= enemigo.getX() - enemigo.getAncho() / 2 && x <= enemigo.getX() + enemigo.getAncho() / 2)
//				|| (x <= enemigo.getX() - enemigo.getAncho() / 2 && x >= enemigo.getX() + enemigo.getAncho() / 2))
//				&& (y > enemigo.getY() - enemigo.getAlto() / 2 && y < enemigo.getY() + enemigo.getAlto() / 2));
//	}

//	public boolean impactaPersonaje(Barbarianna personaje) {
//		return (((x >= personaje.getX() - personaje.getAncho() / 2 && x <= personaje.getX() + personaje.getAncho() / 2)
//				|| (x <= personaje.getX() - personaje.getAncho() / 2
//						&& x >= personaje.getX() + personaje.getAncho() / 2))
//				&& (y > personaje.getY() - personaje.getAlto() / 2 && y < personaje.getY() + personaje.getAlto() / 2));
//	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public boolean getEstaRenderizadoEnPantalla() {
		return estaRenderizadoEnPantalla;
	}
}