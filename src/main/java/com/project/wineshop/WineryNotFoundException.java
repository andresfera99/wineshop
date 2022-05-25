package com.project.wineshop;

public class WineryNotFoundException extends RuntimeException {

    WineryNotFoundException(Long id) {
        super("Could not find winery " + id);
    }
}