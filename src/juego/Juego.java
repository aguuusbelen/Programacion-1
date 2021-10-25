package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	private Image fondo;

//	private Enviroment enviroment;

	private Entorno entorno;
	private Barbie personaje;
	private Piso[] pisos;

	private Velociraptor raptor;
	private RayoEnemigo rayoEnemigo;
	private Computadora compu;

	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Prueba del Entorno", 800, 600);
		this.fondo = Herramientas.cargarImagen("imagenes/fondo.png");

		personaje = new Barbie(entorno.ancho() - 775, entorno.alto() - 90, 2.5);

		compu = new Computadora(entorno.ancho() / 2 + 15, entorno.alto() - 500);

		raptor = new Velociraptor(entorno.ancho() - 400, entorno.alto() - 90, 2.5);

		double x = entorno.ancho() / 2;
		double y = entorno.alto() / 2;
		pisos = new Piso[5];
		pisos[0] = new Piso(x, y + 240, "piso.png", "0");
		pisos[1] = new Piso(x - 164, y + 140, "pisoSuperiores.png", "1");
		pisos[2] = new Piso(x + 164, y + 40, "pisoSuperiores.png", "2");
		pisos[3] = new Piso(x - 164, y - 60, "pisoSuperiores.png", "3");
		pisos[4] = new Piso(x + 164, y - 160, "pisoSuperiores.png", "4");

		// Inicia el juego!
		this.entorno.iniciar();
	}

	public void tick() {
		entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0);

		for (Piso p : pisos) {
			p.dibujar(entorno);
		}

//		enviroment.dibujar(entorno);
		compu.dibujar(entorno);
		if (raptor != null) {
			raptor.dibujar(entorno);
		}

		// movimiento del personaje
		if (entorno.estaPresionada('w') || personaje.estaEnElAire()) {
			personaje.saltar();
		}

		if (entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
			personaje.dispararRayo();
		}

		if (entorno.estaPresionada('u')) {
			personaje.subirUnPiso(entorno);
		}

		if (entorno.estaPresionada('a')) {
			personaje.moverHaciaIzquierda(entorno);
		} else if (entorno.estaPresionada('d')) {
			personaje.moverHaciaDerecha(entorno);
		} else if (entorno.estaPresionada('s')) {
			personaje.agacharse();
		} else {
			personaje.estaQuieto();
		}

		personaje.dibujar(entorno);

//		if (personaje.getRayo() != null && raptor != null && personaje.getRayo().chocasteConVelociraptor(raptor)) {
//			raptor = null;
//			personaje.destruirRayo();
//		}
//		personaje.avanzarDisparo();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}
