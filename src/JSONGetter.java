import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class JSONGetter extends Thread
{
    private List<String> cities = new ArrayList<String>();

    public boolean checkWeather(String cityName)
    {
        if(cityName == null || cityName.equals("") || cityName.isEmpty() || cityName.trim().isEmpty())
        {
            return false;
        }

        char[] s = cityName.toCharArray();
        for(int i = 0; i < s.length; i++)
        {
            if(!Character.isAlphabetic(s[i]))
            {
                if(!Character.isSpaceChar(s[i]))
                {
                    return false;
                }
            }
        }

        String str = String.valueOf(s);
        str.replaceAll("\\s+","");
        cities.add(str);

        return true;
    }

    public void stopSearch()
    {
        cities.add(";");
    }

    public String connectAndGetData(String url)
    {
        String json = "";
        InputStream input;
        try
        {
            input = new URL(url).openStream();
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
                try
                {
                    //reading data
                    StringBuilder sb = new StringBuilder();
                    int cp;
                    while ((cp = reader.read()) != -1)
                    {
                        sb.append((char) cp);
                    }
                    json = sb.toString();
                }
                catch (IOException e)
                {
                    //e.printStackTrace();
                }
            }
            finally
            {
                try
                {
                    input.close();
                }
                catch (IOException e)
                {
                    //e.printStackTrace();
                }
            }
        }
        catch(IOException e)
        {
            //e.printStackTrace();
        }

        return json;
    }

    private void processJSONData(String jsonString)
    {
        Object obj = null;
        try
        {
            obj = new JSONParser().parse(jsonString);
        }
        catch (ParseException e)
        {
            //e.printStackTrace();
        }

        JSONObject data = (JSONObject) obj;
        try
        {
            JSONArray arr = (JSONArray) (data.get("data"));
            for (Object current : arr)
            {
                String cityName = (String) ((JSONObject) current).get("city_name");
                //System.out.println(cityName);
                String weather = (String) ((JSONObject) (((JSONObject) current).get("weather"))).get("description");
                //System.out.println(weather);
                float temp = 0;
                try
                {
                    temp = (Long) ((JSONObject) current).get("temp");
                }
                catch (ClassCastException e)
                {
                    try
                    {
                        double d = (Double) ((JSONObject) current).get("temp");
                        temp = (float) d;
                    }
                    catch (ClassCastException e1)
                    {

                    }
                }

                Main.stats.add(new CityWeatherStat(cityName, temp, weather));
            }
        }
        catch (Exception e)
        {
            Main.guiController.printToLog("An error has occurred while processing the data.");
        }
    }

    @Override
    public void run()
    {
        //"https://api.weatherbit.io/v2.0/current?city=*CITY*&key=0116465a84c941d9a4eb16463b489e1a"
        super.run();

        while(true)
        {
            if(cities.isEmpty())
            {
                try { sleep(200); }
                catch(InterruptedException e) {}
                continue;
            }
            if(cities.get(0) == ";")
            {
                //System.out.println("Stopped");
                cities.remove(0);
                break;
            }

            String url = "https://api.weatherbit.io/v2.0/current?city=" + cities.get(0) + "&key=0116465a84c941d9a4eb16463b489e1a";
            String json = connectAndGetData(url);
            processJSONData(json);
            cities.remove(0);
        }
    }
}
