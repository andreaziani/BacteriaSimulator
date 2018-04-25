package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import model.bacteria.Bacteria;
/**
 * 
 * Implementation of MutationManagerImpl.
 *
 */
public class MutationManagerImpl implements MutationManager {
    private final Map<Bacteria, Mutation> mutations;
    /**
     * Create an empty MutationManager.
     */
    public MutationManagerImpl() {
        this.mutations = new HashMap<>();
    }

    @Override
    public final void updateMutation(final Collection<Bacteria> bactManager) {
            for (final Bacteria b: bactManager) {
                if (!mutations.containsKey(b)) {
                    mutations.put(b, new MutationImpl(b.getGeneticCode().getCode()));
                }
                mutations.get(b).alteratedCode();
            }
    }

    @Override
    public final Map<Bacteria, Mutation> getMutation() {
        return this.mutations;
    }
}
