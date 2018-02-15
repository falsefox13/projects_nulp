
package seatransportations;

public class Port {
    private String name;
    private double longtitude;
    private double latitude;
    
    public Port() {}
    
    public Port(String name)
    {
        this.name = name;
    }
    
    public Port(String name, double longtitude, double latitude)
    {
        this.name = name;
        this.longtitude = longtitude;
        this.latitude = latitude;
    }
    
    public String ToString()
    {
        return "Port called " + this.name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String newName)
    {
        this.name = newName;
    }
    
    public double getLongtitude()
    {
        return this.longtitude;
    }
    
    public void setLongtitude(double newLongtitude)
    {
        this.longtitude = newLongtitude;
    }
    
    public double getLatitude()
    {
        return this.latitude;
    }
    
    public void setLatitude(double newLatitude)
    {
        this.latitude = newLatitude;
    }
}
