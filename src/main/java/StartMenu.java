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


public class StartMenu extends FXGLMenu {

    public StartMenu(@NotNull MenuType type) {
        super(type);

        BorderPane pane = new BorderPane();
        VBox vBox = new VBox(10);
        Label username = new Label("NAME:");
        Label reponse = new Label();
        reponse.setTextFill(Color.web("#929292"));
        reponse.setFont(Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR,20));
        username.setTextFill(Color.web("#929292"));
        username.setFont(Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR,20));
        TextField name = new TextField();
        name.setMaxSize(200,10);
        Button button1 = new Button();
        pane.setMinHeight(FXGL.getAppHeight());
        pane.setMinWidth(FXGL.getAppWidth());
        Button button = new Button();
        button.setPrefSize(144,63);
        button1.setPrefSize(144,63);
        BackgroundImage buttImage = new BackgroundImage(new Image("assets/textures/NewGameButton.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        BackgroundImage buttImageExit = new BackgroundImage(new Image("assets/textures/ExitButton.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background buttimagebg = new Background(buttImage);
        Background buttimagebgExit = new Background(buttImageExit);
        button.setBackground(buttimagebg);
        button1.setBackground(buttimagebgExit);

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(username,name,reponse,button,button1);
        pane.setCenter(vBox);

        BackgroundImage bg = new BackgroundImage(new Image("assets/textures/backgroundmain.png", FXGL.getAppHeight(),FXGL.getAppWidth(), true, false),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        getContentRoot().setBackground(new Background(bg));

        button.setOnAction(e -> {
            if (name.getText().isEmpty()){
                reponse.setText("Name must be entered");
            }else {
            String Uname = name.getText();
                System.out.println(Uname);
            fireNewGame();}
        });
        button1.setOnAction(e -> fireExit());
        getContentRoot().getChildren().add(pane);

    }
}
