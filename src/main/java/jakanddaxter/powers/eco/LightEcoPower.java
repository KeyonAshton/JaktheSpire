package jakanddaxter.powers.eco;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import jakanddaxter.powers.BasePower;

import static jakanddaxter.JakandDaxter.makeID;

public class LightEcoPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(LightEcoPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public LightEcoPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void atStartOfTurnPostDraw() {
    }

    public void onInitialApplication() {
    }

    @Override
    public void atEndOfRound() {
        while (this.amount > 1) {
            addToBot(new ReducePowerAction(this.owner, this.owner, LightEcoPower.POWER_ID, 1));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LightEcoPower(owner, amount);
    }
}
