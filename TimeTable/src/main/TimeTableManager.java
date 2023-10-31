package main;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*
 *  1차 요구사항 - 분반
 *  lecture_list.txt에 분반 2개인 과목, 분반 3개인 과목 추가
 *  
 *  1차 요구사항 - 최대 학점 한도 및 강의 학점 추가
 *  lecture_list.txt에 학점 추가 후 파일에서 읽어와 Lecture 객체의 lectureCredit 초기화
 *  학점 추가에 따른 메소드 수정 필요함
 *  학점 추가에 따른 데이터 파일 정규식 수정 필요함
 *  
 *  1차 요구사항 - 수강 철회
 *  수강 철회된 강의의 수강신청 인원 감소에 대한 업데이트 가능하도록 메소드 추가 또는 수정 필요함 
 *  
 *  이외에 추가 또는 수정해야 할 사항
 *  회원가입 및 로그인 관련 메소드 TimeTableManager에 추가
 *  파일 무결성 검사 관련 메소드 추가
 *  
 *  table print branch에 작성된 시간표 출력 메소드가 LectureFileReader가 변경되기 전에 작성된 메소드라 수정이 필요합니다. 시간표 조회하기 메뉴에서 수강 철회를 진행하므로 
 *  시간표를 출력할 때 학번.txt 파일에 접근하는 것 대신 User 객체의 myLectureList로부터 강의 정보 읽어와 출력하도록 변경하는 게 좋을 것 같습니다.
 */

public class TimeTableManager {

	private static Scanner scan = new Scanner(System.in);
	private User loginUser; // 로그인한 사용자 정보 저장용 User 객체
	private BufferedReader reader; // 학번.txt 파일 입력
	private BufferedWriter writer; // 학번.txt 파일 출력
	private LectureFileReader filereader; // filereader 객체

