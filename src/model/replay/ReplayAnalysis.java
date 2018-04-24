package model.replay;

import model.Analysis;
import model.MutationManager;
import model.state.State;
/**
 * Implementation of Analysis that store the results of an Analysis of a simulation to be represented in a replay.
 */
public final class ReplayAnalysis implements Analysis {
    private final String resultPredominant;
    private final String numberBySpecies;
    private final String resultDead;
    private final String resultBactMutated;
    private final String resultSurvived;
    private final String description;
/**
 * Create a ReplayAnalysis coping results from a previous Analysis.
 * @param analysis an Analysis.
 */
    public ReplayAnalysis(final Analysis analysis) {
        this.resultPredominant = analysis.resultPredominant();
        this.numberBySpecies = analysis.numberBySpecies();
        this.resultDead = analysis.resultDead();
        this.resultBactMutated = analysis.resultBactMutated();
        this.resultSurvived = analysis.resultSurvived();
        this.description = analysis.getDescription();
    }

    @Override
    public void addState(final State state) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String resultPredominant() {
        return this.resultPredominant;
    }

    @Override
    public String numberBySpecies() {
        return this.numberBySpecies;
    }

    @Override
    public String resultDead() {
        return this.resultDead;
    }

    @Override
    public String resultBactMutated() {
        return this.resultBactMutated;
    }

    @Override
    public String resultSurvived() {
        return this.resultSurvived;
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
