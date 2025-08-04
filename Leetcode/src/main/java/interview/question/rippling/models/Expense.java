package interview.question.rippling.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Represents an expense record with details such as type, amount, seller, and item information.
 * Used for rule validation and expense tracking.
 */
@Getter
@Slf4j
@Builder
@AllArgsConstructor
public class Expense {
    /** Unique identifier for the expense. */
    private final String expenseId;

    /** Identifier for the item associated with the expense. */
    private final String itemId;

    /** The type/category of the expense (e.g., Food, Entertainment). */
    private final ExpenseType expenseType;

    /** The amount of the expense in USD. */
    private final double amountUSD;

    /** The type/category of the seller (e.g., Restaurant, Hotel). */
    private final SellerType sellerType;

    /** The name of the seller. */
    private final String sellerName;

    /**
     * Returns a string representation of the Expense object, including all its fields.
     *
     * @return a string describing the expense
     */
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        return builder.append("Expense { ")
                        .append("expenseId: ").append(expenseId).append(", ")
                        .append("itemId: ").append(itemId).append(", ")
                        .append("expenseType: ").append(expenseType.toString()).append(", ")
                        .append("amountUSD: ").append(amountUSD).append(", ")
                        .append("sellerType: ").append(sellerType.toString()).append(", ")
                        .append("sellerName: ").append(sellerName).append(" ")
                .toString();
    }
}
