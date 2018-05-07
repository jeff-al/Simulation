package simulacion;

import java.lang.*;

public class Eventos {
    
    char tipo;
    int tiempo;
    double numeroGen;
    int cliente;
    
    
    public Eventos(){
    this.tipo = 'E';
    this.tiempo = 0;
    this.numeroGen = 0.00;
    this.cliente = 1;
    }
    
    public void GenEvento(char tipo, double tabla[][], int reloj, int cliente) {
        this.tipo = tipo;
        double numero = Math.random();
        this.numeroGen = numero;
        this.cliente = cliente;
        
        for (int i = 0; i < tabla.length; i++) {
            if (numero < tabla[i][1]) {
                this.tiempo = reloj + (int) tabla[i][0];
                break;
            }
        }

    }
}
