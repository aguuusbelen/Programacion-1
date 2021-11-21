package juego;

import java.util.Random;
import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	private Image fondo;
	private Entorno entorno;

	private Piso[] pisos;

	private Item corazon;
	private Item estrella;
	private Item computadora;
	private Item escudo;

	private Barbarianna barbarianna;
	private Rayo rayoDeBarbarianna;

	private Velociraptor[] velociraptors;
	private Rayo[] rayoDeVelociraptors;
	private double tiempoDeEsperaParaCrearVelociraptor; // para que aparezcan de manera aleatoria
	private double tiempoDeEsperaParaCrearRayo;
	private Random random;

	private int points;
	private int lives;
	private int kills;

	private boolean gano;


	public Juego() {
		this.entorno = new Entorno(this, "Castlevania", 800, 600);
		this.fondo = Herramientas.cargarImagen("fondo.png");
		this.gano = false;
		

		double x = entorno.ancho() / 2;
		double y = entorno.alto() / 2;
		this.pisos = new Piso[5];
		pisos[0] = new Piso(x, y + 240);
		pisos[1] = new Piso(x - 164, y + 140);
		pisos[2] = new Piso(x + 164, y + 40);
		pisos[3] = new Piso(x - 164, y - 60);
		pisos[4] = new Piso(x + 164, y - 160);

		this.computadora = new Item(entorno.ancho() / 2 + 15, entorno.alto() - 490);
		this.corazon = new Item(entorno.ancho() - 60, entorno.alto() - 400);
		this.estrella = new Item(entorno.ancho() - 714, entorno.alto() - 310);
		this.escudo = new Item(entorno.ancho() - 350, entorno.alto() - 285);
		this.barbarianna = new Barbarianna(entorno.ancho() - 775, entorno.alto() - 96);

		this.velociraptors = new Velociraptor[6];
		this.rayoDeVelociraptors = new Rayo[6];
		random = new Random();

		this.points = 0;
		this.lives = 3;
		this.kills = 0;
		// Inicia el juego!
		this.entorno.iniciar();
	}
	
	public void tick() {

		if (lives <= 0 && gano == false) {
			entorno.dibujarImagen(Herramientas.cargarImagen("gameOver.jpg"), entorno.ancho() / 2, entorno.alto() / 2,
					0);
			entorno.cambiarFont("sans", 24, Color.RED);
			entorno.escribirTexto("points: " + points, entorno.ancho() / 2 - 150, entorno.alto() / 2 + 150);
			entorno.escribirTexto("kills: " + kills, entorno.ancho() - 220, entorno.alto() / 2 + 150);
			return;
		}

		if (lives > 0 && gano == true) {
			entorno.dibujarImagen(Herramientas.cargarImagen("win.jpg"), entorno.ancho() / 2, entorno.alto() / 2, 0);
			entorno.cambiarFont("sans", 24, Color.GREEN);
			entorno.escribirTexto("points: " + (points + 100), entorno.ancho() / 2 - 150, entorno.alto() / 2 + 150);
			entorno.escribirTexto("kills: " + kills, entorno.ancho() - 220, entorno.alto() / 2 + 150);
			return;
		}

		entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0);
		entorno.cambiarFont("sans", 20, Color.WHITE);
		entorno.escribirTexto("lives: " + lives, 40, entorno.alto() - 20);
		entorno.escribirTexto("points: " + points, entorno.ancho() / 2 - 40, entorno.alto() - 20);
		entorno.escribirTexto("kills: " + kills, entorno.ancho() - 120, entorno.alto() - 20);

		for (Piso p : pisos) {
			p.dibujar(entorno);
		}

		computadora.dibujar(entorno, Herramientas.cargarImagen("computadora.png"));

		if (escudo != null) {
			escudo.dibujar(entorno, Herramientas.cargarImagen("escudo_frente.png"));
		}

		if (corazon != null && lives <= 2) {
			corazon.dibujar(entorno, Herramientas.cargarImagen("corazon.png"));
		}
		if (estrella != null) {
			estrella.dibujar(entorno, Herramientas.cargarImagen("estrella_arcoiris.png"));
		}

		if (tiempoDeEsperaParaCrearVelociraptor > 0) {
			tiempoDeEsperaParaCrearVelociraptor--;
		}

		if (tiempoDeEsperaParaCrearRayo > 0) {
			tiempoDeEsperaParaCrearRayo--;
		}

		for (int i = 0; i < velociraptors.length; i++) {
			if (velociraptors[i] != null) {
				velociraptors[i].dibujar(entorno);
				velociraptors[i].mover(entorno);
				velociraptors[i].caer(entorno, pisos);
				if (velociraptors[i].llegueAlFinalDelCamino() == true) {
					velociraptors[i] = null;
				} else if (rayoDeBarbarianna != null && velociraptors[i].meChocoElRayo(rayoDeBarbarianna)) {
					velociraptors[i] = null;
					rayoDeBarbarianna = null;
					kills++;
					points = points + 10;
				}
			}
			if (velociraptors[i] == null && tiempoDeEsperaParaCrearVelociraptor == 0) {
				velociraptors[i] = new Velociraptor(entorno.ancho() + 100, entorno.alto() - 502, 1.5);
				tiempoDeEsperaParaCrearVelociraptor = random.nextInt(150) + 200;
			}
		}

		for (int r = 0; r < rayoDeVelociraptors.length; r++) {
			if (rayoDeVelociraptors[r] != null) {
				rayoDeVelociraptors[r].dibujar(entorno);
				rayoDeVelociraptors[r].mover();
				if (rayoDeVelociraptors[r].salioDeLosBordes(entorno)) {
					rayoDeVelociraptors[r] = null;
				}
			} else if (rayoDeVelociraptors[r] == null && velociraptors[r] != null && tiempoDeEsperaParaCrearRayo == 0) {
				rayoDeVelociraptors[r] = velociraptors[r].dispararRayo();
				tiempoDeEsperaParaCrearRayo = random.nextInt(100);
			}
			if (barbarianna.chocasteConAlgunRayo(rayoDeVelociraptors)) {
				if (barbarianna.tieneEscudo() == true && entorno.estaPresionada('c')) {
					rayoDeVelociraptors[r] = null;
				} else {
					rayoDeVelociraptors[r] = null;
					barbarianna = null;
					lives--;
					barbarianna = new Barbarianna(entorno.ancho() - 775, entorno.alto() - 96);
				}

			}
		}

		barbarianna.dibujar(entorno);

		barbarianna.caer(entorno, pisos);
		if (barbarianna.agarraEscudo(escudo)) {
			escudo = null;
		}
		;

		if (entorno.estaPresionada('w') || barbarianna.estoySaltando()) {
			barbarianna.saltar();
		}
		if (entorno.estaPresionada(entorno.TECLA_ESPACIO) && rayoDeBarbarianna == null) {
			rayoDeBarbarianna = barbarianna.dispararRayo();
		}

		if (entorno.estaPresionada('a')) {
			barbarianna.moverHaciaIzquierda(entorno);
		} else if (entorno.estaPresionada('d')) {
			barbarianna.moverHaciaDerecha(entorno);
		} else if (entorno.estaPresionada('s')) {
			barbarianna.agachar();
		} else {
			barbarianna.quietar();
		}
		if (entorno.estaPresionada('c')) {
			barbarianna.cubrirse();
		}
		if (entorno.estaPresionada('u') || barbarianna.estaSubiendoUnPiso()) {
			barbarianna.subirDePiso(entorno, pisos);
		}
		if (barbarianna.chocasteConAlgunVelociraptor(velociraptors)) {
			barbarianna = null;
			lives--;
			barbarianna = new Barbarianna(entorno.ancho() - 775, entorno.alto() - 96);
		}

		if (barbarianna.estaTocando(computadora)) {
			gano = true;
		}

		if (barbarianna.estaTocando(corazon)) {
			lives++;
			corazon = null;
		}

		if (barbarianna.estaTocando(estrella)) {
			points += 10;
			estrella = null;
		}

		if (rayoDeBarbarianna != null) {
			rayoDeBarbarianna.dibujar(entorno);
			rayoDeBarbarianna.mover();
			if (rayoDeBarbarianna.salioDeLosBordes(entorno)) {
				rayoDeBarbarianna = null;
			}
		}

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
