import java.util.ArrayList;
import java.util.List;

public class WeatherStats
{
    public List<CityWeatherStat> list = new ArrayList<CityWeatherStat>();
    public List<CityWeatherStat> listShowed = new ArrayList<CityWeatherStat>();

    public void add(CityWeatherStat item)
    {
        list.add(item);
        Main.guiController.tableAdd(item);
    }

    public String toString()
    {
        String result = "";

        for(CityWeatherStat item : list)
        {
            result += item.toString() + System.lineSeparator();
        }

        return result;
    }

    public void filterByCity(String cityName)
    {
        listShowed.clear();

        List<CityWeatherStat> result = new ArrayList<CityWeatherStat>();

        for(CityWeatherStat item : this.list)
        {
            if(item.cityName.equals(cityName))
                result.add(item);
        }

        listShowed = result;
    }
}
