package com.company.levelupqueue;


import com.company.levelupqueue.model.LevelUp;
import com.company.levelupqueue.util.feign.Payload.Payload;
import com.company.levelupqueue.util.feign.PointsClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
//@Component
public class Listener {

    @Autowired
    private final PointsClient client;

    public Listener(PointsClient client) {
        this.client = client;
    }

    @RabbitListener(queues = LevelUpQueueApplication.QUEUE_NAME)
    public void receiveMessage(Payload payload) {
        LevelUp levelUp = new LevelUp();
        levelUp.setId(payload.getId());
        levelUp.setCustomerId(payload.getCustomerId());
        levelUp.setPoints(payload.getPoints());
        levelUp.setMemberDate(payload.getMemberDate());
        if (payload.getId() == 0) {
            System.out.println("this is a create level up");
            client.createLevelUp(levelUp);
        } else {
            client.updateLevelUp(levelUp, levelUp.getId());
            System.out.println("this shit is a update level up and the id is " + levelUp.getId());
        }
    }
}
