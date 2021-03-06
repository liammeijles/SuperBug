import Entitys.*;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

public class GameEntityFactory implements EntityFactory {

    Enemy enemy = new Enemy();
    Player player = new Player();
    Bullet bullet = new Bullet();
    Rocket rocket = new Rocket();
    PowerUp powerUp = new PowerUp();

    @Spawns("powerup")
    public Entity newPowerUp(SpawnData data) {
        return powerUp.spawnPowerUp(data);
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return player.spawnPlayer(data);
    }

    @Spawns("minion")
    public Entity newMinion(SpawnData data) {return rocket.spawnRocket(data);}

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
         return enemy.spawnEnemy(data);
    }

    @Spawns("bullet")
    public Entity newBullet(SpawnData data) {
        return bullet.spawnBullet(data);
    }
}