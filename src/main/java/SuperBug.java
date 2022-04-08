
import Entitys.EntityTypes;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import Entitys.PlayerComponent;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class SuperBug extends GameApplication {

    Entity player;

    ArrayList<Texture> hearts = new ArrayList<>();
    private int preHealth;

    SpawnEnemyWave sew = new SpawnEnemyWave();

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setHeight(800);
        gameSettings.setWidth(800);
        gameSettings.setTitle("Super Bug");
        gameSettings.setVersion("1.0");
        gameSettings.setAppIcon("bug09.png");
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setSceneFactory(new UISceneFactory());
    }

    @Override
    protected void initGame() {


        // FXGL: https://youtube.com/playlist?list=PLOpV0IvTJof9mFTWf6OxMvW6zt9pLZSOV
        // GIT:  https://youtube.com/playlist?list=PLOpV0IvTJof9dyBqpTO2ugGcvt2Gy8292

        // CODE: https://github.com/AlmasB/FXGLGames/tree/master/Asteroids/src/main/java/com/almasb/fxglgames
        // video: https://www.youtube.com/watch?v=48rVgdq0mFA

        getSettings().setGlobalSoundVolume(0);
        getGameWorld().addEntityFactory(new GameEntityFactory());

        player = spawn("player");

        //sew.waveManager();


        FXGL.getGameTimer().runAtInterval(() -> {spawn("powerup", 0,0);}, Duration.millis(15000));

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
                    healt.damage(1);
                } else {
                    a.setVisible(false);

                    FXGL.getGameTimer().runOnceAfter(() -> {
                        FXGL.getGameController().gotoMainMenu();
                    }, Duration.seconds(2));

                    int savedScore = FXGL.geti("High score");
                    saveHighScore(savedScore);
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
                FXGL.inc("High score", +100);
                a.removeFromWorld();
                b.removeFromWorld();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.POWER_UP) {
            @Override
            protected void onHitBoxTrigger(Entity a, Entity b, HitBox boxA, HitBox boxB) {
                player.getComponent(PlayerComponent.class).giveRandomPowerUp();
                FXGL.inc("High score", +50);
                b.removeFromWorld();
            }
        });
    }

    @Override
    protected void initUI() {

        FXGL.getGameScene().setBackgroundRepeat("Level1.png");


        Label score = new Label("High score");
        score.setStyle("-fx-text-fill: black");
        score.setStyle("-fx-font-size:30");
        score.setTranslateX(20);
        score.setTranslateY(10);
        score.textProperty().bind(FXGL.getWorldProperties().intProperty("High score").asString());
        FXGL.getGameScene().addUINode(score);

        Label level = new Label("Level");
        level.setStyle("-fx-text-fill:red");
        level.setStyle("-fx-font-size:30");
        level.setTranslateX(20);
        level.setTranslateY(40);
        FXGL.getGameScene().addUINode(level);

        Label currentLEvel = new Label("");
        currentLEvel.setStyle("-fx-text-fill:red");
        currentLEvel.setStyle("-fx-font-size:30");
        currentLEvel.setTranslateX(100);
        currentLEvel.setTranslateY(40);
        currentLEvel.textProperty().bind(FXGL.getWorldProperties().intProperty("CurrentLevel").asString());
        FXGL.getGameScene().addUINode(currentLEvel);

        placeHearts();
    }

    private void placeHearts() {
        FXGL.getGameTimer().runAtInterval(() -> {
            int health = player.getComponent(HealthIntComponent.class).getValue();

            if (health != preHealth) {
                preHealth = health;

                if (hearts.size() > 0) {
                    for (int i = 0; i < hearts.size(); i++) {
                        FXGL.getGameScene().removeUINode(hearts.get(i));
                    }
                }

                for (int i = 0; i < health; i++) {
                    Texture heart = FXGL.getAssetLoader().loadTexture("heart.png");
                    heart.setTranslateX((i * - 40) + 730);
                    heart.setTranslateY(10);
                    heart.setScaleX(.5);
                    heart.setScaleY(.5);
                    FXGL.getGameScene().addUINode(heart);
                    hearts.add(heart);
                }
            }

        }, Duration.millis(50));


    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("High score", 0);
        vars.put("Player health", 5);
        vars.put("CurrentLevel", 1);
    }

    @Override
    protected void initInput() {

        onKey(KeyCode.A, () -> player.getComponent(PlayerComponent.class).rotateLeft());
        onKey(KeyCode.D, () -> player.getComponent(PlayerComponent.class).rotateRight());
        onKey(KeyCode.W, () -> player.getComponent(PlayerComponent.class).move());
        onKey(KeyCode.SPACE, () -> player.getComponent(PlayerComponent.class).shoot());
        onKey(KeyCode.M, () -> {

            FXGL.getGameController().pauseEngine();
        });
    }

    public void saveHighScore(int writeScore) {
        try {
            FileWriter myWriter = new FileWriter("src/main/java/scores.txt", true);
            BufferedWriter myBuffer = new BufferedWriter(myWriter);
            String wScore = Integer.toString(writeScore);
            System.out.println(wScore);
            myBuffer.write(wScore);
            myBuffer.newLine();
            myBuffer.close();
            System.out.println("New high score added");
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
