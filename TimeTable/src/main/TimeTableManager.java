package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TimeTableManager {

	private static Scanner scan = new Scanner(System.in);
	private User loginUser; // 로그인한 사용자 정보 저장용 User 객체
	private BufferedReader reader; // 학번.txt 파일 입력
	private BufferedWriter writer; // 학번.txt 파일 출력
	private LectureFileReader filereader; // filereader 객체

	// 생성자
	public TimeTableManager() {

		// 파일 무결성 검사 //

		// filereader 객체 초기화
		filereader = new LectureFileReader("./lecture_list.txt");

		// 검사 후 프로그램 실행, 로그인/회원가입 메뉴 출력
		loginAndRegisterMenu();
	}

	// 로그인/회원가입 메뉴 출력 메소드
	private void loginAndRegisterMenu() {

		System.out.println("[수강신청 프로그램]");
		String input = null; // 사용자 입력 저장

		while (true) {
			// 메뉴 출력
			System.out.println("1.회원가입\n2.로그인\n3.종료");

			// 사용자 입력
			boolean flag = false; // 올바른 입력인지 확인
			while (!flag) {
				System.out.print("메뉴 입력: ");
				input = scan.nextLine().strip(); // strip(): 앞 뒤 공백 제거
				switch (input) {
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

			switch (input) {
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
			reader = new BufferedReader(new java.io.FileReader(loginUser.FILEPATH));

			loginUser.myLectureList = new ArrayList<Lecture>();
			String lectureNum = null;
			while ((lectureNum = reader.readLine()) != null) {
				loginUser.myLectureList.add(filereader.lecturelist.get(lectureNum));
			}

//			//확인용
//			for(Lecture l : loginUser.myLectureList)
//				System.out.println(l.lectureName);

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
		File file = new File("./" + id + ".txt");
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

		while (true) {
			System.out.println("[메인 메뉴] 실행할 메뉴를 선택하세요");
			System.out.println("1. 수강신청하기\n2. 시간표조회하기\n3. 로그아웃\n4. 종료하기");
			System.out.print("선택: ");

			input = scan.nextLine().strip();

			switch (input) {
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

	}

	// 수강신청 메소드
	private void signUp() {
		String input = null;

		while (true) {

			// 강의 목록 출력 //
			filereader.printLectureList();

			System.out.println("\n\n수강신청할 과목번호를 입력해주세요\n※ 'q'를 입력한 경우 메인메뉴로 돌아갑니다.\n\n");
			System.out.print("과목번호 입력 : ");
			input = scan.nextLine().strip();

			if (input.equals("q")) {
				System.out.println("메인메뉴로 돌아갑니다.");
				return;
			}

			// 문법규칙에 맞는지 검사
			if (!input.matches("\\d{3}")) {
				System.out.println("과목번호는 0과 자연수로만 이루어진 길이가 3인 문자열입니다.");
				continue;
			}

			//의미규칙 검사
			//1. lecture_list.txt에 있는 번호인지
			if (!filereader.lecturelist.containsKey(input)) {
				System.out.println("강의가 존재하지 않습니다.");
				continue;
			}

			//2. 동일한 강의 중복 추가 검사
			Lecture inputLecture = filereader.lecturelist.get(input);
			if(loginUser.myLectureList.contains(inputLecture)) {
				System.out.println("동일한 강의를 추가했습니다.");
				continue;
			}

			//3. 중복 시간대 검사
			boolean flag = true;
			for(Lecture lec : loginUser.myLectureList) {

				if (inputLecture.getLectureDay1().equals(lec.getLectureDay1()) ||
						inputLecture.getLectureDay1().equals(lec.getLectureDay2()) ||
						inputLecture.getLectureDay2().equals(lec.getLectureDay1()) ||
						inputLecture.getLectureDay2().equals(lec.getLectureDay2()))  //같은 요일
				{
					if(inputLecture.getLectureStime() > lec.getLectureStime()) {  //이후 교시에 추가될 때
						if(inputLecture.getLectureStime() < lec.getLectureOtime() + 1) {  //기존 01-02면 03부터 시작하는 수업들 추가 가능
							flag = false;
							break;
						}
					} else if (inputLecture.getLectureStime() < lec.getLectureStime()) {  //이전 교시에 추가될 때
						if(inputLecture.getLectureOtime() > lec.getLectureStime() - 1) {  //기존 03-04면 02까지 끝나는 수업 추가 가능
							flag = false;
							break;
						}
					} else {  //같은 교시에 시작
						flag = false;
						break;
					}
				}
			}
			if(!flag) {
				System.out.println("기존 시간표와 강의 시간이 겹칩니다.");
				continue;
			}

			//4. 인원제한 검사
			if(inputLecture.getLectureCnum() >= inputLecture.getLectureMnum()) {
//				System.out.println("과목 코드: " + inputLecture.getLectureCode());
//				System.out.println("현인원: " + inputLecture.getLectureCnum() + " " + inputLecture.lectureCnum + " / Max:" + inputLecture.getLectureMnum());
				System.out.println("여석이 없습니다.");
				continue;
			}

			// 검사 통과시 break
			break;
		}

		// 수강신청한 과목 loginUser의 myLectureList에 저장
		loginUser.myLectureList.add(filereader.lecturelist.get(input));

		// 학번.txt 파일 업데이트
		updateIdFile();

		//lecturelist의 lecture의 수강신청인원 업데이트
		filereader.lecturelist.get(input).plusLectureCnum();

		//lecture_list 현재 수강신청 인원 업데이트
		filereader.updateLectureFile(input);

		// 수강신청 완료
		System.out.println("수강신청이 완료되었습니다.");
	}
	

	// 학번.txt 파일 업데이트 메소드
	private void updateIdFile() {
		String content = "";
		for (Lecture lecture : loginUser.myLectureList)
			content += lecture.lectureCode + "\n";

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