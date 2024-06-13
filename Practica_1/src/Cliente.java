//Bibliotecas necesarias
import java.net.*;
import java.io.*;
//Definimos la calse y la funcion
public class Cliente {
    public static void main(String[] args) {
        //Creation del bloquye try- catch y definimos un flujo de lectura de la entrada estandar
        try {
            BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
            //A partir del flujo de lectura obtenemos del usuario la direccion y el puerto del servidor
            System.out.println("Escriba la direccion del servidor: ");
            String host = br1.readLine();
            System.out.printf("\n\n Escriba el puerto: ");
            int pto = Integer.parseInt(br1.readLine());
            //Creamos socket
            Socket cl = new Socket(host, pto);
            //Creamos un flujo de caracteres ligamo al socket para recibir el mensaje
            BufferedReader br2 = new BufferedReader(new BufferedReader(new InputStreamReader(cl.getInputStream())));
            //Leemos el mensaje recibido
            String mensaje = br2.readLine();
            System.out.println("Recibimos un mensaje desde el servidor ");
            System.out.println("Mensaje: "+ mensaje);
            //Mandamos mensaje de regreso
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
            pw.println(mensaje);
            pw.flush();
            //Cerramos los flujos, el socket y terminamos el programa
            br1.close();
            br2. close();
            cl.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
