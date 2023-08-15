package jakthespire.cards;

import com.evacipated.cardcrawl.mod.stslib.patches.DamageModifierPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EnemyData;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jakthespire.character.TheSidekick;
import jakthespire.powers.GreenEcoVentPower;
import jakthespire.util.CardStats;

public class SpinKick extends BaseCard{

    public static final String ID = makeID(SpinKick.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheSidekick.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    //        this.damageTypeForTurn = DamageInfo.DamageType.THORNS;

    public SpinKick() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.baseDamage = 3;
        this.isMultiDamage = true;
        this.tags.add(CardTags.STRIKE);
        setMagic(2);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeDamage(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo.DamageType dmgtype;
        if (p.hasPower(GreenEcoVentPower.POWER_ID)) {
            dmgtype = this.damageTypeForTurn;
        } else {
            dmgtype = DamageInfo.DamageType.THORNS;
        }
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot((AbstractGameAction) new DamageAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), new DamageInfo((AbstractCreature) p, this.damage, dmgtype), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    public AbstractCard makeCopy() {
        return new SpinKick();
    }
}
