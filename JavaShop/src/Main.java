import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        CosasTochassChild cosas1 = new CosasTochassChild();
        CosasTochassChild cosas2 = new CosasTochassChild();

        cosas1.name = "Cosassssss";
        cosas2.name = "Otras cosas";

        ArrayList<CosasTochas>muchasCosasTochas = new ArrayList<>();
        muchasCosasTochas.add(cosas1);
        muchasCosasTochas.add(cosas2);
        System.out.println(muchasCosasTochas.get(0).getName());
        System.out.println(muchasCosasTochas.get(1).getName());

        Product product1 = new Product("Pito", 6.9f);
        Product product2 = new Product("Caca", 5.0f);
        cosas1.getElMapa().put("Mercadona", new LinkedList<Product>(Arrays.asList(product1,product2)));

        System.out.print(cosas1.getElMapa().get("Mercadona").get(1).getName());
    }
}