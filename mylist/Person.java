package maman14.mylist;

public class Person implements Comparable{

    private String m_name;
    private String m_id;
    private int m_yearOfBirth;

    public Person(String name, String id, int yearOfBirth) {
        m_id = id;
        m_name = name;
        m_yearOfBirth = yearOfBirth;
    }

    public String getName() {
        return m_name;
    }

    public String getId() {
        return m_id;
    }

    public int getYearOfBirth() {
        return m_yearOfBirth;
    }

    @Override
    public String toString() {
        return "{" + m_name + "|" + m_id + "|" + m_yearOfBirth + "}";
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Person)) {
            return 0;
        }
        if (m_yearOfBirth < ((Person) o).getYearOfBirth()) {
            return 1;
        }
        else if (m_yearOfBirth > ((Person) o).getYearOfBirth()) {
            return -1;
        }
        return 0;
    }
}
