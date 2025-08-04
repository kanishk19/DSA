package interview.question.rippling.validator;

import interview.question.rippling.models.Expense;
import interview.question.rippling.models.ExpenseType;
import interview.question.rippling.models.SellerType;
import interview.question.rippling.rule.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseValidatorTest {

    private ExpenseValidator expenseValidator;
    @BeforeEach
    void setUp() {
        expenseValidator = new ExpenseValidator();
    }
    private Expense sampleExpense(String expenseId, double amount, ExpenseType type, SellerType sellerType) {
        return new Expense(
                expenseId,
                "item1",
                type,
                amount,
                sellerType,
                "Test Seller"
        );
    }

    @Test
    public void testExpenseValidatorDetectsViolations() {
        List<IRule> rules = Arrays.asList(
                new TotalExpenseRule("Total expense should not be > 100", 100.0),
                new ExcusedExpenseTypeRule("Entertainment expense type should not be charged", ExpenseType.ENTERTAINMENT)
        );
        List<Expense> expenses = Arrays.asList(
                sampleExpense("exp1",120, ExpenseType.FOOD, SellerType.RESTAURANT), // Violates total expense
                sampleExpense("exp2", 30, ExpenseType.ENTERTAINMENT, SellerType.CAFE) // Violates excused type
        );
        ExpenseValidator validator = new ExpenseValidator();
        Map<String, List<String>> violations = validator.evaluateRules(rules, expenses);

        assertEquals(2, violations.size());
        assertTrue(violations.get("exp1").contains("Total expense should not be > 100") ||
                violations.get("exp1").contains("Entertainment expense type should not be charged"));
    }

    @Test
    @DisplayName("Should flag expense exceeding total expense limit")
    void evaluateRules_TotalExpenseLimitExceeded_ShouldFlag() {
        List<Expense> expenses = createSampleExpenses(); // exp1: 200, exp2: 150, exp3: 60
        List<IRule> rules = Arrays.asList(
                createTotalExpenseLimitRule("Rule: Total expense should not be > 175", 175.0)
        );

        Map<String, List<String>> violations = expenseValidator.evaluateRules(rules, expenses);

        // Assertions
        assertNotNull(violations);
        assertEquals(1, violations.size(), "Only one expense (exp1) should be flagged");
        assertTrue(violations.containsKey("exp1"), "Expense 'exp1' should be in violations");
        assertEquals(1, violations.get("exp1").size(), "exp1 should have 1 violation");
        assertTrue(violations.get("exp1").contains("Rule: Total expense should not be > 175"));
    }

    @Test
    @DisplayName("Should flag expenses exceeding specific seller type limit")
    void evaluateRules_SellerTypeLimitExceeded_ShouldFlag() {
        List<Expense> expenses = createSampleExpenses(); // exp1: restaurant, 200; exp5: restaurant, 50
        List<IRule> rules = Arrays.asList(
                createSellerTypeLimitRule("Rule: Seller type restaurant should not have expense more than 45", SellerType.RESTAURANT, 45.0)
        );

        Map<String, List<String>> violations = expenseValidator.evaluateRules(rules, expenses);

        assertNotNull(violations);
        assertEquals(2, violations.size(), "Two expenses (exp1, exp5) should be flagged");
        assertTrue(violations.containsKey("exp1"));
        assertTrue(violations.containsKey("exp5"));
        assertTrue(violations.get("exp1").contains("Rule: Seller type restaurant should not have expense more than 45"));
        assertTrue(violations.get("exp5").contains("Rule: Seller type restaurant should not have expense more than 45"));
    }

    @Test
    @DisplayName("Should flag forbidden expense types")
    void evaluateRules_ForbiddenExpenseType_ShouldFlag() {
        List<Expense> expenses = createSampleExpenses(); // exp3: Entertainment
        List<IRule> rules = Arrays.asList(
                createForbiddenExpenseTypeRule("Rule: Entertainment expense type should not be charged", ExpenseType.ENTERTAINMENT)
        );

        Map<String, List<String>> violations = expenseValidator.evaluateRules(rules, expenses);

        assertNotNull(violations);
        assertEquals(1, violations.size(), "One expense (exp3) should be flagged");
        assertTrue(violations.containsKey("exp3"));
        assertTrue(violations.get("exp3").contains("Rule: Entertainment expense type should not be charged"));
    }

    @Test
    @DisplayName("Should return empty map if no rules are violated")
    void evaluateRules_NoViolations_ShouldReturnEmptyMap() {
        List<Expense> expenses = createSampleExpenses();
        List<IRule> rules = Arrays.asList(
                createTotalExpenseLimitRule("Rule: Total expense should not be > 500", 500.0), // No expense exceeds 500
                createSellerTypeLimitRule("Rule: Seller type restaurant should not have expense more than 300", SellerType.RESTAURANT, 300.0), // No restaurant expense exceeds 300
                createForbiddenExpenseTypeRule("Rule: Luxury expense type should not be charged", ExpenseType.LUXURY) // No "Luxury" expense type
        );

        Map<String, List<String>> violations = expenseValidator.evaluateRules(rules, expenses);

        assertNotNull(violations);
        assertTrue(violations.isEmpty(), "No violations should be found");
    }

    @Test
    @DisplayName("Should flag multiple violations for a single expense")
    void evaluateRules_MultipleViolationsPerExpense_ShouldFlagAll() {
        List<Expense> expenses = Arrays.asList(
                new Expense("exp_bad", "Super Expensive Meal", ExpenseType.FOOD, 250.0, SellerType.RESTAURANT, "Elite Eatery")
        );
        List<IRule> rules = Arrays.asList(
                createTotalExpenseLimitRule("Rule: Total expense should not be > 100", 100.0),
                createSellerTypeLimitRule("Rule: Seller type restaurant should not have expense more than 50", SellerType.RESTAURANT, 50.0)
        );

        Map<String, List<String>> violations = expenseValidator.evaluateRules(rules, expenses);

        assertNotNull(violations);
        assertEquals(1, violations.size(), "Only one expense should be flagged");
        assertTrue(violations.containsKey("exp_bad"));
        assertEquals(2, violations.get("exp_bad").size(), "exp_bad should have 2 violations");
        assertTrue(violations.get("exp_bad").contains("Rule: Total expense should not be > 100"));
        assertTrue(violations.get("exp_bad").contains("Rule: Seller type restaurant should not have expense more than 50"));
    }

    // ========================================================================================
    // UNIT TESTS FOR AGGREGATE RULES (evaluateRules method)
    // ========================================================================================

    @Test
    @DisplayName("Should flag total trip expense limit violation")
    void evaluateRules_TripTotalExpenseLimitExceeded_ShouldFlag() {
        List<Expense> expenses = createSampleExpenses(); // Total sum: 200+150+60+10+50+20 = 490
        List<IRule> rules = Arrays.asList(
                createTripTotalExpenseLimitRule("Aggregate Rule: Trip total should not exceed 400", 400.0)
        );

        Map<String, List<String>> violations = expenseValidator.evaluateRules(rules, expenses);

        assertNotNull(violations);
        assertEquals(1, violations.size(), "Only one aggregate violation should be flagged");
        assertTrue(violations.containsKey("TRIP_SUMMARY_VIOLATIONS"));
        assertEquals(1, violations.get("TRIP_SUMMARY_VIOLATIONS").size());
        assertTrue(violations.get("TRIP_SUMMARY_VIOLATIONS").contains("Aggregate Rule: Trip total should not exceed 400"));
    }

    @Test
    @DisplayName("Should not flag total trip expense if within limit")
    void evaluateRules_TripTotalExpenseWithinLimit_ShouldNotFlag() {
        List<Expense> expenses = createSampleExpenses(); // Total sum: 490
        List<IRule> rules = Arrays.asList(
                createTripTotalExpenseLimitRule("Aggregate Rule: Trip total should not exceed 500", 500.0)
        );

        Map<String, List<String>> violations = expenseValidator.evaluateRules(rules, expenses);

        assertNotNull(violations);
        assertFalse(violations.containsKey("TRIP_SUMMARY_VIOLATIONS"), "Trip summary should not be flagged");
        // Other individual expense violations might exist if other rules were present, but for this rule set, it's empty
        assertTrue(violations.isEmpty(), "No violations expected for this test case");
    }

    @Test
    @DisplayName("Should flag trip food expense limit violation")
    void evaluateRules_TripFoodExpenseLimitExceeded_ShouldFlag() {
        List<Expense> expenses = createSampleExpenses(); // Food expenses: exp1: 200, exp4: 10, exp5: 50. Total Food: 260
        List<IRule> rules = Arrays.asList(
                createTripFoodExpenseLimitRule("Aggregate Rule: Trip food should not exceed 250", 250.0)
        );

        Map<String, List<String>> violations = expenseValidator.evaluateRules(rules, expenses);

        assertNotNull(violations);
        assertEquals(1, violations.size(), "Only one aggregate violation should be flagged");
        assertTrue(violations.containsKey("TRIP_SUMMARY_VIOLATIONS"));
        assertEquals(1, violations.get("TRIP_SUMMARY_VIOLATIONS").size());
        assertTrue(violations.get("TRIP_SUMMARY_VIOLATIONS").contains("Aggregate Rule: Trip food should not exceed 250"));
    }

    @Test
    @DisplayName("Should return empty map if no aggregate rules are violated")
    void evaluateRules_NoAggregateViolations_ShouldReturnEmptyMap() {
        List<Expense> expenses = createSampleExpenses(); // Total sum: 490, Total Food: 260
        List<IRule> rules = Arrays.asList(
                createTripTotalExpenseLimitRule("Aggregate Rule: Trip total should not exceed 600", 600.0),
                createTripFoodExpenseLimitRule("Aggregate Rule: Trip food should not exceed 300", 300.0)
        );

        Map<String, List<String>> violations = expenseValidator.evaluateRules(rules, expenses);

        assertNotNull(violations);
        assertFalse(violations.containsKey("TRIP_SUMMARY_VIOLATIONS"), "Trip summary should not be flagged");
        assertTrue(violations.isEmpty(), "No violations expected for this test case");
    }

    // ========================================================================================
    // UNIT TESTS FOR MIXED RULE TYPES
    // ========================================================================================

    @Test
    @DisplayName("Should flag both individual and aggregate violations")
    void evaluateRules_MixedViolations_ShouldFlagBoth() {
        List<Expense> expenses = createSampleExpenses(); // exp1: 200, exp3: Entertainment. Total: 490, Food: 260
        List<IRule> rules = Arrays.asList(
                createTotalExpenseLimitRule("Rule: Total expense should not be > 175", 175.0), // exp1 violates
                createForbiddenExpenseTypeRule("Rule: Entertainment expense type should not be charged", ExpenseType.ENTERTAINMENT), // exp3 violates
                createTripTotalExpenseLimitRule("Aggregate Rule: Trip total should not exceed 450", 450.0), // Trip total 490 violates
                createTripFoodExpenseLimitRule("Aggregate Rule: Trip food should not exceed 200", 200.0) // Trip food 260 violates
        );

        Map<String, List<String>> violations = expenseValidator.evaluateRules(rules, expenses);

        assertNotNull(violations);
        assertEquals(3, violations.size(), "Three entries expected: exp1, exp3, TRIP_SUMMARY_VIOLATIONS");
        assertTrue(violations.containsKey("exp1"));
        assertTrue(violations.get("exp1").contains("Rule: Total expense should not be > 175"));

        assertTrue(violations.containsKey("exp3"));
        assertTrue(violations.get("exp3").contains("Rule: Entertainment expense type should not be charged"));

        assertTrue(violations.containsKey("TRIP_SUMMARY_VIOLATIONS"));
        assertEquals(2, violations.get("TRIP_SUMMARY_VIOLATIONS").size());
        assertTrue(violations.get("TRIP_SUMMARY_VIOLATIONS").contains("Aggregate Rule: Trip total should not exceed 450"));
        assertTrue(violations.get("TRIP_SUMMARY_VIOLATIONS").contains("Aggregate Rule: Trip food should not exceed 200"));
    }

    @Test
    @DisplayName("Should handle empty expense list gracefully")
    void evaluateRules_EmptyExpenseList_ShouldReturnEmptyMap() {
        List<Expense> expenses = new ArrayList<>();
        List<IRule> rules = Arrays.asList(
                createTotalExpenseLimitRule("Rule: Total expense should not be > 100", 100.0),
                createTripTotalExpenseLimitRule("Aggregate Rule: Trip total should not exceed 0", 0.0)
        );

        Map<String, List<String>> violations = expenseValidator.evaluateRules(rules, expenses);
        assertNotNull(violations);
        assertTrue(violations.isEmpty(), "No violations expected for empty expense list");
    }

    @Test
    @DisplayName("Should handle empty rule list gracefully")
    void evaluateRules_EmptyRuleList_ShouldReturnEmptyMap() {
        List<Expense> expenses = createSampleExpenses();
        List<IRule> rules = new ArrayList<>();

        Map<String, List<String>> violations = expenseValidator.evaluateRules(rules, expenses);
        assertNotNull(violations);
        assertTrue(violations.isEmpty(), "No violations expected for empty rule list");
    }

    // --- Helper method to create sample expenses ---
    private List<Expense> createSampleExpenses() {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense("exp1", "Burger", ExpenseType.FOOD, 200.0, SellerType.RESTAURANT, "Burger Joint"));
        expenses.add(new Expense("exp2", "Flight", ExpenseType.TRAVEL, 150.0, SellerType.AIRLINE, "SkyLink"));
        expenses.add(new Expense("exp3", "Concert", ExpenseType.ENTERTAINMENT, 60.0, SellerType.VENUE, "Music Hall"));
        expenses.add(new Expense("exp4", "Coffee", ExpenseType.FOOD, 10.0, SellerType.CAFE, "Daily Brew"));
        expenses.add(new Expense("exp5", "Dinner", ExpenseType.FOOD, 50.0, SellerType.RESTAURANT, "Fancy Place"));
        expenses.add(new Expense("exp6", "Taxi", ExpenseType.TRAVEL, 20.0, SellerType.CAB, "City Cabs"));
        return expenses;
    }

    // --- Helper methods to create specific rule types ---
    // These directly instantiate rules, bypassing RuleParser for specific tests of evaluateRules
    private IRule createTotalExpenseLimitRule(String description, double limit) {
        return new TotalExpenseRule(description, limit);
    }

    private IRule createSellerTypeLimitRule(String description, SellerType sellerType, double limit) {
        return SellerTypeExpenseRule.builder().sellerType(sellerType).description(description).limit(limit).build();
    }

    private IRule createForbiddenExpenseTypeRule(String description, ExpenseType expenseType) {
        return new ExcusedExpenseTypeRule(description, expenseType);
    }

    private IAggregationRule createTripTotalExpenseLimitRule(String description, double limit) {
        return TotalExpenseOnTripRule.builder().description(description).limit(limit).build();
    }

    private IAggregationRule createTripFoodExpenseLimitRule(String description, double limit) {
        return TotalExpenseOnFoodRule.builder().description(description).limit(limit).build();
    }


}