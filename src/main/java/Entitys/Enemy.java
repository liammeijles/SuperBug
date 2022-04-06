package Entitys;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.*;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;


import static Entitys.EntityTypes.PLAYER;
import static com.almasb.fxgl.dsl.FXGL.*;

public class Enemy{

    HealthIntComponent health;

    public Entity spawnEnemy(SpawnData data) {
        health = new HealthIntComponent(1);

        double randX = (Math.random() * 2) - 1;
        double randY = (Math.random() * 1) - 1;
        double randSpeed = (Math.random() * 60) + 60;
        double playX = FXGL.getGameWorld().getSingleton(PLAYER).getCenter().getX()-400;
        double playY = FXGL.getGameWorld().getSingleton(PLAYER).getCenter().getY();

        return entityBuilder(data)
                .type(EntityTypes.ENEMY)
                .viewWithBBox("enemy01.png")
                .with(health)
                .at(400,0)
                .with(new OffscreenCleanComponent())
                //.at(Math.random() * 800, Math.random() * 800)
                .with(new ProjectileComponent(new Point2D(-randX,-randY), randSpeed))
                //.with (new ProjectileComponent(new Point2D(playX, playY), randSpeed))
                //.with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppHeight(), getAppWidth()), 50))
                //.with(new FollowComponent())
                .collidable()
                .build();
    }

    public void dmgEnemy(int dmg) {
        this.health.damage(dmg);
    }
}
