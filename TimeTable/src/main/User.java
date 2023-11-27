package main;

import java.util.ArrayList;
import java.util.Collections;

public class User {
	
	String id; // 학번 
	String password; // 비밀번호
	final String FILEPATH; // 학번.txt 파일 경로 문자열
	ArrayList<Lecture> myLectureList = null; // 수강 신청한 강의 리스트
	ArrayList<Lecture> pastLectureList = null; // 2차 요구사항 - 수강했던 강의 리스트
	ArrayList<Integer> pastLectureListYear = null; // 수강했던 강의 연도 리스트
	// 1차 요구사항 - 최대 학점 한도, 사용자 학점 추가
	static final int MAX_CREDIT = 18; // 최대 학점 한도
	int myCredit; // 학점
	
 	
	public User(String id, String password) {
		super();
		this.id = id;
		this.password = password;
		this.FILEPATH = "./"+id+".txt";
		this.myCredit = 0;
	}
	
	public boolean printMyList() {
		if (myLectureList.isEmpty()) {
			return false;
		} else {
			Collections.sort(myLectureList);
			System.out.println("수강신청내역");
			System.out.printf("%-10s %-10s %-10s %-20s\n", "과목번호", "교과목명", "학점","강의시간");
			for (Lecture value : myLectureList) {
				value.printMyLectureList();
		  }
		  System.out.println();
		  System.out.println(id);
      
		  return true;
		}
  }
  
}
