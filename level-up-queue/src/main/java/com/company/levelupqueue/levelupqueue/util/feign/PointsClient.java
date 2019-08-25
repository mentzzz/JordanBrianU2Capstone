package com.company.levelupqueue.levelupqueue.util.feign;

import com.company.levelupqueue.levelupqueue.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@FeignClient("level-up-service")
@RequestMapping("/levelup")
public interface PointsClient {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUp createLevelUp(@RequestBody LevelUp levelUp);

    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateLevelUp(@RequestBody LevelUp levelUp, @PathVariable int id);

}
