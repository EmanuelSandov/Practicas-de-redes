import java.net.*;
import java.io.*;

public class CHolaD {
    public static void main(String[] args) {
        try {
            DatagramSocket cl = new DatagramSocket();
            System.out.print("Cliente iniciado, escriba un mensaje de saludo: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String mensaje = br.readLine() + '\0'; // Añadir caracter especial para indicar el final
            byte[] b = mensaje.getBytes();
            String dst = "127.0.0.1";
            int pto = 2000;
            int maxLength = 200;

            // Fragmentar y enviar el mensaje
            for (int i = 0; i < b.length; i += maxLength) {
                int end = Math.min(b.length, i + maxLength);
                DatagramPacket p = new DatagramPacket(b, i, end - i, InetAddress.getByName(dst), pto);
                System.out.println("Enviando paquete de tamaño " + (end - i) + " bytes");

                cl.send(p);
            }

            // Recibir respuesta del servidor
            byte[] buffer = new byte[65535];
            int length = 0;
            DatagramPacket respuestaPacket = new DatagramPacket(new byte[2000], 2000);
            while (true) {
                cl.receive(respuestaPacket);
                System.arraycopy(respuestaPacket.getData(), respuestaPacket.getOffset(), buffer, length, respuestaPacket.getLength());
                length += respuestaPacket.getLength();
                if (buffer[length-1] == '\0') {
                    String respuesta = new String(buffer, 0, length-1);
                    System.out.println("Respuesta del servidor: " + respuesta);
                    break;
                }
            }

            cl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
