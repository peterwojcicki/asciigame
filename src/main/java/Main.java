import com.freedom.levels.Level1;
import com.freedom.levels.Menu;

public class Main {
    public static void main(String[] args) throws Exception {

        while (true) {
            new Menu().play();
            new Level1().play();
        }
    }
}
