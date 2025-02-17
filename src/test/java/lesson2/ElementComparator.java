package lesson2;

import org.openqa.selenium.WebElement;

public class ElementComparator {
    public static void compareElements(WebElement element1, WebElement element2) {
        int element1Top = element1.getLocation().getY();
        int element1Left = element1.getLocation().getX();
        int element1Width = element1.getSize().getWidth();
        int element1Height = element1.getSize().getHeight();
        int element1Area = element1Width * element1Height;
        int element2Top = element2.getLocation().getY();
        int element2Left = element2.getLocation().getX();
        int element2Width = element2.getSize().getWidth();
        int element2Height = element2.getSize().getHeight();
        int element2Area = element2Width * element2Height;
        if (element1Top < element2Top) {
            System.out.println("Element 1 is higher.");
        } else if (element1Top > element2Top) {
            System.out.println("Element 2 is higher.");
        } else {
            System.out.println("Both elements are on the same height.");
        }
        if (element1Left < element2Left) {
            System.out.println("Element 1 is more to the left.");
        } else if (element1Left > element2Left) {
            System.out.println("Element 2 is more to the left.");
        } else {
            System.out.println("Both elements are aligned horizontally.");
        }
        if (element1Area > element2Area) {
            System.out.println("Element 1 is bigger.");
        } else if (element1Area < element2Area) {
            System.out.println("Element 2 is bigger.");
        } else {
            System.out.println("Both elements are the same size.");
        }
    }
}

