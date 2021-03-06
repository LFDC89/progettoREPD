package androidhive.info.materialdesign.classes;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Marco on 13/07/2015.
 */
public class DataPreferences
{
    private static SharedPreferences settings = null;
    public static final String PREFS_USER_FOODS = "user_food_list";
    public static final String PUF_KEY = "ufl_key";


    private DataPreferences() {}

    public static void writePreference(Context context, String preference_name, String key, String value)
    {
        settings = context.getSharedPreferences(preference_name, Context.MODE_PRIVATE);

        // Writing data to SharedPreferences
        SharedPreferences.Editor editor = settings.edit();
        // Reading from SharedPreferences
        String user_foods_string = settings.getString(key, "no food added");

        if (user_foods_string.equals("no food added"))
        {
            editor.putString(key, value + ",");
            editor.commit();
        }
        else
        {
            // add only new food
            String[] temp = user_foods_string.split(",");
            int check = 0;

            for (int i=0; i<temp.length; i++)
            {
                if(temp[i].equals(value))
                {
                    check = 1;
                    break;
                }
            }

            if(check == 0)
            {
                editor.putString(key, user_foods_string + value + ",");
                editor.commit();
            }
        }
    }

    public static String readPreference(Context context, String preference_name, String key)
    {
        settings = context.getSharedPreferences(preference_name, Context.MODE_PRIVATE);

        // Reading from SharedPreferences
        String user_foods_string = settings.getString(key, "no food added");


        return  user_foods_string;

    }
}
