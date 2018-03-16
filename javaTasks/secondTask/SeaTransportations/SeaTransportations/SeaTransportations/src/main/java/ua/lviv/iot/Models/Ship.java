package ua.lviv.iot.Models;

public class Ship {
    private long length;
    private long width;
    private int capacity;
    private int speed;

    public Ship() {
    }

    public Ship(final long newLength, final long newWidth, final int newCapacity, final int newSpeed) {
        this.length = newLength;
        this.width = newWidth;
        this.capacity = newCapacity;
        this.speed = newSpeed;
    }

    public final long getLength() {
        return this.length;
    }

    public final void setLength(final long newLength) {
        this.length = newLength;
    }

    public final long getWidth() {
        return this.width;
    }

    public final void setWidth(final long newWidth) {
        this.width = newWidth;
    }

    public final int getCapacity() {
        return this.capacity;
    }

    public final void setCapacity(final int newCapacity) {
        this.capacity = newCapacity;
    }

    public final int getSpeed() {
        return this.speed;
    }

    public final void setSpeed(final int newSpeed) {
        this.speed = newSpeed;
    }

    @Override
    public final String toString() {
        return "Ship "
                + "length = " + length
                + ", width = " + width
                + ", capacity = " + capacity
                + ", speed = " + speed;
    }
}
