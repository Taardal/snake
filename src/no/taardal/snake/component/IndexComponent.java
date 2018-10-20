package no.taardal.snake.component;

public class IndexComponent implements Comparable<IndexComponent> {

    private int index;

    public IndexComponent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public boolean isHead() {
        return index == 0;
    }

    @Override
    public String toString() {
        return "IndexComponent{" +
                "index=" + index +
                '}';
    }

    @Override
    public int compareTo(IndexComponent indexComponent) {
        return Integer.compare(index, indexComponent.index);
    }
}
