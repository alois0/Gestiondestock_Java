package app;

import javax.swing.plaf.metal.MetalBorders.TextFieldBorder;
import javax.swing.text.TableView;

import controleur.ProduitController;
import vue.ProduitView;

public class App extends Application {

    private final ProductService productService = new ProductService();
    private final TableView<Product> productTable = new TableView<>();
    private final TextField nameField = new TextField();
    private final TextField priceField = new TextField();
    
    public void start(Stage stage) {
        stage.setTitle("CRUD APP");

        TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleIntegerProperty(
                        cellData.getValue().getId()).asObject());

        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getName()));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleDoubleProperty(
                        cellData.getValue().getPrice()).asObject());
    
        productTable.getColumns().addAll(idColumn, nameColumn, priceColumn);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        refreshTable();

        nameField.setPromptText("Name");
        priceField.setPromptText("Price");

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addProduct());

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateProduct());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteProduct());

        Hbox inputBox = new Hbox(10, nameField, priceField, addButton, updateButton, deleteButton);
        inputBox.setSpacing(10);

        Vbox layout = new Vbox(10, productTable, inputBox);
        layout.setSpacing(20);
        layout.setPadding(new javafx.geometry.Insets(10));

        Scene.setScene(scene);
        stage.show();
    }

    private void addProduct() {
        String name = nameField.getText();
        double price;
        try {
            price = Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid price format");
            return;
        }
        if (!name.isEmpty()) {
            productService.addProduct(name, price);
            refreshTable();
            nameField.clear();
            priceField.clear();
        } else {
            showAlert("Name");
        }
    }
}
