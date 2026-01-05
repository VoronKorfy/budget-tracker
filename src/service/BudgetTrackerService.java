package service;

import model.*;

import java.time.LocalDate;
import java.util.Scanner;
import java.lang.Math;

public class BudgetTrackerService {
    public static final Scanner scanner = new Scanner(System.in);
    static LocalDate today = LocalDate.now();
    public static int todayDate = today.getYear()*10000+today.getMonthValue()*100+today.getDayOfMonth();

    // ---------------- INITIALIZATION ------------------ //

    public static DailyRecord addDay(BudgetTracker tracker, int date) {
        for (DailyRecord day : tracker.days) {
            if (day.date == date) {
                return day;
            }
        }
        DailyRecord day = new DailyRecord(date);
        tracker.days.addFirst(day);
        return day;
    }

    // ---------------- OPERATIONS ------------------ //
    public static void addOperation(BudgetTracker tracker, float amount, String description, int date) {
        DailyRecord day = addDay(tracker, date);
        day.operations.add(new Operation(amount, description));
        System.out.printf("Added operation: %.2f dollars on %d%n", amount, date);
    }

    public static void addOperation(BudgetTracker tracker, float amount, String description) {
        addOperation(tracker, amount, description, todayDate);
    }

    public static void addIncome(BudgetTracker tracker) {
        System.out.print("Enter amount of income: ");
        float amount = scanner.nextFloat();

        if (amount < 0) {
            System.out.println("You can't add a negative amount! Turned into a positive.");
            amount = -amount;
        }

        System.out.print("Enter description: ");
        scanner.nextLine();
        String description = scanner.nextLine();

        System.out.print("Enter date (YYYYMMDD) or write today: ");
        String dayOfOperation = scanner.next();
        if (!dayOfOperation.equals("today")) {
            int dateOfOperation = Integer.parseInt(dayOfOperation);
            addOperation(tracker, amount, description, dateOfOperation);
        }
        else {
            addOperation(tracker, amount, description);
        }
    }


    public static void addExpense(BudgetTracker tracker) {
        System.out.print("Enter the amount you spent: ");
        float amount = scanner.nextFloat();
        if (amount > 0) {
            amount = -amount;
        }

        float currentNetBalance = getNetBalance(tracker);
        if (currentNetBalance < Math.abs(amount)) {
            System.out.printf("Wow, you spent a lot lately! You better start saving!%n Your current net balance is %f", currentNetBalance);
        }

        System.out.print("Enter description: ");
        scanner.nextLine();
        String description = scanner.nextLine();

        System.out.print("Enter date (YYYYMMDD) or write today: ");
        String dayOfOperation = scanner.next();
        if (!dayOfOperation.equals("today")) {
            int dateOfOperation = Integer.parseInt(dayOfOperation);
            addOperation(tracker, amount, description, dateOfOperation);
        }
        else {
            addOperation(tracker, amount, description);
        }
    }

    public static float getDayTotal(BudgetTracker tracker, int date) {
        DailyRecord day = addDay(tracker, date);

        float total = 0;
        for (Operation t : day.operations) {
            total += t.amount;
        }
        return total;
    }

    public static float getNetBalance(BudgetTracker tracker) {
        return getIncome(tracker) + getExpense(tracker);
    }

    public static float getIncome(BudgetTracker tracker) {
        float income = 0;
        for (DailyRecord day : tracker.days) {
            float dayTotal = getDayTotal(tracker, day.date);
            if (dayTotal > 0) {
                income += dayTotal;
            }
        }
        return income;
    }

    public static float getExpense(BudgetTracker tracker) {
        float expense = 0;
        for (DailyRecord day : tracker.days) {
            float dayTotal = getDayTotal(tracker, day.date);
            if (dayTotal < 0) {
                expense += dayTotal;
            }
        }
        return expense;
    }

    public static void showOperationHistory(BudgetTracker tracker, int date) {
        DailyRecord day = addDay(tracker, date);
        if (day.operations.isEmpty()) {
            System.out.println("There were no operations on the date " + date);
            return;
        }
        System.out.printf("The net income on that day is %f", getDayTotal(tracker, date));
        System.out.println("Operations for " + date + ":");
        for (Operation operation : day.operations) {
            System.out.printf(
                    "%d | %.2f | %s%n",
                    day.date,
                    operation.amount,
                    operation.description
            );
        }
    }

    public static void showOperationHistory(BudgetTracker tracker) {
        int printed = 0;
        System.out.printf("Your overall NET income is %f", getNetBalance(tracker));
        System.out.println("The list of 5 last entered operations:");

        for (int d = tracker.days.size() - 1; d >= 0 && printed < 5; d--) {
            DailyRecord day = tracker.days.get(d);

            for (int t = day.operations.size() - 1; t >= 0 && printed < 5; t--) {
                Operation operation = day.operations.get(t);

                System.out.printf(
                        "%d | %.2f | %s%n",
                        day.date,
                        operation.amount,
                        operation.description
                );

                printed++;
            }
        }

        if (printed == 0) {
            System.out.println("There were no operations yet!");
        }
    }

    public static void showIncomeHistory(BudgetTracker tracker){
        int printed = 0;
        System.out.printf("Your overall worth of income is %f", getIncome(tracker));
        System.out.println("The list of 5 last entered operations:");

        for (int d = tracker.days.size() - 1; d >= 0 && printed < 5; d--) {
            DailyRecord day = tracker.days.get(d);

            for (int t = day.operations.size() - 1; t >= 0 && printed < 5; t--) {
                Operation operation = day.operations.get(t);
                if (operation.amount > 0) {
                    System.out.printf(
                            "%d | %.2f | %s%n",
                            day.date,
                            operation.amount,
                            operation.description
                    );

                    printed++;
                }
            }
        }

        if (printed == 0) {
            System.out.println("No income operations yet");
        }
    }

    public static void showExpenseHistory(BudgetTracker tracker){
        int printed = 0;
        System.out.printf("Your overall spending is %f", getExpense(tracker));
        System.out.println("The list of 5 last entered payments:");

        for (int d = tracker.days.size() - 1; d >= 0 && printed < 5; d--) {
            DailyRecord day = tracker.days.get(d);

            for (int t = day.operations.size() - 1; t >= 0 && printed < 5; t--) {
                Operation operation = day.operations.get(t);
                if (operation.amount < 0) {
                    System.out.printf(
                            "%d | %.2f | %s%n",
                            day.date,
                            operation.amount,
                            operation.description
                    );

                    printed++;
                }
            }
        }

        if (printed == 0) {
            System.out.println("No spending operations yet");
        }
    }
}
