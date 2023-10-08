package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class fileReader {

	private String filePath = "./lecture_list.txt";
	public HashMap<String, Lecture> lecturelist = new HashMap<>();

	public fileReader(String filePath) {
		this.filePath = filePath;
		readFile();
	}

	private void readFile() {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;

			while ((line = br.readLine()) != null) {

				String[] parts = line.split(" "); // 탭으로 문자열을 분할
				
				if (parts.length >= 7) {
					Lecture lec = new Lecture();
					lec.lectureCode = parts[0].trim();
					lec.lectureName = parts[1].trim();
					lec.lectureDay1 = parts[2].trim();
					lec.lectureDay2 = parts[3].trim();
					lec.lectureStime = parts[4].trim();
					lec.lectureOtime = parts[5].trim();
					lec.lectureCnum = parts[6].trim();
					lec.lectureMnum = parts[7].trim();
					lecturelist.put(parts[0].trim(), lec);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("파일을 찾을 수 없습니다,");
		}
	}

	public void printLectureList() {
		System.out.println("강의목록");
		System.out.printf("%-10s %-20s %-20s %-20s%n", "과목번호", "교과목명", "강의시간", "현재수강인원 / 최대수강인원 \t");
		Collection<Lecture> values = lecturelist.values();
		for (Lecture value : values) {
			value.printLectureList();
		}}}
