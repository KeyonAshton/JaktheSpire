package jakanddaxter.powers.eco;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import jakanddaxter.powers.BasePower;

import static jakanddaxter.JakandDaxter.makeID;

public class YellowEcoPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(YellowEcoPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private boolean justApplied = false;

    public YellowEcoPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        if (!owner.isPlayer) {
            this.justApplied = true;
        }
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        for (int i = 0; i < this.amount; i++) {
            addToTop(new DamageAction((target), new DamageInfo(target, this.amount, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            if (target.hasPower(MetallicizePower.POWER_ID)) {
                addToTop(new ReducePowerAction(target, this.owner, MetallicizePower.POWER_ID, 1));
            }
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
        return new YellowEcoPower(owner, amount);
    }
}
