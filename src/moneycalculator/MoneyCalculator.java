package moneycalculator;

import moneycalculator.model.ExchangeRate;
import moneycalculator.model.Money;
import moneycalculator.model.CurrencyList;
import moneycalculator.model.Currency;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.time.Month;
import java.time.LocalDate;

public class MoneyCalculator {

    public static void main(String[] args) throws Exception {
      MoneyCalculator moneyCalculator = new MoneyCalculator();
      moneyCalculator.execute();       
    }
     private final CurrencyList currencies;
     private Money money;
     private Currency currencyTo;
     private ExchangeRate exchangeRate;
     
     public MoneyCalculator(){
         currencies = new CurrencyList();
    }
     
     private void execute() throws Exception{
        input();
        process();
        output();
    }
     
     private void input(){
        System.out.println("Introduzca cantidad");
        Scanner scanner = new Scanner(System.in);
        double amount = Double.parseDouble(scanner.next());
        
        while (true) {
            System.out.println("Introduzca código divisa origen");
            Currency currency = currencies.get(scanner.next());
            money = new Money(amount, currency);
            if (currency != null) break;
            System.out.println("Divisa no conocida");
        }

        while (true) {
            System.out.println("Introduzca codigo divisa destino");
            currencyTo = currencies.get(scanner.next());
            if (currencyTo != null) break;
            System.out.println("Divisa no conocida");
        }
    }
     
     private void process() throws Exception{
        exchangeRate = getExchangeRate(money.getCurrency(), currencyTo);
    }
      
     private void output(){
        System.out.println(money.getAmount() + " " 
                + money.getCurrency().getSymbol() + " equivalen a "
                + money.getAmount() * exchangeRate.getRate() + " "
                + currencyTo.getSymbol());
    }
     
     /**
      * Otras paginas para el exchange rate
      * http://www.floatrates.com/scripts/converter.js
      */
     private static ExchangeRate getExchangeRate(Currency from, Currency to) 
            throws IOException {
        URL url = 
            new URL("https://api.exchangeratesapi.io/latest?symbols="+to.getCode()+"&base="+from.getCode());
        URLConnection connection = url.openConnection();
        try (BufferedReader reader = 
                new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
            String line = reader.readLine();
            String line1 = line.substring(line.indexOf(to.getCode())+5, line.indexOf("}"));
            return new ExchangeRate(from, to, 
                    LocalDate.of(2018, Month.SEPTEMBER, 26), 
                    Double.parseDouble(line1));
            
        }
    }
}
