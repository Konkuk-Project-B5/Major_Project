package main;

import java.util.List;

public class Lecture implements Comparable<Lecture> {
	protected String lectureCode;
	protected String lectureName;
	protected String lectureCredit; // 1�� �䱸���� - ���� ���� �߰�
	protected String lectureDay1;
	protected String lectureDay2;
	protected String lectureDay1Stime;
	protected String lectureDay1Otime;
	protected String lectureDay2Stime;//2���䱸���� - ���Ͽ� ���� ���� �ð� �ٸ��� ����
	protected String lectureDay2Otime;
	protected String lectureCnum;
	protected String lectureMnum;
	protected String lecturer; //2���䱸���� - ������ �߰�
	protected String lectureRoomDay1;  //2���䱸���� - ���ǽ� �߰�
	protected String lectureRoomDay2; //2���䱸���� - ���ǽ� �߰�
	protected String grade; // 2�� �䱸���� - ��� �߰�
	public String getGrade() {
		return grade;
	}
	
	// // Comparable �й��� ���� ��Ģ
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
	
	// 2�� �䱸���� - �����ߴ� ���� ���� ��ü ������
	public Lecture(String lectureCode, String lectureName, String lectureCredit, String grade) {
		this.lectureCode = lectureCode;
		this.lectureName = lectureName;
		this.lectureCredit = lectureCredit;
		this.grade = grade;
	}

	public Lecture() {

	}

	// 1�� �䱸���� - ���� getter
	public int getLectureCredit() {
		int credit = 0;
		try {
			credit = Integer.parseInt(lectureCredit);
		} catch (NumberFormatException e) {
			System.exit(0); // ���� �߻��� ���α׷� ����
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
			System.exit(0); // ���� �߻��� ���α׷� ����
		}
		return LectureDay1Stime;
	}

	public int int_getLectureDay2Stime() {
		int LectureDay2Stime = 0;
		try {
			LectureDay2Stime = Integer.parseInt(lectureDay2Stime);
		} catch (NumberFormatException e) {
			System.exit(0); // ���� �߻��� ���α׷� ����
		}
		return LectureDay2Stime;
	}

	public int int_getLectureDay1Otime() {
		int LectureDay1Otime = 0;
		try {
			LectureDay1Otime = Integer.parseInt(lectureDay1Otime);
		} catch (NumberFormatException e) {
			System.exit(0); // ���� �߻��� ���α׷� ����
		}
		return LectureDay1Otime;
	}

	public int int_getLectureDay2Otime() {
		int LectureDay2Otime = 0;
		try {
			LectureDay2Otime = Integer.parseInt(lectureDay2Otime);
		} catch (NumberFormatException e) {
			System.exit(0); // ���� �߻��� ���α׷� ����
		}
		return LectureDay2Otime;
	}

	public int getLectureCnum() {
		int Cnum = 0;
		try {
			Cnum = Integer.parseInt(lectureCnum);
		} catch (NumberFormatException e) {
			System.exit(0); // ���� �߻��� ���α׷� ����
		}
		return Cnum;
	}

	public void setLectureCnum(String lectureCnum) { // setter�̰Źۿ� ����
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
			System.exit(0); // ���� �߻��� ���α׷� ����
		}
		return Mnum;
	}

	public void printLectureList() {
		String enrollment = String.format("%s / %s", lectureCnum, lectureMnum);

		if (lectureDay2.isEmpty()) {// ���� �Ѱ�
			String time = String.format("%s %s-%s", lectureDay1, lectureDay1Stime, lectureDay1Otime);
			System.out.printf("%-10s %-20s %-20s %-20s %10s%n", lectureCode, lectureName, time, enrollment,lectureCredit);
		} else {// ���� �ΰ�
			String time = String.format("%s %s-%s, %s %s-%s", lectureDay1, lectureDay1Stime, lectureDay1Otime, lectureDay2, lectureDay2Stime, lectureDay2Otime);
			System.out.printf("%-10s %-20s %-20s %-20s %10s%n", lectureCode, lectureName, time, enrollment,lectureCredit);
		}
	}
	
	// ���ǽ�, ���� �߰�
	public void printMyLectureList() {
		if (lectureDay2.isEmpty()) {// ���� �Ѱ�
			String time = String.format("%s %s-%s", lectureDay1, lectureDay1Stime, lectureDay1Otime);
			System.out.printf("%-10s %-10s %-10s %-20s %-12s %-10s%n", lectureCode, lectureName ,lectureCredit,time, lectureRoomDay1, lecturer);
		} else {// ���� �ΰ�
			String time = String.format("%s %s-%s, %s %s-%s", lectureDay1, lectureDay1Stime, lectureDay1Otime, lectureDay2, lectureDay2Stime, lectureDay2Otime);
			String lectureRoom = "";
			// ���ǽ� 2���� ���
			if(!lectureRoomDay1.equals(lectureRoomDay2)) {
				 lectureRoom = String.format("%s / %s", lectureRoomDay1, lectureRoomDay2);
		    // ���ǽ� 1���� ���
			} else {
				 lectureRoom = lectureRoomDay1;
			}
			// ���
			System.out.printf("%-10s %-10s %-10s %-20s %-12s %-10s%n", lectureCode, lectureName,lectureCredit,time, lectureRoom,lecturer);
		}

	}

	// 2�� �䱸���� ���� ���� Getter �߰�
		public String getLectureDay1Stime() {
			return lectureDay1Stime;
		}

		// 2�� �䱸���� ���� ���� Getter �߰�
		public String getLectureDay1Otime() {
			return lectureDay1Otime;
		}
	
	
	@Override
	public int compareTo(Lecture other) {
		// ���� ���� ����
	    List<String> daysOfWeek = List.of("��", "ȭ", "��", "��", "��");
	    
	    // ���� ��
	    int dayComparison = daysOfWeek.indexOf(this.getLectureDay1()) - daysOfWeek.indexOf(other.getLectureDay1());
	    if (dayComparison != 0) {
	        return dayComparison;
	    }

	    // �ð� ��
	    // ���۽ð� 
	    int lectureStartTime = Integer.parseInt(getLectureDay1Stime());
	    int otherlectureStartTime = Integer.parseInt(other.getLectureDay1Stime());
	    
	    // ����ð�
	    int lectureEndTime = Integer.parseInt(getLectureDay1Otime());
	    int OtherlectureEndTime	= Integer.parseInt(other.getLectureDay1Otime());
	    
	    // ���۽ð� �񱳰��
	    int timeComparison1 = Integer.compare(lectureStartTime, otherlectureStartTime);
	    // ����ð� �񱳰��
	    int timeComparison2 = Integer.compare(lectureEndTime, OtherlectureEndTime);
	    
	    // ���۽ð� ��
	    if (timeComparison1 != 0) {
	        return timeComparison1;
	    } // ����ð� ��
	    else if(timeComparison2 != 0) {
	    	return timeComparison2;
	    }
	    // �����ȣ ��
	    return this.lectureCode.compareTo(other.lectureCode);
	}
}