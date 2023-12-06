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

public class myFileReader {

private String filePath1 = "./lecture_list.txt";
	private String filePath2 = "./lecturer.txt";
	private String filePath3 = "./lecture_room.txt";
	public HashMap<String, Lecture> lecturelist = new HashMap<>();
	public HashMap<String, Lecture_room> lectureRoomlist = new HashMap<>();
	public HashMap<String, Lecturer> lecturerlist = new HashMap<>();
	public myFileReader(String filePath1, String filePath2, String filePath3) {   
		this.filePath1 = filePath1;
		this.filePath2 = filePath2;
		this.filePath3 = filePath3;
		readLectureFile();
		readLecuturerFile();
		readLectureRoomFile();
	}

	private void readLectureRoomFile() { // 2차요구사항 - 강의실 추가 강의실 파일 읽어와서 해쉬맵에 저장HashMap<String, Lecture_room> lectureRoomlist
		try (BufferedReader br = new BufferedReader(new FileReader(filePath2))) {
			String line;

			while ((line = br.readLine()) != null) {

				String[] parts = line.split(" "); // 스페이스바로 문자열을 분할

				if (parts.length >= 2) {
					Lecture_room lec = new Lecture_room(parts[0].trim(), parts[1].trim());
					lectureRoomlist.put(parts[0].trim(), lec);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("파일을 찾을 수 없습니다,");
		}
	}

	private void readLecuturerFile() { // 2차요구사항 - 교강사 추가 교강사 파일 읽어와서 해쉬맵에 저장 HashMap<String, Lecturer> lecturerlist = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath3))) {
			String line;

			while ((line = br.readLine()) != null) {

				String[] parts = line.split(" "); // 스페이스바로 문자열을 분할

				if (parts.length >= 1) {
					Lecturer lec = new Lecturer(parts[0].trim());
					lecturerlist.put(parts[0].trim(), lec);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("파일을 찾을 수 없습니다,");
		}
	}
	private void readLectureFile() {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath1))) {
			String line;

			while ((line = br.readLine()) != null) {

				String[] parts = line.split(" "); // 스페이스바로 문자열을 분할

				if (parts.length >= 13) {
					Lecture lec = new Lecture();
					lec.lectureCode = parts[0].trim();
					lec.lectureName = parts[1].trim();
					lec.lecturer = parts[2].trim(); // 2차요구사항 - 교강사 추가
					lec.lectureDay1 = parts[3].trim(); 
					lec.lectureDay1Stime = parts[4].trim(); // 2차요구사항 - 요일에 따라 강의 시간 다르게 가능
					lec.lectureDay1Otime = parts[5].trim(); // 2차요구사항 - 요일에 따라 강의 시간 다르게 가능
					lec.lectureRoomDay1 = parts[6].trim(); // 2차요구사항 - 강의실 추가
					lec.lectureDay2 = parts[7].trim(); // 2차요구사항 - 요일에 따라 강의 시간 다르게 가능
					lec.lectureDay2Stime = parts[8].trim();  // 2차요구사항 - 요일에 따라 강의 시간 다르게 가능
					lec.lectureDay2Otime = parts[9].trim(); // 2차요구사항 - 요일에 따라 강의 시간 다르게 가능
					lec.lectureRoomDay2 = parts[10].trim(); // 2차요구사항 - 강의실 추가
					lec.lectureCnum = parts[11].trim(); 
					lec.lectureMnum = parts[12].trim();
					// 1차 요구사항 - 강의 학점 임시 초기화
					lec.lectureCredit = parts[13].trim();
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
		System.out.printf("%-10s %-20s %-20s %-18s %10s%n", "과목번호", "교과목명", "강의시간", "현재수강인원 / 최대수강인원","학점");
		Collection<Lecture> values = lecturelist.values();
		for (Lecture value : values) {
			value.printLectureList();
		}
	}
	
	public void updateLectureFile(String lectureNum) {
	    // 임시로 수정된 데이터를 저장할 리스트
	    List<String> updatedData = new ArrayList<>();

	try (BufferedReader br = new BufferedReader(new FileReader(filePath1))) {
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
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath1))) {
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

	try (BufferedReader br = new BufferedReader(new FileReader(filePath1))) {
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
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath1))) {
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