package no.taardal.snake.keyboard;

public class Keyboard {

    private static final int NO_KEY_PRESSED = 0;

    private int keyCode;

    public boolean isPressed(int keyCode) {
        return this.keyCode == keyCode;
    }

    public void update() {

    }

    public void press(int keyCode) {
        this.keyCode = keyCode;
    }

    public void release() {
        keyCode = 0;
    }
}
