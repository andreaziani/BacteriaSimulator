package model.geneticcode;

import model.Energy;
import model.food.Nutrient;

/**
 * Interface of a part of GeneticCode. It represent the speed of bacteria.
 */
public interface NutrientsGene {
    /**
     * Gene interprets part of the DNA code.
     * eg. ""AAA" "AAT" "AAC" "AAG".
     * @param nutrient
     *          nutrient eat by bacteria.
     * @return an interpretation of DNA.
     */
    Energy interpretNutrients(Nutrient nutrient);
}
