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

    static int level;

    public Entity spawnEnemy(SpawnData data) {
        health = new HealthIntComponent(1);
        level++;


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
                health = new HealthIntComponent(10 * level);
                move = new ProjectileComponent(new Point2D(-randX, -randY), randSpeed);
                photo = "enemy01.png";
                break;

            case JQ:
                X = 400;
                Y = 0;
                randSpeed = (Math.random() * 40) + 40;
                health = new HealthIntComponent(10 * level);
                move = new ProjectileComponent(new Point2D(playX, playY), randSpeed);
                photo = "enemy02.png";
                break;

            case JS:
                X = Math.random() * 800;
                Y = Math.random() * 800;
                randSpeed = (Math.random() * 60) + 60;
                health = new HealthIntComponent(10 * level);
                move = new RandomMoveComponent(new Rectangle2D(0, 0, getAppHeight(), getAppWidth()), randSpeed);
                photo = "enemy03.png";
                break;

            case P:
                X = 400;
                Y = 0;
                randSpeed = (Math.random() * 120) + 120;
                health = new HealthIntComponent(3 * level);
                move = new ProjectileComponent(new Point2D(-randX, -randY), randSpeed);
                photo = "enemy04.png";
                break;

            case Python:
                X = 400;
                Y = 0;
                randSpeed = (Math.random() * 120) + 120;
                health = new HealthIntComponent(3 * level);
                move = new ProjectileComponent(new Point2D(playX, playY), randSpeed);
                photo = "enemy05.png";
                break;

            case Ruby:
                X = 400;
                Y = 0;
                randSpeed = (Math.random() * 10) + 10;
                health = new HealthIntComponent(50 * level);
                move = new ProjectileComponent(new Point2D(0, 10), randSpeed);
                photo = "enemy06.png";
                break;

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
