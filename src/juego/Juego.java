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
	private Barbarianna barbarianna;
	private Computadora compu;
	private Rayo rayoDeBarbarianna;
	private int points;
	private int lives;
	private int kills;
	private boolean statusGame;
	
	private int time;
	private Velociraptor[] raptors;
	private Rayo [] rayoDeVelociraptors;
	public Juego() {
		this.entorno = new Entorno(this, "Castlevania", 800, 600);
		this.fondo = Herramientas.cargarImagen("fondo.png");
		this.statusGame = false;

		barbarianna = new Barbarianna(entorno.ancho() - 775, entorno.alto() - 102, 2.5);
//		barbarianna = new Barbarianna(180, 30, 2.5);
		compu = new Computadora(entorno.ancho() / 2 + 15, entorno.alto() - 500);
		
		double x = entorno.ancho() / 2;
		double y = entorno.alto() / 2;
		this.time = 0;
		pisos = new Piso[5];
		pisos[0] = new Piso(x, y + 240, "piso.png");
		pisos[1] = new Piso(x - 164, y + 140, "pisoSuperiores.png");
		pisos[2] = new Piso(x + 164, y + 40, "pisoSuperiores.png");
		pisos[3] = new Piso(x - 164, y - 60, "pisoSuperiores.png");
		pisos[4] = new Piso(x + 164, y - 160, "pisoSuperiores.png");

		raptors = new Velociraptor[3];
		rayoDeVelociraptors = new Rayo[3];
				
		this.points = 0;
		this.lives = 1;
		this.kills = 0;
		// Inicia el juego!
		this.entorno.iniciar();
	}

	public void tick() {
		time++; //Contador para la aparición de enemigos.
		// --------------------FONDO----------------------
		entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0);
		// --------------------PISOS----------------------
		for (Piso p : pisos) {
			p.dibujar(entorno);
		}
		// --------------------COMPUTADORA----------------------
		compu.dibujar(entorno);
		// --------------------MAIN----------------------
		if (lives < 0) { // NO TIENE MAS VIDA.
			entorno.dibujarImagen(Herramientas.cargarImagen("gameOver.jpg"), entorno.ancho() / 2, entorno.alto() / 2,0);
			entorno.cambiarFont("sans", 24, Color.RED);
			entorno.escribirTexto("points: " + points, entorno.ancho() / 2 - 150, entorno.alto() / 2 + 150);
			entorno.escribirTexto("kills: " + kills, entorno.ancho() - 220, entorno.alto() / 2 + 150);
			return;
		} else if (barbarianna == null) { // SE EJECUTA CUANDO BARBARIANNA MUERE.
			barbarianna = new Barbarianna(entorno.ancho() - 775, entorno.alto() - 102, 2.5);
			lives -= 1;
		} else if(statusGame) { // SE EJECUTA CUANDO TOCA LA PC. 
				entorno.dibujarImagen(Herramientas.cargarImagen("win.jpg"), entorno.ancho() / 2, entorno.alto() / 2, 0);
				entorno.cambiarFont("sans", 24, Color.GREEN);
				entorno.escribirTexto("points: " + (points + 100), entorno.ancho() / 2 - 150, entorno.alto() / 2 + 150);
				entorno.escribirTexto("kills: " + kills, entorno.ancho() - 220, entorno.alto() / 2 + 150);
				return;
		}else {		
			for(int i = 0 ; i < raptors.length;i++) {
				if (raptors[i] == null ) {
					if(time >= 200) {
						raptors[i] = new Velociraptor(entorno.ancho() - 100, 90, 2);
						time = 0 ;
					}
				}else {
					// --------------------VELOCIRAPTOR----------------------				
					if(rayoDeBarbarianna != null) {
						if (rayoDeBarbarianna.impactaEnemigo(raptors[i])) {
							rayoDeBarbarianna = null;
							raptors[i]= null;
							barbarianna.terminoDeDisparar();
							points += 30;
							kills++;
						}
					}
					if (raptors[i] != null) {
						raptors[i].dibujar(entorno);
						if (raptors[i].getDisparando()) {
							rayoDeVelociraptors[i].mover(entorno);
							rayoDeVelociraptors[i].dibujar(entorno);
							if (!rayoDeVelociraptors[i].getEstaEnPantalla()) {
								rayoDeVelociraptors[i]= null;
								raptors[i].terminoDeDisparar();
							} else if (rayoDeVelociraptors[i].impactaPersonaje(barbarianna) && barbarianna != null) {			
								rayoDeVelociraptors[i] = null;
								barbarianna = null;
								raptors[i].terminoDeDisparar();
								return;
							}
						}
						if (!raptors[i].tocandoElPiso(pisos) && !raptors[i].getEstaEnElAire()) {
							raptors[i].caer(pisos);
						} else if (raptors[i].tocandoElPiso(pisos)) {
							if(raptors[i].getPisoDondeEstaParado() == 4 || raptors[i].getPisoDondeEstaParado() == 2 || raptors[i].getPisoDondeEstaParado() == 0) {
								if(raptors[i].getDireccion()) {
									raptors[i].girar();									
								}
									raptors[i].caminar();									
							}else {
								if(!raptors[i].getDireccion()) {
									raptors[i].girar();									
								}
								raptors[i].caminar();
							}
							if (barbarianna != null) {
								if(raptors[i].tocaPersonaje(barbarianna)) {
									barbarianna = null;
									return;
								}else if (raptors[i].getPisoDondeEstaParado() == barbarianna.getPisoDondeEstaParado()) {
									if ((raptors[i].getX() > barbarianna.getX()) && !raptors[i].getDireccion()) {
										if (!raptors[i].getDisparando()) {
											rayoDeVelociraptors[i] = new Rayo(raptors[i].getX(), raptors[i].getY() , 5,
													raptors[i].getDireccion());
											raptors[i].dispararRayo();
										}
									} else if ((raptors[i].getX() < barbarianna.getX()) && raptors[i].getDireccion()) {
										if (!raptors[i].getDisparando()) {
											rayoDeVelociraptors[i] = new Rayo(raptors[i].getX(), raptors[i].getY() , 5,
													raptors[i].getDireccion());
											raptors[i].dispararRayo();
										}
									}
								}
							}
							if (raptors[i].getPisoDondeEstaParado() == 0) {
								if (raptors[i].tocaLaPared(entorno) && !raptors[i].getDireccion()) {
									raptors[i] = null;
								}
							}
						}
					}
				}
			}
			// --------------------BARBARIANNA----------------------
			barbarianna.dibujar(entorno);
			// --------------------PANTALLA DE GANADOR----------------------
			if (barbarianna.tocaLaPC(compu)) {
			statusGame = true;	
			}
			if ((barbarianna.estaEnElAire() && entorno.estaPresionada('u'))
					|| barbarianna.estaEnElAireSuperSaltando()) {
				barbarianna.superSalto(pisos);
			} else if ((entorno.estaPresionada('w') || barbarianna.estaEnElAire())
					&& !barbarianna.estaEnElAireSuperSaltando()) {
				barbarianna.saltar(pisos);
			} else {
				barbarianna.estaInmovil();
			}
			if (barbarianna.getDisparando()) {
				rayoDeBarbarianna.mover(entorno);
				rayoDeBarbarianna.dibujar(entorno);
				if (!rayoDeBarbarianna.getEstaEnPantalla()) {
					rayoDeBarbarianna = null;
					barbarianna.terminoDeDisparar();
				}
			} else if (entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
				rayoDeBarbarianna = new Rayo(barbarianna.getX(), barbarianna.getY(), 5, barbarianna.getDireccion());
				barbarianna.dispararRayo();
			}
			if (entorno.estaPresionada('a') && barbarianna.tocandoElPiso(pisos)) {
				barbarianna.moverHaciaIzquierda(entorno, pisos);
			} else if (entorno.estaPresionada('a') && !barbarianna.tocandoElPiso(pisos)
					&& !barbarianna.estaEnElAire()) {
				barbarianna.caer(pisos);
			} else if (entorno.estaPresionada('a') && barbarianna.estaEnElAire()) {
				barbarianna.moverHaciaIzquierda(entorno, pisos);
			} else if (entorno.estaPresionada('d') && barbarianna.tocandoElPiso(pisos)) {
				barbarianna.moverHaciaDerecha(entorno, pisos);
			} else if (entorno.estaPresionada('d') && !barbarianna.tocandoElPiso(pisos)
					&& !barbarianna.estaEnElAire()) {
				barbarianna.caer(pisos);
			} else if (entorno.estaPresionada('d') && barbarianna.estaEnElAire()) {
				barbarianna.moverHaciaDerecha(entorno, pisos);
			} else if (entorno.estaPresionada('s') && barbarianna.tocandoElPiso(pisos)) {
				barbarianna.agachar();
			} else {
				if (!barbarianna.tocandoElPiso(pisos) && !barbarianna.estaEnElAire()) {
					barbarianna.caer(pisos);
				} else if (barbarianna.estaAgachada()) {
					barbarianna.levantarse();
				}
			}
		}
				// -------------------TEXTO--------------------------
		entorno.cambiarFont("sans", 24, Color.BLUE);
		entorno.escribirTexto("lives: " + lives, 40, entorno.alto() - 20);
		entorno.escribirTexto("points: " + points, entorno.ancho() / 2 - 40, entorno.alto() - 20);
		entorno.escribirTexto("kills: " + kills, entorno.ancho() - 120, entorno.alto() - 20);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
