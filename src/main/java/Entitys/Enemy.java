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

        double X = 0;
        double Y = 0;
        double randX = (Math.random() * 2) - 1;
        double randY = (Math.random() * 1) - 1;
        double playX = FXGL.getGameWorld().getSingleton(PLAYER).getCenter().getX() - 400;
        double playY = FXGL.getGameWorld().getSingleton(PLAYER).getCenter().getY();
        double randSpeed = (Math.random() * 60) + 60;
        String photo = "enemy01.png";
        Component move = new ProjectileComponent(new Point2D(-randX, -randY), randSpeed);
        EnemyTypes enemyType = EnemyTypes.getEnemytypes(null);



        switch (enemyType) {
            case C:
                X = 400;
                Y = 0;
                randSpeed = (Math.random() * 40) + 40;
                health = new HealthIntComponent(3);
                move = new ProjectileComponent(new Point2D(-randX, -randY), randSpeed);
                break;

            case JQ:
                X = 400;
                Y = 0;
                randSpeed = (Math.random() * 40) + 40;
                health = new HealthIntComponent(3);
                move = new ProjectileComponent(new Point2D(playX, playY), randSpeed);
                break;

            case JS:
                X = Math.random() * 800;
                Y = Math.random() * 800;
                randSpeed = (Math.random() * 60) + 60;
                health = new HealthIntComponent(3);
                move = new RandomMoveComponent(new Rectangle2D(0, 0, getAppHeight(), getAppWidth()), randSpeed);
                break;

            case P:
                X = 400;
                Y = 0;
                randSpeed = (Math.random() * 120) + 120;
                health = new HealthIntComponent(1);
                move = new ProjectileComponent(new Point2D(-randX, -randY), randSpeed);
                break;

            case Python:
                X = 400;
                Y = 0;
                randSpeed = (Math.random() * 120) + 120;
                health = new HealthIntComponent(1);
                move = new ProjectileComponent(new Point2D(playX, playY), randSpeed);
                break;

            case Ruby:
                X = 400;
                Y = 0;
                randSpeed = (Math.random() * 10) + 10;
                health = new HealthIntComponent(15);
                move = new ProjectileComponent(new Point2D(0, 10), randSpeed);

        }
        return entityBuilder(data)
                .type(EntityTypes.ENEMY)
                .viewWithBBox(photo)
                .with(health)
                .at(X, Y)
                .with(new OffscreenCleanComponent())
                .with (move)
                .collidable()
                .build();

        }


    public void dmgEnemy(int dmg) {
        this.health.damage(dmg);
    }
}
