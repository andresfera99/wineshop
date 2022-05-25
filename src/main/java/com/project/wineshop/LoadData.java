package com.project.wineshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Configuration //hace que se ejecute al iniciar la aplicacion
class LoadData {

    private static final Logger log = LoggerFactory.getLogger(LoadData.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    CommandLineRunner initDatabase(WineRepository wineRepository, WineryRepository wineryRepository, TypeRepository typeRepository, RegionRepository regionRepository) {

        return args -> {

            RowCallbackHandler callback = new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    typeRepository.save(new Type(rs.getString("type")));
                }
            };
            jdbcTemplate.query("Select distinct type from wines_spa", callback);

            RowCallbackHandler callback2 = new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    wineryRepository.save(new Winery(rs.getString("winery")));
                }
            };
            jdbcTemplate.query("Select distinct winery from wines_spa", callback2);

            RowCallbackHandler callback3 = new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    regionRepository.save(
                            new Region(
                                    rs.getString("region"),
                                    rs.getString("country")
                            )
                    );
                }
            };
            jdbcTemplate.query("Select distinct region, country from wines_spa", callback3);

            RowCallbackHandler callback4 = new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    /*this.name = name;
                    this.year = year;
                    this.price = price;
                    this.rating = rating;
                    this.body = body;
                    this.acidity = acidity;
                    this.winery = winery;
                    this.type = type;
                    this.region = region;*/
                    wineRepository.save(
                            new Wine(
                                    rs.getString("wine"),
                                    rs.getInt("year"),
                                    rs.getDouble("price"),
                                    rs.getFloat("rating"),
                                    rs.getInt("num_reviews"),
                                    rs.getInt("body"),
                                    rs.getInt("acidity"),
                                    rs.getInt("winery"),
                                    rs.getInt("type"),
                                    rs.getInt("region")

                            )
                    );
                }
            };
            jdbcTemplate.query("Select * from wines_spa", callback4);

        };
    }
}