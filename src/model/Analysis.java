package model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import model.bacteria.Bacteria;
import model.bacteria.Species;
/** Final analysis.
 * Interface of Analysis.
 * 
 *
 */
public interface Analysis {
        /**
         * List of all bacteria.
         * @param bacteria
         *          a Map of bacteria and his position.
         * @return a list of bacteria.
         */
        List<Bacteria> listOfBacteria(Map<Position, Bacteria> bacteria);

        /**
         * A set of species.
         * @param bacteria
         *          list of bacteria.
         * @return a set of species.
         * 
         */
        Set<Species> speciesOfBacteria(List<Bacteria> bacteria);

        /**
         * Species have win.
         * @param species
         *          species at the game start.
         * @param bacteria
         *          list of bacteria at the end game.
         * 
         * @return Map of species and bacteria.
         */
        Map<Species, List<Bacteria>> dividedBySpecies(Set<Species> species, List<Bacteria> bacteria);

        /**
         * Number of bacteria alive per species.
         * @param species
         *          species at the game start.
         * @param bacteria
         *          list of bacteria at the end game.
         * @return Map of number of bacteria alive per species.
         */
        Map<Species, Integer> numberBySpecies(Set<Species> species, List<Bacteria> bacteria);

        /**
         * Set of extinct species.
         * @param species
         *           species at the game start.
         * @param bacteria
         *          list of bacteria at the end game.
         * @return Set of extinct species (that doesn't have any bacteria alive).
         */
        Set<Species> dead(Set<Species> species, List<Bacteria> bacteria);

        /**
         * One or more species have more bacteria than other.
         * @param species
         *           species at the game start.
         * @param bacteria
         *          list of bacteria at the end game.
         * @return Map of species have win.
         */
        Map<Species, Integer> win(Set<Species> species, List<Bacteria> bacteria);

        /**
         * Set of Species and her mutated bacteria.
         * @param species
         *          species at the game start.
         * @param bacteria
         *          list of bacteria at the end game.
         * @return Map of Species and number of mutated bacteria.
         */
        Map<Species, Integer> mutated(Set<Species> species, List<Bacteria> bacteria);

        /**
         * Set of Species and her bacteria bacteria that are not changed.
         * @param species
         *          species at the game start.
         * @param bacteria
         *          list of bacteria at the end game.
         * @return Map of Species and number of bacteria that are not changed.
         */
        Map<Species, Integer> notMutated(Set<Species> species, List<Bacteria> bacteria);
}
