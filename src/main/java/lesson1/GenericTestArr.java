package lesson1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericTestArr <T> {
    private T[] obj;

    public T[] getObj() {
        return obj;
    }

    public GenericTestArr(T... obj) {
        this.obj = obj;
    }

    public T[] ChangeItemArr (int a, int b){
        T buf = obj[a];
        obj[a] = obj [b];
        obj[b] = buf;
        return obj;
    }

    public ArrayList<T> ArrToAL (){
        ArrayList<T> list1 = new ArrayList<T>();
        for (int i = 0; i < obj.length; i++) {
            list1.add(obj[i]);
        }
        return list1;
    }
}
