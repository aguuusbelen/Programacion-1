package juego;

import java.awt.Color;

import entorno.Entorno;
import entorno.Herramientas;

public class Barbarianna{

	private double x;
	private double y;

	private double ancho;
	private double alto;

	private double velocidad;
	private boolean caminaHaciaLaDerecha; //estaCaminandoHaciaLaDerecha
	private boolean meEstoyMoviendo;

	
	private int x0;
	private int x1;
	private int y0;
	private int y1;
//	private RayoBarbie rayo;

	// salto (para esquivar disparo)
	private boolean estaSaltando; // fijense el nombre
	private int contSalto; // distanciaDelPisoALosSaltos???

	// agacharse
	private boolean estaAgachado; // ella, indentidad de género

	private int altoAgachada; // alturaCuandoEstáAgachada
	private int altoOriginal;
	private double auxPosY; //??

	// saltopiso
	private int contSaltoPiso;

	private int alturaSalto;
	private int contSaltoFinal;
	private boolean subiendo;
	
	private boolean tocTecho;
	private boolean cayendo;
	public Barbarianna(double x, double y, double velocidad) {
		this.x = x;
		this.y = y;

		this.auxPosY = y;
		this.ancho = 36;
		this.alto = 60;
		this.altoOriginal = 60;
		this.altoAgachada = 40;

		this.velocidad = velocidad;
		this.estaSaltando = false;
		this.estaAgachado = false;
		this.contSalto = 0;
		this.contSaltoPiso = 0;
		this.contSaltoFinal = 0;
		
		
		
		this.caminaHaciaLaDerecha = true;
//		this.rayo = null;
		this.meEstoyMoviendo = false;
		this.subiendo = false;
		
		this.alturaSalto = 60;
		
		this.tocTecho = false;
		this.cayendo = false;
		

	}

	public void dibujar(Entorno e) {
		if (!estaSaltando) { 
			if (estaAgachado == true) {
				if (caminaHaciaLaDerecha == false) {
					e.dibujarImagen(Herramientas.cargarImagen("Personaje_abajoIzq.png"), x, y, 0, 0.75);
//					e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
				} else {
					e.dibujarImagen(Herramientas.cargarImagen("Personaje_abajoDer.png"), x, y, 0, 0.75);
//					e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
				}
			} else if (caminaHaciaLaDerecha == false && meEstoyMoviendo == true) {
				e.dibujarImagen(Herramientas.cargarImagen("PersonajeIzq().png"), x, y, 0, 0.75);
				// e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			} else if (caminaHaciaLaDerecha == true && meEstoyMoviendo == true) {
				e.dibujarImagen(Herramientas.cargarImagen("PersonajeDer().png"), x, y, 0, 0.75);
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("PersonajeQuieto().png"), x, y, 0, 0.75);
				// e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
			}
		} else {
			if(caminaHaciaLaDerecha == true) {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaDer.png"), x, y, 0, 0.75);
			} else {
				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaIzq.png"), x, y, 0, 0.75);
			}
		}
//		if (rayo != null) {
//			rayo.dibujarRayo(e);
//		}
	}

