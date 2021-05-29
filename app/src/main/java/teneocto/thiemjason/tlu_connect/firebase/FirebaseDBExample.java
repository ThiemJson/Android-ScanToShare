package teneocto.thiemjason.tlu_connect.firebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.models.NotificationDTO;
import teneocto.thiemjason.tlu_connect.models.ScanningHistoryDTO;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.models.SocialNetworkDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class FirebaseDBExample {
    Context context;

    public FirebaseDBExample(Context context) {
        this.context = context;
    }

    public void USER_initFirebaseDB() {
//        Bitmap bitmap = ((BitmapDrawable) mImagePicker.getDrawable()).getBitmap();
//        String imageBase64 = Base64.getEncoder().encodeToString(Utils.getBitmapAsByteArray(bitmap));
//
//        UserDTO userDTO = new UserDTO(
//                1,
//                mFirstName.getText().toString(),
//                mLastName.getText().toString(),
//                mEmail.getText().toString(),
//                mPosition.getText().toString(),
//                mCompany.getText().toString(),
//                imageBase64
//        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void NOTIFICATION_initFirebaseDB() {
        NotificationDTO notificationDTO = new NotificationDTO(
                1,
                1,
                "Cập nhật ứng dụng lên phiên bản 1.0.1, chính sửa các lỗi hiện đang hiện hữu tại đăng nhập bằng Facebook",
                "Cập nhật phiên bản mới",
                Base64.getEncoder().encodeToString(Utils.getBitmapAsByteArray(((BitmapDrawable) context.getResources().getDrawable(R.drawable.facebook)).getBitmap()))
        );
        FirebaseDBHelper firebaseDBHelper = new FirebaseDBHelper();
        firebaseDBHelper.Notification_Insert(notificationDTO);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SOCIAL_NETWORK_initFirebaseDB() {
        FirebaseDBHelper firebaseDBHelper = new FirebaseDBHelper();
        byte[] facebookImageBase64 = Utils.getBitmapAsByteArray(((BitmapDrawable) context.getResources().getDrawable(R.drawable.facebook)).getBitmap());
        SocialNetworkDTO facebookSocialNetworkDTO = new SocialNetworkDTO(
                1,
                "facebook",
                Base64.getEncoder().encodeToString(facebookImageBase64)

        );

        SocialNetworkDTO insta = new SocialNetworkDTO(
                2,
                "instagram",
                Base64.getEncoder().encodeToString(Utils.getBitmapAsByteArray(((BitmapDrawable) context.getResources().getDrawable(R.drawable.instagram)).getBitmap()))

        );

        SocialNetworkDTO linked = new SocialNetworkDTO(
                3,
                "linkedin",
                Base64.getEncoder().encodeToString(Utils.getBitmapAsByteArray(((BitmapDrawable) context.getResources().getDrawable(R.drawable.linkedin)).getBitmap()))

        );

        SocialNetworkDTO snaochat = new SocialNetworkDTO(
                4,
                "snapchat",
                Base64.getEncoder().encodeToString(Utils.getBitmapAsByteArray(((BitmapDrawable) context.getResources().getDrawable(R.drawable.sapchat)).getBitmap()))

        );

        SocialNetworkDTO twitter = new SocialNetworkDTO(
                5,
                "twitter",
                Base64.getEncoder().encodeToString(Utils.getBitmapAsByteArray(((BitmapDrawable) context.getResources().getDrawable(R.drawable.twiiter)).getBitmap()))

        );

        SocialNetworkDTO logo = new SocialNetworkDTO(
                6,
                "logo",
                Base64.getEncoder().encodeToString(Utils.getBitmapAsByteArray(((BitmapDrawable) context.getResources().getDrawable(R.drawable.logo)).getBitmap()))

        );
        firebaseDBHelper.SN_Insert(facebookSocialNetworkDTO);
        firebaseDBHelper.SN_Insert(insta);
        firebaseDBHelper.SN_Insert(linked);
        firebaseDBHelper.SN_Insert(snaochat);
        firebaseDBHelper.SN_Insert(logo);
        firebaseDBHelper.SN_Insert(twitter);
    }

    public void SCANNING_HISTORY_initFirebaseDB() {
        ScanningHistoryDTO scanningHistoryDTO = new ScanningHistoryDTO(
                1, 1, 2
        );
    }

    public void SHARED_initFirebaseDB() {
        FirebaseDBHelper firebaseDBHelper = new FirebaseDBHelper();
        SharedDTO user1 = new SharedDTO(
                1,
                1,
                1,
                "https://facebook.com/thiemtinhte"
        );

        SharedDTO user2 = new SharedDTO(
                2,
                1,
                2,
                "https://www.instagram.com/thiemjason/"
        );
        SharedDTO user3 = new SharedDTO(
                3,
                1,
                3,
                "https://www.linkedin.com/in/cao-thiem-nguyen-628945206/"
        );
        SharedDTO user4 = new SharedDTO(
                4,
                1,
                5,
                "https://twitter.com/ThiemJason"
        );
        SharedDTO user5 = new SharedDTO(
                5,
                1,
                4,
                "https://cv-nguyencaothiem.herokuapp.com/"
        );
        firebaseDBHelper.Shared_Insert(user1);
        firebaseDBHelper.Shared_Insert(user2);
        firebaseDBHelper.Shared_Insert(user3);
        firebaseDBHelper.Shared_Insert(user4);
        firebaseDBHelper.Shared_Insert(user5);
    }
}
