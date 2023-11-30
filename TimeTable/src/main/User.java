package main;

import java.util.ArrayList;
import java.util.Collections;

public class User {
	
	String id; // �й� 
	String password; // ��й�ȣ
	final String FILEPATH; // �й�.txt ���� ��� ���ڿ�
	ArrayList<Lecture> myLectureList = null; // ���� ��û�� ���� ����Ʈ
	ArrayList<Lecture> pastLectureList = null; // 2�� �䱸���� - �����ߴ� ���� ����Ʈ
	ArrayList<Integer> pastLectureListYear = null; // �����ߴ� ���� ���� ����Ʈ
	// 1�� �䱸���� - �ִ� ���� �ѵ�, ����� ���� �߰�
	static final int MAX_CREDIT = 18; // �ִ� ���� �ѵ�
	int myCredit; // ����
	
 	
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
			System.out.println("������û����");
			System.out.printf("%-10s %-10s %-10s %-18s %-10s %-10s\n", "�����ȣ", "�������", "����","���ǽð�", "���ǽ�", "������");
			for (Lecture value : myLectureList) {
				value.printMyLectureList();
		  }
		  System.out.println();
		  System.out.println(id);
      
		  return true;
		}
  }
  
}