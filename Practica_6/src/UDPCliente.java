import java.net.*;
import java.nio.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;

public class UDPCliente {
    public final static int PUERTO = 7;
    private final static int LIMITE = 100;

    public static void main(String[] args) {
        boolean bandera = false;
        SocketAddress remote = new InetSocketAddress("127.0.0.1", PUERTO);
        try {
            DatagramChannel canal = DatagramChannel.open(); // Abre un canal de datagramas
            canal.configureBlocking(false); // Configura el canal para que no sea bloqueante
            canal.connect(remote); // Conecta el canal al servidor remoto
            Selector selector = Selector.open(); // Abre un selector
            canal.register(selector, SelectionKey.OP_WRITE); // Registra el canal en el selector para operaciones de escritura
            ByteBuffer buffer = ByteBuffer.allocateDirect(4); // Asigna un buffer de 4 bytes
            int n = 0;
            while (true) {
                selector.select(5000); // Espera hasta 5 segundos para que haya canales listos
                Set<SelectionKey> sk = selector.selectedKeys(); // Obtiene las claves de selección
                if (sk.isEmpty() && n == LIMITE || bandera) { // Si no hay claves y se ha alcanzado el límite o la bandera está activa
                    canal.close(); // Cierra el canal
                    break;
                } else {
                    Iterator<SelectionKey> it = sk.iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();
                        if (key.isWritable()) { // Si la clave está lista para escritura
                            buffer.clear();
                            buffer.putInt(n);
                            buffer.flip();
                            canal.write(buffer); // Escribe el dato en el canal
                            System.out.println("Escribe el dato: " + n);
                            n++;
                            if (n == LIMITE) { // Si se ha alcanzado el límite
                                buffer.clear();
                                buffer.putInt(1000);
                                buffer.flip();
                                canal.write(buffer); // Escribe el valor de terminación
                                bandera = true;
                                key.interestOps(SelectionKey.OP_READ); // Cambia la clave a operación de lectura
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
