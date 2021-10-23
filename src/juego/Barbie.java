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
	private boolean estaCayendo;
	
	
	private boolean estaAgachado;
	private int contSaltoInicial;
	private int contSaltoFinal;
	private boolean caminaDerecha;


	public Barbie(double x, double y, double velocidad) {

		this.x = x;
		this.y = y;
		this.aux = y;
		this.ancho = 30;
		this.alto = 44;
		this.velocidad = velocidad;
		this.estaSaltando = false;
		this.estaAgachado = false;
		this.contSaltoInicial = 0;
		this.contSaltoFinal = 20;
		this.caminaDerecha = true;
		this.estaCayendo = true;

	}
	
	// {Xinicial, Xfinal, Ybase, Ytop}

	

	public void dibujar(Entorno e, String img) {
		e.dibujarImagen(Herramientas.cargarImagen(img), x, y, 0, .6);
	}

	
	
	//--------------------TECLA A -- MOVER A LA IZQUIERDA-----------------------
	public void moverHaciaIzquierda(Entorno e) {
		if (x > ancho / 2) {
			x -= velocidad;
			dibujar(e,"PersonajeIzq().png");
		}
		caminaDerecha = false;
	}
	
	public void moverHaciaIzquierdaSaltando(Entorno e) {
		if (x > ancho / 2) {
			x -= velocidad;
			dibujar(e,"Personaje_esquivarArribaIzq.png");
		}
		caminaDerecha = false;
	}
	//--------------------TECLA D -- MOVER A LA DERECHA-----------------------
	public void moverHaciaDerecha(Entorno e) {
		if (x < e.ancho() - ancho / 2) {
			x += velocidad;
			dibujar(e, "PersonajeDer().png");
		}
		caminaDerecha = true;
	}
	public void moverHaciaDerechaSaltando(Entorno e) {
		if (x < e.ancho() - ancho / 2) {
			x += velocidad;
			dibujar(e, "Personaje_esquivarArribaDer.png");
		}
		caminaDerecha = true;
	}
	
	
	
	private boolean isColisionando(Piso[] pisos) {
			//for (Piso colision : pisos) {
		
			System.out.println(y);
			
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
			}else if ( y + (alto/2) == colision.getPosColision()[3] ) { // 3 es la posicion en Y de arriba de los pisos
				contSaltoInicial = 0;
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
	
	
	public void rebotar(Entorno e) {
		System.out.println("REINICIADO");
		estaSaltando = false;
		contSaltoInicial = 0;
		caer(e);
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
		
		if (estaSaltando && contSaltoInicial <= 22  ) {
			//dibujar(e, "Personaje_esquivarArribaDer.png");
			y = y - 2;
			contSaltoInicial++;
			//System.out.println(contSaltoInicial);
		}else if (estaSaltando && contSaltoInicial > 22  ) {
			//dibujar(e, "Personaje_esquivarArribaDer.png");
			//estaSaltando = false;
			estaCayendo = true;
			caer(e);
			contSaltoInicial++;		
		
		}
		System.out.println(contSaltoInicial);
		if (contSaltoInicial == 50) {
			System.out.println("REINICIADO");
				estaSaltando = false;
		
				contSaltoInicial = 0;
			
		}else{
			if(contSaltoInicial == 0) {
				
				estaSaltando = true;
			}
		}
		
		
		
			
			
			

		
	}
	

	public void caer(Entorno e) {
		y = y + 2;
		estaCayendo = true;
		estaSaltando = false;
		//System.out.println("Esta cayendo");
		//dibujar(e,"PersonajeQuieto.png");
	}
	  
	
	public boolean getEstaSaltando() {
		return estaSaltando;
	}
	
	public boolean getEstaCayendo() {
		return estaCayendo;
	}
	
	//----------------------------TEST ----------------------------------
	
	public void dibujarColision(Entorno e) {
		e.dibujarRectangulo(x, y - (alto/2), ancho, 2, 0, Color.GREEN);	//yTOP --  y - (alto/2)
		e.dibujarRectangulo(x, y + (alto/2), ancho, 2, 0, Color.GREEN);	//yBASE --  y + (alto/2)
		e.dibujarRectangulo(x-ancho/2, y , 2, alto, 0, Color.GREEN);	//xINICIAL --  x-ancho/2
		e.dibujarRectangulo(x+ancho/2, y , 2, alto, 0, Color.GREEN);	//xINICIAL --  x+ancho/2
		
	//	e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
		
	}
	
	public double getX() {
		return x;
	}



	public double getY() {
		return y;
	}


	public double getAncho() {
		return ancho;
	}

	public double getAlto() {
		return alto;
	}



	public boolean isCaminaDerecha() {
		return caminaDerecha;
	}


}
