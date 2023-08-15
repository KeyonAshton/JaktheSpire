package jakthespire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import jakthespire.character.TheSidekick;
import jakthespire.powers.GreenEcoVentPower;
import jakthespire.util.CardStats;

import static jakthespire.helpers.CustomTags.TAG_GREEN_ECO;

public class GreenEcoCard extends BaseCard{

    public static final String ID = makeID(GreenEcoCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheSidekick.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like  Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public GreenEcoCard() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(3);
        tags.add(TAG_GREEN_ECO);
        keywords.add("jakthespire:green_eco");
    }

    @Override
    public void upgrade() {
        if (timesUpgraded < 3) {
            name = cardStrings.EXTENDED_DESCRIPTION[timesUpgraded * 2];
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[timesUpgraded * 2 + 1];
            initializeDescription();
        }
        if (timesUpgraded == 0) {
            upgradeMagicNumber(2);
        } else if (timesUpgraded == 1) {
            upgradeBaseCost(2);
            upgradeMagicNumber(45);
            this.setExhaust(true);
        } else {
            upgradeBaseCost(3);
            this.baseMagicNumber = 0;
            this.magicNumber = this.baseMagicNumber;
            this.upgradedMagicNumber = true;
            this.type = CardType.POWER;
            tags.remove(TAG_GREEN_ECO);
            this.setExhaust(false);
        }
        ++timesUpgraded;
        upgraded = true;
    }
    @Override
    public boolean canUpgrade() {
        return timesUpgraded < 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        if (timesUpgraded > 2) {
            addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new GreenEcoVentPower((AbstractCreature)p, 1), 1));
        }
    }

    public AbstractCard makeCopy() {
        return new GreenEcoCard();
    }

}
