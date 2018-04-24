package model.replay;

import java.util.List;

import model.Analysis;
import model.MutationManager;
import model.state.State;

public class ReplayAnalysis implements Analysis{
    private final String resultPredominant;
    private final String numberBySpecies;
    private final String resultDead;
    private final String resultBactMutated;
    private final String resultSurvived;
    private final String description;

    public ReplayAnalysis(final Analysis analysis) {
        super();
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
    public void setMutation(MutationManager mutManager) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> listOfDescription() {
        throw new UnsupportedOperationException();
    }

}
