package Entitys;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class Player {

    public Entity spawnPlayer(SpawnData data) {

        return entityBuilder(data)
                .type(EntityTypes.PLAYER)
                .viewWithBBox("bug01.png")
                .with(new HealthIntComponent(4))
                .with(new PlayerComponent())
                .collidable()
                .zIndex(10)
                .build();
    }
}
