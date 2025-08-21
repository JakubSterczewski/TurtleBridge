package p02.game;

public enum GameElement {
    EMPTY(0),
    PLAYER_RIGHT(1),
    PLAYER_LEFT(2),
    PLAYER_RIGHT_WITH_PACKAGE(3),
    PLAYER_LEFT_WITH_PACKAGE(4),
    TURTLE_EMERGED(5),
    TURTLE_SUBMERGED(6),
    FISH(7),
    RECEIVER(8);

    private final int value;

    GameElement(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GameElement getEnum(int value) {
        for (GameElement e : GameElement.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        throw new IllegalArgumentException("Illegal argument: " + value);
    }
}