package juego;

import entorno.Entorno;

public class Enviroment {
	
	private double x;
	private double y;
	
	private Piso [] pisos;
	private Background fondo;
	
	
	public Enviroment(double x , double y) {
		this.x = x;
		this.y = y;
		this.pisos = new Piso [5];
		this.fondo = new Background(x,y,"imagenes/fondo.png");		//Imagen del fondo
		
		generarPisos();
	}
	
	private void generarPisos() {
		pisos[0] = new Piso(x,y + 240,"piso.png");
		pisos[1] = new Piso(x-82,y + 140,"pisoSuperiores.png");
		pisos[2] = new Piso(x+82,y + 40,"pisoSuperiores.png");
		pisos[3] = new Piso(x-82,y - 60,"pisoSuperiores.png");
		pisos[4] = new Piso(x+82,y - 160,"pisoSuperiores.png");
		
	}
	public void dibujar(Entorno e) {
		fondo.dibujar(e);
		for(Piso colision :pisos){
			colision.dibujar(e);
		}
	}
	
}


