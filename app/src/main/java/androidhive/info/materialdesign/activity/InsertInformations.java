package androidhive.info.materialdesign.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import androidhive.info.materialdesign.R;

public class InsertInformations extends ActionBarActivity
{

    private EditText weight;
    private EditText age;
    private EditText height;
    private EditText username;
    private Spinner spinner_gender;
    private Spinner spinner_lavoro;
    private Spinner spinner_attivita_fisica;
    private ArrayAdapter adapter_gender;
    private ArrayAdapter adapter_lavoro;
    private ArrayAdapter adapter_attivita_fisica;
    public String weightString;
    public String ageString;
    public String heightString;
    public String gender;
    public String lavoro;
    public String attivita_fisica;
    public String usernameString;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_informations);

        //lo spinner è un menu a tendina e lo ricavo da R.java,
        // mi creo anche un adapter(contenitore in cui specifico il contenuto dello spinner_gender)
        spinner_gender = (Spinner) findViewById(R.id.insert_info_spinner_gender);
        adapter_gender = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(adapter_gender);// unisco spinner_gender ed adapter

        //registro l'evento del click sullo spinner_gender
        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                 {
                     public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
                     {
                         gender = (String) parent.getItemAtPosition(pos);
                         //quando seleziono maschio o femmina acquisisce la scelta
                     }

                     public void onNothingSelected(AdapterView<?> parent)
                     {
                         // Another interface callback
                     }
                 }
        );

        spinner_lavoro = (Spinner) findViewById(R.id.insert_info_spinner_work);
        adapter_lavoro = ArrayAdapter.createFromResource(this, R.array.work_type, android.R.layout.simple_spinner_item);
        adapter_lavoro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_lavoro.setAdapter(adapter_lavoro);

        spinner_lavoro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
                 {
                     lavoro = (String) parent.getItemAtPosition(pos);
                 }

                 public void onNothingSelected(AdapterView<?> parent)
                 {
                     // Another interface callback
                 }
             }
        );

        spinner_attivita_fisica = (Spinner) findViewById(R.id.insert_info_spinner_phys_act);
        adapter_attivita_fisica = ArrayAdapter.createFromResource(this, R.array.physical_activity, android.R.layout.simple_spinner_item);
        adapter_attivita_fisica.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_attivita_fisica.setAdapter(adapter_attivita_fisica);

        spinner_attivita_fisica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
              {
                  public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
                  {
                      attivita_fisica = (String) parent.getItemAtPosition(pos);
                  }

                  public void onNothingSelected(AdapterView<?> parent)
                  {
                      // Another interface callback
                  }
              }
        );

    }


    private boolean checkFields()
    {
        username = (EditText) findViewById(R.id.insert_info_edittext_username);
        usernameString = username.getText().toString();
        height = (EditText) findViewById(R.id.insert_info_edittext_height);//casting da una view a un edit text
        heightString = height.getText().toString();
        weight = (EditText) findViewById(R.id.insert_info_edittext_weight);
        weightString = weight.getText().toString();
        age = (EditText) findViewById(R.id.insert_info_edittext_age);
        ageString = age.getText().toString();


        //  nel caso in cui non vengono inseriti peso ed anni
        if(weightString=="" || ageString=="" || heightString=="")
            return false;
        else
            return true;

    }

    public void calculate (View v){

        double a, b, c,attività;

        //faccio un controllo sui campi inseriti
        if( this.checkFields())
        {
            double metabolismoBasale = 0;
            double fabbisognoCaloricoGiornaliero = 0;
            double fabbisognoCalorico = 0;
            double fabbisognoCaloricoTotale = 0;

            //faccio una conversione
            double weight = Double.parseDouble(weightString);//dalla classe double richiamo il metodo parseDouble per passare da stringa a Double
            double age = Double.parseDouble(ageString);
            double height = Double.parseDouble(heightString);

            /*CALCOLO DEL METABOLISMO BASALE
                DONNE: 655+(9.6*PESO IN KG)+(1.8*ALTEZZA IN CM)-(4.7*ETA IN ANNI)
                UOMINI: 66+(13.7*PESO IN KG)+(5*ALTEZZA)-(6.8*ETA IN ANNI)
            */

            if (gender.equals("donna"))
            {
                // donna
                a = 9.6 * weight;
                b = 1.8 * height;
                c = 4.7 * age;

                metabolismoBasale = 655 + a + b - c;
            }
            else
            {
                // uomo
                a = 13.7 * weight;
                b = 5 * height;
                c = 6.8 * age;

                metabolismoBasale = 66 + a + b - c;
            }

            // CALCOLO DEL FABBISOGNO CALORICO GIORNALIERO
            fabbisognoCaloricoGiornaliero = metabolismoBasale * 0.10;

            // aggiungo il lavoro
            if (lavoro.equals("sedentario"))
            {
                fabbisognoCalorico = (metabolismoBasale * 1.25) + fabbisognoCaloricoGiornaliero;
            }
            else if (lavoro.equals("leggero"))
            {
                fabbisognoCalorico = (metabolismoBasale * 1.45) + fabbisognoCaloricoGiornaliero;
            }
            else if (lavoro.equals("moderato"))
            {
                fabbisognoCalorico = (metabolismoBasale * 1.65) + fabbisognoCaloricoGiornaliero;
            }
            else if (lavoro.equals("pesante"))
            {
                fabbisognoCalorico = (metabolismoBasale * 1.85) + fabbisognoCaloricoGiornaliero;
            }

            //aggiungo l'attività fisica
            if (attivita_fisica.equals("meno di tre ore a settimana"))
            {
                attività = metabolismoBasale * 6.5;
                fabbisognoCaloricoTotale = fabbisognoCalorico + attività;
            }
            else if (attivita_fisica.equals("meno di quattro ore a settimana"))
            {
                attività = metabolismoBasale * 11;
                fabbisognoCaloricoTotale = fabbisognoCalorico + attività;
            }
            else if (attivita_fisica.equals("meno di cinque ore a settimana"))
            {
                attività = metabolismoBasale * 15;
                fabbisognoCaloricoTotale = fabbisognoCalorico + attività;
            }
            else if (attivita_fisica.equals("meno di sei ore a settimana"))
            {
                attività = metabolismoBasale * 19;
                fabbisognoCaloricoTotale = fabbisognoCalorico + attività;
            }
            else if (attivita_fisica.equals("meno di sette ore a settimana"))
            {
                attività = metabolismoBasale * 23.5;
                fabbisognoCaloricoTotale = fabbisognoCalorico + attività;
            }
            else if (attivita_fisica.equals("meno di otto ore a settimana"))
            {
                attività = metabolismoBasale * 27.5;
                fabbisognoCaloricoTotale = fabbisognoCalorico + attività;
            }
            else if (attivita_fisica.equals("meno di nove ore a settimana"))
            {
                attività = metabolismoBasale * 31.5;
                fabbisognoCaloricoTotale = fabbisognoCalorico + attività;
            }
            else if (attivita_fisica.equals("meno di tre ore a settimana"))
            {
                attività = metabolismoBasale * 36;
                fabbisognoCaloricoTotale = fabbisognoCalorico + attività;
            }



            else
            { //  caso con alcuni campi vuoti
                //faccio una prova usando toast
                Toast.makeText(this, "inserire campi obbligatori", Toast.LENGTH_LONG).show();
            }


            // sezione di codice per la scrittura dei dati dell'utente su file
            class Persona
            {
                private String user = usernameString;
                private String sesso = spinner_gender.getSelectedItem().toString() ;
                private String eta = ageString;
                private String peso = weightString;
                private String altezza = heightString;
                private String att_fis = spinner_attivita_fisica.getSelectedItem().toString();
                private String lavoro = spinner_lavoro.getSelectedItem().toString();

                // costruttore vuoto
                public Persona(){}

                //Overriding toString to be able to print out the object in a readable way
                //when it is later read from the file.
                public String toString()
                {
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("\n");
                    buffer.append(user);
                    buffer.append("\n");
                    buffer.append(sesso);
                    buffer.append("\n");
                    buffer.append(eta);
                    buffer.append("\n");
                    buffer.append(peso);
                    buffer.append("\n");
                    buffer.append(altezza);
                    buffer.append("\n");
                    buffer.append(att_fis);
                    buffer.append("\n");
                    buffer.append(lavoro);
                    buffer.append("\n");
                    buffer.append("--------------------------------");

                    return buffer.toString();
                }
            }

            String filename = "utente.txt";

            try
            {
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename));
                Persona utente = new Persona();
                outputStream.writeObject(utente);
                // chiudo l'output stream
                if(outputStream!=null)
                {
                    outputStream.flush();
                    outputStream.close();
                }
            }
            catch(FileNotFoundException ex)
            {
                ex.printStackTrace();
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }


            // sezione di codice per la gestione del bottone
            Button subButton = (Button)findViewById(R.id.insert_info_submit_button);
            subButton.setOnClickListener(new  View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent openMainActivity = new Intent(InsertInformations.this,MainActivity.class);
                    startActivity(openMainActivity);
                }
            });

            String testoDaMostrare = Double.toString(fabbisognoCaloricoTotale);

            Toast.makeText(this, testoDaMostrare, Toast.LENGTH_LONG).show();
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_insert_informations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
