package teneocto.thiemjason.tlu_connect.utils;

public class AppConst {
    public static String NOTIFICATION_CHANNEL_ID = "channel";
    public static String NOTIFICATION_TOPIC = "messaging";

    // Shared Preferences keys
    public static String SHARED_PREFER_CONTAINER = "TluConnect sharedPrefer container";
    public static int SP_CURRENT_USER_ID = 1;
    public static String SP_UPDATED = "Is app up to date";

    // TAG
    public static String TAG_Launcher = "==> Launcher";
    public static String TAG_RegisterProfileViewModel = "==> RegisterProfileViewModel";
    public static String TAG_RegisterSocialNetworkViewModel = "==> Register SocialNetwork view model";
    public static String TAG_Profile_Social_NW = "==>  Profile social network";

    // Regex
    public static String REGEX_Email = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    public static String REGEX_Facebook_URL = "(?:https?:)?\\/\\/(?:www\\.)?(?:facebook|fb)\\.com\\/(?<profile>(?![A-z]+\\.php)(?!marketplace|gaming|watch|me|messages|help|search|groups)[A-z0-9_\\-\\.]+)\\/?";
    public static String REGEX_Instagram_URL = "https?:\\/\\/(www\\.)?instagram\\.com\\/([A-Za-z0-9_](?:(?:[A-Za-z0-9_]|(?:\\.(?!\\.))){0,28}(?:[A-Za-z0-9_]))?)";
    public static String REGEX_LinkedIn_URL = "((http|https)://)?(www[.])?(linkedin)?(linkedin)?.com/in/.[A-z0-9_-]*";
    public static String REGEX_Snapchat_URL = "(?:https?:)?\\/\\/(?:www\\.)?snapchat\\.com\\/add\\/(?<username>[A-z0-9\\.\\_\\-]+)\\/?";
    public static String REGEX_Twitter_URL = "((https?://)?(www\\.)?twitter\\.com/)?(@|#!/)?([A-Za-z0-9_]{1,15})(/([-a-z]{1,20}))?";
}
