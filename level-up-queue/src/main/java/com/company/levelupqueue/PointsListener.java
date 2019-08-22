package com.company.levelupqueue;

import com.company.levelupqueue.model.LevelUp;
import com.company.levelupqueue.util.feign.LevelUpServiceClient;
import com.company.levelupqueue.util.messages.LevelEntry;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class PointsListener {

    @Autowired
    private final LevelUpServiceClient client;

    public PointsListener(LevelUpServiceClient client) {
        this.client = client;
    }

    @RabbitListener(queues = LevelUpQueueApplication.QUEUE_NAME)
    public void receiveMessage(LevelEntry levelUp){
        LevelUp newLevel = new LevelUp();
        newLevel.setId(levelUp.getId());
        newLevel.setCustomerId(levelUp.getCustomerId());
        newLevel.setMemberDate(levelUp.getMemberDate());
        newLevel.setPoints(levelUp.getPoints());
        if (levelUp.getId() == 0){
            System.out.println("This is a create level up");
            client.createLevelUp(newLevel);
        } else {
            client.updateLevelUp(newLevel, newLevel.getId());
            System.out.println("this is a update level up and the id is "+ newLevel.getId());
        }
    }
}
