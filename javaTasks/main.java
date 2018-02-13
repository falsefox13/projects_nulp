/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Користувач
 */
public class main {
    public static void main(String args[])
    {
        Stadium ArenaLviv = new Stadium();
        Stadium Ukraine = new Stadium("Ukraine", "soccer", 28051, 7140);
        Stadium Shanghai = new Stadium("Shanghai Stadium", "universal", 80000, 12000, "grass");
        
        System.out.println(ArenaLviv.toString());
        System.out.println(Ukraine.toString());
        System.out.println(Shanghai.toString());
        
        ArenaLviv.resetValues("Lviv", "umiversal", 35000, 10000, "fake grass");
        
        ArenaLviv.printSum();
        Ukraine.printSum();
        Shanghai.printSum();
        
        Ukraine.setNumberOfSeats(40000);
        
        Stadium.printStaticSum();
        
    }
}
