package com.project.wineshop;

public class RegionNotFoundException extends RuntimeException {

    RegionNotFoundException(Long id) {
        super("Could not find region " + id);
    }
}