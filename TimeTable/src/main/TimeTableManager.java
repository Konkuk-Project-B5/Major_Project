package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TimeTableManager {
	
	private static Scanner scan = new Scanner(System.in);
	private User loginUser; // 로그인한 사용자 정보 저장용 User 객체
	private BufferedReader reader; // 파일 입력
	private BufferedWriter writer; // 파일 출력
		
	// 생성자
	public TimeTableManager() {
		
		// 파일 무결성 검사 //
		
		// 검사 후 프로그램 실행, 로그인/회원가입 메뉴 출력
		loginAndRegisterMenu(); 
	}
	
	// 로그인/회원가입 메뉴 출력 메소드
	private void loginAndRegisterMenu() {
		
		System.out.println("[수강신청 프로그램]");
		String input = null; // 사용자 입력 저장
		
		while(true) {
			// 메뉴 출력
			System.out.println("1.회원가입\n2.로그인\n3.종료");
						
			// 사용자 입력
			boolean flag = false; // 올바른 입력인지 확인
			while(!flag) {
				System.out.print("메뉴 입력: ");
				input = scan.nextLine().strip(); // strip(): 앞 뒤 공백 제거
				switch(input) {
				case "1":
				case "회원가입":
				case "2":
				case "로그인":
				case "3":
				case "종료":
				// 올바른 입력인 경우 break
					flag = true;
					break;
				default:
				// 오류 메세지 출력
					System.out.println("입력이 올바르지 않습니다. 다시 입력해주세요.");
					break;
				}
			}
			
			switch(input) {
			case "1":
			case "회원가입":
				// 회원가입 메소드 실행
				register();
				break;
			case "2":
			case "로그인":
				// 로그인 메소드 실행
				login();
				break;
			case "3":
			case "종료":
				// 프로그램 종료
				System.out.println("프로그램을 종료합니다.");
				return;
			}
		}
		
	}
	
	// 로그인 메소드
	private void login() {
		
		// 로그인 //
		
		
		// 사용자로부터 받은 입력값 검사 후 User 객체 생성
		String id = "202211329"; // 테스트
		String password = "a1b2c3"; // 테스트
		loginUser = new User(id, password);
		
		// 학번.txt에서 과목번호 읽고 loginUser의 myLectureList 초기화
		initUserLectureList();		
		
		// 로그인 완료 후 메인 메뉴로 이동
		System.out.println("로그인을 완료하였습니다.");
		mainMenu();
	}
	
	// loginUser의 myLectureList 초기화 메소드
	private void initUserLectureList() {
		try {
			reader = new BufferedReader(new FileReader(loginUser.FILEPATH));
			
			loginUser.myLectureList = new ArrayList<String>();
			String lectureNum = null;
			while((lectureNum = reader.readLine()) != null) {
				loginUser.myLectureList.add(lectureNum);
			}
			
//			//확인용
//			for(String l : loginUser.lectureList)
//				System.out.println(l);
			
			reader.close();
			
		} catch (FileNotFoundException e) {
//			System.out.println(loginUser.FILEPATH+": 파일 존재하지 않음");
			System.exit(0); // 오류 발생시 프로그램 종료
		} catch (IOException e) {
//			System.out.println(loginUser.FILEPATH+": 읽기 실패");
			System.exit(0); // 오류 발생시 프로그램 종료
		}
	}

	// 회원가입 메소드
	private void register() {
		
		// 회원 가입 //
		
		// 학번.txt 파일 생성
		String id = "202211329"; // 테스트
		createIdFile(id);
		
		System.out.println("회원가입을 완료했습니다.");
	}
	
	// 학번.txt 파일 생성 메소드
	private void createIdFile(String id) {
		File file = new File("./"+id+".txt");
		try {
			file.createNewFile(); 
		} catch (IOException e) {
//			System.out.println(id + ": 파일 생성 실패");
			System.exit(0); // 오류 발생시 프로그램 종료
		}
	}

	// 수강신청 및 시간표 조회 (메인 메뉴) 출력 메소드
	private void mainMenu() {
		String input = null;
		
		while(true) {
			System.out.println("[메인 메뉴] 실행할 메뉴를 선택하세요");
			System.out.println("1. 수강신청하기\n2. 시간표조회하기\n3. 로그아웃\n4. 종료하기");
			System.out.print("선택: ");
			
			input = scan.nextLine().strip();
			
			switch(input) {
			case "1":
			case "수강신청하기":
				// 수강 신청 메소드 실행
				signUp();
				break;
			case "2":
			case "시간표조회하기":
				// 시간표 조회 메소드 실행
				showTimeTable();
				break;
			case "3":
			case "로그아웃":
				// 로그아웃
				System.out.println("로그아웃이 완료되었습니다.");
				return;
			case "4":
			case "종료하기":
				// 프로그램 종료
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			default:
				// 오류 메세지 출력
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
				break;
			}
		}
	}
	
	// 시간표 조회 메소드
	
	private void showTimeTable() {
		System.out.println("수강신청내역");
		System.out.printf("%-10s %-20s %-20s", "과목번호", "교과목명", "강의시간");
		System.out.println();
		// User 클래스의 FILEPATH를 사용하여 파일 경로 지정
		String idFilePath = loginUser.FILEPATH;
		// String idFilePath = "202011372.txt";
		
		// 학번.txt 파일 읽은 후 printLectureList로 출력
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

		System.out.println(loginUser.ID);
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

	// 수강신청 메소드
	private void signUp() {
		String input = null;
		
		while(true) {
			
			// 강의 목록 출력 //
			
			System.out.println("\n\n수강신청할 과목번호를 입력해주세요\n※ 'q'를 입력한 경우 메인메뉴로 돌아갑니다.\n\n");
			System.out.print("과목번호 입력 : ");
			input = scan.nextLine().strip();
			
			if(input.equals("q")) {
				System.out.println("메인메뉴로 돌아갑니다.");
				return;
			}
			
			// 문법규칙에 맞는지 검사
			if(!input.matches("\\d{3}")) {
				System.out.println("과목번호는 0과 자연수로만 이루어진 길이가 3인 문자열입니다.");
				continue;
			}
			
			// 중복 시간대 검사, 중복 수강신청, 인원제한 검사 //
			
			// 검사 통과시 break
			break;
		}
		
		// 수강신청한 과목번호 loginUser의 myLectureList에 저장
		loginUser.myLectureList.add(input);
										
		// 학번.txt 파일 업데이트
		updateIdFile();
					
		// 수강신청 완료
		System.out.println("수강신청이 완료되었습니다.");
	}
	
	// 학번.txt 파일 업데이트 메소드
	private void updateIdFile() {
		String content = "";
		for(String lectureNum : loginUser.myLectureList)
			content += lectureNum + "\n";
	
		try {
			writer = new BufferedWriter(new FileWriter(loginUser.FILEPATH));
			writer.write(content);
			writer.close();
		} catch (IOException e) {
//			System.out.println(loginUser.FILEPATH+": 쓰기 실패");
			System.exit(0); // 오류 발생시 프로그램 종료
		}
	}
}