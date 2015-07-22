package androidhive.info.materialdesign.activity;

import android.content.Intent;

import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import java.util.List;

import androidhive.info.materialdesign.classes.DataPreferences;
import androidhive.info.materialdesign.classes.Food;
import androidhive.info.materialdesign.classes.FoodsData;
import androidhive.info.materialdesign.classes.Nutrient;
import androidhive.info.materialdesign.R;

public class FoodDetailsActivityHome extends ActionBarActivity
{
    static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_food_details_home);

        // The detail Activity called via intent.  Inspect the intent for forecast data.
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT))
        {
            position = Integer.parseInt(intent.getStringExtra(Intent.EXTRA_TEXT));

            // point to all the data
            List<Food> temp = FoodsData.foodsData;

            // extract food object in position
            Food temp_food = temp.get(position);

            // extract the list of nutrients
            List<Nutrient> temp_nut_list = temp_food.getNutList();

            // food's name
            String food_name_home = temp_food.getDescription();

            // putting food's name into text view
            TextView foodNameHome = (TextView) findViewById(R.id.activity_food_details_home_textview_foodsName);
            Typeface future_font = Typeface.createFromAsset(getAssets(),"fonts/a song for jennifer.ttf");
            foodNameHome.setTypeface(future_font);

            foodNameHome.setText(food_name_home);

            String food_nutrients_home = new String();

            for (int i = 0; i < temp_nut_list.size(); i++)
            {
                if (!temp_nut_list.get(i).getName().equals("Carbohydrate, by difference"))
                {
                    // build the texts
                    food_nutrients_home += ""//"         "
                            + temp_nut_list.get(i).getName()
                            + ":  " + temp_nut_list.get(i).getValue()
                            + "\n\n";
                }
                else
                {
                    // build the texts
                    food_nutrients_home += ""//"         "
                            + "Carbohydrate"
                            + ":    " + temp_nut_list.get(i).getValue()
                            + "\n\n";
                }

            }

            ((TextView)findViewById(R.id.activity_food_details_home_textview_foodsNutrients)).setText(food_nutrients_home);

            TextView nutrients_home = (TextView) findViewById(R.id.activity_food_details_home_textview_foodsNutrients);
            Typeface custom_font_home_nutrients = Typeface.createFromAsset(getAssets(),"fonts/Girls_Have_Many Secrets.ttf");
            nutrients_home.setTypeface(custom_font_home_nutrients);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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

    public void addFood(View view)
    {
        Log.v("Button", " ++++++++++++++++++++++++++++++++++++++++++ ");

        // point to all the data
        List<Food> temp = FoodsData.foodsData;

        // add the position of the food
        String new_position = Integer.toString(position)+ ",";

        DataPreferences.writePreference(getApplicationContext(), DataPreferences.PREFS_USER_FOODS, DataPreferences.PUF_KEY, new_position);

        /*
        // add food to listview
        HomeFragment.mUserFoodAdapter.add(temp_food.getDescription());
        HomeFragment.mUserFoodAdapter.notifyDataSetChanged();
        */

        // starting HomeFragment
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }






}
