package org.jikvict.tasks.exposed;

public class DurationExtenderDecorator extends PotionDecorator {
    private int multiplier;

    public DurationExtenderDecorator(Potion wrappedPotion, int multiplier) {
        super(wrappedPotion);
        this.multiplier = multiplier;
    }

    @Override
    public int getDuration() {
        return super.getDuration() * multiplier;
    }

    @Override
    public String getDescription()
    {
        return super.getDescription() + ", Duration x" + multiplier;
    }
}
