package sample;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class TipCalculatorModifiedController {

    private static final NumberFormat currency =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percent =
            NumberFormat.getPercentInstance();
    private BigDecimal tipPercentage = new BigDecimal(0.15);

    @FXML
    private TextField amountTextField;

    @FXML
    private TextField tipTextField;

    @FXML
    private TextField totalTextField;

    @FXML
    private Slider tipPercentageSlider;

    @FXML
    private Label tipPercentageLabel;

    @FXML
    private void calculateButtonPressed(ActionEvent event) {

    }

    public void initialize() {
        currency.setRoundingMode(RoundingMode.HALF_UP);
        tipPercentageSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue,
                                Number number, Number t1) {
                tipPercentage = BigDecimal.valueOf(t1.intValue()/100.0);
                tipPercentageLabel.setText(percent.format(tipPercentage));
                try {
                    BigDecimal amount = new BigDecimal(amountTextField.getText());
                    BigDecimal tip = amount.multiply(tipPercentage);
                    BigDecimal total = amount.add(tip);
                    tipTextField.setText(currency.format(tip));
                    totalTextField.setText(currency.format(total));
                } catch (NumberFormatException e) {
                    amountTextField.setText("Enter amount");
                    amountTextField.selectAll();
                    amountTextField.requestFocus();
                }
            }
        });

        amountTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue,
                                String s, String t1) {
                try {
                    BigDecimal amount = new BigDecimal(t1);
                    BigDecimal tip = amount.multiply(tipPercentage);
                    BigDecimal total = amount.add(tip);
                    tipTextField.setText(currency.format(tip));
                    totalTextField.setText(currency.format(total));
                } catch (NumberFormatException e) {
                    amountTextField.setText("Enter amount");
                    amountTextField.selectAll();
                    amountTextField.requestFocus();
                    tipTextField.clear();
                    totalTextField.clear();
                }
            }
        });
    }
}
