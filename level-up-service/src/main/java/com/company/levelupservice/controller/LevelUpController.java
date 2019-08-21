package com.company.levelupservice.controller;

import com.company.levelupservice.exception.NotFoundException;
import com.company.levelupservice.model.LevelUp;
import com.company.levelupservice.service.LevelUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/levelup")
@RefreshScope
public class LevelUpController {

    @Autowired
    LevelUpService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUp createLevelUp(@RequestBody LevelUp levelUp) {
        return service.saveLevelUp(levelUp);
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getLevelUp(@PathVariable("id") int id) {
        LevelUp levelUp = service.getLevelUp(id);
        if (levelUp == null)
            throw new NotFoundException("Not found");
        return levelUp;
    }

    @GetMapping("/levelup/customerid/{customerid}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getLevelUpByCustomer(@PathVariable("customerid") int customerId) {
        LevelUp levelUp = service.getLevelUpByCustomerId(customerId);
        if (levelUp == null)
            throw new NotFoundException("Not found");
        return levelUp;
    }

    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLevelUp(@PathVariable("id") int id) {
        service.deleteLevelUp(id);
    }

    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateLevelUp(@PathVariable("id") LevelUp levelUp) {
        service.updateLevelUp(levelUp);
        if (levelUp == null)
            throw new NotFoundException("Not found");
    }
}