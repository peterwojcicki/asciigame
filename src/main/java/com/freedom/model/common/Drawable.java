package com.freedom.model.common;

import com.freedom.display.Pencil;

public abstract class Drawable implements Comparable<Drawable> {
    protected Point position;
    int zindex;

    public Drawable(int zindex) {
        this.zindex = zindex;
    }

    public abstract void draw(Pencil pencil);

    public Point getPosition() {
        return position;
    }

    @Override
    public int compareTo(Drawable o) {
        if (o.zindex < zindex) {
            return 1;
        } else if (o.zindex > zindex) {
            return -1;
        } else {
            return 0;
        }
    }
}
