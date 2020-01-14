import domain.Szachy;
import services.SzachyService;

public class Main {

    public static void main(String[] args) {

        SzachyService szachyService = new SzachyService();

        szachyService.read();

        szachyService.create(new Szachy("Test", "Testewicz", 12));

        szachyService.update(0, new Szachy(0, "NowyTest", "Testewicz", 13));

        szachyService.delete(1);

        System.out.println("----------------------");

        szachyService.read();

    }
}
