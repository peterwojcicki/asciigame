package com.freedom.levels;

import com.freedom.model.Point;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;
import java.util.List;

public class Menu extends Level {

    Integer selectedLevel;
    private final List<List<String>> levels;
    private volatile int currentOption;

    public Menu() {
        super("Undead City", new Point(30, 5));
        levels = new ArrayList<>();
        List<String> level1 = new ArrayList<>();
        level1.add("Undead City");

        levels.add(level1);

        List<String> level2 = new ArrayList<>();
        level2.add("Lake Of Drowns");
        levels.add(level2);

        currentOption = 0;
    }

    @Override
    void init() {
    }

    @Override
    public void render() {
        clearScreen();

        int width = pencil.getTerminalSize().getColumns();
        int height = pencil.getTerminalSize().getRows();

        pencil.setForegroundColor(TextColor.ANSI.WHITE);
        pencil.moveToAbsolute(width / 2 - 32, 5);
        pencil.println("                            ,-.");
        pencil.println("       ___,---.__          /'|`\\          __,---,___");
        pencil.println("    ,-'    \\`    `-.____,-'  |  `-.____,-'    //    `-.");
        pencil.println("  ,'        |           ~'\\     /`~           |        `.");
        pencil.println(" /      ___//              `. ,'          ,  , \\___      \\");
        pencil.println("|    ,-'   `-.__   _         |        ,    __,-'   `-.    |");
        pencil.println("|   /          /\\_  `   .    |    ,      _/\\          \\   |");
        pencil.println("\\  |           \\ \\`-.___ \\   |   / ___,-'/ /           |  /");
        pencil.println(" \\  \\           | `._   `\\\\  |  //'   _,' |           /  /");
        pencil.println("  `-.\\         /'  _ `---'' , . ``---' _  `\\         /,-'");
        pencil.println("     ``       /     \\    ,='/ \\`=.    /     \\       ''");
        pencil.println("             |__   /|\\_,--.,-.--,--._/|\\   __|");
        pencil.println("             /  `./  \\\\`\\ |  |  | /,//' \\,'  \\");
        pencil.println("            /   /     ||--+--|--+-/-|     \\   \\");
        pencil.println("           |   |     /'\\_\\_\\ | /_/_/`\\     |   |");
        pencil.println("            \\   \\__, \\_     `~'     _/ .__/   /");
        pencil.println("             `-._,-'   `-._______,-'   `-._,-'");

        pencil.moveToAbsolute(width / 2 - 10, 27);
        for (int i = 0; i < levels.size(); i++) {

            List<String> level = levels.get(i);
            if (currentOption == i) {
                pencil.setForegroundColor(TextColor.ANSI.RED);

                level.forEach(line -> pencil.println("" + Symbols.TRIANGLE_RIGHT_POINTING_BLACK + " " + line));
            } else {
                pencil.setForegroundColor(TextColor.ANSI.WHITE);

                level.forEach(line -> pencil.println("  " + line));
            }

            pencil.println("");
        }

        pencil.setForegroundColor(TextColor.ANSI.WHITE);
        pencil.moveToAbsolute(width / 2 - 14, height - 2);
        pencil.println("Copyright by DragonRider1666");

        pencil.flush();
    }

    private void clearScreen() {
        pencil.setBackgroundColor(TextColor.ANSI.BLACK);
        for (int x = 0; x < pencil.getTerminalSize().getColumns(); x++) {
            for (int y = 0; y < pencil.getTerminalSize().getRows(); y++) {
                pencil.moveToAbsolute(x, y);
                pencil.print(" ");
            }
        }
    }

    @Override
    public void play() throws Exception {

        init();

        while (!isLevelCompleted()) {
            render();
            Thread.sleep(10);

            KeyStroke keyStroke = pencil.pollInput();
            if (keyStroke != null) {
                if (keyStroke.getKeyType().equals(KeyType.ArrowUp)) {
                    if (currentOption > 0) {
                        currentOption = currentOption - 1;
                    } else {
                        currentOption = levels.size() - 1;
                    }
                }
                if (keyStroke.getKeyType().equals(KeyType.ArrowDown)) {
                    if (currentOption < levels.size() - 1) {
                        currentOption = currentOption + 1;
                    } else {
                        currentOption = 0;
                    }
                }
                if (keyStroke.getKeyType().equals(KeyType.Enter)) {
                    selectedLevel = currentOption;
                }
            }
        }

        pencil.exitPrivateMode();
    }

    @Override
    boolean isLevelCompleted() {
        return selectedLevel != null;
    }
}
