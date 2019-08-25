package com.company.adminservice.service;

import com.company.adminservice.dto.LevelUp;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class LevelUpServiceLayerTest {

    LevelUpServiceLayer service;

    @Before
    public void setUp() throws Exception {
        setUpLevelUpServiceMock();
    }

    public void setUpLevelUpServiceMock() {

    service = mock(LevelUpServiceLayer.class);

        LevelUp levelUp = new LevelUp();
        levelUp.setId(1);
        levelUp.setCustomerId(1);
        levelUp.setPoints(25);
        levelUp.setMemberDate(LocalDate.of(2009, 12, 12));

        LevelUp levelUp1 = new LevelUp();
        levelUp1.setCustomerId(1);
        levelUp1.setPoints(25);
        levelUp1.setMemberDate(LocalDate.of(2009, 12, 12));

        List<LevelUp> levelUpList = new ArrayList<>();
        levelUpList.add(levelUp);

        doReturn(levelUp).when(service).createLevelUp(levelUp1);
        doReturn(levelUp).when(service).findLevelUp(1);
        doReturn(levelUpList).when(service).findAllLevelUps();
        doReturn(levelUp).when(service).findLevelUpByCustomerId(1);


    }
    @Test
    public void saveFindFindAllLevelUp() {
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setMemberDate(LocalDate.of(2009, 12, 12));
        levelUp.setPoints(25);
        levelUp = service.createLevelUp(levelUp);

        LevelUp levelUp1 = service.findLevelUp(levelUp.getId());
        List<LevelUp> levelUpList = service.findAllLevelUps();
        assertEquals(levelUpList.size(), 1);
        assertEquals(levelUp1, levelUp);

    }

    @Test
    public void getLevelUpByCustomerId() {
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setMemberDate(LocalDate.of(2009, 12, 12));
        levelUp.setPoints(25);
        levelUp = service.createLevelUp(levelUp);
        LevelUp levelUp1 = service.findLevelUpByCustomerId(levelUp.getCustomerId());

        assertEquals(levelUp, levelUp1);

    }
}