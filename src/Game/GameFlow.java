package Game;

import Animation.AnimationRunner;
import Levels.Level1;
import Levels.Level2;
import Levels.Level3;
import Levels.Level4;
import Levels.Level5;
import Levels.LevelInformation;
import Screens.LevelEndScreen;
import Screens.LevelSelectionScreen;
import Screens.MainMenuScreen;
import Utils.Counter;
import Utils.MouseTracker;
import biuoop.GUI;
import biuoop.KeyboardSensor;

/**
 * Manages the high-level application flow, screen transitions, session level progression,
 * and level lifecycle.
 */
public class GameFlow {
    private GUI gui;
    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private MouseTracker mouse;
    private int unlockedLevel;

    /**
     * Constructs a GameFlow instance.
     */
    public GameFlow() {
        this.gui = new GUI("Breakout Arcade", Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
        this.runner = new AnimationRunner(this.gui, 60);
        this.keyboard = this.gui.getKeyboardSensor();
        this.mouse = new MouseTracker(this.gui);
        this.unlockedLevel = 1; // Initially only Level 1 is unlocked; 2-5 are locked
    }

    /**
     * Executes the main application loop, handling transitions between the Main Menu,
     * Level Selection, active gameplay, and end screens.
     */
    public void run() {
        boolean runningApp = true;

        while (runningApp) {
            // 1. Main Menu Screen (mouse-driven)
            MainMenuScreen mainMenu = new MainMenuScreen(this.mouse);
            this.runner.run(mainMenu);

            String menuAction = mainMenu.getSelectedAction();
            if ("Quit".equalsIgnoreCase(menuAction)) {
                runningApp = false;
                break;
            }

            // 2. Level Selection Flow (mouse-driven)
            boolean inLevelSelect = true;
            while (inLevelSelect) {
                // Baseline score resets when entering/returning to Level Selection
                int sessionScore = 0;

                LevelSelectionScreen levelSelect = new LevelSelectionScreen(this.mouse, this.unlockedLevel);
                this.runner.run(levelSelect);

                int chosenLevel = levelSelect.getChosenLevel();
                if (chosenLevel == -1) {
                    // Back to Main Menu
                    inLevelSelect = false;
                    break;
                }

                // 3. Play selected level and handle level progression
                boolean playLevelLoop = true;
                int scoreAtStartOfLevel = sessionScore;

                while (playLevelLoop) {
                    // Fresh score counter initialized from score at the start of this level attempt
                    Counter levelScore = new Counter(scoreAtStartOfLevel);
                    LevelInformation levelInfo = createLevel(chosenLevel);
                    GameLevel gameLevel = new GameLevel(levelInfo, this.keyboard, this.runner, this.mouse, levelScore);
                    gameLevel.initialize();
                    this.runner.run(gameLevel);

                    if (gameLevel.isQuitRequested()) {
                        // Exited via Pause Screen -> safely return to Level Selection screen
                        playLevelLoop = false;
                        break;
                    }

                    if (gameLevel.isRetryRequested()) {
                        // Paused and chose "RETRY" -> restart level attempt with score reverted to baseline
                        continue;
                    }

                    boolean isWin = gameLevel.isLevelSuccess();
                    if (isWin) {
                        // Successfully cleared level: permanently unlock next level for session
                        if (chosenLevel == this.unlockedLevel && this.unlockedLevel < 5) {
                            this.unlockedLevel = chosenLevel + 1;
                        }
                        sessionScore = levelScore.getValue();
                    }

                    // 4. Level Outcome Screen (Win / Loss, mouse-driven)
                    LevelEndScreen endScreen = new LevelEndScreen(this.mouse, isWin, chosenLevel, levelScore.getValue());
                    this.runner.run(endScreen);

                    String endAction = endScreen.getSelectedAction();
                    if ("Next".equalsIgnoreCase(endAction)) {
                        if (chosenLevel < 5) {
                            chosenLevel++;
                            scoreAtStartOfLevel = sessionScore; // Carry over cleared score into next level
                        } else {
                            playLevelLoop = false;
                        }
                    } else if ("Retry".equalsIgnoreCase(endAction)) {
                        // Retry current level: baseline score remains scoreAtStartOfLevel
                    } else if ("MainMenu".equalsIgnoreCase(endAction)) {
                        playLevelLoop = false;
                        inLevelSelect = false;
                    } else {
                        // "LevelSelect" -> return to Level Selection screen
                        playLevelLoop = false;
                    }
                }
            }
        }

        this.gui.close();
        System.exit(0);
    }

    private LevelInformation createLevel(int levelNumber) {
        switch (levelNumber) {
            case 1:
                return new Level1();
            case 2:
                return new Level2();
            case 3:
                return new Level3();
            case 4:
                return new Level4();
            case 5:
                return new Level5();
            default:
                return new Level1();
        }
    }
}
