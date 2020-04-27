import com.freedom.display.Pencil;
import com.freedom.display.Sky;
import com.freedom.model.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class Main {
    public static void main(String[] args) throws Exception {

        Player player = new Player(new Point(0, -4));

        final Pencil pencil = new Pencil(player);

        Level level1 = new Level("Undead city");
        level1.add(player);
//        drawables.add(new Flat1(new Point(8, -6)));
        level1.add(new Flat2(new Point(29, -18)));
        level1.add(new Tree1(new Point(8, -13)));
        level1.add(new Sky());
//        level1.add(new Grass());
        level1.add(new Ground(new Point(-2, 1), 20, 1));
        level1.add(new Ground(new Point(15, 5), 10, 1));


        for (int i = 0; i < 10; i++) {
            level1.add(new Clouds(new Point(-100 + 80 * i, -20 + 2 * (i % 2))));
        }

        long index = 0;
        while (index < Long.MAX_VALUE) {

            index++;

            level1.render(pencil);


            Thread.sleep(10);

            KeyStroke keyStroke = pencil.pollInput();
            if (keyStroke != null) {
                if (keyStroke.getKeyType().equals(KeyType.ArrowLeft)) {
                    player.moveLeft();
                }
                if (keyStroke.getKeyType().equals(KeyType.ArrowRight)) {
                    player.moveRight();
                }
                if (keyStroke.getKeyType().equals(KeyType.ArrowUp)) {
                    player.jump();
                }
            }
        }

        pencil.exitPrivateMode();
    }
}
