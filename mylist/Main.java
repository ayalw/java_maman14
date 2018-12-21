package maman14.mylist;

import java.util.Scanner;

public class Main {

    final static int LENGTH = 6;

    public static void main (String[] args) {
        System.out.println("Welcome to Maman 14!");
        MyList<String> list = new MyList<String>();
        Scanner scanner = new Scanner(System.in);
        for (int i=1; i<=LENGTH; i++) {
            System.out.println("Please enter string " + i + " of " + LENGTH + ":");
            list.add(scanner.next());
        }
        System.out.println("Got list with values: " + list.toString());

        MyList<String> reversedList = reverseLinkedList(list);
        System.out.println("Got INVERTED list with values: " + reversedList.toString());

        MyList<Person> personList = new MyList<>();
        personList.add(new Person("Johnny", "111", 1983));
        personList.add(new Person("Billy", "222", 1975));
        personList.add(new Person("Danielle", "333", 1999));
        personList.add(new Person("Jenny", "444", 1981));

        Person oldest = max(personList);
        System.out.println("Oldest person is: " + oldest);


    }

    public static MyList<String> reverseLinkedList( MyList<String>  list)
    {
        MyNode<String> currentNode = list.getHead();
        MyNode<String>  previousNode=null;
        MyNode<String>  nextNode;
        while(currentNode!=null)
        {
            nextNode=currentNode.getNext();
            currentNode.setNext(previousNode);
            previousNode=currentNode;
            currentNode=nextNode;
        }
        MyList<String> reversedList = new MyList();
        currentNode = previousNode;
        while (currentNode != null) {
            reversedList.add(currentNode.getData());
            currentNode = currentNode.getNext();
        }
        return reversedList;
    }

    private static <T extends Comparable<T>> T max(MyList<T> list) {
        if (list == null || list.getHead() == null) return null;
        T maxSoFar = list.getHead().getData();
        MyNode<T> node = list.getHead();
        while (node != null) {
            if (node.getData().compareTo(maxSoFar) > 0) {
                maxSoFar = node.getData();
            }
            node = node.getNext();
        }
        return maxSoFar;
    }
}
