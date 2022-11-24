package com.wizeline.maven.learningjavamaven.patterns;

public class ClientModel {
    private String apellidoPaterno;
    private String nombre;
    private int edad;
    private String telefono;
    private String tipoCliente;

    private ClientModel(ClientBuilder builder) {
        this.apellidoPaterno = builder.apellidoPaterno;
        this.nombre = builder.nombre;
        this.edad = builder.edad;
        this.telefono = builder.telefono;
        this.tipoCliente = builder.tipoCliente;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    @Override
    public String toString() {
        return "Cliente: "+this.apellidoPaterno+", "+this.nombre+", "+this.edad+", "+this.telefono+", "+this.tipoCliente;
    }

    public static final class ClientBuilder {
        private String apellidoPaterno;
        private String nombre;
        private int edad;
        private String telefono;
        private String tipoCliente;

        public ClientBuilder() {
        }

        public ClientBuilder apellidoPaterno(String apellidoPaterno) {
            this.apellidoPaterno = apellidoPaterno;
            return this;
        }

        public ClientBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public ClientBuilder edad(int edad) {
            this.edad = edad;
            return this;
        }

        public ClientBuilder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public ClientBuilder tipoCliente(String tipoCliente) {
            this.tipoCliente = tipoCliente;
            return this;
        }

        public ClientModel build() {
            ClientModel cliente =  new ClientModel(this);
            validateUserObject(cliente);
            return cliente;
        }

        private void validateUserObject(ClientModel cliente) {
            //Do some basic validations to check
            //if user object does not break any assumption of system
        }
    }

    }
