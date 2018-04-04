package model;

import model.bacteria.Bacteria;

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
