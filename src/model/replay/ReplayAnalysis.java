package model.replay;

import model.Analysis;
import model.MutationManager;
import model.state.State;

/**
 * Implementation of Analysis that store the results of an Analysis of a
 * simulation to be represented in a replay.
 */
public final class ReplayAnalysis implements Analysis {
    private final String predominant;
    private final String speciesNumbers;
    private final String deadResults;
    private final String mutationResults;
    private final String survivedResults;
    private final String description;

    /**
     * Create a ReplayAnalysis coping results from a previous Analysis.
     * 
     * @param analysis
     *            an Analysis.
     */
    public ReplayAnalysis(final Analysis analysis) {
        this.predominant = analysis.resultPredominant();
        this.speciesNumbers = analysis.numberBySpecies();
        this.deadResults = analysis.resultDead();
        this.mutationResults = analysis.resultBactMutated();
        this.survivedResults = analysis.resultSurvived();
        this.description = analysis.getDescription();
    }

    @Override
    public void addState(final State state) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String resultPredominant() {
        return this.predominant;
    }

    @Override
    public String numberBySpecies() {
        return this.speciesNumbers;
    }

    @Override
    public String resultDead() {
        return this.deadResults;
    }

    @Override
    public String resultBactMutated() {
        return this.mutationResults;
    }

    @Override
    public String resultSurvived() {
        return this.survivedResults;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void updateAnalysis() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMutation(final MutationManager mutManager) {
        throw new UnsupportedOperationException();
    }

}
