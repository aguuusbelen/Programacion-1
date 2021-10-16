package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Personaje {
		//posici�n
		private double x;
		private double y;
		
		//dimensiones
		private double ancho;
		private double alto;
		private Image img;
		private double velocidad;

		//salto (para esquivar disparo)
		private boolean estaSaltando;
		private int contSalto;
		
		public Personaje(double x, double y, double velocidad) {
			
			this.x = x;
			this.y = y;
			this.ancho = 36;
			this.alto = 60;
			this.velocidad = velocidad;
			this.img = Herramientas.cargarImagen("PersonajeQuieto.png");
			this.estaSaltando = false;
			this.contSalto = 0;			
		}
		
		public void dibujar (Entorno e) {
			//e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);

			if (!estaSaltando) {  //para que no se superpongan las imagenes, solo dibujo si NO esta saltando
				e.dibujarImagen(img, x, y, 0, 0.75);
			}
			return;
		}
		
		public void dibujarIzquierda (Entorno e) {
			e.dibujarImagen(Herramientas.cargarImagen("PersonajeIzq().png"), x, y, 0, 0.75);
			return;
		}
		
		public void moverHaciaIzquierda (Entorno e) {
			if (x > ancho/2) {
				x -= velocidad;
			}
			if (!estaSaltando) {
				dibujarIzquierda(e);
			}
		}
		
		public void dibujarDerecha (Entorno e) {
			e.dibujarImagen(Herramientas.cargarImagen("PersonajeDer().png"), x, y, 0, 0.75);
			return;
		}
		
		public void moverHaciaDerecha (Entorno e) {
			if (x < e.ancho() - ancho/2) {
			x += velocidad;
		}
		if (!estaSaltando) {
			dibujarDerecha(e);
		}
	}
		
		public void saltar (Entorno e) {
			if (estaSaltando == true && contSalto<=29) {   //entorno.ancho()-775,entorno.alto()-40
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArriba.png"), x, y, 0, 0.75);
				y = y-2;
				contSalto++;
			} 
			else if (estaSaltando == true && contSalto>29) {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArriba.png"), x, y, 0, 0.75);
				y = y+2;
				contSalto++;
				if(contSalto == 60) {
					estaSaltando = false;
					contSalto = 0;
				}
			} else {
				estaSaltando = true;
			}
		}

		public boolean isEstaSaltando() {
			return estaSaltando;
		}
		
}
