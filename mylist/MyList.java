package maman14.mylist;

public class MyList<T> {

    private MyNode<T> m_head;
    private MyNode<T> m_tail;

    public MyList() {
        m_head = null;
        m_tail = null;
    }

    public MyNode<T> getHead() {
        return m_head;
    }

    public  MyNode<T> getTail() { return m_tail; }

    public void add(T data) {
        MyNode<T> newNode = new MyNode<>(data);
        if (m_head == null) {
            m_head = newNode;
            m_tail = newNode;
            m_head.setNext(m_tail);
        }
        else {
            m_tail.setNext(newNode);
            m_tail = newNode;
        }
    }

    public MyNode<T> removeFirst() throws EmptyListException {
        if (m_head == null) {
            throw new EmptyListException("Cannot remove item from an empty list.");
        }
        MyNode<T> head = m_head;
        m_head = m_head.getNext();
        return head;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        MyNode<T> current = m_head;
        while (current != null) {
            sb.append("[");
            sb.append(current.getData().toString());
            sb.append("]");
            current = current.getNext();
        }
        sb.append("}");
        return sb.toString();
    }
}
