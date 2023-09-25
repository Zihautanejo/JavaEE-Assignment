import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GetClassTest {

    Main m;

    @Test
    public void rightGetClass(){
        assertEquals(MyClass.class, this.m.getClass("MyClass"));
        assertEquals(OurClass.class, this.m.getClass("OurClass"));
        assertEquals(YourClass.class, this.m.getClass("YourClass"));
    }

    @Test
    public void wrongRoad(){
        assertEquals(null, this.m.getClass("wrongClass"));
    }
}