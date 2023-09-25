
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReadTest {
    Main m;

    @Test
    public void rightReader(){
        assertEquals("MyClass", this.m.Reader("myapp","MyClass"));
        assertEquals("OurClass", this.m.Reader("myapp","OurClass"));
        assertEquals("YourClass", this.m.Reader("myapp","YourClass"));
    }

    @Test
    public void wrongFilename(){
        assertEquals(null,this.m.Reader("wrongFilename","MyClass"));
    }

    @Test
    public void wrongKey() {
        assertEquals(null, this.m.Reader("myapp", "wrongKey"));
    }
}