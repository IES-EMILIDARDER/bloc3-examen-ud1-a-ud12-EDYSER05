package examen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main2 {
        static int preuSuperior15k=0;
        static double preuMitja=0;
        static int contadorPreuMitja=0;
        static double preuMesCar=0;
        static double preuMesBarat=0;
    public static void main(String[] args) {
        try (Stream<String> linies = Files.lines(Paths.get("c:\\temp\\vehicles.csv"))) {
            //Lectura
            List<Vehicle> vehicles = 
                   (List<Vehicle>) linies.filter(linia -> !linia.isBlank() && !linia.startsWith("#"))
                  .map(linia -> linia.split(","))
                  .map(parts -> new Vehicle(parts[0].trim(),
                                            parts[1].trim(),
                                            parts[2].trim(),
                                            Integer.parseInt(parts[3].trim()),
                                            Integer.parseInt(parts[4].trim())))
                  .collect(Collectors.toList());

            //Filtrar dades
            preuSuperior15k = (int) vehicles.stream().filter(v -> v.getPreu()>15000).count();
            
            //For each utilitzat per asignar a preuMesBarat cualque valor que no sigui 0
            vehicles.forEach(v -> preuMesBarat = v.getPreu());
            //Calcular Preu Mitja, trobar preu Mes Car y trobar preu Mes Barat
            vehicles.forEach(v -> {
                preuMitja+=v.getPreu(); 
                contadorPreuMitja++;
                if(v.getPreu()>preuMesCar) preuMesCar= v.getPreu();
                if (preuMesBarat>v.getPreu()) preuMesBarat = v.getPreu();
            });
            
            //Mostrar Resultats
            System.out.println("Vehicles amb preu major de 15000 EUR: "+preuSuperior15k);
            System.out.println("Preu mitjà: "+(preuMitja/contadorPreuMitja)+" EUR");
            System.out.println("Més car: "+preuMesCar+" EUR");
            System.out.println("Més barat: "+preuMesBarat+" EUR");

            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

