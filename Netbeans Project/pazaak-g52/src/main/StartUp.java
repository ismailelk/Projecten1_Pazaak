/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import cui.Cui;
import domein.DomeinController;

/**
 *
 * @author Jonathan
 */
public class StartUp
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        DomeinController dc = new DomeinController();
        Cui io = new Cui(dc);
        
        try {
            io.startApplicatie();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
