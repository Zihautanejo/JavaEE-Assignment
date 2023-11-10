package edu.whu.demo;

import edu.whu.demo.aspect.LogAspect;
import edu.whu.demo.controller.ProductController;
import edu.whu.demo.entity.Product;
import edu.whu.demo.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import javax.annotation.PostConstruct;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AspectTest {

    @LocalServerPort
    private Integer port = 8081;

    //@Autowired
    ProductService productService = new ProductService();
    //@Autowired
    ProductController productController = new ProductController();
    //@Autowired
    private TestRestTemplate restTemplate = new TestRestTemplate();
    String url ;
    //@Autowired
    private LogAspect logAspect = new LogAspect();
    //@Autowired
    private Product product = new Product();

    @PostConstruct
    private void init(){
        this.url="http://localhost:" + port + "/products";
    }
    //初始化的时候，设置端口--构造完成的时候执行

    @AfterEach
    private void clean()throws Exception{
        productService.deleteAll();
    }

    @BeforeEach
    private void initData(){
        product.setId(1);
        product.setName("IPhone 13");
        product.setPrice(8000);
        product.setProductType("SND-dff");
        product.setCategory("手机");
        product.setStockQuantity(20);
    }

    @Test
    public void CallTest(){

        //调用增加，修改，删除，查询各一次
        restTemplate.postForEntity(url, product, Product.class);
        //exchange(url, HttpMethod.GET,null, PostDTO.class);
        restTemplate.getForEntity(url+"?name={name}&quantity={quantity}",
                Product[].class,"IPhone",15);

        restTemplate.put(url+"/1",product);
        restTemplate.delete(url+"/2");

        assertEquals(4,LogAspect.getCalltimes().size());
        assertEquals(4,LogAspect.getTime().size());



    }

}
