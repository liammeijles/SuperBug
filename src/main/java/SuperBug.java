import Entitys.PlayerComponent;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;



public class SuperBug extends GameApplication {

    Entity player;

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
//        FXGL: https://youtube.com/playlist?list=PLOpV0IvTJof9mFTWf6OxMvW6zt9pLZSOV
//        GIT:  https://youtube.com/playlist?list=PLOpV0IvTJof9dyBqpTO2ugGcvt2Gy8292

        // CODE: https://github.com/AlmasB/FXGLGames/tree/master/Asteroids/src/main/java/com/almasb/fxglgames
        // video: https://www.youtube.com/watch?v=48rVgdq0mFA



        getSettings().setGlobalSoundVolume(0.1);
        getGameWorld().addEntityFactory(new GameEntityFactory());

        player = spawn("player", 0, 0);


    }

    @Override
    protected void initInput() {

        onKey(KeyCode.A, () -> player.getComponent(PlayerComponent.class).rotateLeft());
        onKey(KeyCode.D, () -> player.getComponent(PlayerComponent.class).rotateRight());
        onKey(KeyCode.W, () -> player.getComponent(PlayerComponent.class).move());
        onKeyDown(KeyCode.SPACE, () -> player.getComponent(PlayerComponent.class).shoot());

    }

    public static void main(String[] args) {
        launch(args);
    }
}
