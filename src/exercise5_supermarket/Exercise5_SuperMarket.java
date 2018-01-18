/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise5_supermarket;

import java.util.ArrayList;

/**
 *
 * @author DAM
 */
public class Exercise5_SuperMarket {
    
    private static final int NUM_CUSTOMERS = 25;
    private static final int NUM_CASHES = 2;
    
    private static ArrayList<ThreadCustomer> customers;
    private static ArrayList<Cash> cashes;
    private static double totalMoney = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Initializate the arrays
        customers = new ArrayList<>(NUM_CUSTOMERS);
        cashes = new ArrayList<>(NUM_CASHES);
        
        //Create cashes
        createCashes();
        
        //Create all threads
        createThreads();
        
        //Start all threads
        startThreads();
        
        //Controls supermarket
        controlSuperMarket();

    }

    /**
     * Create cashes
     */
    private static void createCashes() {
        for (int i = 0; i < NUM_CASHES; i++) {
            String name = "Cash"+(i+1);
            Cash cash = new Cash(name);
            cashes.add(cash);
        }
        System.out.println("SuperMarket Opened:\nThere are "+NUM_CASHES+" cash/cashes\n");
    }
    
     /**
     * Create threads
     */
    private static void createThreads() {
        for (int i = 0; i < NUM_CUSTOMERS; i++) {
            String name = "Customer"+(i+1);
            ThreadCustomer customer = new ThreadCustomer(name, cashes);
            customers.add(customer);
        }
    }
    
    /**
     * Starts all threads
     */
    private static void startThreads() {
        for (ThreadCustomer customer : customers) {
            customer.start();
        }
    }

    /**
     * Controls the SuperMarket
     * Prints the history
     * Prints the total earned
     */
    private static void controlSuperMarket() {
        //Waits to all threads to finish
        for (ThreadCustomer customer : customers) {
            try {
                customer.join();
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        //Prints stats when supermarker are closed
        System.out.println("\nSuperMarket closed");
        for (Cash cash : cashes) {
            System.out.println("\n"+cash.getName()+" earned "+roundNumber(cash.getMoney())+"€");
            System.out.print(cash.getName()+" history: ");
            for(String customer: cash.getHistory()){
                System.out.print(customer+" ");
            }
            totalMoney += cash.getMoney();
        }
        System.out.println("\n\nSuperMarket earned a total of: " + roundNumber(totalMoney)+"€");
    }

    /**
     * Rounds a double to 2 
     * @param val
     * @return
     */
    private static double roundNumber(double val) {
        val = val * 100.0;
        val = Math.round(val);
        val = val / 100.0;
        return val;
    }

}
