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
public class Cash {
    
    private double money = 0;
    private String name;
    private ArrayList<ThreadCustomer> queue;
    private ArrayList<String> history;

    /**
     * Constructor
     * @param name
     */
    public Cash(String name) {
        this.name = name;
        queue = new ArrayList<>();
        history = new ArrayList<>();
    }
    
    /**
     * Add customer to waiting queue
     * @param customer
     * @throws java.lang.InterruptedException
     */
    public synchronized void addToQueue(ThreadCustomer customer) throws InterruptedException{
        queue.add(0, customer);
    }
    
    /**
     * Pay the products of the customer
     * @param customer
     * @throws java.lang.InterruptedException
     */
    public synchronized void payProducts(ThreadCustomer customer) throws InterruptedException{
        
        while(!customer.getName().equals(queue.get(queue.size()-1).getName())){
            wait();
        }
        
        //Gets the current customer
        System.out.println("\tIs your turn in "+name+": "+customer.getName());
        
        //Gets her payout
        double payout = customer.getPayout();
        
        //pays the ammount
        System.out.println("\t\t"+customer.getName()+" pays "+payout+"€ in "+name);
        money += payout;
        
        //The customer leaves supermarket
        history.add(customer.getName());
        queue.remove(customer);
        
        //Notify all threads
        notifyAll();
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getHistory() {
        return history;
    }
    
}
