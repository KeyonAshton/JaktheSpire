package jakanddaxter.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import jakanddaxter.cards.eco.DarkEcoCard;
import jakanddaxter.cards.eco.GreenEcoCard;
import jakanddaxter.cards.eco.LightEcoCard;
import jakanddaxter.character.TheSidekick;
import jakanddaxter.powers.eco.DarkEcoPower;
import jakanddaxter.powers.eco.vent.GreenVentPower;
import jakanddaxter.powers.eco.LightEcoPower;

import java.util.ArrayList;

import static jakanddaxter.JakandDaxter.*;

public class SidekickRelic extends BaseRelic implements CustomSavable<int[]> {
    private static final String NAME = "GreenEco"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    private static final int GREENECOCOUNT = 50; //Max Green Eco stacks.
    private static final int ECOCOUNT = 50; //Max Light and Dark Eco stacks.
    private static final int DMG = 16; //The fraction of health green eco will heal.
    private static final int DMGLOSS = (DMG - 1) / DMG; //Fraction needed for maf's.
    private int greenEcoAmount = 0; //The amount of green eco.
    private int lightEcoAmount = 0; //The amount of light eco.
    private int darkEcoAmount = 0; //The amount of dark eco.

    public SidekickRelic() {
        super(ID, NAME, TheSidekick.Enums.CARD_COLOR, RARITY, SOUND);
    }

    int[] savedstats = new int[3];

    @Override
    public int[] onSave()
    {
        savedstats[0] = greenEcoAmount;
        savedstats[1] = lightEcoAmount;
        savedstats[2] = darkEcoAmount;
        return savedstats;
        // Return the location of the card in your deck. AbstractCard cannot be serialized so we use an Integer instead.
    }

    @Override
    public void onLoad(int[] cardIndex)
    {
        greenEcoAmount = savedstats[0];
        lightEcoAmount = savedstats[1];
        darkEcoAmount = savedstats[2];
    }

    //Method to add to the counter and check player health.
    public void GreenEcoCount(int count) {
        if (greenEcoAmount + count >= GREENECOCOUNT && AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth > 2/3) {
            if (greenEcoAmount != GREENECOCOUNT){
                greenEcoAmount = GREENECOCOUNT;
            }
            logger.info("Green Eco maxed out");
            return;
        }
        if (greenEcoAmount <= -1) {
            greenEcoAmount += 2;
        } else {
            logger.info("Green Eco Increased");
            if (greenEcoAmount + count >= GREENECOCOUNT ){
                logger.info("Green Eco maxed out");
                greenEcoAmount = GREENECOCOUNT;
                GreenEcoHeal();
            } else {
                logger.info("Count added to");
                greenEcoAmount = greenEcoAmount + count;
            }
        }
    }

    public void LightEcoCount(int count) {
        if (lightEcoAmount + count >= ECOCOUNT) {
            lightEcoAmount = ECOCOUNT;
        } else if (lightEcoAmount + count <= 0) {
            lightEcoAmount = 0;
        } else {
            lightEcoAmount = lightEcoAmount + count;
        }
    }

    public void DarkEcoCount(int count) {
        if (darkEcoAmount + count >= ECOCOUNT) {
            darkEcoAmount = ECOCOUNT;
        } else if (darkEcoAmount + count <= 0) {
            darkEcoAmount = 0;
        } else {
            darkEcoAmount = darkEcoAmount + count;
        }
    }

    //Method to heal player if count is high enough.
    public void GreenEcoHeal() {
        if (AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth > DMGLOSS) {
            logger.info("Health above " + (DMG-1) + "/" +DMG);
        } else  if (AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth <= DMGLOSS){
            logger.info("Health at or below " + (DMG-1) + "/" +DMG);
            greenEcoAmount = greenEcoAmount - GREENECOCOUNT;
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            int healAmt = AbstractDungeon.player.maxHealth / DMG;
            logger.info("Healing " + healAmt);
            AbstractDungeon.player.heal(healAmt, true);
            flash();
        }
    }

    @Override
    protected void initializeTips() {
        super.initializeTips();
        tips.add(new PowerTip(BaseMod.getKeywordTitle("jakanddaxter:Green Eco"), BaseMod.getKeywordDescription("jakanddaxter:Green Eco")));
    }

    @Override
    public void onEquip(){
        greenEcoAmount = 0;
        lightEcoAmount = 0;
        darkEcoAmount = 0;
    }

    @Override
    public void onLoseHp(int damageAmount) {
        if (greenEcoAmount >= GREENECOCOUNT) {
            GreenEcoHeal();
        }
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard.cardID.equals(GreenEcoCard.ID)) {
            GreenEcoCount(targetCard.magicNumber);
        }
    }

    public void atBattleStart() {
        super.atBattleStart();
        ArrayList<AbstractCard> derp = new ArrayList();
        derp.add(new DarkEcoCard());
        derp.add(new LightEcoCard());
        addToBot(new ChooseOneAction(derp));
        logger.info("Dark Eco: " + darkEcoAmount + " | Light Eco: " + lightEcoAmount);
    }

    public void onVictory(){
        super.onVictory();
        if (AbstractDungeon.player.hasPower(LightEcoPower.POWER_ID)) {
            LightEcoCount(1);
        } else if (AbstractDungeon.player.hasPower(DarkEcoPower.POWER_ID)) {
            DarkEcoCount(1);
        }
    }

    public void onEnterRoom(AbstractRoom room) {

    }

    @Override
    public void atTurnStartPostDraw() {
        if (AbstractDungeon.player.hasPower(GreenVentPower.POWER_ID)) {
            this.GreenEcoCount(2);
            flash();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], GREENECOCOUNT);
    }

}
