package model;

import java.util.List;

import model.state.State;

/**
 * 
 * Implementation of ReverseAnalysis.
 *
 */
public class ReverseAnalysis implements Analysis {

    private Analysis analysis;
    /**
     * 
     * @param analysis
     *          analysis of simulation.
     */
    public ReverseAnalysis(final Analysis analysis) {
        this.analysis = analysis;
    }

    /**
     * String of List of Analysis.
     * @return string of Mutated Bacteria.
     */
    public String revMutation() {
        final String rev = listOfDescription().get(3);
        return "Quantity of bacteria mutated per Species: \n" + rev;
    }

    /**
     * String of List of Analysis.
     * @return string of Predominant Bacteria.
     */
    public String revPredominant() {
        final String rev = listOfDescription().get(0);
        return "Predominant Species: \n" + rev;
    }

    /**
     * String of List of Analysis.
     * @return string of Survived Bacteria.
     */
    public String revSurvived() {
        final String rev = listOfDescription().get(4);
        return "Species are survived: \n" + rev;
    }

    /**
     * String of List of Analysis.
     * @return string of bacteria per species.
     */
    public String revNumberBySpecies() {
        final String rev = listOfDescription().get(1);
        return "Quantity of bacteria per Species: \n" + rev;
    }

    /**
     * String of List of Analysis.
     * @return string of Dead Species.
     */
    public String revDead() {
        final String rev = listOfDescription().get(2);
        return "Species are dead: \n" + rev;
    }

    @Override
    public void addState(final State state) {
        analysis.addState(state);
    }

    @Override
    public String resultPredominant() {
        return analysis.resultPredominant();
    }

    @Override
    public String numberBySpecies() {
        return analysis.numberBySpecies();
    }

    @Override
    public String resultDead() {
        return analysis.resultDead();
    }

    @Override
    public String resultBactMutated() {
        return analysis.resultBactMutated();
    }

    @Override
    public String resultSurvived() {
        return analysis.resultSurvived();
    }

    @Override
    public String getDescription() {
        return analysis.getDescription();
    }

    @Override
    public void updateAnalysis() {
        analysis.updateAnalysis();
    }

    @Override
    public void setMutation(final MutationManager mutManager) {
        analysis.setMutation(mutManager);
    }

    @Override
    public List<String> listOfDescription() {
        return analysis.listOfDescription();
    }
}
