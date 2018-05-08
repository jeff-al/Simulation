package simulacion;

//Programa hecho por Jefferson Álvarez López para el curso Investigación de Operaciones.
//Carné: B60380

import java.util.*;
import static java.lang.System.out;

public class Simulacion {

    //Itera sobre la lista de ventos para buscar el que sigue según el reloj y lo devuelve.
    //Si una salida y una entrada tienen el mismo tiempo entonces devuelve la salida.
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
        if (args.length == 1) {
            double[][] tabla1 = { //Tabla 1 (Tiempo entre llamadas)
                {1.00, 0.40},
                {2.00, 0.75},
                {3.00, 1.00}
            };

            double[][] tabla2 = { //Tabla 2 (Duración de llamada)
                {2.00, 0.10},
                {3.00, 0.35},
                {4.00, 0.75},
                {7.00, 0.95},
                {10.00, 1.00}
            };

            int reloj = 0;    //variable de reloj

            int statS = 0;   //Status de los servidores 0 == libres, 1 == 1 coupado y 1 libre, 2 == ocupados

            int l = 0;         //Total de clientes en el sistema (En cola y siendo atendidos)
            int salida = 0;

            List<Eventos> lista = new ArrayList<Eventos>();      //Lista de eventos
            Queue<Integer> cola = new LinkedList<Integer>();     //Cola del sistema

            Eventos e = new Eventos(); //Para eventos de entrada
            Eventos s = new Eventos(); //Para eventos de salida
            lista.add(e); //Añade la primera entrada con id de cliente 1 y tiempo 0

            Eventos aux; //Para manejar el evento que se van a procesar

            int idCliente = 1;  //ID del cliente 

            int numeroSalidas = Integer.parseInt(args[0]); //Numero de salidas que escoge el usuario para la condicion de parada (15 por defecto)

            while (salida < numeroSalidas) {
                aux = BuscarMenor(lista);
                System.out.print("Cliente: " + aux.cliente + " | Tipo: " + aux.tipo + " | Tiempo: " + aux.tiempo + " | Numero: " + String.format("%.2f", aux.numeroGen));
                reloj = aux.tiempo;
                if (aux.tipo == 'E') { //Si el evento entrante es una Llegada
                    if (statS < 2) { //Si hay al menos un servidor libre
                        statS++;
                        l++;
                        s = new Eventos();
                        s.GenEvento('S', tabla2, reloj, aux.cliente); //Se genera la salida de esa entrada
                        lista.add(s);
                    } else {             //Si los servidores están ocupados      
                        l++;
                        cola.add(aux.cliente);
                    }
                    idCliente++; //Se cambia de cliente
                    e = new Eventos();
                    e.GenEvento('E', tabla1, reloj, idCliente); //Se genera el la llegada del cliente
                    lista.add(e);
                }//fin de una Llegada
                else { //Si el evento es una salida
                    if (cola.size() > 0) { //Si hay, al menos, un cliente en cola
                        s = new Eventos();
                        s.GenEvento('S', tabla2, reloj, cola.poll()); //Se saca al cliente de la cola y se le genera la salida
                        lista.add(s);
                    } else { //Si no hay clientes en cola
                        statS--;
                    }
                    l--;
                    salida++;
                } //Fin de la salida
                System.out.println(" | Reloj: " + reloj + " | StatS: " + statS + " (Ocupado(s)) |  L: " + l + " |  Lq: " + cola.size() + "\n");
            }
            System.out.println(" Eventos Restantes: ");
            while (!lista.isEmpty()) {
                aux = lista.remove(0);
                System.out.println("Cliente: " + aux.cliente + " | Tipo: " + aux.tipo + " | Tiempo: " + aux.tiempo + " | Numero: " + String.format("%.2f", aux.numeroGen));
            }
        } else {
            System.out.println("Se necesita un parametro y debe ser el numero de clientes que deben salir del sistema");
        }
    }
}
