package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {

    private Map<Long, Koala> koalas;

    @PostConstruct
    public void init() {
        koalas = new HashMap<>();
    }

    @GetMapping
    public List<Koala> getAll() {
        return koalas.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Koala get(@PathVariable long id) {
        if (id <= 0) {
            throw new ZooException("Id cannot be negative", HttpStatus.BAD_REQUEST);
        }
        if (!koalas.containsKey(id)) {
            throw new ZooException("Id does not exist", HttpStatus.NOT_FOUND);
        }
        return koalas.get(id);
    }

    @PostMapping
    public Koala create(@RequestBody Koala koala) {
        if (koala.getName() == null || koala.getWeight() <= 0 || koala.getSleepHour() <= 0) {
            throw new ZooException("Fields cannot be null, negative, or 0", HttpStatus.BAD_REQUEST);
        }
        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/{id}")
    public Koala update(@PathVariable long id, @RequestBody Koala koala) {
        if (id <= 0) {
            throw new ZooException("Id cannot be 0 or negative", HttpStatus.BAD_REQUEST);
        }
        if (!koalas.containsKey(id)) {
            throw new ZooException("Id does not exist", HttpStatus.BAD_REQUEST);
        }
        koalas.put(id, koala);
        return koala;
    }

    @DeleteMapping("/{id}")
    public Koala delete(@PathVariable long id) {
        if (id <= 0) {
            throw new ZooException("Id cannot be 0 or negative", HttpStatus.BAD_REQUEST);
        }
        if (!koalas.containsKey(id)) {
            throw new ZooException("Id does not exist", HttpStatus.BAD_REQUEST);
        }
        Koala koala = koalas.get(id);
        koalas.remove(id);
        return koala;
    }
}