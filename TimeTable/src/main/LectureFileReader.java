package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class LectureFileReader {

	private String filePath = "./lecture_list.txt";
	public HashMap<String, Lecture> lecturelist = new HashMap<>();

	public LectureFileReader(String filePath) {
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
					// 1차 요구사항 - 강의 학점 임시 초기화
					lec.lectureCredit = "3";
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
		}
	}
	
//	public void printLectureList(String key) {      
//        HashMap<String, Object> secondHashMap = lecturelist.get(key);
//        String lectureName = (String) secondHashMap.get("name");
//
//        String day1 = (String) secondHashMap.get("day1");
//        String day2 = (String) secondHashMap.get("day2");
//        String stime = (String) secondHashMap.get("stime");
//        String etime = (String) secondHashMap.get("otime");
//        // 현재수강인원 / 최대수강인원 형식으로 출력하고 공백을 추가하여 맞춤
//        String enrollment = " ";
//
//
//
//        if (day2.isEmpty()) {// 요일 한개
//            String time = String.format("%s %s-%s", day1, stime, etime);
//            System.out.printf("%-10s %-20s %-20s %-20s%n", key, lectureName, time, enrollment);
//        } else {// 요일 두개
//            String time = String.format("%s %s-%s, %s %s-%s", day1, stime, etime, day2, stime, etime);
//            System.out.printf("%-10s %-20s %-20s %-20s%n", key, lectureName, time, enrollment);
//        }
//    }

	public void updateLectureFile(String lectureNum) {
	    // 임시로 수정된 데이터를 저장할 리스트
	    List<String> updatedData = new ArrayList<>();

	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        String line;

	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(" "); // 공백으로 문자열을 분할
	            if (parts.length >= 7) {
	                if (parts[0].trim().equals(lectureNum)) {
	                    int val = Integer.parseInt(parts[6].trim());
	                    val++;
	                    // 데이터 파일 형식 맞게 10 이하일 경우 앞에 0 추가
	                    if(val < 10) {
	                    	parts[6] = "0" + Integer.toString(val);
	                    } else {
	                    	parts[6] = Integer.toString(val);
	                    }
	                    // 수정된 줄을 리스트에 추가
	                    updatedData.add(String.join(" ", parts));
	                } else {
	                    // 수정하지 않은 줄은 그대로 리스트에 추가
	                    updatedData.add(line);
	                }
	         
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("파일을 찾을 수 없습니다.");
	        return;
	    }

	    // 파일에 수정된 데이터를 다시 씀
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
	        for (String updatedLine : updatedData) {
	            bw.write(updatedLine);
	            bw.newLine(); 
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("파일 쓰기 오류 발생.");
	    }
	}
	
	// 인원 감소 업데이트
	public void updateLectureFile2(String lectureNum) {
	    // 임시로 수정된 데이터를 저장할 리스트
	    List<String> updatedData = new ArrayList<>();

	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        String line;

	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(" "); // 공백으로 문자열을 분할
	            if (parts.length >= 7) {
	                if (parts[0].trim().equals(lectureNum)) {
	                    int val = Integer.parseInt(parts[6].trim());
	                    val--;
	                    if (val < 0) {
	                        val = 0; // 음수로 떨어지지 않도록 처리
	                        parts[6] = "0" + Integer.toString(val);
	                    } 
	                    else if(val < 10) {
	                    	parts[6] = "0" + Integer.toString(val);
	                    } else {
	                    	parts[6] = Integer.toString(val);
	                    }
	                    // 수정된 줄을 리스트에 추가
	                    updatedData.add(String.join(" ", parts));
	                } else {
	                    // 수정하지 않은 줄은 그대로 리스트에 추가
	                    updatedData.add(line);
	                }
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("파일을 찾을 수 없습니다.");
	        return;
	    }

	    // 파일에 수정된 데이터를 다시 씀
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
	        for (String updatedLine : updatedData) {
	            bw.write(updatedLine);
	            bw.newLine(); 
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("파일 쓰기 오류 발생.");
	    }
	}
}