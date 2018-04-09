package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

    @Override
    public String getDescription() {
        final List<Bacteria> before = listOfBacteria(lstate.get(0).getBacteriaState());
        final List<Bacteria> after = listOfBacteria(lstate.get(lstate.size() - 1).getBacteriaState());
        final Set<Species> speciesB = speciesOfBacteria(before);
        final Set<Species> speciesA = speciesOfBacteria(after);
        final Map<Species, Integer> wins = win(speciesB, after);
        final SortedMap<Species, Integer> nByS = numberBySpecies(speciesB, after);
        final Set<Species> dead = dead(speciesB, speciesA);
        final List<Bacteria> bactMutated = listOfBacteriaMutated(mutManager.getMutation());
        final Map<Species, Integer> mt = numberBySpecies(speciesB, bactMutated);
        return ("Species survived: " + wins + "\n"
              + "Species dead: " + dead + "\n"
              + "Number by Species: " + nByS + "\n"
              + "Species mutated: " + mt);
    }

}
