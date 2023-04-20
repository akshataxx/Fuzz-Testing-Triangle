import java.util.Random;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class FuzzTestingTriangle {
    String out;
    int value;
    //datastructure used is TreeMap to store expected values
    Map<String, Integer> treemap = new TreeMap<String, Integer>();

    //Program given to determine type of Triangle a.k.a equilateral, isosceles ordinary or non-triangle

    public String triangle(int a, int b, int c){
        if ((a+b>c)&&(a+c>b)&&(b+c>a)) {
            if (a==b || a==c || b==c) {
                if (a==c && a==b)
                    return ("equilateral triangle.\n");
                else if (a==c||b==c)
                    return ("isosceles triangle.\n");
            }
            else
                return ("triangle.\n");
        }
        else
            return ("non-triangle.\n");
        return "No Result\n";
    }

    //method to run the fuzz program and put the treemap to the map
    public void fuzz(){
        Random r = new Random();
        int upperLimit = 26;
        int a = r.nextInt(upperLimit);
        int b = r.nextInt(upperLimit);
        int c = r.nextInt(upperLimit);

        System.out.print("Side 1 = " + a + ", Side 2 = " + b + ", Side 3 = " + c +"\n");
        out = triangle(a,b,c);

        value = treemap.get(out);
        treemap.put(out, ++value);
        System.out.print(out);
    }

    public void execute()
    {

        treemap.put("equilateral triangle.\n", 1);
        treemap.put("isosceles triangle.\n", 2);
        treemap.put("triangle.\n", 3);
        treemap.put("non-triangle.\n", 4);
        treemap.put("No Result\n", 5);

        long s = System.currentTimeMillis();

        do{
            fuzz();
        }while(check()==true);

        long e = System.currentTimeMillis();
        long timediff = e - s; //calculates total computational time

        //Total Data
        System.out.println("\nEquilateral Triangle:\t " + treemap.get("equilateral triangle.\n"));
        System.out.println("Isosceles Triangle:\t " + treemap.get("isosceles triangle.\n"));
        System.out.println("Triangle: \t\t" + treemap.get("triangle.\n"));
        System.out.println("Non-triangle:\t\t " + treemap.get("non-triangle.\n"));
        System.out.println("Error Count:\t\t" + treemap.get("No Result\n"));
        System.out.println("Time difference:\t "+timediff);

    }

    //method to check
    public boolean check(){
        if(treemap.get("equilateral triangle.\n") == 1) {return true; }
        if(treemap.get("isosceles triangle.\n") == 2){ return true; }
        if(treemap.get("triangle.\n") == 3){ return true; }
        if(treemap.get("non-triangle.\n") == 4){ return true; }

        return false;
    }

    //main method
    public static void main(String[] args){
        FuzzTestingTriangle f = new FuzzTestingTriangle();
        f.execute();
    }
}
