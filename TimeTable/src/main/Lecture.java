package main;

import java.util.List;

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
	protected String grade; // 2차 요구사항 - 등급 추가
	public String getGrade() {
		return grade;
	}
	
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
	
	// 2차 요구사항 - 수강했던 과목에 대한 객체 생성자
	public Lecture(String lectureCode, String lectureName, String lectureCredit, String grade) {
		this.lectureCode = lectureCode;
		this.lectureName = lectureName;
		this.lectureCredit = lectureCredit;
		this.grade = grade;
	}

	public Lecture() {

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

	public int int_getLectureDay1Stime() {
		int LectureDay1Stime = 0;
		try {
			LectureDay1Stime = Integer.parseInt(lectureDay1Stime);
		} catch (NumberFormatException e) {
			System.exit(0); // 오류 발생시 프로그램 종료
		}
		return LectureDay1Stime;
	}

	public int int_getLectureDay2Stime() {
		int LectureDay2Stime = 0;
		try {
			LectureDay2Stime = Integer.parseInt(lectureDay2Stime);
		} catch (NumberFormatException e) {
			System.exit(0); // 오류 발생시 프로그램 종료
		}
		return LectureDay2Stime;
	}

	public int int_getLectureDay1Otime() {
		int LectureDay1Otime = 0;
		try {
			LectureDay1Otime = Integer.parseInt(lectureDay1Otime);
		} catch (NumberFormatException e) {
			System.exit(0); // 오류 발생시 프로그램 종료
		}
		return LectureDay1Otime;
	}

	public int int_getLectureDay2Otime() {
		int LectureDay2Otime = 0;
		try {
			LectureDay2Otime = Integer.parseInt(lectureDay2Otime);
		} catch (NumberFormatException e) {
			System.exit(0); // 오류 발생시 프로그램 종료
		}
		return LectureDay2Otime;
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
			String time = String.format("%s %s-%s", lectureDay1, lectureDay1Stime, lectureDay1Otime);
			System.out.printf("%-10s %-20s %-20s %-20s %10s%n", lectureCode, lectureName, time, enrollment,lectureCredit);
		} else {// 요일 두개
			String time = String.format("%s %s-%s, %s %s-%s", lectureDay1, lectureDay1Stime, lectureDay1Otime, lectureDay2, lectureDay2Stime, lectureDay2Otime);
			System.out.printf("%-10s %-20s %-20s %-20s %10s%n", lectureCode, lectureName, time, enrollment,lectureCredit);
		}
	}
	
	// 강의실, 강사 추가
	public void printMyLectureList() {
		if (lectureDay2.isEmpty()) {// 요일 한개
			String time = String.format("%s %s-%s", lectureDay1, lectureDay1Stime, lectureDay1Otime);
			System.out.printf("%-10s %-10s %-10s %-20s %-12s %-10s%n", lectureCode, lectureName ,lectureCredit,time, lectureRoomDay1, lecturer);
		} else {// 요일 두개
			String time = String.format("%s %s-%s, %s %s-%s", lectureDay1, lectureDay1Stime, lectureDay1Otime, lectureDay2, lectureDay2Stime, lectureDay2Otime);
			String lectureRoom = "";
			// 강의실 2개인 경우
			if(!lectureRoomDay1.equals(lectureRoomDay2)) {
				 lectureRoom = String.format("%s / %s", lectureRoomDay1, lectureRoomDay2);
		    // 강의실 1개인 경우
			} else {
				 lectureRoom = lectureRoomDay1;
			}
			// 출력
			System.out.printf("%-10s %-10s %-10s %-20s %-12s %-10s%n", lectureCode, lectureName,lectureCredit,time, lectureRoom,lecturer);
		}

	}

	// 2차 요구사항 정렬 위한 Getter 추가
		public String getLectureDay1Stime() {
			return lectureDay1Stime;
		}

		// 2차 요구사항 정렬 위한 Getter 추가
		public String getLectureDay1Otime() {
			return lectureDay1Otime;
		}
	
	
	@Override
	public int compareTo(Lecture other) {
		// 요일 숫자 매핑
	    List<String> daysOfWeek = List.of("월", "화", "수", "목", "금");
	    
	    // 요일 비교
	    int dayComparison = daysOfWeek.indexOf(this.getLectureDay1()) - daysOfWeek.indexOf(other.getLectureDay1());
	    if (dayComparison != 0) {
	        return dayComparison;
	    }

	    // 시간 비교
	    // 시작시간 
	    int lectureStartTime = Integer.parseInt(getLectureDay1Stime());
	    int otherlectureStartTime = Integer.parseInt(other.getLectureDay1Stime());
	    
	    // 종료시간
	    int lectureEndTime = Integer.parseInt(getLectureDay1Otime());
	    int OtherlectureEndTime	= Integer.parseInt(other.getLectureDay1Otime());
	    
	    // 시작시간 비교결과
	    int timeComparison1 = Integer.compare(lectureStartTime, otherlectureStartTime);
	    // 종료시간 비교결과
	    int timeComparison2 = Integer.compare(lectureEndTime, OtherlectureEndTime);
	    
	    // 시작시간 비교
	    if (timeComparison1 != 0) {
	        return timeComparison1;
	    } // 종료시간 비교
	    else if(timeComparison2 != 0) {
	    	return timeComparison2;
	    }
	    // 과목번호 비교
	    return this.lectureCode.compareTo(other.lectureCode);
	}
}