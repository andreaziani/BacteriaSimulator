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

    private Map<Species, List<Bacteria>> dividedBySpecies(final Set<Species> species, final List<Bacteria> bacteria) {
        final Map<Species, List<Bacteria>> map = new HashMap<>();
        for (final Species sp : species) {
            final List<Bacteria> list = new ArrayList<>();
            for (final Bacteria bt : bacteria) {
                if (sp.equals(bt.getSpecies())) {
                    list.add(bt);
                }
            }
            map.put(sp, list);
        }
        return map;
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

    private Set<Species> dead(final Set<Species> species, final List<Bacteria> bacteria) {
        final Map<Species, Integer> map = numberBySpecies(species, bacteria);
        final Set<Species> dead = new HashSet<>();
        for (final Species sp : species) {
            final int value = map.get(sp);
            if (value == 0) {
                dead.add(sp);
            }
        }
        return dead;
    }

    private Map<Species, Integer> win(final Set<Species> species, final List<Bacteria> bacteria) {
        final SortedMap<Species, Integer> smap = numberBySpecies(species, bacteria);
        final Map<Species, Integer> wins = new HashMap<>();
        final int value = smap.get(bacteria.get(0).getSpecies());
        for (final Species sp : species) {
            if (value == smap.get(sp)) {
                wins.put(sp, smap.get(sp));
            } else if (value < smap.get(sp)) {
                wins.clear();
                wins.put(sp, smap.get(sp));
            }
        }
        return wins;
    }

    private Map<Species, Integer> mutated(final Set<Species> species, final List<Bacteria> bacteriaMutated) {
        final Map<Species, Integer> map = numberBySpecies(species, bacteriaMutated);
        return map;
    }

    private Map<Species, Integer> notMutated(final Set<Species> species, final List<Bacteria> bacteriaMutated, final List<Bacteria> bacteria) {
        final Map<Species, Integer> btM = mutated(species, bacteriaMutated);
        final Map<Species, Integer> btNotM = new HashMap<>();
        final Map<Species, Integer> allBt = numberBySpecies(species, bacteria);
        for (final Species sp : species) {
            btNotM.put(sp, (allBt.get(sp) - btM.get(sp)));
        }
        return btNotM;
    }

    @Override
    public void addState(final State state) {
        // TODO Auto-generated method stub
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

}
