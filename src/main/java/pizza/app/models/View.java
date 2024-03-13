package pizza.app.models;

public enum View {

    ORDER("order.fxml");

    private String path;

    View(String path){
        this.path = path;
    }

    @Override
    public String toString(){
        return path;
    }

}
