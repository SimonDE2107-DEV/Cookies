package de.simonde2107.cookies.util;

public enum GameState {

    SETUP,
    LOBBY,
    INGAME,
    ENDING;

    static GameState currentState;

    public static boolean isState(GameState gameState) {
        return currentState == gameState;
    }

    public static void setState(GameState newState) {
        currentState = newState;
    }

    public static boolean isSetup() {
        return isState(GameState.SETUP);
    }

    public static boolean isLobby() {
        return isState(GameState.LOBBY);
    }

    public static boolean isIngame() {
        return isState(GameState.INGAME);
    }

    public static boolean isEnding() {
        return isState(GameState.ENDING);
    }
}
