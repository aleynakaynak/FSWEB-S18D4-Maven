package com.workintech.s18d1.util;

import com.workintech.s18d1.exceptions.BurgerException;
import org.springframework.http.HttpStatus;

public class BurgerValidation {

    public static void checkId(Long id) {
        if (id == null || id <= 0) {
            throw new BurgerException("ID must be greater than zero: " + id, HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkBurgerParams(String name, Double price, String contents) {
        if (name == null || name.trim().isEmpty()) {
            throw new BurgerException("Burger name cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        if (price == null || price <= 0) {
            throw new BurgerException("Burger price must be greater than zero", HttpStatus.BAD_REQUEST);
        }
        if (contents == null || contents.trim().isEmpty()) {
            throw new BurgerException("Burger contents cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
    }
}