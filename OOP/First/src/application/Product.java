package application;

import javafx.beans.property.*;

public class Product {

    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty quantity = new SimpleIntegerProperty();
    private final DoubleProperty priceR = new SimpleDoubleProperty();
    private final DoubleProperty priceW = new SimpleDoubleProperty();
    private final StringProperty periodW = new SimpleStringProperty();

    public Product(String name, int quantity, double priceR, double priceW, String periodW){
        this.name.set(name);
        this.quantity.set(quantity);
        this.priceR.set(priceR);
        this.priceW.set(priceW);
        this.periodW.set(periodW);
    }

    public String getName(){
        return name.get();
    }
    public void setName(String value){
        name.set(value);
    }
    public StringProperty nameProperty(){
        return name;
    }

    public int getQuantity(){
        return quantity.get();
    }
    public void setQuantity(int value){
        quantity.set(value);
    }
    public IntegerProperty quantityProperty(){
        return quantity;
    }

    public double getPriceR(){
        return priceR.get();
    }
    public void setPriceR(double value){
        priceR.set(value);
    }
    public DoubleProperty priceRProperty(){
        return priceR;
    }

    public double getPriceW(){
        return priceW.get();
    }
    public void setPriceW(double value){
        priceW.set(value);
    }
    public DoubleProperty priceWProperty(){
        return priceW;
    }

    public String getPeriodW(){
        return periodW.get();
    }
    public void setPeriodW(String value){
        periodW.set(value);
    }
    public StringProperty periodWProperty(){
        return periodW;
    }
}
