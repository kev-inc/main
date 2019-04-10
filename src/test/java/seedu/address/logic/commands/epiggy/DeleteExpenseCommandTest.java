package seedu.address.logic.commands.epiggy;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showExpenseAtIndex;
import static seedu.address.testutil.epiggy.TypicalExpenses.getTypicalEPiggy;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.expense.Allowance;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.TypicalIndexes;
import seedu.address.testutil.epiggy.TypicalAllowances;

public class DeleteExpenseCommandTest {
    private Model model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexIsExpense_deleteSuccess() {
        Expense expenseToDelete = model.getFilteredExpenseList()
                .get(TypicalIndexes.INDEX_FIRST_EXPENSE.getZeroBased());
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(TypicalIndexes.INDEX_FIRST_EXPENSE);

        String expectedMessage = String.format(DeleteExpenseCommand.MESSAGE_DELETE_EXPENSE_SUCCESS,
                expenseToDelete);

        ModelManager expectedModel = new ModelManager(model.getEPiggy(), new UserPrefs());
        expectedModel.deleteExpense(expenseToDelete);
        expectedModel.commitEPiggy();

        assertCommandSuccess(deleteExpenseCommand, model, commandHistory,
                expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexIsAllowance_throwsCommandException() {
        Model tempModel = new ModelManager(TypicalAllowances.getTypicalEPiggy(),
                new UserPrefs());
        Allowance allowanceToDelete = (Allowance) tempModel.getFilteredExpenseList()
                .get(TypicalIndexes.INDEX_FIRST_ALLOWANCE.getZeroBased());
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(TypicalIndexes.INDEX_FIRST_ALLOWANCE);

        String expectedMessage = String.format(DeleteExpenseCommand.MESSAGE_ITEM_NOT_EXPENSE,
                allowanceToDelete);

        ModelManager expectedModel = new ModelManager(tempModel.getEPiggy(), new UserPrefs());
        expectedModel.deleteExpense(allowanceToDelete);
        expectedModel.commitEPiggy();

        assertCommandFailure(deleteExpenseCommand, tempModel, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        showExpenseAtIndex(model, TypicalIndexes.INDEX_FIRST_EXPENSE);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_EXPENSE;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEPiggy().getExpenseList().size());
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(outOfBoundIndex);
        assertCommandFailure(deleteExpenseCommand, model, commandHistory,
                DeleteExpenseCommand.MESSAGE_INDEX_OUT_OF_BOUNDS);
    }
}
