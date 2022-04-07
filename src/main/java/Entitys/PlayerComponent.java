package Entitys;

import com.almasb.fxgl.core.View;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
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

import static com.almasb.fxgl.dsl.FXGL.*;

public class PlayerComponent extends Component {

    private boolean alInPowerUp = false;
    private double cooldown;
    private double speedup = 0.4;

    Sound sound = new Sound();

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
    }

    public void shoot() {
        Timer timer = FXGL.getGameTimer();

        if (timer.getNow() > cooldown + speedup) {
            sound.playSE(3);
            cooldown = FXGL.getGameTimer().getNow();

            Point2D center = entity.getCenter().subtract(37/2.0, 13/2.0);
            Vec2 dir = Vec2.fromAngle(entity.getRotation() - 90);
            spawn("bullet", new SpawnData(center.getX(), center.getY()).put("dir", dir.toPoint2D()));
        }
    }

    public void giveRandomPowerUp() {
        if (alInPowerUp) return;
        alInPowerUp = true;

        PowerUps randPower = PowerUps.getRandom();
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
                        Point2D center = entity.getCenter().subtract(37/2.0, 13/2.0);
                        Vec2 dir = Vec2.fromAngle(entity.getRotation() - 90);

                        double posX = 1 * dir.x;
                        double posY = 1 * dir.y;

                        spawn("bullet", new SpawnData(center.getX(), center.getY()).put("dir", new Point2D(posY, -posX)));
                        spawn("bullet", new SpawnData(center.getX(), center.getY()).put("dir", new Point2D(-posX, -posY)));
                        spawn("bullet", new SpawnData(center.getX(), center.getY()).put("dir", new Point2D(-posY, posX)));
                        spawn("bullet", new SpawnData(center.getX(), center.getY()).put("dir", new Point2D(posX, posY)));
                    }, Duration.millis(timeToSleepMilis * i));
                }


                FXGL.getGameTimer().runOnceAfter(() -> {
                    updateType("bug01.png");
                    alInPowerUp = false;
                }, Duration.millis(timeToSleepMilis * timeToShoot));

                break;

        }
    }

    private void updateType(String bugType) {
        ViewComponent view = entity.getComponent(ViewComponent.class);
        view.onRemoved();
        view.addChild(new ImageView(PATH + bugType));
    }
}