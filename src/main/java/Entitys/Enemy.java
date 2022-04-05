package Entitys;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Enemy {

    public Entity spawnEnemy(SpawnData data) {
        var hp = new HealthIntComponent(2);

        var hpView = new ProgressBar(false);
        hpView.setFill(Color.LIGHTGREEN);
        hpView.setMaxValue(2);
        hpView.setWidth(85);
        hpView.setTranslateY(90);
        hpView.currentValueProperty().bind(hp.valueProperty());

        return entityBuilder(data)
                .type(EntityType.ENEMY)
                .viewWithBBox("codeicons")
                .view(hpView)
                .with(hp)
                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), getAppHeight()), 100))
                .collidable()
                .build();
    }
}
