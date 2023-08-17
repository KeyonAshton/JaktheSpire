package jakanddaxter.powers.eco;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import jakanddaxter.cards.eco.GreenEcoCard;
import jakanddaxter.powers.BasePower;

import static jakanddaxter.JakandDaxter.makeID;

public class RedVentPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("RedVentPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing this controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if they're a buff or debuff.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public RedVentPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (this.owner.getPower(RedEcoPower.POWER_ID).amount < 4 || !this.owner.hasPower(RedEcoPower.POWER_ID)) {
            addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)this.owner, (AbstractCreature)this.owner, (AbstractPower)new RedEcoPower((AbstractCreature)this.owner, 1), 2));
            flash();
        } else if (this.owner.getPower(RedEcoPower.POWER_ID).amount == 4) {
            addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) this.owner, (AbstractCreature) this.owner, (AbstractPower) new RedEcoPower((AbstractCreature) this.owner, 1), 1));
            flash();
        }else if (this.owner.getPower(RedEcoPower.POWER_ID).amount > 5) {
            flash();
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new RedVentPower(owner, amount);
    }
}
