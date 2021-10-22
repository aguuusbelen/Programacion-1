package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	//private Background fondo;
	private Enviroment enviroment;

	private Entorno entorno;
	private Barbie personaje;
	private Enemigo enemigo;
	private RayoBarbie rayo;
	private RayoEnemigo rayoEnemigo;
	private Computadora compu;

	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Prueba del Entorno", 800, 600);

		enviroment = new Enviroment(entorno.ancho() / 2, entorno.alto() / 2); // Creamos todo el ambiente.

		personaje = new Barbie(entorno.ancho() - 775, 0, 2.5); // Creamos el personaje.
		compu = new Computadora(entorno.ancho()/2+15,entorno.alto()-500);
		// enemigo = new Enemigo(entorno.ancho() / 2, entorno.alto() - 15, 3);

		// Inicia el juego!

		this.entorno.iniciar();
	}

	public void tick() {

		enviroment.dibujar(entorno); // Dibujamos todo el ambiente.	
		compu.dibujar(entorno);

		//DONDE ESTA EL PERSONAJE???????????
		if (personaje.estaSobreElPiso(enviroment.getColisiones())) {
			System.out.println("Esta en el piso");
			// Puede moverse...
			if (entorno.estaPresionada('w')) {
				//personaje.saltar(entorno , enviroment.getColisiones() );
				personaje.saltar(entorno);
			}else if(entorno.estaPresionada('a')) {
				personaje.moverHaciaIzquierda(entorno);
			} else if (entorno.estaPresionada('d')) {
				personaje.moverHaciaDerecha(entorno);
			} else if (entorno.estaPresionada('s')) {
				//personaje.agacharse(entorno);
			} else {
				//personaje.dibujar(entorno); // para que no se superponga y dibuje constantemente la imagen sin movimiento
			
			}
		}else {
			//Esta callendo.... o esta saltando....
			if (personaje.getEstaSaltando()) {
				 personaje.saltar(entorno);
				 
				
				if(entorno.estaPresionada('a')) {
					personaje.moverHaciaIzquierda(entorno);
				} else if (entorno.estaPresionada('d')) {
					personaje.moverHaciaDerecha(entorno);
				}
			}else {
				personaje.caer(entorno);				
			}
		}
		
		
		// movimiento del personaje
		
		
		
		//--------------------------------TEST------------------------------------
		enviroment.dibujarColisiones(entorno);
		personaje.dibujarColision(entorno);

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}
