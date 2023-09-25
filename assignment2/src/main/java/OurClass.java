public class OurClass {

    private int x =0;

    //俩个分别为无参和有参的构造函数
    public OurClass(){}
    public OurClass(int x){
        this.x=x;
    }

    //添加有注解的函数和无注解的函数

    public int getX(){ return x;}

    public void setX(int n){ x=n; }

    //添加有注解的函数和无注解的函数
    public void init(){
        this.x=100;
    }

    public void add(){
        this.x+=20;
    }

    @InitMethod
    public void sub() { this.x-=10; }
}
