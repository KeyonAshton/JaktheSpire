package jakanddaxter.powers.eco.vent;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import jakanddaxter.powers.BasePower;
import jakanddaxter.powers.eco.YellowEcoPower;

import static jakanddaxter.JakandDaxter.makeID;

public class YellowVentPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(YellowVentPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public YellowVentPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (this.owner.getPower(YellowEcoPower.POWER_ID).amount < 5 || !this.owner.hasPower(YellowEcoPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(this.owner, this.owner, new YellowEcoPower(this.owner, 1), 1));
            flash();
        } else if (this.owner.getPower(YellowEcoPower.POWER_ID).amount > 4) {
            flash();
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atEndOfRound() {
        while (this.amount > 1) {
            this.amount--;
        }
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new YellowVentPower(owner, amount);
    }
}
