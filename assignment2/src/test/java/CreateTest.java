
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CreateTest {
    Main m;

    //采用强制类型转换
    @Test
    public void RightCreate() {
        MyClass myClass = (MyClass) this.m.Create(MyClass.class);
        OurClass ourClass = (OurClass) this.m.Create(OurClass.class);
        YourClass yourClass = (YourClass) this.m.Create(YourClass.class);

        assertEquals(0, myClass.getX());
        assertEquals(0, ourClass.getX());
        assertEquals(0,yourClass.getX());
    }

    @Test
    public void RightCreateWithPara() {
        MyClass myClass = (MyClass) this.m.Create(MyClass.class,10);
        OurClass ourClass = (OurClass) this.m.Create(OurClass.class,20);
        YourClass yourClass = (YourClass) this.m.Create(YourClass.class,30);

        assertEquals(10, myClass.getX());
        assertEquals(20, ourClass.getX());
        assertEquals(30,yourClass.getX());
    }


}