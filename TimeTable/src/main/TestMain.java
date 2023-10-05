package main;

public class TestMain {

    public static void main(String[] args) {
        fileReader file = new fileReader("./lecture_list.txt");
        file.printLectureList();
        Lecture lec1 = file.lecturelist.get("001");
        
        System.out.println("값 접근");
        lec1.printLectureList();
        System.out.println(lec1.lectureCode);
    }

}
