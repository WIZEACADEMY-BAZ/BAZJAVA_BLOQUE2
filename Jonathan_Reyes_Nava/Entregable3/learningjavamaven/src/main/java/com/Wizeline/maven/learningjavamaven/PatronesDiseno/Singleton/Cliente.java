package com.Wizeline.maven.learningjavamaven.PatronesDiseno.Singleton;

public class Cliente {

        public static void main(String[] args) {
            DatabaseConfig databaseConfig = DatabaseConfig.getInstance("Cristiano Ronaldo");
            databaseConfig.connect();

            DatabaseConfig databaseConfig2 = DatabaseConfig.getInstance("Lionel Messi");
            databaseConfig2.connect();
        }


}
