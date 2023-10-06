package main;

import java.util.Scanner;

public class TimeTableManager {
	
	private static Scanner scan = new Scanner(System.in);
	private User loginUser; // 로그인한 사용자 정보 저장용 User 객체
		
	// 생성자
	public TimeTableManager() {
		
		// 파일 무결성 검사 //
		
		initSystem(); // 검사 후 프로그램 실행
	}

	private void initSystem() {
		loginAndRegisterMenu();	
	}
	
	// 로그인/회원가입 메뉴
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
		
		
		// 로그인 완료 후 메인 메뉴로 이동
		System.out.println("로그인을 완료하였습니다.");
		mainMenu();
	}

	// 회원가입 메소드
	private void register() {
		
		// 회원 가입 //
		
		System.out.println("회원가입을 완료했습니다.");
	}
	
	// 수강신청 및 시간표 조회 (메인 메뉴)
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
		// TODO Auto-generated method stub
		
	}

	// 수강신청 메소드
	private void signUp() {
		// TODO Auto-generated method stub
		
	}
}
