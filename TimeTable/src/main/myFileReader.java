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

	private void readLectureRoomFile() { // 2���䱸���� - ���ǽ� �߰� ���ǽ� ���� �о�ͼ� �ؽ��ʿ� ����HashMap<String, Lecture_room> lectureRoomlist
		try (BufferedReader br = new BufferedReader(new FileReader(filePath2))) {
			String line;

			while ((line = br.readLine()) != null) {

				String[] parts = line.split(" "); // �����̽��ٷ� ���ڿ��� ����

				if (parts.length >= 2) {
					Lecture_room lec = new Lecture_room(parts[0].trim(), parts[1].trim());
					lectureRoomlist.put(parts[0].trim(), lec);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("������ ã�� �� �����ϴ�,");
		}
	}

	private void readLecuturerFile() { // 2���䱸���� - ������ �߰� ������ ���� �о�ͼ� �ؽ��ʿ� ���� HashMap<String, Lecturer> lecturerlist = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath3))) {
			String line;

			while ((line = br.readLine()) != null) {

				String[] parts = line.split(" "); // �����̽��ٷ� ���ڿ��� ����

				if (parts.length >= 1) {
					Lecturer lec = new Lecturer(parts[0].trim());
					lecturerlist.put(parts[0].trim(), lec);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("������ ã�� �� �����ϴ�,");
		}
	}
	private void readLectureFile() {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath1))) {
			String line;

			while ((line = br.readLine()) != null) {

				String[] parts = line.split(" "); // �����̽��ٷ� ���ڿ��� ����

				if (parts.length >= 13) {
					Lecture lec = new Lecture();
					lec.lectureCode = parts[0].trim();
					lec.lectureName = parts[1].trim();
					lec.lecturer = parts[2].trim(); // 2���䱸���� - ������ �߰�
					lec.lectureDay1 = parts[3].trim(); 
					lec.lectureDay1Stime = parts[4].trim(); // 2���䱸���� - ���Ͽ� ���� ���� �ð� �ٸ��� ����
					lec.lectureDay1Otime = parts[5].trim(); // 2���䱸���� - ���Ͽ� ���� ���� �ð� �ٸ��� ����
					lec.lectureRoomDay1 = parts[6].trim(); // 2���䱸���� - ���ǽ� �߰�
					lec.lectureDay2 = parts[7].trim(); // 2���䱸���� - ���Ͽ� ���� ���� �ð� �ٸ��� ����
					lec.lectureDay2Stime = parts[8].trim();  // 2���䱸���� - ���Ͽ� ���� ���� �ð� �ٸ��� ����
					lec.lectureDay2Otime = parts[9].trim(); // 2���䱸���� - ���Ͽ� ���� ���� �ð� �ٸ��� ����
					lec.lectureRoomDay2 = parts[10].trim(); // 2���䱸���� - ���ǽ� �߰�
					lec.lectureCnum = parts[11].trim(); 
					lec.lectureMnum = parts[12].trim();
					// 1�� �䱸���� - ���� ���� �ӽ� �ʱ�ȭ
					lec.lectureCredit = parts[13].trim();
					lecturelist.put(parts[0].trim(), lec);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("������ ã�� �� �����ϴ�,");
		}
	}

	public void printLectureList() {
		System.out.println("���Ǹ��");
		System.out.printf("%-10s %-20s %-20s %-18s %10s%n", "�����ȣ", "�������", "���ǽð�", "��������ο� / �ִ�����ο�","����");
		Collection<Lecture> values = lecturelist.values();
		for (Lecture value : values) {
			value.printLectureList();
		}
	}
	
	public void updateLectureFile(String lectureNum) {
	    // �ӽ÷� ������ �����͸� ������ ����Ʈ
	    List<String> updatedData = new ArrayList<>();

	try (BufferedReader br = new BufferedReader(new FileReader(filePath1))) {
	        String line;

	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(" "); // �������� ���ڿ��� ����
	            if (parts.length >= 7) {
	                if (parts[0].trim().equals(lectureNum)) {
	                    int val = Integer.parseInt(parts[6].trim());
	                    val++;
	                    // ������ ���� ���� �°� 10 ������ ��� �տ� 0 �߰�
	                    if(val < 10) {
	                    	parts[6] = "0" + Integer.toString(val);
	                    } else {
	                    	parts[6] = Integer.toString(val);
	                    }
	                    // ������ ���� ����Ʈ�� �߰�
	                    updatedData.add(String.join(" ", parts));
	                } else {
	                    // �������� ���� ���� �״�� ����Ʈ�� �߰�
	                    updatedData.add(line);
	                }
	         
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("������ ã�� �� �����ϴ�.");
	        return;
	    }

	    // ���Ͽ� ������ �����͸� �ٽ� ��
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath1))) {
	        for (String updatedLine : updatedData) {
	            bw.write(updatedLine);
	            bw.newLine(); 
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("���� ���� ���� �߻�.");
	    }
	}
	
	// �ο� ���� ������Ʈ
	public void updateLectureFile2(String lectureNum) {
	    // �ӽ÷� ������ �����͸� ������ ����Ʈ
	    List<String> updatedData = new ArrayList<>();

	try (BufferedReader br = new BufferedReader(new FileReader(filePath1))) {
	        String line;

	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(" "); // �������� ���ڿ��� ����
	            if (parts.length >= 7) {
	                if (parts[0].trim().equals(lectureNum)) {
	                    int val = Integer.parseInt(parts[6].trim());
	                    val--;
	                    if (val < 0) {
	                        val = 0; // ������ �������� �ʵ��� ó��
	                        parts[6] = "0" + Integer.toString(val);
	                    } 
	                    else if(val < 10) {
	                    	parts[6] = "0" + Integer.toString(val);
	                    } else {
	                    	parts[6] = Integer.toString(val);
	                    }
	                    // ������ ���� ����Ʈ�� �߰�
	                    updatedData.add(String.join(" ", parts));
	                } else {
	                    // �������� ���� ���� �״�� ����Ʈ�� �߰�
	                    updatedData.add(line);
	                }
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("������ ã�� �� �����ϴ�.");
	        return;
	    }

	    // ���Ͽ� ������ �����͸� �ٽ� ��
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath1))) {
	        for (String updatedLine : updatedData) {
	            bw.write(updatedLine);
	            bw.newLine(); 
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("���� ���� ���� �߻�.");
	    }
	}
	
}