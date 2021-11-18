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
	private Computadora computadora;
	private Corazon corazon;

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
	private boolean tieneVidaExtra;

	public Juego() {
		this.entorno = new Entorno(this, "Castlevania", 800, 600);
		this.fondo = Herramientas.cargarImagen("fondo.png");
		this.gano = false;
		this.tieneVidaExtra= false;

		double x = entorno.ancho() / 2;
		double y = entorno.alto() / 2;
		this.pisos = new Piso[5];
		pisos[0] = new Piso(x, y + 240);
		pisos[1] = new Piso(x - 164, y + 140);
		pisos[2] = new Piso(x + 164, y + 40);
		pisos[3] = new Piso(x - 164, y - 60);
		pisos[4] = new Piso(x + 164, y - 160);

		this.computadora = new Computadora(entorno.ancho() / 2 + 15, entorno.alto() - 500);
		this.corazon = new Corazon(entorno.ancho() - 60, entorno.alto() - 400);  //corazon
		this.corazon = new Corazon(entorno.ancho()/2, entorno.alto() - 300);    //estrella
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

		computadora.dibujar(entorno);
		
		if (corazon != null && lives <= 2 && tieneVidaExtra == false) {
		corazon.dibujar(entorno, Herramientas.cargarImagen("corazon.png"));
		}
		if (corazon != null) {
			corazon.dibujar(entorno, Herramientas.cargarImagen("estrella.png"));
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
				if (rayoDeVelociraptors[r].getX() > entorno.ancho() || rayoDeVelociraptors[r].getX() < 0) {
					rayoDeVelociraptors[r] = null;
				}
			} else if (rayoDeVelociraptors[r] == null && velociraptors[r] != null && tiempoDeEsperaParaCrearRayo == 0) {
				rayoDeVelociraptors[r] = velociraptors[r].dispararRayo();
				tiempoDeEsperaParaCrearRayo = random.nextInt(100);
			}
			if (barbarianna.chocasteConRayo(rayoDeVelociraptors)) {
				rayoDeVelociraptors[r] = null;
				barbarianna = null;
				lives--;
				barbarianna = new Barbarianna(entorno.ancho() - 775, entorno.alto() - 96);
			}
		}

		barbarianna.dibujar(entorno);
		barbarianna.caer(entorno, pisos);
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
		} else if (entorno.estaPresionada('u') || barbarianna.estaSubiendoUnPiso()) {
			barbarianna.subirDePiso(entorno, pisos);
		} else {
			barbarianna.estaQuieta();
		}
		if (barbarianna.chocasteConVelociraptor(velociraptors)) {
			barbarianna = null;
			lives--;
			barbarianna = new Barbarianna(entorno.ancho() - 775, entorno.alto() - 96);
		}

		if (barbarianna.estaTocandoLaComputadora(computadora, pisos[4])) {
			gano = true;
		}
		
		if (barbarianna.estaTocandoElCorazon(corazon)) {
			lives++;
			corazon = null;
			tieneVidaExtra = true;
		}

		if (rayoDeBarbarianna != null) {
			rayoDeBarbarianna.dibujar(entorno);
			rayoDeBarbarianna.mover();
			if (rayoDeBarbarianna.getX() > entorno.ancho() || rayoDeBarbarianna.getX() < 0) {
				rayoDeBarbarianna = null;
			}
		}

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
