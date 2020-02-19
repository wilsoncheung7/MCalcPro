package eecs1022.mcalcpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ca.roumani.i2c.MPro;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, SensorEventListener {
    MPro mp;
    private TextToSpeech textToSpeech;
    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp=new MPro();
        this.textToSpeech=new TextToSpeech(this,this);
        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_FASTEST);
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

        String table=myMortgage+"\t\t\t\t\tn\t\t\t\t\t\t\tBalance\n";
        mp.setPrinciple(principle);
        mp.setAmortization(amortization);
        mp.setInterest(interest_rate);

        if (Integer.parseInt(amortization)>=MPro.AMORT_MIN&&Integer.parseInt(amortization)<=MPro.AMORT_MAX){


                try {
                    for(int i=0;i<=5;i++){

                    table+="\n\t\t"
                            +String.format("%8d",i)
                            +"\t\t\t\t"
                            +mp.outstandingAfter(i,"%,16.0f")
                            +"\n\n";
                    }
                    for (int j=10;j<=20;j+=5){
                        table+="\n\t\t"
                                +String.format("%8d",j)
                                +"\t\t\t\t"
                                +mp.outstandingAfter(j,"%,16.0f")
                                +"\n\n";
                    }
                }
                catch (Exception e){
                    Toast toast=Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT);
                    toast.show();

                }

        }
        ((TextView)findViewById(R.id.result)).setText(table);
        textToSpeech.speak(table,TextToSpeech.QUEUE_FLUSH,null);

    }

    @Override
    public void onInit(int i) {
        this.textToSpeech.setLanguage(Locale.CANADA);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x=sensorEvent.values[0];
        double y=sensorEvent.values[1];
        double z=sensorEvent.values[2];
        double a=Math.sqrt(x*x)+Math.sqrt(y*y)+Math.sqrt(z*z);
        if(a>40){
            ((TextView)findViewById(R.id.principle)).setText("");
            ((TextView)findViewById(R.id.amortization)).setText("");
            ((TextView)findViewById(R.id.interest_rate)).setText("");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
