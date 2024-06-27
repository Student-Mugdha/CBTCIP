import java.io.*;
import java.util.*;

public class BanKY {
    private static final String DATA_FILE = "accounts.txt";
    private Map<String, Double> accounts;

    public BanKY() {
        accounts = new HashMap<>();
        loadAccounts();
    }

    public void createAccount(String accountNumber) {
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account already exists.");
        } else {
            accounts.put(accountNumber, 0.0);
            saveAccounts();
            System.out.println("Account created successfully.");
        }
    }

    public void deposit(String accountNumber, double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        if (accounts.containsKey(accountNumber)) {
            double newBalance = accounts.get(accountNumber) + amount;
            accounts.put(accountNumber, newBalance);
            saveAccounts();
            System.out.println("Deposit successful. New balance: " + newBalance);
        } else {
            System.out.println("Account does not exist.");
        }
    }

    public void withdraw(String accountNumber, double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }
        if (accounts.containsKey(accountNumber)) {
            double currentBalance = accounts.get(accountNumber);
            if (currentBalance >= amount) {
                double newBalance = currentBalance - amount;
                accounts.put(accountNumber, newBalance);
                saveAccounts();
                System.out.println("Withdrawal successful. New balance: " + newBalance);
            } else {
                System.out.println("Insufficient funds.");
            }
        } else {
            System.out.println("Account does not exist.");
        }
    }

    public void transfer(String fromAccount, String toAccount, double amount) {
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive.");
            return;
        }
        if (accounts.containsKey(fromAccount) && accounts.containsKey(toAccount)) {
            double fromBalance = accounts.get(fromAccount);
            if (fromBalance >= amount) {
                double toBalance = accounts.get(toAccount);
                accounts.put(fromAccount, fromBalance - amount);
                accounts.put(toAccount, toBalance + amount);
                saveAccounts();
                System.out.println("Transfer successful. New balance of " + fromAccount + ": " + (fromBalance - amount));
                System.out.println("New balance of " + toAccount + ": " + (toBalance + amount));
            } else {
                System.out.println("Insufficient funds in the source account.");
            }
        } else {
            System.out.println("One or both accounts do not exist.");
        }
    }

    private void loadAccounts() {
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                accounts.put(parts[0], Double.parseDouble(parts[1]));
            }
        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
    }

    private void saveAccounts() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Map.Entry<String, Double> entry : accounts.entrySet()) {
                pw.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        BanKY bank = new BanKY();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Create account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.nextLine();
                    bank.createAccount(accountNumber);
                    break;
                case 2:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextLine();
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    bank.deposit(accountNumber, depositAmount);
                    break;
                case 3:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextLine();
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    bank.withdraw(accountNumber, withdrawAmount);
                    break;
                case 4:
                    System.out.print("Enter source account number: ");
                    String fromAccount = scanner.nextLine();
                    System.out.print("Enter destination account number: ");
                    String toAccount = scanner.nextLine();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    bank.transfer(fromAccount, toAccount, transferAmount);
                    break;
                case 5:
                    scanner.close();
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
