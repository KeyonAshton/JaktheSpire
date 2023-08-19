package jakanddaxter.cards.MorphGun.Modes;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jakanddaxter.cards.BaseCard;
import jakanddaxter.character.TheSidekick;
import jakanddaxter.powers.morphgun.*;
import jakanddaxter.powers.morphgun.mode.*;
import jakanddaxter.util.CardStats;

public class RedMorphMode extends BaseCard {

    public static final String ID = makeID(RedMorphMode.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheSidekick.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like  Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public RedMorphMode() {
        super(ID, info);
        rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            name = cardStrings.EXTENDED_DESCRIPTION[1];
            rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[2];
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        switch (new MorphGunTest().getMorphGunMode()) {
            case ("ScatterGun"):
                addToBot(new RemoveSpecificPowerAction(p, p, ScatterGun.POWER_ID));
            case ("WaveConcussor"):
                addToBot(new RemoveSpecificPowerAction(p, p, WaveConcussor.POWER_ID));
            case ("Blaster"):
                addToBot(new RemoveSpecificPowerAction(p, p, Blaster.POWER_ID));
            case ("BeamReflexor"):
                addToBot(new RemoveSpecificPowerAction(p, p, BeamReflexor.POWER_ID));
            case ("VulcanFury"):
                addToBot(new RemoveSpecificPowerAction(p, p, VulcanFury.POWER_ID));
            case ("ArcWielder"):
                addToBot(new RemoveSpecificPowerAction(p, p, ArcWielder.POWER_ID));
            case ("PeaceMaker"):
                addToBot(new RemoveSpecificPowerAction(p, p, PeaceMaker.POWER_ID));
            case ("SuperNova"):
                addToBot(new RemoveSpecificPowerAction(p, p, SuperNova.POWER_ID));
        }
        if (this.upgraded) {
            addToBot(new ApplyPowerAction(p, p, new WaveConcussor(p, 1), 1));
        } else {
            addToBot(new ApplyPowerAction(p, p, new ScatterGun(p, 1), 1));
        }
    }
}
