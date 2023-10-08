package main;

import java.util.ArrayList;

public class User {
	
	String id; // 학번 
	String password; // 비밀번호
	final String FILEPATH; // 학번.txt 파일 경로 문자열
	ArrayList<String> myLectureList = null; // 수강 신청한 강의 리스트
	
	public User(String id, String password) {
		super();
		this.id = id;
		this.password = password;
		this.FILEPATH = "./"+id+".txt";
	}

}
