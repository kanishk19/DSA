package interview.question.company.stripe.string_expander;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringExpander {
    //"/2022/{jan,feb,march}/report"

    private static final String OPEN_BRACE = "{";
    private static final String CLOSE_BRACE = "}";
    private static final String COMMA_DELIMITER = ",";

    public static void main(String[] args){
        List<String> expandedStrings = expandString("/2022/{jan,feb,march}/report");
        for(String expandedString: expandedStrings){
            System.out.println(expandedString);
        }
        System.out.println(" -------------------- ");

        expandedStrings = expandString("over{crowd,eager,bold,fond}ness");
        for(String expandedString: expandedStrings){
            System.out.println(expandedString);
        }
        System.out.println(" -------------------- ");
        expandedStrings = expandString("read.txt{,.bak}");
        for(String expandedString: expandedStrings){
            System.out.println(expandedString);
        }
        System.out.println(" -------------------- ");
    }

    public static List<String> expandString(String expression){
        System.out.println("expanding string : " + expression);
        boolean validateInputString = validateInputString(expression);
        if(!validateInputString){
            System.out.println("invalid input string for expanding " + expression);
            return Collections.EMPTY_LIST;
        }
        List<String> expandedStrings = new ArrayList<>();

        int openBraceIndex = expression.indexOf(OPEN_BRACE);
        int closeBraceIndex = expression.indexOf(CLOSE_BRACE);

        if(openBraceIndex == -1 && closeBraceIndex == -1){
            System.out.println("Rule Applied: no open or close brace found, returning the expression");
            expandedStrings.add(expression);
            return expandedStrings;
        }

        if(openBraceIndex == -1 || closeBraceIndex == -1 || openBraceIndex > closeBraceIndex){
            System.out.println("Rule Applied: invalid positioning of open and closed braces");
            return expandedStrings;
        }

        String prefix = expression.substring(0, openBraceIndex);
        String tokenString = expression.substring(openBraceIndex+1, closeBraceIndex);
        String suffix = expression.substring(closeBraceIndex+1, expression.length());

        String[] tokens = tokenString.split(COMMA_DELIMITER);
        if(tokens == null || tokens.length == 0){
            String expandedString = prefix + suffix;
            expandedStrings.add(expandedString);
        }
        else{
            for(String token : tokens){
                StringBuilder expandedStringBuilder = new StringBuilder();
                expandedStringBuilder.append(prefix).append(token).append(suffix);
                expandedStrings.add(expandedStringBuilder.toString());
            }
        }

        return expandedStrings;

    }

    private static boolean validateInputString(String inputString){
        if(inputString == null || inputString.length() == 0)
            return false;
        return true;
    }
}
