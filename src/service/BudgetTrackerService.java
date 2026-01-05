package service;

import model.*;

import java.time.LocalDate;
import java.util.Scanner;
import java.lang.Math;

public class BudgetTrackerService {

    public static DailyRecord getDayByDate(BudgetTracker tracker, int date) {
        for (DailyRecord day : tracker.days) {
            if (day.date == date) {
                return day;
            }
        }
        return null;
    }

    public static DailyRecord addDay(BudgetTracker tracker, int date) {
        if (getDayByDate(tracker, date) != null) {
            return getDayByDate(tracker, date);
        }
        DailyRecord day = new DailyRecord(date);
        tracker.days.addFirst(day);
        return day;
    }

    public static void addOperation(BudgetTracker tracker,
                                    float amount,
                                    String description, int date) {

        DailyRecord day = addDay(tracker, date);
        day.operations.add(new Operation(amount, description));
        System.out.printf("Added operation: %.2f dollars on %d\n", amount, date);
    }

    public static void addOperation(BudgetTracker tracker,
                                    float amount,
                                    String description) {
        LocalDate today = LocalDate.now();
        int todayDate = today.getYear()*10000+today.getMonthValue()*100+today.getDayOfMonth();
        addOperation(tracker, amount, description, todayDate);
    }


    public static void addIncome(BudgetTracker tracker) {
        Scanner scanner = new Scanner(System.in);

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
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter amount of expense: ");
        float amount = scanner.nextFloat();
        if (amount > 0) {
            amount = -amount;
        }
        float currentNetBalance = getNetBalance(tracker);
        if (currentNetBalance < Math.abs(amount)) {
            System.out.printf("Wow, your expenses are big lately! You better start saving!%n Your current net balance is %f", currentNetBalance);
        }

        System.out.print("Enter description: ");
        scanner.nextLine();
        String description = scanner.nextLine();

        addOperation(tracker, amount, description);
    }

    public static float getDayTotal(BudgetTracker tracker, int date) {
        DailyRecord day = getDayByDate(tracker, date);
        if (day == null) {
            return 0;
        }

        float total = 0;
        for (Operation t : day.operations) {
            total += t.amount;
        }
        return total;
    }

    public static float getNetBalance(BudgetTracker tracker) {
        float total = 0;
        for (DailyRecord day : tracker.days) {
            total += getDayTotal(tracker, day.date);
        }
        return total;
    }

    public static void showOperations(BudgetTracker tracker, int date){
        for (DailyRecord day : tracker.days) {
            System.out.println(day.operations);
        }
    }

    public static void showOperations(BudgetTracker tracker) {
        int printed = 0;
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
            System.out.println("No operations");
        }
    }

    public static void showIncome(BudgetTracker tracker){
        int printed = 0;
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
            System.out.println("No income operations");
        }
    }

    public static void showExpense(BudgetTracker tracker){
        int printed = 0;
        System.out.println("The list of 5 last entered operations:");

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
            System.out.println("No income operations");
        }
    }
}
