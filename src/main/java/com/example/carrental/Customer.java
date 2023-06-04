package com.example.carrental;

import javafx.beans.property.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;

public class Customer implements Serializable {
    private transient StringProperty name;
    private transient StringProperty address;
    private transient StringProperty carType;
    private transient DoubleProperty amountPaid;
    private transient ObjectProperty<LocalDate> rentDate;
    private transient ObjectProperty<LocalDate> returnDate;

    public Customer(String name, String address, String carType, double amountPaid, LocalDate rentDate, LocalDate returnDate) {
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.carType = new SimpleStringProperty(carType);
        this.amountPaid = new SimpleDoubleProperty(amountPaid);
        this.rentDate = new SimpleObjectProperty<>(rentDate);
        this.returnDate = new SimpleObjectProperty<>(returnDate);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(name.get());
        out.writeObject(address.get());
        out.writeObject(carType.get());
        out.writeDouble(amountPaid.get());
        out.writeObject(rentDate.get());
        out.writeObject(returnDate.get());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        name = new SimpleStringProperty((String) in.readObject());
        address = new SimpleStringProperty((String) in.readObject());
        carType = new SimpleStringProperty((String) in.readObject());
        amountPaid = new SimpleDoubleProperty(in.readDouble());
        rentDate = new SimpleObjectProperty<>((LocalDate) in.readObject());
        returnDate = new SimpleObjectProperty<>((LocalDate) in.readObject());
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getCarType() {
        return carType.get();
    }

    public void setCarType(String carType) {
        this.carType.set(carType);
    }

    public StringProperty carTypeProperty() {
        return carType;
    }

    public double getAmountPaid() {
        return amountPaid.get();
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid.set(amountPaid);
    }

    public DoubleProperty amountPaidProperty() {
        return amountPaid;
    }

    public LocalDate getRentDate() {
        return rentDate.get();
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate.set(rentDate);
    }

    public ObjectProperty<LocalDate> rentDateProperty() {
        return rentDate;
    }

    public LocalDate getReturnDate() {
        return returnDate.get();
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate.set(returnDate);
    }

    public ObjectProperty<LocalDate> returnDateProperty() {
        return returnDate;
    }
}
