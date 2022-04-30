package emu.grasscutter.data;

import emu.grasscutter.Grasscutter;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LanguageResourceDict {

    private static ResourceBundle bundle = ResourceBundle.getBundle("Language", getLocatByConfig());

    public static final String USAGE = "USAGE";
    public static final String ALIAS = "ALIAS";

    public static final String RUN_IN_GAME = "RUN_IN_GAME";
    public static final String DO_NOT_PERMMISSION = "DO_NOT_PERMMISSION";

    public static final String ACCOUNT_DESC = "ACCOUNT_DESC";
    public static final String HEAL_DESC = "HEAL_DESC";


    /**
     * Read Resource Properties File Context
     * @param key Properties File Key
     * @return
     */
    public static String getText(String key){
        try {
            String tempHelp = bundle.getString(key);
            if(tempHelp != null) return tempHelp;
        }catch (MissingResourceException me){
            return key;
        }
        return key;
    }

    /**
     * get Config Language Type
     * @return Config LocatInfo
     */
    public static Locale getLocatByConfig(){
        return new Locale(Grasscutter.getConfig().LANGUAGE_LOCAT);
    }


}
