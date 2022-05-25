package com.project.wineshop;

import org.springframework.data.jpa.repository.JpaRepository;

interface WineryRepository extends JpaRepository<Winery, Long> {
}
