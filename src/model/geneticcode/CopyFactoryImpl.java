package model.geneticcode;
/**
 * 
 * Implementation of CopyFactory.
 *
 */
public class CopyFactoryImpl implements CopyFactory {

    @Override
    public GeneticCode copyGene(final GeneticCode gc) {
        final Gene copy = gc.getCode();
        final double radius = gc.getRadius();
        final double perceptionRaius = gc.getPerceptionRadius();
        return new GeneticCodeImpl(copy, radius, perceptionRaius);
    }
}
