package application;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.HPos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.*;

import java.util.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final ObservableList<Product> products = FXCollections.observableArrayList();


    private final File DATA_FILE = new File("products.txt");

    @Override
    public void start(Stage primaryStage)
            throws Exception
    {
        primaryStage.setTitle("Практична робота Пащенко Ангеліни КП-222");
        GridPane gridPane=new GridPane();
        gridPane.setPadding(new Insets(0,20,20,20));
        gridPane.setVgap(10);
        gridPane.setHgap(15);
        Scene scene=new Scene(gridPane,1024,600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        //Створення елементів
        //Label
        Label lblName=new Label("Назва товару");
        Label lblQuantity=new Label("Кількість товару");
        Label lblPriceR=new Label("Роздрібна ціна");
        Label lblPriceW=new Label("Оптова ціна");
        Label lblPeriodW=new Label("Гарантійний термін");
        Label lblElements=new Label("Кількість записів:\n");

        //TextField
        TextField textName=new TextField();
        textName.setPromptText("Введіть назву");
        TextField textQuantity=new TextField();
        textQuantity.setPromptText("Введіть кількість");
        TextField textPriceR=new TextField();
        textPriceR.setPromptText("Введіть роздрібну ціну");
        TextField textPriceW=new TextField();
        textPriceW.setPromptText("Введіть оптову ціну");
        TextField textPeriodW=new TextField();
        textPeriodW.setPromptText("Введіть гарантійний термін");
        //Button
        Button buttonAdd=new Button("Додавання в буфер");
        buttonAdd.setOnAction(e -> {
            try {
                String name = textName.getText();
                int quantity = Integer.parseInt(textQuantity.getText());
                double priceR = Double.parseDouble(textPriceR.getText());
                double priceW = Double.parseDouble(textPriceW.getText());
                String period = textPeriodW.getText();

                Product p = new Product(name, quantity, priceR, priceW, period);
                products.add(p);

                textName.clear();
                textQuantity.clear();
                textPriceR.clear();
                textPriceW.clear();
                textPeriodW.clear();

            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Перевірь числа в полях кількість/ціна");
                alert.showAndWait();
            }
        });
        Button buttonAccept=new Button("Запис у файл");
        buttonAccept.setOnAction(e -> {
            try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {

                for (Product p : products) {
                    writer.println(
                            p.getName() + "|" +
                                    p.getQuantity() + "|" +
                                    p.getPriceR() + "|" +
                                    p.getPriceW() + "|" +
                                    p.getPeriodW()
                    );
                }

                new Alert(Alert.AlertType.INFORMATION, "Дані записано у файл").showAndWait();

            } catch (IOException ex) {
                new Alert(Alert.AlertType.ERROR, "Помилка запису у файл").showAndWait();
            }
        });

        Button buttonSortByNameLength = new Button("Сорт за довж назви");
        buttonSortByNameLength.setOnAction(e -> {
            FXCollections.sort(products, (first, second) -> {
                int la = first.getName() == null ? 0 : first.getName().length();
                int lb = second.getName() == null ? 0 : second.getName().length();
                return Integer.compare(la, lb);
            });
        });


        //Налаштування елементів
        //Label
        lblName.setFont(new Font("Times New Roman",14));
        lblQuantity.setFont(new Font("Times New Roman",14));
        lblPriceR.setFont(new Font("Times New Roman",14));
        lblPriceW.setFont(new Font("Times New Roman",14));
        lblPeriodW.setFont(new Font("Times New Roman",14));
        //TextField
        textName.setPrefSize(200,20);
        textQuantity.setPrefSize(200,20);
        textPriceR.setPrefSize(200,20);
        textPriceW.setPrefSize(200,20);
        textPeriodW.setPrefSize(200,20);
        //Button
        buttonAdd.setFont(new Font("Times New Roman",14));
        buttonAdd.setPrefSize(200,20);
        buttonAccept.setFont(new Font("Times New Roman",14));
        buttonAccept.setPrefSize(200,20);
        buttonSortByNameLength.setFont(new Font("Times New Roman",14));
        buttonSortByNameLength.setPrefSize(200,20);
        //Додавання на сцену
        TitledPane tpane=new TitledPane();
        GridPane gridTPanel=new GridPane();
        gridTPanel.setPadding(new Insets(10,20,20,20));
        gridTPanel.setVgap(5);
        gridTPanel.setHgap(10);
        gridTPanel.setStyle("-fx-background-color: darkgray");
        //Name
        GridPane.setConstraints(lblName, 0,1);
        GridPane.setHalignment(lblName,HPos.CENTER);
        gridTPanel.getChildren().add(lblName);
        GridPane.setConstraints(textName,0,2);
        gridTPanel.getChildren().add(textName);
        //Quantity
        GridPane.setConstraints(lblQuantity,1,1);
        GridPane.setHalignment(lblQuantity, HPos.CENTER);
        gridTPanel.getChildren().add(lblQuantity);
        GridPane.setConstraints(textQuantity, 1, 2);
        gridTPanel.getChildren().add(textQuantity);
        //PriceR
        GridPane.setConstraints(lblPriceR,2,1);
        GridPane.setHalignment(lblPriceR,HPos.CENTER);
        gridTPanel.getChildren().add(lblPriceR);
        GridPane.setConstraints(textPriceR,2,2);
        gridTPanel.getChildren().add(textPriceR);
        //PriceW
        GridPane.setConstraints(lblPriceW,3,1);
        GridPane.setHalignment(lblPriceW,HPos.CENTER);
        gridTPanel.getChildren().add(lblPriceW);
        GridPane.setConstraints(textPriceW,3,2);
        gridTPanel.getChildren().add(textPriceW);
        //PeriodW
        GridPane.setConstraints(lblPeriodW,4,1);
        GridPane.setHalignment(lblPeriodW, HPos.CENTER);
        gridTPanel.getChildren().add(lblPeriodW);
        GridPane.setConstraints(textPeriodW, 4, 2);
        gridTPanel.getChildren().add(textPeriodW);
        //Button
        GridPane.setConstraints(buttonAdd,0,5);
        gridTPanel.getChildren().add(buttonAdd);
        GridPane.setConstraints(lblElements,1,5);
        GridPane.setColumnSpan(lblElements,3);
        GridPane.setHalignment(lblElements,HPos.CENTER);
        gridTPanel.getChildren().add(lblElements);

        GridPane.setConstraints(buttonAccept,4,5);
        gridTPanel.getChildren().add(buttonAccept);
        GridPane.setConstraints(buttonSortByNameLength,4,6);
        gridTPanel.getChildren().add(buttonSortByNameLength);
        //Додавання gridTPanel на tpane
        tpane.setCollapsible(false);
        tpane.setText("Дані для заповнення:");
        tpane.setContent(gridTPanel);
        //Добавление tpane на gridPane
        GridPane.setConstraints(tpane,0,1);
        GridPane.setColumnSpan(tpane,5);
        gridPane.getChildren().add(tpane);
        //Виведення інформації з файлу
        //Створення елементів
        //TextArea
        TextArea textArea=new TextArea();
        textArea.setPrefSize(775,400);
        textArea.setPromptText("Результат:\n");
        textArea.setFont(new Font("Times New Roman",14));
        //Button
        Button buttonRead=new Button("Читання з файлу");
        buttonRead.setFont(new Font("Times New Roman",14));
        buttonRead.setPrefSize(200,20);
        buttonRead.setOnAction(e -> {
            if (!DATA_FILE.exists()) {
                new Alert(Alert.AlertType.WARNING, "Файл не знайдено").showAndWait();
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {

                products.clear();
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    String[] parts = line.split("\\|");

                    String name = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    double priceR = Double.parseDouble(parts[2]);
                    double priceW = Double.parseDouble(parts[3]);
                    String periodW = parts[4];

                    products.add(new Product(name, quantity, priceR, priceW, periodW));
                }

                lblElements.setText("Кількість записів:\n" + products.size());

            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Помилка читання файлу").showAndWait();
            }
        });


        Button buttonClear=new Button("Очищення");
        buttonClear.setFont(new Font("Times New Roman",14));
        buttonClear.setPrefSize(200,20);
        buttonClear.setOnAction(e -> {
            products.clear();
            lblElements.setText("Кількість записів:\n0");
        });


        Button buttonSearch=new Button("Пошук");
        buttonSearch.setFont(new Font("Times New Roman",14));

        Button buttonChange=new Button("Заміна");
        buttonChange.setFont(new Font("Times New Roman",14));
        //TextField
        TextField textSearch=new TextField();
        textSearch.setPromptText("Введіть назву товару");

        TextField textChange=new TextField();
        textChange.setPromptText("Введіть текст для заміни");
        //ComboBox
        ComboBox comboBox=new ComboBox();
        comboBox.setPromptText("Виберіть категорію для заміни");
        comboBox.getItems().add("Назва товару");
        comboBox.getItems().add("Кількість");
        comboBox.getItems().add("Роздрібна ціна");
        comboBox.getItems().add("Оптова ціна");
        comboBox.getItems().add("Гарантійний термін");
        comboBox.setEditable(false);
        //Створення панелей
        //tpaneArray, gridArray
        TitledPane tpaneArray=new TitledPane();
        tpaneArray.setText("Виведення:");
        tpaneArray.setCollapsible(false);
        GridPane gridArray=new GridPane();
        gridArray.setPadding(new Insets(10));
        gridArray.setVgap(5);
        gridArray.setHgap(10);
        gridArray.setStyle("-fx-background-color: darkgrey");
        tpaneArray.setContent(gridArray);
        //tpaneSearch,gridSearch
        TitledPane tpaneSearch=new TitledPane();
        tpaneSearch.setPrefSize(100,200);
        tpaneSearch.setText("Пошук");
        GridPane gridSearch=new GridPane();
        gridSearch.setPadding(new Insets(10));
        gridSearch.setVgap(5);
        gridSearch.setHgap(10);
        gridSearch.setStyle("-fx-background-color: darkgrey");
        tpaneSearch.setContent(gridSearch);
        //Додавання на сцену
        // Таблиця
        TableView<Product> tableView = new TableView<>();
        tableView.setItems(products);
        tableView.setPrefWidth(1200);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Колонки
        TableColumn<Product, String> colName = new TableColumn<>("Назва");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Product, Number> colQuantity = new TableColumn<>("Кількість");
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        TableColumn<Product, Number> colPriceR = new TableColumn<>("Роздрібна ціна");
        colPriceR.setCellValueFactory(new PropertyValueFactory<>("priceR"));
        TableColumn<Product, Number> colPriceW = new TableColumn<>("Оптова ціна");
        colPriceW.setCellValueFactory(new PropertyValueFactory<>("priceW"));
        TableColumn<Product, String> colPeriodW = new TableColumn<>("Гарантія");
        colPeriodW.setCellValueFactory(new PropertyValueFactory<>("periodW"));
        tableView.getColumns().addAll(
                colName, colQuantity, colPriceR, colPriceW, colPeriodW
        );
        tableView.setPrefHeight(250);
        //Формування tpaneSearch
        GridPane.setConstraints(textSearch, 1, 0);
        gridSearch.getChildren().add(textSearch);
        GridPane.setConstraints(buttonSearch, 1, 1);
        GridPane.setHalignment(buttonSearch,HPos.CENTER);
        gridSearch.getChildren().add(buttonSearch);
        GridPane.setConstraints(comboBox,1,2);
        gridSearch.getChildren().add(comboBox);
        GridPane.setConstraints(textChange,1,3);
        gridSearch.getChildren().add(textChange);
        GridPane.setConstraints(buttonChange, 1, 4);
        GridPane.setHalignment(buttonChange,HPos.CENTER);
        gridSearch.getChildren().add(buttonChange);
        //Формування tpaneArray
        GridPane.setConstraints(tableView, 0, 0);
        GridPane.setColumnSpan(tableView, 4);
        GridPane.setRowSpan(tableView, 5);
        gridArray.getChildren().add(tableView);

        GridPane.setConstraints(buttonRead,4,0);
        gridArray.getChildren().add(buttonRead);
        GridPane.setConstraints(buttonClear,4,2);
        gridArray.getChildren().add(buttonClear);
        GridPane.setConstraints(tpaneSearch,4,4);
        gridArray.getChildren().add(tpaneSearch);
        //Формування gridPane
        GridPane.setConstraints(tpaneArray,0,7);
        GridPane.setColumnSpan(tpaneArray,5);
        gridPane.getChildren().add(tpaneArray);
        primaryStage.show();
    }
}
