import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class SuperBug extends GameApplication {
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


    }

    public static void main(String[] args) {
        launch(args);
    }
}
