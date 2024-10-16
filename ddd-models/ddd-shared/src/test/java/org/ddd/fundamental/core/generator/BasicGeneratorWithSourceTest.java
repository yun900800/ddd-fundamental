package org.ddd.fundamental.core.generator;

import org.ddd.fundamental.core.generator.model.Cappuccino;
import org.ddd.fundamental.core.generator.model.Coffee;
import org.ddd.fundamental.core.generator.model.CoffeeWithParam;
import org.junit.Assert;
import org.junit.Test;

public class BasicGeneratorWithSourceTest {

    @Test
    public void testBasicGeneratorWithSourceCreate(){
        GeneratorWithSource<CoffeeWithParam> generator = new BasicGeneratorWithSource<>(
                CoffeeWithParam.class, new Class[]{String.class}
        );
        CoffeeWithParam coffee = generator.next("withParameter");
        Assert.assertEquals("CoffeeWithParam withParameter",coffee.toString());

        GeneratorWithSource<CoffeeWithParam> generator1 = new BasicGeneratorWithSource<>(
                CoffeeWithParam.class, new Class[]{String.class,String.class}
        );
        CoffeeWithParam coffee1 = generator1.next(new Object[]{"name","blue"});
        Assert.assertEquals("CoffeeWithParam name",coffee1.toString());
    }

    @Test
    public void testBasicGenerator() {
        Generator<Coffee> generator = new BasicGenerator<>(Coffee.class);
        Coffee coffee = generator.next();
        Assert.assertEquals(coffee.toString(),"Coffee 0");

        Generator<Cappuccino> generator1 = new BasicGenerator<>(Cappuccino.class);
        Coffee coffee1 = generator1.next();
        Assert.assertEquals(coffee1.toString(),"Cappuccino 1");
    }
}
