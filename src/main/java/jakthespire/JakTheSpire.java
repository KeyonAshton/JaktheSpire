package jakthespire;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import jakthespire.cards.BaseCard;
import jakthespire.character.TheSidekick;
import jakthespire.relics.BaseRelic;
import jakthespire.util.GeneralUtils;
import jakthespire.util.KeywordInfo;
import jakthespire.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@SpireInitializer
public class JakTheSpire implements
        EditCharactersSubscriber,
        EditRelicsSubscriber,
        EditCardsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber {
    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this
    static { loadModInfo(); }
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.
    private static final String resourcesFolder = "jakthespire";

    //This is used to prefix the IDs of various objects like cards and relics,
    //to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new JakTheSpire();

        BaseMod.addColor(TheSidekick.Enums.CARD_COLOR, marColor, MAR_BG_ATTACK, MAR_BG_SKILL, MAR_BG_POWER, MAR_ENERGY_ORB,
                MAR_BG_ATTACK_P, MAR_BG_SKILL_P, MAR_BG_POWER_P, MAR_ENERGY_ORB_P,
                MAR_SMALL_ORB);
    }

    public JakTheSpire() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
    }

    @Override
    public void receivePostInitialize() {
        //This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TextureLoader.getTexture(resourcePath("logo.png"));
        //Set up the mod information displayed in the in-game mods menu.
        //The information used is taken from your pom.xml file.
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, null);
    }

    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString()
    {
        return Settings.language.name().toLowerCase();
    }
    private static final String defaultLanguage = "eng";
    private static final String MAR_BG_ATTACK = characterPath("thesidekick/cardback/bg_attack.png");
    private static final String MAR_BG_ATTACK_P = characterPath("thesidekick/cardback/bg_attack_p.png");
    private static final String MAR_BG_SKILL = characterPath("thesidekick/cardback/bg_skill.png");
    private static final String MAR_BG_SKILL_P = characterPath("thesidekick/cardback/bg_skill_p.png");
    private static final String MAR_BG_POWER = characterPath("thesidekick/cardback/bg_power.png");
    private static final String MAR_BG_POWER_P = characterPath("thesidekick/cardback/bg_power_p.png");
    private static final String MAR_ENERGY_ORB = characterPath("thesidekick/cardback/energy_orb.png");
    private static final String MAR_ENERGY_ORB_P = characterPath("thesidekick/cardback/energy_orb_p.png");
    private static final String MAR_SMALL_ORB = characterPath("thesidekick/cardback/small_orb.png");
    private static final String MAR_CHAR_SELECT_BUTTON = characterPath("thesidekick/select/button.png");
    private static final String MAR_CHAR_SELECT_PORTRAIT = characterPath("thesidekick/select/portrait.png");
    public static final Color marColor = new Color(137f/255f, 85f/255f, 48f/255f, 1f);
//    private static final String OTTSEL_BG_ATTACK = characterPath("orangelightning/cardback/bg_attack.png");
//   private static final String OTTSEL_BG_ATTACK_P = characterPath("orangelightning/cardback/bg_attack_p.png");
//    private static final String OTTSEL_BG_SKILL = characterPath("orangelightning/cardback/bg_skill.png");
//    private static final String OTTSEL_BG_SKILL_P = characterPath("orangelightning/cardback/bg_skill_p.png");
//    private static final String OTTSEL_BG_POWER = characterPath("orangelightning/cardback/bg_power.png");
//    private static final String OTTSEL_BG_POWER_P = characterPath("orangelightning/cardback/bg_power_p.png");
//    private static final String OTTSEL_ENERGY_ORB = characterPath("orangelightning/cardback/energy_orb.png");
//    private static final String OTTSEL_ENERGY_ORB_P = characterPath("orangelightning/cardback/energy_orb_p.png");
//    private static final String OTTSEL_SMALL_ORB = characterPath("orangelightning/cardback/small_orb.png");
//    private static final String OTTSEL_CHAR_SELECT_BUTTON = characterPath("orangelightning/select/button.png");
//    private static final String OTTSEL_CHAR_SELECT_PORTRAIT = characterPath("orangelightning/select/portrait.png");
//    private static final Color ottselColor = new Color(255f/255f, 156f/255f, 1f/255f, 1f);

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            }
            catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
    }

    @Override
    public void receiveEditKeywords()
    {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try
            {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    registerKeyword(keyword);
                }
            }
            catch (Exception e)
            {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String resourcePath(String file) {
        return resourcesFolder + "/" + file;
    }
    public static String characterPath(String file) {
        return resourcesFolder + "/character/" + file;
    }
    public static String powerPath(String file) {
        return resourcesFolder + "/powers/" + file;
    }
    public static String relicPath(String file) {
        return resourcesFolder + "/relics/" + file;
    }


    //This determines the mod's ID based on information stored by ModTheSpire.
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(JakTheSpire.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheSidekick(), MAR_CHAR_SELECT_BUTTON, MAR_CHAR_SELECT_PORTRAIT, TheSidekick.Enums.THE_SIDEKICK);
//        BaseMod.addCharacter(new OrangeLightning(), OTTSEL_CHAR_SELECT_BUTTON, OTTSEL_CHAR_SELECT_PORTRAIT, TheSidekick.Enums.ORANGE_LIGHTNING);
    }

    @Override
    public void receiveEditRelics() { //somewhere in the class
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseRelic.class) //In the same package as this class
                .any(BaseRelic.class, (info, relic) -> { //Run this code for any classes that extend this class
                    if (relic.pool != null)
                        BaseMod.addRelicToCustomPool(relic, relic.pool); //Register a custom character specific relic
                    else
                        BaseMod.addRelic(relic, relic.relicType); //Register a shared or base game character specific relic

                    //If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
                    //If you want all your relics to be visible by default, just remove this if statement.
                    if (info.seen)
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                });
    }

    @Override
    public void receiveEditCards() { //somewhere in the class
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseCard.class) //In the same package as this class
                .setDefaultSeen(true) //And marks them as seen in the compendium
                .cards(); //Adds the cards
    }
}
