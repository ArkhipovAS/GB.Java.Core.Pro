package lesson1;

import java.util.Arrays;

public class hw1 {
    public static void main(String[] args) {
//        GenericTestArr<String> gtaStr = new GenericTestArr<String>("a", "b", "c");
//        System.out.println(Arrays.toString(gtaStr.getObj()));
//        gtaStr.ChangeItemArr(0, 2);
//        System.out.println(Arrays.toString(gtaStr.getObj()));
//
//        GenericTestArr<Integer> gtaInt = new GenericTestArr<Integer>(1, 2, 3);
//        System.out.println(Arrays.toString(gtaInt.getObj()));
//        gtaInt.ChangeItemArr(0, 2);
//        System.out.println(Arrays.toString(gtaInt.getObj()));
//
//        System.out.println(gtaInt.ArrToAL());
//        System.out.println(gtaStr.ArrToAL());

        Apple a1 = new Apple();
        Apple a2 = new Apple();
        Apple a3 = new Apple();
        Apple a4 = new Apple();

        Orange o1 = new Orange();
        Orange o2 = new Orange();
        Orange o3 = new Orange();
        Orange o4 = new Orange();

//        System.out.println(a1.getWeight() + " " + a1.getName());

        Box<Apple> ba1 = new Box<Apple>();
        Box<Orange> bo1 = new Box<Orange>();
        ba1.putBox(a1, a2, a3);
        bo1.putBox(o1, o2, o3);
        System.out.println(ba1.getWeight() + " Weight BoxApple");
        System.out.println(bo1.getWeight() + " Weight BoxOrange");

        System.out.println("Compare box: " + ba1.compare(bo1));

    }
}
