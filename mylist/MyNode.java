package maman14.mylist;

public class MyNode<T> {

    private T m_data;
    private MyNode<T> m_next;

    public MyNode(T data) {
        m_data = data;
    }

    public T getData() {
        return m_data;
    }

    public MyNode<T> getNext() {
        return m_next;
    }

    public void setData(T data) {
        m_data = data;
    }

    public void setNext(MyNode<T> next) {
        m_next = next;
    }
}
