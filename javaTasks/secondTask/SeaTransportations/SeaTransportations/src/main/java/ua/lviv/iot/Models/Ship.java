package ua.lviv.iot.Models;

public class Ship {
    private int id;
    private long length;
    private long width;
    private int capacity;
    private int speed;

    public Ship(final long newLength, final long newWidth, final int newCapacity, final int newSpeed) {
        this.length = newLength;
        this.width = newWidth;
        this.capacity = newCapacity;
        this.speed = newSpeed;
    }

    public final String getHeaders() {
        return "length, width, capacity, speed";
    }

    public final String toCSV() {
        return this.length + ", "
                + this.width + ", "
                + this.capacity + ", "
                + this.speed;
    }

    public final int getCapacity() {
        return this.capacity;
    }

    public final int getSpeed() {
        return this.speed;
    }

    @Override
    public final String toString() {
        return "Ship "
                + "length = " + length
                + ", width = " + width
                + ", capacity = " + capacity
                + ", speed = " + speed;
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(final int newId) {
        this.id = newId;
    }
}
