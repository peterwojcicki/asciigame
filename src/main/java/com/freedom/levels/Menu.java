package com.freedom.levels;

import com.freedom.model.Point;
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
        level1.add("                                                                                 ");
        level1.add("                        (                     (          (             )         ");
        level1.add("          (             )\\ )     (       )    )\\ )       )\\    (    ( /(   (     ");
        level1.add("          )\\    (      (()/(    ))\\   ( /(   (()/(     (((_)   )\\   )\\())  )\\ )  ");
        level1.add("       _ ((_)   )\\ )    ((_))  /((_)  )(_))   ((_))    )\\___  ((_) (_))/  (()/(  ");
        level1.add("      | | | |  _(_/(    _| |  (_))   ((_)_    _| |    ((/ __|  (_) | |_    )(_)) ");
        level1.add("      | |_| | | ' \\)) / _` |  / -_)  / _` | / _` |     | (__   | | |  _|  | || | ");
        level1.add("       \\___/  |_||_|  \\__,_|  \\___|  \\__,_| \\__,_|      \\___|  |_|  \\__|   \\_, | ");
        level1.add("                                                                           |__/  ");

        levels.add(level1);

        List<String> level2 = new ArrayList<>();
        level2.add("                                             )             (                                        ");
        level2.add("                 (    (                    ( /(    (        )\\ )                                     ");
        level2.add(" (   (       )   )\\   )\\     (    (        )\\())   )\\ )    (()/(    (           (  (                 ");
        level2.add(" )\\  )\\   ( /(  ((_) ((_)   ))\\   )\\ )    ((_)\\   (()/(     /(_))   )(     (    )\\))(     (      (   ");
        level2.add("((_)((_)  )(_))  _    _    /((_) (()/(      ((_)   /(_))   (_))_   (()\\    )\\  ((_)()\\    )\\ )   )\\  ");
        level2.add("\\ \\ / /  ((_)_  | |  | |  (_))    )(_))    / _ \\  (_) _|    |   \\   ((_)  ((_) _(()((_)  _(_/(  ((_) ");
        level2.add(" \\ V /   / _` | | |  | |  / -_)  | || |   | (_) |  |  _|    | |) | | '_| / _ \\ \\ V  V / | ' \\)) (_-< ");
        level2.add("  \\_/    \\__,_| |_|  |_|  \\___|   \\_, |    \\___/   |_|      |___/  |_|   \\___/  \\_/\\_/  |_||_|  /__/ ");
        level2.add("                                  |__/                                                               ");
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

        pencil.moveToAbsolute(width / 2 - 40, 27);
        for (int i = 0; i < levels.size(); i++) {

            if (currentOption == i) {
                pencil.setForegroundColor(TextColor.ANSI.RED);
            } else {
                pencil.setForegroundColor(TextColor.ANSI.WHITE);
            }


            List<String> level = levels.get(i);
            level.forEach(line -> pencil.println(line));
            pencil.println("");
        }

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
                    }
                }
                if (keyStroke.getKeyType().equals(KeyType.ArrowDown)) {
                    if (currentOption < levels.size() - 1) {
                        currentOption = currentOption + 1;
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
