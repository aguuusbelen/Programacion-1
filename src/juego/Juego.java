package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	private Image fondo;

	private Entorno entorno;
	private Barbarianna barbarianna;
	private Piso[] pisos;

	private Velociraptor raptor;
	private RayoEnemigo rayoEnemigo;
	private Computadora compu;

	private Rayo rayoDeBarbarianna;
	private Rayo rayoDeVelociraptor;
	
	private int points;
	private int lives;
	private int kills;
	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Castlevania", 800, 600);
		this.fondo = Herramientas.cargarImagen("fondo.png");

		barbarianna = new Barbarianna(entorno.ancho() - 775, entorno.alto() - 102, 2.5);

		compu = new Computadora(entorno.ancho() / 2 + 15, entorno.alto() - 500);
		raptor = new Velociraptor(entorno.ancho() - 100, 90, 2);

		double x = entorno.ancho() / 2;
		double y = entorno.alto() / 2;
		pisos = new Piso[5];
		pisos[0] = new Piso(x, y + 240, "piso.png", "0");
		pisos[1] = new Piso(x - 164, y + 140, "pisoSuperiores.png", "1");
		pisos[2] = new Piso(x + 164, y + 40, "pisoSuperiores.png", "2");
		pisos[3] = new Piso(x - 164, y - 60, "pisoSuperiores.png", "3");
		pisos[4] = new Piso(x + 164, y - 160, "pisoSuperiores.png", "4");

		this.points = 0;
		this.lives = 1;
		this.kills = 0;
		// Inicia el juego!
		this.entorno.iniciar();
	}

	public void tick() {
		

		// --------------------FONDO----------------------
		entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0);
		// --------------------PISOS----------------------
		for (Piso p : pisos) {
			p.dibujar(entorno);
		}


		compu.dibujar(entorno);
		
		if(lives < 0) {
			entorno.dibujarImagen(Herramientas.cargarImagen("gameOver.jpg"), entorno.ancho() / 2, entorno.alto() / 2, 0);
			entorno.cambiarFont("sans", 24, Color.RED);
			entorno.escribirTexto("points: " + points , entorno.ancho() / 2 -150, entorno.alto() /2 + 150);
			entorno.escribirTexto("kills: " + kills ,entorno.ancho()-220, entorno.alto() /2 + 150);
			return;
		}else if(barbarianna == null) {
			barbarianna = new Barbarianna(entorno.ancho() - 775, entorno.alto() - 102, 2.5);
			lives -= 1;
		}else {
			
			
			// --------------------BARBARIANNA----------------------
			barbarianna.dibujar(entorno);
			
			if(barbarianna.tocaLaPC(compu)) {
				
				entorno.dibujarImagen(Herramientas.cargarImagen("win.jpg"), entorno.ancho() / 2, entorno.alto() / 2, 0);
				entorno.cambiarFont("sans", 24, Color.GREEN);
				entorno.escribirTexto("points: " + (points + 100) , entorno.ancho() / 2 -150, entorno.alto() /2 + 150);
				entorno.escribirTexto("kills: " + kills ,entorno.ancho()-220, entorno.alto() /2 + 150);
				
				return;
			}
			
			
		//	barbarianna.dibujarColision(entorno);
			if ((barbarianna.estaEnElAire() && entorno.estaPresionada('u')) || barbarianna.estaEnElAireSuperSaltando()) {
				barbarianna.saltar2(pisos);
			} else if ((entorno.estaPresionada('w') || barbarianna.estaEnElAire())
					&& !barbarianna.estaEnElAireSuperSaltando()) {
				barbarianna.saltar(pisos);
			} else {
				barbarianna.estaQuieto();
			}
			
			
			
			if (barbarianna.getDisparando()) {
				rayoDeBarbarianna.mover();
				rayoDeBarbarianna.dibujar(entorno);		

				
				if (!rayoDeBarbarianna.getDisparado() ) {
					rayoDeBarbarianna = null;
					barbarianna.terminoDeDisparar();
				}else if(raptor != null) {
					if(rayoDeBarbarianna.impactaEnemigo(raptor)) {
						rayoDeBarbarianna = null;
						barbarianna.terminoDeDisparar();
						points += 30;
						kills++;
					}
				}
				
				
			} else if (entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
				rayoDeBarbarianna = new Rayo(barbarianna.getX(), barbarianna.getY(), 5, barbarianna.getDireccion());
				barbarianna.dispararRayo();
			}
			if (entorno.estaPresionada('a') && barbarianna.tocandoElPiso(pisos)) {
				barbarianna.moverHaciaIzquierda(entorno, pisos);
			} else if (entorno.estaPresionada('a') && !barbarianna.tocandoElPiso(pisos) && !barbarianna.estaEnElAire()) {
				barbarianna.caer(pisos);
			} else if (entorno.estaPresionada('a') && barbarianna.estaEnElAire()) {
				barbarianna.moverHaciaIzquierda(entorno, pisos);

			} else if (entorno.estaPresionada('d') && barbarianna.tocandoElPiso(pisos)) {
				barbarianna.moverHaciaDerecha(entorno, pisos);
			} else if (entorno.estaPresionada('d') && !barbarianna.tocandoElPiso(pisos) && !barbarianna.estaEnElAire()) {
				barbarianna.caer(pisos);
			} else if (entorno.estaPresionada('d') && barbarianna.estaEnElAire()) {
				barbarianna.moverHaciaDerecha(entorno, pisos);
			} else if (entorno.estaPresionada('s') && barbarianna.tocandoElPiso(pisos)) {
				barbarianna.agacharse();
			} else {
				if (!barbarianna.tocandoElPiso(pisos) && !barbarianna.estaEnElAire()) {
					barbarianna.caer(pisos);
				} else if (barbarianna.estaAgachada()) {
					barbarianna.levantar();
				}
			}
			// --------------------BARBARIANNA----------------------
		}
	
		
		

		// --------------------VELOCIRAPTOR----------------------
		if (raptor != null) {
			raptor.dibujar(entorno);

			
			if (raptor.getDisparando()) {
				rayoDeVelociraptor.mover();
				rayoDeVelociraptor.dibujar(entorno);
				if (!rayoDeVelociraptor.getDisparado()) {
					rayoDeVelociraptor = null;
					raptor.terminoDeDisparar();
				}else if( rayoDeVelociraptor.impactaPersonaje(barbarianna) ) {
					rayoDeVelociraptor = null;
					barbarianna = null;
					raptor.terminoDeDisparar();
				}
			
				
	
				
				
				
				
				
				
				
				
			}
			
			if (!raptor.tocandoElPiso(pisos) && !raptor.estaEnElAire()) {
				raptor.caer(pisos);

			} else if (raptor.tocandoElPiso(pisos)) {

				if (!raptor.tocaLaPared(entorno)) {
					raptor.caminar();
				} else {
					raptor.girar();
					raptor.caminar();
				}
				
				
				if(barbarianna != null) {
					if(raptor.getPisoDondeEstaParado() == barbarianna.getPisoDondeEstaParado() ) {
						
						if((raptor.getX() > barbarianna.getX()) && !raptor.getDireccion() ) {
							if(!raptor.getDisparando()) {
								rayoDeVelociraptor = new Rayo(raptor.getX(), raptor.getY()-15, 5, raptor.getDireccion());
								raptor.dispararRayo();								
							}
						}else if((raptor.getX() < barbarianna.getX()) && raptor.getDireccion() ) {
							if(!raptor.getDisparando()) {
								rayoDeVelociraptor = new Rayo(raptor.getX(), raptor.getY() -15 , 5, raptor.getDireccion());
								raptor.dispararRayo();								
							}
						}
						
					}					
				}
				
				
				
				
				
				
				
				
				if (raptor.getPisoDondeEstaParado() == 0) {
					if (raptor.tocaLaPared(entorno) && !raptor.getDireccion()) {
						raptor = null;
					}
				}
			}

		}

		
		
		
		
		
		
		
		
	

//		if (personaje.getRayo() != null && raptor != null && personaje.getRayo().chocasteConVelociraptor(raptor)) {
//			raptor = null;
//			personaje.destruirRayo();
//		}
//		personaje.avanzarDisparo();
		
		//-------------------TEXTO--------------------------
		entorno.cambiarFont("sans", 24, Color.BLUE);
		entorno.escribirTexto("lives: " + lives , 40, entorno.alto() - 20);
		entorno.escribirTexto("points: " + points , entorno.ancho() / 2 -40, entorno.alto() - 20);
		entorno.escribirTexto("kills: " + kills ,entorno.ancho()-120, entorno.alto() - 20);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}
