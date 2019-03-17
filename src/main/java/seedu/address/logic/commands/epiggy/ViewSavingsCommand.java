package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.SimpleObjectProperty;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.epiggy.Savings;

/**
 * Allows the user to view the savings recorded in ePiggy.
 */
public class ViewSavingsCommand extends Command {

    public static final String COMMAND_WORD = "viewSavings";
    public static final String COMMAND_ALIAS = "vS";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View your savings. ";

    public static final String MESSAGE_SUCCESS = "Your savings are %1$s";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);
        SimpleObjectProperty<Savings> savings = model.getSavings();
        return new CommandResult(String.format(MESSAGE_SUCCESS, savings.getValue()));
    }
}
