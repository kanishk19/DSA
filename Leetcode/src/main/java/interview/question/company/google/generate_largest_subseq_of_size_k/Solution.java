package interview.question.company.google.generate_largest_subseq_of_size_k;


import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    private static final Character ZERO = '0';

    public String generateLargestNumberOfSizeK(String number, int subsequenceLength){
        String cornerCaseResponse = handleCornerCases(number, subsequenceLength);
        if(cornerCaseResponse != null){
            return cornerCaseResponse;
        }

        //creates a monotonic stack to contain largest number after removing removalAllowed
        int removalsAllowed = number.length() - subsequenceLength;
        Deque<Character> digitStack = buildDigitDecreasingStack(number, subsequenceLength);

        //generate the number after creating stack
        String rawResponse = buildNumberFromDigitStack(digitStack);

        //trim the leading zeros
        String trimmedResponse = trimLeadingZeros(rawResponse);

        return (trimmedResponse == null || trimmedResponse.length() == 0) ? ZERO.toString() : trimmedResponse;

    }

    private String trimLeadingZeros(String number){
        StringBuilder trimmedNumberBuilder = new StringBuilder();
        boolean isNonZeroDigitFound = false;
        for(int itr = 0; itr < number.length(); itr++){
            Character currDigit = number.charAt(itr);
            if(!isNonZeroDigitFound && currDigit != ZERO){
                isNonZeroDigitFound = true;
            }

            if(isNonZeroDigitFound) trimmedNumberBuilder.append(currDigit);
        }
        return trimmedNumberBuilder.toString();
    }

    private String buildNumberFromDigitStack(Deque<Character> digitStack){
        StringBuilder numberBuilder = new StringBuilder();
        while (!digitStack.isEmpty()){
            Character currDigit = digitStack.pop();
            numberBuilder.append(currDigit);
        }

        return numberBuilder.reverse().toString();
    }

    private Deque<Character> buildDigitDecreasingStack(String number, int removalsAllowed){
        Deque<Character> digitStack = new ArrayDeque<>();
        int numItr = 0;
        while(numItr < number.length()){
            Character currDigit = number.charAt(numItr);
            while(removalsAllowed > 0 && !digitStack.isEmpty()){
                Character currPeek = digitStack.peek();
                if(currPeek >= currDigit){
                    break;
                }

                removalsAllowed -= 1;
                digitStack.pop();
            }

            digitStack.push(currDigit);
            numItr += 1;
        }

        while(removalsAllowed > 0 && !digitStack.isEmpty()){
            removalsAllowed -= 1;
            digitStack.pop();
        }

        return digitStack;
    }

    private String handleCornerCases(String number, int subsequenceLength){
        String cornerCaseResponse = null;
        if(number == null || number.length() == 0 || subsequenceLength == 0)
            cornerCaseResponse = ZERO.toString();

        else if(subsequenceLength >= number.length())
            cornerCaseResponse = number;

        return cornerCaseResponse;
    }
}
