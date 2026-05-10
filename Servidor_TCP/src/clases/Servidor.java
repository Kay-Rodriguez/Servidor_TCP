package clases;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Servidor {

    private HashMap<String, Integer> registros = new HashMap<>();

    public String getFecha() {
        Date fecha = new Date();
        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formato.format(fecha);
    }

    public void servicio(int puerto) throws Exception {
        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Servidor corriendo en el puerto: " + puerto);

        while (true) {
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado exitosamente");

            DataInputStream dis = new DataInputStream(cliente.getInputStream());

            String nombre = dis.readUTF();
            String tipoRegistro = dis.readUTF();

            int cantidad = registros.getOrDefault(nombre, 0);

            String respuesta;

            if (cantidad >= 4) {
                respuesta = "El empleado " + nombre + " ya completó los 4 registros del día.";
            } else {
                cantidad++;
                registros.put(nombre, cantidad);

                String fechaHora = getFecha();

                respuesta = "Registro exitoso\n"
                        + "Empleado: " + nombre + "\n"
                        + "Tipo de registro: " + tipoRegistro + "\n"
                        + "Hora registrada: " + fechaHora + "\n"
                        + "Registro número: " + cantidad + " de 4";

                System.out.println("Empleado: " + nombre);
                System.out.println("Tipo de registro: " + tipoRegistro);
                System.out.println("Hora: " + fechaHora);
                System.out.println("Registro " + cantidad + " de 4");
            }

            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
            dos.writeUTF(respuesta);

            cliente.close();
        }
    }
}