

public class Main {

    public static void main(String[] args) {


        MultMap<Integer,String>map1 = new MultMap<>(999);
        map1.put(10,"doido");
        map1.put(10, "doido");
        map1.put(10,"1");
        map1.put(10,"2");
        map1.put(10,"3");
        map1.put(10,"4");


        map1.findAll(10);

    }

}
