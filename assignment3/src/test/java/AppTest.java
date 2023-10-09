import dao.impl.WhiteCat;
import framework.BeanException;
import framework.MyApplicationContext;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppTest {


    /**
     * 测试依赖注入是否成功，注入的对象和原对象是否为同一个
     * @throws BeanException
     */
    @Test
    public void testApp() throws BeanException {

        MyApplicationContext.Start("applicationContext.xml");
        Map<String,Object> map = MyApplicationContext.getMap();
        WhiteCat whiteCat = (WhiteCat) map.get("cat");
        assertEquals(map.get("fish"),whiteCat.getFish());
    }

    @Test
    public void testReadFile(){

        BeanException exception = assertThrows(BeanException.class,
                ()->MyApplicationContext.Start("app.xml"));
        assertEquals(exception.getErrorType(),BeanException.ErrorType.DOCUMENT_OPEN_ERROR);

    }

    @Test
    public void testAnalysisFile(){

        BeanException exception = assertThrows(BeanException.class,
                ()->MyApplicationContext.AnalysisFile(null));
        assertEquals(exception.getErrorType(),BeanException.ErrorType.DOCUMENT_READ_ERROR);

    }

    @Test
    public void testScanList(){

        BeanException exception = assertThrows(BeanException.class,
                ()->MyApplicationContext.Start("app-root-error.xml"));
        assertEquals(exception.getErrorType(),BeanException.ErrorType.BEANROOT_ERROR);

        BeanException exception1 = assertThrows(BeanException.class,
                ()->MyApplicationContext.Start("app-element-error.xml"));
        assertEquals(exception1.getErrorType(),BeanException.ErrorType.ELEMENT_ERROR);

    }

    @Test
    public void testInjection(){

        BeanException exception1 = assertThrows(BeanException.class,
                MyApplicationContext::Injection);
        assertEquals(exception1.getErrorType(),BeanException.ErrorType.BEAN_ADD_ERROR);


    }

    @Test
    public void testFindRef1(){

        BeanException exception = assertThrows(BeanException.class,
                ()->MyApplicationContext.Start("app-ref-notfound.xml"));
        assertEquals(exception.getErrorType(),BeanException.ErrorType.REFBEAN_NOTFOUND);

    }

    @Test
    public void testFinderRef2(){

        BeanException exception1 = assertThrows(BeanException.class,
                ()->MyApplicationContext.Start("app-method-notfound.xml"));
        assertEquals(exception1.getErrorType(),BeanException.ErrorType.METHOD_NOTFOUND);

    }

    @Test
    public void testgetClass(){

        BeanException exception1 = assertThrows(BeanException.class,
                ()->MyApplicationContext.Start("app-class-error.xml"));
        assertEquals(exception1.getErrorType(),BeanException.ErrorType.CLASS_NOTFOUND);

    }


}
