package model.geneticcode;

import java.util.List;

import model.Energy;
import model.action.ActionType;
import model.food.Nutrient;

/**
 * Implementation of interface NutrientsGene.
 */
public class NutrientsGeneImpl implements NutrientsGene {

    public Gene code;
    private List<Integer> list;
    private static final int VAR_CARBOHYDRATES = 3;
    private static final int VAR_WATER = 1;
    private static final int VAR_PEPTONES = 8;
    private static final int VAR_HYDROLYSATES = 5;
    private static final int VAR_INORGANIC_SALT = 1;
    public List<Nutrient> nutrients;
    /**
     * Construct a SpeedGene of GeneticCode.
     * 
     * @param code
     *          the code of DNA.
     */
    public NutrientsGeneImpl(final Gene code, final List<Nutrient> nutrients) {
        this.code = code;
        this.nutrients = nutrients;
        for (int i = 7; i < 10; i++) {
            list.add(i);
        }
    }

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public Energy interpretNutrients(final Nutrient nutrient) {
        Energy b = null;
        if (nutrients.equals(Nutrient.CARBOHYDRATES)) {
            b = code.interpret(list, VAR_CARBOHYDRATES);
        } else if (nutrients.equals(Nutrient.HYDROLYSATES)) {
            b = code.interpret(list, VAR_HYDROLYSATES);
        } else if (nutrients.equals(Nutrient.WATER)) {
            b = code.interpret(list, VAR_WATER);
        } else if (nutrients.equals(Nutrient.PEPTONES)) {
            b = code.interpret(list, VAR_PEPTONES);
        } else if (nutrients.equals(Nutrient.INORGANIC_SALT)) {
            b = code.interpret(list, VAR_INORGANIC_SALT);
        } else {
            throw new IllegalArgumentException("error");
        }
        return b;
    }
}
