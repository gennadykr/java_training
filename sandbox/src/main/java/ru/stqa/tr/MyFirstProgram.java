package ru.stqa.tr;


public class MyFirstProgram {

    public static void main(String[] args) {
        System.out.println("Hello, world!");

        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        System.out.println("Distance between two point is: " + p1.distance(p2));
    }

}