	// 생성자
	public TimeTableManager() {

		try{
		        //파일 객체 생성
		        File lecture_list_file = new File("./lecture_list.txt");
		        //입력 스트림 생성
		        FileReader filereader = new FileReader(lecture_list_file);
		        //입력 버퍼 생성
		        BufferedReader lecture_list_bufReader = new BufferedReader(filereader);
		        String line = "";
		        while ((line = lecture_list_bufReader.readLine()) != null) {
		            boolean result = line.matches("^\\d{3}\s[가-힣]+\s(([월|화|수|목|금]{1}\s\s)|((월|화|수|목|금){1}\s){2})\\d{2}\s\\d{2}\s\\d{2}\s\\d{2}$");
		            if (result == false) {
		            	System.out.println("오류 : 데이터 파일이 손상되었습니다.");
		            	System.out.println("프로그램을 종료합니다.");
				       	System.exit(0);
		            }
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
		        } catch(IOException e) {
		            System.out.println(e);
		        	System.exit(0);
		        }

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
				
				// filereader로부터 lecture 객체 가져옴
				Lecture lecture = filereader.lecturelist.get(lectureNum);
				
				// loginUser의 myLectureList에 lecture 추가
				loginUser.myLectureList.add(lecture);
				
				// 1차 요구사항 - loginUser의 myCredit에 lecture의 credit 추가
				loginUser.myCredit += lecture.getLectureCredit();
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
					registerForLecture();
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
		String input = null;
		
		
		while(true) {
			// 수강신청내역 출력 //
			 System.out.println("수강신청내역");
			    // 과목번호 순으로 정렬
			    Collections.sort(loginUser.myLectureList);
			    // 수강신청 안한 경우
			    if (loginUser.myLectureList.isEmpty()) {
			        System.out.println("아직 수강신청한 강의가 없습니다.");
			    } else {
			    	System.out.println("과목번호\t\t교과목명\t\t학점\t강의시간");
			        for (Lecture lecture : loginUser.myLectureList) {
			        	String time; // 강의 시간 정보
			        	String LectureName; // 강의 이름, 10글자로 포맷팅
			        	LectureName = String.format("%-10s", lecture.getLectureName()); 
			        	
			        	// 강의 시간 정보 처리
			        	if(lecture.lectureDay2.isEmpty()) {
			        		time = String.format("%s %s-%s", lecture.lectureDay1, lecture.lectureStime, lecture.lectureOtime);
			            } else {// 요일 두개
							 time = String.format("%s %s-%s, %s %s-%s", lecture.lectureDay1, lecture.lectureStime, lecture.lectureOtime, 
									lecture.lectureDay2, lecture.lectureStime, lecture.lectureOtime);
			            }
			        	// 시간표 출력
			            System.out.println(lecture.getLectureCode() + "\t\t" +
			                    LectureName + "\t" + 
			                    lecture.getLectureCredit() + "\t" + time);
			            
			        }
			        System.out.println();
			        System.out.println(loginUser.id); // 학번 출력
			    }
			
			
			// 1차 요구사항 - 수강 철회 기능 추가
			
			System.out.println("\n수강을 철회할 과목번호를 입력해주세요. 현재 수강 학점은 "+loginUser.myCredit+"학점입니다. (최대 수강 학점: "+User.MAX_CREDIT+"학점)\n※ 'q'를 입력한 경우 메인메뉴로 돌아갑니다.\n\n");
			
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
			
			//lecturelist의 lecture의 수강신청인원 업데이트
			filereader.lecturelist.get(input).minusLectureCnum();

			//lecture_list 현재 수강신청 인원 업데이트
			filereader.updateLectureFile2(input);
=======
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/*
 *  1차 요구사항 - 분반
 *  lecture_list.txt에 분반 2개인 과목, 분반 3개인 과목 추가
 *  
 *  1차 요구사항 - 최대 학점 한도 및 강의 학점 추가
 *  lecture_list.txt에 학점 추가 후 파일에서 읽어와 Lecture 객체의 lectureCredit 초기화
 *  학점 추가에 따른 메소드 수정 필요함
 *  학점 추가에 따른 데이터 파일 정규식 수정 필요함
 *  
 *  1차 요구사항 - 수강 철회
 *  수강 철회된 강의의 수강신청 인원 감소에 대한 업데이트 가능하도록 메소드 추가 또는 수정 필요함 
 *  
 *  이외에 추가 또는 수정해야 할 사항
 *  회원가입 및 로그인 관련 메소드 TimeTableManager에 추가
 *  파일 무결성 검사 관련 메소드 추가
 *  
 *  table print branch에 작성된 시간표 출력 메소드가 LectureFileReader가 변경되기 전에 작성된 메소드라 수정이 필요합니다. 시간표 조회하기 메뉴에서 수강 철회를 진행하므로 
 *  시간표를 출력할 때 학번.txt 파일에 접근하는 것 대신 User 객체의 myLectureList로부터 강의 정보 읽어와 출력하도록 변경하는 게 좋을 것 같습니다.
 */

public class TimeTableManager {

	private static Scanner scan = new Scanner(System.in);
	private User loginUser; // 로그인한 사용자 정보 저장용 User 객체
	private BufferedReader reader; // 학번.txt 파일 입력
	private BufferedWriter writer; // 학번.txt 파일 출력
	private LectureFileReader filereader; // filereader 객체

	// 생성자
	public TimeTableManager() {

		try{
		        //파일 객체 생성
		        File lecture_list_file = new File("./lecture_list.txt");
		        //입력 스트림 생성
		        FileReader filereader = new FileReader(lecture_list_file);
		        //입력 버퍼 생성
		        BufferedReader lecture_list_bufReader = new BufferedReader(filereader);
		        String line = "";
		        while ((line = lecture_list_bufReader.readLine()) != null) {
		            boolean result = line.matches("^\\d{3}\s[가-힣]+\s(([월|화|수|목|금]{1}\s\s)|((월|화|수|목|금){1}\s){2})\\d{2}\s\\d{2}\s\\d{2}\s\\d{2}$");
		            if (result == false) {
		            	System.out.println("오류 : 데이터 파일이 손상되었습니다.");
		            	System.out.println("프로그램을 종료합니다.");
				       	System.exit(0);
		            }
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
		        } catch(IOException e) {
		            System.out.println(e);
		        	System.exit(0);
		        }

		// filereader 객체 초기화
		filereader = new LectureFileReader("./lecture_list.txt");

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
		if(input.equals("1")||input.equals("회원가입"))
			signup();
		else if(input.equals("2")||input.equals("로그인"))
			signin();
		else if(input.equals("3")||input.equals("종료")) {
			System.out.println("프로그램을 종료합니다.");
			System.exit(0);
		}
		else {
			System.out.println("입력이 올바르지 않습니다. 다시입력해주세요");
			System.out.print("메뉴 입력: ");
			menuinput();
		}
	}
	
	//회원가입 시 입력한 Id가 이미 존재하는지 검사 메소드
	private boolean isID(String id) {
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
	
	//로그인 시 입력 Id와 Pw가 매치되는지 검사 메소드
	private boolean isIdPwMatch(String id, String pw) {
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
	
	//회원가입 메소드
	private void signup() {
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
		createIdFile(inputid);
		screen();
		menuinput();
		return;
	}
	
	//로그인 메소드
	private void signin() {
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
	private void initUserLectureList() {
		try {
			reader = new BufferedReader(new java.io.FileReader(loginUser.FILEPATH));

			loginUser.myLectureList = new ArrayList<Lecture>();
			String lectureNum = null;
			while ((lectureNum = reader.readLine()) != null) {
				
				// filereader로부터 lecture 객체 가져옴
				Lecture lecture = filereader.lecturelist.get(lectureNum);
				
				// loginUser의 myLectureList에 lecture 추가
				loginUser.myLectureList.add(lecture);
				
				// 1차 요구사항 - loginUser의 myCredit에 lecture의 credit 추가
				loginUser.myCredit += lecture.getLectureCredit();
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
					registerForLecture();
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
					screen();
					menuinput();
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
		String input = null;
		
		while(true) {
			
			// 수강신청내역 출력 //
			
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

			//lecture_list 현재 수강신청 인원 업데이트 - 수강신청 인원 감소
>>>>>>> branch 'master' of https://github.com/Konkuk-Project-B5/Major_Project.git

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
			if (loginUser.myLectureList.contains(inputLecture)) {
				System.out.println("동일한 강의를 추가했습니다.");
				continue;
			} else {
				// 1차 요구 사항 - 분반 검사 추가 
				// 분반 == 수강신청내역에 없음 && 수강신청내역에 동일한 이름의 과목 존재
				boolean flag = true;
				for(Lecture lec : loginUser.myLectureList) {
					if (lec.lectureName.equals(inputLecture.lectureName)) {
						flag = false;
						break;
					}
				}
				
				if (!flag) {
					// 분반 신청시 메세지 출력 - 질문?
					System.out.println("이미 수강 중인 강의의 다른 분반을 추가했습니다.");
					continue;
				}
			}

			//3. 중복 시간대 검사
			boolean flag = true;
			for(Lecture lec : loginUser.myLectureList) {

				if (inputLecture.getLectureDay1().equals(lec.getLectureDay1()) ||
						inputLecture.getLectureDay1().equals(lec.getLectureDay2()) ||
						inputLecture.getLectureDay2().equals(lec.getLectureDay1()) ||
						inputLecture.getLectureDay2().equals(lec.getLectureDay2()))  //같은 요일
				{
					if (inputLecture.getLectureStime() > lec.getLectureStime()) {  //이후 교시에 추가될 때
						if (inputLecture.getLectureStime() < lec.getLectureOtime() + 1) {  //기존 01-02면 03부터 시작하는 수업들 추가 가능
							flag = false;
							break;
						}
					} else if (inputLecture.getLectureStime() < lec.getLectureStime()) {  //이전 교시에 추가될 때
						if (inputLecture.getLectureOtime() > lec.getLectureStime() - 1) {  //기존 03-04면 02까지 끝나는 수업 추가 가능
							flag = false;
							break;
						}
					} else {  //같은 교시에 시작
						flag = false;
						break;
					}
				}
			}
			if (!flag) {
				System.out.println("기존 시간표와 강의 시간이 겹칩니다.");
				continue;
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
				// 최대 학점 한도 초과시 메세지 출력 - 질문?
				System.out.println(User.MAX_CREDIT+"학점을 초과해 신청할 수 없습니다.");
				continue;
			}
			
			// 검사 통과시 break
			break;
		}

		// 수강신청한 과목 loginUser의 myLectureList에 저장
		loginUser.myLectureList.add(filereader.lecturelist.get(input));
		
		// loginUser의 myCredit 수강신청한 과목의 학점만큼 증가 
		loginUser.myCredit += filereader.lecturelist.get(input).getLectureCredit();
		
		// 학번.txt 파일 업데이트
		updateIdFile();

		//lecturelist의 lecture의 수강신청인원 업데이트
		filereader.lecturelist.get(input).plusLectureCnum();

		//lecture_list 현재 수강신청 인원 업데이트
		filereader.updateLectureFile(input);

		// 수강신청 완료
		System.out.println("수강신청이 완료되었습니다.");
//		System.out.println("학점: "+loginUser.myCredit);
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