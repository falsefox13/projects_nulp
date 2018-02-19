package seatransportations;

public class Ship {
    private long length;
    private long width;
    private int capacity;
    private int speed;

    public Ship() {}

    public Ship(long length, long width, int capacity, int speed) {
        this.length = length;
        this.width = width;
        this.capacity = capacity;
        this.speed = speed;
    }

    public long getLength() {
        return this.length;
    }

    public void setLength(long newLength) {
        this.length = newLength;
    }

    public long getWidth() {
        return this.width;
    }

    public void setWidth(long newWidth) {
        this.width = newWidth;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int newCapacity) {
        this.capacity = newCapacity;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }
}

