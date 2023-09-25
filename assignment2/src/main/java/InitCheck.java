import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InitCheck {

    //传的是一个实例化的对象
    public static void check(Object Ob) {
        //检查函数是否有注解，
        try {
            for (Method method : Ob.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(InitMethod.class)) {

                    //这里的想法是要根据注解的变量来判断这个函数是否要传参进行运算
                    //但遇到的问题是 如何判断这个哪一个函数，在subnum和addnum都不为零的情况下
                    method.invoke(Ob);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}