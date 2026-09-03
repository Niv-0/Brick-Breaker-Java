import Game.GameFlow;

public class BreakoutGame {
    /**
     * The main method to start the game.
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        GameFlow flow = new GameFlow();
        flow.run();
    }
}
