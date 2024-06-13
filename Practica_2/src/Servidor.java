import java.net.*;
import java.io.*;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(7000);

            for (;;) {
                Socket cl = s.accept();
                System.out.println("Conexion establecida desde: " + cl.getInetAddress() + ":" + cl.getPort());
                DataInputStream dis = new DataInputStream(cl.getInputStream());

                int numArchivos = dis.readInt(); // Recibir la cantidad de archivos

                for (int i = 0; i < numArchivos; i++) { //Hace el ciclo para todos los archivos recibidos
                    String nombre = dis.readUTF();
                    System.out.println("Recibimos el archivo: " + nombre);
                    long tam = dis.readLong();
                    DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));

                    byte[] b = new byte[1024];
                    long recibidos = 0;
                    int n;
                    while (recibidos < tam) {
                        n = dis.read(b);
                        dos.write(b, 0, n);
                        dos.flush();
                        recibidos += n;
                    }
                    dos.close();
                }
                System.out.print("\n\nArchivos recibidos");
                dis.close();
                cl.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
