import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    public static WeatherStats stats = new WeatherStats();
    public static JSONGetter jsonGetter = new JSONGetter();
    public static Controller guiController;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("World Weather");
        primaryStage.setScene(new Scene(root, 800, 712));

        guiController = loader.getController();

        primaryStage.show();
    }

    @Override
    public void stop() throws Exception
    {
        jsonGetter.stopSearch();
        super.stop();
    }
}
