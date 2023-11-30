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
	private User loginUser; // �α����� ����� ���� ����� User ��ü
	private BufferedReader reader; // �й�.txt ���� �Է�
	private BufferedWriter writer; // �й�.txt ���� ���
	private myFileReader filereader; // filereader ��ü

	// 2�� �䱸���� - ���� ��¥ �߰�
	private LocalDate date;
	
	// ������
	public TimeTableManager() {
		// 2�� �䱸���� - ���� ��¥ �ʱ�ȭ
		date = LocalDate.now();
		// System.out.println(date.getYear()+" "+date.getMonthValue());
		
		// ���� ���Ἲ �˻�
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
			while ((line = lecture_list_bufReader.readLine()) != null) {
//				System.out.println(line);
				boolean result =
			line.matches(
				"^\\d{3}\s[��-�R]+[0-9]*\s[��-�R]{3}\s[��|ȭ|��|��|��]\s\\d{2}\s\\d{2}\s\\d{3}((\s\s\s\s\s)|(\s[��|ȭ|��|��|��]\s\\d{2}\s\\d{2}\s\\d{3}\s))\\d{2}\s\\d{2}\s\\d{1}$"
			);
				String[] lectureInfo = line.split(" ");
				//System.out.println(lectureInfo[lectureInfo.length-2]+" "+lectureInfo[6]+" "+lectureInfo[10]); //������û �����ο�, ���ǽ�
			    for(int i =0; i<sizeCount; i++) {
			    	if(lectureInfo[6].equals(roomInfo[i])) {
			    		if(Integer.parseInt(lectureInfo[lectureInfo.length-2]) >  roomMaxSize[i]) {
			    			System.out.println(lectureInfo[1]+"�� ������û�����ο��� "+lectureInfo[6]+" ���ǽ� �ִ�����ο��� �ѽ��ϴ�.");
			    			break;
			    		}
			    	}
			    	else if(lectureInfo[10].equals(roomInfo[i])) {
			    		if(Integer.parseInt(lectureInfo[lectureInfo.length-2]) >  roomMaxSize[i]) {
			    			System.out.println(lectureInfo[1]+"�� ������û�����ο��� "+lectureInfo[10]+" ���ǽ� �ִ�����ο��� �ѽ��ϴ�.");
			    			break;
			    		}
			    	}
			    }
				if (result == false) { System.out.println("���� :lecture ������ ������ �ջ�Ǿ����ϴ�.");
			System.out.println("���α׷��� �����մϴ�."); System.exit(0); } }
			 
		    lecture_list_bufReader.close();
		    File user_file = new File("./user.txt");
		    FileReader user_filereader = new FileReader(user_file);
		    BufferedReader user_bufReader = new BufferedReader(user_filereader);
		    line = "";
		    while ((line = user_bufReader.readLine()) != null) {
		        boolean result = line.matches("(201[0-9])|(202[0-3])[0-9]{5}\s[a-z0-9]{7,13}");
		        if (result == false) {        
		        	System.out.println("���� : ������ ������ �ջ�Ǿ����ϴ�.");
				    System.out.println("���α׷��� �����մϴ�.");
				    System.exit(0);
		        }
		    }		    
		    user_bufReader.close();
		} catch (FileNotFoundException e) {
		    System.out.println("���� : �ùٸ� ��ο� ������ ������ �������� �ʽ��ϴ�.");
		    System.out.println("���α׷��� �����մϴ�.");
		    System.exit(0);
		} catch (IOException e) {
		    System.out.println(e);
		    System.exit(0);
		}

		// ��ü �ʱ�ȭ
		filereader = new myFileReader("./lecture_list.txt", "./lecturer.txt", "./lecture_room.txt");

		// �˻� �� ���α׷� ����, �α���/ȸ������ �޴� ���
		System.out.println("[������û ���α׷�]");
		screen();
		menuinput();
	}

	// �α���/ȸ������ �޴� ��� �޼ҵ�
	private void screen() {
        System.out.println("1. ȸ������");
        System.out.println("2. �α���");
        System.out.println("3. ����");
        System.out.print("�޴� �Է�: ");
    }
	
	// �α���/ȸ������ �޴� �Է� �޼ҵ�
	private void menuinput() {
		String input = scan.nextLine().replaceAll("\\s", "");
		if (input.equals("1")||input.equals("ȸ������"))
			signup();
		else if (input.equals("2")||input.equals("�α���"))
			signin();
		else if (input.equals("3")||input.equals("����")) {
			System.out.println("���α׷��� �����մϴ�.");
			System.exit(0);
		}
		else {
			System.out.println("�Է��� �ùٸ��� �ʽ��ϴ�. �ٽ��Է����ּ���");
			System.out.print("�޴� �Է�: ");
			menuinput();
		}
	}
	

	// ȸ������ �� �Է��� Id�� �̹� �����ϴ��� �˻� �޼ҵ�
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
			System.out.println("���� �б� ����");
		}
		return false;
	}
	
	// �α��� �� �Է� Id�� Pw�� ��ġ�Ǵ��� �˻� �޼ҵ�
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
			System.out.println("���� �б� ����");
		}
		return false;
	}
	
	// ȸ������ �޼ҵ�
	private void signup() {
		System.out.println("ȸ�������� �����մϴ�.");
		System.out.print("�й� �Է�: ");
		String inputid = scan.nextLine();
		if (!(Pattern.matches("(201[0-9]|202[0-3])([0-9]{5})", inputid))) {
			System.out.println("�й��� 201000000�̻� 202399999 ������ �ڿ����Դϴ�.");
			screen();
			menuinput();
			return;
		}
		if (isID(inputid)) {
			System.out.println("�̹� ��ϵ� �й��Դϴ�.");
			screen();
			menuinput();
			return;
		}
		System.out.print("��й�ȣ �Է�: ");
		String inputpw = scan.nextLine();
		if (!(Pattern.matches("[a-z0-9]{7,13}", inputpw))) {
			System.out.println("��й�ȣ�� ���� �ҹ��ڿ� ���ڷθ� �̷���� ���̰� 7�̻� 13������ ���ڿ��̾���մϴ�.");
			screen();
			menuinput();
			return;
		}
		try {
			File file = new File("./user.txt");
			FileWriter fw = new FileWriter(file, true);
			fw.write(inputid+" "+inputpw+"\n");
			fw.close();
			System.out.println("ȸ�������� �Ϸ��߽��ϴ�.");
		} catch (Exception e) {
			System.out.println("���� ���� ����");
		}
		createIdFile(inputid);
		screen();
		menuinput();
		return;
	}
	
	// �α��� �޼ҵ�
	private void signin() {
		System.out.println("�α����� �����մϴ�. �й��� ��й�ȣ�� �Է����ּ���.");
		System.out.println("����: (<Ⱦ�������0><�й�><Ⱦ�������1><��й�ȣ><Ⱦ�������0>)");
		System.out.print("�Է�: ");
		String input = scan.nextLine().trim();
		String inputidpw[] = input.split("\\s+");
		if (inputidpw.length != 2 || !(Pattern.matches("(201[0-9]|202[0-3])([0-9]{5})", inputidpw[0])) || !(Pattern.matches("[a-z0-9]{7,13}", inputidpw[1]))) {
			System.out.println("�й��� ��й�ȣ�� Ȯ�����ּ���.");
			screen();
			menuinput();
			return;
		}
		if (isIdPwMatch(inputidpw[0],inputidpw[1])) {
			try {
				File student_file = new File("./"+inputidpw[0]+".txt");
			    // �Է� ��Ʈ�� ����
			    FileReader filereader = new FileReader(student_file);
			    // �Է� ���� ����
			    BufferedReader student_bufReader = new BufferedReader(filereader);
			    String line = "";
			    
				while ((line = student_bufReader.readLine()) != null) {
//					System.out.println(line);
					boolean result = line.matches("^\\d{4}\s\\d{3}\s[��-�R]+[0-9]*\s\\d{1}\s([ABCDF][+]*|X)$"); 
					if (result == false) { 
						System.out.println("���� : ������ ������ �ջ�Ǿ����ϴ�.");
						System.out.println("���α׷��� �����մϴ�."); 
						System.exit(0); 
					} 
				}
				 
			    student_bufReader.close();	
			}
		    catch (FileNotFoundException e) {
			    System.out.println("���� : �ùٸ� ��ο� ������ ������ �������� �ʽ��ϴ�.");
			    System.out.println("���α׷��� �����մϴ�.");
			    System.exit(0);
			} catch (IOException e) {
			    System.out.println(e);
			    System.exit(0);
			}
			loginUser = new User(inputidpw[0], inputidpw[1]);
			System.out.println("�α����� �Ϸ��߽��ϴ�.");
			initUserLectureList();
			mainMenu();
		}
		else {
			System.out.println("�й��� ��й�ȣ�� Ȯ�����ּ���.");
			screen();
			menuinput();
			return;
		}
	}

	// loginUser�� myLectureList �ʱ�ȭ �޼ҵ�
	// 2�� �䱸���� - loginUser�� myLectureList�� pastLectureList �ʱ�ȭ
	private void initUserLectureList() {
		try {
			reader = new BufferedReader(new java.io.FileReader(loginUser.FILEPATH));

			loginUser.myLectureList = new ArrayList<Lecture>();
			loginUser.pastLectureList = new ArrayList<Lecture>();
			loginUser.pastLectureListYear = new ArrayList<Integer>();
			String line = null;
			while ((line = reader.readLine()) != null) {
				
				// 2�� �䱸���� - �й�.txt�� ����� ���� ���� ������
				// ���� ����, ���� ��ȣ, ���Ǹ�, ����, ��� 
				String[] lectureInfo = line.split(" ");
					
				// ���� ������ ���� ������ ������  
				if (lectureInfo[0].equals(Integer.toString(date.getYear()))) {
					
					// filereader�κ��� lecture ��ü ������
					Lecture lecture = filereader.lecturelist.get(lectureInfo[1]);
					
					// 2�� �䱸���� - lecture ��ü�� grade �ʱ�ȭ
					lecture.grade = lectureInfo[4];
					
					// loginUser�� myLectureList�� lecture �߰�
					loginUser.myLectureList.add(lecture);
					
					// loginUser�� myCredit�� lecture�� credit �߰�
					loginUser.myCredit += lecture.getLectureCredit();
				} else { // ���� �ߴ� �����̸�
					
					// �����ߴ� ���� ���� Lecture ��ü ����
					Lecture lecture = new Lecture(lectureInfo[1], lectureInfo[2], lectureInfo[3], lectureInfo[4]);
		
					// pastLectureList�� �߰�
					loginUser.pastLectureList.add(lecture);
					loginUser.pastLectureListYear.add(Integer.parseInt(lectureInfo[0])); // �����ߴ� ���� ���� ����
				}
			}

			reader.close();
		} catch (FileNotFoundException e) {
//			System.out.println(loginUser.FILEPATH+": ���� �������� ����");
			System.exit(0); // ���� �߻��� ���α׷� ����
		} catch (IOException e) {
//			System.out.println(loginUser.FILEPATH+": �б� ����");
			System.exit(0); // ���� �߻��� ���α׷� ����
		}
	}

	// �й�.txt ���� ���� �޼ҵ�
	private void createIdFile(String id) {
		File file = new File("./" + id + ".txt");
		try {
			file.createNewFile();
		} catch (IOException e) {
//			System.out.println(id + ": ���� ���� ����");
			System.exit(0); // ���� �߻��� ���α׷� ����
		}
	}

	// ������û �� �ð�ǥ ��ȸ (���� �޴�) ��� �޼ҵ�
	private void mainMenu() {
		String input = null;
		
		while (true) {
			System.out.println("\n[���� �޴�] ������ �޴��� �����ϼ���");
			System.out.println("1. ������û�ϱ�\n2. �ð�ǥ��ȸ�ϱ�\n3. ���������ȸ�ϱ�\n4. �α׾ƿ�\n5. �����ϱ�");
			System.out.print("����: ");

			input = scan.nextLine().strip();

			switch (input) {
				case "1":
				case "������û�ϱ�":
					// ���� ��û �޼ҵ� ����
					registerForLecture();
					break;
				case "2":
				case "�ð�ǥ��ȸ�ϱ�":
					// �ð�ǥ ��ȸ �޼ҵ� ����
					showTimeTable();
					break;
				// 2�� �䱸 ���� - �����ߴ� ���� ��ȸ
				case "3":
				case "���������ȸ�ϱ�":
					// ������� ��ȸ �޼ҵ� ����
					showPastTimeTable();
					break;
				case "4":
				case "�α׾ƿ�":
					// �α׾ƿ�
					System.out.println("�α׾ƿ��� �Ϸ�Ǿ����ϴ�.");
					screen();
					menuinput();
					return;
				case "5":
				case "�����ϱ�":
					// ���α׷� ����
					System.out.println("���α׷��� �����մϴ�.");
					System.exit(0);
				default:
					// ���� �޼��� ���
					System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
					break;
			}
		}
	}
	
	// ������� ��ȸ �޼ҵ�
	private void showPastTimeTable() {
		// ���� �޾Ҵ� ���� ���
		String content="";
		int count=0;
		for (Lecture lecture : loginUser.pastLectureList) {
			content += loginUser.pastLectureListYear.get(count) + " " + lecture.lectureCode + " " + lecture.lectureName + " " + lecture.lectureCredit + " " + lecture.grade + "\n";
			count++;
		}
		System.out.println(content);
		System.out.println("�Է��� ������ ��� ���� �޴��� ���ư��ϴ�.\n");
		scan.nextLine();
	}

	// �ð�ǥ ��ȸ �޼ҵ�
	private void showTimeTable() {
		String input = null;
		
		while (true) {
			if (!loginUser.printMyList()) {
				System.out.println();
				System.out.println("������û�� �������� �����ϴ�.");
				System.out.println();
				while (true) {
					System.out.println("�� 'q'�� �Է��� ��� ���θ޴��� ���ư��ϴ�.");
					System.out.println();
					input = scan.nextLine().strip();
					if (input.equals("q")) {
						System.out.println("���θ޴��� ���ư��ϴ�.");
						System.out.println();
						return;
					}
					System.out.println("�������Է��Դϴ�.");
				}
			}

			// 1�� �䱸���� - ���� öȸ ��� �߰�
			System.out.println("\n\n������ öȸ�� �����ȣ�� �Է����ּ���. ���� ���� ������ "+loginUser.myCredit+"�����Դϴ�. (�ִ� ���� ����: "+User.MAX_CREDIT+"����)\n�� 'q'�� �Է��� ��� ���θ޴��� ���ư��ϴ�.\n\n");
			System.out.print("�����ȣ �Է� : ");
			input = scan.nextLine().strip();

			if (input.equals("q")) {
				System.out.println("���θ޴��� ���ư��ϴ�.");
				return;
			}
			
			// ������Ģ�� �´��� �˻�
			if (!input.matches("\\d{3}")) {
				System.out.println("�����ȣ�� 0�� �ڿ����θ� �̷���� ���̰� 3�� ���ڿ��Դϴ�.");
				continue;
			}
			
			//�ṉ̀�Ģ �˻�
			//1. lecture_list.txt�� �ִ� ��ȣ����
			if (!filereader.lecturelist.containsKey(input)) {
				System.out.println("���ǰ� �������� �ʽ��ϴ�.");
				continue;
			}
			
			//2. �й�.txt�� �ִ� ��ȣ����
			boolean flag = false;
			for (Lecture lec : loginUser.myLectureList) {
				if (lec.lectureCode.equals(input)) {
					flag = true;
					break;
				}
			}
			
			if (!flag) {
				System.out.println("������û������ �Է��� ���ǰ� �������� �ʽ��ϴ�.");
				continue;
			}
			
			// �˻� ��� 
	
			// �Է��� ���� loginUser�� myLectureList���� ����
			loginUser.myLectureList.remove(filereader.lecturelist.get(input));
						
			// loginUser�� myCredit ����öȸ�� ������ ������ŭ ���� 
			loginUser.myCredit -= filereader.lecturelist.get(input).getLectureCredit();
						
			// �й�.txt ���� ������Ʈ
			updateIdFile();

			//lecturelist�� lecture�� ������û �ο� ������Ʈ - ������û �ο� ����
			filereader.lecturelist.get(input).minusLectureCnum();

			//lecture_list ���� ������û �ο� ������Ʈ - ������û �ο� ����
			filereader.updateLectureFile2(input);

			// ����öȸ �Ϸ�
			System.out.println("����öȸ�� �Ϸ�Ǿ����ϴ�.");
//			System.out.println("����: "+loginUser.myCredit);
		}
	}

	// ������û �޼ҵ�
	private void registerForLecture() {
		String input = null;

		while (true) {

			// ���� ��� ���
			filereader.printLectureList();

			System.out.printf("\n\n������û�� �����ȣ�� �Է����ּ���(�������� :%d/18)", loginUser.myCredit);
			System.out.println("�� 'q'�� �Է��� ��� ���θ޴��� ���ư��ϴ�.\n\n");
			System.out.print("�����ȣ �Է� : ");
			input = scan.nextLine().strip();

			if (input.equals("q")) {
				System.out.println("���θ޴��� ���ư��ϴ�.");
				return;
			}

			// ������Ģ�� �´��� �˻�
			if (!input.matches("\\d{3}")) {
				System.out.println("�����ȣ�� 0�� �ڿ����θ� �̷���� ���̰� 3�� ���ڿ��Դϴ�.");
				continue;
			}

			//�ṉ̀�Ģ �˻�
			//1. lecture_list.txt�� �ִ� ��ȣ����
			if (!filereader.lecturelist.containsKey(input)) {
				System.out.println("���ǰ� �������� �ʽ��ϴ�.");
				continue;
			}

			//2. ������ ���� �ߺ� �߰� �˻�
			Lecture inputLecture = filereader.lecturelist.get(input);
			if (loginUser.myLectureList.contains(inputLecture)) {
				System.out.println("������ ���Ǹ� �߰��߽��ϴ�.");
				continue;
			} else {
				// 1�� �䱸 ���� - �й� �˻� �߰� 
				// �й� == ������û������ ���� && ������û������ ������ �̸��� ���� ����
				boolean flag = true;
				for (Lecture lec : loginUser.myLectureList) {
					if (lec.lectureName.equals(inputLecture.lectureName)) {
						flag = false;
						break;
					}
				}

				if (!flag) {
					// �й� ��û�� �޼��� ���
					System.out.println("�̹� ���� ���� ������ �ٸ� �й��� �߰��߽��ϴ�.");
					continue;
				}
			}

			//3. �ߺ� �ð��� �˻�
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
				if (flag_inputDay1_lecDay1)  //Day1, Day1 ���� ����
				{
					if (inputLecture.int_getLectureDay1Stime() > lec.int_getLectureDay1Stime()) {  //���� ���ÿ� �߰��� ��
						if (inputLecture.int_getLectureDay1Stime() < lec.int_getLectureDay1Otime() + 1) {  //���� 01-02�� 03���� �����ϴ� ������ �߰� ����
							flag = false;
//							System.out.println("a");
							break;
						}
					} else if (inputLecture.int_getLectureDay1Stime() < lec.int_getLectureDay1Stime()) {  //���� ���ÿ� �߰��� ��
						if (inputLecture.int_getLectureDay1Otime() > lec.int_getLectureDay1Stime() - 1) {  //���� 03-04�� 02���� ������ ���� �߰� ����
							flag = false;
//							System.out.println("a2");
							break;
						}
					} else {  //���� ���ÿ� ����
						flag = false;
//						System.out.println("a3");
						break;
					}
				}

				if (flag_inputDay1_lecDay2)  //Day1, Day2 ���� ����
				{
					if (inputLecture.int_getLectureDay1Stime() > lec.int_getLectureDay2Stime()) {  //���� ���ÿ� �߰��� ��
						if (inputLecture.int_getLectureDay1Stime() < lec.int_getLectureDay2Otime() + 1) {  //���� 01-02�� 03���� �����ϴ� ������ �߰� ����
							flag = false;
//							System.out.println("b");
							break;
						}
					} else if (inputLecture.int_getLectureDay1Stime() < lec.int_getLectureDay2Stime()) {  //���� ���ÿ� �߰��� ��
						if (inputLecture.int_getLectureDay1Otime() > lec.int_getLectureDay2Stime() - 1) {  //���� 03-04�� 02���� ������ ���� �߰� ����
							flag = false;
//							System.out.println("b2");
							break;
						}
					} else {  //���� ���ÿ� ����
						flag = false;
//						System.out.println("b3");
						break;
					}
				}

				if (flag_inputDay2_lecDay1)  //Day2, Day1 ���� ����
				{
					if (inputLecture.int_getLectureDay2Stime() > lec.int_getLectureDay1Stime()) {  //���� ���ÿ� �߰��� ��
						if (inputLecture.int_getLectureDay2Stime() < lec.int_getLectureDay2Otime() + 1) {  //���� 01-02�� 03���� �����ϴ� ������ �߰� ����
							flag = false;
//							System.out.println("c");
							break;
						}
					} else if (inputLecture.int_getLectureDay2Stime() < lec.int_getLectureDay1Stime()) {  //���� ���ÿ� �߰��� ��
						if (inputLecture.int_getLectureDay2Otime() > lec.int_getLectureDay1Stime() - 1) {  //���� 03-04�� 02���� ������ ���� �߰� ����
							flag = false;
//							System.out.println("c2");
							break;
						}
					} else {  //���� ���ÿ� ����
						flag = false;
//						System.out.println("c3");
						break;
					}
				}
				if (flag_inputDay2_lecDay2)  //Day2, Day2 ���� ����
				{
					if (inputLecture.int_getLectureDay2Stime() > lec.int_getLectureDay2Stime()) {  //���� ���ÿ� �߰��� ��
						if (inputLecture.int_getLectureDay2Stime() < lec.int_getLectureDay2Otime() + 1) {  //���� 01-02�� 03���� �����ϴ� ������ �߰� ����
							flag = false;
//							System.out.println("d");
							break;
						}
					} else if (inputLecture.int_getLectureDay2Stime() < lec.int_getLectureDay2Stime()) {  //���� ���ÿ� �߰��� ��
						if (inputLecture.int_getLectureDay2Otime() > lec.int_getLectureDay2Stime() - 1) {  //���� 03-04�� 02���� ������ ���� �߰� ����
							flag = false;
//							System.out.println("d2");
							break;
						}
					} else {  //���� ���ÿ� ����
						flag = false;
//						System.out.println("d3");
						break;
					}
				}

				if (!flag) {
					System.out.println("���� �ð�ǥ�� ���� �ð��� ��Ĩ�ϴ�.");
					continue;
				}
			}


				//4. �ο����� �˻�
				if (inputLecture.getLectureCnum() >= inputLecture.getLectureMnum()) {
//				System.out.println("���� �ڵ�: " + inputLecture.getLectureCode());
//				System.out.println("���ο�: " + inputLecture.getLectureCnum() + " " + inputLecture.lectureCnum + " / Max:" + inputLecture.getLectureMnum());
					System.out.println("������ �����ϴ�.");
					continue;
				}

				//5. 1�� �䱸���� - �ִ� ���� �ѵ� �˻�
				if (loginUser.myCredit + inputLecture.getLectureCredit() > User.MAX_CREDIT) {
					// �ִ� ���� �ѵ� �ʰ��� �޼��� ���
					System.out.println(User.MAX_CREDIT + "������ �ʰ��� ��û�� �� �����ϴ�.");
					continue;
				}

				//6. 2�� �䱸���� - A���� �̻� ����� ����
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
					System.out.println("A���� �̻� �޾Ҵ� ������ ������� �� �����ϴ�.");
					continue;
				}

				// �˻� ����� break
				break;
			}

			// 2�� �䱸���� - ������û�� ������ ��� �ʱ�ȭ
			filereader.lecturelist.get(input).grade = "X";

			// ������û�� ���� loginUser�� myLectureList�� ����
			loginUser.myLectureList.add(filereader.lecturelist.get(input));

			// loginUser�� myCredit ������û�� ������ ������ŭ ����
			loginUser.myCredit += filereader.lecturelist.get(input).getLectureCredit();

			// �й�.txt ���� ������Ʈ
			updateIdFile();

			// lecturelist�� lecture�� ������û�ο� ������Ʈ
			filereader.lecturelist.get(input).plusLectureCnum();

			// lecture_list ���� ������û �ο� ������Ʈ
			filereader.updateLectureFile(input);

			// ������û �Ϸ�
			System.out.println("������û�� �Ϸ�Ǿ����ϴ�.");
//		System.out.println("����: "+loginUser.myCredit);
		}
	
	// �й�.txt ���� ������Ʈ �޼ҵ�
	private void updateIdFile() {
		String content = "";
		int count = 0;
		for (Lecture lecture : loginUser.myLectureList)
			content += date.getYear() + " " + lecture.lectureCode + " " + lecture.lectureName + " " + lecture.lectureCredit + " " + lecture.grade + "\n";
		
		// 2�� �䱸���� - �����ߴ� ���� �߰�
		for (Lecture lecture : loginUser.pastLectureList) {
			content += loginUser.pastLectureListYear.get(count) + " " + lecture.lectureCode + " " + lecture.lectureName + " " + lecture.lectureCredit + " " + lecture.grade + "\n";
			count++;
			// �����ߴ� ���� �״�� ����
		}
		
		try {
			writer = new BufferedWriter(new FileWriter(loginUser.FILEPATH));
			writer.write(content);
			writer.close();
		} catch (IOException e) {
//			System.out.println(loginUser.FILEPATH+": ���� ����");
			System.exit(0); // ���� �߻��� ���α׷� ����
		}
	}
}