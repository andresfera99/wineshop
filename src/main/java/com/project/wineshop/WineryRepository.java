package com.project.wineshop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface WineryRepository extends JpaRepository<Winery, Long> {

    Winery findByName(String name);
}
