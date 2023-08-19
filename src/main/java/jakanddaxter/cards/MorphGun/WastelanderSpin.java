package jakanddaxter.cards.MorphGun;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jakanddaxter.cards.BaseCard;
import jakanddaxter.character.TheSidekick;
import jakanddaxter.util.CardStats;

public class WastelanderSpin extends BaseCard {

    public static final String ID = makeID(WastelanderSpin.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheSidekick.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    //        this.damageTypeForTurn = DamageInfo.DamageType.THORNS;

    public WastelanderSpin() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.baseDamage = 3;
        this.isMultiDamage = true;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public WastelanderSpin(int dmg) {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.baseDamage = dmg/2;
        this.damage = this.baseDamage;
        this.isMultiDamage = true;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new DamageRandomEnemyAction(new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    @Override
    public boolean canUpgrade() {
        return this.timesUpgraded < 2;
    }
}
