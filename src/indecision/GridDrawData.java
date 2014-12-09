/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indecision;

import java.awt.Point;

/**
 *
 * @author mayajones
 */
public interface GridDrawData {
    public int getCellHeight();
    public int gerCellWidth();
    
    public Point getCellCoordinate(Point cellCoordinate);
    
}
