package eecs1022.mcalcpro;

import ca.roumani.i2c.MPro;

public class MortgageModel {

    private String p;
    private String a;
    private String i;
    MPro mp=new MPro();

    public MortgageModel(String p, String a, String i) {
        this.p = p;
        this.a = a;
        this.i = i;
        mp.setPrinciple(this.p);
        mp.setAmortization(this.a);
        mp.setInterest(this.i);
        mp.outstandingAfter(Integer.valueOf(this.a),"%,16.0f");
    }

    public String computePayment(){
        return mp.computePayment("%,.2f");
    }

    public String outstandingAfter(){
        return mp.outstandingAfter(3,"%,16.0f");
    }
}
