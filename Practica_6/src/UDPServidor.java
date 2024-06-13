import java.net.*;
import java.nio.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;

public class UDPServidor {
    public final static int PUERTO = 7;
    public final static int TAM_MAXIMO = 65507;

    public static void main(String[] args) {
        int port = PUERTO;
        try {
            DatagramChannel canal = DatagramChannel.open(); // Abre un canal de datagramas
            canal.configureBlocking(false); // Configura el canal para que no sea bloqueante
            DatagramSocket socket = canal.socket();
            SocketAddress dir = new InetSocketAddress(port);
            socket.bind(dir); // Asocia el socket a la dirección especificada
            Selector selector = Selector.open(); // Abre un selector
            canal.register(selector, SelectionKey.OP_READ); // Registra el canal en el selector para operaciones de lectura
            ByteBuffer buffer = ByteBuffer.allocateDirect(TAM_MAXIMO); // Asigna un buffer de tamaño máximo

            while (true) { // Bucle infinito para mantener el servidor en ejecución
                selector.select(5000); // Espera hasta 5 segundos para que haya canales listos
                Set<SelectionKey> sk = selector.selectedKeys(); // Obtiene las claves de selección
                Iterator<SelectionKey> it = sk.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    if (key.isReadable()) { // Si la clave está lista para lectura
                        buffer.clear();
                        SocketAddress client = canal.receive(buffer); // Recibe datos del cliente
                        buffer.flip();
                        int eco = buffer.getInt();
                        if (eco == 1000) { // Si el entero leído es 1000
                            canal.close();
                            System.exit(0); // Termina el programa
                        } else {
                            System.out.println("Dato leído: " + eco);
                            buffer.flip();
                            canal.send(buffer, client); // Envía el dato de vuelta al cliente
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
