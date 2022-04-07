package Entitys;

import com.almasb.fxgl.dsl.components.*;
import com.almasb.fxgl.dsl.effects.SlowTimeEffect;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.TimeComponent;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class Bullet {
    public Entity spawnBullet(SpawnData data) {
        Point2D dir = data.get("dir");

        var effectComponent = new EffectComponent();

        var e = entityBuilder(data)
                .type(EntityTypes.BULLET)
                .viewWithBBox("bullet.png")
                .with(new ProjectileComponent(dir, 270))
                .with(new OffscreenCleanComponent())
                .with(new AutoRotationComponent())
                .with(new TimeComponent())
                .with(effectComponent)
                .collidable()
                .build();

        e.setOnActive(() -> {
            effectComponent.startEffect(new SlowProjectile());
        });

        return e;
    }
}
