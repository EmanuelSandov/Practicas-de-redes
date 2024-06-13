import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SHolaD{
    public static void main(String[] args) {
        try {
            DatagramSocket s = new DatagramSocket(2000);
            System.out.println("Servidor iniciado, esperando cliente");

            byte[] buffer = new byte[65535]; // Buffer para almacenar datos fragmentados
            int length = 0; // Longitud del mensaje final

            for (;;) {
                DatagramPacket p = new DatagramPacket(new byte[500], 500);
                s.receive(p);
                System.arraycopy(p.getData(), p.getOffset(), buffer, length, p.getLength());
                length += p.getLength();

                if (buffer[length-1] == '\0') { // Termina el mensaje con un caracter especial
                    String msj = new String(buffer, 0, length-1);
                    System.out.printf("Mensaje completo recibido desde " + p.getAddress() + ":" + p.getPort() + " Con el mensaje: " + msj + "\n");

                    System.out.print("Escriba un mensaje de regreso al cliente: ");
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String respuesta = br.readLine() + '\0';
                    byte[] b = respuesta.getBytes();
                    DatagramPacket respuestaPacket = new DatagramPacket(b, b.length, p.getAddress(), p.getPort());
                    s.send(respuestaPacket);
                    length = 0; // Reiniciar para el próximo mensaje
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
