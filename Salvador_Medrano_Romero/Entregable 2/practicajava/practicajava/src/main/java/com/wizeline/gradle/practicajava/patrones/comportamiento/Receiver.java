package com.wizeline.gradle.practicajava.patrones.comportamiento;

public class Receiver {

	public void crear() {
        System.out.println("Obteniendo informacion del pedido");
        System.out.println("Creando orden");
        System.out.println("Guardando orden");
    }

    public void enviar() {
        System.out.println("Recuperando orden");
        System.out.println("Enviando orden");
    }

    public void enviarNot() {
        System.out.println("Checando estatus del pedido");
        System.out.println("Enviando notificacion");
    }
    
    public void cancelar() {
        System.out.println("Checando estatus del pedido");
        System.out.println("Recuperando orden");
        System.out.println("Cancelando orden");
    }
    
}
