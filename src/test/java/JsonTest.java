import org.junit.Test;
import org.rdlinux.luava.json.JacksonUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class JsonTest {
    @Test
    public void test1() {
        Student student = new Student();
        student.setNameT("test");
        student.setAgeS("sdf");
        student.setNow(new Date());
        System.out.println(JacksonUtils.toJsonString(student));
        System.out.println(JacksonUtils.toSnakeCaseJsonString(student));
        student = JacksonUtils.conversion("{\"nameT\":\"test\",\"ageS\":\"sdf\",\"now\":\"2018-12-13T09:27:41.000 UTC\"}", Student.class);
        System.out.println(JacksonUtils.toJsonString(student));
    }

    @Test
    public void localDateTime() {
        Student student = new Student();
        student.setBirthday(LocalDateTime.now());
        String json = JacksonUtils.toJsonString(student);
        System.out.println(json);
        student = JacksonUtils.conversion(json, Student.class);
        System.out.println(JacksonUtils.toJsonString(student));
        LocalDateTime localDateTime = LocalDateTime.now();
    }

    @Test
    public void localDate() {
        Student student = new Student();
        student.setLocalDate(LocalDate.now());
        String json = JacksonUtils.toJsonString(student);
        System.out.println(json);
        student = JacksonUtils.conversion(json, Student.class);
        System.out.println(JacksonUtils.toJsonString(student));
    }

    @Test
    public void localTime() {
        Student student = new Student();
        student.setLocalTime(LocalTime.now());
        String json = JacksonUtils.toJsonString(student);
        System.out.println(json);
        student = JacksonUtils.conversion(json, Student.class);
        System.out.println(JacksonUtils.toJsonString(student));
    }
}

class Student {
    private String nameT;
    private String ageS;
    private Date now;
    private LocalDateTime birthday;
    private LocalDate localDate;
    private LocalTime localTime;

    public String getNameT() {
        return this.nameT;
    }

    public void setNameT(String nameT) {
        this.nameT = nameT;
    }

    public String getAgeS() {
        return this.ageS;
    }

    public void setAgeS(String ageS) {
        this.ageS = ageS;
    }

    public Date getNow() {
        return this.now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public LocalDateTime getBirthday() {
        return this.birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public LocalDate getLocalDate() {
        return this.localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return this.localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }
}