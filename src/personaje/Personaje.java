package personaje;

import posicion.Posicion;


public class Personaje {

    public int VALOR_NULO = 0;
    public int VIDA_MAXIMA = 100;

    String nombre;
    int vida;
    Posicion posicion;
    int vision;
    double fuerza;
    double salud;

    public Personaje(String nombre, int vida, Posicion posicion, double fuerza, int vision, double salud) {
        this.nombre = nombre;
        this.vida = vida;
        this.posicion = posicion;
        this.fuerza = fuerza;
        this.vision = vision;
        this.salud = salud;
    }

    // Metodos
    public boolean estaVivo() {
        return vida > VALOR_NULO;
    }

    public void danhoRecibido(int danho){
        vida = Math.max(0, vida - danho); // (Usamos al 0 como tope inferior, no tendria sentido decir que se adeuda vida.)
    }

    // GETTERS
    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public double getFuerza() {
        return fuerza;
    }

    public double getSalud() {
        return salud;
    }

    // SETTERS
    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setSalud(double salud) {
        this.salud = salud;
    }
}
