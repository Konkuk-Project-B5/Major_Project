package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

class Login {
	
	Scanner scan = new Scanner(System.in);
	
	void screen() {
        System.out.println("1. 회원가입");
        System.out.println("2. 로그인");
        System.out.println("3. 종료");
        System.out.print("메뉴 입력: ");
    }
	
	void menuinput() {
		String input = scan.nextLine().replaceAll("\\s", "");
		if(input.equals("1")||input.equals("회원가입"))
			signup();
		else if(input.equals("2")||input.equals("로그인"))
			signin();
		else if(input.equals("3")||input.equals("종료"))
			exit();
		else {
			System.out.println("입력이 올바르지 않습니다. 다시입력해주세요");
			System.out.print("메뉴 입력: ");
			menuinput();
		}
	}
	
	boolean isID(String id) {
		try {
			File file = new File("./user.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line="";
			while((line=br.readLine())!= null) {
				String[] idpw = line.split(" ");
				if(idpw[0].equals(id))
					return true;
			}
			br.close();
		}catch(Exception e) {
			System.out.println("파일 읽기 실패");
		}
		return false;
	}
	
	boolean isIdPwMatch(String id, String pw) {
		try {
			File file = new File("./user.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line="";
			while((line=br.readLine())!= null) {
				String[] idpw = line.split(" ");
				if(idpw[0].equals(id)&&idpw[1].equals(pw))
					return true;
			}
			br.close();
		}catch (Exception e) {
			System.out.println("파일 읽기 실패");
		}
		return false;
	}
	
	void signup() {
		System.out.println("회원가입을 시작합니다.");
		System.out.print("학번 입력: ");
		String inputid = scan.nextLine();
		if(!(Pattern.matches("(201[0-9]|202[0-3])([0-9]{5})", inputid))) {
			System.out.println("학번은 201000000이상 202399999 이하의 자연수입니다.");
			screen();
			menuinput();
			return;
		}
		if(isID(inputid)) {
			System.out.println("이미 등록된 학번입니다.");
			screen();
			menuinput();
			return;
		}
		System.out.print("비밀번호 입력: ");
		String inputpw = scan.nextLine();
		if(!(Pattern.matches("[a-z0-9]{7,13}", inputpw))){
			System.out.println("비밀번호는 영문 소문자와 숫자로만 이루어진 길이가 7이상 13이하인 문자열이어야합니다.");
			screen();
			menuinput();
			return;
		}
		try {
			File file = new File("./user.txt");
			FileWriter fw = new FileWriter(file, true);
			fw.write(inputid+" "+inputpw+"\n");
			fw.close();
			System.out.println("회원가입을 완료했습니다.");
		}catch(Exception e) {
			System.out.println("파일 쓰기 실패");
		}
		screen();
		menuinput();
		return;
	}
	
	void signin() {
		System.out.println("로그인을 시작합니다. 학번과 비밀번호를 입력해주세요.");
		System.out.println("형식: (<횡공백류열0><학번><횡공백류열1><비밀번호><횡공백류열0>)");
		System.out.print("입력: ");
		String input = scan.nextLine().trim();
		String inputidpw[] = input.split("\\s+");
		if(inputidpw.length != 2 || !(Pattern.matches("(201[0-9]|202[0-3])([0-9]{5})", inputidpw[0])) || !(Pattern.matches("[a-z0-9]{7,13}", inputidpw[1]))) {
			System.out.println("학번과 비밀번호를 확인해주세요.");
			screen();
			menuinput();
			return;
		}
		if(isIdPwMatch(inputidpw[0],inputidpw[1])) {
			System.out.println("로그인을 완료했습니다.");
			//메인 메뉴 실행
		}
		else {
			System.out.println("학번과 비밀번호를 확인해주세요.");
			screen();
			menuinput();
			return;
		}
	}
	
	void exit() {
		System.out.println("프로그램을 종료합니다.");
		System.exit(0);
	}
	
		public static void main(String[] args) {
			//System.out.println("김한결");
			Login start = new Login();
			System.out.println("[수강신청 프로그램]");
			start.screen();
			start.menuinput();
		}

}
