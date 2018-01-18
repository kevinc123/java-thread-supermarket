/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise5_supermarket;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author DAM
 */
public class ThreadCustomer extends Thread{
    
    private double payout;
    private ArrayList<Cash> cashes;
    private Random random;

    public ThreadCustomer(String name, ArrayList<Cash> cashes) {
        super(name);
        
        this.cashes = cashes;
        
        //Initializates random
        random = new Random();
        
        
        //Generate randompayout
        this.payout = generateRandomPayout();
        
    }
    
    /**
     * Generates a random payout
     * @return 
     */
    private double generateRandomPayout() {
        double val = random.nextDouble()*100;
        val = val * 100;
        val = Math.round(val);
        val = val / 100;
        return val;
    }

    @Override
    public void run() {
        
        //Do the shopping random time
        try{
            int shopTime = random.nextInt(1000);
            Thread.sleep(shopTime);
            System.out.println(getName()+" shopped for "+shopTime+" minutes");
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }

        //Gets random cash
        Cash cash = cashes.get(random.nextInt(cashes.size()));
        System.out.println(getName()+" is waiting on "+cash.getName());
        
        try {
            //Waits in a queue
            cash.addToQueue(this);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        
        try {
            //Pays in a cash
            int delay = random.nextInt(125);
            Thread.sleep(delay); //simulates payment-delay
            cash.payProducts(this);
            System.out.println("\t\t"+getName()+" payed in the cash in "+delay+" seconds.");
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public double getPayout() {
        return payout;
    }

    public void setPayout(double payout) {
        this.payout = payout;
    }

}
