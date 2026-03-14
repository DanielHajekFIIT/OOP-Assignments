package org.jikvict.tasks.exposed;

public class HealingBoostDecorator extends PotionDecorator {
    private int bonus;

    public HealingBoostDecorator(Potion wrappedPotion, int bonus) {
        super(wrappedPotion);
        this.bonus = bonus;
    }

    @Override
    public int getHealingPower() {
        return super.getHealingPower() + bonus;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Healing Boost +" + bonus;
    }
}
