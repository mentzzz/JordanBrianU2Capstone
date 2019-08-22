package com.company.adminservice.service;

import com.company.adminservice.dto.Inventory;
import com.company.adminservice.dto.LevelUp;
import com.company.adminservice.feign.InventoryServiceClient;
import com.company.adminservice.feign.LevelUpServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LevelUpServiceLayer {


    @Autowired
    private LevelUpServiceClient levelUpServiceClient;


    // CREATE LEVELUP
    public LevelUp createLevelUp(LevelUp levelUp) {
        levelUp = levelUpServiceClient.createLevelUp(levelUp);
        return levelUp;
    }


    // FIND LEVELUP BY ID
    public LevelUp findLevelUp(int id) {
        LevelUp levelUp = levelUpServiceClient.getLevelUpById(id);
        return levelUp;
    }

    // FINDALL LEVELUP
    public List<LevelUp> findAllLevelUps() {
        List<LevelUp> levelUp = levelUpServiceClient.getAllLevelUps();
        return levelUp;
    }

    // UPDATE LEVELUP
    public void updateILevelUp(int id, LevelUp levelUp) {
        levelUpServiceClient.updateLevelUp(id, levelUp);
    }

    // DELETE LEVELUP BY ID
    public void deleteLevelUp(int id) {
        levelUpServiceClient.deleteLevelUp(id);
    }


    // FIND LEVELUP BY CUSTOMER ID
    public LevelUp findLevelUpByCustomerId(int id) {
        LevelUp levelUp = levelUpServiceClient.getLevelUpByCustomerId(id);
        return levelUp;
    }










}
