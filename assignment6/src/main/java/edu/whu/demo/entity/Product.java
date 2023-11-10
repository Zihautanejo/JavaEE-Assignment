package edu.whu.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiaxy
 */

@Data
@NoArgsConstructor
public class Product {

    //产品Id
    long id;
    //产品名称
    String name;
    //产品单价
    float price;
    //库存数量
    float stockQuantity;
    //产品类别
    String category;
    //产品类型
    String productType;
    //产品描述
    String description;


}
