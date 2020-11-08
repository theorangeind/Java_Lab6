import java.util.Comparator;

public class CityWeatherStat
{
    public final String cityName;
    public final float temperature;
    public final String weather;

    public CityWeatherStat(String cityName, float temperature, String weather)
    {
        this.cityName = cityName;
        this.temperature = temperature;
        this.weather = weather;
    }

    public String getCity()
    {
        return cityName;
    }

    public Float getTemperature()
    {
        return temperature;
    }

    public String getWeather()
    {
        return weather;
    }

    public String toString()
    {
        String result = "City: " + cityName;
        result += "\ntemperature: " + temperature;
        result += "\nWeather: " + weather;
        return result;
    }

    public static Comparator<CityWeatherStat> byCityName = Comparator.comparing(a -> a.cityName);
    public static Comparator<CityWeatherStat> byTemp = (a, b) -> a.temperature > b.temperature ? 1 : a.temperature < b.temperature ? -1 : 0;
}
