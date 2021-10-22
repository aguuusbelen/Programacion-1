package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Barbie {
	// posici�n
	private double x;
	private double y;

	private double aux;

	// dimensiones
	private double ancho;
	private double alto;

	private Image img;
	private double velocidad;

	// salto (para esquivar disparo)
	private boolean estaSaltando;
	private boolean estaCallendo;
	
	private boolean estaAgachado;
	private int contSaltoInicial;
	private int contSaltoFinal;
	private boolean caminaDerecha;


	
	public Barbie(double x, double y, double velocidad) {

		this.x = x;
		this.y = y;
		this.aux = y;
		this.ancho = 36;
		this.alto = 60;
		this.velocidad = velocidad;
		this.estaSaltando = false;
		this.estaAgachado = false;
		this.contSaltoInicial = 0;
		this.contSaltoFinal = 20;
		this.caminaDerecha = true;

	}
	
	// {Xinicial, Xfinal, Ybase, Ytop}

	

	public void dibujar(Entorno e, String img) {
		e.dibujarImagen(Herramientas.cargarImagen(img), x, y, 0, 1);
	}

	
	
	//--------------------TECLA A -- MOVER A LA IZQUIERDA-----------------------
	public void moverHaciaIzquierda(Entorno e) {
		if (x > ancho / 2) {
			x -= velocidad;
			dibujar(e,"PersonajeIzq().png");
		}
	}
	//--------------------TECLA D -- MOVER A LA DERECHA-----------------------
	public void moverHaciaDerecha(Entorno e) {
		if (x < e.ancho() - ancho / 2) {
			x += velocidad;
			dibujar(e, "PersonajeDer().png");
		}
	}
	
	
	
	private boolean isColisionando(Piso[] pisos) {
			//for (Piso colision : pisos) {
		
		//	System.out.println(colision.getName());
			
	//	}
		
		
		
		for (Piso colision : pisos) {
			// ------------------------------------------------------- COLISION CON LA PARTE DE ABAJO DE LOS PISOS-------------------------------------------
			if (y - (alto/2) == colision.getPosColision()[2]) {  // 2 es la posicion en Y de abajo de los pisos
				 if(x-ancho/2 >= colision.getPosColision()[0] && x-ancho/2 <= colision.getPosColision()[1] || x+ancho/2 <= colision.getPosColision()[0] && x+ancho/2 >= colision.getPosColision()[1] ) {
					 return true;
				 }else if(x-ancho/2 <= colision.getPosColision()[0] && x-ancho/2 >= colision.getPosColision()[1] || x+ancho/2 >= colision.getPosColision()[0] && x+ancho/2 <= colision.getPosColision()[1] ) {
					 return true;
				 }
				// ------------------------------------------------------- COLISION CON LA PARTE DE ARRIBA DE LOS PISOS-------------------------------------------	
			}
			if ( y + (alto/2) == colision.getPosColision()[3]) { // 3 es la posicion en Y de arriba de los pisos
				if(x-ancho/2 >= colision.getPosColision()[0] && x-ancho/2 <= colision.getPosColision()[1] || x+ancho/2 <= colision.getPosColision()[0] && x+ancho/2 >= colision.getPosColision()[1] ) {
					 return true;
				 }else if(x-ancho/2 <= colision.getPosColision()[0] && x-ancho/2 >= colision.getPosColision()[1] || x+ancho/2 >= colision.getPosColision()[0] && x+ancho/2 <= colision.getPosColision()[1] ) {
					 return true;
				 }
			}
			
			
			
			
		}
		return false;		
	}
	
	
	
	public boolean estaSobreElPiso(Piso[] pisos) {

		return isColisionando(pisos);
	}
	
	
	
/**	
	public void saltar(Entorno e, Piso[] pisos) {
		if(isColisionando(pisos)) {
			System.out.println("SI");
		}else {			
			y = y - 2;

		}
		//if(alto )
	}
	**/
	
	public void saltar(Entorno e) {			
		
		if (estaSaltando && contSaltoInicial <= alto/2) {
			dibujar(e, "Personaje_esquivarArribaDer.png");
			y = y - 2;
			contSaltoInicial++;
			System.out.println(contSaltoInicial);
		}else if (estaSaltando && contSaltoInicial > alto/2) {
			dibujar(e, "Personaje_esquivarArribaDer.png");
			y = y + 2;
			contSaltoInicial++;			
		}else {
			estaSaltando = true;
		}
		
		if (contSaltoInicial == 63) {
			estaSaltando = false;
			contSaltoInicial = 0;
		}
		
			
			
			

		
	}
	

	public void caer(Entorno e) {
		y = y + 2;
	}
	  
	
	public boolean getEstaSaltando() {
		return estaSaltando;
	}
	
	//----------------------------TEST ----------------------------------
	
	public void dibujarColision(Entorno e) {
		e.dibujarRectangulo(x, y - (alto/2), ancho, 2, 0, Color.GREEN);	//yTOP --  y - (alto/2)
		e.dibujarRectangulo(x, y + (alto/2), ancho, 2, 0, Color.GREEN);	//yBASE --  y + (alto/2)
		e.dibujarRectangulo(x-ancho/2, y , 2, alto, 0, Color.GREEN);	//xINICIAL --  x-ancho/2
		e.dibujarRectangulo(x+ancho/2, y , 2, alto, 0, Color.GREEN);	//xINICIAL --  x+ancho/2
		
	//	e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
		
		
		
	}


}
