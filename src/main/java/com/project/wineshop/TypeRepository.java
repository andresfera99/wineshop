package com.project.wineshop;

import org.springframework.data.jpa.repository.JpaRepository;

interface TypeRepository extends JpaRepository<Type, Integer> {
    Type findByName(String name);
}
