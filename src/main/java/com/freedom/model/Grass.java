package com.freedom.model;

import com.freedom.display.Pencil;
import com.freedom.model.common.Drawable;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;

public class Grass extends Drawable {

    public Grass() {
        super(Integer.MIN_VALUE + 1);
    }

    @Override
    public void draw(Pencil pencil) {
        pencil.setForegroundColor(TextColor.ANSI.GREEN);
        for (int x = 0; x < pencil.getTerminalSize().getColumns(); x++) {
            for (int y = pencil.getTerminalSize().getRows() / 2 + 3; y < pencil.getTerminalSize().getRows(); y++) {
                pencil.moveToAbsolute(x, y);
                pencil.print(Symbols.BLOCK_MIDDLE);
            }
        }

    }
}
