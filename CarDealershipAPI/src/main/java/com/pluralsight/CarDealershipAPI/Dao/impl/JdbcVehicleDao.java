package com.pluralsight.CarDealershipAPI.Dao.impl;

import com.pluralsight.CarDealershipAPI.Dao.interfaces.VehicleDao;
import com.pluralsight.CarDealershipAPI.Dao.wrapper.Inventory;
import com.pluralsight.CarDealershipAPI.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcVehicleDao implements VehicleDao {

    private DataSource dataSource;

    @Autowired
    JdbcVehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Vehicle mapResultSetToVehicle(ResultSet resultSet) {
        try {
            return new Vehicle(
                    resultSet.getString("vin"),
                    resultSet.getInt("year"),
                    resultSet.getString("make"),
                    resultSet.getString("model"),
                    resultSet.getString("type"),
                    resultSet.getString("color"),
                    resultSet.getInt("odometer"),
                    resultSet.getDouble("price")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Vehicle> executeQuery(String sql, Object... params) {
        List<Vehicle> vehicleList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    vehicleList.add(mapResultSetToVehicle(resultSet));
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicleList;

    }

    @Override
    public List<Vehicle> getAll(int dealershipID) {
        List<Vehicle> vehicleList;

        String sql =
                """
                         SELECT\s
                         vehicles.vin
                         ,year
                         ,make
                         ,model
                         ,type
                         ,color
                         ,odometer
                         ,price
                         ,SOLD FROM cardealership.vehicles
                         INNER JOIN cardealership.inventory
                                ON inventory.vin = vehicles.vin
                         INNER JOIN cardealership.dealerships
                                ON dealerships.dealership_id = inventory.dealership_id
                         WHERE inventory.dealership_id = ?;
                        """;

        vehicleList = executeQuery(sql, dealershipID);
        return vehicleList;
    }

    @Override
    public List<Vehicle> getVehiclesByPriceRange(int dealershipID, int min, int max) {
        List<Vehicle> vehicleList = new ArrayList<>();

        String sql =
                """
                                								SELECT
                                                                vehicles.vin
                                                                ,year
                                                                ,make
                                                                ,model
                                                                ,type
                                                                ,color
                                                                ,odometer
                                                                ,price
                                                                ,SOLD FROM cardealership.vehicles
                                                                INNER JOIN cardealership.inventory
                                                                		ON inventory.vin = vehicles.vin
                                                                INNER JOIN cardealership.dealerships
                                                                		ON dealerships.dealership_id = inventory.dealership_id
                                                                WHERE dealerships.dealership_id = (?) AND vehicles.price BETWEEN (?) AND (?);
                        """;
        vehicleList = executeQuery(sql, dealershipID, min, max);

        return vehicleList;
    }

    @Override
    public List<Vehicle> getVehiclesByMake(int dealershipID, String make, String model) {
        List<Vehicle> vehicleList = new ArrayList<>();
        String sql =
                """
                 SELECT
                 vehicles.vin
                 ,year
                 ,make
                 ,model
                 ,type
                 ,color
                 ,odometer
                 ,price
                 ,SOLD FROM cardealership.vehicles
                 INNER JOIN cardealership.inventory
                         ON inventory.vin = vehicles.vin
                 INNER JOIN cardealership.dealerships
                         ON dealerships.dealership_id = inventory.dealership_id
                 WHERE dealerships.dealership_id = (?) AND vehicles.make = (?) AND vehicles.model = (?);
                """;
        vehicleList = executeQuery(sql, dealershipID, make, model);

        return vehicleList;
    }

    @Override
    public List<Vehicle> getVehiclesByYear(int dealershipID, int minYear, int maxYear) {
        List<Vehicle> vehicleList = new ArrayList<>();

        String sql =
                """
                                SELECT
                                vehicles.vin
                                ,year
                                ,make
                                ,model
                                ,type
                                ,color
                                ,odometer
                                ,price
                                ,SOLD FROM cardealership.vehicles
                                INNER JOIN cardealership.inventory
                                        ON inventory.vin = vehicles.vin
                                INNER JOIN cardealership.dealerships
                                        ON dealerships.dealership_id = inventory.dealership_id
                                WHERE dealerships.dealership_id = (?) AND vehicles.year BETWEEN (?) AND (?);
                """;
        vehicleList = executeQuery(sql, dealershipID, minYear, maxYear);

        return vehicleList;
    }

    @Override
    public List<Vehicle> getVehiclesByColor(int dealershipID, String color ){
        List<Vehicle> vehicleList = new ArrayList<>();
        String sql =
                """
                        SELECT
                        dealerships.dealership_id\s
                        ,vehicles.vin
                        ,year
                        ,make
                        ,model
                        ,type
                        ,color
                        ,odometer
                        ,price
                        ,SOLD FROM cardealership.vehicles
                        INNER JOIN cardealership.inventory
                        		ON inventory.vin = vehicles.vin
                        INNER JOIN cardealership.dealerships
                        		ON dealerships.dealership_id = inventory.dealership_id
                        WHERE dealerships.dealership_id = ? AND vehicles.color = ?;
                """;
        vehicleList = executeQuery(sql,dealershipID,color);
        return vehicleList;
    }

    @Override
    public List<Vehicle> getVehiclesByMilage(int dealershipID, int min, int max) {
        List<Vehicle> vehicleList = new ArrayList<>();
        String sql =
                """
                        SELECT
                        vehicles.vin
                        ,year
                        ,make
                        ,model
                        ,type
                        ,color
                        ,odometer
                        ,price
                        ,SOLD FROM cardealership.vehicles
                        INNER JOIN cardealership.inventory
                                ON inventory.vin = vehicles.vin
                        INNER JOIN cardealership.dealerships
                                ON dealerships.dealership_id = inventory.dealership_id
                        WHERE dealerships.dealership_id = (?) AND vehicles.odometer BETWEEN (?) AND (?);
                """;
        vehicleList = executeQuery(sql, dealershipID, min, max);

        return vehicleList;
    }

    @Override
    public List<Vehicle> getVehiclesByType(int dealershipID, String type) {
        List<Vehicle> vehicleList = new ArrayList<>();
        String sql =
                """
                        SELECT
                        vehicles.vin
                        ,year
                        ,make
                        ,model
                        ,type
                        ,color
                        ,odometer
                        ,price
                        ,SOLD FROM cardealership.vehicles
                        INNER JOIN cardealership.inventory
                                ON inventory.vin = vehicles.vin
                        INNER JOIN cardealership.dealerships
                                ON dealerships.dealership_id = inventory.dealership_id
                        WHERE dealerships.dealership_id = (?) AND vehicles.type = (?);
                """;
        vehicleList = executeQuery(sql, dealershipID, type);

        return vehicleList;
    }

    @Override
    public Vehicle insert(int dealershipID, Vehicle v) {
        String sqlquery1 =
                """
                    INSERT INTO cardealership.inventory(
                        dealership_id, vin
                    )
                    VALUES (
                        (?),(?)
                    );
                """;
        String sqlquery2 =
                """ 
                    INSERT INTO cardealership.vehicles(
                        vin, year, make, model, type, color, odometer, price, SOLD
                    )
                    VALUES (
                        ?,?,?,?,?,?,?,?,null
                    );
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlquery1);
             PreparedStatement statement2 = connection.prepareStatement(sqlquery2)
        ) {
            //set parameters
            statement.setInt(1, dealershipID);
            statement.setString(2, v.getVin());

            statement2.setString(1, v.getVin());
            statement2.setInt(2, v.getYear());
            statement2.setString(3, v.getMake());
            statement2.setString(4, v.getModel());
            statement2.setString(5, v.getVehicleType());
            statement2.setString(6, v.getColor());
            statement2.setInt(7, v.getOdometer());
            statement2.setDouble(8, v.getPrice());

            // update statement
            int rows = statement.executeUpdate();
            statement2.executeUpdate();
            // confirm the update
            System.out.printf("Rows updated %d\n", rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return v;
    }

    @Override
    public void delete(int dealershipID, Vehicle vehicle) {
        String sqlquery1 =
                """
                DELETE FROM cardealership.inventory WHERE vin = (?);
                """;
        String sqlquery2 =
                """
                DELETE FROM cardealership.vehicles WHERE vin = (?);
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement1 = connection.prepareStatement(sqlquery1);
             PreparedStatement preparedStatement2 = connection.prepareStatement(sqlquery2)){
            preparedStatement1.setString(1,vehicle.getVin());
            preparedStatement2.setString(1, vehicle.getVin());

            int row = preparedStatement1.executeUpdate();
            preparedStatement2.executeUpdate();

            System.out.printf("Updated rows: %d", row);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}


