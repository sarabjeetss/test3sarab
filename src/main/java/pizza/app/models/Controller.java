package pizza.app.models;

import javafx.scene.control.Alert;

public abstract class Controller {

    private UIUpdator updator;

    public Controller(){
        //
    }

    public UIUpdator getUpdator() {
        return updator;
    }

    public void setUpdator(UIUpdator updator) {
        this.updator = updator;
    }

    public abstract void init();

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
