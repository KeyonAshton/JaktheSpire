package jakanddaxter.cards.eco;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jakanddaxter.cards.BaseCard;
import jakanddaxter.character.TheSidekick;
import jakanddaxter.powers.eco.DarkEcoPower;
import jakanddaxter.powers.eco.LightEcoPower;
import jakanddaxter.util.CardStats;

import static jakanddaxter.helpers.CustomTags.*;

public class LightEcoCard extends BaseCard {

    public static final String ID = makeID(LightEcoCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheSidekick.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like  Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public LightEcoCard() {
        super(ID, info);
        tags.add(TAG_ECO);
        keywords.add("${modID}:Light_Eco");
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
    }

    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower(DarkEcoPower.POWER_ID)) {
            addToBot(new RemoveSpecificPowerAction(p, p, DarkEcoPower.POWER_ID));
        }
        addToBot(new ApplyPowerAction(p, p, new LightEcoPower(p, 1), 1));
    }
}
