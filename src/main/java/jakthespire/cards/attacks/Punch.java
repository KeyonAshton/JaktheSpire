package jakthespire.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jakthespire.cards.BaseCard;
import jakthespire.character.TheSidekick;
import jakthespire.powers.eco.YellowEcoPower;
import jakthespire.util.CardStats;

public class Punch extends BaseCard {

    public static final String ID = makeID(Punch.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheSidekick.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Punch() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.baseDamage = 6;
        this.tags.add(AbstractCard.CardTags.STRIKE);
        this.tags.add(AbstractCard.CardTags.STARTER_STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(YellowEcoPower.POWER_ID)) {
            if (Settings.isDebug) {
                if (Settings.isInfo) {
                    this.multiDamage = new int[(AbstractDungeon.getCurrRoom()).monsters.monsters.size()];
                    for (int i = 0; i < (AbstractDungeon.getCurrRoom()).monsters.monsters.size(); i++)
                        this.multiDamage[i] = 150;
                    addToBot((AbstractGameAction) new DamageAllEnemiesAction((AbstractCreature) p, this.multiDamage, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                } else {
                    addToBot((AbstractGameAction) new DamageAction((AbstractCreature) m, new DamageInfo((AbstractCreature) p, 150, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                }
            } else {
                addToBot((AbstractGameAction) new DamageAction((AbstractCreature) m, new DamageInfo((AbstractCreature) p, this.damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        } else {
            if (Settings.isDebug) {
                if (Settings.isInfo) {
                    this.multiDamage = new int[(AbstractDungeon.getCurrRoom()).monsters.monsters.size()];
                    for (int i = 0; i < (AbstractDungeon.getCurrRoom()).monsters.monsters.size(); i++)
                        this.multiDamage[i] = 150;
                    addToBot((AbstractGameAction) new DamageAllEnemiesAction((AbstractCreature) p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                } else {
                    addToBot((AbstractGameAction) new DamageAction((AbstractCreature) m, new DamageInfo((AbstractCreature) p, 150, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                }
            } else {
                addToBot((AbstractGameAction) new DamageAction((AbstractCreature) m, new DamageInfo((AbstractCreature) p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        }
    }

    public AbstractCard makeCopy() {
        return new Punch();
    }
}
