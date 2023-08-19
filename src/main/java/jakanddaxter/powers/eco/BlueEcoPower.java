package jakanddaxter.powers.eco;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import jakanddaxter.powers.BasePower;

import static jakanddaxter.JakandDaxter.makeID;

public class BlueEcoPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(BlueEcoPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private boolean justApplied = false;

    public BlueEcoPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        if (!owner.isPlayer) {
            this.justApplied = true;
        }
    }

    public void atStartOfTurnPostDraw() {
        if (this.owner.isPlayer) {
            AbstractDungeon.player.energy.energy = AbstractDungeon.player.energy.energy + this.amount;
        }
    }
    public void onInitialApplication() {
        if (this.owner.isPlayer) {
            AbstractDungeon.player.energy.energy = AbstractDungeon.player.energy.energy + this.amount;
        }
    }

    @Override
    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        while (this.amount > 5) {
            this.amount--;
        }
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else if (!this.owner.hasPower(this.ID)) {
            addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new BlueEcoPower(owner, amount);
    }
}
