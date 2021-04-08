import java.util.Arrays;
import java.util.Objects;

public class ArrayCollection implements Collection {

    private String[] object;
    private String[] objectTemp;
    private int size = 0;

    public ArrayCollection(int length) {
        object = new String[length];
    }

    public ArrayCollection() {
        object = new String[10];
    }

    public boolean objectString(Object o) {
        if (!Objects.nonNull(object)) {
            return false;
        }
        return o.getClass() == String.class;
    }

    @Override
    public boolean add(Object o) {
        if (objectString(o)) {
            if (size == this.object.length) {
                objectTemp = new String[this.object.length];
                System.arraycopy(this.object, 0, objectTemp, 0, this.object.length);
                this.object = new String[objectTemp.length + 10];
                System.arraycopy(objectTemp, 0, this.object, 0, objectTemp.length);
            }
            this.object[size] = o.toString();
            this.size++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean add(int index, Object o) {
        if (objectString(o)) {
            if (index == size && index < this.object.length) {
                this.object[index] = o.toString();
                this.size++;
            } else if (index == size && size == this.object.length) {
                objectTemp = new String[this.object.length];
                System.arraycopy(this.object, 0, objectTemp, 0, this.object.length);
                this.object = new String[objectTemp.length + 10];
                System.arraycopy(objectTemp, 0, this.object, 0, objectTemp.length);
                this.object[size] = o.toString();
                this.size++;
            } else if (index < size) {
                if (size == this.object.length) {
                    objectTemp = new String[this.object.length];
                    System.arraycopy(this.object, 0, objectTemp, 0, this.object.length);
                    this.object = new String[objectTemp.length + 10];
                    System.arraycopy(objectTemp, 0, this.object, 0, index);
                    System.arraycopy(objectTemp, index, this.object, index + 1, objectTemp.length - (index));
                    this.object[index] = o.toString();
                    this.size++;
                } else if (size < this.object.length) {
                    System.arraycopy(this.object, index, this.object, index + 1, size - index);
                    this.object[index] = o.toString();
                    this.size++;
                }
            } else if (index > size) {
                System.err.println("Элемент c индексом: " + index + ", не добавлен! \nИндекс: " + index
                        + " - выходит за пределы массива. Крайний индекс: " + this.size + ".");
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean zeroSize() {
        if (size == 0) {
            System.err.println("Массив не содержит элементов!");
            return true;
        }
        return false;
    }

    private boolean lengthDelete(int index) {
        if (index == size - 1) {
            if (10 <= object.length - (size - 1)) {
                objectTemp = new String[object.length - 10];
                System.arraycopy(objectTemp, 0, objectTemp, 0, objectTemp.length);
                object = new String[objectTemp.length];
                System.arraycopy(objectTemp, 0, object, 0, objectTemp.length);
                this.size--;
                return true;
            } else if (10 > object.length - (size - 1)) {
                object[index] = null;
                this.size--;
                return true;
            }
        }
        if (index < size - 1) {
            if (10 <= object.length - (size - 1)) {
                objectTemp = new String[object.length - 10];
                System.arraycopy(object, 0, objectTemp, 0, index);
                System.arraycopy(object, index + 1, objectTemp, index, size - index - 1);
                object = new String[objectTemp.length];
                System.arraycopy(objectTemp, 0, object, 0, size - 1);
                this.size--;
                return true;
            } else if (10 > object.length - (size - 1)) {
                System.arraycopy(object, index + 1, object, index, size - (index + 1));
                object[size - 1] = null;
                this.size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Object o) {
        if (objectString(object)) {
            return false;
        }
        if (zeroSize()) {
            return false;
        }
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (object[i].equals(o)) {
                index = i;
                break;
            }
        }
        if (index < 0) {
            System.err.println("Элемент не найден!");
            return false;
        }
        if (index > size - 1) {
            System.err.println("Элемент c индексом: " + index + ", не удален! \nИндекс: " + index
                    + " - выходит за пределы массива. Крайний индекс: " + (this.size - 1) + ".");
            return false;
        }
        return lengthDelete(index);
    }

    @Override
    public Object get(int index) {
        if (zeroSize()) {
            return null;
        }
        if (index > size) {
            System.err.println("Элемент c индексом: " + index + " - выходит за пределы массива. Крайний индекс: "
                    + (this.size - 1) + ".");
            return null;
        }
        return object[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayCollection test = (ArrayCollection) o;
        return size == test.size && object == test.object;
    }

    @Override
    public int hashCode() {
        int result = size + 1;
        result = 31 * result + Arrays.hashCode(object);
        return result;
    }

    @Override
    public boolean contain(Object o) {
        for (int i = 0; i < size; i++) {
            if (object[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Collection str) {
        return str == this;
    }

    @Override
    public boolean clear() {
        if (zeroSize()) {
            this.size = 0;
            return true;
        }
        if (object.length <= 10) {
            object = new String[object.length];
        } else {
            int length = object.length;
            while (10 < length) {
                length -= 10;
            }
            object = new String[length];
        }
        this.size = 0;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "Массив не содержит элементов!";
        }
        System.out.println("\nСодержание массива: ");
        for (int i = 0; i < size; i++) {
            System.out.println("Индекс: " + i + ", Содержание: " + object[i]);
        }
        return "Размер массива: " + size + ".";
    }
}