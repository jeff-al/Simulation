package simulacion;

import java.lang.*;

public class Eventos {
    
    char tipo; //Tipo del evento ('E' == Entrada, 'S' == Salida)
    int tiempo;  //Tiempo del evento (Tiempo de reloj + Tiempo generado)
    double numeroGen; //Numero obtenido al azar
    int cliente;  //ID del cliente que participa en el evento
    
    
    public Eventos(){
    this.tipo = 'E'; 
    this.tiempo = 0;
    this.numeroGen = 0.00;
    this.cliente = 1;
    }
    
    
    //Se genera un evento del Tipo E o S. (Parametro 1)
    //Recibe la tabla ligada a ese evento (Parametro 2)
    //Recibe el tiempo de reloj para determinar el tiempo del evento (Parametro 3)
    //Recibe el ID del cliente al que se le asigna ese evento (Parametro 4)
    public void GenEvento(char tipo, double tabla[][], int reloj, int cliente) {
        this.tipo = tipo; 
        this.numeroGen = Math.random(); // Se genera un numero al azar para el evento
        this.cliente = cliente;
        
        for (int i = 0; i < tabla.length; i++) { //Determina a cual tiempo de la tabla corresponde
            if (numeroGen < tabla[i][1]) {       
                this.tiempo = reloj + (int) tabla[i][0];
                break;
            }
        }
    }
}
