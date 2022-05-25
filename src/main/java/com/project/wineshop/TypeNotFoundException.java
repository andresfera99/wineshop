package com.project.wineshop;

public class TypeNotFoundException extends RuntimeException {

    TypeNotFoundException(Integer id) {
        super("Could not find type " + id);
    }
}