package eecs1022.mcalcpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ca.roumani.i2c.MPro;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.compute);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(v);
            }
        });
    }

    public void buttonClicked(View v){
        EditText principleView=findViewById(R.id.principle);
        String principle=principleView.getText().toString();

        EditText amortizationView=findViewById(R.id.amortization);
        String amortization=amortizationView.getText().toString();

        EditText rateView=findViewById(R.id.interest_rate);
        String interest_rate=rateView.getText().toString();

        MortgageModel mortgageModel=new MortgageModel(principle,amortization,interest_rate);
        String myMortgage="Monthly Payment = "+mortgageModel.computePayment()
                +"\n"
                +"\n"
                +"By making this payments monthly for "+amortization
                +"\n"
                +"years, the mortgage will be paid in full. But if\n"
                +"you terminate the mortgage on its nth anniversary, the balance still owing depends\n"
                +"on n as shown below:"
                +"\n"
                +"\n";
//        ((TextView)findViewById(R.id.result)).setText(myMortgage);
        String table=myMortgage+"\t\t\t\t\tn\t\t\t\t\t\t\tBalance\n";
        MPro mp=new MPro();
        if (Integer.parseInt(amortization)>=MPro.AMORT_MIN&&Integer.parseInt(amortization)<=MPro.AMORT_MAX){

            for(int i=0;i<6;i++){
                try {

                }
                catch (NumberFormatException nf){
                    System.out.println("Error");
                }


                table+="\n\t\t"
                        +String.format("%8d",i)
                        +"\t\t\t\t"
                        +mortgageModel.outstandingAfter()
                        +"\n\n";

            }
        }
        ((TextView)findViewById(R.id.result)).setText(table);

    }
}
