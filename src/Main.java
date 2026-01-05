import model.*;
import service.*;
import static service.BudgetTrackerService.*;


void main() {
    BudgetTracker tracker = new BudgetTracker();

    System.out.println("Welcome to the Budget Tracker Service! Here you can track all your expenses and incomes and see the history!");
    System.out.println();
    System.out.println("1. Add an income");
    System.out.println("2. Add a payment");
    System.out.println("3. Display last 5 operations");
    System.out.println("4. Display payment history on a certain date");
    System.out.println("5. Display the income history");
    System.out.println("6. Display the expense history");
    System.out.println("0. Exit ");
    System.out.println();

    while (true) {
        System.out.println("What would you like to do?");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        System.out.println();
        MenuOption option = MenuOption.fromInt(choice);

        if (option == null) {
            System.out.println("Invalid choice");
            continue;
        }

        switch (option) {
            case DISPLAY_LAST -> showOperationHistory(tracker);
            case DISPLAY_BY_DATE -> {
                System.out.print("Enter date (YYYYMMDD) or write today: ");
                String displayedDay = scanner.next();
                if (!displayedDay.equals("today")) {
                    int dateOfOperation = Integer.parseInt(displayedDay);
                    showOperationHistory(tracker, dateOfOperation);
                } else {
                    showOperationHistory(tracker, todayDate);
                }
            }
            case SHOW_INCOME_HISTORY -> showIncomeHistory(tracker);
            case SHOW_EXPENSE_HISTORY -> showExpenseHistory(tracker);
            case ADD_INCOME -> addIncome(tracker);
            case ADD_EXPENSE -> addExpense(tracker);
            case EXIT -> System.exit(0);
        }
    }

}
