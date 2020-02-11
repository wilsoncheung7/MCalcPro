package eecs1022.mcalcpro;

import ca.roumani.i2c.MPro;

public class Pro {
    public static void main(String args[]){
        MPro mp=new MPro();
        mp.setPrinciple("400000");
        mp.setAmortization("20");
        mp.setInterest("5");

        System.out.println(mp.computePayment("%,.2f"));
        System.out.println(mp.outstandingAfter(2,"%,16.0f"));
    }
}
