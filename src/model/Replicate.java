package model;

import model.action.Action;
import model.food.Nutrient;
import model.bacteria.*;

/**
 * Interface of a classical replicate of a bacteria.
 * With a standard energy bacteria can replicate and create bacteria same him. (????)
 */

public interface Replicate {

    /**
     * 
     */
    Bacteria duplicate();
    
    Boolean radius();
    
    Boolean pos();
    
    Position getDefinitivePos();
    
    Position setDefinitivePosition(Position pos);
}
