package jakanddaxter.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jakanddaxter.cards.BaseCard;
import jakanddaxter.cards.defense.Jump;
import jakanddaxter.cards.defense.Roll;
import jakanddaxter.character.TheSidekick;
import jakanddaxter.util.CardStats;

public class Punch extends BaseCard {

    public static final String ID = makeID(Punch.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheSidekick.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.BASIC, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
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

    public void triggerWhenDrawn() {
        boolean h = !AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty();
        if (h) {
            String c = (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1)).cardID;
            if (c.equals(Jump.ID)) {
                this.cardsToPreview = new GroundSlam();
                rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            } else if (c.equals(Uppercut.ID)) {
                GroundSlam groundSlam = new GroundSlam();
                groundSlam.upgrade();
                this.cardsToPreview = groundSlam;
                rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            } else if (c.equals(Roll.ID)) {
                this.cardsToPreview = new Uppercut();
                rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1];
            } else {
                this.cardsToPreview = null;
                rawDescription = cardStrings.DESCRIPTION;
            }
        } else {
            this.cardsToPreview = null;
            rawDescription = cardStrings.DESCRIPTION;
        }
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        boolean h = !AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty();
        if (h) {
            if (c.cardID.equals(Jump.ID)) {
                this.cardsToPreview = new GroundSlam();
                rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            } else if (c.cardID.equals(Uppercut.ID)) {
                GroundSlam groundSlam = new GroundSlam();
                groundSlam.upgrade();
                this.cardsToPreview = groundSlam;
                rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            } else if (c.cardID.equals(Roll.ID)) {
                this.cardsToPreview = new Uppercut();
                rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1];
            } else {
                this.cardsToPreview = null;
                rawDescription = cardStrings.DESCRIPTION;
            }
        } else {
            this.cardsToPreview = null;
            rawDescription = cardStrings.DESCRIPTION;
        }
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(AbstractDungeon.getMonsters().getMonster(m.id), new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        boolean h = AbstractDungeon.actionManager.cardsPlayedThisTurn.size() >= 2;
        if (h) {
            int c = AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 2;
            if ((AbstractDungeon.actionManager.cardsPlayedThisTurn.get(c)).cardID.equals(Jump.ID)) {
                addToTop(new NewQueueCardAction(new GroundSlam(), m, true, true));
            } else if ((AbstractDungeon.actionManager.cardsPlayedThisTurn.get(c)).cardID.equals(Uppercut.ID)) {
                GroundSlam groundSlam = new GroundSlam();
                groundSlam.upgrade();
                addToTop(new NewQueueCardAction(groundSlam, m, true, true));
            } else if ((AbstractDungeon.actionManager.cardsPlayedThisTurn.get(c)).cardID.equals(Roll.ID)) {
                addToTop(new NewQueueCardAction(new GroundSlam(), m, true, true));
            }
        }
    }

    public AbstractCard makeCopy() {
        return new Punch();
    }
}
