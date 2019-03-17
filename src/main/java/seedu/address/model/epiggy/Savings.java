package seedu.address.model.epiggy;

/**
 * Represents a saving in the expense book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Savings {
    private float savings;

    public Savings() {
        this.savings = 0.00f;
    }

    public float getSavings() {
        return savings;
    }

    public void addSavings(float increment) {
        savings += increment;
    }

    public void deductSavings(float decrement) {
        savings -= decrement;
    }

    @Override
    public String toString() {
        return String.format("$%.2f", savings);
    }
}
