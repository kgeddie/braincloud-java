package com.bitheads.braincloud.client;


public interface ReasonCodes {

    int OK = 0;

    // Error codes
    int NO_REASON_CODE = 0;

    int INVALID_NOTIFICATION = 20200;

    int INVALID_REQUEST = 40001;

    // 402
    int SWITCHING_FACEBOOK_MEMORY = 40201;
    int MERGING_MEMORY = 40202;
    int RESETING_MEMORY = 40203;
    int MOVING_ANONYMOUS_MEMORY = 40204;
    int LOGIN_SECURITY_ERROR = 40205;
    int MISSING_IDENTITY_ERROR = 40206;
    int SWITCHING_PROFILES = 40207;
    int MISSING_PROFILE_ERROR = 40208;

    int SECURITY_ERROR = 40209;

    int DOWNGRADING_TO_ANONYMOUS_ERROR = 40210;
    int DUPLICATE_IDENTITY_TYPE = 40211;
    int MERGE_PROFILES = 40212;
    int INVALID_PROPERTY_NAME = 40213;
    int EMAIL_NOT_VALIDATED = 40214;
    int DATABASE_ERROR = 40215;
    int PROPERTY_NOT_OVERRIDEABLE = 40216;
    int UNKNOWN_AUTH_ERROR = 40217;


    // Forbidden (403)...
    int UNABLE_TO_GET_FRIENDS_FROM_FACEBOOK = 40300;
    int BAD_SIGNATURE = 40301;
    int UNABLE_TO_VALIDATE_PLAYER = 40302;
    int PLAYER_SESSION_EXPIRED = 40303;
    int USER_SESSION_EXPIRED = 40303;
    int NO_SESSION = 40304;
    int PLAYER_SESSION_MISMATCH = 40305;
    int USER_SESSION_MISMATCH = 40305;
    int OPERATION_REQUIRES_A_SESSION = 40306;
    int TOKEN_DOES_NOT_MATCH_USER = 40307;
    int EVENT_CAN_ONLY_SEND_TO_FRIEND_OR_SELF = 40309;
    int NOT_FRIENDS = 40310;
    int VC_BALANCE_CANNOT_BE_SPECIFIED = 40311;
    int VC_LIMIT_EXCEEDED = 40312;
    int UNABLE_TO_GET_MY_DATA_FROM_FACEBOOK = 40313;
    int INVALID_AUTHENTICATION_TYPE = 40315;
    int INVALID_GAME_ID = 40316;
    int INVALID_APP_ID = INVALID_GAME_ID;


    // This product and receipt have already been claimed
    int APPLE_TRANS_ID_ALREADY_CLAIMED = 40317;
    // @deprecated - use APPLE_TRANS_ID_ALREADY_CLAIMED
    int ITUNES_PURCHASE_ALREADY_CLAIMED = APPLE_TRANS_ID_ALREADY_CLAIMED;

    // 400 Bad version
    int CLIENT_VERSION_NOT_SUPPORTED = 40318;
    int BRAINCLOUD_VERSION_NOT_SUPPORTED = 40319;
    int PLATFORM_NOT_SUPPORTED = 40320;

    int INVALID_PLAYER_STATISTICS_EVENT_NAME = 40321;
    int INVALID_USER_STATISTICS_EVENT_NAME = INVALID_PLAYER_STATISTICS_EVENT_NAME;

    // Game Version No Longer Supported
    int GAME_VERSION_NOT_SUPPORTED = 40322;
    int APP_VERSION_NOT_SUPPORTED = GAME_VERSION_NOT_SUPPORTED;
    // @deprecated - use APP_VERSION_NOT_SUPPORTED
    int APP_VERSION_NO_LONGER_SUPPORTED = APP_VERSION_NOT_SUPPORTED;

