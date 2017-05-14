package ru.stqa.tr;


import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTest {

    @Test
    public void testDistance(){
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        Assert.assertEquals(p1.distance(p2),5.0);
    }

    @Test
    public void testDistanceZero(){
        Point p = new Point(0, 0);
        Assert.assertEquals(p.distance(p),0.0);
    }

}
