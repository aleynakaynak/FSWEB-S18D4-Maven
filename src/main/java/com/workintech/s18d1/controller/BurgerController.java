package com.workintech.s18d1.controller;

import com.workintech.s18d1.dao.BurgerDao;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import com.workintech.s18d1.util.BurgerValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/workintech/burgers")
public class BurgerController {

    private final BurgerDao burgerDao;

    @Autowired
    public BurgerController(BurgerDao burgerDao) {
        this.burgerDao = burgerDao;
    }

    @GetMapping
    public List<Burger> findAll() {
        log.info("findAll endpoint invoked.");
        return burgerDao.findAll();
    }

    @GetMapping("/{id}")
    public Burger findById(@PathVariable Long id) {
        log.info("findById endpoint invoked with id: {}", id);
        BurgerValidation.checkId(id);
        Burger burger = burgerDao.findById(id);
        if (burger == null) {
            throw new BurgerException("Burger with given id not found: " + id, HttpStatus.NOT_FOUND);
        }
        return burger;
    }

    @PostMapping
    public Burger save(@RequestBody Burger burger) {
        log.info("save endpoint invoked with burger: {}", burger);
        BurgerValidation.checkBurgerParams(burger.getName(), burger.getPrice(), burger.getContents());
        return burgerDao.save(burger);
    }

    @PutMapping("/{id}")
    public Burger update(@PathVariable Long id, @RequestBody Burger burger) {
        log.info("update endpoint invoked with id: {} and burger: {}", id, burger);
        BurgerValidation.checkId(id);
        BurgerValidation.checkBurgerParams(burger.getName(), burger.getPrice(), burger.getContents());
        
        Burger existingBurger = burgerDao.findById(id);
        if (existingBurger == null) {
            throw new BurgerException("Burger with given id not found: " + id, HttpStatus.NOT_FOUND);
        }
        burger.setId(id);
        return burgerDao.update(burger);
    }

    @DeleteMapping("/{id}")
    public Burger remove(@PathVariable Long id) {
        log.info("remove endpoint invoked with id: {}", id);
        BurgerValidation.checkId(id);
        Burger burger = burgerDao.findById(id);
        if (burger == null) {
            throw new BurgerException("Burger with given id not found: " + id, HttpStatus.NOT_FOUND);
        }
        return burgerDao.remove(id);
    }

    @GetMapping("/findByPrice")
    public List<Burger> findByPrice(@RequestParam Double price) {
        log.info("findByPrice endpoint invoked with price: {}", price);
        if (price == null || price < 0) {
            throw new BurgerException("Price must be valid", HttpStatus.BAD_REQUEST);
        }
        return burgerDao.findByPrice(price);
    }

    @GetMapping("/findByBreadType")
    public List<Burger> findByBreadType(@RequestParam String breadType) {
        log.info("findByBreadType endpoint invoked with breadType: {}", breadType);
        try {
            BreadType type = BreadType.valueOf(breadType.toUpperCase());
            return burgerDao.findByBreadType(type);
        } catch (IllegalArgumentException e) {
            throw new BurgerException("Invalid bread type: " + breadType, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByContent")
    public List<Burger> findByContent(@RequestParam String content) {
        log.info("findByContent endpoint invoked with content: {}", content);
        if (content == null || content.trim().isEmpty()) {
            throw new BurgerException("Content cannot be empty", HttpStatus.BAD_REQUEST);
        }
        return burgerDao.findByContent(content);
    }
}