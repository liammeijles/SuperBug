package Entitys;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.AutoRotationComponent;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.TimeComponent;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;
import java.util.Timer;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.random;


public class Rocket extends Component {

    private int randomEnemy = 9999;
    private double timer;

    @Override
    public void onAdded() {
        timer = FXGL.getGameTimer().getNow();
    }

    @Override
    public void onUpdate(double tpf) {
        double FixedTimer = FXGL.getGameTimer().getNow() - timer;
        if (FixedTimer > 15) {
            entity.removeFromWorld();
        }

        List<Entity> ets = FXGL.getGameWorld().getEntitiesByType(EntityTypes.ENEMY);

        if (ets.size() > 0) {
            if (randomEnemy == 9999) {
                randomEnemy = random(0, ets.size());
            }
            try {
                entity.translateTowards(ets.get(randomEnemy).getCenter(), 100 * tpf);
            } catch (Exception e) {
                randomEnemy = random(0, ets.size());
            }
        } else {
            randomEnemy = 9999;
        }
    }

    public Entity spawnRocket(SpawnData data) {

        var effectComponent = new EffectComponent();

        var e = entityBuilder(data)
                .type(EntityTypes.MINION)
                .viewWithBBox("rocket.png")
                .with(new Rocket())
                .with(new OffscreenCleanComponent())
                .with(new TimeComponent())
                .with(effectComponent)
                .with(new AutoRotationComponent())
                .collidable()
                .build();


        e.setOnActive(() -> {
            effectComponent.startEffect(new SlowProjectile());
        });

        return e;
    }
}
