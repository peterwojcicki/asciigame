package com.freedom.model.backgrounditems;

import com.freedom.display.Pencil;
import com.freedom.model.common.Drawable;
import com.freedom.model.common.Point;
import com.googlecode.lanterna.TextColor;

public class BlockOfFlats extends Drawable {

    public BlockOfFlats(Point initialPosition) {
        super(100);
        this.position = initialPosition;
    }

    @Override
    public void draw(Pencil pencil) {

        int x = position.getX();
        int y = position.getY();

        pencil.setForegroundColor(TextColor.ANSI.BLACK);

        pencil.moveTo(x, y);

        pencil.println(" _._._._._._._._._._._._._._._._._ ");
        pencil.println(" | ___   ___    ___    ___   ___ |");
        pencil.println(" ||_|_| |_|_|  |_|_|  |_|_| |_|_||");
        pencil.println(" |IIIII_IIIII__IIIII__IIIII_IIIII|");
        pencil.println(" | ___   ___    ___    ___   ___ |");
        pencil.println(" ||_|_| |_|_|  |_|_|  |_|_| |_|_||");
        pencil.println(" |IIIII_IIIII__IIIII__IIIII_IIIII|");
        pencil.println(" | ___   ___    ___    ___   ___ |");
        pencil.println("  ||_|_| |_|_|  |_|_|  |_|_| |_|_| ");
        pencil.println(" |IIIII_IIIII__IIIII__IIIII_IIIII|)");
        pencil.println(" | ___   ___   _____   ___   ___ |");
        pencil.println(" ||_|_| |_|_|  o~|~o  |_|_| |_|_||");
        pencil.println(" |IIIII_IIIII__|_|_|__IIIII_IIIII|");
    }

    public Point getPosition() {
        return position;
    }
}
