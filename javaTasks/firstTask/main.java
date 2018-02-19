public class main {
    public static void main(String args[])
    {
        Stadium arenaLviv = new Stadium();
        Stadium ukraine = new Stadium("Ukraine", "soccer", 28051, 7140);
        Stadium shanghai = new Stadium("Shanghai Stadium", "universal", 80000, 12000, "grass");
        
        System.out.println(arenaLviv.toString());
        System.out.println(ukraine.toString());
        System.out.println(shanghai.toString());
        
        arenaLviv.resetValues("Lviv", "umiversal", 35000, 10000, "fake grass");
        
        arenaLviv.printSum();
        ukraine.printSum();
        shanghai.printSum();
        
        ukraine.setNumberOfSeats(40000);
        
        Stadium.printStaticSum();
        
    }
}
