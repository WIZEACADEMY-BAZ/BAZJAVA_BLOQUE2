package com.wizeline.gradle.practicajava.patrones.comportamiento;

public class PatronCommand {

	public static void main(String[] args) {
		Invoker invoker = new Invoker();
		Receiver receiver = new Receiver();

		if (args[0].equals("Crear orden")) {
			invoker.executeOperation(() -> receiver.crear());

		} else if (args[0].equals("Enviar orden")) {

			invoker.executeOperation(() -> receiver.enviar());

		} else if (args[0].equals("Enviar notificacion")) {

			invoker.executeOperation(() -> receiver.enviarNot());

		} else if (args[0].equals("Cancelar orden")) {

			invoker.executeOperation(() -> receiver.cancelar());

		}
	}

}
