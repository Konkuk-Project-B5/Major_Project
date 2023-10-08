package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TablePrint {
	// lecture 객체 선언

	fileReader lecture = new fileReader("lecture_list.txt");
	private static Scanner scan = new Scanner(System.in);

	private void showTimeTable() {
		System.out.println("수강신청내역");
		System.out.printf("%-10s %-20s %-20s", "과목번호", "교과목명", "강의시간");
		System.out.println();
		// User 클래스의 FILEPATH를 사용하여 파일 경로 지정
		// String idFilePath = loginUser.FILEPATH;
		String idFilePath = "202011372.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(idFilePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String key = line; // 탭으로 문자열을 분할
				lecture.printLectureList(key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();

		// System.out.println(loginUser.ID);
		System.out.println(202011372);

		System.out.println();
		
			System.out.print("※ 'q'를 입력한 경우 메인메뉴로 돌아갑니다.\n\n");
			while (true) {
			String input = scan.nextLine().strip();

			if (input.equals("q")) {
				System.out.println("메인메뉴로 돌아갑니다.");
				return;
			}
			else
				System.out.print("※ 'q'를 입력한 경우 메인메뉴로 돌아갑니다.\n\n");
		}
	}

	public static void main(String[] args) {
		TablePrint tb = new TablePrint();
		tb.showTimeTable();
	}
}
