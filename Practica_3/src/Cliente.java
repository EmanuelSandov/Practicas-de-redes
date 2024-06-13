import java.io.*;
import java.net.Socket;


public class Cliente implements Serializable {
    private  String nombre; // Excluded from serialization
    private  int edad;      // Excluded from serialization
    private double salario;
    private String email;
    private boolean casado;
    private  float altura;  // Excluded from serialization

    public Cliente(String nombre, int edad, double salario, String email, boolean casado, float altura) {
        this.nombre = nombre;
        this.edad = edad;
        this.salario = salario;
        this.email = email;
        this.casado = casado;
        this.altura = altura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isCasado() {
        return casado;
    }

    public void setCasado(boolean casado) {
        this.casado = casado;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public static void main(String[] args) {
        try {
            System.out.println("Sin serializar:");
            Cliente cliente = new Cliente("Emanuel", 21, 2000.2, "mariovsgoku12@gmail.com", true, 1.75f);
            System.out.println(cliente);

            // Serializing the client
            FileOutputStream fos = new FileOutputStream("cliente.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(cliente);
            oos.close();
            fos.close();

            // Sending the serialized file via socket (assuming server is already listening)
            File file = new File("cliente.ser");
            Socket socket = new Socket("localhost", 1234);
            FileInputStream fileStream = new FileInputStream(file);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            //Serializacion
            FileInputStream fis = new FileInputStream("cliente.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Cliente deserializedCliente = (Cliente) ois.readObject();
            ois.close();
            fis.close();

            // Send the file name and size before the file content
            dos.writeUTF(file.getName());
            dos.writeLong(file.length());

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileStream.read(buffer)) != -1) {
                dos.write(buffer, 0, bytesRead);
            }
            dos.flush();
            //print deserializacion
            System.out.println("Serializado:");
            System.out.println("Nombre: " + deserializedCliente.getNombre());
            System.out.println("Edad: " + deserializedCliente.getEdad());
            System.out.println("Salario: " + deserializedCliente.getSalario());
            System.out.println("Email: " + deserializedCliente.getEmail());
            System.out.println("Casado: " + deserializedCliente.isCasado());
            System.out.println("Altura: " + deserializedCliente.getAltura());
            fileStream.close();
            dos.close();
            socket.close();
            System.out.println("Archivo enviado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre +
                ", Edad: " + edad +
                ", Salario: " + salario +
                ", Email: " + email +
                ", Casado: " + casado +
                ", Altura: " + altura;
    }
}
