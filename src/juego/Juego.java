package juego;
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

	private Barbarianna barbarianna;
	private Rayo rayoDeBarbarianna;

	private Velociraptor[] velociraptors;
	private Rayo[] rayoDeVelociraptors;

	private int points;
	private int lives;
	private int kills;

	private boolean gano;
	private int time;

	public Juego() {
		this.gano = false;
		this.time = 0;

		this.entorno = new Entorno(this, "Castlevania", 800, 600);
		this.fondo = Herramientas.cargarImagen("fondo.png");
		this.computadora = new Computadora(entorno.ancho() / 2 + 15, entorno.alto() - 500);
		this.barbarianna = new Barbarianna(entorno.ancho() - 775, entorno.alto() - 100, 2.5);
		this.velociraptors = new Velociraptor[2];
		this.rayoDeVelociraptors = new Rayo[2];

		
		double x = entorno.ancho() / 2;
		double y = entorno.alto() / 2;
		this.pisos = new Piso[5];
		pisos[0] = new Piso(x, y + 240, "piso.png");
		pisos[1] = new Piso(x - 164, y + 140, "pisoSuperiores.png");
		pisos[2] = new Piso(x + 164, y + 40, "pisoSuperiores.png");
		pisos[3] = new Piso(x - 164, y - 60, "pisoSuperiores.png");
		pisos[4] = new Piso(x + 164, y - 160, "pisoSuperiores.png");
		this.barbarianna.actualizarPisos(pisos);
		


		this.points = 0;
		this.lives = 9;
		this.kills = 0;
		// Inicia el juego!
		this.entorno.iniciar();
	}

	public void tick() {
		time++;
		if (perdio())
			return;
		if (gano())
			return;
		dibujarFondo();
		dibujarPisos();
		computadora();
		velociraptor();
		barbarianna();
		textoEnPantalla();
	}

	private void barbarianna() {
		if (barbarianna == null) {
			barbarianna = new Barbarianna(entorno.ancho() - 775, entorno.alto() - 102, 2.5);
		}
		if (barbarianna.estaTocandoLaComputadora(computadora)) {
			gano = true;
		}
		if (barbarianna.chocasteConVelociraptor(velociraptors)) {
			barbarianna = null;
			lives--;
			return;
		}
		if (barbarianna.chocasteConRayo(rayoDeVelociraptors)) {
			barbarianna = null;
			lives--;
			return;
		}
		rayoBarbarianna();
		if (entorno.estaPresionada('w')) {
			barbarianna.saltar();
		}
		if (entorno.estaPresionada(entorno.TECLA_ESPACIO) && rayoDeBarbarianna == null) {
			rayoDeBarbarianna = barbarianna.dispararRayo();
		}
		if (entorno.estaPresionada('u') && barbarianna.estaHabilitadoParaSubirDePiso(entorno)) {
			barbarianna.cuandoSubirUnPiso(entorno);
		}
		if (entorno.estaPresionada('a')) {
			barbarianna.moverHaciaIzquierda(entorno);
		} else if (entorno.estaPresionada('d')) {
			barbarianna.moverHaciaDerecha(entorno);
		} else if (entorno.estaPresionada('s')) {
			barbarianna.agachar();
		} else {
			barbarianna.estaQuieta();
		}
		if (barbarianna.estaSubiendoUnPiso()) {
			barbarianna.saltarUnPiso(entorno, pisos);
		}
		barbarianna.Actualizar(entorno);
		barbarianna.actualizarPisos(pisos);
		barbarianna.dibujar(entorno);
	}

	private void dibujarFondo() {
		entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0);
	}

	private void dibujarPisos() {
		for (Piso p : pisos) {
			p.dibujar(entorno);
		}
	}

	private void computadora() {
		computadora.dibujar(entorno);
	}

	private void textoEnPantalla() {
		entorno.cambiarFont("sans", 24, Color.BLUE);
		entorno.escribirTexto("lives: " + lives, 40, entorno.alto() - 20);
		entorno.escribirTexto("points: " + points, entorno.ancho() / 2 - 40, entorno.alto() - 20);
		entorno.escribirTexto("kills: " + kills, entorno.ancho() - 120, entorno.alto() - 20);
	}

	private boolean perdio() {
		if (lives == 0) {
			entorno.dibujarImagen(Herramientas.cargarImagen("gameOver.jpg"), entorno.ancho() / 2, entorno.alto() / 2,
					0);
			entorno.cambiarFont("sans", 24, Color.RED);
			entorno.escribirTexto("points: " + points, entorno.ancho() / 2 - 150, entorno.alto() / 2 + 150);
			entorno.escribirTexto("kills: " + kills, entorno.ancho() - 220, entorno.alto() / 2 + 150);
			return true;
		}
		return false;
	}

	private boolean gano() {
		if (gano) {
			entorno.dibujarImagen(Herramientas.cargarImagen("win.jpg"), entorno.ancho() / 2, entorno.alto() / 2, 0);
			entorno.cambiarFont("sans", 24, Color.GREEN);
			entorno.escribirTexto("points: " + (points + 100), entorno.ancho() / 2 - 150, entorno.alto() / 2 + 150);
			entorno.escribirTexto("kills: " + kills, entorno.ancho() - 220, entorno.alto() / 2 + 150);
			return true;
		}
		return false;
	}

	private void velociraptor() {
		int idxV = 0;
		for (Velociraptor v : velociraptors) {
			if (v != null) {
				if (!v.getEstaVivo()) {
					velociraptors[idxV] = null;
					return;
				} else if (rayoDeBarbarianna != null && velociraptors[idxV].meChocoElRayo(rayoDeBarbarianna)) {
					velociraptors[idxV] = null;
					rayoDeBarbarianna = null;
					points += 10;
					kills++;
				}
				v.dibujar(entorno);
				v.mover(entorno, pisos);
				if (barbarianna != null) {
					if (v.getFueHallada(barbarianna) && rayoDeVelociraptors[idxV] == null) {
						rayoDeVelociraptors[idxV] = v.dispararRayo();
					}
				}
			}
			if (v == null && time > 100) {
				velociraptors[idxV] = new Velociraptor(entorno.ancho() - 100, 90, 2);
				time = 0;
			}
			idxV++;
		}
		rayoVelociraptor();
	}

	private void rayoVelociraptor() {
		int idxR = 0;
		for (Rayo r : rayoDeVelociraptors) {
			if (r != null) {
				r.dibujar(entorno);
				r.mover();
				if (!r.getEstaRenderizadoEnPantalla()) {
					rayoDeVelociraptors[idxR] = null;
				}
			}
			idxR++;
		}

	}

	private void rayoBarbarianna() {
		if (rayoDeBarbarianna != null) {
			rayoDeBarbarianna.dibujar(entorno);
			rayoDeBarbarianna.mover();
			if (!rayoDeBarbarianna.getEstaRenderizadoEnPantalla()) {
				rayoDeBarbarianna = null;
			}
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
