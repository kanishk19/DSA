package interview.question.company.uber.min_time_for_all_cars_at_common_loc;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Solution {
    public int computeMinTime(List<Integer> locationList, List<Integer> speedList, int carsCount){
        if(locationList == null || locationList.isEmpty() || speedList == null || speedList.isEmpty() || carsCount == 0)
            return 0;

        int low = 0, high = (int) Math.pow(10,9);
        boolean flag = false;
        while(low < high){
            int midTime = (low + high)/2;
            flag = canPlaceCarInTime(locationList, speedList, midTime);
            if(flag)
                high = midTime;
            else
                low = midTime + 1;
        }
        return low;
    }

    private boolean canPlaceCarInTime(List<Integer> locationList, List<Integer> speedList, int time){
        long maxLeftBoundary = Integer.MIN_VALUE, minRightBoundary = Integer.MAX_VALUE;
        for(int itr = 0; itr < locationList.size(); itr++){
            long currLocation = (long)locationList.get(itr);
            long currSpeed = (long)speedList.get(itr);

            long currRightBoundary = currLocation + (currSpeed * time);
            long currLeftBoundary = currLocation - (currSpeed * time);

            maxLeftBoundary = Math.max(currLeftBoundary, maxLeftBoundary);
            minRightBoundary = Math.min(currRightBoundary, minRightBoundary);
        }
        return (maxLeftBoundary <= minRightBoundary);
    }
}
