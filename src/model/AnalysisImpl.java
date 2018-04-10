package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import model.bacteria.Bacteria;
import model.bacteria.Species;
/**
 * 
 * Implementation of Analysis.
 *
 */
public class AnalysisImpl implements Analysis {

    private final List<State> lstate = new ArrayList<>();
    private final MutationManager mutManager = new MutationManagerImpl();
    private List<Bacteria> lbefore = new ArrayList<>();
    private List<Bacteria> lafter = new ArrayList<>();
    private Set<Species> speciesB = new HashSet<>();
    private Set<Species> speciesA = new HashSet<>();

    private List<Bacteria> listOfBacteria(final Map<Position, Bacteria> bacteria) {
        final List<Bacteria> bt = new ArrayList<>();
        bt.addAll(bacteria.values());
        return bt;
    }

    private List<Bacteria> listOfBacteriaMutated(final Map<Bacteria, Mutation> bacteria) {
        final List<Bacteria> bt = new ArrayList<>();
        bt.addAll(bacteria.keySet());
        return bt;
    }

    private Set<Species> speciesOfBacteria(final List<Bacteria> bacteria) {
        final Set<Species> sp = new HashSet<>();
        for (final Bacteria bt : bacteria) {
            sp.add(bt.getSpecies());
        }
        return sp;
    }

    private SortedMap<Species, Integer> numberBySpecies(final Set<Species> species, final List<Bacteria> bacteria) {
        final SortedMap<Species, Integer> smap = new TreeMap<>();
        for (final Species sp : species) {
            for (final Bacteria bt : bacteria) {
                if (sp.equals(bt.getSpecies())) {
                    final int count = smap.containsKey(sp) ? smap.get(sp) : 0;
                    smap.put(sp, count + 1);
                }
            }
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
        final SortedMap<Species, Integer> smap = numberBySpecies(species, bacteria);
        final Map<Species, Integer> wins = new HashMap<>();
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
        return wins;
    }

    @Override
    public void addState(final State state) {
        this.lstate.add(state);
    }

    private void before() {
        this.lbefore = listOfBacteria(this.lstate.get(0).getBacteriaState());
        this.speciesB = speciesOfBacteria(this.lbefore);
    }

    private void after() {
        this.lafter = listOfBacteria(this.lstate.get(this.lstate.size() - 1).getBacteriaState());
        this.speciesA = speciesOfBacteria(this.lafter);
    }

    private String toString(final Map<Species, Integer> mapBacteria) {
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<Species, Integer>> iter = mapBacteria.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<Species, Integer> entry = iter.next();
            sb.append(entry.getKey());
            sb.append(':').append('"');
            sb.append(entry.getValue());
            sb.append('"');
            if (iter.hasNext()) {
                sb.append(',').append('\n');
            }
        }
        return sb.toString();

    }

    private String resultWins() {
        final Map<Species, Integer> wins = win(this.speciesB, this.lafter);
        return toString(wins);
    }

    private String resultNByS() {
        final SortedMap<Species, Integer> nByS = numberBySpecies(speciesB, this.lafter);
        return toString(nByS);
    }

    private String resultDead() {
        final Set<Species> dead = dead(this.speciesB, this.speciesA);
        return dead.toString();
    }

    private String resultBactMutated() {
        final List<Bacteria> bactMutated = listOfBacteriaMutated(this.mutManager.getMutation());
        final Map<Species, Integer> mt = numberBySpecies(this.speciesB, bactMutated);
        return toString(mt);
    }

    @Override
    public String getDescription() {
        before();
        after();
        return ("Species survived: \n" + resultWins() + "\n"
              + "Species dead: \n" + resultDead() + "\n"
              + "Number by Species: \n" + resultNByS() + "\n"
              + "Species mutated: \n" + resultBactMutated());
    }

}
