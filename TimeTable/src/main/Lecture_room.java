package main;

public class Lecture_room {
    protected String lectureRoomName;
    protected String maxNum;
    public Lecture_room(String lectureRoomName, String maxNum) {
        this.lectureRoomName = lectureRoomName;
        this.maxNum = maxNum;
    }
    public String getLectureRoomName() {
        return lectureRoomName;
    }
    public String getMaxNum() {
        return maxNum;
    }
}
