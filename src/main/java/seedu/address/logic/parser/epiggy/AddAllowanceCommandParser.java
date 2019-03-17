package seedu.address.logic.parser.epiggy;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;

import java.util.Date;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.epiggy.AddAllowanceCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.epiggy.item.Item;
import seedu.address.model.epiggy.item.Name;
import seedu.address.model.epiggy.item.Price;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddAllowanceCommand object.
 */
public class AddAllowanceCommandParser implements Parser<AddAllowanceCommand> {
    @Override
    public AddAllowanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COST);
        if (!arePrefixesPresent(argMultimap, PREFIX_COST)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAllowanceCommand.MESSAGE_USAGE));
        }

        Name name = new Name("Allowance");
        Price cost = ParserUtil.parseCost(argMultimap.getValue(PREFIX_COST).get());
        Date date = new Date(); //TODO
        Set<Tag> tagList = Set.of(new Tag("Allowance"));

        Item item = new Item(name, cost, tagList);

        Allowance allowance = new Allowance(item, date);

        return new AddAllowanceCommand(allowance);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
