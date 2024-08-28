package com.accessauthority.chatvox;

import java.util.HashMap;

public class Constants {
    public static final String KEY_COLLECTION_USERS = "users";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NUMBER = "number";
    public static final String KEY_PREFERENCE_NAME = "ChatVoxPreference";
    public static final String KEY_IS_SIGNED_ID = "isSignedIn";
    public static final String  KEY_USER_ID = "userId";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_FCM_TOKEN = "fcmToken";
    public static final String KEY_USER = "user";
    public static final String KEY_COLLECTION_CHAT = "chat";
    public static final String KEY_SENDER_ID = "senderID";
    public static final String KEY_RECEIVER_ID="receiverId";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_LAST_MESSAGE = "lastMessage";
    public static final String KEY_COLLECTION_CONVERSATIONS = "conversations";
    public static final String KEY_SENDER_NAME = "senderName";
    public static final String KEY_RECEIVER_NAME = "receiverName";
    public static final String KEY_RECEIVER_IMAGE = "receiverImage";
    public static final String KEY_SENDER_IMAGE = "senderImage";
    public static final String KEY_AVAILABILITY = "availability";
    public  static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static  final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    public static  final String REMOTE_MSG_DATA = "data";
    public static  final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";

    public static HashMap<String, String> remoteMsgHeaders = null;
    public  static HashMap<String, String> getRemoteMsgHeaders(){
        if (remoteMsgHeaders == null){
            remoteMsgHeaders = new HashMap<>();
            remoteMsgHeaders.put(
                    REMOTE_MSG_AUTHORIZATION,
                    "key=AAAAIMjhlA8:APA91bEdcD8qDE5aumq_sWMcryvZ8IdByZEcMpePAsu-smbumLlE-eUdOF7SjdR6jRLsq7NaqNtGFN5rRSQRvV0LyhlBNkctvq82DkgOhxK64bLP2B0Daz-k-cRjI5Dmq7MDfQZ5Zfh7"
            );
            remoteMsgHeaders.put(
                    REMOTE_MSG_CONTENT_TYPE,
                    "application/json"
            );
        }
        return remoteMsgHeaders;
    }
}
