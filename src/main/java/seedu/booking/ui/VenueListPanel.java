package seedu.booking.ui;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.booking.model.venue.Venue;

/**
 * Panel containing the list of venues.
 */
public class VenueListPanel extends UiPart<Region> {
    private static final String FXML = "VenueListPanel.fxml";

    @FXML
    private ListView<Venue> venueListView;

    /**
     * Creates a {@code VenueListPanel} with the given {@code ObservableList}.
     */
    public VenueListPanel(ObservableList<Venue> bookingList) {
        super(FXML);
        venueListView.setItems(bookingList);
        venueListView.setCellFactory(listView -> new VenueListViewCell());
    }

    public void addListener(ChangeListener<Venue> listener) {
        venueListView.getSelectionModel().selectedItemProperty().addListener(listener);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Venue} using a {@code VenueCard}.
     */
    class VenueListViewCell extends ListCell<Venue> {
        @Override
        protected void updateItem(Venue venue, boolean empty) {
            super.updateItem(venue, empty);

            if (empty || venue == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new VenueCard(venue, getIndex() + 1).getRoot());
            }
        }
    }

}
