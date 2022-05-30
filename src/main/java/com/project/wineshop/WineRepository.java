package com.project.wineshop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface WineRepository extends JpaRepository<Wine, Long> {
    @Query(value = "SELECT * FROM wine ORDER BY rating DESC limit ?1", nativeQuery = true)
    List<Wine> findBest(int num); //se puede usar Pageable para implementar un offset y se usa con PageRequest

    @Query(value = "SELECT * FROM wine ORDER BY price DESC limit ?1", nativeQuery = true)
    List<Wine> findExpensive(int num);

    @Query(value = "SELECT * FROM wine ORDER BY (price /rating) DESC limit ?1", nativeQuery = true)
    List<Wine> findBang(int num);

    @Query(value = "SELECT *\n" +
            "FROM wine as w inner join (select `year` from wine group by `year` order by avg(rating) desc limit ?1) as w2\n" +
            "on w.`year` = w2.`year`\n" +
            "order by w.`year` DESC", nativeQuery = true)
    List<Wine> findVintage(int num);
}
