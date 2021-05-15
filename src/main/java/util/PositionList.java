package util;

import java.util.ArrayList;
import java.util.Arrays;

/***
 * ArrayList that remembers the position of the last requested object
 * @param <E>
 */
public class PositionList<E> extends ArrayList<E> {

    private int position = 0;

    public int getPosition() {
        return position;
    }

    @Override
    public E get(int index) {
        position = index;
        return super.get(index);
    }

    public E getHere() {
        return get(position);
    }

    public E next() {
        if (position < this.size() - 1) {
            position++;
            return super.get(position);
        } else return null;
    }

    public E previous() {
        if (position > 0) {
            position--;
            return super.get(position);
        } else return null;
    }

    public void addLast(E item) {
        this.add(item);
    }

    @SafeVarargs
    public final void addAll(E... values) {
        super.addAll(Arrays.asList(values));
    }

    public void setPositionOn(E item) {
        if (item != null){
            for (int i = 0; i < size(); i++) {
                if (get(i).equals(item)){
                    break;
                }
            }
        }
    }
}
