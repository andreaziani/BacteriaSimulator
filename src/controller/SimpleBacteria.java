package controller;

import java.util.List;

import model.bacteria.Bacteria;
import model.geneticcode.NucleicAcid;
import view.model.bacteria.ViewSpecies;

/**
 * Simplification of a Bacteria object that maintains only useful information
 * for saving and loading simulations and is easily serializable via json.
 */
public class SimpleBacteria {
    private final double radius;
    private final double perceptionRadius;
    private final ViewSpecies species;
    private final List<NucleicAcid> code;

    /**
     * Create a SimpleBacteria from a Bacteria and a view representation of the
     * bacteria's species. No control is being made to assure that the species given
     * is correct in comparison to the species actually indicated by the bacteria.
     * 
     * @param bacteria
     *            a bacteria.
     * @param species
     *            a view representation of the bacteria's species.
     */
    public SimpleBacteria(final Bacteria bacteria, final ViewSpecies species) {
        this.radius = bacteria.getRadius();
        this.perceptionRadius = bacteria.getPerceptionRadius();
        this.species = species;
        this.code = bacteria.getGeneticCode().getCode().getCode();
    }

    /**
     * @return the radius of the bacteria.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @return the radius of perception of the bacteria.
     */
    public double getPerceptionRadius() {
        return perceptionRadius;
    }

    /**
     * @return the view representation of the species of the bacteria.
     */
    public ViewSpecies getSpecies() {
        return species;
    }

    /**
     * @return the row version of the bacteria's genetic code, represented as a list
     *         of nucleic acids.
     */
    public List<NucleicAcid> getCode() {
        return code;
    }
}
