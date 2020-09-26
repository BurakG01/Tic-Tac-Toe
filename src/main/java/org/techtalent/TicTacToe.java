package org.techtalent;

import org.techtalent.exceptions.AlreadyMarkedException;
import org.techtalent.exceptions.InvalidRangeException;
import org.techtalent.models.Position;
import org.techtalent.enums.Marker;
import org.techtalent.exceptions.IsNotYourTurnException;

public class TicTacToe {

    private Marker[][] board;
    private Marker currentPlayerMarker;

    public TicTacToe() {
        this.board = new Marker[3][3];
        currentPlayerMarker = Marker.X;
        initializeBoard();
    }

    public boolean isBoardFull() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Marker.EMPTY)
                    return false;
            }
        }
        return true;
    }

    public void initializeBoard() {

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {
                board[i][j] = Marker.EMPTY;
            }
        }
    }

    public void changePlayer() {
        if (currentPlayerMarker.equals(Marker.X)) {
            currentPlayerMarker = Marker.O;
        } else {
            currentPlayerMarker = Marker.X;
        }
    }


    public Marker getCurrentPlayerMarker() {
        return this.currentPlayerMarker;
    }

    public void placeMark(Marker marker, Position position) {
        if (currentPlayerMarker != marker) {
            throw new IsNotYourTurnException("It is not your turn!");
        }
        if (position.getI() < 0 || position.getI() > 3 || position.getJ() < 0 || position.getJ() > 3) {
            throw new InvalidRangeException("Range is not in board!");
        }
        if (board[position.getI()][position.getJ()] != Marker.EMPTY) {
            throw new AlreadyMarkedException("Already marked!");
        }
        board[position.getI()][position.getJ()] = marker;
    }

    public boolean checkForWin() {

        return (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin());
    }

    private boolean checkRowsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[i][0], board[i][1], board[i][2]) == true) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumnsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[0][i], board[1][i], board[2][i]) == true) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalsForWin() {
        return ((checkRowCol(board[0][0], board[1][1], board[2][2]) == true) || (checkRowCol(board[0][2], board[1][1], board[2][0]) == true));
    }

    private boolean checkRowCol(Marker m1, Marker m2, Marker m3) {
        return ((m1 != Marker.EMPTY) && (m1 == m2) && (m2 == m3));
    }
}
