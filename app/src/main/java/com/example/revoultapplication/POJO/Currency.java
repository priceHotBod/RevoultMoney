package com.example.revoultapplication.POJO;

import com.example.revoultapplication.R;

// POJO to contain all the attributes of a currency
public class Currency {

    private String currencyAbbreviatedName;
    private String currencyFullName;
    private double exchangeRate;  // The exchange rate as compared to the currency currently being examined
    private int flagImageReference = -1;

    public Currency(String currencyAbbreviatedName, double exchangeRate){
        this.currencyAbbreviatedName = currencyAbbreviatedName;
        this.exchangeRate = exchangeRate;
    }

    public String getCurrencyAbbreviatedName() { return currencyAbbreviatedName; }
    public void setCurrencyAbbreviatedName(String currencyAbbreviatedName) { this.currencyAbbreviatedName = currencyAbbreviatedName; }
    public void setCurrencyFullName(String currencyFullName) { this.currencyFullName = currencyFullName; }
    public double getExchangeRate() {
        return exchangeRate;
    }
    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getCurrencyFullName(){
        if (this.currencyFullName == null) {

            switch (this.currencyAbbreviatedName) {
                case "AUD":
                    this.currencyFullName = "Australian Dollar";
                    break;
                case "BGN":
                    this.currencyFullName = "Bulgarian Lev";
                    break;
                case "BRL":
                    this.currencyFullName = "Brazilian Real";
                    break;
                case "CAD":
                    this.currencyFullName = "Canadian Dollar";
                    break;
                case "CHF":
                    this.currencyFullName = "Swiss Franc";
                    break;
                case "CNY":
                    this.currencyFullName = "Chinese Yuan";
                    break;
                case "CZK":
                    this.currencyFullName = "Czech Koruna";
                    break;
                case "DKK":
                    this.currencyFullName = "Danish Krone";
                    break;
                case "EUR":
                    this.currencyFullName = "Euro";
                    break;
                case "GBP":
                    this.currencyFullName = "Great British Pound";
                    break;
                case "HKD":
                    this.currencyFullName = "Hong Kong Dollar";
                    break;
                case "HRK":
                    this.currencyFullName = "Croatian Kuna";
                    break;
                case "HUF":
                    this.currencyFullName = "Hungarian Forint";
                    break;
                case "IDR":
                    this.currencyFullName = "Indonesian Rupiah";
                    break;
                case "ILS":
                    this.currencyFullName = "Israeli New Shekel";
                    break;
                case "INR":
                    this.currencyFullName = "Indian Rupee";
                    break;
                case "ISK":
                    this.currencyFullName = "Icelandic Krona";
                    break;
                case "JPY":
                    this.currencyFullName = "Japanese Yen";
                    break;
                case "KRW":
                    this.currencyFullName = "South Korean Won";
                    break;
                case "MXN":
                    this.currencyFullName = "Mexican Peso";
                    break;
                case "MYR":
                    this.currencyFullName = "Malaysian Ringgit";
                    break;
                case "NOK":
                    this.currencyFullName = "Norwegian Krone";
                    break;
                case "NZD":
                    this.currencyFullName = "New Zealand Dollar";
                    break;
                case "PHP":
                    this.currencyFullName = "Philippine Peso";
                    break;
                case "PLN":
                    this.currencyFullName = "Polish Zloty";
                    break;
                case "RON":
                    this.currencyFullName = "Romanian Leu";
                    break;
                case "RUB":
                    this.currencyFullName = "Russian Ruble";
                    break;
                case "SEK":
                    this.currencyFullName = "Swedish Krona";
                    break;
                case "SGD":
                    this.currencyFullName = "Singapore Dollar";
                    break;
                case "THB":
                    this.currencyFullName = "Thai Baht";
                    break;
                case "TRY":
                    this.currencyFullName = "Turkish Lira";
                    break;
                case "USD":
                    this.currencyFullName = "United States Dollar";
                    break;
                case "ZAR":
                    this.currencyFullName = "South African Rand";
                    break;
                default:
                    this.currencyFullName = "";
            }
        }
        return this.currencyFullName;

    }

    // Get the image reference for this currency's matching flag. If it has already been set, simply return the preset value
    // Otherwise, lookup which image to use based on the abbreviated name received from the API
    public int getFlagImageReference(){
        if (this.flagImageReference == -1) {

            switch (this.currencyAbbreviatedName) {
                case "AUD": this.flagImageReference = R.drawable.australia;
                    break;
                case "BGN": this.flagImageReference = R.drawable.bulgaria;
                    break;
                case "BRL": this.flagImageReference = R.drawable.brazil;
                    break;
                case "CAD": this.flagImageReference = R.drawable.canada;
                    break;
                case "CHF": this.flagImageReference = R.drawable.switzerland;
                    break;
                case "CNY": this.flagImageReference = R.drawable.china;
                    break;
                case "CZK": this.flagImageReference = R.drawable.czech_republic;
                    break;
                case "DKK": this.flagImageReference = R.drawable.denmark;
                    break;
                case "EUR": this.flagImageReference = R.drawable.european_union;
                    break;
                case "GBP": this.flagImageReference = R.drawable.united_kingdom;
                    break;
                case "HKD": this.flagImageReference = R.drawable.hong_kong;
                    break;
                case "HRK": this.flagImageReference = R.drawable.croatia;
                    break;
                case "HUF": this.flagImageReference = R.drawable.hungary;
                    break;
                case "IDR": this.flagImageReference = R.drawable.indonesia;
                    break;
                case "ILS": this.flagImageReference = R.drawable.israel;
                    break;
                case "INR": this.flagImageReference = R.drawable.india;
                    break;
                case "ISK": this.flagImageReference = R.drawable.iceland;
                    break;
                case "JPY": this.flagImageReference = R.drawable.japan;
                    break;
                case "KRW": this.flagImageReference = R.drawable.south_korea;
                    break;
                case "MXN": this.flagImageReference = R.drawable.mexico;
                    break;
                case "MYR": this.flagImageReference = R.drawable.malaysia;
                    break;
                case "NOK": this.flagImageReference = R.drawable.norway;
                    break;
                case "NZD": this.flagImageReference = R.drawable.new_zealand;
                    break;
                case "PHP": this.flagImageReference = R.drawable.philippines;
                    break;
                case "PLN": this.flagImageReference = R.drawable.republic_of_poland;
                    break;
                case "RON": this.flagImageReference = R.drawable.romania;
                    break;
                case "RUB": this.flagImageReference = R.drawable.russia;
                    break;
                case "SEK": this.flagImageReference = R.drawable.sweden;
                    break;
                case "SGD": this.flagImageReference = R.drawable.singapore;
                    break;
                case "THB": this.flagImageReference = R.drawable.thailand;
                    break;
                case "TRY": this.flagImageReference = R.drawable.turkey;
                    break;
                case "USD": this.flagImageReference = R.drawable.united_states_of_america;
                    break;
                case "ZAR": this.flagImageReference = R.drawable.south_africa;
                    break;
                default:
                    this.flagImageReference = R.drawable.croatia;
            }
        }
        return flagImageReference;
    }

}
