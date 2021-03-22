package seedu.booking.logic.parser;

import static seedu.booking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.booking.logic.parser.CliSyntax.PREFIX_BOOKER;
import static seedu.booking.logic.parser.CliSyntax.PREFIX_BOOKINGEND;
import static seedu.booking.logic.parser.CliSyntax.PREFIX_BOOKINGSTART;
import static seedu.booking.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.booking.logic.parser.CliSyntax.PREFIX_VENUE;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.booking.logic.commands.CreateBookingCommand;
import seedu.booking.logic.parser.exceptions.ParseException;
import seedu.booking.model.booking.Booking;
import seedu.booking.model.person.Person;
import seedu.booking.model.venue.Venue;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class CreateBookingCommandParser implements Parser<CreateBookingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddBookingCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateBookingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BOOKER, PREFIX_VENUE,
                        PREFIX_DESCRIPTION, PREFIX_BOOKINGSTART, PREFIX_BOOKINGEND);

        if (!arePrefixesPresent(argMultimap, PREFIX_BOOKER, PREFIX_VENUE,
                PREFIX_DESCRIPTION, PREFIX_BOOKINGSTART, PREFIX_BOOKINGEND)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CreateBookingCommand.MESSAGE_USAGE));
        }

        Person booker = ParserUtil.parseBooker(argMultimap.getValue(PREFIX_BOOKER).get());
        Venue venue = ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get());
        String description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        LocalDateTime bookingStart = ParserUtil.parseBookingStart(argMultimap.getValue(PREFIX_BOOKINGSTART).get());
        LocalDateTime bookingEnd = ParserUtil.parseBookingEnd(argMultimap.getValue(PREFIX_BOOKINGEND).get());

        Booking booking = new Booking(booker, venue, description,
                bookingStart, bookingEnd, Booking.getNewBookingId());

        return new CreateBookingCommand(booking);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}