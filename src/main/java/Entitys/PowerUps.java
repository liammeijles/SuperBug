package Entitys;

public enum PowerUps {
    // normal bug type = bug01.png
    FAST_SHOOTING("bug07.png"),
    MEGA_HEALTH("bug08.png"),
    SHOTGUN_HEALTH("bug05.png"),
    QUAD_ATTACK("bug06.png");

    private final String bugType;

    PowerUps(String bugType) {
        this.bugType = bugType;
    }

    public String getBugType() {
        return bugType;
    }

    public static PowerUps getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }

}
