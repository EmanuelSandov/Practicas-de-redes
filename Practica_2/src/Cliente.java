import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Escribir una direccion del servidor: ");
            String host = br.readLine();
            System.out.printf("\nEscriba un puerto: ");
            int pto = Integer.parseInt(br.readLine());

            Socket cl = new Socket(host, pto);

            JFileChooser jf = new JFileChooser();
            jf.setMultiSelectionEnabled(true); //Selecciona multiples archivos
            int r = jf.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                File[] files = jf.getSelectedFiles(); // Obtener múltiples archivos
                DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                dos.writeInt(files.length); // Enviar primero la cantidad de archivos

                for (File f : files) { //Hace el ciclo para cada archivo
                    String archivo = f.getAbsolutePath();
                    String nombre = f.getName();
                    long tam = f.length();

                    DataInputStream dis = new DataInputStream(new FileInputStream(archivo));
                    dos.writeUTF(nombre);
                    dos.flush();
                    dos.writeLong(tam);
                    dos.flush();

                    byte[] b = new byte[1024];
                    long enviados = 0;
                    int n;
                    while (enviados < tam) {
                        n = dis.read(b);
                        dos.write(b, 0, n);
                        dos.flush();
                        enviados += n;
                    }
                    dis.close();
                }
                System.out.print("\n\nArchivos enviados");
                dos.close();
            }
            cl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
