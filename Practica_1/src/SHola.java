//Librerias necesarias
import java.net.*;
import java.io.*;
//Ligamos el puerto a 1234
public class SHola{
    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(1234);
            System.out.println("Esperando el cliente...");
            //El servidor espera dentro de un cliclo infinito la solicitud de la conexion de un cliente
            for(;;){
                Socket cl = s.accept();
                System.out.println("Conexion establecida desde" + cl.getLocalAddress()+":"+cl.getLocalPort());
                //Definimos el mensjae a enviar y ligamos un PrintWriter a un flujo de salida de caracteres
                String mensaje = "Emanuel Sandoval Muñoz, Redes 2, 6CM4";
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
                pw.println(mensaje);
                pw.flush();
                //Mostramos mensaje
                BufferedReader br3 = new BufferedReader(new BufferedReader(new InputStreamReader(cl.getInputStream())));
                String mensajeR = br3.readLine();
                System.out.println("Recibimos un mensaje desde el servidor de regreso");
                System.out.println("Mensaje: "+ mensajeR);
                //Cerramos el flujo, cerramos el socket, terminamos el ciclo for y el bucle try-catch y cerramos la clase
                pw.close();
                cl.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
