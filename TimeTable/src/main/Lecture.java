package main;

public class Lecture implements Comparable<Lecture> {
	protected String lectureCode;
	protected String lectureName;
	protected String lectureCredit; // 1차 요구사항 - 강의 학점 추가
	protected String lectureDay1;
	protected String lectureDay2;
	protected String lectureDay1Stime;
	protected String lectureDay1Otime;
	protected String lectureDay2Stime;//2차요구사항 - 요일에 따라 강의 시간 다르게 가능
	protected String lectureDay2Otime;
	protected String lectureCnum;
	protected String lectureMnum;
	protected String lecturer; //2차요구사항 - 교강사 추가
	protected String lectureRoomDay1;  //2차요구사항 - 강의실 추가
	protected String lectureRoomDay2; //2차요구사항 - 강의실 추가
	
	// 2차 요구사항 - 등급 추가
	String grade;
	
	// // Comparable 학번순 정렬 규칙
	// @Override
	// public int compareTo(Lecture other) {
	// 	return this.lectureCode.compareTo(other.lectureCode);
	// }
	
	// public Lecture() {
	// 	super();
	// }

	// public String getLectureCode() {
	// 	return lectureCode;
	// }

	// public String getLectureName() {
	// 	return lectureName;
	// }
	
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
		//	 Stime = Integer.parseInt(lectureStime);
		} catch (NumberFormatException e) {
			System.exit(0); // 오류 발생시 프로그램 종료
		}
		return Stime;
	}

	public int getLectureOtime() {
		int Otime = 0;
		try {
		//	Otime = Integer.parseInt(lectureOtime);
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
		if(n < 10) {
			this.lectureCnum = "0" +Integer.toString(++n);
		} else
			this.lectureCnum = Integer.toString(++n);
		
	}

	public void minusLectureCnum() {
		int n = getLectureCnum();
		if(n < 10) {
			this.lectureCnum = "0" +Integer.toString(--n);
		} else
			this.lectureCnum = Integer.toString(--n);
		
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
		//	String time = String.format("%s %s-%s", lectureDay1, lectureStime, lectureOtime);
		//	System.out.printf("%-10s %-20s %-20s %-20s %10s%n", lectureCode, lectureName, time, enrollment,lectureCredit);
		} else {// 요일 두개
		//	String time = String.format("%s %s-%s, %s %s-%s", lectureDay1, lectureStime, lectureOtime, lectureDay2, lectureStime, lectureOtime);
	//		System.out.printf("%-10s %-20s %-20s %-20s %10s%n", lectureCode, lectureName, time, enrollment,lectureCredit);
		}
	}
	
	public void printMyLectureList() {
		if (lectureDay2.isEmpty()) {// 요일 한개
	//		String time = String.format("%s %s-%s", lectureDay1, lectureStime, lectureOtime);
	//		System.out.printf("%-10s %-10s %-10s %-20s%n", lectureCode, lectureName ,lectureCredit,time);
		} else {// 요일 두개
	//		String time = String.format("%s %s-%s, %s %s-%s", lectureDay1, lectureStime, lectureOtime, lectureDay2, lectureStime, lectureOtime);
		//	System.out.printf("%-10s %-10s %-10s %-20s%n", lectureCode, lectureName,lectureCredit,time);
		}

	}

	@Override
	public int compareTo(Lecture o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
	}

}


