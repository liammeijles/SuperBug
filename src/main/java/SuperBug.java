import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;


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

        player = FXGL.entityBuilder()
                .at(400, 400)
                .view("bug01.png")
                .scale(0.1,0.1)
                .buildAndAttach();
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.W, () -> {
            player.translateY(-5);
            player.setRotation(0);
        });
        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-5);
            player.setRotation(-90);

        });
        FXGL.onKey(KeyCode.S, () -> {
            player.translateY(5);
            player.setRotation(180);
        });
        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(5);
            player.setRotation(90);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
