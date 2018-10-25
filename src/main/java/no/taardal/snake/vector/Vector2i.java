package no.taardal.snake.vector;

import java.util.Objects;

public class Vector2i {

    private int x;
    private int y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static int getLength(Vector2i a, Vector2i b) {
        int x = Math.abs(a.getX() - b.getX());
        int y = Math.abs(a.getY() - b.getY());
        return (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static Vector2i zero() {
        return new Vector2i(0, 0);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Vector2i copy() {
        return new Vector2i(x, y);
    }

    public Vector2i add(Vector2i vector2i) {
        return new Vector2i(x + vector2i.getX(), y + vector2i.getY());
    }

    public Vector2i add(int x, int y) {
        return new Vector2i(this.x + x, this.y + y);
    }

    public Vector2i subtract(Vector2i vector2i) {
        return new Vector2i(x - vector2i.getX(), y - vector2i.getY());
    }

    public Vector2i subtract(int x, int y) {
        return new Vector2i(this.x - x, this.y - y);
    }

    public Vector2i multiply(int value) {
        return new Vector2i(x * value, y * value);
    }

    public Vector2i divide(int value) {
        return multiply(1 / value);
    }

    public void addAssign(Vector2i vector2i) {
        x += vector2i.getX();
        y += vector2i.getY();
    }

    public void addAssign(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void subtractAssign(Vector2i vector2i) {
        x -= vector2i.getX();
        y -= vector2i.getY();
    }

    public void subtractAssign(int x, int y) {
        this.x -= x;
        this.y -= y;
    }

    public void multiplyAssign(int value) {
        x *= value;
        y *= value;
    }

    public void divideAssign(int value) {
        multiplyAssign(1 / value);
    }

    public Vector2i minus() {
        return new Vector2i(-x, -y);
    }

    public Vector2i normalize() {
        int length = getLength();
        if (length != 0) {
            int x = this.x / length;
            int y = this.y / length;
            return new Vector2i(x, y);
        } else {
            return Vector2i.zero();
        }
    }

    public int getLength() {
        return (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public int getLength(Vector2i vector2i) {
        int x = Math.abs(this.x - vector2i.getX());
        int y = Math.abs(this.y - vector2i.getY());
        return (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public int getDotProduct(Vector2i vector2i) {
        return x * vector2i.x + y * vector2i.y;
    }

    public Vector2i withX(int x) {
        return new Vector2i(x, y);
    }

    public Vector2i withY(int y) {
        return new Vector2i(x, y);
    }

    @Override
    public String toString() {
        return "Vector2i{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2i vector2i = (Vector2i) o;
        return x == vector2i.x &&
                y == vector2i.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}