package com.project.wineshop;

import org.springframework.data.jpa.repository.JpaRepository;

interface RegionRepository extends JpaRepository<Region, Long> {
    Region findByName(String name);
}
