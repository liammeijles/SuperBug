package Entitys;

import com.almasb.fxgl.core.View;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.time.Timer;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PlayerComponent extends Component {

    private boolean alInPowerUp = false;
    private double cooldown;
    private double speedup = 0.4;
    private boolean enableShotGun = false;
    private int extraRandomBullet = 0;

    private final String PATH = "/assets/textures/";

    @Override
    public void onAdded() {
        cooldown = FXGL.getGameTimer().getNow();
        entity.getTransformComponent().setScaleOrigin(new Point2D(540, 540));
    }

    public void rotateLeft() {
        entity.rotateBy(-5);
    }

    public void rotateRight() {
        entity.rotateBy(5);
    }

    public void move() {
        Vec2 dir = Vec2.fromAngle(entity.getRotation() - 90).mulLocal(4);
        entity.translate(dir);

        double playerCenterX = entity.getCenter().getX();
        double playerCenterY = entity.getCenter().getY();

        if (playerCenterY < -40) entity.setPosition(new Point2D(entity.getX(), getAppHeight()));
        if (playerCenterX < - 40) entity.setPosition(new Point2D(getAppWidth(), entity.getY()));
        if (playerCenterX > getAppWidth() + 40) entity.setPosition(new Point2D(-entity.getWidth(), entity.getY()));
        if (playerCenterY > getAppHeight() + 40) entity.setPosition(new Point2D(entity.getX(), -entity.getHeight()));
    }

    public void shoot() {

        Timer timer = FXGL.getGameTimer();

        if (timer.getNow() > cooldown + speedup) {

            cooldown = FXGL.getGameTimer().getNow();
            spawnBullet(Vec2.fromAngle(entity.getRotation() - 90).toPoint2D());

            for (int i = 0; i < extraRandomBullet; i++) {
                double randomBullet = (Math.random() * 30) + 80;
                spawnBullet(Vec2.fromAngle(entity.getRotation() - randomBullet).toPoint2D());
            }

            if (enableShotGun) {

                spawnBullet(Vec2.fromAngle(entity.getRotation() - 80).toPoint2D());
                spawnBullet(Vec2.fromAngle(entity.getRotation() - 70).toPoint2D());
                spawnBullet(Vec2.fromAngle(entity.getRotation() - 100).toPoint2D());
                spawnBullet(Vec2.fromAngle(entity.getRotation() - 110).toPoint2D());
            }
        }
    }


    private void spawnBullet(Point2D rotation) {
        Point2D center = entity.getCenter().subtract(0f, 13/2.0);
        spawn("bullet", new SpawnData(center.getX(), center.getY()).put("dir", rotation));
    }

    public void giveRandomPowerUp() {
        extraRandomBullet++;
        if (alInPowerUp) return;
        alInPowerUp = true;

        PowerUps randPower = PowerUps.getRandom();
        //PowerUps randPower = PowerUps.HOMING_MISSILE;
        updateType(randPower.getBugType());

        switch (randPower) {
            case FAST_SHOOTING:
                speedup = 0.1;
                FXGL.getGameTimer().runOnceAfter(() -> {
                    speedup = 0.4;

                    updateType("bug01.png");
                    alInPowerUp = false;
                }, Duration.seconds(10));
                break;
            case MEGA_HEALTH:
                HealthIntComponent health = entity.getComponent(HealthIntComponent.class);
                health.setValue(10000);
                FXGL.getGameTimer().runOnceAfter(() -> {
                    health.setValue(4);

                    updateType("bug01.png");
                    alInPowerUp = false;
                }, Duration.seconds(10));
                break;
            case QUAD_ATTACK:

                int timeToShoot = 60;
                int timeToSleepMilis = 100;

                for (int i = 0; i < timeToShoot; i++) {
                    FXGL.getGameTimer().runOnceAfter(() -> {
                        Vec2 dir = Vec2.fromAngle(entity.getRotation() - 90);

                        double posX = 1 * dir.x;
                        double posY = 1 * dir.y;

                        spawnBullet(new Point2D(posY, -posX));
                        spawnBullet(new Point2D(-posX, -posY));
                        spawnBullet(new Point2D(-posY, posX));
                        spawnBullet(new Point2D(posX, posY));


                    }, Duration.millis(timeToSleepMilis * i));
                }


                FXGL.getGameTimer().runOnceAfter(() -> {
                    updateType("bug01.png");
                    alInPowerUp = false;
                }, Duration.millis(timeToSleepMilis * timeToShoot));

                break;

            case SHOTGUN_HEALTH:
                enableShotGun = true;

                FXGL.getGameTimer().runOnceAfter(() -> {
                    updateType("bug01.png");
                    enableShotGun = false;
                    alInPowerUp = false;
                }, Duration.seconds(10));

                break;

            case HOMING_MISSILE:
                int timeBetween = 100;

                for (int i = 0; i < extraRandomBullet * 50; i++) {
                    FXGL.getGameTimer().runOnceAfter(() -> {
                        spawn("minion", entity.getCenter().subtract(0f, 13/2.0));
                    }, Duration.millis(timeBetween * i));

                }

                FXGL.getGameTimer().runOnceAfter(() -> {
                    updateType("bug01.png");
                    alInPowerUp = false;
                }, Duration.millis(extraRandomBullet * 5 * timeBetween));


                break;
        }
    }

    private void updateType(String bugType) {
        ViewComponent view = entity.getComponent(ViewComponent.class);
        view.onRemoved();
        view.addChild(new ImageView(PATH + bugType));
    }
}