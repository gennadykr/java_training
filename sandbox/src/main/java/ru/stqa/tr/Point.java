package ru.stqa.tr;

import static java.lang.Math.sqrt;

/**
 * Created by guena on 14.05.2017.
 */
public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point p) {
        double deltax = p.x - this.x;
        double deltay = p.y - this.y;
        return sqrt(deltax * deltax + deltay * deltay);
    }
}
