package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	
	private Background fondo;
	private Enviroment enviroment;
	
	
	private Entorno entorno;
	private Personaje personaje;
	private Enemigo enemigo;
	private Rayo rayo;
	private RayoEnemigo rayoEnemigo;
	//private Mapa mapa;

	
	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Prueba del Entorno", 800, 600);
		
		enviroment = new Enviroment(entorno.ancho()/2,entorno.alto()/2); //Creamos todo el ambiente.
		
		personaje = new Personaje(entorno.ancho()-775,entorno.alto()-40, 2.5); //Creamos el personaje.
		//enemigo = new Enemigo(entorno.ancho() / 2, entorno.alto() - 15, 3);
		
			// Inicia el juego!
		this.entorno.iniciar();
	}

	public void tick() {
		
	    enviroment.dibujar(entorno); //Dibujamos todo el ambiente.
		    
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