//	public void avanzarDisparo() {
//		if (rayo != null) {
//			rayo.avanzar();
//			if (rayo.getX() > 800 || rayo.getX() < 0) {
//				rayo = null;
//			}
//		}
//	}

	// está encaminado, pero tienen que repensarlo
	public void dispararRayo() {

//		if (rayo == null) {
//			if (caminaHaciaLaDerecha) {
//				rayo = new RayoBarbie(x + ancho / 2, y, 4, caminaHaciaLaDerecha);
//			} else {
//				rayo = new RayoBarbie(x - ancho / 2, y, 4, caminaHaciaLaDerecha);
//			}
//		}
		
	}

	public void moverHaciaIzquierda(Entorno e, Piso [] pisos) {
		if (estaAgachado) {
			revertirAgachar();
		}
		
		if(tocandoElCostado(pisos)) {
			System.out.println("RETURNNN");
			return;
		}
		
		if (x > ancho / 2)  {
			x -= velocidad;
		}
		caminaHaciaLaDerecha = false;
		meEstoyMoviendo = true;
	}

	public void moverHaciaDerecha(Entorno e, Piso [] pisos) {
		if (estaAgachado) {
			revertirAgachar();
		}

		if(tocandoElCostado(pisos)) {
			System.out.println("RETURNNN");
			return;
		}
		if (x < e.ancho() - ancho / 2) {
			x += velocidad;
		}
		caminaHaciaLaDerecha = true;
		meEstoyMoviendo = true;
		
		
		
		
		
		
		
		
		
		
		
	}
	
	public void estaQuieto () {
		meEstoyMoviendo = false;
		if (estaAgachado) {
			revertirAgachar();
		}
	}

	public void saltar(Piso [] pisos) {
		alturaSalto = 60;
		//if (estaSaltando == true && contSalto <= alto / 2) {
		if (estaSaltando == true && !tocandoElTecho(pisos) &&  !cayendo)  {
			
	
				y = y - 2;
				contSalto++;				
			
			
		}else if(estaSaltando == true && tocandoElTecho(pisos) ) {
			System.out.println("TOQUEEE");
			y = y + 2;
			cayendo = true;
			contSaltoFinal = contSalto;
	
		//	contSaltoFinal = contSalto;
			
			
			
		} else if(estaSaltando && cayendo) {
			y = y + 2;
			contSalto--;
			if ( tocandoElPiso(pisos) ) {
				estaSaltando = false;
				cayendo = false;
				contSalto = 0;
			}
		}
		
		
		
		else{
			if (estaAgachado) {
				revertirAgachar();
			}
			estaSaltando = true;
		}

	}

	public void agacharse() { // agachar()
		if (estaSaltando == false) {
			estaAgachado = true;
			alto = 40;
			y = auxPosY + 10;
		}
	}

	public void revertirAgachar() { // levantar() pararseDerecho()
		estaAgachado = false;
		alto = altoOriginal;
		y = auxPosY;
	}
	
	public void caer(Piso [] pisos) {
		if(!tocandoElPiso(pisos)) {
			y = y +2;
		}
	}
	
	
	public boolean tocandoElPiso(Piso [] pisos) {
		for(int i = 0 ; i <  pisos.length ; i++) {

//			if((((x - ancho / 2) >= pisos[i].getPosColision()[0] && ((x + ancho / 2) <= pisos[i].getPosColision()[1])) || ((x + ancho / 2) >= pisos[i].getPosColision()[0]) && (x + ancho / 2) <= pisos[i].getPosColision()[1]  ) && (y + alto / 2) == pisos[i].getPosColision()[2] ){
			if((((x - ancho / 2) >= pisos[i].getPosColision()[0] && (x <= pisos[i].getPosColision()[1])) || (x>= pisos[i].getPosColision()[0]) && (x + ancho / 2) <= pisos[i].getPosColision()[1]  ) && (y + alto / 2) == pisos[i].getPosColision()[2] ){
				return true;
			}
			
		}
		return false;
	}
//	x0 = (x - ancho / 2)
//	x1 = (x + ancho / 2)
//	y0 = (y - alto / 2)
//	y1 = (y + alto / 2)
	private boolean tocandoElTecho(Piso [] pisos) {
		for(int i = 0 ; i <  pisos.length ; i++) {
			
			if(((((x - ancho / 2)>= pisos[i].getPosColision()[0] && ((x - ancho / 2) <= pisos[i].getPosColision()[1])) || ((x + ancho / 2) >=pisos[i].getPosColision()[0] && (x + ancho / 2) <= pisos[i].getPosColision()[1] ) ) && (y - alto / 2) == pisos[i].getPosColision()[3]) || ((y - alto / 2) <= 0) ){
				//System.out.println("");
				return true;
			}
			
		}
		return false;
	}
	
