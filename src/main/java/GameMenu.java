import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.jetbrains.annotations.NotNull;

public class GameMenu extends FXGLMenu {
    public GameMenu(@NotNull MenuType type) {
        super(type);

        BorderPane pane = new BorderPane();
        VBox vBox = new VBox(10);
        Button button1 = new Button();
        pane.setMinHeight(FXGL.getAppHeight());
        pane.setMinWidth(FXGL.getAppWidth());
        Button button = new Button();
        Button button2 = new Button();
        button.setPrefSize(144,63);
        button1.setPrefSize(144,63);
        button2.setPrefSize(144,63);
        BackgroundImage buttImage = new BackgroundImage(new Image("assets/textures/BackButton.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        BackgroundImage buttImageExit = new BackgroundImage(new Image("assets/textures/ExitButton.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        BackgroundImage buttImageOption = new BackgroundImage(new Image("assets/textures/OptionsButton.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background buttimagebg = new Background(buttImage);
        Background buttimagebgExit = new Background(buttImageExit);
        Background buttimagebgOption = new Background(buttImageOption);
        button.setBackground(buttimagebg);
        button1.setBackground(buttimagebgExit);
        button2.setBackground(buttimagebgOption);

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(button,button2,button1);
        pane.setCenter(vBox);

        BackgroundImage bg = new BackgroundImage(new Image("assets/textures/backgroundmain.png", FXGL.getAppHeight(),FXGL.getAppWidth(), true, false),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        getContentRoot().setBackground(new Background(bg));

        button.setOnAction(e -> {fireResume();});
        button1.setOnAction(e -> fireExitToMainMenu());
        button2.setOnAction(e ->{fireExit();});
        getContentRoot().getChildren().add(pane);

    }
}
