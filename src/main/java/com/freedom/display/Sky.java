package com.freedom.display;

import com.freedom.model.common.Drawable;
import com.googlecode.lanterna.TextColor;

public class Sky extends Drawable {

    public Sky() {
        super(Integer.MIN_VALUE);
    }

    @Override
    public void draw(Pencil pencil) {
        pencil.setBackgroundColor(TextColor.Factory.fromString("#BDDFFF"));
        for (int x = 0; x < pencil.getTerminalSize().getColumns(); x++) {
            for (int y = 0; y < pencil.getTerminalSize().getRows(); y++) {
                pencil.moveToAbsolute(x, y);
                pencil.print(" ");
            }
        }

    }
}
