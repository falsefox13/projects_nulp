public class Stadium {
    public static int totalNumberOfSeats = 0;
    private String name = "NoName";
    private String type; 
    private int numberOfSeats;
    private long fieldArea;
    private String covering = "grass";

    public Stadium() {
    }

    public Stadium(String name, String type, int numberOfSeats, int fieldArea) {
        setName(name);
        setType(type);
        setNumberOfSeats(numberOfSeats);
        setFieldArea(fieldArea);
    }

    public Stadium(String name, String type, int numberOfSeats, int fieldArea, String covering) {
        setName(name);
        setType(type);
        setNumberOfSeats(numberOfSeats);
        setFieldArea(fieldArea);
        setCovering(covering);
    }

    @Override
    public String toString() {
        return "Stadium is called " + name + ", is for " + type 
                    + ", games, has seats: " + numberOfSeats
                    + ", has area: " + fieldArea
                    + ", is covered with " + covering;
    }

    static void printStaticSum() {
        System.out.println("Stadiums can accommodate " + totalNumberOfSeats + " visitors");
    }

    public void printSum() {
        System.out.println("The stadium " + name + " can accomodate " + numberOfSeats + " visitors, total number is: " + totalNumberOfSeats);
    }

    public void resetValues(String name, String type, int numberOfSeats, int fieldArea, String covering) {
        setName(name);
        setType(type);
        setNumberOfSeats(numberOfSeats);
        setFieldArea(fieldArea);
        setCovering(covering);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberOfSears() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        totalNumberOfSeats -= this.numberOfSeats;
        this.numberOfSeats = numberOfSeats;
        totalNumberOfSeats += this.numberOfSeats;
    }

    public long getFieldArea() {
        return fieldArea;
    }

    public void setFieldArea(long fieldArea) {
        this.fieldArea = fieldArea;
    }

    public String getCovering() {
        return covering;
    }

    public void setCovering(String covering) {
        this.covering = covering;
    }
}
