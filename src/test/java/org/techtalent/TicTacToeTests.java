package org.techtalent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.techtalent.enums.Marker;
import org.techtalent.exceptions.AlreadyMarkedException;
import org.techtalent.exceptions.InvalidRangeException;
import org.techtalent.exceptions.IsNotYourTurnException;
import org.techtalent.models.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class TicTacToeTests {

    @Test
    public void isBoardFull_should_return_false_when_board_is_empty() {
        // Arrange
        TicTacToe sut = new TicTacToe();

        // Act
        boolean isBoardFull = sut.isBoardFull();

        //Assert
        assertThat(isBoardFull).isFalse();

    }

    @Test
    public void changePlayer_should_change_marker() {
        //Arrange
        TicTacToe sut = new TicTacToe();

        //Act
        sut.changePlayer();
        Marker currentPlayerMarker = sut.getCurrentPlayerMarker();

        //Assert
        assertThat(currentPlayerMarker).isEqualTo(Marker.O);
    }

    @Test
    public void placeMark_should_throw_IsNotYourTurnException_when_try_to_place_same_marker_consecutive() {

        // Arrange
        TicTacToe sut = new TicTacToe();

        // Act
        sut.placeMark(Marker.X, new Position(1, 2));
        sut.changePlayer();
        Throwable throwable = catchThrowable(() -> sut.placeMark(Marker.X, new Position(1, 1)));

        // Assert
        assertThat(throwable).isInstanceOf(IsNotYourTurnException.class).hasMessage("It is not your turn!");
    }

    @Test
    public void checkForWin_should_return_false_when_board_is_empty() {
        //Arrange
        TicTacToe sut = new TicTacToe();

        //Act
        boolean isWin = sut.checkForWin();

        //Assert
        assertThat(isWin).isFalse();

    }

    @Test
    public void placeMark_should_throw_AlreadyMarkedException_when_try_to_add_same_place() {
        // Arrange
        TicTacToe sut = new TicTacToe();

        // Act
        sut.placeMark(Marker.X, new Position(1, 1));
        sut.changePlayer();
        Throwable throwable = catchThrowable(() -> sut.placeMark(Marker.O, new Position(1, 1)));

        // Assert
        assertThat(throwable).isInstanceOf(AlreadyMarkedException.class).hasMessage("Already marked!");

    }

    @ParameterizedTest
    @MethodSource("provideMarkerData")
    public void checkForWin_should_return_true_when_markers_put_in_the_right_place(List<Position> positions) {
        // Arrange
        TicTacToe sut = new TicTacToe();

        //Act
        for (Position position : positions) {
            sut.placeMark(Marker.X, position);
        }

        boolean isWin = sut.checkForWin();

        //Assert
        assertThat(isWin).isTrue();

    }


    @ParameterizedTest
    @MethodSource("provideOutOfRangeData")
    public void placeMark_should_throw_InvalidRangeException_when_range_is_not_board(Position position) {
        // Arrange
        TicTacToe sut = new TicTacToe();

        // Act
        Throwable throwable = catchThrowable(() -> sut.placeMark(Marker.X, position));

        // Assert
        assertThat(throwable).isInstanceOf(InvalidRangeException.class).hasMessage("Range is not in board!");

    }


    private static Stream<Arguments> provideOutOfRangeData() {
        return Stream.of(
                Arguments.of(new Position(4, 1)),
                Arguments.of(new Position(-1, 1)),
                Arguments.of(new Position(1, 4)),
                Arguments.of(new Position(1, -1)));
    }

    private static Stream<Arguments> provideMarkerData() {
        return Stream.of(
                Arguments.of(getRowsMarkers()),
                Arguments.of(getDiagonalsMarkers()),
                Arguments.of(getColumnsMarkers()));
    }

    private static List<Position> getRowsMarkers() {

        return new ArrayList<Position>() {{
            add(new Position(0, 0));
            add(new Position(0, 1));
            add(new Position(0, 2));
        }};
    }

    private static List<Position> getColumnsMarkers() {
        return new ArrayList<Position>() {{
            add(new Position(0, 0));
            add(new Position(1, 0));
            add(new Position(2, 0));
        }};
    }

    private static List<Position> getDiagonalsMarkers() {
        return new ArrayList<Position>() {{
            add(new Position(0, 0));
            add(new Position(1, 1));
            add(new Position(2, 2));
        }};
    }


}
