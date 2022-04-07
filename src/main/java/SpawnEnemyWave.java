import Entitys.EntityTypes;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.util.Duration;

import java.util.Dictionary;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;


public class SpawnEnemyWave {

    private boolean waveSpawned = false;
    private int levelCount = 4;

    public void waveManager() {
        FXGL.getGameTimer().runAtInterval(() -> {
            List<Entity> list = getGameWorld().getEntitiesByType(EntityTypes.ENEMY);
            System.out.println("Spawn wave");
            System.out.println(list.size());
            System.out.println(levelCount);
            if (list.size() < 1 && !waveSpawned) {
                waveSpawned = true;
                FXGL.getGameTimer().runOnceAfter(() -> {
                    if (levelCount < 3) {
                        spawnSimpleWave(1, 1000);}
                    else {
                        spawnSimpleWave(100, 10000);}
                    levelCount++;
                    waveSpawned = false;
                }, Duration.seconds(0));
            }
        }, Duration.seconds(1.1));

    }

    public void spawnSimpleWave(int amount, int seconds) {
        for (int i = 0; i < amount; i++) {

            FXGL.getGameTimer().runOnceAfter(() -> {
                spawn("enemy", 0,0);
            }, Duration.millis(seconds));

        }
    }

}
