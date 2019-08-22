package com.company.retailservice.feign;

import com.company.retailservice.dto.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "level-up-service")
public interface LevelUpServiceClient {

    @RequestMapping(value = "/levelup/customerid/{customerid}", method = RequestMethod.GET)
    public LevelUp getLevelUp(@PathVariable int customerid);

}
