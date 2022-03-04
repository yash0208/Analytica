package com.rajaryan.analytica;

public class Trade_Data {
    String Symbol,Amount,Buy_Value,Shares,Current_Price,BuyDate;

    public Trade_Data() {
    }

    public Trade_Data(String symbol, String amount, String buy_Value, String shares, String current_Price, String buyDate) {
        Symbol = symbol;
        Amount = amount;
        Buy_Value = buy_Value;
        Shares = shares;
        Current_Price = current_Price;
        BuyDate = buyDate;
    }

    public String getBuyDate() {
        return BuyDate;
    }

    public void setBuyDate(String buyDate) {
        BuyDate = buyDate;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getBuy_Value() {
        return Buy_Value;
    }

    public void setBuy_Value(String buy_Value) {
        Buy_Value = buy_Value;
    }

    public String getShares() {
        return Shares;
    }

    public void setShares(String shares) {
        Shares = shares;
    }

    public String getCurrent_Price() {
        return Current_Price;
    }

    public void setCurrent_Price(String current_Price) {
        Current_Price = current_Price;
    }
}
