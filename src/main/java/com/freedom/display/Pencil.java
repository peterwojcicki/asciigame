package com.freedom.display;

import com.freedom.model.Player;
import com.freedom.model.Point;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.nio.charset.Charset;

public class Pencil {
    private Terminal terminal;
    private Player player;
    private boolean cursorPositionInVisibleArea = false;
    private Point cursorPosition;

    public Pencil(Player player) {
        this.player = player;
        try {
            terminal = new DefaultTerminalFactory(System.out, System.in, Charset.forName("UTF8")).createTerminal();
            this.terminal.setCursorVisible(false);
            this.terminal.enterPrivateMode();
            this.cursorPosition = new Point(terminal.getCursorPosition().getColumn(), terminal.getCursorPosition().getRow());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TerminalSize getTerminalSize() {
        try {
            return terminal.getTerminalSize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setBackgroundColor(TextColor color) {
        try {
            terminal.setBackgroundColor(color);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setForegroundColor(TextColor color) {
        try {
            terminal.setForegroundColor(color);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void moveTo(int x, int y) {
        try {
            Point playerPosition = player.getPosition();
            TerminalSize terminalSize = terminal.getTerminalSize();

            int newX = x - playerPosition.getX() + terminalSize.getColumns() / 2;
            int newY = y - playerPosition.getY() + terminalSize.getRows() / 2;

            setCursorPosition(newX, newY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isWithinVisibleArea(int newX, int newY) {
        try {
            return newX >= 0 && newX < terminal.getTerminalSize().getColumns()
                    && newY >= 0 && newY < terminal.getTerminalSize().getRows();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void moveToAbsolute(int x, int y) {
        setCursorPosition(x, y);
    }

    public void print(char ch) {
        try {
            if (cursorPositionInVisibleArea) {
                terminal.putCharacter(ch);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void print(String text) {
        for (char ch : text.toCharArray()) {
            print(ch);
            setCursorPosition(cursorPosition.getX() + 1, cursorPosition.getY());
        }
    }

    public void println(String text) {
        int cursorPositionX = cursorPosition.getX();
        int cursorPositionY = cursorPosition.getY();

        print(text);

        setCursorPosition(cursorPositionX, cursorPositionY + 1);
    }

    public void exitPrivateMode() {
        try {
            terminal.exitPrivateMode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void flush() {
        try {
            terminal.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public KeyStroke pollInput() {
        try {
            return terminal.pollInput();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCursorPosition(int x, int y) {
        cursorPosition = new Point(x, y);
        cursorPositionInVisibleArea = isWithinVisibleArea(x, y);

        try {
            terminal.setCursorPosition(x, y);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
