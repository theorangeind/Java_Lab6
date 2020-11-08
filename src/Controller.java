import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class Controller
{
    private ObservableList<CityWeatherStat> tableData = FXCollections.observableArrayList();

    @FXML
    TableView<CityWeatherStat> tblMain;
    @FXML
    TableColumn<CityWeatherStat, String> tblColCity;
    @FXML
    TableColumn<CityWeatherStat, Float> tblColTemperature;
    @FXML
    TableColumn<CityWeatherStat, String> tblColWeather;

    @FXML
    TextField txtCity;
    @FXML
    TextArea txtLog;
    @FXML
    Button btnClear;
    @FXML
    Button btnGetWeather;
    @FXML
    Button btnShowCurrent;
    @FXML
    Button btnShowAll;

    @FXML
    private void initialize()
    {
        tblColCity.setCellValueFactory(new PropertyValueFactory<>("City"));
        tblColTemperature.setCellValueFactory(new PropertyValueFactory<>("Temperature"));
        tblColWeather.setCellValueFactory(new PropertyValueFactory<>("Weather"));
    }

    public void clearCity()
    {
        txtCity.setText("");
        printToLog("City field cleared.");
    }

    public void getData()
    {
        if(!Main.jsonGetter.isAlive())
        {
            Main.jsonGetter.start();
        }

        if (!Main.jsonGetter.checkWeather(txtCity.getText()))
        {
            printToLog("Incorrect city name. Please, try again.");
        }
        else
        {
            printToLog("City weather data requested: " + txtCity.getText());
        }
    }

    public void showCurrent()
    {
        Main.stats.filterByCity(txtCity.getText());
        tableUpdate(Main.stats.listShowed);
        if(!Main.stats.listShowed.isEmpty()) printToLog("Weather in " + txtCity.getText() + " showed.");
        else printToLog("No such city found.");
    }

    public void showAll()
    {
        tableUpdate(Main.stats.list);
        printToLog("All imported data showed.");
    }

    public void tableUpdate()
    {
        tblMain.setItems(tableData);
    }

    public void tableUpdate(List<CityWeatherStat> statList)
    {
        tableData.clear();

        for(CityWeatherStat item : statList)
        {
            tableData.add(item);
        }

        tableUpdate();
    }

    public void tableAdd(CityWeatherStat cityStat)
    {
        tableData.add(cityStat);
        tblMain.getItems().add(cityStat);
        printToLog("Weather data has been added to table.");
        //tableUpdate();
        showAll();
    }

    public void printToLog(String content)
    {
        txtLog.appendText(content + "\n");
    }
}
