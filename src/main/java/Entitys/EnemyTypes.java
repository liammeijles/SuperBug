package Entitys;

public enum EnemyTypes {
    C, JQ, JS, P, Python, Ruby;
    static EnemyTypes enemyType = EnemyTypes.C;
    public static EnemyTypes getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }

    public static EnemyTypes getEnemytypes(EnemyTypes type) {
        if (type != null){
            enemyType = type;
        }
        return enemyType;
    }
}
