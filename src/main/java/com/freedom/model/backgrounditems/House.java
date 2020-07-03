package com.freedom.model.backgrounditems;

import com.freedom.display.Pencil;
import com.freedom.model.common.Drawable;
import com.freedom.model.common.Point;
import com.googlecode.lanterna.TextColor;

public class House extends Drawable {

    public House(Point initialPosition) {
        super(100);
        this.position = initialPosition;
    }

    @Override
    public void draw(Pencil pencil) {

        int x = position.getX();
        int y = position.getY();

        pencil.setForegroundColor(TextColor.ANSI.BLACK);

        pencil.moveTo(x, y);

        pencil.println("                            +&-");
        pencil.println("                          _.-^-._    .--.");
        pencil.println("                       .-'   _   '-. |__|");
        pencil.println("                      /     |_|     \\|  |");
        pencil.println("                     /               \\  |");
        pencil.println("                    /|     _____     |\\ |");
        pencil.println("                     |    |==|==|    |  |");
        pencil.println(" |---|---|---|---|---|    |--|--|    |  |");
        pencil.println(" |---|---|---|---|---|    |==|==|    |  |");

    }

    public Point getPosition() {
        return position;
    }
}