    int BAD_REFERENCE_DATA = 40324;
    int MISSING_OAUTH_TOKEN = 40325;
    int MISSING_OAUTH_VERIFIER = 40326;
    int MISSING_OAUTH_TOKEN_SECRET = 40327;
    int MISSING_TWEET = 40328;
    int FACEBOOK_PAYMENT_ID_ALREADY_PROCESSED = 40329;
    int DISABLED_GAME = 40330;
    int DISABLED_APP = DISABLED_GAME;
    int MATCH_MAKING_DISABLED = 40331;
    int UPDATE_FAILED = 40332;
    int INVALID_OPERATION = 40333;  // invalid operation for API call
    int MATCH_RANGE_ERROR = 40334;
    int PLAYER_IN_MATCH = 40335;
    int MATCH_PLAYER_SHIELDED = 40336;
    int MATCH_PLAYER_MISSING = 40337;
    int MATCH_PLAYER_LOGGED_IN = 40338;
    int INVALID_ITEM_ID = 40339;
    int MISSING_PRICE = 40340;
    int MISSING_USER_INFO = 40341;
    int MISSING_STEAM_RESPONSE = 40342;
    int MISSING_STEAM_TRANSACTION = 40343;
    int ENTITY_VERSION_MISMATCH = 40344;
    int MISSING_RECORD = 40345;
    int INSUFFICIENT_PERMISSIONS = 40346;
    int MISSING_IN_QUERY = 40347;
    int RECORD_EXPIRED = 40348;
    int INVALID_WHERE = 40349;
    int S3_ERROR = 40350;
    int INVALID_ATTRIBUTES = 40351;
    int IMPORT_MISSING_GAME_DATA = 40352;
    int IMPORT_MISSING_APP_DATA = IMPORT_MISSING_GAME_DATA;
    int IMPORT_SCHEMA_VERSION_TOO_OLD = 40353;
    int IMPORT_SCHEMA_VERSION_INVALID = 40355;
    int PLAYER_SESSION_LOGGED_OUT = 40356;
    int USER_SESSION_LOGGED_OUT = PLAYER_SESSION_LOGGED_OUT;
    int API_HOOK_SCRIPT_ERROR = 40357;
    int MISSING_REQUIRED_PARAMETER = 40358;
    int INVALID_PARAMETER_TYPE = 40359;
    int INVALID_IDENTITY_TYPE = 40360;
    int EMAIL_SEND_ERROR = 40361;
    int CHILD_ENTITY_PARTIAL_UPDATE_INVALID_DATA = 40362;
    int MISSING_SCRIPT = 40363;
    int SCRIPT_SECURITY_ERROR = 40364;
    int SERVER_SESSION_EXPIRED = 40365;
    int STREAM_DOES_NOT_EXIT = 40366;
    int STREAM_ACCESS_ERROR = 40367;
    int STREAM_COMPLETE = 40368;
    int INVALID_STATISTIC_NAME = 40369;
    int INVALID_HTTP_REQUEST = 40370;
    int GAME_LIMIT_REACHED = 40371;
    int APP_LIMIT_REACHED = GAME_LIMIT_REACHED;
    int GAME_RUNSTATE_DISABLED = 40372;
    int APP_RUNSTATE_DISABLED = GAME_RUNSTATE_DISABLED;
    int INVALID_COMPANY_ID = 40373;
    int INVALID_PLAYER_ID = 40374;
    int INVALID_USER_ID = 40374;
    int INVALID_TEMPLATE_ID = 40375;
    int MINIMUM_SEARCH_INPUT = 40376;
    int MISSING_GAME_PARENT = 40377;
    int MISSING_APP_PARENT = MISSING_GAME_PARENT;
    int GAME_PARENT_MISMATCH = 40378;
    int APP_PARENT_MISMATCH = GAME_PARENT_MISMATCH;
    int CHILD_PLAYER_MISSING = 40379;
    int CHILD_USER_MISSING = CHILD_PLAYER_MISSING;
    int MISSING_PLAYER_PARENT = 40380;
    int MISSING_USER_PARENT = MISSING_PLAYER_PARENT;
    int PLAYER_PARENT_MISMATCH = 40381;
    int USER_PARENT_MISMATCH = PLAYER_PARENT_MISMATCH;
    int MISSING_PLAYER_ID = 40382;
    int MISSING_USER_ID = MISSING_PLAYER_ID;
    int DECODE_CONTEXT = 40383;
    int INVALID_QUERY_CONTEXT = 40384;
    int GROUP_MEMBER_NOT_FOUND = 40385;
    int INVALID_SORT = 40386;
    int GAME_NOT_FOUND = 40387;
    int APP_NOT_FOUND = GAME_NOT_FOUND;
    int GAMES_NOT_IN_SAME_COMPANY = 40388;
    int APPS_NOT_IN_SAME_COMPANY = GAMES_NOT_IN_SAME_COMPANY;
    int IMPORT_NO_PARENT_ASSIGNED = 40389;
    int IMPORT_PARENT_CURRENCIES_MISMATCH = 40390;
    int INVALID_SUBSTITUION_ENTRY = 40391;
    int INVALID_TEMPLATE_STRING = 40392;
    int TEMPLATE_SUBSTITUTION_ERROR = 40393;
    int INVALID_OPPONENTS = 40394;
    int REDEMPTION_CODE_NOT_FOUND = 40395;
    int REDEMPTION_CODE_VERSION_MISMATCH = 40396;
    int REDEMPTION_CODE_ACTIVE = 40397;
    int REDEMPTION_CODE_NOT_ACTIVE = 40398;
    int REDEMPTION_CODE_TYPE_NOT_FOUND = 40399;
    int REDEMPTION_CODE_INVALID = 40400;
    int REDEMPTION_CODE_REDEEMED = 40401;
    int REDEMPTION_CODE_REDEEMED_BY_SELF = 40402;
    int REDEMPTION_CODE_REDEEMED_BY_OTHER = 40403;
    int SCRIPT_EMPTY = 40404;
    int ITUNES_COMMUNICATION_ERROR = 40405;
    int ITUNES_NO_RESPONSE = 40406;
    int ITUNES_RESPONSE_NOT_OK = 40407;
    int JSON_PARSING_ERROR = 40408;
    int ITUNES_NULL_RESPONSE = 40409;
    int ITUNES_RESPONSE_WITH_NULL_STATUS = 40410;
    int ITUNES_STATUS_BAD_JSON_RECEIPT = 40411;
    int ITUNES_STATUS_BAD_RECEIPT = 40412;
    int ITUNES_STATUS_RECEIPT_NOT_AUTHENTICATED = 40413;
    int ITUNES_STATUS_BAD_SHARED_SECRET = 40414;
    int ITUNES_STATUS_RECEIPT_SERVER_UNAVAILABLE = 40415;
    int ITUNES_RECEIPT_MISSING_ITUNES_PRODUCT_ID = 40416;
    int PRODUCT_NOT_FOUND_FOR_ITUNES_PRODUCT_ID = 40417;
    int DATA_STREAM_EVENTS_NOT_ENABLED = 40418;
    int INVALID_DEVICE_TOKEN = 40419;
    int ERROR_DELETING_DEVICE_TOKEN = 40420;
    int WEBPURIFY_NOT_CONFIGURED = 40421;
    int WEBPURIFY_EXCEPTION = 40422;
    int WEBPURIFY_FAILURE = 40423;
    int WEBPURIFY_NOT_ENABLED = 40424;
    int NAME_CONTAINS_PROFANITY = 40425;
    int NULL_SESSION = 40426;
    int PURCHASE_ALREADY_VERIFIED = 40427;
    int GOOGLE_IAP_NOT_CONFIGURED = 40428;
    int UPLOAD_FILE_TOO_LARGE = 40429;
    int FILE_ALREADY_EXISTS = 40430;
    int CLOUD_STORAGE_SERVICE_ERROR = 40431;
    int FILE_DOES_NOT_EXIST = 40432;
    int UPLOAD_ID_MISSING = 40433;
    int UPLOAD_JOB_MISSING = 40434;
    int UPLOAD_JOB_EXPIRED = 40435;
    int UPLOADER_EXCEPTION = 40436;
    int UPLOADER_FILESIZE_MISMATCH = 40437;
    int PUSH_NOTIFICATIONS_NOT_CONFIGURED = 40438;
    int MATCHMAKING_FILTER_SCRIPT_FAILURE = 40439;
    int ACCOUNT_ALREADY_EXISTS = 40440;
    int PROFILE_ALREADY_EXISTS = 40441;
    int MISSING_NOTIFICATION_BODY = 40442;
    int INVALID_SERVICE_CODE = 40443;
    int IP_ADDRESS_BLOCKED = 40444;
    int UNAPPROVED_SERVICE_CODE = 40445;
    int PROFILE_NOT_FOUND = 40446;
    int ENTITY_NOT_SHARED = 40447;
    int SELF_FRIEND = 40448;
    int PARSE_NOT_CONFIGURED = 40449;
    int PARSE_NOT_ENABLED = 40450;
    int PARSE_REQUEST_ERROR = 40451;
    int GROUP_CANNOT_ADD_OWNER = 40452;
    int NOT_GROUP_MEMBER = 40453;
    int INVALID_GROUP_ROLE = 40454;
    int GROUP_OWNER_DELETE = 40455;
    int NOT_INVITED_GROUP_MEMBER = 40456;
    int GROUP_IS_FULL = 40457;
    int GROUP_OWNER_CANNOT_LEAVE = 40458;
    int INVALID_INCREMENT_VALUE = 40459;
    int GROUP_VERSION_MISMATCH = 40460;
    int GROUP_ENTITY_VERSION_MISMATCH = 40461;
    int INVALID_GROUP_ID = 40462;
    int INVALID_FIELD_NAME = 40463;
    int UNSUPPORTED_AUTH_TYPE = 40464;
    int CLOUDCODE_JOB_NOT_FOUND = 40465;
    int CLOUDCODE_JOB_NOT_SCHEDULED = 40466;
    int GROUP_TYPE_NOT_FOUND = 40467;
    int MATCHING_GROUPS_NOT_FOUND = 40468;
    int GENERATE_CDN_URL_ERROR = 40469;
    int INVALID_PROFILE_IDS = 40470;
    int MAX_PROFILE_IDS_EXCEEDED = 40471;
    int PROFILE_ID_MISMATCH = 40472;
    int LEADERBOARD_DOESNOT_EXIST = 40473;
    int APP_LICENSING_EXCEEDED = 40474;
    int SENDGRID_NOT_INSTALLED = 40475;
    int SENDGRID_EMAIL_SEND_ERROR = 40476;
    int SENDGRID_NOT_ENABLED_FOR_APP = 40477;
    int SENDGRID_GET_TEMPLATES_ERROR = 40478;
    int SENDGRID_INVALID_API_KEY = 40479;
    int EMAIL_SERVICE_NOT_CONFIGURED = 40480;
    int INVALID_EMAIL_TEMPLATE_TYPE = 40481;
    int SENDGRID_KEY_EMPTY_OR_NULL = 40482;
    int BODY_TEMPLATE_CANNOT_COEXIST = 40483;
    int SUBSTITUTION_BODY_CANNOT_COEXIST = 40484;
    int INVALID_FROM_ADDRESS = 40485;
    int INVALID_FROM_NAME = 40486;
    int INVALID_REPLY_TO_ADDRESS = 40487;
    int INVALID_REPLY_TO_NAME = 40488;
    int FROM_NAME_WITHOUT_FROM_ADDRESS = 40489;
    int REPLY_TO_NAME_WITHOUT_REPLY_TO_ADDRESS = 40490;
    int CURRENCY_SECURITY_ERROR = 40491;
    int INVALID_PEER_CODE = 40492;
    int PEER_NO_LONGER_EXISTS = 40493;

