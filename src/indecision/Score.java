/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indecision;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author mayajones
 */
public class Score {
    
    public void draw(Graphics graphics){
        graphics.setColor(Color.BLACK);
        graphics.setFont(font);
        graphics.drawString("Score;" + value, position.x,position.y);
    }

    private int value=0;
    private Point position;
    private Font font = new Font("Apple Chancery",Font.PLAIN,44);

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @param value the amount to add to the current value
     */
    public void addToValue(int amount) {
        this.value += amount;
    }

    /**
     * @return the position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Point position) {
        this.position = position;
    }

}
