package com.company.levelupqueue.util.feign;

import com.company.levelupqueue.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="level-up-service")
public interface LevelUpServiceClient {
    @RequestMapping(value="/notes/{id}",method= RequestMethod.PUT)
    public void updateLevelUp(@RequestBody LevelUp levelUp, @PathVariable int id);


    @RequestMapping(value="/notes",method= RequestMethod.POST)
    public LevelUp createLevelUp(@RequestBody LevelUp levelUp);

    @GetMapping("/levelup/customerid/{customerid}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getLevelUpByCustomer(@PathVariable("customerid") int customerId);
}
