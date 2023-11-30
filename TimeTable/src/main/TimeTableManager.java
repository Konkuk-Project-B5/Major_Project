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
	private User loginUser; // ·Î±×ÀÎÇÑ »ç¿ëÀÚ Á¤º¸ ÀúÀå¿ë User °´Ã¼
	private BufferedReader reader; // ÇĞ¹ø.txt ÆÄÀÏ ÀÔ·Â
	private BufferedWriter writer; // ÇĞ¹ø.txt ÆÄÀÏ Ãâ·Â
	private myFileReader filereader; // filereader °´Ã¼

	// 2Â÷ ¿ä±¸»çÇ× - ÇöÀç ³¯Â¥ Ãß°¡
	private LocalDate date;
	
	// »ı¼ºÀÚ
	public TimeTableManager() {
		// 2Â÷ ¿ä±¸»çÇ× - ÇöÀç ³¯Â¥ ÃÊ±âÈ­
		date = LocalDate.now();
		// System.out.println(date.getYear()+" "+date.getMonthValue());
		
		// ÆÄÀÏ ¹«°á¼º °Ë»ç
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
				"^\\d{3}\s[°¡-ÆR]+[0-9]*\s[°¡-ÆR]{3}\s[¿ù|È­|¼ö|¸ñ|±İ]\s\\d{2}\s\\d{2}\s\\d{3}((\s\s\s\s\s)|(\s[¿ù|È­|¼ö|¸ñ|±İ]\s\\d{2}\s\\d{2}\s\\d{3}\s))\\d{2}\s\\d{2}\s\\d{1}$"
			);
				String[] lectureInfo = line.split(" ");
				//System.out.println(lectureInfo[lectureInfo.length-2]+" "+lectureInfo[6]+" "+lectureInfo[10]); //¼ö°­½ÅÃ» Á¦ÇÑÀÎ¿ø, °­ÀÇ½Ç
			    for(int i =0; i<sizeCount; i++) {
			    	if(lectureInfo[6].equals(roomInfo[i])) {
			    		if(Integer.parseInt(lectureInfo[lectureInfo.length-2]) >  roomMaxSize[i]) {
			    			System.out.println(lectureInfo[1]+"ÀÇ ¼ö°­½ÅÃ»Á¦ÇÑÀÎ¿øÀÌ "+lectureInfo[6]+" °­ÀÇ½Ç ÃÖ´ë¼ö¿ëÀÎ¿øÀ» ³Ñ½À´Ï´Ù.");
			    			break;
			    		}
			    	}
			    	else if(lectureInfo[10].equals(roomInfo[i])) {
			    		if(Integer.parseInt(lectureInfo[lectureInfo.length-2]) >  roomMaxSize[i]) {
			    			System.out.println(lectureInfo[1]+"ÀÇ ¼ö°­½ÅÃ»Á¦ÇÑÀÎ¿øÀÌ "+lectureInfo[10]+" °­ÀÇ½Ç ÃÖ´ë¼ö¿ëÀÎ¿øÀ» ³Ñ½À´Ï´Ù.");
			    			break;
			    		}
			    	}
			    }
				if (result == false) { System.out.println("¿À·ù :lecture µ¥ÀÌÅÍ ÆÄÀÏÀÌ ¼Õ»óµÇ¾ú½À´Ï´Ù.");
			System.out.println("ÇÁ·Î±×·¥À» Á¾·áÇÕ´Ï´Ù."); System.exit(0); } }
			 
		    lecture_list_bufReader.close();
		    File user_file = new File("./user.txt");
		    FileReader user_filereader = new FileReader(user_file);
		    BufferedReader user_bufReader = new BufferedReader(user_filereader);
		    line = "";
		    while ((line = user_bufReader.readLine()) != null) {
		        boolean result = line.matches("(201[0-9])|(202[0-3])[0-9]{5}\s[a-z0-9]{7,13}");
		        if (result == false) {        
		        	System.out.println("¿À·ù : µ¥ÀÌÅÍ ÆÄÀÏÀÌ ¼Õ»óµÇ¾ú½À´Ï´Ù.");
				    System.out.println("ÇÁ·Î±×·¥À» Á¾·áÇÕ´Ï´Ù.");
				    System.exit(0);
		        }
		    }		    
		    user_bufReader.close();
		} catch (FileNotFoundException e) {
		    System.out.println("¿À·ù : ¿Ã¹Ù¸¥ °æ·Î¿¡ µ¥ÀÌÅÍ ÆÄÀÏÀÌ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
		    System.out.println("ÇÁ·Î±×·¥À» Á¾·áÇÕ´Ï´Ù.");
		    System.exit(0);
		} catch (IOException e) {
		    System.out.println(e);
		    System.exit(0);
		}

		// °´Ã¼ ÃÊ±âÈ­
		filereader = new myFileReader("./lecture_list.txt", "./lecturer.txt", "./lecture_room.txt");

		// °Ë»ç ÈÄ ÇÁ·Î±×·¥ ½ÇÇà, ·Î±×ÀÎ/È¸¿ø°¡ÀÔ ¸Ş´º Ãâ·Â
		System.out.println("[¼ö°­½ÅÃ» ÇÁ·Î±×·¥]");
		screen();
		menuinput();
	}

	// ·Î±×ÀÎ/È¸¿ø°¡ÀÔ ¸Ş´º Ãâ·Â ¸Ş¼Òµå
	private void screen() {
        System.out.println("1. È¸¿ø°¡ÀÔ");
        System.out.println("2. ·Î±×ÀÎ");
        System.out.println("3. Á¾·á");
        System.out.print("¸Ş´º ÀÔ·Â: ");
    }
	
	// ·Î±×ÀÎ/È¸¿ø°¡ÀÔ ¸Ş´º ÀÔ·Â ¸Ş¼Òµå
	private void menuinput() {
		String input = scan.nextLine().replaceAll("\\s", "");
		if (input.equals("1")||input.equals("È¸¿ø°¡ÀÔ"))
			signup();
		else if (input.equals("2")||input.equals("·Î±×ÀÎ"))
			signin();
		else if (input.equals("3")||input.equals("Á¾·á")) {
			System.out.println("ÇÁ·Î±×·¥À» Á¾·áÇÕ´Ï´Ù.");
			System.exit(0);
		}
		else {
			System.out.println("ÀÔ·ÂÀÌ ¿Ã¹Ù¸£Áö ¾Ê½À´Ï´Ù. ´Ù½ÃÀÔ·ÂÇØÁÖ¼¼¿ä");
			System.out.print("¸Ş´º ÀÔ·Â: ");
			menuinput();
		}
	}
	

	// È¸¿ø°¡ÀÔ ½Ã ÀÔ·ÂÇÑ Id°¡ ÀÌ¹Ì Á¸ÀçÇÏ´ÂÁö °Ë»ç ¸Ş¼Òµå
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
			System.out.println("ÆÄÀÏ ÀĞ±â ½ÇÆĞ");
		}
		return false;
	}
	
	// ·Î±×ÀÎ ½Ã ÀÔ·Â Id¿Í Pw°¡ ¸ÅÄ¡µÇ´ÂÁö °Ë»ç ¸Ş¼Òµå
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
			System.out.println("ÆÄÀÏ ÀĞ±â ½ÇÆĞ");
		}
		return false;
	}
	
	// È¸¿ø°¡ÀÔ ¸Ş¼Òµå
	private void signup() {
		System.out.println("È¸¿ø°¡ÀÔÀ» ½ÃÀÛÇÕ´Ï´Ù.");
		System.out.print("ÇĞ¹ø ÀÔ·Â: ");
		String inputid = scan.nextLine();
		if (!(Pattern.matches("(201[0-9]|202[0-3])([0-9]{5})", inputid))) {
			System.out.println("ÇĞ¹øÀº 201000000ÀÌ»ó 202399999 ÀÌÇÏÀÇ ÀÚ¿¬¼öÀÔ´Ï´Ù.");
			screen();
			menuinput();
			return;
		}
		if (isID(inputid)) {
			System.out.println("ÀÌ¹Ì µî·ÏµÈ ÇĞ¹øÀÔ´Ï´Ù.");
			screen();
			menuinput();
			return;
		}
		System.out.print("ºñ¹Ğ¹øÈ£ ÀÔ·Â: ");
		String inputpw = scan.nextLine();
		if (!(Pattern.matches("[a-z0-9]{7,13}", inputpw))) {
			System.out.println("ºñ¹Ğ¹øÈ£´Â ¿µ¹® ¼Ò¹®ÀÚ¿Í ¼ıÀÚ·Î¸¸ ÀÌ·ç¾îÁø ±æÀÌ°¡ 7ÀÌ»ó 13ÀÌÇÏÀÎ ¹®ÀÚ¿­ÀÌ¾î¾ßÇÕ´Ï´Ù.");
			screen();
			menuinput();
			return;
		}
		try {
			File file = new File("./user.txt");
			FileWriter fw = new FileWriter(file, true);
			fw.write(inputid+" "+inputpw+"\n");
			fw.close();
			System.out.println("È¸¿ø°¡ÀÔÀ» ¿Ï·áÇß½À´Ï´Ù.");
		} catch (Exception e) {
			System.out.println("ÆÄÀÏ ¾²±â ½ÇÆĞ");
		}
		createIdFile(inputid);
		screen();
		menuinput();
		return;
	}
	
	// ·Î±×ÀÎ ¸Ş¼Òµå
	private void signin() {
		System.out.println("·Î±×ÀÎÀ» ½ÃÀÛÇÕ´Ï´Ù. ÇĞ¹ø°ú ºñ¹Ğ¹øÈ£¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.");
		System.out.println("Çü½Ä: (<È¾°ø¹é·ù¿­0><ÇĞ¹ø><È¾°ø¹é·ù¿­1><ºñ¹Ğ¹øÈ£><È¾°ø¹é·ù¿­0>)");
		System.out.print("ÀÔ·Â: ");
		String input = scan.nextLine().trim();
		String inputidpw[] = input.split("\\s+");
		if (inputidpw.length != 2 || !(Pattern.matches("(201[0-9]|202[0-3])([0-9]{5})", inputidpw[0])) || !(Pattern.matches("[a-z0-9]{7,13}", inputidpw[1]))) {
			System.out.println("ÇĞ¹ø°ú ºñ¹Ğ¹øÈ£¸¦ È®ÀÎÇØÁÖ¼¼¿ä.");
			screen();
			menuinput();
			return;
		}
		if (isIdPwMatch(inputidpw[0],inputidpw[1])) {
			try {
				File student_file = new File("./"+inputidpw[0]+".txt");
			    // ÀÔ·Â ½ºÆ®¸² »ı¼º
			    FileReader filereader = new FileReader(student_file);
			    // ÀÔ·Â ¹öÆÛ »ı¼º
			    BufferedReader student_bufReader = new BufferedReader(filereader);
			    String line = "";
			    
				while ((line = student_bufReader.readLine()) != null) {
//					System.out.println(line);
					boolean result = line.matches("^\\d{4}\s\\d{3}\s[°¡-ÆR]+[0-9]*\s\\d{1}\s([ABCDF][+]*|X)$"); 
					if (result == false) { 
						System.out.println("¿À·ù : µ¥ÀÌÅÍ ÆÄÀÏÀÌ ¼Õ»óµÇ¾ú½À´Ï´Ù.");
						System.out.println("ÇÁ·Î±×·¥À» Á¾·áÇÕ´Ï´Ù."); 
						System.exit(0); 
					} 
				}
				 
			    student_bufReader.close();	
			}
		    catch (FileNotFoundException e) {
			    System.out.println("¿À·ù : ¿Ã¹Ù¸¥ °æ·Î¿¡ µ¥ÀÌÅÍ ÆÄÀÏÀÌ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
			    System.out.println("ÇÁ·Î±×·¥À» Á¾·áÇÕ´Ï´Ù.");
			    System.exit(0);
			} catch (IOException e) {
			    System.out.println(e);
			    System.exit(0);
			}
			loginUser = new User(inputidpw[0], inputidpw[1]);
			System.out.println("·Î±×ÀÎÀ» ¿Ï·áÇß½À´Ï´Ù.");
			initUserLectureList();
			mainMenu();
		}
		else {
			System.out.println("ÇĞ¹ø°ú ºñ¹Ğ¹øÈ£¸¦ È®ÀÎÇØÁÖ¼¼¿ä.");
			screen();
			menuinput();
			return;
		}
	}

	// loginUserÀÇ myLectureList ÃÊ±âÈ­ ¸Ş¼Òµå
	// 2Â÷ ¿ä±¸»çÇ× - loginUserÀÇ myLectureList¿Í pastLectureList ÃÊ±âÈ­
	private void initUserLectureList() {
		try {
			reader = new BufferedReader(new java.io.FileReader(loginUser.FILEPATH));

			loginUser.myLectureList = new ArrayList<Lecture>();
			loginUser.pastLectureList = new ArrayList<Lecture>();
			loginUser.pastLectureListYear = new ArrayList<Integer>();
			String line = null;
			while ((line = reader.readLine()) != null) {
				
				// 2Â÷ ¿ä±¸»çÇ× - ÇĞ¹ø.txt¿¡ ÀúÀåµÈ °­ÀÇ Á¤º¸ °¡Á®¿È
				// ¼ö°­ ¿¬µµ, °ú¸ñ ¹øÈ£, °­ÀÇ¸í, ÇĞÁ¡, µî±Ş 
				String[] lectureInfo = line.split(" ");
					
				// ¼ö°­ ¿¬µµ°¡ ÇöÀç ¿¬µµ¿Í °°À¸¸é  
				if (lectureInfo[0].equals(Integer.toString(date.getYear()))) {
					
					// filereader·ÎºÎÅÍ lecture °´Ã¼ °¡Á®¿È
					Lecture lecture = filereader.lecturelist.get(lectureInfo[1]);
					
					// 2Â÷ ¿ä±¸»çÇ× - lecture °´Ã¼ÀÇ grade ÃÊ±âÈ­
					lecture.grade = lectureInfo[4];
					
					// loginUserÀÇ myLectureList¿¡ lecture Ãß°¡
					loginUser.myLectureList.add(lecture);
					
					// loginUserÀÇ myCredit¿¡ lectureÀÇ credit Ãß°¡
					loginUser.myCredit += lecture.getLectureCredit();
				} else { // ¼ö°­ Çß´ø °ú¸ñÀÌ¸é
					
					// ¼ö°­Çß´ø °ú¸ñ¿¡ ´ëÇÑ Lecture °´Ã¼ »ı¼º
					Lecture lecture = new Lecture(lectureInfo[1], lectureInfo[2], lectureInfo[3], lectureInfo[4]);
		
					// pastLectureList¿¡ Ãß°¡
					loginUser.pastLectureList.add(lecture);
					loginUser.pastLectureListYear.add(Integer.parseInt(lectureInfo[0])); // ¼ö°­Çß´ø °­ÀÇ ¿¬µµ ÀúÀå
				}
			}

			reader.close();
		} catch (FileNotFoundException e) {
//			System.out.println(loginUser.FILEPATH+": ÆÄÀÏ Á¸ÀçÇÏÁö ¾ÊÀ½");
			System.exit(0); // ¿À·ù ¹ß»ı½Ã ÇÁ·Î±×·¥ Á¾·á
		} catch (IOException e) {
//			System.out.println(loginUser.FILEPATH+": ÀĞ±â ½ÇÆĞ");
			System.exit(0); // ¿À·ù ¹ß»ı½Ã ÇÁ·Î±×·¥ Á¾·á
		}
	}

	// ÇĞ¹ø.txt ÆÄÀÏ »ı¼º ¸Ş¼Òµå
	private void createIdFile(String id) {
		File file = new File("./" + id + ".txt");
		try {
			file.createNewFile();
		} catch (IOException e) {
//			System.out.println(id + ": ÆÄÀÏ »ı¼º ½ÇÆĞ");
			System.exit(0); // ¿À·ù ¹ß»ı½Ã ÇÁ·Î±×·¥ Á¾·á
		}
	}

	// ¼ö°­½ÅÃ» ¹× ½Ã°£Ç¥ Á¶È¸ (¸ŞÀÎ ¸Ş´º) Ãâ·Â ¸Ş¼Òµå
	private void mainMenu() {
		String input = null;
		
		while (true) {
			System.out.println("\n[¸ŞÀÎ ¸Ş´º] ½ÇÇàÇÒ ¸Ş´º¸¦ ¼±ÅÃÇÏ¼¼¿ä");
			System.out.println("1. ¼ö°­½ÅÃ»ÇÏ±â\n2. ½Ã°£Ç¥Á¶È¸ÇÏ±â\n3. ¼ö°­±â·ÏÁ¶È¸ÇÏ±â\n4. ·Î±×¾Æ¿ô\n5. Á¾·áÇÏ±â");
			System.out.print("¼±ÅÃ: ");

			input = scan.nextLine().strip();

			switch (input) {
				case "1":
				case "¼ö°­½ÅÃ»ÇÏ±â":
					// ¼ö°­ ½ÅÃ» ¸Ş¼Òµå ½ÇÇà
					registerForLecture();
					break;
				case "2":
				case "½Ã°£Ç¥Á¶È¸ÇÏ±â":
					// ½Ã°£Ç¥ Á¶È¸ ¸Ş¼Òµå ½ÇÇà
					showTimeTable();
					break;
				// 2Â÷ ¿ä±¸ »çÇ× - ¼ö°­Çß´ø °ú¸ñ Á¶È¸
				case "3":
				case "¼ö°­±â·ÏÁ¶È¸ÇÏ±â":
					// ¼ö°­±â·Ï Á¶È¸ ¸Ş¼Òµå ½ÇÇà
					showPastTimeTable();
					break;
				case "4":
				case "·Î±×¾Æ¿ô":
					// ·Î±×¾Æ¿ô
					System.out.println("·Î±×¾Æ¿ôÀÌ ¿Ï·áµÇ¾ú½À´Ï´Ù.");
					screen();
					menuinput();
					return;
				case "5":
				case "Á¾·áÇÏ±â":
					// ÇÁ·Î±×·¥ Á¾·á
					System.out.println("ÇÁ·Î±×·¥À» Á¾·áÇÕ´Ï´Ù.");
					System.exit(0);
				default:
					// ¿À·ù ¸Ş¼¼Áö Ãâ·Â
					System.out.println("Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
					break;
			}
		}
	}
	
	// ¼ö°­±â·Ï Á¶È¸ ¸Ş¼Òµå
	private void showPastTimeTable() {
		// ÇĞÁ¡ ¹Ş¾Ò´ø °ú¸ñ Ãâ·Â
		String content="";
		int count=0;
		for (Lecture lecture : loginUser.pastLectureList) {
			content += loginUser.pastLectureListYear.get(count) + " " + lecture.lectureCode + " " + lecture.lectureName + " " + lecture.lectureCredit + " " + lecture.grade + "\n";
			count++;
		}
		System.out.println(content);
		System.out.println("ÀÔ·ÂÀÌ µé¾î¿À´Â °æ¿ì ¸ŞÀÎ ¸Ş´º·Î µ¹¾Æ°©´Ï´Ù.\n");
		scan.nextLine();
	}

	// ½Ã°£Ç¥ Á¶È¸ ¸Ş¼Òµå
	private void showTimeTable() {
		String input = null;
		
		while (true) {
			if (!loginUser.printMyList()) {
				System.out.println();
				System.out.println("¼ö°­½ÅÃ»ÇÑ ±³°ú¸ñÀÌ ¾ø½À´Ï´Ù.");
				System.out.println();
				while (true) {
					System.out.println("¡Ø 'q'¸¦ ÀÔ·ÂÇÑ °æ¿ì ¸ŞÀÎ¸Ş´º·Î µ¹¾Æ°©´Ï´Ù.");
					System.out.println();
					input = scan.nextLine().strip();
					if (input.equals("q")) {
						System.out.println("¸ŞÀÎ¸Ş´º·Î µ¹¾Æ°©´Ï´Ù.");
						System.out.println();
						return;
					}
					System.out.println("ºñÁ¤»óÀÔ·ÂÀÔ´Ï´Ù.");
				}
			}

			// 1Â÷ ¿ä±¸»çÇ× - ¼ö°­ Ã¶È¸ ±â´É Ãß°¡
			System.out.println("\n\n¼ö°­À» Ã¶È¸ÇÒ °ú¸ñ¹øÈ£¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä. ÇöÀç ¼ö°­ ÇĞÁ¡Àº "+loginUser.myCredit+"ÇĞÁ¡ÀÔ´Ï´Ù. (ÃÖ´ë ¼ö°­ ÇĞÁ¡: "+User.MAX_CREDIT+"ÇĞÁ¡)\n¡Ø 'q'¸¦ ÀÔ·ÂÇÑ °æ¿ì ¸ŞÀÎ¸Ş´º·Î µ¹¾Æ°©´Ï´Ù.\n\n");
			System.out.print("°ú¸ñ¹øÈ£ ÀÔ·Â : ");
			input = scan.nextLine().strip();

			if (input.equals("q")) {
				System.out.println("¸ŞÀÎ¸Ş´º·Î µ¹¾Æ°©´Ï´Ù.");
				return;
			}
			
			// ¹®¹ı±ÔÄ¢¿¡ ¸Â´ÂÁö °Ë»ç
			if (!input.matches("\\d{3}")) {
				System.out.println("°ú¸ñ¹øÈ£´Â 0°ú ÀÚ¿¬¼ö·Î¸¸ ÀÌ·ç¾îÁø ±æÀÌ°¡ 3ÀÎ ¹®ÀÚ¿­ÀÔ´Ï´Ù.");
				continue;
			}
			
			//ÀÇ¹Ì±ÔÄ¢ °Ë»ç
			//1. lecture_list.txt¿¡ ÀÖ´Â ¹øÈ£ÀÎÁö
			if (!filereader.lecturelist.containsKey(input)) {
				System.out.println("°­ÀÇ°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
				continue;
			}
			
			//2. ÇĞ¹ø.txt¿¡ ÀÖ´Â ¹øÈ£ÀÎÁö
			boolean flag = false;
			for (Lecture lec : loginUser.myLectureList) {
				if (lec.lectureCode.equals(input)) {
					flag = true;
					break;
				}
			}
			
			if (!flag) {
				System.out.println("¼ö°­½ÅÃ»³»¿ª¿¡ ÀÔ·ÂÇÑ °­ÀÇ°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
				continue;
			}
			
			// °Ë»ç Åë°ú 
	
			// ÀÔ·ÂÇÑ °ú¸ñ loginUserÀÇ myLectureList¿¡¼­ »èÁ¦
			loginUser.myLectureList.remove(filereader.lecturelist.get(input));
						
			// loginUserÀÇ myCredit ¼ö°­Ã¶È¸ÇÑ °ú¸ñÀÇ ÇĞÁ¡¸¸Å­ °¨¼Ò 
			loginUser.myCredit -= filereader.lecturelist.get(input).getLectureCredit();
						
			// ÇĞ¹ø.txt ÆÄÀÏ ¾÷µ¥ÀÌÆ®
			updateIdFile();

			//lecturelistÀÇ lectureÀÇ ¼ö°­½ÅÃ» ÀÎ¿ø ¾÷µ¥ÀÌÆ® - ¼ö°­½ÅÃ» ÀÎ¿ø °¨¼Ò
			filereader.lecturelist.get(input).minusLectureCnum();

			//lecture_list ÇöÀç ¼ö°­½ÅÃ» ÀÎ¿ø ¾÷µ¥ÀÌÆ® - ¼ö°­½ÅÃ» ÀÎ¿ø °¨¼Ò
			filereader.updateLectureFile2(input);

			// ¼ö°­Ã¶È¸ ¿Ï·á
			System.out.println("¼ö°­Ã¶È¸°¡ ¿Ï·áµÇ¾ú½À´Ï´Ù.");
//			System.out.println("ÇĞÁ¡: "+loginUser.myCredit);
		}
	}

	// ¼ö°­½ÅÃ» ¸Ş¼Òµå
	private void registerForLecture() {
		String input = null;

		while (true) {

			// °­ÀÇ ¸ñ·Ï Ãâ·Â
			filereader.printLectureList();

			System.out.printf("\n\n¼ö°­½ÅÃ»ÇÒ °ú¸ñ¹øÈ£¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä(¼ö°­ÇĞÁ¡ :%d/18)", loginUser.myCredit);
			System.out.println("¡Ø 'q'¸¦ ÀÔ·ÂÇÑ °æ¿ì ¸ŞÀÎ¸Ş´º·Î µ¹¾Æ°©´Ï´Ù.\n\n");
			System.out.print("°ú¸ñ¹øÈ£ ÀÔ·Â : ");
			input = scan.nextLine().strip();

			if (input.equals("q")) {
				System.out.println("¸ŞÀÎ¸Ş´º·Î µ¹¾Æ°©´Ï´Ù.");
				return;
			}

			// ¹®¹ı±ÔÄ¢¿¡ ¸Â´ÂÁö °Ë»ç
			if (!input.matches("\\d{3}")) {
				System.out.println("°ú¸ñ¹øÈ£´Â 0°ú ÀÚ¿¬¼ö·Î¸¸ ÀÌ·ç¾îÁø ±æÀÌ°¡ 3ÀÎ ¹®ÀÚ¿­ÀÔ´Ï´Ù.");
				continue;
			}

			//ÀÇ¹Ì±ÔÄ¢ °Ë»ç
			//1. lecture_list.txt¿¡ ÀÖ´Â ¹øÈ£ÀÎÁö
			if (!filereader.lecturelist.containsKey(input)) {
				System.out.println("°­ÀÇ°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
				continue;
			}

			//2. µ¿ÀÏÇÑ °­ÀÇ Áßº¹ Ãß°¡ °Ë»ç
			Lecture inputLecture = filereader.lecturelist.get(input);
			if (loginUser.myLectureList.contains(inputLecture)) {
				System.out.println("µ¿ÀÏÇÑ °­ÀÇ¸¦ Ãß°¡Çß½À´Ï´Ù.");
				continue;
			} else {
				// 1Â÷ ¿ä±¸ »çÇ× - ºĞ¹İ °Ë»ç Ãß°¡ 
				// ºĞ¹İ == ¼ö°­½ÅÃ»³»¿ª¿¡ ¾øÀ½ && ¼ö°­½ÅÃ»³»¿ª¿¡ µ¿ÀÏÇÑ ÀÌ¸§ÀÇ °ú¸ñ Á¸Àç
				boolean flag = true;
				for (Lecture lec : loginUser.myLectureList) {
					if (lec.lectureName.equals(inputLecture.lectureName)) {
						flag = false;
						break;
					}
				}

				if (!flag) {
					// ºĞ¹İ ½ÅÃ»½Ã ¸Ş¼¼Áö Ãâ·Â
					System.out.println("ÀÌ¹Ì ¼ö°­ ÁßÀÎ °­ÀÇÀÇ ´Ù¸¥ ºĞ¹İÀ» Ãß°¡Çß½À´Ï´Ù.");
					continue;
				}
			}

			//3. Áßº¹ ½Ã°£´ë °Ë»ç
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
				if (flag_inputDay1_lecDay1)  //Day1, Day1 °°Àº ¿äÀÏ
				{
					if (inputLecture.int_getLectureDay1Stime() > lec.int_getLectureDay1Stime()) {  //ÀÌÈÄ ±³½Ã¿¡ Ãß°¡µÉ ¶§
						if (inputLecture.int_getLectureDay1Stime() < lec.int_getLectureDay1Otime() + 1) {  //±âÁ¸ 01-02¸é 03ºÎÅÍ ½ÃÀÛÇÏ´Â ¼ö¾÷µé Ãß°¡ °¡´É
							flag = false;
//							System.out.println("a");
							break;
						}
					} else if (inputLecture.int_getLectureDay1Stime() < lec.int_getLectureDay1Stime()) {  //ÀÌÀü ±³½Ã¿¡ Ãß°¡µÉ ¶§
						if (inputLecture.int_getLectureDay1Otime() > lec.int_getLectureDay1Stime() - 1) {  //±âÁ¸ 03-04¸é 02±îÁö ³¡³ª´Â ¼ö¾÷ Ãß°¡ °¡´É
							flag = false;
//							System.out.println("a2");
							break;
						}
					} else {  //°°Àº ±³½Ã¿¡ ½ÃÀÛ
						flag = false;
//						System.out.println("a3");
						break;
					}
				}

				if (flag_inputDay1_lecDay2)  //Day1, Day2 °°Àº ¿äÀÏ
				{
					if (inputLecture.int_getLectureDay1Stime() > lec.int_getLectureDay2Stime()) {  //ÀÌÈÄ ±³½Ã¿¡ Ãß°¡µÉ ¶§
						if (inputLecture.int_getLectureDay1Stime() < lec.int_getLectureDay2Otime() + 1) {  //±âÁ¸ 01-02¸é 03ºÎÅÍ ½ÃÀÛÇÏ´Â ¼ö¾÷µé Ãß°¡ °¡´É
							flag = false;
//							System.out.println("b");
							break;
						}
					} else if (inputLecture.int_getLectureDay1Stime() < lec.int_getLectureDay2Stime()) {  //ÀÌÀü ±³½Ã¿¡ Ãß°¡µÉ ¶§
						if (inputLecture.int_getLectureDay1Otime() > lec.int_getLectureDay2Stime() - 1) {  //±âÁ¸ 03-04¸é 02±îÁö ³¡³ª´Â ¼ö¾÷ Ãß°¡ °¡´É
							flag = false;
//							System.out.println("b2");
							break;
						}
					} else {  //°°Àº ±³½Ã¿¡ ½ÃÀÛ
						flag = false;
//						System.out.println("b3");
						break;
					}
				}

				if (flag_inputDay2_lecDay1)  //Day2, Day1 °°Àº ¿äÀÏ
				{
					if (inputLecture.int_getLectureDay2Stime() > lec.int_getLectureDay1Stime()) {  //ÀÌÈÄ ±³½Ã¿¡ Ãß°¡µÉ ¶§
						if (inputLecture.int_getLectureDay2Stime() < lec.int_getLectureDay2Otime() + 1) {  //±âÁ¸ 01-02¸é 03ºÎÅÍ ½ÃÀÛÇÏ´Â ¼ö¾÷µé Ãß°¡ °¡´É
							flag = false;
//							System.out.println("c");
							break;
						}
					} else if (inputLecture.int_getLectureDay2Stime() < lec.int_getLectureDay1Stime()) {  //ÀÌÀü ±³½Ã¿¡ Ãß°¡µÉ ¶§
						if (inputLecture.int_getLectureDay2Otime() > lec.int_getLectureDay1Stime() - 1) {  //±âÁ¸ 03-04¸é 02±îÁö ³¡³ª´Â ¼ö¾÷ Ãß°¡ °¡´É
							flag = false;
//							System.out.println("c2");
							break;
						}
					} else {  //°°Àº ±³½Ã¿¡ ½ÃÀÛ
						flag = false;
//						System.out.println("c3");
						break;
					}
				}
				if (flag_inputDay2_lecDay2)  //Day2, Day2 °°Àº ¿äÀÏ
				{
					if (inputLecture.int_getLectureDay2Stime() > lec.int_getLectureDay2Stime()) {  //ÀÌÈÄ ±³½Ã¿¡ Ãß°¡µÉ ¶§
						if (inputLecture.int_getLectureDay2Stime() < lec.int_getLectureDay2Otime() + 1) {  //±âÁ¸ 01-02¸é 03ºÎÅÍ ½ÃÀÛÇÏ´Â ¼ö¾÷µé Ãß°¡ °¡´É
							flag = false;
//							System.out.println("d");
							break;
						}
					} else if (inputLecture.int_getLectureDay2Stime() < lec.int_getLectureDay2Stime()) {  //ÀÌÀü ±³½Ã¿¡ Ãß°¡µÉ ¶§
						if (inputLecture.int_getLectureDay2Otime() > lec.int_getLectureDay2Stime() - 1) {  //±âÁ¸ 03-04¸é 02±îÁö ³¡³ª´Â ¼ö¾÷ Ãß°¡ °¡´É
							flag = false;
//							System.out.println("d2");
							break;
						}
					} else {  //°°Àº ±³½Ã¿¡ ½ÃÀÛ
						flag = false;
//						System.out.println("d3");
						break;
					}
				}

				if (!flag) {
					System.out.println("±âÁ¸ ½Ã°£Ç¥¿Í °­ÀÇ ½Ã°£ÀÌ °ãÄ¨´Ï´Ù.");
					continue;
				}
			}


				//4. ÀÎ¿øÁ¦ÇÑ °Ë»ç
				if (inputLecture.getLectureCnum() >= inputLecture.getLectureMnum()) {
//				System.out.println("°ú¸ñ ÄÚµå: " + inputLecture.getLectureCode());
//				System.out.println("ÇöÀÎ¿ø: " + inputLecture.getLectureCnum() + " " + inputLecture.lectureCnum + " / Max:" + inputLecture.getLectureMnum());
					System.out.println("¿©¼®ÀÌ ¾ø½À´Ï´Ù.");
					continue;
				}

				//5. 1Â÷ ¿ä±¸»çÇ× - ÃÖ´ë ÇĞÁ¡ ÇÑµµ °Ë»ç
				if (loginUser.myCredit + inputLecture.getLectureCredit() > User.MAX_CREDIT) {
					// ÃÖ´ë ÇĞÁ¡ ÇÑµµ ÃÊ°ú½Ã ¸Ş¼¼Áö Ãâ·Â
					System.out.println(User.MAX_CREDIT + "ÇĞÁ¡À» ÃÊ°úÇØ ½ÅÃ»ÇÒ ¼ö ¾ø½À´Ï´Ù.");
					continue;
				}

				//6. 2Â÷ ¿ä±¸»çÇ× - AÇĞÁ¡ ÀÌ»ó Àç¼ö°­ ±İÁö
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
					System.out.println("AÇĞÁ¡ ÀÌ»ó ¹Ş¾Ò´ø °ú¸ñÀº Àç¼ö°­ÇÒ ¼ö ¾ø½À´Ï´Ù.");
					continue;
				}

				// °Ë»ç Åë°ú½Ã break
				break;
			}

			// 2Â÷ ¿ä±¸»çÇ× - ¼ö°­½ÅÃ»ÇÑ °ú¸ñÀÇ µî±Ş ÃÊ±âÈ­
			filereader.lecturelist.get(input).grade = "X";

			// ¼ö°­½ÅÃ»ÇÑ °ú¸ñ loginUserÀÇ myLectureList¿¡ ÀúÀå
			loginUser.myLectureList.add(filereader.lecturelist.get(input));

			// loginUserÀÇ myCredit ¼ö°­½ÅÃ»ÇÑ °ú¸ñÀÇ ÇĞÁ¡¸¸Å­ Áõ°¡
			loginUser.myCredit += filereader.lecturelist.get(input).getLectureCredit();

			// ÇĞ¹ø.txt ÆÄÀÏ ¾÷µ¥ÀÌÆ®
			updateIdFile();

			// lecturelistÀÇ lectureÀÇ ¼ö°­½ÅÃ»ÀÎ¿ø ¾÷µ¥ÀÌÆ®
			filereader.lecturelist.get(input).plusLectureCnum();

			// lecture_list ÇöÀç ¼ö°­½ÅÃ» ÀÎ¿ø ¾÷µ¥ÀÌÆ®
			filereader.updateLectureFile(input);

			// ¼ö°­½ÅÃ» ¿Ï·á
			System.out.println("¼ö°­½ÅÃ»ÀÌ ¿Ï·áµÇ¾ú½À´Ï´Ù.");
//		System.out.println("ÇĞÁ¡: "+loginUser.myCredit);
		}
	
	// ÇĞ¹ø.txt ÆÄÀÏ ¾÷µ¥ÀÌÆ® ¸Ş¼Òµå
	private void updateIdFile() {
		String content = "";
		int count = 0;
		for (Lecture lecture : loginUser.myLectureList)
			content += date.getYear() + " " + lecture.lectureCode + " " + lecture.lectureName + " " + lecture.lectureCredit + " " + lecture.grade + "\n";
		
		// 2Â÷ ¿ä±¸»çÇ× - ¼ö°­Çß´ø °­ÀÇ Ãß°¡
		for (Lecture lecture : loginUser.pastLectureList) {
			content += loginUser.pastLectureListYear.get(count) + " " + lecture.lectureCode + " " + lecture.lectureName + " " + lecture.lectureCredit + " " + lecture.grade + "\n";
			count++;
			// ¼ö°­Çß´ø ¿¬µµ ±×´ë·Î º¯°æ
		}
		
		try {
			writer = new BufferedWriter(new FileWriter(loginUser.FILEPATH));
			writer.write(content);
			writer.close();
		} catch (IOException e) {
//			System.out.println(loginUser.FILEPATH+": ¾²±â ½ÇÆĞ");
			System.exit(0); // ¿À·ù ¹ß»ı½Ã ÇÁ·Î±×·¥ Á¾·á
		}
	}
}