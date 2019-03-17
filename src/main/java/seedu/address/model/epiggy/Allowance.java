package seedu.address.model.epiggy;

import java.util.Date;

import seedu.address.model.epiggy.item.Item;

/**
 * Represents an allowance in the expense book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Allowance extends Expense {
    public Allowance(Item item, Date date) {

        super(item, date);
    }


}
