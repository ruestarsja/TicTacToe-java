import java.util.Scanner;
import java.util.InputMismatchException;


public class TicTacToe {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        byte[][] board = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
        };
        byte turn = 1;

        printBoard(board);

        boolean running = true;
        while (running) {
            byte[] playerSquareChoice = getSquare(scanner, board);
            board = updateBoard(board, playerSquareChoice, turn);
            printBoard(board);
            byte win = detectWin(board);
            running = (win == 0);
            reportWin(win);
            turn = swapTurn(turn);
        }


        scanner.close();
    }


    private static void printBoard(byte[][] board) {
        /*
         *  X O .
         *  O . .
         *  X . X
         */
        
        int r = 0;
        while (r < board.length) {
            int c = 0;
            while (c < board[r].length) {
                System.out.print(" " +
                    switch (board[r][c]) {
                        case 0 -> ".";
                        case 1 -> "X";
                        case 2 -> "O";
                        default -> "?";
                    }
                );
                ++c;
            }
            System.out.println();
            ++r;
        }
    }


    private static byte[] getSquare(Scanner scanner, byte[][] board) {
        boolean success = false;
        byte squareNum = -1;
        byte[] squareNums = {-1, -1};
        while (!success) {
            try {
                System.out.print("Please select a square (1-9): ");
                squareNum = scanner.nextByte();
                scanner.nextLine(); // clear buffer to allow future input

                if (squareNum >= 1 && squareNum <= 9) {
                    squareNums[0] = (byte)((squareNum - 1) / 3);
                    squareNums[1] = (byte)((squareNum - 1) % 3);
                    if (board[squareNums[0]][squareNums[1]] == 0) {
                        success = true;
                    } else {
                        System.out.println("Please pick an empty square.");
                    }
                } else {
                    System.out.println("Error: Please enter an integer between 1 and 9.");
                }
            } catch (InputMismatchException ex) {
                scanner.nextLine(); // clear buffer to allow future input
                System.out.println("Error: Please enter an integer between 1 and 9.");
            }
        }
        return(squareNums);
    }


    private static byte[][] updateBoard(byte[][] board, byte[] square, byte turn) {
        board[square[0]][square[1]] = turn;
        return(board);
    }


    private static byte detectWin(byte[][] board) {
        boolean full = true;
        for (byte[] row : board) {
            for (byte cell : row) {
                if (cell == 0) {
                    full = false;
                }
            }
        }
        if (full) {
            return(-1);
        } // else keep going

        byte[] winnerSquare = {-1, -1};
        if (
            board[0][0] != 0 && (
                (board[0][0] == board[0][1] && board[0][0] == board[0][2]) ||
                (board[0][0] == board[1][0] && board[0][0] == board[2][0])
            )
        ) {
            winnerSquare[0] = 0;
            winnerSquare[1] = 0;
        } else if (
            board[1][1] != 0 && (
                (board[1][1] == board[1][0] && board[1][1] == board[1][2]) ||
                (board[1][1] == board[0][0] && board[1][1] == board[2][2]) ||
                (board[1][1] == board[0][1] && board[1][1] == board[2][1]) ||
                (board[1][1] == board[0][2] && board[1][1] == board[2][0])
            )
        ) {
            winnerSquare[0] = 1;
            winnerSquare[1] = 1;
        } else if (
            board[2][2] != 0 && (
                (board[2][2] == board[0][2] && board[2][2] == board[1][2]) ||
                (board[2][2] == board[2][0] && board[2][2] == board[2][1])
            )
        ) {
            winnerSquare[0] = 2;
            winnerSquare[1] = 2;
        }

        if (winnerSquare[0] == -1) { // nothing found
            return(0);
        } else {
            return(
                board [winnerSquare[0]] [winnerSquare[1]]
            );
        }
    }


    private static void reportWin(byte win) {
        System.out.println(
            switch (win) {
                case -1 -> "Tie game!";
                case 0 -> "No win yet!";
                case 1 -> "Player X wins!";
                case 2 -> "Player O wins!";
                default -> "Error in reporting win condition. Got win value: " + win;
            }
        );
    }


    private static byte swapTurn(byte turn) {
        if (turn == 1) {
            turn = 2;
        } else {
            turn = 1;
        }
        return(turn);
    }

}
