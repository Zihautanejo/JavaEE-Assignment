
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.Assert.*;

public class AnnotationTest {

    Main m;
    MyClass myClass = new MyClass(10);
    OurClass ourClass = new OurClass(20);
    YourClass yourClass = new YourClass(30);

    @BeforeEach
    public void init() {
        myClass.setX(10);
        ourClass.setX(20);
        yourClass.setX(30);
    }

    @Test
    public void MyClassTest() {
        InitCheck.check(myClass);
        assertEquals(100,myClass.getX());
    }

    @Test
    public void OurClassTest() {
        InitCheck.check(ourClass);
        assertEquals(10,ourClass.getX());
    }

    @Test
    public void YourClassTest(){
        InitCheck.check(yourClass);
        assertEquals(40,yourClass.getX());
    }

}
 