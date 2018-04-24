package model.simulator.task;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import model.Energy;
import model.bacteria.Bacteria;
import model.bacteria.BacteriaImpl;
import model.bacteria.species.Species;
import model.geneticcode.CopyFactory;
import model.geneticcode.CopyFactoryImpl;
import model.geneticcode.GeneticCode;
import model.simulator.BacteriaEnvironment;
import model.simulator.EnvironmentUtil;
import model.state.Position;
import utils.Logger;

public class ReplicateBacteriaTask extends BacteriaTask {
    private final CopyFactory geneFactory;

    public ReplicateBacteriaTask(Position position, Bacteria bacteria, BacteriaEnvironment environment,
            Position maxPosition, final CopyFactory geneFactory) {
        super(position, bacteria, environment, maxPosition);
        this.geneFactory = geneFactory;
    }

    private Bacteria cloneBacteria(final Bacteria bacteria) {
        final int bacteriaID = this.environment.getNumberOfBacteria();
        final Species species = bacteria.getSpecies();
        final GeneticCode geneticCode = this.geneFactory.copyGene(bacteria.getGeneticCode());
        final Energy halfEnergy = bacteria.getEnergy().multiply(0.5);
        bacteria.spendEnergy(halfEnergy);

        return new BacteriaImpl(bacteriaID, species, geneticCode, halfEnergy);
    }

    @Override
    public void execute() {
        Logger.getInstance().info("REPLIC " + this.environment.getQuadrant(position),
                "THREAD" + Thread.currentThread().getId() + " IN");
        if (!bacteria.isReplicating()) {
            bacteria.startReplicating();
            final double bacteriaRadius = bacteria.getRadius();

            final List<Position> positions = EnvironmentUtil
                    .circularPositionStream((int) Math.ceil(bacteriaRadius * 2), position,
                            this.maxPosition)
                    .filter(position -> EnvironmentUtil.causeCollision(position, bacteriaRadius, maxPosition,
                            pos -> this.environment.isPositionOccupied(pos)))
                    .collect(Collectors.toList());

            if (!positions.isEmpty()) {
                final Bacteria newBacteria = cloneBacteria(bacteria);
                final Position freePosition = positions
                        .get(ThreadLocalRandom.current().nextInt(positions.size()));
                this.environment.insertBacteria(freePosition, newBacteria);
            }
        }
        Logger.getInstance().info("REPLIC " + this.environment.getQuadrant(position),
                "THREAD" + Thread.currentThread().getId() + " OUT");
    }
}
