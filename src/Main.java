import java.util.ArrayList;

public class Main
{
    public static void main(String args[])
    {
       // MetaMindResults sample = new MetaMindResults("terrible");
         //System.out.println(sample.toString());

        //Game halo = new Game("Halo 3 review");
        ArrayList<String> sites = new ArrayList<String>();
        sites = Navigation.google("halo 3 review");

        for(String site : sites){
            System.out.println("URL: " + site + " scores: ");
            System.out.println(Navigation.open(site));
            //MetaMindResults m =     new MetaMindResults(Navigation.open(site));
        }


    }
}
