package com.company.retailservice.feign;

import com.company.retailservice.dto.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "level-up-service")
public interface LevelUpServiceClient {

<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
    @GetMapping("/points/{customerid}")
    @ResponseStatus(HttpStatus.OK)
    public int getTotalPoints(@PathVariable("customerid") int customerId);
>>>>>>> cb49a4cd13b71eb9154934be2aecdd57f4c41e40

    @RequestMapping(value = "/levelup", method = RequestMethod.POST)
    public LevelUp createLevelUp(@RequestBody LevelUp levelUp);

    @RequestMapping(value = "/levelup", method = RequestMethod.GET)
    public List<LevelUp> getAllLevelUps();

    @RequestMapping(value = "/levelup/id/{id}", method = RequestMethod.GET)
    public LevelUp getLevelUpById(@PathVariable int id);

    @RequestMapping(value = "/levelup/id/{id}", method = RequestMethod.PUT)
    public void updateLevelUp(@PathVariable int id, @RequestBody LevelUp levelUp);

    @RequestMapping(value = "/levelup/id/{id}", method = RequestMethod.DELETE)
    public void deleteLevelUp(@PathVariable int id);

<<<<<<< HEAD

>>>>>>> d47480d11b233955b071f30045083c6f417bd26d
=======
>>>>>>> cb49a4cd13b71eb9154934be2aecdd57f4c41e40
    @RequestMapping(value = "/levelup/customerid/{customerid}", method = RequestMethod.GET)
    public List<LevelUp> getLevelUpByCustomer(@PathVariable("customerid") int customerId);


// =======
//     public LevelUp getLevelUpByCustomerId(@PathVariable int customerid);



// //    @RequestMapping(value = "/levelup/customerid/{customerid}", method = RequestMethod.GET)
// //    public LevelUp getLevelUpByCustomer(@PathVariable int customerId);
// >>>>>>> master

}
