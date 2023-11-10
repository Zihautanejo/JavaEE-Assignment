package edu.whu.demo.controller;

import edu.whu.demo.entity.Product;
import edu.whu.demo.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "商品管理")
@RestController
@RequestMapping("products")
public class ProductController {
    @Autowired
    ProductService productService;

    @ApiOperation("根据Id查询商品")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@ApiParam("商品Id")@PathVariable long id) throws Throwable{
        Product result = productService.getProduct(id);
        if(result==null) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(result);
        }
    }

    @ApiOperation("根据条件查询商品")
    @GetMapping("")
    public ResponseEntity<List<Product>> findProduct(@ApiParam("商品名称")String name, @ApiParam("商品数量")Float quantity) throws Exception{
        List<Product> result = productService.findProduct(name,quantity);
        return ResponseEntity.ok(result);
    }

    @ApiOperation("添加商品")
    @PostMapping("")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) throws Exception{
        Product result = productService.addProduct(product);
        return ResponseEntity.ok(result);
    }

    @ApiOperation("修改商品信息")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable long id,@RequestBody Product product)throws Exception{
        productService.updateProduct(id,product);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("删除商品")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) throws Exception{
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

}
