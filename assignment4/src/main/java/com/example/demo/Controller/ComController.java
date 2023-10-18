
package com.example.demo.Controller;
import com.example.demo.Service.*;
import com.example.demo.Dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("Commodity")
public class ComController {


    ComService comService = new ComService();

    //对商品列表进行增删改查
    @PostMapping("")
    public ResponseEntity<Void> PostCom(@RequestBody Commodity com){
        comService.AddCom(com);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commodity> GetComById(@PathVariable int id){
        return ResponseEntity.ok(comService.GetComById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<Commodity>> GetCom(){
        return ResponseEntity.ok(comService.GetCom());
    }

    @PutMapping("")
    public ResponseEntity<Void> PutCom(int id, @RequestBody Commodity com){
        comService.UpdataCom(id,com);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DelComById(@PathVariable int id){
        comService.DelCom(id);
        return ResponseEntity.ok().build();
    }
}
