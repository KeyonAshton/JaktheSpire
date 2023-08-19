package jakanddaxter.cards.MorphGun;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import jakanddaxter.cards.BaseCard;
import jakanddaxter.cards.MorphGun.Modes.BlueMorphMode;
import jakanddaxter.cards.MorphGun.Modes.PurpleMorphMode;
import jakanddaxter.cards.MorphGun.Modes.RedMorphMode;
import jakanddaxter.cards.MorphGun.Modes.YellowMorphMode;
import jakanddaxter.cards.attacks.Punch;
import jakanddaxter.cards.attacks.SpinKick;
import jakanddaxter.cards.defense.Jump;
import jakanddaxter.character.TheSidekick;
import jakanddaxter.powers.morphgun.MorphGunTest;
import jakanddaxter.powers.morphgun.ammo.BlueAmmoPower;
import jakanddaxter.powers.morphgun.ammo.PurpleAmmoPower;
import jakanddaxter.powers.morphgun.ammo.RedAmmoPower;
import jakanddaxter.powers.morphgun.ammo.YellowAmmoPower;
import jakanddaxter.util.CardStats;

import static jakanddaxter.helpers.CustomTags.TAG_AMMO;
import static jakanddaxter.helpers.CustomTags.TAG_MODE;

public class Fire extends BaseCard {

