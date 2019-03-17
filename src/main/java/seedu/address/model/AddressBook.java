package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
//import javafx.beans.value.ObservableObjectValue;
//import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.Expense;
import seedu.address.model.epiggy.Goal;
import seedu.address.model.epiggy.Savings;
import seedu.address.model.epiggy.item.Item;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final ObservableList<Expense> expenses;
    private final ObservableList<Item> items;
    private SimpleObjectProperty<Budget> budget;
    private SimpleObjectProperty<Goal> goal;
    private SimpleObjectProperty<Savings> savings;
    private final UniquePersonList persons;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        expenses = FXCollections.observableArrayList();
        items = FXCollections.observableArrayList();
        //TODO init budget
        persons = new UniquePersonList();
        budget = new SimpleObjectProperty<>();
        goal = new SimpleObjectProperty<>();
        savings = new SimpleObjectProperty<>(new Savings());

    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
        indicateModified();
    }

    /**
     * Adds an expense to the expense book.
     */
    public void addExpense(Expense expense) {
        expenses.add(expense);
        Savings s = savings.get();
        s.deductSavings(expense.getItem().getPrice().getAmount());
        savings.set(s);
        indicateModified();
    }

    /**
     * Adds an allowance to the expense book.
     * @param allowance to be added.
     */
    public void addAllowance(Allowance allowance) {
        expenses.add(allowance);
        Savings s = savings.get();
        s.addSavings(allowance.getItem().getPrice().getAmount());
        savings.set(s);
        indicateModified();
    }

    public SimpleObjectProperty<Savings> getSavings() {
        return savings;
    }

    /**
     * Sets a budget for ePiggy.
     */
    public void setBudget(Budget budget) {
        this.budget.setValue(budget);
        indicateModified();
    }

    /**
     * Gets the current budget for ePiggy.
     */
    public SimpleObjectProperty<Budget> getBudget() {
        return budget;
    }

    /**
     * Sets the saving goal for ePiggy.
     */
    public void setGoal(Goal goal) {
        this.goal.setValue(goal);
        indicateModified();
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Expense> getExpenseList() {
        return FXCollections.unmodifiableObservableList(expenses);
    }

    @Override
    public ObservableList<Item> getItemList() {
        return FXCollections.unmodifiableObservableList(items);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