    int CANNOT_MODIFY_TOURNAMENT_WITH_LEADERBOARD_SERVICE = 40494;
    int NO_TOURNAMENT_ASSOCIATED_WITH_LEADERBOARD = 40495;
    int TOURNAMENT_NOT_ASSOCIATED_WITH_LEADERBOARD = 40496;
    int PLAYER_ALREADY_TOURNAMENT_FOR_LEADERBOARD = 40497;
    int PLAYER_EARLY_FOR_JOINING_TOURNAMENT = 40498;
    int NO_LEADERBOARD_FOUND = 40499;
    int PLAYER_NOT_IN_TIME_RANGE_FOR_POSTSCORE_TOURNAMENT = 40500;
    int LEADERBOARD_ID_BAD = 40501;
    int SCORE_INPUT_BAD = 40502;
    int ROUND_STARTED_EPOCH_INPUT_BAD = 40503;
    int TOURNAMENT_CODE_INPUT_BAD = 40504;
    int PLAYER_NOT_ENROLLED_IN_TOURNAMENT = 40505;
    int LEADERBOARD_VERSION_ID_INVALID = 40506;
    int NOT_ENOUGH_BALANCE_TO_JOIN_TOURNAMENT = 40507;
    int PARENT_ALREADY_ATTACHED = 40508;
    int PEER_ALREADY_ATTACHED = 40509;
    int IDENTITY_NOT_ATTACHED_WITH_PARENT = 40510;
    int IDENTITY_NOT_ATTACHED_WITH_PEER = 40511;
    int LEADERBOARD_SCORE_UPDATE_ERROR = 40512;
    int ERROR_CLAIMING_REWARD = 40513;
    int NOT_ENOUGH_PARENT_BALANCE_TO_JOIN_TOURNAMENT = 40514;
    int NOT_ENOUGH_PEER_BALANCE_TO_JOIN_TOURNAMENT = 40515;
    int PLAYER_LATE_FOR_JOINING_TOURNAMENT = 40516;
    int VIEWING_REWARD_FOR_NON_PROCESSED_TOURNAMENTS = 40517;
    int NO_REWARD_ASSOCIATED_WITH_LEADERBOARD = 40518;
    int PROFILE_PEER_NOT_FOUND = 40519;
    int LEADERBOARD_IN_ACTIVE_STATE = 40520;
    int LEADERBOARD_IN_CALCULATING_STATE = 40521;
    int TOURNAMENT_RESULT_PROCESSING_FAILED = 40522;
    int TOURNAMENT_REWARDS_ALREADY_CLAIMED = 40523;
    int NO_TOURNAMENT_FOUND = 40524;
    int UNEXPECTED_ERROR_RANK_ZERO_AFTER_PROCESSING = 40525;
    int UNEXPECTED_ERROR_DELETING_TOURNAMENT_LEADERBOARD_SCORE = 40526;
    int INVALID_RUN_STATE = 40527;
    int LEADERBOARD_SCORE_DOESNOT_EXIST = 40528;
    int INITIAL_SCORE_NULL = 40529;
    int TOURNAMENT_NOTIFICATIONS_PROCESSING_FAILED = 40530;
    int ACL_NOT_READABLE = 40531;
    int INVALID_OWNER_ID = 40532;
    int IMPORT_MISSING_PEERS_DATA = 40533;
    int INVALID_CREDENTIAL = 40534;

    int NO_TWITTER_CONSUMER_KEY = 500001;
    int NO_TWITTER_CONSUMER_SECRET = 500002;
    int INVALID_CONFIGURATION = 500003;
    int ERROR_GETTING_REQUEST_TOKEN = 500004;
    int ERROR_GETTING_ACCESS_TOKEN = 500005;

    int FACEBOOK_ERROR = 500010;
    int FACEBOOK_SECRET_MISMATCH = 500011;
    int FACEBOOK_AUTHENTICATION_ERROR = 500012;
    int FACEBOOK_APPLICATION_TOKEN_REQUEST_ERROR = 500013;
    int FACEBOOK_BAD_APPLICATION_TOKEN_SIGNATURE = 500014;

    int MONGO_DB_EXCEPTION = 600001;

    /**
     * Client defined value for a timeout detected client-side.
     */
    int CLIENT_NETWORK_ERROR_TIMEOUT = 90001;
    int CLIENT_UPLOAD_FILE_CANCELLED = 90100;
    int CLIENT_UPLOAD_FILE_TIMED_OUT = 90101;
    int CLIENT_UPLOAD_FILE_UNKNOWN = 90102;
    int CLIENT_DISABLED = 90200;
}
