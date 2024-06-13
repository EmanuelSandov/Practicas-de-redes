import java.net.*;
import java.io.*;

public class CPrimD {
    public static void main(String[] args) {
        try{
            //Se selecciona puerto y direccion
            int pto = 2000;
            InetAddress dst = InetAddress.getByName("127.0.0.1");
            //Se inicia el datagrama
            DatagramSocket cl = new DatagramSocket();
            //Un arreglo para guardar los datos que se vana a enviar
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //Donde se van a leer los datos a enviar en el arreglo
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeInt(4);
            dos.flush();
            dos.writeFloat(4.1f);
            dos.flush();
            dos.writeLong(72);
            dos.flush();
            //Guarda los datos a enviar
            byte[] b = baos.toByteArray();
            DatagramPacket p = new DatagramPacket(b,b.length, dst,pto);
            //Envia los datos al servidor
            cl.send(p);
            //cierra el socket del datagrama
            cl.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
