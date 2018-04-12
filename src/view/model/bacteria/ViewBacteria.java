package view.model.bacteria;



import view.Radius;

/**
 * Representation of a Bacteria for the View.
 *
 */
public interface ViewBacteria {
    /**
     * @return the radius of the bacteria.
     */
    Radius getRadius();

    /**
     * @return the Species of the Bacteria.
     */
    ViewSpecies getSpecies();
}
