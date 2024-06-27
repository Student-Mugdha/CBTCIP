import java.util.Scanner;
import java.util.Random;

public class Guess {
    private final int maxRounds = 3;
    private final int maxAttempts = 5;
    private int totalScore = 0;
    private int round = 1;

    public Guess() {
        startNewRound();
    }

    private void startNewRound() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Welcome to the Guess the Number game!");
        System.out.println("You have " + maxRounds + " rounds to play.");

        for (int round = 1; round <= maxRounds; round++) {
            int numberToGuess = random.nextInt(100) + 1;
            int attemptsLeft = maxAttempts;
            boolean hasGuessedCorrectly = false;

            System.out.println("Round " + round + " begins! Guess a number between 1 and 100.");

            while (attemptsLeft > 0 && !hasGuessedCorrectly) {
                System.out.print("Enter your guess (attempts left: " + attemptsLeft + "): ");
                int userGuess;
                try {
                    userGuess = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
                }
                attemptsLeft--;

                if (userGuess == numberToGuess) {
                    hasGuessedCorrectly = true;
                    int points = attemptsLeft + 1;
                    totalScore += points;
                    System.out.println("Congratulations! You guessed the number. You earned " + points + " points.");
                } else if (userGuess < numberToGuess) {
                    System.out.println("The number is higher. Attempts left: " + attemptsLeft);
                } else {
                    System.out.println("The number is lower. Attempts left: " + attemptsLeft);
                }
            }

            if (!hasGuessedCorrectly) {
                System.out.println("Sorry! You've run out of attempts. The number was " + numberToGuess + ".");
            }
        }

        System.out.println("Game over! Your total score is: " + totalScore);
    }

    public static void main(String[] args) {
        new Guess();
    }
}
