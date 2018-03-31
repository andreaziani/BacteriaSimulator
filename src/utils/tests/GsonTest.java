package utils.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.gson.Gson;

import controller.InitialState;
import model.bacteria.behavior.BehaviorDecoratorOption;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;
import view.model.bacteria.ViewSpecies;

public class GsonTest {
interface A {
    int geta();
    String getb();
}
class AImpl implements A {
    int a;
    String b;
    List<String> c;
    public AImpl() {
        a = 0;
        b = "caoimcie";
        c = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            c.add(b);
        }
    }
    @Override
    public int geta() {
        return a;
    }
    @Override
    public String getb() {
        return b;
    }
}

interface B {
    A getA();
}

class BImpl implements B {
    A a;
    public BImpl() {
        a = new AImpl();
    }
    @Override
    public A getA() {
        return a;
    }
}
class CImpl implements B {
    A a;
    public CImpl(A a) {
        this.a = a;
    }
    @Override
    public A getA() {
        return a;
    }
}

    @Test
    public void testing() {
        String file = "D:\\tmp";
        Gson gson = new Gson();
        Set<DecisionMakerOption> decisionOptions = new HashSet<>();
        List<BehaviorDecoratorOption> decoratorOptions = new ArrayList<>();
        decisionOptions.add(DecisionMakerOption.ALWAYS_EAT);
        decisionOptions.add(DecisionMakerOption.ALWAYS_REPLICATE);
        decisionOptions.add(DecisionMakerOption.RANDOM_MOVEMENT);
        decoratorOptions.add(BehaviorDecoratorOption.COST_FILTER);
        decoratorOptions.add(BehaviorDecoratorOption.PREFERENTIAL_EAT);
        decoratorOptions.add(BehaviorDecoratorOption.PREFERENTIAL_REPLICATE);
        ViewSpecies species = new ViewSpecies("name", new Color(1, 2, 3), decisionOptions, decoratorOptions);
        String json = gson.toJson(species);
        ViewSpecies otherSpecies = gson.fromJson(json, ViewSpecies.class);
        System.out.println(json);
        assertEquals(species.getColor(), otherSpecies.getColor());
        assertEquals(species.getName(), otherSpecies.getName());
        assertEquals(species.getName(), otherSpecies.getName());
        assertTrue(otherSpecies.getDecisionOptions().containsAll(species.getDecisionOptions())
                && otherSpecies.getDecisionOptions().containsAll(species.getDecisionOptions()));
        assertTrue(otherSpecies.getDecoratorOptions().containsAll(species.getDecoratorOptions())
                && otherSpecies.getDecoratorOptions().containsAll(species.getDecoratorOptions()));

        InitialState state = new InitialState();
        state.addSpecies(species);
        state.addSpecies(otherSpecies);
        json = gson.toJson(state);
        InitialState otherState = gson.fromJson(json, InitialState.class);
        for (ViewSpecies s: otherState.getSpecies()) {
            System.out.println(s.getDecisionOptions());
            System.out.println(s.getDecoratorOptions());
        }
        System.out.println(json);

        //GeneticCode code = new GeneticCodeImpl();
        
        A a = new AImpl(); 
        json = gson.toJson(a);
        A a2 = gson.fromJson(json, AImpl.class);
        assertEquals(a.geta(), a2.geta());
        assertEquals(a.getb(), a2.getb());
        
        B b = new BImpl();
        json = gson.toJson(b);
        B b2 = gson.fromJson(json, BImpl.class);
        assertEquals(b.getA().geta(), b2.getA().geta());
        assertEquals(b.getA().getb(), b2.getA().getb());
        b = new CImpl(a);
        json = gson.toJson(b);
        b2 = gson.fromJson(json, CImpl.class);
        assertEquals(b.getA().geta(), b2.getA().geta());
        assertEquals(b.getA().getb(), b2.getA().getb());
    }
}
