import java.net.*;
import java.io.*;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Servidor listo, esperando conexiones...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Conexion establecida desde: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

                try (DataInputStream dis = new DataInputStream(clientSocket.getInputStream())) {
                    System.out.println("Waiting to receive file name and size...");
                    String fileName = dis.readUTF();
                    long fileSize = dis.readLong();
                    System.out.println("Recibiendo archivo: " + fileName + " de tamaño " + fileSize + " bytes");

                    // Receiving file
                    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName))) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        long totalRead = 0;
                        while (totalRead < fileSize) {
                            bytesRead = dis.read(buffer);
                            totalRead += bytesRead;
                            bos.write(buffer, 0, bytesRead);
                        }
                        bos.flush();
                        System.out.println("Archivo " + fileName + " recibido.");
                    }

                    // Deserialize the object from file
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
                        Cliente cliente = (Cliente) ois.readObject();
                        System.out.println("Objeto Cliente deserializado: ");
                        System.out.println(cliente); // Assuming Cliente class has a proper toString() method
                    }
                } catch (EOFException e) {
                    System.out.println("EOFException caught: Client may have sent incomplete data.");
                } finally {
                    clientSocket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