//	x0 = (x - ancho / 2)
//	x1 = (x + ancho / 2)
//	y0 = (y - alto / 2)
//	y1 = (y + alto / 2)
	private boolean tocandoElCostado(Piso [] pisos) {
	//	System.out.println(x);
		//System.out.println(pisos[1].getPosColision()[1]);
		for(int i = 0 ; i <  pisos.length ; i++) {
		
			if (i != pisos.length - 1) {
				if((((x - ancho / 2) <= pisos[i+1].getPosColision()[1] && (x + ancho / 2) >= pisos[i+1].getPosColision()[1]) || ((x + ancho / 2) >= pisos[i+1].getPosColision()[0] && (x - ancho / 2) <= pisos[i+1].getPosColision()[0]))  && (((y - alto / 2) <  pisos[i+1].getPosColision()[3] && (y + alto / 2) >  pisos[i+1].getPosColision()[3]) ||  ((y + alto / 2) >  pisos[i+1].getPosColision()[2] && (y - alto / 2) <  pisos[i+1].getPosColision()[2]))){
					System.out.println("IGUAL");
				//	return true;
					
					//(((y - alto / 2) <  pisos[i+1].getPosColision()[3] && (y + alto / 2) >  pisos[i+1].getPosColision()[3]) ||  ((y + alto / 2) >  pisos[i+1].getPosColision()[2] && (y - alto / 2) >  pisos[i+1].getPosColision()[2]))
					
					//((x - ancho / 2) == pisos[i+1].getPosColision()[1] || (x + ancho / 2)  == pisos[i+1].getPosColision()[0])
					
					//((y + alto / 2) >pisos[i+1].getPosColision()[2] && (y + alto / 2) >pisos[i+1].getPosColision()[3])
					
					//((y - alto / 2) >pisos[i+1].getPosColision()[2] && (y - alto / 2) <pisos[i+1].getPosColision()[3])
					return true;
					
				}			
			}
			
			
		}
		return false;
	}
	
	
	
	
	
	// FIXME
	// subirUnPiso(Piso[] pisos)
	public void subirUnPiso(Entorno e, Piso[] pisos) {

//		if(subiendo == false) {
//			if(tocandoElPiso(pisos)) {
//				subiendo = true;
//				
//			}			
//		}else 
			if(estaSaltando || subiendo) {
			y -= velocidad; 
			alturaSalto = 90; 
			if (tocandoElPiso(pisos)) {
				alturaSalto = 60; 
				subiendo = false;
				estaSaltando = false;
				return;
			}
		
		}
	
		}
		
		
//		if (contSaltoPiso <= 70) {
//			y = y - 2;
//			contSaltoPiso++;
//			if (caminaHaciaLaDerecha == true) {
//				x = x + 0.5;
//				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaDer.png"), x, y, 0, 0.75);
//				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
//			} else {
//				x = x - 0.5;
//				e.dibujarImagen(Herramientas.cargarImagen("Personaje_esquivarArribaIzq.png"), x, y, 0, 0.75);
//				e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
//
//			}
//		}
		
//		x0 = (x - ancho / 2)
//		x1 = (x + ancho / 2)
//		y0 = (y - alto / 2)
//		y1 = (y + alto / 2)
		//System.out.println(pisos[0].getPosColision()[2]);
		//System.out.println(y + alto / 2);
		//System.out.println(y + alto / 2);
		//System.out.println( pisos[1].getPosColision()[2]);
	
		
		
	
		
		
		
	//}
	
//	public void destruirRayo() {
//		rayo = null;
//	}
	
//	public RayoBarbie getRayo() {
//		return rayo;
//	}

//	public boolean estaAgachado() {
//		return estaAgachado;
//
//	}
//
	public boolean estaEnElAire() {
		return estaSaltando;
	}
	public boolean estaSubiendo() {
		return subiendo;
	}
	

//	public boolean isCaminaDerecha() {
//		return caminaHaciaLaDerecha;
//	}
//
//	public double getX() {
//		return x;
//
//	}
//
//	public double getY() {
//		return y;
//	}
//
//	public double getAncho() {
//		return ancho;
//	}
//
//	public double getAlto() {
//		return alto;
//	}
}
