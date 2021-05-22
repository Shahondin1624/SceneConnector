package util;

import javafx.collections.ModifiableObservableListBase;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.*;
import java.util.function.Predicate;

/***
 * ObservableList that remembers the position of the last requested object
 * @param <E>
 */
public class PositionList<E> extends ModifiableObservableListBase<E> {
    private List<E> backingList = new ArrayList<>();
    private int position = 0;

    public PositionList() {
    }

    public PositionList(Collection<E> collection) {
        if (collection != null) {
            backingList.addAll(collection);
        }
    }

    public PositionList(E... items) {
        if (items != null) {
            backingList.addAll(Arrays.asList(items));
        }
    }

    public int setPositionOnItem(E item) throws ObjectNotFoundException {
        for (int i = 0; i < backingList.size(); i++) {
            if (get(i).equals(item)) {
                position = i;
                return i;
            }
        }
        throw new ObjectNotFoundException("Item has not been found in list");
    }

    public E next() {
        if (position >= 0 && position + 1 < backingList.size()) {
            position++;
            return get(position);
        } else return null;
    }

    public E previous() {
        if (position < backingList.size() && position - 1 >= 0) {
            position--;
            return get(position);
        } else return null;
    }

    public E select(int index) {
        if (index >= 0 && index < backingList.size()) {
            position = index;
            return get(position);
        } else return null;
    }

    public E first() {
        if (backingList.size() > 0) {
            position = 0;
            return get(position);
        } else return null;
    }

    public E getAtPos(){
        return get(position);
    }

    public void replaceAll(Collection<E> col) {
        position = 0;
        backingList.clear();
        backingList.addAll(col);
    }

    public void replaceItem(E oldItem, E newItem) {
        for (int i = 0; i < backingList.size(); i++) {
            if (backingList.get(i).equals(oldItem)) {
                backingList.set(i, newItem);
            }
        }
    }

    @Override
    public E get(int index) {
        return backingList.get(index);
    }

    @Override
    public int size() {
        return backingList.size();
    }

    @Override
    public void sort(Comparator<? super E> c) {
        throw new UnsupportedOperationException("Sorting would destroy Workflow-Order");
    }

    @Override
    protected void doAdd(int index, E element) {
        try {
            backingList.listIterator(index).add(element);
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

    @Override
    protected E doSet(int index, E element) {
        try {
            ListIterator<E> e = backingList.listIterator(index);
            E oldVal = e.next();
            e.set(element);
            return oldVal;
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

    @Override
    protected E doRemove(int index) {
        try {
            ListIterator<E> e = backingList.listIterator(index);
            E outCast = e.next();
            e.remove();
            return outCast;
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

    @Override
    public FilteredList<E> filtered(Predicate<E> predicate) {
        return super.filtered(predicate);
    }

    @Override
    public SortedList<E> sorted(Comparator<E> comparator) {
        throw new UnsupportedOperationException("Sorting would destroy Workflow-Order");
    }

    @Override
    public SortedList<E> sorted() {
        throw new UnsupportedOperationException("Sorting would destroy Workflow-Order");
    }

    @Override
    public ListIterator<E> listIterator(final int index) {
        return new ListIterator<E>() {

            private final ListIterator<E> backingIt = backingList.listIterator(index);
            private E lastReturned;

            @Override
            public boolean hasNext() {
                return backingIt.hasNext();
            }

            @Override
            public E next() {
                return lastReturned = backingIt.next();
            }

            @Override
            public boolean hasPrevious() {
                return backingIt.hasPrevious();
            }

            @Override
            public E previous() {
                return lastReturned = backingIt.previous();
            }

            @Override
            public int nextIndex() {
                return backingIt.nextIndex();
            }

            @Override
            public int previousIndex() {
                return backingIt.previousIndex();
            }

            @Override
            public void remove() {
                beginChange();
                int idx = previousIndex();
                backingIt.remove();
                nextRemove(idx, lastReturned);
                endChange();
            }

            @Override
            public void set(E e) {
                beginChange();
                int idx = previousIndex();
                backingIt.set(e);
                nextSet(idx, lastReturned);
                endChange();
            }

            @Override
            public void add(E e) {
                beginChange();
                int idx = nextIndex();
                backingIt.add(e);
                nextAdd(idx, idx + 1);
                endChange();
            }
        };
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
