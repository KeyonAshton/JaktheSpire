package jakanddaxter.cards.defense;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jakanddaxter.cards.BaseCard;
import jakanddaxter.cards.attacks.Punch;
import jakanddaxter.cards.attacks.Uppercut;
import jakanddaxter.character.TheSidekick;
import jakanddaxter.util.CardStats;


public class Jump extends BaseCard {

    public static final String ID = makeID(Jump.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheSidekick.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.BASIC, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Jump() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.baseBlock = 4;
        this.baseDamage = 1;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(2);
            upgradeMagicNumber(1);
        }
    }

    public void triggerWhenDrawn() {
        boolean h = !AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty();
        if (h) {
            String c = (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1)).cardID;
            if (c.equals(Jump.ID)) {
                name = cardStrings.EXTENDED_DESCRIPTION[0];
                rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1];
                this.cardsToPreview = null;
            } else if (c.equals(Roll.ID)) {
                name = cardStrings.EXTENDED_DESCRIPTION[2];
                rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[3];
                this.cardsToPreview = null;
            } else if (c.equals(Punch.ID)) {
                name = cardStrings.NAME;
                rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[4];
                this.cardsToPreview = new Uppercut();
            } else {
                name = cardStrings.NAME;
                rawDescription = cardStrings.DESCRIPTION;
                this.cardsToPreview = null;
            }
        } else {
            name = cardStrings.NAME;
            rawDescription = cardStrings.DESCRIPTION;
            this.cardsToPreview = null;
        }
        initializeDescription();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        boolean h = !AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty();
        if (h) {
            if (c.cardID.equals(Jump.ID)) {
                name = cardStrings.EXTENDED_DESCRIPTION[0];
                rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1];
            } else if (c.cardID.equals(Roll.ID)) {
                name = cardStrings.EXTENDED_DESCRIPTION[2];
                rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[3];
            } else if (c.cardID.equals(Punch.ID)) {
                name = cardStrings.NAME;
                rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[4];
                this.cardsToPreview = new Uppercut();
            } else {
                this.cardsToPreview = null;
                name = cardStrings.NAME;
                rawDescription = cardStrings.DESCRIPTION;
            }
        } else {
            this.cardsToPreview = null;
            name = cardStrings.NAME;
            rawDescription = cardStrings.DESCRIPTION;
        }
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new GainBlockAction(p, p, this.block));
        boolean h = AbstractDungeon.actionManager.cardsPlayedThisTurn.size() >= 2;
        if (h) {
            int c = AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 2;

            if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(c).cardID.equals(Jump.ID)) {
                if ((AbstractDungeon.actionManager.cardsPlayedThisTurn.get(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 2)).upgraded) {
                    this.magicNumber++;
                }
                addToTop(new GainBlockAction(p, p, this.magicNumber));
            } else if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(c).cardID.equals(Roll.ID)) {
                addToTop(new DamageRandomEnemyAction(new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            } else if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(c).cardID.equals(Punch.ID)) {
                Uppercut upperCut = new Uppercut();
                addToTop(new NewQueueCardAction(upperCut, AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), true, true));
            }
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.damage = this.baseDamage;
    }

}
