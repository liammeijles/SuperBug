package Entitys;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PowerUp {

    public Entity spawnPowerUp(SpawnData data) {

        double windowX = getAppWidth();
        double windowY = getAppHeight();

        int randomX = (int)random(10, windowX - 10);
        int randomY = (int)random(10, windowY - 10);

        return entityBuilder(data)
                .type(EntityTypes.POWER_UP)
                .at(randomX, randomY)
                .viewWithBBox(new Circle(8, Color.GREEN))
                .collidable()
                .zIndex(10)
                .build();

    }
}
