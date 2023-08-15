package jakthespire.relics;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import jakthespire.character.TheSidekick;

import static jakthespire.JakTheSpire.logger;
import static jakthespire.JakTheSpire.makeID;
import static jakthespire.helpers.CustomTags.TAG_GREEN_ECO;

public class GreenEco extends BaseRelic{
    private static final String NAME = "GreenEco"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    private static final int ORBS = 50; //The amount of green eco needed to heal.
    private static final int DMG = 16; //The fraction of health green eco will heal.
    private static final int DMGLOSS = (DMG - 1) / DMG; //Fraction needed for math's.

    public GreenEco() {
        super(ID, NAME, TheSidekick.Enums.CARD_COLOR, RARITY, SOUND);
    }

    //Method to add to the counter and check player health.
    public void GreenEcoCount(int count) {
        if (this.counter + count >= ORBS && AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth > 2/3) {
            if (this.counter != ORBS){
                this.counter = ORBS;
            }
            logger.info("Count maxed out");
            return;
        }
        if (this.counter <= -1) {
            this.counter += 2;
        } else {
            logger.info("Eco Increased");
            if (this.counter + count >= ORBS ){
                    logger.info("Count maxed out");
                    this.counter = ORBS;
                    GreenEcoHeal();
            } else {
                logger.info("Count added to");
                this.counter = this.counter + count;
            }
        }
    }

    //Method to heal player if count is high enough.
    public void GreenEcoHeal() {
        if (AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth > DMGLOSS) {
            logger.info("Health above " + (DMG-1) + "/" +DMG);
        } else  if (AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth <= DMGLOSS){
            logger.info("Health at or below " + (DMG-1) + "/" +DMG);
            this.counter = this.counter - ORBS;
            addToTop((AbstractGameAction) new RelicAboveCreatureAction((AbstractCreature) AbstractDungeon.player, this));
            int healAmt = AbstractDungeon.player.maxHealth / DMG;
            logger.info("Healing " + healAmt);
            AbstractDungeon.player.heal(healAmt, true);
            flash();
        }
    }

    @Override
    protected void initializeTips() {
        super.initializeTips();
        tips.add(new PowerTip(BaseMod.getKeywordTitle("jakthespire:Green Eco"), BaseMod.getKeywordDescription("jakthespire:Green Eco")));
    }

    @Override
    public void onEquip(){
        this.counter = 0;
    }

    @Override
    public void onLoseHp(int damageAmount) {
        if (this.counter >= ORBS) {
            GreenEcoHeal();
        }
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard.hasTag(TAG_GREEN_ECO)) {
            GreenEcoCount(targetCard.magicNumber);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], ORBS);
    }

}
