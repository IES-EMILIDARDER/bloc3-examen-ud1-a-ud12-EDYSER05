package examen;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Main5 {

    public static void main(String[] args) throws SQLException, IOException {
        final String MYSQL_CON = "c:\\temp\\mysql.con";
        GestorBBDD gestorBBDD = new GestorBBDD(MYSQL_CON);
        
        List<Vehicle> vehicles = Arrays.asList(
                new Vehicle("4321-JKL", "Ford", "Focus", 2019, 17000),
                new Vehicle("8765-MNO", "Hyundai", "Ioniq 5", 2023, 42000),
                new Vehicle("2109-PQR", "Peugeot", "308", 2016, 14000)
        );
        
        try (Connection connexio = gestorBBDD.getConnectionFromFile()) {
            connexio.setAutoCommit(true);
            
            for (Vehicle v : vehicles) {
                try {
                    gestorBBDD.executaSQL(connexio,"INSERT INTO vehicles(matricula,marca,model,any,preu) VALUES (?,?,?,?,?)",
                                          v.getMatricula(), v.getMarca(),v.getModel(), (Integer) v.getAny(), (double) v.getPreu());
                } catch (SQLException e) {
                    if (e.getSQLState().equals("23000") && e.getErrorCode() == 1062)
                        gestorBBDD.executaSQL(connexio,"UPDATE vehicles SET marca = ?, model = ?, any = ?, preu = ? WHERE matricula = ?",
                                              v.getMarca(),v.getModel(), (Integer) v.getAny(), (double) v.getPreu(),v.getMatricula());
                    else throw e;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error obrint la base de dades");
        }
    }
}
