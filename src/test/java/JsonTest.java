import org.junit.Test;
import org.linuxprobe.luava.json.JacksonUtils;

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
}

class Student {
    private String nameT;
    private String ageS;
    private Date now;

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
}