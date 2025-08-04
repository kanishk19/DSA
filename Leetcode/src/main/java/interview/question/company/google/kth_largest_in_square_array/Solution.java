package interview.question.company.google.kth_largest_in_square_array;

import com.sun.tools.javac.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Slf4j
public class Solution {
    public int getKthSquareNumber(TestInput testInput){
        if(testInput == null || CollectionUtils.isEmpty(testInput.getNumList())){
            return -1;
        }

        List<Integer> numList = testInput.getNumList();
        int k = testInput.getK();

        PriorityQueue<Pair<Integer, Integer>> squareQueue = new PriorityQueue<Pair<Integer, Integer>>(new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> squarePair1, Pair<Integer, Integer> squarePair2) {
                return Integer.compare(squarePair1.snd, squarePair2.snd);
            }
        });

        for(int num: numList){
            squareQueue.add(new Pair<>(num, num));
        }

        Pair<Integer, Integer> runningPair = squareQueue.peek();
        while(k > 0){
            runningPair = squareQueue.poll();
            int runningNum = runningPair.fst;
            int runningMultiple = runningPair.snd;
            squareQueue.add(new Pair<>(runningNum, runningMultiple + runningNum));

            k-=1;
        }
        return runningPair.snd;
    }

}
