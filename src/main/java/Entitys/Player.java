package Entitys;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class Player {

    public Entity spawnPlayer(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.PLAYER)
                .scale(0.1,0.1)
                .viewWithBBox("bug01.png")
                .with(new PlayerComponent())
                .collidable()
                .zIndex(10)
                .build();
    }
}
