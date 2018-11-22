package util;


import java.util.*;

public class Taal {

    private static String taal, taalpad;
    private static ResourceBundle resourceBundle;
    Scanner sc = new Scanner(System.in);
    private static List<String> talen;

    public Taal() {
        this(null, null);
    }

    public Taal(String taal, String taalPad) {
        talen = new ArrayList<>();
        talen.add("NL");
        talen.add("FR");
        talen.add("EN");
        Taal.taal = taal;
        Taal.taalpad = taalPad;
    }

    public static List<String> getTalen(){
        return talen;
    }
    
    public String getTaal() {
        return taal;
    }

    public void setTaal(String taal) {
        Taal.taal = taal;
    }

    public String getTaalpad() {
        return taalpad;
    }

    public void setTaalpad(String taalpad) {
        Taal.taalpad = taalpad;
    }

    public void kiesTaal(int taalnr) {
        //maak een array van alle taalpaden, meer flexibiliteit!
        setTaal(talen.get(taalnr - 1));
        setTaalpad("util/language_" + talen.get(taalnr - 1).toLowerCase());
        
    }

    public static String getWoordUitBundle(String key) {
        resourceBundle = ResourceBundle.getBundle(taalpad);
        return resourceBundle.getString(key);
    }
}