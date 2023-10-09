package dao.impl;
import dao.*;

public class WhiteCat implements Cat {

    private Fish fish;

    public String eat(){
        return this.getClass()+" eats "+fish.getClass();
    }

    public void setFish(Fish fish){
        this.fish = fish;
    }

    public Fish getFish(){return fish;}

}
