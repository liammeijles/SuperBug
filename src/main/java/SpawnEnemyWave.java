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


    public void waveManager() {
        FXGL.getGameTimer().runAtInterval(() -> {
            List<Entity> list = getGameWorld().getEntitiesByType(EntityTypes.ENEMY);
            System.out.println("Spawn wave");
            System.out.println(levelCount);

            if (list.size() <= enemyLimit  && !waveSpawned) {
                waveSpawned = true;
                if(levelCount < 5) {
                    spawnSimpleWave(1, EnemyTypes.C);
                    enemyLimit = 1;

                }

                else if(levelCount < 10) {
                    spawnSimpleWave(2, EnemyTypes.JQ);
                    enemyLimit = 11;
                    waveDelay = 3000.0;

                }
                else if(levelCount < 15){
                    spawnDoubleWave(3, EnemyTypes.C, 1, EnemyTypes.JQ);
                    enemyLimit = 16;
                    waveDelay = 2000;
                }
                else if(levelCount < 20){
                    spawnSimpleWave(5, EnemyTypes.JS);
                    enemyLimit = 20;
                    waveDelay = 8000;

                }
                else if(levelCount < 25){
                    spawnSimpleWave(3, EnemyTypes.P);
                    enemyLimit = 12;
                    waveDelay = 1000;

                }
                else if(levelCount < 30) {
                    spawnSimpleWave(1, EnemyTypes.Python);
                    enemyLimit = 5;
                    waveDelay = 500;
                }
                else if(levelCount < 35) {
                    spawnDoubleWave(2, EnemyTypes.C, 1, EnemyTypes.JQ);
                    enemyLimit = 16;
                    waveDelay = 1500;
                }
                else if(levelCount < 40) {
                    spawnDoubleWave(3, EnemyTypes.JS, 1, EnemyTypes.Python);
                    enemyLimit = 16;
                    waveDelay = 1500;
                }
                else if(levelCount < 45) {
                    spawnSimpleWave(5, EnemyTypes.P);
                    enemyLimit = 30;
                    waveDelay = 1000;
                }
                else if(levelCount < 50) {
                    spawnSimpleWave(3, EnemyTypes.JQ);
                    enemyLimit = 30;
                    waveDelay = 1000;
                }
                else if(levelCount == 50) {
                    spawnDoubleWave(7, EnemyTypes.JS, 3, EnemyTypes.Python);
                    spawnDoubleWave(1, EnemyTypes.Ruby, 10, EnemyTypes.C);
                    enemyLimit = 0;
                    waveDelay = 1000;
                }
                levelCount++;
            }
        }, Duration.seconds(waveDelay));

    }

    public void spawnSimpleWave(int amount, EnemyTypes type) {
        FXGL.getGameTimer().runOnceAfter(() -> {
            EnemyTypes.getEnemytypes(type);
            for (int i = 0; i < amount; i++) {
                spawn("enemy", 0, 0);
                waveSpawned = false;

            }
        }, Duration.millis(waveDelay));
    }

    public void spawnDoubleWave(int amount1, EnemyTypes type1, int amount2, EnemyTypes type2){
        spawnSimpleWave(amount1, type1);
        spawnSimpleWave(amount2, type2);
    }


}
