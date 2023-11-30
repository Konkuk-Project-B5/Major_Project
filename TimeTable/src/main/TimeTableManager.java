package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TimeTableManager {
	
	private static Scanner scan = new Scanner(System.in);
	private User loginUser; // 로그인한 사용자 정보 저장용 User 객체
	private BufferedReader reader; // 학번.txt 파일 입력
	private BufferedWriter writer; // 학번.txt 파일 출력
	private myFileReader filereader; // filereader 객체

	// 2차 요구사항 - 현재 날짜 추가
	private LocalDate date;
	
	// 생성자
	public TimeTableManager() {
		// 2차 요구사항 - 현재 날짜 초기화
		date = LocalDate.now();
		// System.out.println(date.getYear()+" "+date.getMonthValue());
		
		// 파일 무결성 검사
		try {
			FileInputStream input=new FileInputStream("./lecture_list.txt");
	        InputStreamReader reader=new InputStreamReader(input,"MS949");
	        
	        FileInputStream roomFile=new FileInputStream("./lecture_room.txt");
	        InputStreamReader roomReader=new InputStreamReader(roomFile,"MS949");
	        BufferedReader roomBufferReader = new BufferedReader(roomReader);
	        String roomSize = "";
			Integer[] roomMaxSize = new Integer[100];
			String[] roomInfo = new String[100];
			int sizeCount = 0;
	        while ((roomSize = roomBufferReader.readLine()) != null) { 
	        	roomMaxSize[sizeCount] = Integer.parseInt(roomSize.split(" ")[1]);
	        	roomInfo[sizeCount] = roomSize.split(" ")[0];
	        	//System.out.println(roomInfo[sizeCount]+" "+roomMaxSize[sizeCount]);
	        	sizeCount++;
	        }
	        roomBufferReader.close();
		    BufferedReader lecture_list_bufReader = new BufferedReader(reader);
		    String line = "";
		    boolean conditionFlag = false;
			while ((line = lecture_list_bufReader.readLine()) != null) {
				//System.out.println(line);
				boolean result =
			line.matches(
				"^\\d{3}\s[가-힣]+[0-9]*\s[가-힣]{3}\s[월|화|수|목|금]\s\\d{2}\s\\d{2}\s\\d{3}((\s\s\s\s\s)|(\s[월|화|수|목|금]\s\\d{2}\s\\d{2}\s\\d{3}\s))\\d{2}\s\\d{2}\s\\d{1}$"
			);
				
				String[] lectureInfo = line.split(" ");
				//System.out.println(lectureInfo[lectureInfo.length-2]+" "+lectureInfo[6]+" "+lectureInfo[10]); //수강신청 제한인원, 강의실
			    for(int i =0; i<sizeCount; i++) {
			    	if(lectureInfo[6].equals(roomInfo[i])) {
			    		if(Integer.parseInt(lectureInfo[lectureInfo.length-2]) >  roomMaxSize[i]) {
			    			System.out.println(lectureInfo[1]+"의 수강신청제한인원이 "+lectureInfo[6]+" 강의실 최대수용인원을 넘습니다.");
			    			conditionFlag = true;
			    		}
			    	}
			    	else if(lectureInfo[10].equals(roomInfo[i])) {
			    		if(Integer.parseInt(lectureInfo[lectureInfo.length-2]) >  roomMaxSize[i]) {
			    			System.out.println(lectureInfo[1]+"의 수강신청제한인원이 "+lectureInfo[10]+" 강의실 최대수용인원을 넘습니다.");
			    			conditionFlag = true;
			    		}
			    	}
			    }
			    
				if (result == false) { System.out.println("오류 :lecture 데이터 파일이 손상되었습니다.");
			System.out.println("프로그램을 종료합니다."); System.exit(0); } }
			if(conditionFlag == true) {
		    	System.out.println("프로그램을 종료합니다."); System.exit(0);
		    }
		    lecture_list_bufReader.close();
		    File user_file = new File("./user.txt");
		    FileReader user_filereader = new FileReader(user_file);
		    BufferedReader user_bufReader = new BufferedReader(user_filereader);
		    line = "";
		    while ((line = user_bufReader.readLine()) != null) {
		        boolean result = line.matches("(201[0-9])|(202[0-3])[0-9]{5}\s[a-z0-9]{7,13}");
		        if (result == false) {        
		        	System.out.println("오류 : 데이터 파일이 손상되었습니다.");
				    System.out.println("프로그램을 종료합니다.");
				    System.exit(0);
		        }
		    }		    
		    user_bufReader.close();
		} catch (FileNotFoundException e) {
		    System.out.println("오류 : 올바른 경로에 데이터 파일이 존재하지 않습니다.");
		    System.out.println("프로그램을 종료합니다.");
		    System.exit(0);
		} catch (IOException e) {
		    System.out.println(e);
		    System.exit(0);
		}

		// 객체 초기화
		filereader = new myFileReader("./lecture_list.txt", "./lecturer.txt", "./lecture_room.txt");

		// 검사 후 프로그램 실행, 로그인/회원가입 메뉴 출력
		System.out.println("[수강신청 프로그램]");
		screen();
		menuinput();
	}

	// 로그인/회원가입 메뉴 출력 메소드
	private void screen() {
        System.out.println("1. 회원가입");
        System.out.println("2. 로그인");
        System.out.println("3. 종료");
        System.out.print("메뉴 입력: ");
    }
	
	// 로그인/회원가입 메뉴 입력 메소드
	private void menuinput() {
		String input = scan.nextLine().replaceAll("\\s", "");
		if (input.equals("1")||input.equals("회원가입"))
			signup();
		else if (input.equals("2")||input.equals("로그인"))
			signin();
		else if (input.equals("3")||input.equals("종료")) {
			System.out.println("프로그램을 종료합니다.");
			System.exit(0);
		}
		else {
			System.out.println("입력이 올바르지 않습니다. 다시입력해주세요");
			System.out.print("메뉴 입력: ");
			menuinput();
		}
	}
	

	// 회원가입 시 입력한 Id가 이미 존재하는지 검사 메소드
	private boolean isID(String id) {
		try {
			File file = new File("./user.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line="";
			while ((line=br.readLine())!= null) {
				String[] idpw = line.split(" ");
				if (idpw[0].equals(id)) {
					br.close();
					return true;
				}
			}
		}catch(Exception e) {
			System.out.println("파일 읽기 실패");
		}
		return false;
	}
	
	// 로그인 시 입력 Id와 Pw가 매치되는지 검사 메소드
	private boolean isIdPwMatch(String id, String pw) {
		try {
			File file = new File("./user.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line="";
			while ((line=br.readLine())!= null) {
				String[] idpw = line.split(" ");
				if (idpw[0].equals(id)&&idpw[1].equals(pw)) {
					br.close();
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println("파일 읽기 실패");
		}
		return false;
	}
	
	// 회원가입 메소드
	private void signup() {
		System.out.println("회원가입을 시작합니다.");
		System.out.print("학번 입력: ");
		String inputid = scan.nextLine();
		if (!(Pattern.matches("(201[0-9]|202[0-3])([0-9]{5})", inputid))) {
			System.out.println("학번은 201000000이상 202399999 이하의 자연수입니다.");
			screen();
			menuinput();
			return;
		}
		if (isID(inputid)) {
			System.out.println("이미 등록된 학번입니다.");
			screen();
			menuinput();
			return;
		}
		System.out.print("비밀번호 입력: ");
		String inputpw = scan.nextLine();
		if (!(Pattern.matches("[a-z0-9]{7,13}", inputpw))) {
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
		} catch (Exception e) {
			System.out.println("파일 쓰기 실패");
		}
		createIdFile(inputid);
		screen();
		menuinput();
		return;
	}
	
	// 로그인 메소드
	private void signin() {
		System.out.println("로그인을 시작합니다. 학번과 비밀번호를 입력해주세요.");
		System.out.println("형식: (<횡공백류열0><학번><횡공백류열1><비밀번호><횡공백류열0>)");
		System.out.print("입력: ");
		String input = scan.nextLine().trim();
		String inputidpw[] = input.split("\\s+");
		if (inputidpw.length != 2 || !(Pattern.matches("(201[0-9]|202[0-3])([0-9]{5})", inputidpw[0])) || !(Pattern.matches("[a-z0-9]{7,13}", inputidpw[1]))) {
			System.out.println("학번과 비밀번호를 확인해주세요.");
			screen();
			menuinput();
			return;
		}
		if (isIdPwMatch(inputidpw[0],inputidpw[1])) {
			try {
				File student_file = new File("./"+inputidpw[0]+".txt");
			    // 입력 스트림 생성
			    FileReader filereader = new FileReader(student_file);
			    // 입력 버퍼 생성
			    BufferedReader student_bufReader = new BufferedReader(filereader);
			    String line = "";
			    
				while ((line = student_bufReader.readLine()) != null) {
					boolean result = line.matches("^\\d{4}\s\\d{3}\s[가-힣]+[0-9]*\s\\d{1}\s([ABCDF][+]*|X)$"); 
					if (result == false) { 
						System.out.println("오류 : 데이터 파일이 손상되었습니다.");
						System.out.println("프로그램을 종료합니다."); 
						System.exit(0); 
					} 
				}
				 
			    student_bufReader.close();	
			}
		    catch (FileNotFoundException e) {
			    System.out.println("오류 : 올바른 경로에 데이터 파일이 존재하지 않습니다.");
			    System.out.println("프로그램을 종료합니다.");
			    System.exit(0);
			} catch (IOException e) {
			    System.out.println(e);
			    System.exit(0);
			}
			loginUser = new User(inputidpw[0], inputidpw[1]);
			System.out.println("로그인을 완료했습니다.");
			initUserLectureList();
			mainMenu();
		}
		else {
			System.out.println("학번과 비밀번호를 확인해주세요.");
			screen();
			menuinput();
			return;
		}
	}

	// loginUser의 myLectureList 초기화 메소드
	// 2차 요구사항 - loginUser의 myLectureList와 pastLectureList 초기화
	private void initUserLectureList() {
		try {
			reader = new BufferedReader(new java.io.FileReader(loginUser.FILEPATH));

			loginUser.myLectureList = new ArrayList<Lecture>();
			loginUser.pastLectureList = new ArrayList<Lecture>();
			loginUser.pastLectureListYear = new ArrayList<Integer>();
			String line = null;
			while ((line = reader.readLine()) != null) {
				
				// 2차 요구사항 - 학번.txt에 저장된 강의 정보 가져옴
				// 수강 연도, 과목 번호, 강의명, 학점, 등급 
				String[] lectureInfo = line.split(" ");
					
				// 수강 연도가 현재 연도와 같으면  
				if (lectureInfo[0].equals(Integer.toString(date.getYear()))) {
					
					// filereader로부터 lecture 객체 가져옴
					Lecture lecture = filereader.lecturelist.get(lectureInfo[1]);
					
					// 2차 요구사항 - lecture 객체의 grade 초기화
					lecture.grade = lectureInfo[4];
					
					// loginUser의 myLectureList에 lecture 추가
					loginUser.myLectureList.add(lecture);
					
					// loginUser의 myCredit에 lecture의 credit 추가
					loginUser.myCredit += lecture.getLectureCredit();
				} else { // 수강 했던 과목이면
					
					// 수강했던 과목에 대한 Lecture 객체 생성
					Lecture lecture = new Lecture(lectureInfo[1], lectureInfo[2], lectureInfo[3], lectureInfo[4]);
		
					// pastLectureList에 추가
					loginUser.pastLectureList.add(lecture);
					loginUser.pastLectureListYear.add(Integer.parseInt(lectureInfo[0])); // 수강했던 강의 연도 저장
				}
			}

			reader.close();
		} catch (FileNotFoundException e) {
//			System.out.println(loginUser.FILEPATH+": 파일 존재하지 않음");
			System.exit(0); // 오류 발생시 프로그램 종료
		} catch (IOException e) {
//			System.out.println(loginUser.FILEPATH+": 읽기 실패");
			System.exit(0); // 오류 발생시 프로그램 종료
		}
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
			System.out.println("\n[메인 메뉴] 실행할 메뉴를 선택하세요");
			System.out.println("1. 수강신청하기\n2. 시간표조회하기\n3. 수강기록조회하기\n4. 로그아웃\n5. 종료하기");
			System.out.print("선택: ");

			input = scan.nextLine().strip();

			switch (input) {
				case "1":
				case "수강신청하기":
					// 수강 신청 메소드 실행
					registerForLecture();
					break;
				case "2":
				case "시간표조회하기":
					// 시간표 조회 메소드 실행
					showTimeTable();
					break;
				// 2차 요구 사항 - 수강했던 과목 조회
				case "3":
				case "수강기록조회하기":
					// 수강기록 조회 메소드 실행
					showPastTimeTable();
					break;
				case "4":
				case "로그아웃":
					// 로그아웃
					System.out.println("로그아웃이 완료되었습니다.");
					screen();
					menuinput();
					return;
				case "5":
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
	
	// 수강기록 조회 메소드
	private void showPastTimeTable() {
		// 학점 받았던 과목 출력
		String content="";
		int count=0;
		for (Lecture lecture : loginUser.pastLectureList) {
			content += loginUser.pastLectureListYear.get(count) + " " + lecture.lectureCode + " " + lecture.lectureName + " " + lecture.lectureCredit + " " + lecture.grade + "\n";
			count++;
		}
		System.out.println(content);
		System.out.println("입력이 들어오는 경우 메인 메뉴로 돌아갑니다.\n");
		scan.nextLine();
	}

	// 시간표 조회 메소드
	private void showTimeTable() {
		String input = null;
		
		while (true) {
			if (!loginUser.printMyList()) {
				System.out.println();
				System.out.println("수강신청한 교과목이 없습니다.");
				System.out.println();
				while (true) {
					System.out.println("※ 'q'를 입력한 경우 메인메뉴로 돌아갑니다.");
					System.out.println();
					input = scan.nextLine().strip();
					if (input.equals("q")) {
						System.out.println("메인메뉴로 돌아갑니다.");
						System.out.println();
						return;
					}
					System.out.println("비정상입력입니다.");
				}
			}

			// 1차 요구사항 - 수강 철회 기능 추가
			System.out.println("\n\n수강을 철회할 과목번호를 입력해주세요. 현재 수강 학점은 "+loginUser.myCredit+"학점입니다. (최대 수강 학점: "+User.MAX_CREDIT+"학점)\n※ 'q'를 입력한 경우 메인메뉴로 돌아갑니다.\n\n");
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
			
			//2. 학번.txt에 있는 번호인지
			boolean flag = false;
			for (Lecture lec : loginUser.myLectureList) {
				if (lec.lectureCode.equals(input)) {
					flag = true;
					break;
				}
			}
			
			if (!flag) {
				System.out.println("수강신청내역에 입력한 강의가 존재하지 않습니다.");
				continue;
			}
			
			// 검사 통과 
	
			// 입력한 과목 loginUser의 myLectureList에서 삭제
			loginUser.myLectureList.remove(filereader.lecturelist.get(input));
						
			// loginUser의 myCredit 수강철회한 과목의 학점만큼 감소 
			loginUser.myCredit -= filereader.lecturelist.get(input).getLectureCredit();
						
			// 학번.txt 파일 업데이트
			updateIdFile();

			//lecturelist의 lecture의 수강신청 인원 업데이트 - 수강신청 인원 감소
			filereader.lecturelist.get(input).minusLectureCnum();

			//lecture_list 현재 수강신청 인원 업데이트 - 수강신청 인원 감소
			filereader.updateLectureFile2(input);

			// 수강철회 완료
			System.out.println("수강철회가 완료되었습니다.");
//			System.out.println("학점: "+loginUser.myCredit);
		}
	}

	// 수강신청 메소드
	private void registerForLecture() {
		String input = null;

		while (true) {

			// 강의 목록 출력
			filereader.printLectureList();

			System.out.printf("\n\n수강신청할 과목번호를 입력해주세요(수강학점 :%d/18)", loginUser.myCredit);
			System.out.println("※ 'q'를 입력한 경우 메인메뉴로 돌아갑니다.\n\n");
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
			if (loginUser.myLectureList.contains(inputLecture)) {
				System.out.println("동일한 강의를 추가했습니다.");
				continue;
			} else {
				// 1차 요구 사항 - 분반 검사 추가 
				// 분반 == 수강신청내역에 없음 && 수강신청내역에 동일한 이름의 과목 존재
				boolean flag = true;
				for (Lecture lec : loginUser.myLectureList) {
					if (lec.lectureName.equals(inputLecture.lectureName)) {
						flag = false;
						break;
					}
				}

				if (!flag) {
					// 분반 신청시 메세지 출력
					System.out.println("이미 수강 중인 강의의 다른 분반을 추가했습니다.");
					continue;
				}
			}

			//3. 중복 시간대 검사
			boolean flag = true;
			for (Lecture lec : loginUser.myLectureList) {
				boolean flag_inputDay1_lecDay1 = inputLecture.getLectureDay1().equals(lec.getLectureDay1());
				boolean flag_inputDay1_lecDay2 = false;
				boolean flag_inputDay2_lecDay1 = false;
				boolean flag_inputDay2_lecDay2 = false;

				if (!lec.getLectureDay2().equals("")) {
					flag_inputDay1_lecDay2 = inputLecture.getLectureDay1().equals(lec.getLectureDay2());
				}
				if (!inputLecture.getLectureDay2().equals("")) {
					flag_inputDay2_lecDay1 = inputLecture.getLectureDay2().equals(lec.getLectureDay1());
				}
				if (!lec.getLectureDay2().equals("") || !inputLecture.getLectureDay2().equals("")) {
					flag_inputDay2_lecDay2 = inputLecture.getLectureDay2().equals(lec.getLectureDay2());
				}
				if (flag_inputDay1_lecDay1)  //Day1, Day1 같은 요일
				{
					if (inputLecture.int_getLectureDay1Stime() > lec.int_getLectureDay1Stime()) {  //이후 교시에 추가될 때
						if (inputLecture.int_getLectureDay1Stime() < lec.int_getLectureDay1Otime() + 1) {  //기존 01-02면 03부터 시작하는 수업들 추가 가능
							flag = false;
//							System.out.println("a");
							break;
						}
					} else if (inputLecture.int_getLectureDay1Stime() < lec.int_getLectureDay1Stime()) {  //이전 교시에 추가될 때
						if (inputLecture.int_getLectureDay1Otime() > lec.int_getLectureDay1Stime() - 1) {  //기존 03-04면 02까지 끝나는 수업 추가 가능
							flag = false;
//							System.out.println("a2");
							break;
						}
					} else {  //같은 교시에 시작
						flag = false;
//						System.out.println("a3");
						break;
					}
				}

				if (flag_inputDay1_lecDay2)  //Day1, Day2 같은 요일
				{
					if (inputLecture.int_getLectureDay1Stime() > lec.int_getLectureDay2Stime()) {  //이후 교시에 추가될 때
						if (inputLecture.int_getLectureDay1Stime() < lec.int_getLectureDay2Otime() + 1) {  //기존 01-02면 03부터 시작하는 수업들 추가 가능
							flag = false;
//							System.out.println("b");
							break;
						}
					} else if (inputLecture.int_getLectureDay1Stime() < lec.int_getLectureDay2Stime()) {  //이전 교시에 추가될 때
						if (inputLecture.int_getLectureDay1Otime() > lec.int_getLectureDay2Stime() - 1) {  //기존 03-04면 02까지 끝나는 수업 추가 가능
							flag = false;
//							System.out.println("b2");
							break;
						}
					} else {  //같은 교시에 시작
						flag = false;
//						System.out.println("b3");
						break;
					}
				}

				if (flag_inputDay2_lecDay1)  //Day2, Day1 같은 요일
				{
					if (inputLecture.int_getLectureDay2Stime() > lec.int_getLectureDay1Stime()) {  //이후 교시에 추가될 때
						if (inputLecture.int_getLectureDay2Stime() < lec.int_getLectureDay2Otime() + 1) {  //기존 01-02면 03부터 시작하는 수업들 추가 가능
							flag = false;
//							System.out.println("c");
							break;
						}
					} else if (inputLecture.int_getLectureDay2Stime() < lec.int_getLectureDay1Stime()) {  //이전 교시에 추가될 때
						if (inputLecture.int_getLectureDay2Otime() > lec.int_getLectureDay1Stime() - 1) {  //기존 03-04면 02까지 끝나는 수업 추가 가능
							flag = false;
//							System.out.println("c2");
							break;
						}
					} else {  //같은 교시에 시작
						flag = false;
//						System.out.println("c3");
						break;
					}
				}
				if (flag_inputDay2_lecDay2)  //Day2, Day2 같은 요일
				{
					if (inputLecture.int_getLectureDay2Stime() > lec.int_getLectureDay2Stime()) {  //이후 교시에 추가될 때
						if (inputLecture.int_getLectureDay2Stime() < lec.int_getLectureDay2Otime() + 1) {  //기존 01-02면 03부터 시작하는 수업들 추가 가능
							flag = false;
//							System.out.println("d");
							break;
						}
					} else if (inputLecture.int_getLectureDay2Stime() < lec.int_getLectureDay2Stime()) {  //이전 교시에 추가될 때
						if (inputLecture.int_getLectureDay2Otime() > lec.int_getLectureDay2Stime() - 1) {  //기존 03-04면 02까지 끝나는 수업 추가 가능
							flag = false;
//							System.out.println("d2");
							break;
						}
					} else {  //같은 교시에 시작
						flag = false;
//						System.out.println("d3");
						break;
					}
				}

				if (!flag) {
					System.out.println("기존 시간표와 강의 시간이 겹칩니다.");
					continue;
				}
			}


				//4. 인원제한 검사
				if (inputLecture.getLectureCnum() >= inputLecture.getLectureMnum()) {
//				System.out.println("과목 코드: " + inputLecture.getLectureCode());
//				System.out.println("현인원: " + inputLecture.getLectureCnum() + " " + inputLecture.lectureCnum + " / Max:" + inputLecture.getLectureMnum());
					System.out.println("여석이 없습니다.");
					continue;
				}

				//5. 1차 요구사항 - 최대 학점 한도 검사
				if (loginUser.myCredit + inputLecture.getLectureCredit() > User.MAX_CREDIT) {
					// 최대 학점 한도 초과시 메세지 출력
					System.out.println(User.MAX_CREDIT + "학점을 초과해 신청할 수 없습니다.");
					continue;
				}

				//6. 2차 요구사항 - A학점 이상 재수강 금지
				Boolean flag_grade = true;
				for (Lecture l : loginUser.pastLectureList) {
					if (l.lectureName.equals(inputLecture.lectureName)) {
						if (l.getGrade().contains("A")) {
							flag_grade = false;
							break;
						}
					}
				}
				
				if (!flag_grade) {
					System.out.println("A학점 이상 받았던 과목은 재수강할 수 없습니다.");
					continue;
				}

				// 검사 통과시 break
				break;
			}

			// 2차 요구사항 - 수강신청한 과목의 등급 초기화
			filereader.lecturelist.get(input).grade = "X";

			// 수강신청한 과목 loginUser의 myLectureList에 저장
			loginUser.myLectureList.add(filereader.lecturelist.get(input));

			// loginUser의 myCredit 수강신청한 과목의 학점만큼 증가
			loginUser.myCredit += filereader.lecturelist.get(input).getLectureCredit();

			// 학번.txt 파일 업데이트
			updateIdFile();

			// lecturelist의 lecture의 수강신청인원 업데이트
			filereader.lecturelist.get(input).plusLectureCnum();

			// lecture_list 현재 수강신청 인원 업데이트
			filereader.updateLectureFile(input);

			// 수강신청 완료
			System.out.println("수강신청이 완료되었습니다.");
//		System.out.println("학점: "+loginUser.myCredit);
		}
	
	// 학번.txt 파일 업데이트 메소드
	private void updateIdFile() {
		String content = "";
		int count = 0;
		for (Lecture lecture : loginUser.myLectureList)
			content += date.getYear() + " " + lecture.lectureCode + " " + lecture.lectureName + " " + lecture.lectureCredit + " " + lecture.grade + "\n";
		
		// 2차 요구사항 - 수강했던 강의 추가
		for (Lecture lecture : loginUser.pastLectureList) {
			content += loginUser.pastLectureListYear.get(count) + " " + lecture.lectureCode + " " + lecture.lectureName + " " + lecture.lectureCredit + " " + lecture.grade + "\n";
			count++;
			// 수강했던 연도 그대로 변경
		}
		
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