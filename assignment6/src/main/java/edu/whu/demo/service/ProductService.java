package edu.whu.demo.service;

import edu.whu.demo.entity.Product;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    // 创建线程安全的Map
    private Map<Long, Product> productMap =
            Collections.synchronizedMap(new HashMap<Long, Product>());

    /**
     * 添加商品
     * @param product
     * @return
     */
    public Product addProduct(Product product) throws Exception{
        productMap.put(product.getId(), product);
        return product;
    }

    /**
     * 根据Id查找
     * @param id
     * @return
     */
    public Product getProduct(long id)throws Exception {
        return productMap.get(id);
    }

    /**
     * 根据名称和库存查找
     * @param name
     * @param quantity
     * @return
     */
    public List<Product> findProduct(String name, Float quantity) throws Exception {
        List<Product> result=new ArrayList<>();
        for (Product product: productMap.values()){
            if (name!=null && !product.getName().contains(name)) {
                continue;
            }
            if (quantity!=null && product.getStockQuantity()>= quantity) {
                continue;
            }
            result.add(product);
        }
        return result;
    }

    /**
     * 更新商品信息
     * @param id
     * @param product
     */
    public void updateProduct(long id, Product product) throws Exception{
        Product product1  = productMap.get(id);
        if(product1!=null){
            productMap.put(id,product);
        }
    }

    /**
     * 删除商品
     * @param id
     */
    public void deleteProduct(long id) throws Exception{
        productMap.remove(id);
    }

    /**
     * 删除所有商品
     */
    public void deleteAll() throws Exception{
        productMap.clear();
    }


}
