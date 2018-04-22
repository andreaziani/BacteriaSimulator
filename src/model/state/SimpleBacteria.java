package model.state;

import java.util.List;
import java.util.Objects;

import model.bacteria.Bacteria;
import model.bacteria.species.SpeciesOptions;
import model.geneticcode.NucleicAcid;

/**
 * Simplification of a Bacteria object that maintains only useful information
 * for saving and loading simulations and is easily serializable via json.
 */
public final class SimpleBacteria {
    private final int id;
    private final double radius;
    private final double perceptionRadius;
    private final SpeciesOptions species;
    private final List<NucleicAcid> code;

    /**
     * Create a SimpleBacteria from a Bacteria and a the options to construct
     * bacteria's species. No control is being made to assure that the species given
     * is correct in comparison to the species actually indicated by the bacteria.
     * 
     * @param bacteria
     *            a bacteria.
     * @param species
     *            a simple representation of the bacteria's species from the options
     *            that make it.
     */
    public SimpleBacteria(final Bacteria bacteria, final SpeciesOptions species) {
        this.id = bacteria.getId();
        this.radius = bacteria.getRadius();
        this.perceptionRadius = bacteria.getPerceptionRadius();
        this.species = species;
        this.code = bacteria.getGeneticCode().getCode().getCode();
    }

    /**
     * @return the id of the bacteria.
     */
    public int getId() {
        return id;
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
     * @return the options to build the species of the bacteria.
     */
    public SpeciesOptions getSpecies() {
        return species;
    }

    /**
     * @return the raw version of the bacteria's genetic code, represented as a list
     *         of nucleic acids.
     */
    public List<NucleicAcid> getCode() {
        return code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, radius, perceptionRadius, species);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SimpleBacteria other = (SimpleBacteria) obj;
        boolean result = Objects.equals(this.id, other.id) && Objects.equals(this.radius, other.radius)
                && Objects.equals(this.perceptionRadius, other.perceptionRadius)
                && Objects.equals(this.species, other.species) && Objects.equals(this.code.size(), other.code.size());
        for (int i = 0; result && i < this.code.size(); i++) {
            result = Objects.equals(this.code.get(i), other.code.get(i));
        }
        return result;
    }

}
