import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.PositionList;

public class ListTest {

    private PositionList<String> list;

    @BeforeEach
    public void beforeEach() {
        list = new PositionList<>();
        list.addAll("1", "2", "3");
    }


    @Test
    public void addAndGet() {
        String res = list.getAtPos();
        Assertions.assertEquals("1", res);
    }

    @Test
    public void addAndGet2() {
        String res = list.next();
        Assertions.assertEquals("2", res);
    }

    @Test
    public void getPrevious() {
        list.get(2);
        String res = list.previous();
        Assertions.assertEquals("2", res);
    }

    @Test
    public void selectAndContinue() {
        list.setPositionOnItem("2");
        String res = list.next();
        Assertions.assertEquals("3", res);
    }
}
