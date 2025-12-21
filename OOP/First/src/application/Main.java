package application;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.HPos;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.ArrayList;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private File currentFile = new File("products.txt");
    private final File DATA_FILE = new File("products.txt");
    private final Set<Product> found = new HashSet<>();


    @Override
    public void start(Stage primaryStage)
            throws Exception
    {
        primaryStage.setTitle("Практична робота Пащенко Ангеліни КП-222");
        GridPane gridPane=new GridPane();
        gridPane.setPadding(new Insets(0,20,20,20));
        gridPane.setVgap(10);
        gridPane.setHgap(15);
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("Файл");
        Menu menuFormat = new Menu("Формат");
        menuBar.getMenus().addAll(menuFile, menuFormat);
        GridPane.setConstraints(menuBar, 0, 0);
        GridPane.setColumnSpan(menuBar, 5);
        gridPane.getChildren().add(menuBar);

        Scene scene=new Scene(gridPane,1024,650);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        //Створення елементів
        MenuItem miOpen = new MenuItem("Open");
        MenuItem miSave = new MenuItem("Save");
        MenuItem miSaveAs = new MenuItem("Save As");
        MenuItem miExit = new MenuItem("Exit");
        menuFile.getItems().addAll(miOpen, miSave, miSaveAs, new SeparatorMenuItem(), miExit);
        Menu menuColor = new Menu("Колір");
        ToggleGroup colorGroup = new ToggleGroup();
        RadioMenuItem miBlack = new RadioMenuItem("Чорний");
        RadioMenuItem miRed = new RadioMenuItem("Червоний");
        RadioMenuItem miGreen = new RadioMenuItem("Зелений");
        RadioMenuItem miBlue = new RadioMenuItem("Синій");
        miBlack.setToggleGroup(colorGroup);
        miRed.setToggleGroup(colorGroup);
        miGreen.setToggleGroup(colorGroup);
        miBlue.setToggleGroup(colorGroup);
        miBlack.setSelected(true);
        menuColor.getItems().addAll(miBlack, miRed, miGreen, miBlue);
        menuFormat.getItems().add(menuColor);


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
            FileChooser fc = new FileChooser();
            fc.setTitle("Зберегти файл");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
            File chosen = fc.showSaveDialog(primaryStage);
            if (chosen == null) return;

            currentFile = chosen;
            saveToFile(chosen);

            new Alert(Alert.AlertType.INFORMATION, "Збережено").showAndWait();
        });


        Button buttonSortByNameLength = new Button("Сорт за довж назви");
        buttonSortByNameLength.setOnAction(e -> {
            FXCollections.sort(products, (first, second) -> {
                int la = first.getName() == null ? 0 : first.getName().length();
                int lb = second.getName() == null ? 0 : second.getName().length();
                return Integer.compare(la, lb);
            });
        });

        miOpen.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Відкрити файл");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
            File chosen = fc.showOpenDialog(primaryStage);
            if (chosen == null) return;

            currentFile = chosen;
            loadFromFile(currentFile);   // метод нижче
            lblElements.setText("Кількість записів:\n" + products.size());
        });

        miSave.setOnAction(e -> {
            saveToFile(currentFile);
        });

        miSaveAs.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Зберегти як");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
            File chosen = fc.showSaveDialog(primaryStage);
            if (chosen == null) return;

            currentFile = chosen;
            saveToFile(currentFile);
        });

        miExit.setOnAction(e -> Platform.exit());

        Button tbSave = new Button("💾");
        tbSave.setTooltip(new Tooltip("Зберегти файл"));
        tbSave.setOnAction(e -> buttonAccept.fire());

        Button tbComparator = new Button("⇅");
        tbComparator.setTooltip(new Tooltip("Власний компаратор"));
        tbComparator.setOnAction(e -> {
            FXCollections.sort(products, (a, b) -> Integer.compare(
                    a.getName().length(),
                    b.getName().length()
            ));
        });

        Button tbClear = new Button("🧹");
        tbClear.setTooltip(new Tooltip("Очищення текстових полів введення"));
        tbClear.setOnAction(e -> {
            textName.clear();
            textQuantity.clear();
            textPriceR.clear();
            textPriceW.clear();
            textPeriodW.clear();
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
        GridPane.setConstraints(tpane,0,2);
        GridPane.setColumnSpan(tpane,5);
        gridPane.getChildren().add(tpane);

        ToolBar toolBar = new ToolBar(tbSave, tbComparator, tbClear);

        GridPane.setConstraints(toolBar, 0, 1);
        GridPane.setColumnSpan(toolBar, 5);
        gridPane.getChildren().add(toolBar);

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

                FileChooser fc = new FileChooser();
                fc.setTitle("Відкрити файл");
                fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
                File chosen = fc.showOpenDialog(primaryStage);
                if (chosen == null) return;

                currentFile = chosen;
                loadFromFile(chosen);

                lblElements.setText("Кількість записів:\n" + products.size());
        });


        Button buttonClear=new Button("Очищення");
        buttonClear.setFont(new Font("Times New Roman",14));
        buttonClear.setPrefSize(200,20);
        buttonClear.setOnAction(e -> {
            products.clear();
            lblElements.setText("Кількість записів:\n0");
        });


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

        Menu menuFont = new Menu("Шрифт");
        menuFormat.getItems().add(menuFont);

        MenuItem fTimes = new MenuItem("Times New Roman");
        MenuItem fCourier = new MenuItem("Courier");
        MenuItem fConsolas = new MenuItem("Consolas");
        MenuItem fBold = new MenuItem("Bold");
        MenuItem fItalic = new MenuItem("Italic");

        menuFont.getItems().addAll(fTimes, fCourier, fConsolas, new SeparatorMenuItem(), fBold, fItalic);

        //Додавання на сцену
        // Таблиця
        TableView<Product> tableView = new TableView<>();
        tableView.setItems(products);
        tableView.setPrefWidth(1200);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ContextMenu ctx = new ContextMenu();
        MenuItem miDelete = new MenuItem("Видалити вибраний рядок");
        MenuItem miClearAll = new MenuItem("Очистити таблицю");
        MenuItem miSaveBar = new MenuItem("Зберегти");
        ctx.getItems().addAll(miDelete, miClearAll, new SeparatorMenuItem(), miSaveBar);
        miDelete.setOnAction(e -> {
            Product selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) products.remove(selected);
        });
        miClearAll.setOnAction(e -> products.clear());
        miSaveBar.setOnAction(e -> buttonAccept.fire());
        tableView.setContextMenu(ctx);

        tableView.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if (found.contains(item)) {
                    setStyle("-fx-background-color: lightgreen;");
                } else {
                    setStyle("");
                }
            }
        });


        // Колонки
        TableColumn<Product, String> colName = new TableColumn<>("Назва");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colName.setOnEditCommit(e -> {
            Product p = e.getRowValue();
            p.setName(e.getNewValue());
        });
        TableColumn<Product, Integer> colQuantity = new TableColumn<>("Кількість");
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.IntegerStringConverter()));
        colQuantity.setOnEditCommit(e -> {
            Product p = e.getRowValue();
            p.setQuantity(e.getNewValue().intValue());
        });
        TableColumn<Product, Double> colPriceR = new TableColumn<>("Роздрібна ціна");
        colPriceR.setCellValueFactory(new PropertyValueFactory<>("priceR"));
        colPriceR.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.DoubleStringConverter()));
        colPriceR.setOnEditCommit(e -> {
            Product p = e.getRowValue();
            p.setPriceR(e.getNewValue().doubleValue());
        });
        TableColumn<Product, Double> colPriceW = new TableColumn<>("Оптова ціна");
        colPriceW.setCellValueFactory(new PropertyValueFactory<>("priceW"));
        colPriceW.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.DoubleStringConverter()));
        colPriceW.setOnEditCommit(e -> {
            Product p = e.getRowValue();
            p.setPriceW(e.getNewValue().doubleValue());
        });
        TableColumn<Product, String> colPeriodW = new TableColumn<>("Гарантія");
        colPeriodW.setCellValueFactory(new PropertyValueFactory<>("periodW"));
        colPeriodW.setCellFactory(TextFieldTableCell.forTableColumn());
        colPeriodW.setOnEditCommit(e -> {
            Product p = e.getRowValue();
            p.setPeriodW(e.getNewValue());
        });
        tableView.getColumns().addAll(
                colName, colQuantity, colPriceR, colPriceW, colPeriodW
        );
        tableView.setPrefHeight(250);
        tableView.setEditable(true);

        final int baseSize = 14;

        Button buttonSearch=new Button("Пошук");
        buttonSearch.setFont(new Font("Times New Roman",14));
        buttonSearch.setOnAction(e -> {
            String q = textSearch.getText().trim();
            if (q.isEmpty()) return;

            String field = String.valueOf(comboBox.getValue());

            found.clear();

            for (Product p : products) {
                if (matches(p, field, q)) {
                    found.add(p);
                }
            }

            tableView.refresh();

            if (!found.isEmpty()) {
                tableView.getSelectionModel().select(found.iterator().next());
                tableView.scrollTo(found.iterator().next());
            }

            new Alert(Alert.AlertType.INFORMATION, "Знайдено: " + found.size()).showAndWait();
        });
        Button buttonChange=new Button("Заміна");
        buttonChange.setFont(new Font("Times New Roman",14));
        buttonChange.setOnAction(e -> {
            if (found.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Спочатку виконай пошук").showAndWait();
                return;
            }

            String newText = textChange.getText().trim();
            if (newText.isEmpty()) return;
            String field = String.valueOf(comboBox.getValue());
            if (field == null) field = "Назва товару";
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Вікно CONFIRMATION");
            confirm.setContentText("Ви впевнені, що хочете зберегти зміни?");

            Optional<ButtonType> res = confirm.showAndWait();
            if (res.isEmpty() || res.get() != ButtonType.OK) return;

            for (Product p : found) {
                applyReplace(p, field, newText);
            }

            found.clear();
            tableView.refresh();
        });

        miBlack.setOnAction(e -> setTableColorClass(tableView, "table-text-black"));
        miRed.setOnAction(e -> setTableColorClass(tableView, "table-text-red"));
        miGreen.setOnAction(e -> setTableColorClass(tableView, "table-text-green"));
        miBlue.setOnAction(e -> setTableColorClass(tableView, "table-text-blue"));
        setTableColorClass(tableView, "table-text-black");

        fTimes.setOnAction(e -> setTableFontClass(tableView, "font-times"));
        fCourier.setOnAction(e -> setTableFontClass(tableView, "font-courier"));
        fConsolas.setOnAction(e -> setTableFontClass(tableView, "font-consolas"));
        fBold.setOnAction(e -> setTableFontClass(tableView, "font-bold"));
        fItalic.setOnAction(e -> setTableFontClass(tableView, "font-italic"));


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

    private void saveToFile(File file) {
        try {
            List<String> lines = new ArrayList<>();
            for (Product p : products) {
                lines.add(p.getName() + "|" + p.getQuantity() + "|" + p.getPriceR() + "|" +
                        p.getPriceW() + "|" + p.getPeriodW());
            }
            Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Помилка збереження: " + ex.getMessage()).showAndWait();
        }
    }

    private void loadFromFile(File file) {
        try {
            if (!file.exists()) {
                new Alert(Alert.AlertType.WARNING, "Файл не знайдено").showAndWait();
                return;
            }
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            products.clear();

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 5) continue;

                String name = parts[0];
                int quantity = Integer.parseInt(parts[1]);
                double priceR = Double.parseDouble(parts[2]);
                double priceW = Double.parseDouble(parts[3]);
                String period = parts[4];

                products.add(new Product(name, quantity, priceR, priceW, period));
            }
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Помилка читання: " + ex.getMessage()).showAndWait();
        }
    }

    private void setTableColorClass(TableView<?> tableView, String cssClass) {
        tableView.getStyleClass().removeAll(
                "table-text-black", "table-text-red", "table-text-green", "table-text-blue"
        );
        tableView.getStyleClass().add(cssClass);
    }

    private void setTableFontClass(TableView<?> tableView, String cssClass) {
        tableView.getStyleClass().removeAll(
                "font-times", "font-courier", "font-consolas", "font-bold", "font-italic"
        );
        tableView.getStyleClass().add(cssClass);
    }

    private boolean matches(Product p, String field, String q) {
        q = q.toLowerCase();

        if (field == null || field.equals("Назва товару")) {
            return p.getName().toLowerCase().contains(q);
        }
        if (field.equals("Кількість")) {
            return String.valueOf(p.getQuantity()).contains(q);
        }
        if (field.equals("Роздрібна ціна")) {
            return String.valueOf(p.getPriceR()).contains(q);
        }
        if (field.equals("Оптова ціна")) {
            return String.valueOf(p.getPriceW()).contains(q);
        }
        if (field.equals("Гарантійний термін")) {
            return p.getPeriodW().toLowerCase().contains(q);
        }
        return false;
    }

    private void applyReplace(Product p, String field, String newText) {
        try {
            if (field.equals("Назва товару")) {
                p.setName(newText);
            } else if (field.equals("Кількість")) {
                p.setQuantity(Integer.parseInt(newText));
            } else if (field.equals("Роздрібна ціна")) {
                p.setPriceR(Double.parseDouble(newText));
            } else if (field.equals("Оптова ціна")) {
                p.setPriceW(Double.parseDouble(newText));
            } else if (field.equals("Гарантійний термін")) {
                p.setPeriodW(newText);
            }
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "Некоректне число для поля: " + field).showAndWait();
        }
    }


}
