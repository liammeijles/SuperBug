
import Entitys.EntityTypes;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.handlers.CollectibleHandler;
import com.almasb.fxgl.entity.Entity;
import Entitys.PlayerComponent;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.awt.*;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class SuperBug extends GameApplication {

    Entity player;

    SpawnEnemyWave sew = new SpawnEnemyWave();

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setHeight(800);
        gameSettings.setWidth(800);
        gameSettings.setTitle("Super Bug");
        gameSettings.setVersion("1.0");
        gameSettings.setAppIcon("bug09.png");
    }

    @Override
    protected void initGame() {
        // FXGL: https://youtube.com/playlist?list=PLOpV0IvTJof9mFTWf6OxMvW6zt9pLZSOV
        // GIT:  https://youtube.com/playlist?list=PLOpV0IvTJof9dyBqpTO2ugGcvt2Gy8292

        // CODE: https://github.com/AlmasB/FXGLGames/tree/master/Asteroids/src/main/java/com/almasb/fxglgames
        // video: https://www.youtube.com/watch?v=48rVgdq0mFA

        getSettings().setGlobalSoundVolume(0);
        getGameWorld().addEntityFactory(new GameEntityFactory());

        player = spawn("player", -400, -400);

        sew.waveManager();

        //playSound(0);
        FXGL.getGameTimer().runAtInterval(() -> {
            spawn("enemy", 0,0);
        }, Duration.millis(2000));

        FXGL.getGameTimer().runAtInterval(() -> {
            spawn("powerup", 0,0);
        }, Duration.millis(15000));

    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.ENEMY) {
            @Override
            protected void onHitBoxTrigger(Entity a, Entity b, HitBox boxA, HitBox boxB) {
                HealthIntComponent healt = a.getComponent(HealthIntComponent.class);
                System.out.println("Health of Player: " + healt.getValue());
                FXGL.inc("Player health", -1);
                if (healt.getValue() > 0) {
                    healt.damage(0);
                } else {
                    a.setVisible(false);
                    playSE(4);
                    // TODO: player dead
                }
                b.removeFromWorld();
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.ENEMY, EntityTypes.BULLET) {
            @Override
            protected void onCollision(Entity a, Entity b) {
                HealthIntComponent healt = a.getComponent(HealthIntComponent.class);

                if (healt.getValue() > 1) {
                    healt.damage(1);
                } else {
                    FXGL.inc("High score", +100);
                    a.removeFromWorld();
                }
                b.removeFromWorld();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.ENEMY, EntityTypes.MINION) {
            @Override
            protected void onCollision(Entity a, Entity b) {
                a.removeFromWorld();
                b.removeFromWorld();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.POWER_UP) {
            @Override
            protected void onHitBoxTrigger(Entity a, Entity b, HitBox boxA, HitBox boxB) {
                //stopSound();
                //playSE(1);
                //FXGL.getGameTimer().runOnceAfter(() -> {
                //    stopSound();
                //    playSound(0);
                //}, Duration.seconds(10));
                player.getComponent(PlayerComponent.class).giveRandomPowerUp();
                b.removeFromWorld();
            }
        });
    }

    @Override
    protected void initUI() {
        Label score = new Label("High score");
        score.setStyle("-fx-text-fill: black");
        score.setTranslateX(25);
        score.setTranslateY(25);
        score.textProperty().bind(FXGL.getWorldProperties().intProperty("High score").asString());
        FXGL.getGameScene().addUINode(score);

        Label playerHealth = new Label("Player health");
        playerHealth.setStyle("-fx-text-fill: black");
        playerHealth.setTranslateX(775);
        playerHealth.setTranslateY(25);
        playerHealth.textProperty().bind(FXGL.getWorldProperties().intProperty("Player health").asString());
        FXGL.getGameScene().addUINode(playerHealth);

//        int playerHealth = 5;
//        var heart1 = FXGL.getAssetLoader().loadTexture("heart.png");
//        heart1.setTranslateX(775);
//        heart1.setTranslateY(25);
//        heart1.textProperty().bind(FXGL.getWorldProperties().intProperty("Heart 1").asString());
//        FXGL.getGameScene().addUINode(heart1);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("High score", 0);
        vars.put("Player health", 5);
    }

    @Override
    protected void initInput() {

        onKey(KeyCode.A, () -> player.getComponent(PlayerComponent.class).rotateLeft());
        onKey(KeyCode.D, () -> player.getComponent(PlayerComponent.class).rotateRight());
        onKey(KeyCode.W, () -> player.getComponent(PlayerComponent.class).move());
        onKey(KeyCode.SPACE, () -> player.getComponent(PlayerComponent.class).shoot());
    }

    //Sound setup
    //Initialize sound
    Sound sound = new Sound();

    //Play sound on loop
    public void playSound(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    //Stop sound
    public void stopSound() {
        sound.stop();
    }

    //Play 1 time sound effect
    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
