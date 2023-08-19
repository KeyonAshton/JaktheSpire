package jakanddaxter.powers.morphgun;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import jakanddaxter.powers.morphgun.ammo.BlueAmmoPower;
import jakanddaxter.powers.morphgun.ammo.PurpleAmmoPower;
import jakanddaxter.powers.morphgun.ammo.RedAmmoPower;
import jakanddaxter.powers.morphgun.ammo.YellowAmmoPower;
import jakanddaxter.powers.morphgun.mode.*;

public class MorphGunTest {

    public String getMorphGunAmmo() {
        String f;
        if (AbstractDungeon.player.hasPower(RedAmmoPower.POWER_ID) && AbstractDungeon.player.getPower(RedAmmoPower.POWER_ID).amount > 0) {
            f = "RedAmmo";
            return f;
        } else if (AbstractDungeon.player.hasPower(YellowAmmoPower.POWER_ID) && AbstractDungeon.player.getPower(YellowAmmoPower.POWER_ID).amount > 0) {
            return "YellowAmmo";
        } else if (AbstractDungeon.player.hasPower(BlueAmmoPower.POWER_ID) && AbstractDungeon.player.getPower(BlueAmmoPower.POWER_ID).amount > 0) {
            return "BlueAmmo";
        } else if (AbstractDungeon.player.hasPower(PurpleAmmoPower.POWER_ID) && AbstractDungeon.player.getPower(PurpleAmmoPower.POWER_ID).amount > 0) {
            return "PurpleAmmo";
        } else {
            return "N/A";
        }
    }

    public String getMorphGunMode() {
        String f;
        AbstractPlayer d = AbstractDungeon.player;
        if (d.hasPower(ScatterGun.POWER_ID)) {
            f = "ScatterGun";
            return f;
        } else if (d.hasPower(WaveConcussor.POWER_ID)) {
            f = "WaveConcussor";
            return f;
        } else if (d.hasPower(Blaster.POWER_ID)) {
            f = "Blaster";
            return f;
        } else if (d.hasPower(BeamReflexor.POWER_ID)) {
            f = "BeamReflexor";
            return f;
        } else if (d.hasPower(VulcanFury.POWER_ID)) {
            f = "VulcanFury";
            return f;
        } else if (d.hasPower(ArcWielder.POWER_ID)) {
            f = "ArcWielder";
            return f;
        } else if (d.hasPower(PeaceMaker.POWER_ID)) {
            f = "PeaceMaker";
            return f;
        } else if (d.hasPower(SuperNova.POWER_ID)) {
            f = "SuperNova";
            return f;
        } else {
            return "N/A";
        }
    }

    public String getMorphGunModeAndAmmo() {
        String c = this.getMorphGunAmmo();
        String d = this.getMorphGunMode();
        switch (c) {
            case "RedAmmo":
                if (d.equals("ScatterGun")) {
                    return d;
                } else if (d.equals("WaveConcussor")) {
                    return d;
                } else {
                    return "N/A";
                }
            case "YellowAmmo":
                if (d.equals("Blaster")) {
                    return d;
                } else if (d.equals("BeamReflexor")) {
                    return d;
                } else {
                    return "N/A";
                }
            case "BlueAmmo":
                if (d.equals("VulcanFury")) {
                    return d;
                } else if (d.equals("ArcWielder")) {
                    return d;
                } else {
                    return "N/A";
                }
            case "PurpleAmmo":
                if (d.equals("PeaceMaker")) {
                    return d;
                } else if (d.equals("SuperNova")) {
                    return d;
                } else {
                    return "N/A";
                }
            default:
                return "N/A";
        }
    }
}
