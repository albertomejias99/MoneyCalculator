package moneycalculator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import moneycalculator.control.Command;
import moneycalculator.model.Currency;
import moneycalculator.ui.MoneyDialog;
import moneycalculator.ui.MoneyDisplay;
import moneycalculator.ui.swing.SwingMoneyDialog;
import moneycalculator.ui.swing.SwingMoneyDisplay;

public class MainFrame extends JFrame{
    private final Map<String,Command> commands = new HashMap<>();
    private final Currency[] currencies;
    private MoneyDialog moneyDialog;
    private MoneyDisplay moneyDisplay;
    
    public MainFrame(Currency[] currencies) {
        this.currencies = currencies;
        this.setTitle("MONEY CALCULATOR");
        this.setSize(400,400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(moneyDialog(currencies), BorderLayout.NORTH);
        this.add(toolbar(), BorderLayout.CENTER);
        this.add(moneyDisplay(), BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public MoneyDialog getMoneyDialog() {
        return moneyDialog;
    }

    public MoneyDisplay getMoneyDisplay() {
        return moneyDisplay;
    }

    public void add(Command command){
        commands.put(command.name(), command);
    }
    
    private Component moneyDialog(Currency[] currencies) {
        SwingMoneyDialog swingMoneyDialog = new SwingMoneyDialog(currencies);
        this.moneyDialog = swingMoneyDialog;
        return swingMoneyDialog;
    }
   
    private Component moneyDisplay() {
        SwingMoneyDisplay swingMoneyDisplay = new SwingMoneyDisplay();
        this.moneyDisplay = swingMoneyDisplay;
        return swingMoneyDisplay;
    }

    private Component toolbar() {
        JPanel toolBarPanel = new JPanel();
        toolBarPanel.add(calculateButton());
        return toolBarPanel;
    }

    private JButton calculateButton() {
        JButton button = new JButton("Calculate");
        button.addActionListener(calculate());
        return button;
    }

    private ActionListener calculate() {
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get("calculate").execute();
            }
        };
    }
        
}
