package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {
    private Map<Long, Kangaroo> kangaroos;

    @PostConstruct
    public void init() {
        kangaroos = new HashMap<>();
    }

    @GetMapping
    public List<Kangaroo> getAll() {
        return kangaroos.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Kangaroo get(@PathVariable long id) {
        if (id <= 0) {
            throw new ZooException("Id cannot be negative", HttpStatus.BAD_REQUEST);
        }
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Id does not exist", HttpStatus.NOT_FOUND);
        }
        return kangaroos.get(id);
    }

    @PostMapping
    public Kangaroo create(@RequestBody Kangaroo kangaroo) {
        if (kangaroo.getName() == null  || kangaroo.getWeight() <= 0 || kangaroo.getHeight() <= 0) {
            throw new ZooException("Fields cannot empty, negative, or 0", HttpStatus.BAD_REQUEST);
        }
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }

    @PutMapping("/{id}")
    public Kangaroo update(@PathVariable long id, @RequestBody Kangaroo kangaroo) {
        if (id <= 0) {
            throw new ZooException("Id cannot be 0 or negative", HttpStatus.BAD_REQUEST);
        }
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Id does not exist", HttpStatus.BAD_REQUEST);
        }
        kangaroos.put(id, kangaroo);
        return kangaroo;
    }

    @DeleteMapping("/{id}")
    public Kangaroo delete(@PathVariable long id) {
        if (id <= 0) {
            throw new ZooException("Id cannot be 0 or negative", HttpStatus.BAD_REQUEST);
        }
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Id does not exist", HttpStatus.BAD_REQUEST);
        }
        Kangaroo kangaroo = kangaroos.get(id);
        kangaroos.remove(id);
        return kangaroo;
    }
}