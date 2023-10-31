package main;

import java.util.ArrayList;

public class User {
	
	String id; // 학번 
	String password; // 비밀번호
	final String FILEPATH; // 학번.txt 파일 경로 문자열
	ArrayList<Lecture> myLectureList = null; // 수강 신청한 강의 리스트
	
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

}
