import java.io.*;
import java.util.Scanner;

public class TicTacToe {
    private static char[] board;
    private static int playerScore = 0;
    private static int computerScore = 0;
    private static int lastPlayerMove = -1;
    private static String difficulty;
    private static final String LEADERBOARD_FILE = "leaderboard.txt";

    private static void initializeBoard() {
        board = new char[] {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    }

    private static void displayBoard() {
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.print(" " + board[i] + " ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
                if (i < 6) System.out.println("---|---|---");
            } else {
                System.out.print("|");
            }
        }
        System.out.println();
    }

    private static void chooseDifficulty(Scanner scanner) {
        System.out.println("Choose difficulty level:");
        System.out.println("1 - Easy");
        System.out.println("2 - Medium");
        System.out.println("3 - Hard (Unbeatable)");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1: difficulty = "Easy"; break;
            case 2: difficulty = "Medium"; break;
            case 3: difficulty = "Hard"; break;
            default:
                System.out.println("Invalid choice, defaulting to Hard.");
                difficulty = "Hard";
        }
    }

    private static boolean checkWinner(char player) {
        int[][] winPositions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6}             // Diagonals
        };
        for (int[] pos : winPositions) {
            if (board[pos[0]] == player && board[pos[1]] == player && board[pos[2]] == player) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFull() {
        for (char cell : board) {
            if (cell == ' ') {
                return false;
            }
        }
        return true;
    }

    private static void playerTurn(Scanner scanner) {
        while (true) {
            System.out.print("Enter your move (1-9) or 'u' to undo last move: ");
            String input = scanner.next();

            if (input.equalsIgnoreCase("u") && lastPlayerMove != -1) {
                board[lastPlayerMove] = ' ';
                lastPlayerMove = -1;
                displayBoard();
                continue;
            }

            int move;
            try {
                move = Integer.parseInt(input) - 1;
                if (move >= 0 && move < 9 && board[move] == ' ') {
                    board[move] = 'X';
                    lastPlayerMove = move;
                    break;
                } else {
                    System.out.println("That cell is already taken or out of range!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 9, or 'u' to undo.");
            }
        }
    }

    private static void computerTurn() {
        int move = -1;

        switch (difficulty) {
            case "Easy":
                move = getRandomMove();
                break;
            case "Medium":
                if (Math.random() < 0.5) {
                    move = getRandomMove();
                } else {
                    move = getBestMove();
                }
                break;
            case "Hard":
                move = getBestMove();
                break;
        }

        if (move != -1) {
            board[move] = 'O';
        }
    }

    private static int getRandomMove() {
        int move;
        do {
            move = (int) (Math.random() * 9);
        } while (board[move] != ' ');
        return move;
    }

    private static int getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int move = -1;

        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                board[i] = 'O';
                int score = minimax(board, 0, false);
                board[i] = ' ';
                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        return move;
    }

    private static int minimax(char[] board, int depth, boolean isMaximizing) {
        if (checkWinner('O')) return 1;
        if (checkWinner('X')) return -1;
        if (isFull()) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                if (board[i] == ' ') {
                    board[i] = 'O';
                    int score = minimax(board, depth + 1, false);
                    board[i] = ' ';
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                if (board[i] == ' ') {
                    board[i] = 'X';
                    int score = minimax(board, depth + 1, true);
                    board[i] = ' ';
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    private static void updateScore(String winner) {
        int points;
        switch (difficulty) {
            case "Easy": points = 1; break;
            case "Medium": points = 2; break;
            case "Hard": points = 3; break;
            default: points = 1;
        }

        if (winner.equals("Player")) {
            playerScore += points;
        } else if (winner.equals("Computer")) {
            computerScore += points;
        }
    }

    private static void displayLeaderboard() {
        System.out.println("\n--- Leaderboard ---");
        System.out.println("Player Total Score: " + playerScore);
        System.out.println("Computer Total Score: " + computerScore);
        System.out.println("-------------------\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEADERBOARD_FILE))) {
            writer.write("Player Total Score: " + playerScore + "\n");
            writer.write("Computer Total Score: " + computerScore + "\n");
        } catch (IOException e) {
            System.out.println("Error updating leaderboard: " + e.getMessage());
        }
    }

    private static void playRound(Scanner scanner) {
        initializeBoard();
        displayBoard();

        while (true) {
            playerTurn(scanner);
            displayBoard();
            if (checkWinner('X')) {
                System.out.println("You win this round!");
                updateScore("Player");
                break;
            }
            if (isFull()) {
                System.out.println("This round is a tie!");
                break;
            }

            computerTurn();
            displayBoard();
            if (checkWinner('O')) {
                System.out.println("Computer wins this round!");
                updateScore("Computer");
                break;
            }
            if (isFull()) {
                System.out.println("This round is a tie!");
                break;
            }
        }
        displayScores();
    }

    private static void displayScores() {
        System.out.println("\nScores:");
        System.out.println("Player: " + playerScore);
        System.out.println("Computer: " + computerScore + "\n");
    }

    public static void playGame() {
        Scanner scanner = new Scanner(System.in);
        boolean keepPlaying = true;

        while (keepPlaying) {
            chooseDifficulty(scanner);
            playRound(scanner);

            System.out.println("Play again? (yes/no): ");
            keepPlaying = scanner.next().equalsIgnoreCase("yes");
        }

        displayLeaderboard();
        scanner.close();
    }

    public static void main(String[] args) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEADERBOARD_FILE))) {
            writer.write("Player Total Score: 0\n");
            writer.write("Computer Total Score: 0\n");
        } catch (IOException e) {
            System.out.println("Error resetting leaderboard: " + e.getMessage());
        }

        playGame();
    }
}
