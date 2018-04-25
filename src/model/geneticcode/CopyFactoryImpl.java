package model.geneticcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Implementation of CopyFactory.
 *
 */
public class CopyFactoryImpl implements CopyFactory {

    @Override
    public final GeneticCode copyGene(final GeneticCode gc) {
        final List<NucleicAcid> copy = new ArrayList<>();
        copy.addAll(gc.getCode().getCode());
        final Gene copyGene = new GeneImpl(copy);
        final double radius = gc.getRadius();
        final double perceptionRaius = gc.getPerceptionRadius();
        return new GeneticCodeImpl(copyGene, radius, perceptionRaius);
    }
}
