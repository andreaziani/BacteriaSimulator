package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import model.bacteria.Bacteria;
import model.bacteria.species.Species;
import model.state.Position;
import model.state.State;

/**
 * 
 * Implementation of Analysis.
 *
 */
public class AnalysisImpl implements Analysis {

    private final transient List<State> lstate = new ArrayList<>();
    private transient MutationManager mutManager = new MutationManagerImpl();
    private transient List<Bacteria> lafter = new ArrayList<>();
    private transient Set<Species> speciesB = new HashSet<>();
    private transient Set<Species> speciesA = new HashSet<>();

    /**
     * Normal constructor that create an analysis without data already in it.
     */
    public AnalysisImpl() {
    }


    @Override
    public void setMutation(final MutationManager mutManager) {
        this.mutManager = mutManager;
    }

    private List<Bacteria> listOfBacteria(final Map<Position, Bacteria> bacteria) {
        final List<Bacteria> bt = new ArrayList<>();
        if (!bacteria.isEmpty()) {
            bt.addAll(bacteria.values());
        }
        return bt;
    }

    private List<Bacteria> listOfBacteriaMutated(final Map<Bacteria, Mutation> bacteria) {
        final List<Bacteria> bt = new ArrayList<>();
        if (!bacteria.isEmpty()) {
            bt.addAll(bacteria.keySet());
        }
        return bt;
    }

    private Set<Species> speciesOfBacteria(final List<Bacteria> bacteria) {
        final Set<Species> sp = new HashSet<>();
        if (!bacteria.isEmpty()) {
            for (final Bacteria bt : bacteria) {
                sp.add(bt.getSpecies());
            }
        }
        return sp;
    }

    private Map<Species, Integer> numberBySpecies(final Set<Species> species, final List<Bacteria> bacteria) {
        final Map<Species, Integer> smap = new HashMap<>();
        Map<Species, List<Bacteria>> bact = new HashMap<>();
        bact = bacteria.stream().collect(Collectors.groupingBy(Bacteria::getSpecies, Collectors.toList()));
        for (final Species sp : species) {
            if (bact.containsKey(sp)) {
                smap.put(sp, bact.get(sp).size());
            }
            smap.putIfAbsent(sp, 0);
        }
        return smap;
    }

    private Set<Species> dead(final Set<Species> speciesB, final Set<Species> speciesA) {
        final Set<Species> dead = new HashSet<>();
        for (final Species sp : speciesB) {
            if (!speciesA.contains(sp)) {
                dead.add(sp);
            }
        }
        return dead;
    }

    private Map<Species, Integer> win(final Set<Species> species, final List<Bacteria> bacteria) {
        final Map<Species, Integer> smap = numberBySpecies(species, bacteria);
        final Map<Species, Integer> wins = new HashMap<>();
        if (!bacteria.isEmpty()) {
            int value = smap.get(bacteria.get(0).getSpecies());
            for (final Species sp : species) {
                if (value == smap.get(sp)) {
                    wins.put(sp, smap.get(sp));
                } else if (value < smap.get(sp)) {
                    wins.clear();
                    value = smap.get(sp);
                    wins.put(sp, value);
                }
            }
        }
        return wins;
    }

    private Set<Species> survived(final Set<Species> speciesB, final Set<Species> speciesA) {
        final Set<Species> survived = new HashSet<>();
        for (final Species sp : speciesB) {
            if (speciesA.contains(sp)) {
                survived.add(sp);
            }
        }
        return survived;
    }

    @Override
    public void addState(final State state) {
        this.lstate.add(state);
    }

    private void before() {
        final List<Bacteria> lbefore = listOfBacteria(this.lstate.get(0).getBacteriaState());
        this.speciesB = speciesOfBacteria(lbefore);
    }

    private void after() {
            this.lafter = listOfBacteria(this.lstate.get(this.lstate.size() - 1).getBacteriaState());
            this.speciesA = speciesOfBacteria(this.lafter);
    }

    private <X> String toString(final Set<X> bacteria) {
        final StringBuilder sb = new StringBuilder();
        final Iterator<X> iter = bacteria.iterator();
        while (iter.hasNext()) {
            sb.append(iter.next());
            if (iter.hasNext()) {
                sb.append('\n');
            }
        }
        if (bacteria.isEmpty()) {
            sb.append("None");
        }
        return sb.toString();

    }

    @Override
    public String resultPredominant() {
        updateAnalysis();
        final Map<Species, Integer> wins = win(this.speciesB, this.lafter);
        return toString(wins.entrySet());
    }

    @Override
    public String numberBySpecies() {
        updateAnalysis();
        final Map<Species, Integer> nByS = numberBySpecies(speciesB, this.lafter);
        return toString(nByS.entrySet());
    }

    @Override
    public String resultDead() {
        updateAnalysis();
        final Set<Species> dead = dead(this.speciesB, this.speciesA);
        return toString(dead);
    }

    @Override
    public String resultBactMutated() {
        updateAnalysis();
        List<Bacteria> bactMutated = new ArrayList<>();
        if (!this.mutManager.getMutation().isEmpty()) {
            bactMutated = listOfBacteriaMutated(this.mutManager.getMutation());
        }
        final Map<Species, Integer> mt = numberBySpecies(this.speciesB, bactMutated);
        return toString(mt.entrySet());
    }

    @Override
    public String resultSurvived() {
        updateAnalysis();
        final Set<Species> sur = survived(this.speciesB, this.speciesA);
        return toString(sur);
    }

    @Override
    public void updateAnalysis() {
        before();
        after();
    }

    @Override
    public String getDescription() {
            updateAnalysis();
            return "Predominant Species: \n" + resultPredominant() + "\n" + "\n"
                                           + "Quantity of bacteria per Species: \n" + numberBySpecies() + "\n" + "\n"
                                           + "Species are dead: \n" + resultDead() + "\n" + "\n"
                                           + "Quantity of bacteria mutated per Species: \n" + resultBactMutated() + "\n" + "\n"
                                           + "Species are survived: \n" + resultSurvived();
    }

}
