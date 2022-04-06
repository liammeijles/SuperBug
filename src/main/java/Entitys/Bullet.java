package Entitys;

import com.almasb.fxgl.dsl.components.Effect;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.TimeComponent;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class Bullet {
    public Entity spawnBullet(SpawnData data) {
        Point2D dir = data.get("dir");

        var effectComponent = new EffectComponent();

        var e = entityBuilder(data)
                .type(EntityTypes.BULLET)
                .viewWithBBox(new Circle(8, Color.RED))
                .with(new ProjectileComponent(dir, 270))
                .with(new OffscreenCleanComponent())
                .with(new TimeComponent())
                .with(effectComponent)
                .collidable()
                .build();

        e.setOnActive(() -> {
            effectComponent.startEffect(new Bullet.SuperSlowTimeEffect());
        });

        return e;
    }

    class SuperSlowTimeEffect extends Effect {

        public SuperSlowTimeEffect() {
            super(Duration.seconds(0));
        }

        @Override
        public void onStart(Entity entity) {
            entity.getComponent(TimeComponent.class).setValue(0.05);
        }

        @Override
        public void onEnd(Entity entity) {
            entity.getComponent(TimeComponent.class).setValue(3.0);
        }
    }
}
