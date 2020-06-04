package Main;

import java.lang.annotation.ElementType;
import java.util.*;
import java.util.stream.Collectors;

class Priormas{
    private int[] queue = new int[1000];
    private int[] priority = new int[1000];

    private int size() {
        int size = 0;
        for (int i = 0; i != 1000 && queue[i] != 0; i++) {
            size++;
        }
        return size;
    }

    private boolean isEmpty() {
        return size() == 0;
    }

    void add(int element, int prior) {
        if(size() == queue.length) {
            System.out.println("Массив заполнен! Вы не можете добавить элемент в массив.");
        }
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] != 0) {
                if(priority[i] > 0) {
                    if(prior > 0) {
                        if(priority[i] > prior) {
                            addelement(i, element, prior);
                            break;
                        }
                    } else {
                        if(priority[i] < prior) {
                            addelement(i, element, prior);
                            break;
                        }
                    }
                } else {
                    if(priority[i] < prior) {
                        addelement(i, element, prior);
                        break;
                    }
                }
            } else {
                queue[i] = element;
                priority[i] = prior;
                break;
            }
        }

    }

    private void addelement(int i, int element, int prior) {
        int qi = queue[i];
        int pi = priority[i];
        queue[i] = element;
        priority[i] = prior;
        for (int j = i; j < queue.length - 1; j++) {
            if(queue[j] == 0) {
                queue[j] = qi;
                priority[j] = pi;
            } else {
                int qj1 = queue[j+1];
                int pj1 = priority[j+1];
                queue[j+1] = qi;
                priority[j+1] = pi;
                qi = qj1;
                pi = pj1;
            }
        }
    }

    void removeone() {
        if(isEmpty()) {
            System.out.println("Массив пустой! Сначала заполните массив");
        }
        int size = size();
        for (int i = 0; i != 999 && queue[i] != 0; i++) {
            queue[i] = queue[i+1];
            priority[i] = priority[i+1];
        }
        queue[size-1] = 0;
        priority[size-1] = 0;
    }

    void showlist() {
        for (int i = 0; i < queue.length; i++) {
            if(queue[i] != 0) {
                System.out.println("Данные: " + queue[i] + ". Приоритеты этих данних:" + priority[i]);
            }
        }
    }
}

class ElementList<T> {
    private Object data;
    private int prior;
    private ElementList<T> next;
    private ElementList<T> prev;

    ElementList(Object data, int prior, ElementList<T> next, ElementList<T> prev) {
        this.data = data;
        this.prior = prior;
        this.next = next;
        this.prev = prev;
    }

    ElementList<T> getNext() {
        return next;
    }

    ElementList<T> getPrev() {
        return prev;
    }

    Object getData() {
        return data;
    }

    int getPrior() {
        return prior;
    }

    void setNext(ElementList<T> next) {
        this.next = next;
    }

    void setPrev(ElementList<T> prev) {
        this.prev = prev;
    }
}

class LinkedList<T> {
    private ElementList<T> head;
    private ElementList<T> tail;

    boolean isEmpty() {
        return head == null;
    }

     void size() {
        int size = 0;
        ElementList<T> current = head;
        while(current != null) {
            size++;
            current = current.getNext();
        }
         System.out.println("Размер: " + size);
    }

    void add(T val, int prior) {
        ElementList<T> element = new ElementList<T>(val, prior, null, null);
        ElementList<T> current = head;
        if(tail == null && head == null) {
            head = element;
            tail = element;
            return;
        }
        while(current != null) {
            if(prior > 0) {
                if(current.getPrior() > 0) {
                    if (current.getPrior() > prior) {
                        addelement(current, element);
                        return;
                    }
                } else {
                    if (current.getPrior() < prior) {
                        addelement(current, element);
                        return;
                    }
                }
            } else {
                if(current.getPrior() < 0) {
                    if (current.getPrior() < prior) {
                        addelement(current, element);
                        return;
                    }
                } else {
                    if (current.getPrior() < prior) {
                        addelement(current, element);
                        return;
                    }
                }
            }
            current = current.getNext();
        }
        current = tail;
        tail = element;
        if(current != null) {
            current.setNext(element);
        } else {
            head = element;
        }
        element.setPrev(current);
    }

    private void addelement(ElementList<T> current, ElementList<T> element) {
        ElementList<T> prev = current.getPrev();
        if (prev != null) {
            prev.setNext(element);
        } else head = element;
        current.setPrev(element);
        element.setPrev(prev);
        element.setNext(current);
    }

    void show() {
        ElementList<T> current = head;
        while(current != null) {
            System.out.println("Данные: " + current.getData() +". Приоритет этих данных:" + current.getPrior());
            current = current.getNext();
        }
    }
    void removeone() {
        head = head.getNext();
        head.setPrev(null);
    }
}



public class Main {

    private static Priormas maslist = new Priormas();
    private static LinkedList<Integer> list = new LinkedList<Integer>();
    private Random rad = new Random();

    public static void main(String[] args) {
        Main arr = new Main();
        Scanner sc = new Scanner(System.in);
        int sel = sc.nextInt();
        while ((sel=menu())!=0){
            switch (sel){
                case 1:
                    long startmas = System.currentTimeMillis();
                    for (int i = 0; i < 1000; i++) {
                        maslist.add((int) (Math.random() * 1000), (int)(Math.random()*1000)-500);
                    }
                    long finishmas = System.currentTimeMillis() - startmas;
                    System.out.println("Время на добавление 1000 элементов массива: " + finishmas + " мс. \n");
                    break;
                case 2:
                    maslist.showlist();
                    break;
                case 3:
                    long startremovemas = System.currentTimeMillis();
                    maslist.removeone();
                    long finishremovemas = System.currentTimeMillis() - startremovemas;
                    System.out.println("Время на удаление первого элемента массива: " + finishremovemas + " мс. \n");
                    break;
                case 4:
                    long startlist = System.currentTimeMillis();
                    for (int i = 0; i < 1000; i++) {
                        list.add(((int) (Math.random() * 1000)), (int)(Math.random()*1000)-500);
                    }
                    long finishlist = System.currentTimeMillis() - startlist;
                    System.out.println("Время на добавление 1000 элементов списка: " + finishlist + " мс. \n");
                    break;
                case 5:
                    list.show();
                    break;
                case 6:
                    list.size();
                    break;
                case 7:
                    long startremovelist = System.currentTimeMillis();
                    list.removeone();
                    long finisthremovelist = System.currentTimeMillis() - startremovelist;
                    System.out.println("Время на удаление первого элемента списка: " + finisthremovelist + " мс. \n");
                    break;
            }
        }

    }
    private static int menu() {
        System.out.println("1. Создать массив из рандомных чисел, введите 1");
        System.out.println("2. Вывести массив, введите 2");
        System.out.println("3. Удалить первый элемент, введите 3");
        System.out.println("4. Создать список из рандомных чисел, введите 5");
        System.out.println("5. Вывести список, введите 6");
        System.out.println("6. Узнать размер списка, введите 6");
        System.out.println("7. Удалить первый элемент списка, введите 7");
        System.out.println("0. Для выхода из приложения введите 0");
        return new Scanner(System.in).nextInt();
    }
}
