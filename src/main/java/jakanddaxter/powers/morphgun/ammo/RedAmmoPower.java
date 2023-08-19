package jakanddaxter.powers.morphgun.ammo;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import jakanddaxter.powers.BasePower;

import static jakanddaxter.JakandDaxter.makeID;

public class RedAmmoPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(RedAmmoPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private boolean justApplied = false;

    public RedAmmoPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        if (!owner.isPlayer) {
            this.justApplied = true;
        }
    }

    @Override
    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, RedAmmoPower.POWER_ID));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new RedAmmoPower(owner, amount);
    }
}
