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


    /*@Autowired
    JdbcTemplate jdbcTemplate;*/

    private static final Logger log = LoggerFactory.getLogger(LoadData.class);

    @Bean
    CommandLineRunner initDatabase(WineRepository wineRepository, WineryRepository wineryRepository, TypeRepository typeRepository, RegionRepository regionRepository) {

        return args -> {

            /*RowCallbackHandler callback = new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    typeRepository.save(new Type(rs.getString("type")));
                }
            };
            jdbcTemplate.query("Select distinct type from wines_spa", callback);*/

           /* RowCallbackHandler callback2 = new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    wineryRepository.save(new Winery(rs.getString("winery")));
                }
            };
            jdbcTemplate.query("Select distinct winery from wines_spa", callback2);*/

            /*RowCallbackHandler callback3 = new RowCallbackHandler() {
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
            jdbcTemplate.query("Select distinct region, country from wines_spa", callback3);*/
            /*RowCallbackHandler callback4 = new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    Winery winery = wineryRepository.findByName(rs.getString("winery"));
                    Type type = typeRepository.findByName(rs.getString("type"));
                    Region region = regionRepository.findByName(rs.getString("region"));

                    wineRepository.save(
                            new Wine(
                                    rs.getString("wine"),
                                    rs.getString("year"),
                                    rs.getDouble("price"),
                                    rs.getFloat("rating"),
                                    rs.getInt("num_reviews"),
                                    rs.getString("body"),
                                    rs.getString("acidity"),
                                    Math.toIntExact(winery.getId()),
                                    Math.toIntExact(type.getId()),
                                    Math.toIntExact(region.getId())

                            )
                    );
                }
            };
            jdbcTemplate.query("Select * from wines_spa", callback4);*/
            /*Type aux1 = new Type("tipo h2 prueba");
            typeRepository.save(aux1);

            typeRepository.save(new Type("tipo h2 prueba 2"));
            Type aux2 = new Type("tipo h2 prueba 3");
            typeRepository.save(aux2);


            typeRepository.save(new Type("tipo h2 prueba 3")); // para darle los vinos de prueba

            Winery aux3 = new Winery("vineria tupac");
            wineryRepository.save(aux3);
            //delete
            wineryRepository.save(new Winery("vineria chikibeibi"));
            Winery aux4 = new Winery("vineria chikibeibi 123");
            wineryRepository.save(aux4);

            wineryRepository.save(new Winery("vineria prueba 3")); // para darle los vinos de prueba

            Region aux5 = new Region("tierra media", "Hispania");
            regionRepository.save(aux5);
            Region aux6 = new Region("Jerusalem", "Por africa del este"); //delete
            regionRepository.save(aux6);
            Region aux7 = new Region("ALibaba", "Por africa del este");
            regionRepository.save(aux7);

            regionRepository.save(new Region("region prueba 3", "Hispania")); // para darle los vinos de prueba

            wineRepository.save(new Wine("vino1", "2022", 21365, 2.5f, 2, "5", "54", aux3, aux1, aux7));
            wineRepository.save(new Wine("vino2", "2025", 565, 7.5f, 648, "56", "985", aux3, aux1, aux7));
            wineRepository.save(new Wine("vino borrar", "2028", 565, 7.5f, 648, "56", "985", aux4, aux2, aux7));


            wineryRepository.findAll().forEach(t -> {
                log.info(t.toString());
            });
            regionRepository.findAll().forEach(t -> {
                log.info(t.toString());
            });
            typeRepository.findAll().forEach(t -> {
                log.info(t.toString());
            });
            wineRepository.findAll().forEach(t -> {
                log.info(t.toString());
            });*/

            log.info("Preload complete");
        };
    }
}