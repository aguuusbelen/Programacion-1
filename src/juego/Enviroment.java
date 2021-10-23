package juego;

import entorno.Entorno;

public class Enviroment {

	private double x;
	private double y;

	private Piso[] pisos;
	private Background fondo;

	public Enviroment(double x, double y) {
		this.x = x;
		this.y = y;
		this.pisos = new Piso[5];
		this.fondo = new Background(x, y, "imagenes/fondo.png"); 

		generarPisos();
	}

	private void generarPisos() {
		pisos[0] = new Piso(x, y + 240, "piso.png","0");
		pisos[1] = new Piso(x- 164, y + 140, "pisoSuperiores.png","1");
		pisos[2] = new Piso(x + 164, y + 40, "pisoSuperiores.png","2");
		pisos[3] = new Piso(x - 164, y - 60, "pisoSuperiores.png","3");
		pisos[4] = new Piso(x + 164, y - 160, "pisoSuperiores.png","4");
		//pisos[5] = new Piso(x - 164, y - 300, "pisoSuperiores.png","5");

	}

	public void dibujar(Entorno e) {
		fondo.dibujar(e);
		for (Piso colision : pisos) {
			colision.dibujar(e);
		}
	}

	public void dibujarColisiones(Entorno e) {
		for (Piso colision : pisos) {
			colision.dibujarColision(e);
		}
	}
	
	public Piso[] getColisiones() {
		return pisos;
	}
	
	
	
	
}
