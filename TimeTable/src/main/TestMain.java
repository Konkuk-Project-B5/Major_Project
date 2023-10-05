package source;

import java.util.HashMap;

public class TestMain {

    public static void main(String[] args) {
        fileReader lecture = new fileReader("lecture_list.txt");
        lecture.printLectureList();


        System.out.println();

        HashMap<String, Object> lectureInfo = lecture.getLecture("001");//001을 과목번호로 가짐
        String value = (String) lectureInfo.get("name"); //과목 정보 추가
        System.out.println(lectureInfo);
        System.out.println(value);

    }

}
