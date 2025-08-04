//package interview.question.company.uber.book_meeting_room;
//
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.PriorityQueue;
//import java.util.TreeMap;
//
//@Slf4j
//public class Solution {
//
//    class MeetingRoom{
//        String roomName;
//        TreeMap<Integer, Integer> scheduledMeets;z
//
//        public MeetingRoom(String roomName){
//            this.roomName = roomName;
//            scheduledMeets = new TreeMap<>();
//        }
//
//        @Override
//        public String toString(){
//            return "MeetingRoom{ roomName: }";
//        }
//
//        public void addInterval(int start, int end){
//            scheduledMeets.put(start, end);
//        }
//
//        public boolean isOverlapping(int start, int end){
//            Integer lowerStart = scheduledMeets.lowerKey(start);
//            Integer higherStart = scheduledMeets.higherKey(end);
//
//            if((lowerStart == null || lowerStart < start))
//        }
//    }
//
//    private PriorityQueue<MeetingRoom> roomQueue;
//    private static final String ROOM_PREFIX = "room_";
//    private static final Integer DEFAULT_START_TIME = 0;
//    private static final Integer DEFAULT_END_TIME = 0;
//
//    public Solution(int roomSize){
//        roomQueue = new PriorityQueue<MeetingRoom>((room1, room2) -> {
//            int start1 = room1.startTime, end1 = room1.endTime;
//            int start2 = room2.startTime, end2 = room2.endTime;
//
//            return Integer.compare(end1, end2);
//        });
//
//        for(int roomId = 1; roomId <= roomSize; roomId++){
//            String roomName = generateRoomName(roomId);
//            MeetingRoom meetingRoom = new MeetingRoom(roomName, DEFAULT_START_TIME, DEFAULT_END_TIME);
//            log.info("created meeting room, {}", meetingRoom.toString());
//            roomQueue.add(meetingRoom);
//        }
//    }
//
//    private java.lang.String generateRoomName(int roomId){
//        StringBuilder roomNameBuilder = new StringBuilder();
//        roomNameBuilder.append(ROOM_PREFIX);
//        roomNameBuilder.append(roomId);
//
//        return roomNameBuilder.toString();
//    }
//
//    public String bookMeetingRoom(int startTime, int endTime){
//        log.info("book meeting request : startTime = {} endTime = {}", startTime, endTime);
//        if(startTime > endTime){
//            //return out invalid input error
//            return "-1";
//        }
//
//        MeetingRoom candidateRoom = roomQueue.peek();
//        int currStart = candidateRoom.startTime, currEnd = candidateRoom.endTime;
//        if(isOverlappingInterval(startTime, endTime, currStart, currEnd)){
//            log.warn("overlapping with candidate meeting room: currStart: {}, currEnd:{}", currStart, currEnd);
//            return "-1";
//        }
//
//        candidateRoom = roomQueue.poll();
//        candidateRoom.startTime = startTime;
//        candidateRoom.endTime = endTime;
//        roomQueue.add(candidateRoom);
//
//        log.info("added new meeting room to queue: {}", candidateRoom.toString());
//        return candidateRoom.roomName;
//    }
//
//    private boolean isOverlappingInterval(int newStartTime, int newEndTime, int currStartTime, int currEndTime){
//        return (newStartTime >= currStartTime && newStartTime <= currEndTime);
//    }
//}
