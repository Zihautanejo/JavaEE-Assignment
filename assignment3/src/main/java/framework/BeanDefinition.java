package framework;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//表示bean的配置信息,即在xml中会用到的一些属性配置
public class BeanDefinition {

    public String idName;
    public String className;
    public Map<String,String> properties = new HashMap<String,String>();
    public Object ob;


}
