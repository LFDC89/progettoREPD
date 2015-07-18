package androidhive.info.materialdesign.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import androidhive.info.materialdesign.classes.DataPreferences;
import androidhive.info.materialdesign.classes.Food;
import androidhive.info.materialdesign.classes.FoodsData;
import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.activity.FoodDetailsActivityHome;


public class HomeFragment extends Fragment
{
    public Context context = null;

    // adapter for the listView in this fragment
    public static ArrayAdapter<String> mUserFoodAdapter = null;

    public HomeFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = getActivity();    // get the current context
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        // get all the foods data in the JSON file and stored in the FoodsData static class
        List<Food> data = FoodsData.foodsData;

        // retrieve user foods stored with SharedPreferences method (saved just the indexes)
        String  start_data_string = DataPreferences.readPreference(context, DataPreferences.PREFS_USER_FOODS, DataPreferences.PUF_KEY);

        // split data indexes, because are separated with commas
        final String[] start_data_indexes = start_data_string.split(",");

        // build starting data for the HomeFragment listView
        List<String> start_data = new ArrayList<String>();

        // default case
        if (start_data_string.equals("no food added"))
            start_data.add("no food added");

        else if (data == null)
            start_data.add("loading data");

        else
        {
            // for each food saved by the user
            for (int i = 0; i < start_data_indexes.length; i++)
            {
                // obtain the index of the current food
                int food_position = Integer.parseInt(start_data_indexes[i]);

                // add food description in the listView
                start_data.add(data.get(food_position).getDescription());

                // just a LOG
                // Log.d(" Home fragment: ----> ", data.get(food_position).getDescription());

            }
        }

        mUserFoodAdapter = new ArrayAdapter<String>(
                context,                            // the current context (this activity)
                R.layout.list_item_foodslist,       // the name of the layout to apply to each rows
                R.id.list_item_foodslist_textview,  // ID of textview to populate
                start_data                          // data
        );

        // inflate the fragment and setAdapter for listView
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ListView listView = (ListView)rootView.findViewById(R.id.fragment_home_listview);

        listView.setAdapter(mUserFoodAdapter);

        // listView OnClick method
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                // start FoodDetailsActivityHome and send the current food position
                Intent intent = new Intent(getActivity(), FoodDetailsActivityHome.class)
                        .putExtra(Intent.EXTRA_TEXT, start_data_indexes[position]);

                // start the activity
                startActivity(intent);
            }
        });


        // IF the HomeFragment called via intent.  Inspect the intent for forecast data.
        //Intent intent = getActivity().getIntent();
        //if (intent != null)
        //{
        //    Log.v("INDENT HOME ", " XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX ");
        //}


        // Sezione relativa alla progress bar delle calorie

        /* PROGRESS BAR STUFF */
        final ProgressBar progressBar_kcal = (ProgressBar) rootView.findViewById(R.id.fragment_home_progressBar);
        final int kcal_progressStatus = 0;
        final int kcal_max = 100; // variabile kcal_max che dovra essere letta da InsertInformations
        final android.os.Handler handler_kcal = new android.os.Handler();
        /*
        // Aggiorno la progress bar
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(kcal_progressStatus<kcal_max)
                {
                    // Qui ci va il codice che aggiorna il progress Status
                    // ci va un metodo del tipo onClick {progressstatus + = calories food}

                    // aggiorno la progress bar
                    handler_kcal.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar_kcal.setProgress(kcal_progressStatus);
                        }
                    });
                }
            }
        }).start();
        */
        // Sezione relativa alla progress bar dei carboidrati (non c'e il while perche non abbiamo un massimo di carboidrati)
        final ProgressBar progressBar_carboidrats = (ProgressBar) rootView.findViewById(R.id.fragment_home_carboidrats_progress_bar);
        final int carboidrats_progressStatus = 0;
        final android.os.Handler handler_carboidrats = new android.os.Handler();

        /*
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                // Qui ci va il codice che aggiorna il progress Status
                // ....

                // aggiorno la progress bar
                handler_carboidrats.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar_carboidrats.setProgress(carboidrats_progressStatus);
                    }
                });
            }
        }).start();
        */


        // getting text view to apply custom font
        TextView title_home_textView = (TextView) rootView.findViewById(R.id.fragment_home_title);
        Typeface CF_title_home = Typeface.createFromAsset(getActivity().getAssets(), "fonts/a song for jennifer.ttf");
        title_home_textView.setTypeface(CF_title_home);

        // since the nutrients text view have the same font, we define a "general" custom font for nutrients names
        Typeface CF_nutrients_home = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Girls_Have_Many Secrets.ttf");

        // apply nutrients custom font to all nutrients text view
        TextView proteins_textView = (TextView) rootView.findViewById(R.id.fragment_home_proteins);
        proteins_textView.setTypeface(CF_nutrients_home);

        TextView carboidrats_textView = (TextView) rootView.findViewById(R.id.fragment_home_carboidrats);
        carboidrats_textView.setTypeface(CF_nutrients_home);

        TextView lipids_textView = (TextView) rootView.findViewById(R.id.fragment_home_lipids);
        lipids_textView.setTypeface(CF_nutrients_home);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }


}
