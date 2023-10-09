package framework;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MyApplicationContext {

    public static void main(String[] args ) throws BeanException {

       Start("applicationContext.xml");

    }
    //用来存放Bean对象的Map容器
    private static Map<String,Object> beans = new HashMap<String,Object>();
    private static List<BeanDefinition> BeanList = new ArrayList<>();

    public static Map<String,Object> getMap(){
        return beans;
    }

    public static void Start(String s) throws BeanException {
        Document document = ReadFile(s);
        List<Element> elements = AnalysisFile(document);
        ScanList(elements);
        Injection();
    }


    //根据指定的xml返回一个Document对象
    public static Document ReadFile(String fileName) throws BeanException {
        Document document = null;
        try{
            SAXReader saxReader =new SAXReader();
            ClassLoader classLoader = MyApplicationContext.class.getClassLoader();
            //这里注意，不能直接用filename来创建文件对象，因为classpath下的路径在java文件夹下，找不到resource文件下的资源
            File file = new File(classLoader.getResource(fileName).getFile());
            document = saxReader.read(file);
        }catch(DocumentException | NullPointerException e){
            throw new BeanException(BeanException.ErrorType.DOCUMENT_OPEN_ERROR, "打开文件失败"+e.getMessage());
        }
        return document;
    }

    //根据Document对象，获取bean的列表对象
    public static List<Element> AnalysisFile(Document dom) throws BeanException{
        try{
            if(dom == null){throw new BeanException(BeanException.ErrorType.DOCUMENT_READ_ERROR, "读取文件失败");}
            //获取根节点下所有标签为bean的节点
            Element root = dom.getRootElement();
            return root.elements("bean");
        } catch (NullPointerException e){
            throw new BeanException(BeanException.ErrorType.BEANROOT_ERROR, "无法获取文件中的root节点"+e.getMessage());
        }
    }

    public static void ScanList(List<Element> list) throws BeanException{
        try{
            if (list.isEmpty()){
                throw new BeanException(BeanException.ErrorType.BEANROOT_ERROR, "从root节点无法获取‘bean’标签");
            }
            for(Element e: list){
                BeanDefinition BD = new BeanDefinition();
                BD.idName = e.attributeValue("id");
                BD.className =e.attributeValue("class");
                BD.ob =CreateBean(getClass((BD.className)));

                List<Element> propertyElements =e.elements("property");
                for(Element pe: propertyElements){
                    BD.properties.put(pe.attributeValue("ref"),pe.attributeValue("name"));
                }

                BeanList.add(BD);
            }
        }catch (NullPointerException e){
            throw new BeanException(BeanException.ErrorType.ELEMENT_ERROR, "从element中获取信息错误"+e.getMessage());
        }

    }

    //对list进行扫描，把ref有值的对象注入
    public static void Injection() throws BeanException {
        try{

            if(BeanList.isEmpty()) {
                throw new BeanException(BeanException.ErrorType.BEAN_ADD_ERROR, "没有成功把创建对象传入列表");
            }
            for(BeanDefinition bd: BeanList){
                if(!bd.properties.isEmpty()){ FindRef(bd);}
                beans.put(bd.idName,bd.ob);
            }
        }catch(IllegalArgumentException|UnsupportedOperationException|NullPointerException e){
            throw new BeanException(BeanException.ErrorType.MAP_PUT_ERROR, "写入Map错误"+e.getMessage());
        }
    }

    //根据ref的值找到对应函数，注入
    public static void FindRef(BeanDefinition bd) throws BeanException{
        try{
            for(String s:bd.properties.keySet()){
                Optional<BeanDefinition> refbean =BeanList.stream()
                        .filter(item-> Objects.equals(item.idName,s))
                        .findAny();

                if(!refbean.isPresent()){
                    throw new BeanException(BeanException.ErrorType.REFBEAN_NOTFOUND,
                            "无法获取"+s+"该对象所需要的依赖注入对象");
                }

                //这里存疑,很大的疑/------------------------------------------------------------
                Class<?> refClass = getClass(bd.className);
                Class<?> injClass = getClass(refbean.get().className);
                //refbean.get().ob.class.getInterfaces()  //这样就会报错
                Method method = refClass.getMethod("set"+bd.properties.get(s), injClass.getInterfaces());
                method.invoke(bd.ob,refbean.get().ob);
                //-------------------------------------------------------------------
            }

        } catch (NoSuchMethodException e) {
            throw new BeanException(BeanException.ErrorType.METHOD_NOTFOUND, "依赖注入方法没有找到或者为私有"+e.getMessage());
        } catch (InvocationTargetException e) {
            throw new BeanException(BeanException.ErrorType.INJECT_ERROR,
                    "依赖注入失败，没有找到需要被依赖的对象"+e.getMessage());
        } catch (IllegalAccessException e) {
            throw new BeanException(BeanException.ErrorType.INJECT_ERROR,
                    "依赖注入失败，依赖注入所需要的更改权限不足"+e.getMessage());
        }
    }



    //反射用到的函数
    public static Class<?> getClass (String className) throws BeanException{
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeanException(BeanException.ErrorType.CLASS_NOTFOUND, className+"类不存在");
        }
    }

    public static Object CreateBean(Class<?> clazz) throws BeanException {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new BeanException(BeanException.ErrorType.CREATE_OBJECT_ERROR,
                    "创建对象失败：请检查是否有无参构造函数");
        } catch (IllegalAccessException e) {
            throw new BeanException(BeanException.ErrorType.CREATE_OBJECT_ERROR,
                    "创建对象失败：类不能是抽象类，构造函数不能为私有."+e.getMessage());
        }
    }


}
