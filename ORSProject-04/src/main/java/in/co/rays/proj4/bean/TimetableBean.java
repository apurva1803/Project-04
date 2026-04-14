package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * TimetableBean represents an exam timetable entry.
 * It contains course, subject, semester, exam date and exam time details.
 *
 * Author Apurva Deshmukh
 */
public class TimetableBean extends BaseBean{

	/** Semester of the exam */
    private String semester;

    /** Description for the timetable entry */
    private String description;

    /** Exam date */
    private Date examDate;

    /** Exam time */
    private String examTime;

    /** Course ID (FK) */
    private long courseId;

    /** Course name */
    private String courseName;

    /** Subject ID (FK) */
    private long subjectId;

    /** Subject name */
    private String subjectName;

    /** Returns semester */
    public String getSemester() {
        return semester;
    }

    /** Sets semester */
    public void setSemester(String semester) {
        this.semester = semester;
    }

    /** Returns description */
    public String getDescription() {
        return description;
    }

    /** Sets description */
    public void setDescription(String description) {
        this.description = description;
    }

    /** Returns exam date */
    public Date getExamDate() {
        return examDate;
    }

    /** Sets exam date */
    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    /** Returns exam time */
    public String getExamTime() {
        return examTime;
    }

    /** Sets exam time */
    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    /** Returns course ID */
    public long getCourseId() {
        return courseId;
    }

    /** Sets course ID */
    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    /** Returns course name */
    public String getCourseName() {
        return courseName;
    }

    /** Sets course name */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /** Returns subject ID */
    public long getSubjectId() {
        return subjectId;
    }

    /** Sets subject ID */
    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    /** Returns subject name */
    public String getSubjectName() {
        return subjectName;
    }

    /** Sets subject name */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /** Dropdown key (not used in this bean) */
    @Override
    public String getKey() {
        return null;
    }

    /** Dropdown value (not used in this bean) */
    @Override
    public String getValue() {
        return null;
    }
	
}
