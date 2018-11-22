package cui;

import domein.DomeinController;
import java.util.Scanner;
import util.Taal;
import exceptions.*;
import java.util.ArrayList;
import java.util.List;

public class Cui {
    /**
     * Aanmaken van private final attributen van DomeinController dc
     */
    private final DomeinController dc;
    
    /**
     * Constructor waar de domeincontroller wordt gedefinieerd.
     * @param dc 
     */
    public Cui(DomeinController dc){
            this.dc = dc;
    }

    /**
     * Dit is opstrart van het spel
     * Er wordt een object van de klasse Taal aangemaakt, en de gebruiker krijgt de mogelijkheid om een taal te kiezen.
     * Na het kiezen van een taal krijgt de gebruiker een keuzemenu waar hij moet kiezen wat hij wil doen
     * @throws Exception 
     */
    public void startApplicatie() throws Exception {
        Taal t = new Taal();
        
        ApplicatieUC1 uc1 = new ApplicatieUC1(dc);
        ApplicatieUC3 uc3 = new ApplicatieUC3(dc);
        ApplicatieUC9 uc9 = new ApplicatieUC9(dc);
      
        //default taal
        int taalnr = 1;
        t.kiesTaal(taalnr);
        
        //TAALMENU
        List<String> taalOpties = new ArrayList<>();
        taalOpties.add("Nederlands");
        taalOpties.add("Français");
        taalOpties.add("English");
        taalnr = kiesMenuOptie("Uw keuze", "Kies een taal / Choisir une langue / Choose a language", taalOpties);
        if(taalnr == -1)
            return;
        t.kiesTaal(taalnr);
        
        //HOOFDMENU
        int keuze = 0;
        List<String> hoofdmenuOpties = new ArrayList<>();
        hoofdmenuOpties.add(Taal.getWoordUitBundle("optie1"));
        hoofdmenuOpties.add(Taal.getWoordUitBundle("optie2"));
        hoofdmenuOpties.add(Taal.getWoordUitBundle("optie3"));
        do{
            keuze = kiesMenuOptie(Taal.getWoordUitBundle("kies_toepassing"), "Menu:", hoofdmenuOpties);
            if(keuze == -1)
                return;

            switch (keuze)
            {
                case 1: uc1.maakNieuweSpeler();break;
                case 2: uc3.maakNieuweWedstrijd();break;
                case 3: uc9.laadWedstrijd();break;
            }
        }while(keuze != -1);
    }
     
    /**
     * Dit is een algemene methode die overal in de code opnieuw wordt aangeroepen, deze methode controleert telkens de invoer van de gebruiker.
     * En zet om indien nodig.
     * @param prompt
     * @param datatype
     * @return 
     */
public static String geefInvoer(String prompt, int datatype){
    Scanner sc = new Scanner(System.in);
    String invoer = "";
    Boolean error = true;
    do{
        System.out.printf("%s (STOP = x): ", prompt);
        try{
            invoer = sc.nextLine().trim();
            if(invoer.equals("x")){
                error = false;
                break;
            }
            switch(datatype){
                case 1: Integer.parseInt(invoer); break;
                case 2: Double.parseDouble(invoer); break;
                case 3: Boolean.parseBoolean(invoer);break;
                default: break; //strings
            }
            error = false;
        }catch(NumberFormatException e){
            System.out.println(Taal.getWoordUitBundle("Geefeennummerin"));
        }
    }while(error);
    return invoer;
}
    
/**
 * Deze methode geeft de invoer terug
 * @param prompt
 * @return 
 */
public static String geefInvoer(String prompt){
    return geefInvoer(prompt, 0);
}    
/**
 * Deze methode zorgt ervoor dat de invoer van het keuze menu wordt gecontroleerd
 * try-catch en do-while met een boolean als flag
 * @param prompt
 * @param menuHoofding
 * @param menuOpties
 * @return 
 */
public static int kiesMenuOptie(String prompt, String menuHoofding, List<String> menuOpties){
    int keuze = -1, aantalOpties = menuOpties.size();
    boolean error = true;
    String outputString = "\n" + menuHoofding, invoer = "";
    for(int i = 0; i < aantalOpties; i++)
            outputString += String.format("%n%3d. %s", i+1, menuOpties.get(i));
    do{         
        System.out.printf("%s%n", outputString);
        try{
            invoer = geefInvoer(prompt, 1);
            if(invoer.equals("x"))
                return -1;
            keuze = Integer.parseInt(invoer);
            if(keuze <= 0 || keuze > aantalOpties)
                throw new MenukeuzeBuitenInterval(Taal.getWoordUitBundle("Geefeennummeromeenjuisteoptietekiezen") + aantalOpties);
            error = false;
        }catch(MenukeuzeBuitenInterval e){
            System.out.println(e.getMessage());
        }
        
    }while(error);
     
    return keuze;
}
/**
 * Deze methode maakt menuOptie, geeft deze terug
 * @param prompt
 * @param menuHoofding
 * @return 
 */

public static int kiesMenuOptie(String prompt, String menuHoofding){
    List<String> menuOpties = new ArrayList<>();
    menuOpties.add(Taal.getWoordUitBundle("ja"));
    menuOpties.add(Taal.getWoordUitBundle("nee"));
    return kiesMenuOptie(prompt, menuHoofding, menuOpties);
}

/**
 * Een interne controleer methode, die kijkt of de invoer x was(Zo ja, dan moet hij terugkeren RETURN) anders weergeven return heeft niet gewerkt en de invoer was...
 */
    private void testInvoer() {
        String invoer = geefInvoer(Taal.getWoordUitBundle("geefeengetalin"), 1);
        if(invoer.equals("x")) return;
        System.out.println(Taal.getWoordUitBundle("returnheeftnietgewerkt"));
        System.out.println(Taal.getWoordUitBundle("deinvoerwas") + invoer);
    }
}
