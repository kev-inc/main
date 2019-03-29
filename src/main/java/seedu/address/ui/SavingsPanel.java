package seedu.address.ui;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.epiggy.Goal;
import seedu.address.model.epiggy.Savings;

import java.util.logging.Logger;

public class SavingsPanel extends UiPart<Region> {

    private static final String FXML = "SavingsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SavingsPanel.class);

    @FXML
    private Label currentSavings;

    @FXML
    private Label currentGoal;

    @FXML
    private Label amountDifference;

    public SavingsPanel(ObservableValue<Savings> savings, ObservableValue<Goal> goal) {
        super(FXML);

        savings.addListener(observable -> {
            currentSavings.setText(savings.getValue().toString());
            float diff = goal.getValue().getAmount().getAmount() - savings.getValue().getSavings();
            amountDifference.setText("$" + diff);
        });
        goal.addListener(observable -> {
            currentGoal.setText(goal.getValue().toString());
            float diff = goal.getValue().getAmount().getAmount() - savings.getValue().getSavings();
            amountDifference.setText("$" + diff);
        });

        currentSavings.setText(savings.getValue().toString());
        if (goal.getValue() == null) {
            currentGoal.setText("(None set)");
            amountDifference.setText("$0.00");
        } else {
            currentGoal.setText(goal.getValue().toString());
            float diff = goal.getValue().getAmount().getAmount() - savings.getValue().getSavings();
            amountDifference.setText("$" + diff);
        }
    }
}
