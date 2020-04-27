package com.freedom.model;

import com.freedom.display.Pencil;
import com.googlecode.lanterna.TextColor;

public class Clouds extends Drawable {

    long globalFrame;

    public Clouds(Point initialPosition) {
        super(Integer.MIN_VALUE + 1);
        this.position = initialPosition;
    }

    @Override
    public void draw(Pencil pencil) {

        globalFrame++;
        if (globalFrame % 10 == 0) {
            position = position.left();
        }

        int x = position.getX();
        int y = position.getY();

        pencil.setForegroundColor(TextColor.ANSI.WHITE);

        pencil.moveTo(x, y);

        pencil.println("                _ ");
        pencil.println("              (`  ). _           ");
        pencil.println("             (     ).              .:(`  )`. ");
        pencil.println("            _(       '`.          :(   .    )");
        pencil.println("        .=(`(      .   )     .--  `.  (    ) )");
        pencil.println("       ((    (..__.:'-'   .+(   )   ` _`  ) ) ");
        pencil.println("       `(       ) )       (   .  )     (   )  ._   ");
        pencil.println("         ` __.:'   )     (   (   ))     `-'.-(`  ) ");
        pencil.println("      ( )       --'       `- __.'         :(      )) ");
        pencil.println("     (_.'          .')                    `(    )  ))");
        pencil.println("                  (_  )                     ` __.:'    ");


    }

    public Point getPosition() {
        return position;
    }
}
