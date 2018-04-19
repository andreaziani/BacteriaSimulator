package view.model.bacteria;



import model.bacteria.species.SpeciesOptions;
import view.Radius;

/**
 * Representation of a Bacteria for the View.
 */
public interface ViewBacteria {
    /**
     * @return the radius of the bacteria.
     */
    Radius getRadius();

    /**
     * @return the Species of the Bacteria.
     */
    SpeciesOptions getSpecies();
}
