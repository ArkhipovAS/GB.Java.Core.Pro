package lesson1;

import java.util.ArrayList;

public class Box <T extends Fruit> {
    private ArrayList<T> box = new ArrayList<T>();
    float weight = 0f;
//    public Box(ArrayList<T> box) {
//        this.box = box;
//    }


    public float getWeight(){
        weight = 0f;
        for (T t : box
             ) {

            weight += t.getWeight();
        }
        return weight;
    }

    public void putBox (T... fruits){
        for (T t: fruits
             ) {
            this.box.add(t);
        }
    }

    public boolean compare(Box<?> bc) {
//        System.out.println(" " + weight + " " + bc.getWeight());
        return Math.abs(bc.getWeight() - weight) < 0.001f;
    }
}
