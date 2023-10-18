package com.example.demo.Service;
import com.example.demo.Dao.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComService {

    private List<Commodity> ComList=
            Collections.synchronizedList(new ArrayList<>());

    //增删改查
    public void AddCom(Commodity com){
        ComList.add(com);
    }

    public void DelCom(int id){
        Commodity com = ComList.stream()
                .filter(c->c.getId()==id)
                .findFirst().get();
        ComList.remove(com);
    }

    public Commodity GetComById(int id){
        return ComList.stream()
                .filter(c->c.getId()==id)
                .findAny()
                .get();
    }

    public List<Commodity> GetCom(){
        return ComList;
    }

    public void UpdataCom(int id,Commodity neo){
        Commodity com = ComList.stream()
                .filter(c->c.getId()==id)
                .findFirst().get();
        ComList.remove(com);
        ComList.add(neo);
    }
}
