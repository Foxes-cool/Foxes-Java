import cool.foxes.Foxes;

public class Example {
    public static void main(String args[]) {
        System.out.println(Foxes.Fox(Foxes.Options().Width(150)));
        System.out.println(Foxes.Fox(Foxes.Options().Width(150).Height(150)));
        System.out.println(Foxes.Fox(Foxes.Options().Width(150).Height(150).AspectRatio("1:2")));
    }
}
