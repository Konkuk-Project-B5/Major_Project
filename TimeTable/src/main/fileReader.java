package source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class fileReader {
    private String filePath = "lecture_list.txt";
    private HashMap<String, HashMap<String, Object>> lecturelist = new HashMap<>();

    public fileReader(String filePath) {
        this.filePath = filePath;
        readFile();
    }

    private void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t"); // 탭으로 문자열을 분할

                if (parts.length >= 8) {
                    String key = parts[0].trim(); // 줄의 첫 번째 문자열을 키로 사용
                    HashMap<String, Object> secondHashMap = new HashMap<>();

                    // 이중 HashMap에 데이터 추가
                    secondHashMap.put("name", parts[1].trim()); //과목이름
                    secondHashMap.put("day1", parts[2].trim()); //과목요일
                    secondHashMap.put("day2", parts[3].trim()); //혹시 두번 째 요일이 있으면 저장. 없으면 ""값
                    secondHashMap.put("stime", parts[4].trim()); // 시작시간
                    secondHashMap.put("otime", parts[5].trim()); // 끝나는시간
                    secondHashMap.put("cnum", parts[6].trim()); //현재인원
                    secondHashMap.put("mnum", parts[7].trim()); //최대인원

                    // 고유한 과목번호를 키로 가짐.
                    lecturelist.put(key, secondHashMap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 목업에 맞게 전체 출력 메소드
    public void printLectureList() {
        System.out.println("강의목록");
        System.out.printf("%-10s %-20s %-20s %-20s%n", "과목번호", "교과목명", "강의시간", "현재수강인원 / 최대수강인원 \t");
        for (String key : lecturelist.keySet()) {
            HashMap<String, Object> secondHashMap = lecturelist.get(key);
            String lectureName = (String) secondHashMap.get("name");
            String day1 = (String) secondHashMap.get("day1");
            String day2 = (String) secondHashMap.get("day2");
            String stime = (String) secondHashMap.get("stime");
            String etime = (String) secondHashMap.get("otime");
            String curNum = (String) secondHashMap.get("cnum");
            String maxNum = (String) secondHashMap.get("mnum");
            // 현재수강인원 / 최대수강인원 형식으로 출력하고 공백을 추가하여 맞춤
            String enrollment = String.format("%s / %s", curNum, maxNum);

            if (day2.isEmpty()) {// 요일 한개
                String time = String.format("%s %s-%s", day1, stime, etime);
                System.out.printf("%-10s %-20s %-20s %-20s%n", key, lectureName, time, enrollment);
            } else {// 요일 두개
                String time = String.format("%s %s-%s, %s %s-%s", day1, stime, etime, day2, stime, etime);
                System.out.printf("%-10s %-20s %-20s %-20s%n", key, lectureName, time, enrollment);
            }

        }

    }

    public HashMap<String, Object> getLecture(String key) {
        return lecturelist.get(key);
    }


}
