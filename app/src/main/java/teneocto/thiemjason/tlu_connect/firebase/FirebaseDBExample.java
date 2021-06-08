package teneocto.thiemjason.tlu_connect.firebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.models.NotificationDTO;
import teneocto.thiemjason.tlu_connect.models.ScannedDTO;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.models.SocialNetworkDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.Utils;

/**
 * Handler FIREBASE DATA SEEDER
 */
public class FirebaseDBExample {
    Context context;

    public FirebaseDBExample(Context context) {
        this.context = context;
    }

    /**
     * Data seeder
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void FirebaseDataSeeder() {
        NOTIFICATION_initFirebaseDB();
        SCANNING_HISTORY_initFirebaseDB();
        USER_initFirebaseDB();
        SOCIAL_NETWORK_initFirebaseDB();
    }

    /**
     * Seeder USER_data
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void USER_initFirebaseDB() {
        FirebaseDBHelper firebaseDBHelper = new FirebaseDBHelper();
        Bitmap imageBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.blank);
        byte[] imageBase64 = Utils.getBitmapAsByteArray(imageBitmap);
        UserDTO userDTO = new UserDTO(
                Utils.getUserUUID(context),
                "Thuy Linh",
                "Vu Thi",
                "vuthithuylinh@gmail.com",
                "Designer",
                "Teneocto Technologies",
                Base64.getEncoder().encodeToString(imageBase64)
        );
        firebaseDBHelper.USER_Insert(userDTO);
    }

    /**
     * Seeder Notification data
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void NOTIFICATION_initFirebaseDB() {
        NotificationDTO notificationDTO = new NotificationDTO(
                Utils.getRandomUUID(),
                Utils.getUserUUID(context),
                "Cập nhật ứng dụng lên phiên bản 1.0.1, chính sửa các lỗi hiện đang hiện hữu tại đăng nhập bằng Facebook",
                "Cập nhật phiên bản mới",
                Base64.getEncoder().encodeToString(Utils.getBitmapAsByteArray(((BitmapDrawable) context.getResources().getDrawable(R.drawable.blank)).getBitmap()))
        );
        FirebaseDBHelper firebaseDBHelper = new FirebaseDBHelper();
        firebaseDBHelper.Notification_Insert(notificationDTO);
    }

    /**
     * Seeder Social Network data
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SOCIAL_NETWORK_initFirebaseDB() {
        FirebaseDBHelper firebaseDBHelper = new FirebaseDBHelper();
        byte[] facebookImageBase64 = Utils.getBitmapAsByteArray(((BitmapDrawable) context.getResources().getDrawable(R.drawable.facebook)).getBitmap());
        SocialNetworkDTO facebookSocialNetworkDTO = new SocialNetworkDTO(
                Utils.getRandomUUID(),
                "Facebook",
                Base64.getEncoder().encodeToString(facebookImageBase64)

        );

        SocialNetworkDTO insta = new SocialNetworkDTO(
                Utils.getRandomUUID(),
                "Instagram",
                Base64.getEncoder().encodeToString(Utils.getBitmapAsByteArray(((BitmapDrawable) context.getResources().getDrawable(R.drawable.instagram)).getBitmap()))

        );

        SocialNetworkDTO linked = new SocialNetworkDTO(
                Utils.getRandomUUID(),
                "LinkedIn",
                Base64.getEncoder().encodeToString(Utils.getBitmapAsByteArray(((BitmapDrawable) context.getResources().getDrawable(R.drawable.linkedin)).getBitmap()))

        );

        SocialNetworkDTO snaochat = new SocialNetworkDTO(
                Utils.getRandomUUID(),
                "Snapchat",
                Base64.getEncoder().encodeToString(Utils.getBitmapAsByteArray(((BitmapDrawable) context.getResources().getDrawable(R.drawable.sapchat)).getBitmap()))

        );

        SocialNetworkDTO twitter = new SocialNetworkDTO(
                Utils.getRandomUUID(),
                "Twitter",
                Base64.getEncoder().encodeToString(Utils.getBitmapAsByteArray(((BitmapDrawable) context.getResources().getDrawable(R.drawable.twiiter)).getBitmap()))

        );

        SocialNetworkDTO logo = new SocialNetworkDTO(
                Utils.getRandomUUID(),
                "Logo",
                Base64.getEncoder().encodeToString(Utils.getBitmapAsByteArray(((BitmapDrawable) context.getResources().getDrawable(R.drawable.logo)).getBitmap()))

        );
        firebaseDBHelper.SN_Insert(facebookSocialNetworkDTO);
        firebaseDBHelper.SN_Insert(insta);
        firebaseDBHelper.SN_Insert(linked);
        firebaseDBHelper.SN_Insert(snaochat);
        firebaseDBHelper.SN_Insert(logo);
        firebaseDBHelper.SN_Insert(twitter);
    }

    /**
     * Seeder scanning history data
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SCANNING_HISTORY_initFirebaseDB() {
        Bitmap imageBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.blank);
        byte[] imageBase64 = Utils.getBitmapAsByteArray(imageBitmap);

        ScannedDTO scannedDTO = new ScannedDTO(
                Utils.getRandomUUID(),
                Utils.getUserUUID(context),
                Utils.getSocialNWDTOFromName("Facebook").getId(),
                "Nguyen Cao Thiem",
                Base64.getEncoder().encodeToString(imageBase64),
                "https://facebook.com/thiemtinhte"
        );
        FirebaseDBHelper firebaseDBHelper = new FirebaseDBHelper();
        firebaseDBHelper.Scanning_History_Insert(scannedDTO);
    }

    /**
     * Seeder user information shared
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void SHARED_initFirebaseDB() {
        FirebaseDBHelper firebaseDBHelper = new FirebaseDBHelper();
        SharedDTO user1 = new SharedDTO(
                Utils.getRandomUUID(),
                Utils.getUserUUID(context),
                Utils.getSocialNWDTOFromName("Facebook").getId(),
                "https://facebook.com/thiemtinhte"
        );

        SharedDTO user2 = new SharedDTO(
                Utils.getRandomUUID(),
                Utils.getUserUUID(context),
                Utils.getSocialNWDTOFromName("Instagram").getId(),
                "https://www.instagram.com/thiemjason/"
        );
        SharedDTO user3 = new SharedDTO(
                Utils.getRandomUUID(),
                Utils.getUserUUID(context),
                Utils.getSocialNWDTOFromName("LinkedIn").getId(),
                "https://www.linkedin.com/in/cao-thiem-nguyen-628945206/"
        );
        SharedDTO user4 = new SharedDTO(
                Utils.getRandomUUID(),
                Utils.getUserUUID(context),
                Utils.getSocialNWDTOFromName("Twitter").getId(),
                "https://twitter.com/ThiemJason"
        );
        SharedDTO user5 = new SharedDTO(
                Utils.getRandomUUID(),
                Utils.getUserUUID(context),
                Utils.getSocialNWDTOFromName("Snapchat").getId(),
                "https://cv-nguyencaothiem.herokuapp.com/"
        );
        firebaseDBHelper.Shared_Insert(user1);
        firebaseDBHelper.Shared_Insert(user2);
        firebaseDBHelper.Shared_Insert(user3);
        firebaseDBHelper.Shared_Insert(user4);
        firebaseDBHelper.Shared_Insert(user5);
    }
}
