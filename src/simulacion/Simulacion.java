package simulacion;

import java.util.*;
import static java.lang.System.out;

public class Simulacion {

    public static Eventos BuscarMenor(List<Eventos> lista) {
        int tiempo = 10000;
        int index = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).tiempo < tiempo) {
                index = i;
                tiempo = lista.get(i).tiempo;
            } else if (lista.get(i).tiempo == tiempo) {
                if (lista.get(i).tipo == 'S') {
                    index = i;
                }
            }

        }
        return lista.remove(index);
    }

    public static void main(String[] args) {
        double[][] tabla1 = {
            {1.00, 0.40},
            {2.00, 0.75},
            {3.00, 1.00}
        };

        double[][] tabla2 = {
            {2.00, 0.10},
            {3.00, 0.35},
            {4.00, 0.75},
            {7.00, 0.95},
            {10.00, 1.00}
        };

        int reloj = 0;

        boolean statS1 = false;
        boolean statS2 = false;

        int l = 0;
        int lq = 0;
        int salidos = 0;

        List<Eventos> lista = new ArrayList<Eventos>();
        Queue<Integer> cola = new LinkedList<Integer>();
        
        Eventos e = new Eventos(); //Para eventos de entrada
        Eventos s = new Eventos(); //Para eventos de salida
        lista.add(e); //Añade la primera entrada 

        Eventos aux; //Para manejar los eventos
        int cont = 1;
        while (salidos < 15) {
            aux = BuscarMenor(lista);
            System.out.print("Cliente: " + aux.cliente + "  Tipo: " + aux.tipo + "  Tiempo: " + aux.tiempo + "  Numero: " + String.format("%.2f", aux.numeroGen));
            reloj = aux.tiempo;
            if (aux.tipo == 'E') { //Si el evento entrante es una Llegada
                if (statS1 == false) {
                    statS1 = true;
                    l++;
                    s = new Eventos();
                    s.GenEvento('S', tabla2, reloj, aux.cliente);
                    lista.add(s);
                } else if (statS2 == false) {
                    statS2 = true;
                    l++;
                    s = new Eventos();
                    s.GenEvento('S', tabla2, reloj, aux.cliente);
                    lista.add(s);
                } else {
                    lq++;
                    l++;
                    cola.add(aux.cliente);
                }
                e = new Eventos();
                cont++;
                e.GenEvento('E', tabla1, reloj, cont);
                lista.add(e);
            }//fin de una Llegada
            else { //Si el evento es una salida
                if (lq > 0) {
                    lq--;
                    s = new Eventos();
                    s.GenEvento('S', tabla2, reloj,cola.poll());
                    lista.add(s);
                } else {
                    if (statS1 == true) {
                        statS1 = false;
                    } else {
                        statS2 = false;
                    }
                }
                l--;
                salidos++;
            } //Fin de la salida
            System.out.println("  Reloj: " + reloj + "  StatS1: " + statS1 + "  StatS2: " + statS2 + "  L: " + l + "   Lq: " + lq + "\n");

        }
    }
}
