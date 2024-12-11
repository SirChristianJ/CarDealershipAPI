package com.pluralsight.CarDealershipAPI.Dao.impl;

import com.pluralsight.CarDealershipAPI.Dao.interfaces.DealershipDao;
import com.pluralsight.CarDealershipAPI.models.Dealership;
import com.pluralsight.CarDealershipAPI.models.Vehicle;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcDealershipDao implements DealershipDao {

    private DataSource dataSource;
    @Autowired
    JdbcDealershipDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public List<Dealership> getAll() {
        List<Dealership> dealershipList = new ArrayList<>();

        String sql =
                """
                        SELECT\s
                        *
                        FROM cardealership.dealerships;
                """;

        try (Connection conn = dataSource.getConnection();){
            try(PreparedStatement statement = conn.prepareStatement(sql);){
                try (ResultSet results = statement.executeQuery();){
                    while (results.next()){
                        dealershipList.add(new Dealership(
                                results.getInt("dealership_id"),
                                results.getString("name"),
                                results.getString("address"),
                                results.getString("phone")
                        ));
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dealershipList;
    }

    @Override
    public Dealership insert(Dealership dealership) {
        String sql =
                """
                        INSERT INTO cardealership.dealerships(name,address,phone)
                        VALUES((?),(?),(?));
                """;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, dealership.getName());
            preparedStatement.setString(2,dealership.getAddress());
            preparedStatement.setString(3,dealership.getPhone());

            int rows = preparedStatement.executeUpdate();

            System.out.printf("Rows updated: %d\n", rows);

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new Dealership(dealership.getId(), dealership.getName(), dealership.getAddress(), dealership.getPhone());
    }

}
