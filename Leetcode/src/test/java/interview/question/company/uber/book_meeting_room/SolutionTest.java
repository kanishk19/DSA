//package interview.question.company.uber.book_meeting_room;
//
//import org.junit.Assert;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//class SolutionTest {
//
//    private Solution meetingRoomSolution;
//
//    @BeforeEach
//    void setUp() {
//        meetingRoomSolution = new Solution(5);
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void bookMeetingRoom() {
//        String meetingRoom1 = meetingRoomSolution.bookMeetingRoom(2,9);
//        String meetingRoom2 = meetingRoomSolution.bookMeetingRoom(3,8);
//        String meetingRoom3 = meetingRoomSolution.bookMeetingRoom(4,7);
//        String meetingRoom4 = meetingRoomSolution.bookMeetingRoom(5,10);
//        String meetingRoom5 = meetingRoomSolution.bookMeetingRoom(6,8);
//
//        String meetingRoom6 = meetingRoomSolution.bookMeetingRoom(6,8);
//        Assert.assertEquals("-1", meetingRoom6);
//
//        String meetingRoom7 = meetingRoomSolution.bookMeetingRoom(8,10);
//        Assert.assertEquals(meetingRoom3, meetingRoom7);
//
//    }
//}