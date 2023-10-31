package main;

public class Lecture {

	protected String lectureCode;
	protected String lectureName;
	protected String lectureCredit; // 1차 요구사항 - 강의 학점 추가
	protected String lectureDay1;
	protected String lectureDay2;
	protected String lectureStime;
	protected String lectureOtime;
	protected String lectureCnum;
	protected String lectureMnum;

	public Lecture() {
		super();
	}

	public String getLectureCode() {
		return lectureCode;
	}

	public String getLectureName() {
		return lectureName;
	}
	
	// 1차 요구사항 - 학점 getter
	public int getLectureCredit() {
		int credit = 0;
		try {
			credit = Integer.parseInt(lectureCredit);
		} catch (NumberFormatException e) {
			System.exit(0); // 오류 발생시 프로그램 종료
		}
		return credit;
	}
	
	public String getLectureDay1() {
		return lectureDay1;
	}

	public String getLectureDay2() {
		return lectureDay2;
	}

	public int getLectureStime() {
		int Stime = 0;
		try {
			 Stime = Integer.parseInt(lectureStime);
		} catch (NumberFormatException e) {
			System.exit(0); // 오류 발생시 프로그램 종료
		}
		return Stime;
	}

	public int getLectureOtime() {
		int Otime = 0;
		try {
			Otime = Integer.parseInt(lectureOtime);
		} catch (NumberFormatException e) {
			System.exit(0); // 오류 발생시 프로그램 종료
		}
		return Otime;
	}

	public int getLectureCnum() {
		int Cnum = 0;
		try {
			Cnum = Integer.parseInt(lectureCnum);
		} catch (NumberFormatException e) {
			System.exit(0); // 오류 발생시 프로그램 종료
		}
		return Cnum;
	}

	public void setLectureCnum(String lectureCnum) { // setter이거밖에 없음
		this.lectureCnum = lectureCnum;
	}

	public void plusLectureCnum() {
		int n = getLectureCnum();
		this.lectureCnum = Integer.toString(++n);
	}

	public int getLectureMnum() {
		int Mnum = 0;
		try {
			Mnum = Integer.parseInt(lectureMnum);
		} catch (NumberFormatException e) {
			System.exit(0); // 오류 발생시 프로그램 종료
		}
		return Mnum;
	}

	public void printLectureList() {
		

			String enrollment = String.format("%s / %s", lectureCnum, lectureMnum);

			if (lectureDay2.isEmpty()) {// 요일 한개
				String time = String.format("%s %s-%s", lectureDay1, lectureStime, lectureOtime);
				System.out.printf("%-10s %-20s %-20s %-20s%n", lectureCode, lectureName, time, enrollment);
			} else {// 요일 두개
				String time = String.format("%s %s-%s, %s %s-%s", lectureDay1, lectureStime, lectureOtime, lectureDay2, lectureStime, lectureOtime);
				System.out.printf("%-10s %-20s %-20s %-20s%n", lectureCode, lectureName, time, enrollment);
			}

		}

	}