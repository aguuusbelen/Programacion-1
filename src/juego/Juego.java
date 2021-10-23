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

		personaje = new Barbie(entorno.ancho() - 775, entorno.alto() - 100, 2.5); // Creamos el personaje.
		compu = new Computadora(entorno.ancho()/2+15,entorno.alto()-500);
		// enemigo = new Enemigo(entorno.ancho() / 2, entorno.alto() - 15, 3);
		rayo = null;

		// Inicia el juego!

		this.entorno.iniciar();
	}

	public void tick() {

		enviroment.dibujar(entorno); // Dibujamos todo el ambiente.	
		compu.dibujar(entorno);

		
		 if (entorno.estaPresionada(entorno.TECLA_ESPACIO) && rayo == null) {
			if (personaje.isCaminaDerecha()){
				rayo = new RayoBarbie(personaje.getX() + personaje.getAncho() / 2, personaje.getY(), 2,
						personaje.isCaminaDerecha());
			} else {
				rayo = new RayoBarbie(personaje.getX() - personaje.getAncho() / 2, personaje.getY(), 2,
						personaje.isCaminaDerecha());
			}
		}
		
		if (rayo != null) {
			rayo.avanzar(entorno);
			if (rayo.getX() > entorno.ancho() || rayo.getX() < 0) {
				rayo = null;
			}
		}

		//DONDE ESTA EL PERSONAJE???????????
		if (personaje.estaSobreElPiso(enviroment.getColisiones()) && !personaje.getEstaSaltando()) {
		
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
				personaje.dibujar(entorno, "PersonajeQuieto().png"); // para que no se superponga y dibuje constantemente la imagen sin movimiento
			
			}
		}else {
			//Esta cayendo.... o esta saltando....
			if (personaje.getEstaSaltando()) {
				//System.out.println("Esta en el AIRE");
				 personaje.saltar(entorno);
				 
				 if(personaje.getEstaSaltando() && personaje.estaSobreElPiso(enviroment.getColisiones())){
					 personaje.rebotar(entorno);
	
				 }
				
				if(entorno.estaPresionada('a')) {
					personaje.moverHaciaIzquierdaSaltando(entorno);
				} else if (entorno.estaPresionada('d')) {
					personaje.moverHaciaDerechaSaltando(entorno);
				}
			}else {
				 if(personaje.getEstaCayendo() && !personaje.estaSobreElPiso(enviroment.getColisiones())){
					 personaje.caer(entorno);		
					 if(entorno.estaPresionada('a')) {
							personaje.moverHaciaIzquierdaSaltando(entorno);
						} else if (entorno.estaPresionada('d')) {
							personaje.moverHaciaDerechaSaltando(entorno);
						}
				 }
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
