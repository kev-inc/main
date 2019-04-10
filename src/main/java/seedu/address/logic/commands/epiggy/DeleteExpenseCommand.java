package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expense.Allowance;
import seedu.address.model.expense.Expense;

/**
 * Delete an expense in ePiggy.
 */
public class DeleteExpenseCommand extends Command {

    public static final String COMMAND_WORD = "deleteExpense";
    public static final String COMMAND_ALIAS = "de";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the expense identified by the index number used in the displayed expense list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DELETE_EXPENSE_SUCCESS = "Deleted expense: %1$s";
    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS = "The index does not exist on the expense list.";
    public static final String MESSAGE_ITEM_NOT_EXPENSE = "The item selected is not an expense. "
            + "Please use " + COMMAND_WORD + " to delete expenses and "
            + DeleteAllowanceCommand.COMMAND_WORD + " to delete allowances.";

    private final Index targetIndex;

    public DeleteExpenseCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Expense> lastShownExpenseList = model.getFilteredExpenseList();

        if (targetIndex.getZeroBased() >= lastShownExpenseList.size()) {
            throw new CommandException(MESSAGE_INDEX_OUT_OF_BOUNDS);
        }

        Expense expenseToDelete = lastShownExpenseList.get(this.targetIndex.getZeroBased());
        if (expenseToDelete instanceof Allowance) {
            throw new CommandException(MESSAGE_ITEM_NOT_EXPENSE);
        }
        model.deleteExpense(expenseToDelete);
        model.commitEPiggy();
        return new CommandResult(String.format(MESSAGE_DELETE_EXPENSE_SUCCESS, expenseToDelete));
    }
}
