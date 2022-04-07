import Entitys.EnemyTypes;
import Entitys.EntityTypes;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.util.Duration;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;


public class SpawnEnemyWave {

    private boolean waveSpawned = false;
    private int levelCount = 0;
    private int enemyLimit = 0;
    private double waveDelay = 1.0;
    public EnemyTypes enemyType = EnemyTypes.C;


    public void waveManager() {
        FXGL.getGameTimer().runAtInterval(() -> {
            List<Entity> list = getGameWorld().getEntitiesByType(EntityTypes.ENEMY);
            System.out.println("Spawn wave");
            System.out.println(levelCount);

            if (list.size() <= enemyLimit  && !waveSpawned) {
                waveSpawned = true;
                if (levelCount < 5) {
                    EnemyTypes.getEnemytypes(EnemyTypes.C);
                    spawnSimpleWave(1);
                    enemyLimit = 5;

                }

                else {
                    EnemyTypes.getEnemytypes(EnemyTypes.JQ);
                    spawnSimpleWave(3);
                    enemyLimit = 11;
                    waveDelay = 3000.0;

                }
                levelCount++;
            }
        }, Duration.seconds(waveDelay));

    }

    public void spawnSimpleWave(int amount) {
        FXGL.getGameTimer().runOnceAfter(() -> {
            for (int i = 0; i < amount; i++) {
                spawn("enemy", 0, 0);
                waveSpawned = false;

            }
        }, Duration.millis(waveDelay));
    }

}
