import java.net.*;
import java.io.*;

public class SPrimD {
    public static void main(String[] args) {
        try{
            //Se inicia el Socket con el puerto 2000
            DatagramSocket s = new DatagramSocket(2000);
            System.out.println("Servidor iniciado, esperaando cliente");
            for (;;){
                //Se hace el datagrama que va a recibir los datos del cliente
                DatagramPacket p = new DatagramPacket(new byte[2000],2000);
                s.receive(p);
                //Recibe los datos y los muestra en terminal
                System.out.println("Datagrama recibido desde " + p.getAddress() + ":" + p.getPort());
                DataInputStream dis = new DataInputStream(new ByteArrayInputStream(p.getData()));
                int x = dis.readInt();
                float f = dis.readFloat();
                long z = dis.readLong();
                System.out.println("Datos Recibidos \nEntero: "+x+"\nFlotante: "+f+"\nEntero largo: "+z);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
