package examen;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Main4 {

    public static void main(String[] args) throws SQLException, IOException {
        final String MYSQL_CON = "c:\\temp\\mysql.con";
        GestorBBDD gestorBBDD = new GestorBBDD(MYSQL_CON);
        
        List<Vehicle> vehicles=new ArrayList<>();
        int any = 2020;
        
        String sql = "SELECT * FROM vehicles WHERE any >=?";
        try (Connection connexio = gestorBBDD.getConnectionFromFile();
             ResultSet resultSet = gestorBBDD.executaQuerySQL(connexio, sql,any)) {
            while (resultSet.next()) {
                afegeixVehicle(vehicles, new Vehicle(resultSet.getString("matricula"),
                               resultSet.getString("marca"),
                               resultSet.getString("model"),
                               resultSet.getInt("any"),
                               resultSet.getDouble("preu")));
            }
            
            //Mostrar Vehicles
            for (Vehicle v : vehicles) {
                System.out.println(v);
            }
            
        } catch (SQLException e) {
            System.err.println("Error Carregant la base de dades");
            e.printStackTrace();
        }
    }

    private static void afegeixVehicle(List<Vehicle> vehicles, Vehicle vehicle) {
        vehicles.add(vehicle);
    }
    
    
    
}