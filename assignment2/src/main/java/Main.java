import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Properties;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args){
        String road = Reader("myapp","MyClass");
        Class ObClass = getClass(road);
        Object ob = Create(ObClass,10);
        InitCheck.check(ob);
    }

    ///下面的函数必须都要用static修饰，不然test就会报错，为什么

    //用classpath加载属性文件
    //存在的问题：
    //在try/catch的return null是否合理,应该怎么处理比较好
    //异常的处理，这样只打印处理栈合适吗
    //类加载器的写法里，Main.class这样写的理由是什么
    public static String Reader(String filename,String key){
        Properties props = new Properties();
        try(InputStream input = Main.class.getResourceAsStream("/"+filename+".properties")){
            if(input == null)
            {return null;}
            props.load(input);
            return props.getProperty(key);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    //通过类名来获取变量
    //这里需要弄清楚Class类和类的实例,以Car类为例，弄清楚三个参数的不同
    //Car / car / Class 之间的不同和转换
    //可以参考以下的文章
    //https://zhuanlan.zhihu.com/p/37701743
    public static Class getClass(String road){
        try{
            Class obClass = Class.forName(road);
            return obClass;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //通过输入的对象类实例化和初始化
    public static Object Create (Class obClass) {
        try{
            Object ob = obClass.newInstance();
            return ob;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Object Create (Class obClass, int n){
        try{
            Constructor constructor = obClass.getConstructor(int.class);
            Object ob= constructor.newInstance(n);//使用有参数构造函数创建对象
            return ob;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}