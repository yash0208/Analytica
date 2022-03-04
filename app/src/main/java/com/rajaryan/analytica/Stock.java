package com.rajaryan.analytica;

public class Stock {
    String Name,Symbol;

    public Stock() {
    }

    public Stock(String name, String symbol) {
        Name = name;
        Symbol = symbol;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }
}
