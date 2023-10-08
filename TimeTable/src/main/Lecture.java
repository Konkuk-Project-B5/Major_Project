package main;

public class Lecture {

	protected String lectureCode;
	protected String lectureName;
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

	public String getLectureDay1() {
		return lectureDay1;
	}

	public String getLectureDay2() {
		return lectureDay2;
	}

	public String getLectureStime() {
		return lectureStime;
	}

	public String getLectureOtime() {
		return lectureOtime;
	}

	public String getLectureCnum() {
		return lectureCnum;
	}

	public void setLectureCnum(String lectureCnum) { // setter이거밖에 없음
		this.lectureCnum = lectureCnum;
	}

	public String getLectureMnum() {
		return lectureMnum;
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


