package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	private Entorno entorno;
	private Personaje personaje;
	private Enemigo enemigo;
	private Rayo rayo;
	private RayoEnemigo rayoEnemigo;
	private Mapa mapa;
	
	private Image fondo; 

	
	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Prueba del Entorno", 800, 600);
		
		personaje = new Personaje(entorno.ancho()-775,entorno.alto()-40, 2.5);
		//enemigo = new Enemigo(entorno.ancho() / 2, entorno.alto() - 15, 3);
		fondo = Herramientas.cargarImagen("fondo.jfif");
	

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick() {
		
		entorno.dibujarImagen(fondo, entorno.ancho()/2, entorno.alto()/2, 0);
		
		//movimiento del personaje
		if (entorno.estaPresionada('w') || personaje.isEstaSaltando()) {
			personaje.saltar(entorno);
		} 
		if (entorno.estaPresionada('a')) {
			personaje.moverHaciaIzquierda(entorno);
		} else if (entorno.estaPresionada('d')) {
			personaje.moverHaciaDerecha(entorno);
		} else {
			personaje.dibujar(entorno);  //para que no se superponga y dibuje constantemente la imagen sin movimiento
		}
		
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}
