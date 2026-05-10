package test;

import clases.Servidor;

public class TestServidor {
    public static void main(String[] args) throws Exception {
        Servidor servidor = new Servidor();
        servidor.servicio(2025);
    }
}