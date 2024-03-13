package pizza.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pizza.app.models.Controller;
import pizza.app.models.UIUpdator;
import pizza.app.models.View;

import java.io.IOException;

public class PizzaApplication extends Application implements UIUpdator {

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("Pizza Ordering Application");
        setScene(View.ORDER);
        stage.show();
    }

    public static void main(String[] args) {

        launch(PizzaApplication.class);

    }

    @Override
    public void setScene(View view) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(view.toString()));
            Parent root = loader.load();
            Controller controller = loader.getController();
            controller.setUpdator(this);
            controller.init();
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
