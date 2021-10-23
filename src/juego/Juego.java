package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	private Background fondo;
	private Enviroment enviroment;

	private Entorno entorno;
	private Barbie personaje;
	private Velociraptor raptor;
	private RayoBarbie rayo;
	private RayoEnemigo rayoEnemigo;
	private Computadora compu;

	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Prueba del Entorno", 800, 600);

		enviroment = new Enviroment(entorno.ancho() / 2, entorno.alto() / 2);

		personaje = new Barbie(entorno.ancho() - 775, entorno.alto() - 90, 2.5); 

		compu = new Computadora(entorno.ancho() / 2 + 15, entorno.alto() - 500);

		// enemigo = new Enemigo(entorno.ancho() / 2, entorno.alto() - 15, 3);
		rayo = null;

		// Inicia el juego!
		this.entorno.iniciar();
	}

	public void tick() {

		enviroment.dibujar(entorno); 
		compu.dibujar(entorno);

		// movimiento del personaje
		if (entorno.estaPresionada('w') || personaje.estaSaltando()) {
			personaje.saltar(entorno);

		}

		if (entorno.estaPresionada(entorno.TECLA_ESPACIO) && rayo == null) {
			if (personaje.isCaminaDerecha()) {
				rayo = new RayoBarbie(personaje.getX() + personaje.getAncho() / 2, personaje.getY(), 2,
						personaje.isCaminaDerecha());
				

			} else {
				rayo = new RayoBarbie(personaje.getX() - personaje.getAncho() / 2, personaje.getY(), 2,
						personaje.isCaminaDerecha());
				

			}
			
		}
		if (rayo != null) {
			rayo.avanzar(entorno);
			if (rayo.getX() > entorno.ancho() || rayo.getX()<0) {
				rayo = null;
			}
		}
		if (entorno.estaPresionada('u')) {
			personaje.subirUnPiso(entorno);
		}
		if (entorno.estaPresionada('a')) {
			personaje.moverHaciaIzquierda(entorno);
		} else if (entorno.estaPresionada('d')) {
			personaje.moverHaciaDerecha(entorno);
		} else if (entorno.estaPresionada('s')) {
			personaje.agacharse(entorno);
		} else {
			personaje.dibujar(entorno); // para que no se superponga y dibuje constantemente la imagen sin movimiento
		}

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}
