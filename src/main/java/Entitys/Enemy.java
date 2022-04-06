package Entitys;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.*;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.TimeComponent;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.util.Duration;


import static com.almasb.fxgl.dsl.FXGL.*;

public class Enemy {

    HealthIntComponent health;

    public Entity spawnEnemy(SpawnData data) {
        health = new HealthIntComponent(5);

        double randX = (Math.random() * 3) - 1;
        double randY = (Math.random() * 3) - 1;
        double randSpeed = (Math.random() * 600) + 60;

        return entityBuilder(data)
                .type(EntityTypes.ENEMY)
                .viewWithBBox("enemy01.png")
                .with(health)
                .at(400,0)
                .with(new ProjectileComponent(new Point2D(-randX,-randY), randSpeed))
                .collidable()
                .build();
    }

    public void dmgEnemy(int dmg) {
        this.health.damage(dmg);
    }
}
