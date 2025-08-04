//package interview.question.company.google.segreate_overlapping_intervals;
//
//import lombok.Builder;
//import lombok.Data;
//
//import java.util.*;
//
//public class Solution {
//    @Data
//    @Builder
//    class IntervalDetails{
//        private int startTime;
//        private int endTime;
//        private Set<String> associatedWaiters;
//    }
//
//    @Data
//    @Builder
//    class WaiterInterval{
//        private int startTime;
//        private int endTime;
//        private String associatedWaiter;
//    }
//
//    @Data
//    @Builder
//    static
//    class TimeWaiterEvent{
//        private int timestamp;
//        private Set<String> startSet;
//        private Set<String> endSet;
//
//        public TimeWaiterEvent(int timestamp){
//            this.timestamp = timestamp;
//            startSet = new HashSet<>();
//            endSet = new HashSet<>();
//        }
//    }
//
//    public static List<IntervalDetails> segregateOverlappingIntervals(List<WaiterInterval> waiterIntervals){
//        if(waiterIntervals == null || waiterIntervals.isEmpty()){
//            return Collections.EMPTY_LIST;
//        }
//
//        TreeMap<Integer, TimeWaiterEvent> timeWaiterMap = buildTimeWaiterMap(waiterIntervals); // TC: O(events * log(events)) | SC: O(events)
//        List<IntervalDetails> response = processMapAndGenerateIntervalDetails(timeWaiterMap);
//
//        return response;
//    }
//
//    private static List<IntervalDetails> processMapAndGenerateIntervalDetails(TreeMap<Integer, TimeWaiterEvent> timeWaiterEventTreeMap){
//        List<IntervalDetails> response = new ArrayList<>();
//        Set<String> runningWaiterSet = new HashSet<>();
//        int prevTime = -1;
//        while(!timeWaiterEventTreeMap.isEmpty()){
//            Map.Entry<Integer, TimeWaiterEvent> currEntry = timeWaiterEventTreeMap.pollFirstEntry();
//            int currTime = currEntry.getKey();
//            TimeWaiterEvent currWaiterEvent = currEntry.getValue();
//            Set<String> startWaiterSet = currWaiterEvent.startSet;
//            Set<String> endWaiterSet = currWaiterEvent.endSet;
//
//            if(prevTime != -1){
//                IntervalDetails currIntervalDetails = IntervalDetails.builder()
//                        .associatedWaiters(new HashSet<>(runningWaiterSet))
//                        .endTime(currTime)
//                        .startTime(prevTime)
//                        .build();
//                response.add(currIntervalDetails);
//            }
//
//            prevTime = currTime;
//            runningWaiterSet.addAll(startWaiterSet);
//            runningWaiterSet.removeAll(endWaiterSet);
//        }
//        return response;
//    }
//
//    private static TreeMap<Integer, TimeWaiterEvent> buildTimeWaiterMap(List<WaiterInterval> waiterIntervals){
//        TreeMap<Integer, TimeWaiterEvent> timeWaiterMap = new TreeMap<>();
//        for(WaiterInterval waiterInterval: waiterIntervals){
//            int startTime = waiterInterval.getStartTime();
//            int endTime = waiterInterval.getEndTime();
//            String associatedWaiter = waiterInterval.getAssociatedWaiter();
//
//            timeWaiterMap.putIfAbsent(startTime, new TimeWaiterEvent(startTime)).getStartSet().add(associatedWaiter);
//            timeWaiterMap.putIfAbsent(endTime, new TimeWaiterEvent(endTime)).getEndSet().add(associatedWaiter);
//        }
//        return timeWaiterMap;
//    }
//}