    public static final String ID = makeID(Fire.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheSidekick.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Fire() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.baseDamage = 3;
        this.baseMagicNumber = 1;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    public void triggerWhenDrawn() {
        int d = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        String f = new MorphGunTest().getMorphGunModeAndAmmo();
        switch (f) {
            case "ScatterGun":
                this.target = CardTarget.ALL_ENEMY;
                this.cardsToPreview = new RedMorphMode();
                break;
            case "WaveConcussor":
                this.target = CardTarget.ALL_ENEMY;
                RedMorphMode redGun = new RedMorphMode();
                redGun.upgrade();
                this.cardsToPreview = redGun;
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
                break;
            case "Blaster":
                if (d >= 2 && AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 1).cardID.equals(SpinKick.ID) && AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 2).cardID.equals(Jump.ID)) {
                    WastelanderSpin spin = new WastelanderSpin(this.damage);
                    if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 1).upgraded) {
                        spin.upgrade();
                    }
                    if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 2).upgraded) {
                        spin.upgrade();
                    }
                    if (this.upgraded) {
                        spin.upgrade();
                    }
                    this.cardsToPreview = spin;
                    this.target = CardTarget.ENEMY;
                    rawDescription = cardStrings.EXTENDED_DESCRIPTION[1] + cardStrings.EXTENDED_DESCRIPTION[7];
                } else if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 1).cardID.equals(Punch.ID)) {
                    WastelanderPunch punch = new WastelanderPunch(this.damage);
                    if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 1).upgraded) {
                        punch.upgrade();
                    }
                    if (this.upgraded) {
                        punch.upgrade();
                    }
                    this.cardsToPreview = punch;
                    this.target = CardTarget.ENEMY;
                    rawDescription = cardStrings.EXTENDED_DESCRIPTION[1] + cardStrings.EXTENDED_DESCRIPTION[6];
                } else {
                    this.target = CardTarget.ENEMY;
                    this.cardsToPreview = new YellowMorphMode();
                    rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
                }
                break;
            case "BeamReflexor":
                if (d >= 2 && AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 1).cardID.equals(SpinKick.ID) && AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 2).cardID.equals(Jump.ID)) {
                    WastelanderSpin spin = new WastelanderSpin(this.damage);
                    if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 1).upgraded) {
                        spin.upgrade();
                    }
                    if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 2).upgraded) {
                        spin.upgrade();
                    }
                    if (this.upgraded) {
                        spin.upgrade();
                    }
                    this.cardsToPreview = spin;
                    this.target = CardTarget.ENEMY;
                    rawDescription = cardStrings.EXTENDED_DESCRIPTION[2] + cardStrings.EXTENDED_DESCRIPTION[7];
                } else if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 1).cardID.equals(Punch.ID)) {
                    WastelanderPunch punch = new WastelanderPunch(this.damage);
                    if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 1).upgraded) {
                        punch.upgrade();
                    }
                    if (this.upgraded) {
                        punch.upgrade();
                    }
                    this.cardsToPreview = punch;
                    this.target = CardTarget.ENEMY;
                    rawDescription = cardStrings.EXTENDED_DESCRIPTION[2] + cardStrings.EXTENDED_DESCRIPTION[6];
                } else {
                    this.target = CardTarget.ENEMY;
                    this.cardsToPreview = new YellowMorphMode();
                    rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
                }
                break;
            case "VulcanFury":
                this.target = CardTarget.ALL_ENEMY;
                this.cardsToPreview = new BlueMorphMode();
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
                break;
            case "ArcWielder":
                this.target = CardTarget.ALL_ENEMY;
                BlueMorphMode blueGun = new BlueMorphMode();
                blueGun.upgrade();
                this.cardsToPreview = blueGun;
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[4];
                break;
            case "PeaceMaker":
                this.target = CardTarget.ENEMY;
                this.cardsToPreview = new PurpleMorphMode();
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[5];
                break;
            case "SuperNova":
                this.target = CardTarget.ALL_ENEMY;
                PurpleMorphMode purpleGun = new PurpleMorphMode();
                purpleGun.upgrade();
                this.cardsToPreview = purpleGun;
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
                break;
            default:
                rawDescription = cardStrings.DESCRIPTION;
                break;
        }
        initializeDescription();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.hasTag(TAG_AMMO) || c.hasTag(TAG_MODE)) {
            int d = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
            String f = new MorphGunTest().getMorphGunModeAndAmmo();
            switch (f) {
                case "ScatterGun":
                    this.target = CardTarget.ALL_ENEMY;
                    this.cardsToPreview = new RedMorphMode();
                    break;
                case "WaveConcussor":
                    this.target = CardTarget.ALL_ENEMY;
                    RedMorphMode redGun = new RedMorphMode();
                    redGun.upgrade();
                    this.cardsToPreview = redGun;
                    rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
                    break;
                case "Blaster":
                    if (d >= 2 && c.cardID.equals(SpinKick.ID) && AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 2).cardID.equals(Jump.ID)) {
                        WastelanderSpin spin = new WastelanderSpin(this.damage/2);
                        if (c.upgraded) {
                            spin.upgrade();
                        }
                        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 2).upgraded) {
                            spin.upgrade();
                        }
                        this.cardsToPreview = spin;
                        this.target = CardTarget.ENEMY;
                        rawDescription = cardStrings.EXTENDED_DESCRIPTION[1] + cardStrings.EXTENDED_DESCRIPTION[7];
                    } else if (c.cardID.equals(Punch.ID)) {
                        WastelanderPunch punch = new WastelanderPunch(this.damage);
                        if (c.upgraded) {
                            punch.upgrade();
                        }
                        if (this.upgraded) {
                            punch.upgrade();
                        }
                        this.cardsToPreview = punch;
                        this.target = CardTarget.ENEMY;
                        rawDescription = cardStrings.EXTENDED_DESCRIPTION[1] + cardStrings.EXTENDED_DESCRIPTION[6];
                    } else {
                        this.target = CardTarget.ENEMY;
                        this.cardsToPreview = new YellowMorphMode();
                        rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
                    }
                    break;
                case "BeamReflexor":
                    if (d >= 2 && c.cardID.equals(SpinKick.ID) && AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 2).cardID.equals(Jump.ID)) {
                        WastelanderSpin spin = new WastelanderSpin(this.damage/2);
                        if (c.upgraded) {
                            spin.upgrade();
                        }
                        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 2).upgraded) {
                            spin.upgrade();
                        }
                        this.cardsToPreview = spin;
                        this.target = CardTarget.ENEMY;
                        rawDescription = cardStrings.EXTENDED_DESCRIPTION[2] + cardStrings.EXTENDED_DESCRIPTION[7];
                    } else if (c.cardID.equals(Punch.ID)) {
                        WastelanderPunch punch = new WastelanderPunch(this.damage);
                        if (c.upgraded) {
                            punch.upgrade();
                        }
                        if (this.upgraded) {
                            punch.upgrade();
                        }
                        this.cardsToPreview = punch;
                        this.target = CardTarget.ENEMY;
                        rawDescription = cardStrings.EXTENDED_DESCRIPTION[2] + cardStrings.EXTENDED_DESCRIPTION[6];
                    } else {
                        this.target = CardTarget.ENEMY;
                        this.cardsToPreview = new YellowMorphMode();
                        rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
                    }
                    break;
                case "VulcanFury":
                    this.target = CardTarget.ALL_ENEMY;
                    this.cardsToPreview = new BlueMorphMode();
                    rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
                    break;
                case "ArcWielder":
                    this.target = CardTarget.ALL_ENEMY;
                    BlueMorphMode blueGun = new BlueMorphMode();
                    blueGun.upgrade();
                    this.cardsToPreview = blueGun;
                    rawDescription = cardStrings.EXTENDED_DESCRIPTION[4];
                    break;
                case "PeaceMaker":
                    this.target = CardTarget.ENEMY;
                    this.cardsToPreview = new PurpleMorphMode();
                    rawDescription = cardStrings.EXTENDED_DESCRIPTION[5];
                    break;
                case "SuperNova":
                    this.target = CardTarget.ALL_ENEMY;
                    PurpleMorphMode purpleGun = new PurpleMorphMode();
                    purpleGun.upgrade();
                    this.cardsToPreview = purpleGun;
                    rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
                    break;
                default:
                    rawDescription = cardStrings.DESCRIPTION;
                    break;
            }
        } else {
            rawDescription = cardStrings.DESCRIPTION;
        }
        initializeDescription();
    }

    public boolean cardPlayable(AbstractMonster m) {
        if ((this.target != AbstractCard.CardTarget.ENEMY && this.target != AbstractCard.CardTarget.SELF_AND_ENEMY || m == null || !m.isDying) && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            String f = new MorphGunTest().getMorphGunModeAndAmmo();
            if (f.equals("N/A")) {
                this.cantUseMessage = null;
                return false;
            } else {
                return true;
            }
        } else {
            this.cantUseMessage = null;
            return false;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int d = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        switch (new MorphGunTest().getMorphGunModeAndAmmo()) {
            case "ScatterGun":
            case "WaveConcussor":
                addToBot(new DamageAllEnemiesAction(p, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new ReducePowerAction(p, p, RedAmmoPower.POWER_ID, 1));
                if (p.getPower(RedAmmoPower.POWER_ID).amount == 0) {
                    addToBot(new RemoveSpecificPowerAction(p, p, RedAmmoPower.POWER_ID));
                }
                break;
            case "Blaster":
                if (d >= 2 && AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 2).cardID.equals(SpinKick.ID) && AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 3).cardID.equals(Jump.ID)) {
                    WastelanderSpin spin = new WastelanderSpin(this.damage);
                    if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 2).upgraded) {
                        spin.upgrade();
                    }
                    if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 3).upgraded) {
                        spin.upgrade();
                    }
                    addToTop(new NewQueueCardAction(spin, m, true, true));
                } else if (d >= 2 && AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 2).cardID.equals(Punch.ID)) {
                    WastelanderPunch punch = new WastelanderPunch(this.damage);
                    if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 2).upgraded) {
                        punch.upgrade();
                    }
                    if (this.upgraded) {
                        punch.upgrade();
                    }
                    addToTop(new NewQueueCardAction(punch, m, true, true));
                }
                addToBot(new DamageAction(AbstractDungeon.getMonsters().getMonster(m.id), new DamageInfo(p, this.damage + 2, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new ReducePowerAction(p, p, YellowAmmoPower.POWER_ID, 1));
                if (p.getPower(YellowAmmoPower.POWER_ID).amount == 0) {
                    addToBot(new RemoveSpecificPowerAction(p, p, YellowAmmoPower.POWER_ID));
                }
                break;
            case "BeamReflexor":
                if (d >= 3 && AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 2).cardID.equals(SpinKick.ID) && AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 3).cardID.equals(Jump.ID)) {
                    WastelanderSpin spin = new WastelanderSpin(this.damage);
                    if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 2).upgraded) {
                        spin.upgrade();
                    }
                    if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 3).upgraded) {
                        spin.upgrade();
                    }
                    addToTop(new NewQueueCardAction(spin, m, true, true));
                } else if (d >= 2 && AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 2).cardID.equals(Punch.ID)) {
                    WastelanderPunch punch = new WastelanderPunch(this.damage);
                    if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(d - 2).upgraded) {
                        punch.upgrade();
                    }
                    addToTop(new NewQueueCardAction(punch, m, true, true));
                }
                addToBot(new DamageAction(AbstractDungeon.getMonsters().getMonster(m.id), new DamageInfo(p, this.damage + 2, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new DamageRandomEnemyAction(new DamageInfo(p, this.damage + 2, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new ReducePowerAction(p, p, YellowAmmoPower.POWER_ID, 1));
                if (p.getPower(YellowAmmoPower.POWER_ID).amount == 0) {
                    addToBot(new RemoveSpecificPowerAction(p, p, YellowAmmoPower.POWER_ID));
                }
                break;
            case "VulcanFury":
                addToBot(new DamageRandomEnemyAction(new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new DamageRandomEnemyAction(new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new DamageRandomEnemyAction(new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new ReducePowerAction(p, p, BlueAmmoPower.POWER_ID, 1));
                if (p.getPower(BlueAmmoPower.POWER_ID).amount == 0) {
                    addToBot(new RemoveSpecificPowerAction(p, p, BlueAmmoPower.POWER_ID));
                }
                break;
            case "ArcWielder":
                AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                AbstractMonster randomMonster1 = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                AbstractMonster randomMonster2 = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                addToBot(new DamageAction(randomMonster, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new ApplyPowerAction(randomMonster, p, new WeakPower(randomMonster, 1, true)));
                addToBot(new DamageAction(randomMonster1, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new ApplyPowerAction(randomMonster1, p, new WeakPower(randomMonster, 1, true)));
                addToBot(new DamageAction(randomMonster2, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new ApplyPowerAction(randomMonster2, p, new WeakPower(randomMonster, 1, true)));
                addToBot(new ReducePowerAction(p, p, BlueAmmoPower.POWER_ID, 1));
                if (p.getPower(BlueAmmoPower.POWER_ID).amount == 0) {
                    addToBot(new RemoveSpecificPowerAction(p, p, BlueAmmoPower.POWER_ID));
                }
                break;
            case "PeaceMaker":
                addToBot(new DamageAction(AbstractDungeon.getMonsters().getMonster(m.id), new DamageInfo(p, this.damage + 2, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new ReducePowerAction(p, p, PurpleAmmoPower.POWER_ID, 1));
                if (p.getPower(PurpleAmmoPower.POWER_ID).amount == 0) {
                    addToBot(new RemoveSpecificPowerAction(p, p, PurpleAmmoPower.POWER_ID));
                }
                break;
            case "SuperNova":
                addToBot(new DamageAllEnemiesAction(p, this.damage * 5, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new ReducePowerAction(p, p, PurpleAmmoPower.POWER_ID, 1));
                if (p.getPower(PurpleAmmoPower.POWER_ID).amount == 0) {
                    addToBot(new RemoveSpecificPowerAction(p, p, PurpleAmmoPower.POWER_ID));
                }
                break;
        }
    }
}
