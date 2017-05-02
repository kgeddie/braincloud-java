package com.bitheads.braincloud.client;

/**
 * Created by prestonjennings on 15-09-01.
 */
public enum ServiceOperation {
    AUTHENTICATE,
    RESET_EMAIL_PASSWORD,
    CREATE,
    DELETE,
    READ,
    READ_SINGLETON,
    READ_FRIENDS,
    READ_SHARED,
    READ_SHARED_ENTITY,
    UPDATE,
    UPDATE_SUMMARY,
    RESET,
    VERIFY,

    READ_ACHIEVEMENTS,
    AWARD_ACHIEVEMENTS,
    READ_ACHIEVED_ACHIEVEMENTS,
    UPDATE_REWARDS,

    READ_QUESTS,
    READ_COMPLETED_QUESTS,
    READ_IN_PROGRESS_QUESTS,
    READ_NOT_STARTED_QUESTS,
    READ_QUESTS_WITH_STATUS,
    READ_QUESTS_WITH_BASIC_PERCENTAGE,
    READ_QUESTS_WITH_COMPLEX_PERCENTAGE,
    READ_QUESTS_BY_CATEGORY,
    RESET_MILESTONES,

    READ_FOR_CATEGORY,

    READ_MILESTONES,
    READ_MILESTONES_BY_CATEGORY,
    READ_COMPLETED_MILESTONES,
    READ_IN_PROGRESS_MILESTONES,

    PROCESS_STATISTICS,
    UPDATE_INCREMENT,
    READ_NEXT_XPLEVEL,
    SET_XPPOINTS,
    READ_SUBSET,
    READ_XP_LEVELS,

    GET_LIST,
    GET_LIST_BY_INDEXED_ID,
    GET_LIST_COUNT,
    GET_PAGE,
    GET_PAGE_BY_OFFSET,
    INCREMENT_USER_ENTITY_DATA,
    INCREMENT_SHARED_USER_ENTITY_DATA,
    INCREMENT_GLOBAL_ENTITY_DATA,
    READ_SHARED_ENTITIES_LIST,

    UPDATE_ACL,
    UPDATE_PARTIAL,
    UPDATE_SHARED,
    UPDATE_SET_MINIMUM,
    UPDATE_INCREMENT_TO_MAXIMUM,
    GET_SOCIAL_LEADERBOARD,
    GET_MULTI_SOCIAL_LEADERBOARD,
    GET_GLOBAL_LEADERBOARD,
    GET_GLOBAL_LEADERBOARD_PAGE,
    GET_GLOBAL_LEADERBOARD_VIEW,
    GET_GLOBAL_LEADERBOARD_VERSIONS,
    POST_SCORE,
    POST_SCORE_DYNAMIC,
    GET_COMPLETED_TOURNAMENT,
    REWARD_TOURNAMENT,
    GET_GROUP_SOCIAL_LEADERBOARD,
    GET_PLAYERS_SOCIAL_LEADERBOARD,
    LIST_ALL_LEADERBOARDS,
    GET_GLOBAL_LEADERBOARD_ENTRY_COUNT,
    UPDATE_ENTITY_OWNER_AND_ACL,
    MAKE_SYSTEM_ENTITY,
    GET_PLAYER_SCORE,
    GET_PLAYER_SCORES_FROM_LEADERBOARDS,
    REMOVE_PLAYER_SCORE,

    CREATE_WITH_INDEXED_ID,
    DEREGISTER,
    DEREGISTER_ALL,
    REGISTER,
    SEND_SIMPLE,
    SEND_RICH,
    SEND_TEMPLATED_TO_GROUP,
    SEND_NORMALIZED_TO_GROUP,
    SEND_NORMALIZED,
    SEND_NORMALIZED_BATCH,
    SEND_NORMALIZED_AT_UTC,
    SEND_NORMALIZED_AFTER_MINUTES,
    SEND_RICH_AT_UTC,
    SEND_RICH_AFTER_MINUTES,

    FULL_PLAYER_RESET,
    GAME_DATA_RESET,

    ATTACH,
    DETACH,
    MERGE,
    SWITCH_TO_CHILD_PROFILE,
    SWITCH_TO_PARENT_PROFILE,
    GET_CHILD_PROFILES,
    GET_IDENTITIES,
    GET_EXPIRED_IDENTITIES,
    REFRESH_IDENTITY,
    ATTACH_PARENT_WITH_IDENTITY,
    ATTACH_PEER_PROFILE,
    DETACH_PEER,
    GET_PEER_PROFILES,
    DETACH_PARENT,

    FIND_PLAYERS,
    FIND_PLAYERS_USING_FILTER,
    ENABLE_FOR_MATCH,
    DISABLE_FOR_MATCH,
    SHIELD_OFF,
    SHIELD_ON,
    SHIELD_ON_FOR,
    GET_SHIELD_EXPIRY,
    DECREMENT_PLAYER_RATING,
    INCREMENT_PLAYER_RATING,
    RESET_PLAYER_RATING,
    SET_PLAYER_RATING,

    START_MATCH,
    CANCEL_MATCH,
    COMPLETE_MATCH,

    TRIGGER,
    TRIGGER_MULTIPLE,

    GET_INVENTORY,
    AWARD_VC,
    RESET_PLAYER_VC,
    CONSUME_VC,
    GET_PLAYER_VC,
    START_STEAM_TRANSACTION,
    FINALIZE_STEAM_TRANSACTION,
    VERIFY_MICROSOFT_RECEIPT,
    ELIGIBLE_PROMOTIONS,
    CONFIRM_GOOGLEPLAY_PURCHASE,
    AWARD_PARENT_VC,
    CONSUME_PARENT_VC,
    GET_PARENT_VC,
    RESET_PARENT_VC,

    GET_UPDATED_FILES,
    GET_FILE_LIST,

    SEND,
    RUN,
    SCHEDULE_CLOUD_SCRIPT,
    RUN_PARENT_SCRIPT,
    CANCEL_SCHEDULED_SCRIPT,
    RUN_PEER_SCRIPT,
    RUN_PEER_SCRIPT_ASYNC,

    READ_FRIEND_PLAYER_STATE,
    LIST_FRIENDS,
    ADD_FRIENDS,
    REMOVE_FRIENDS,
    GET_USERS_ONLINE_STATUS,

    SUBMIT_TURN,
    //UPDATE_SUMMARY,
    ABANDON,
    COMPLETE,
    READ_MATCH,
    READ_MATCH_HISTORY,
    FIND_MATCHES,
    FIND_MATCHES_COMPLETED,
    DELETE_MATCH,

    DELETE_SENT,
    DELETE_INCOMING,
    UPDATE_EVENT_DATA,
    UPDATE_NAME,
    GET_EVENTS,
    LOGOUT,

    GET_FRIEND_PROFILE_INFO_FOR_EXTERNAL_ID,
    GET_PROFILE_INFO_FOR_CREDENTIAL,
    GET_PROFILE_INFO_FOR_EXTERNAL_AUTH_ID,
    GET_EXTERNAL_ID_FOR_PROFILE_ID,
    FIND_PLAYER_BY_NAME,
    FIND_USERS_BY_EXACT_NAME,
    FIND_USERS_BY_SUBSTR_NAME,
    FIND_PLAYER_BY_UNIVERSAL_ID,
    GET_SUMMARY_DATA_FOR_PROFILE_ID,
    READ_FRIEND_ENTITY,
    READ_FRIENDS_ENTITIES,
    READ_FRIENDS_WITH_APPLICATION,
    READ_BY_TYPE,
    UPDATE_TIME_TO_LIVE,

    UPDATE_SINGLETON,
    DELETE_SINGLETON,

    GET_ATTRIBUTES,
    UPDATE_ATTRIBUTES,
    REMOVE_ATTRIBUTES,
    UPDATE_PICTURE_URL,
    UPDATE_CONTACT_EMAIL,

    READ_PROPERTIES,

    START_STREAM,
    READ_STREAM,
    END_STREAM,
    DELETE_STREAM,
    ADD_EVENT,
    GET_STREAM_SUMMARIES_FOR_INITIATING_PLAYER,
    GET_STREAM_SUMMARIES_FOR_TARGET_PLAYER,

    REDEEM_CODE,
    GET_REDEEMED_CODES,

    CUSTOM_PAGE_EVENT,
    CUSTOM_SCREEN_EVENT,
    CUSTOM_TRACK_EVENT,

    // profanity
    PROFANITY_CHECK,
    PROFANITY_REPLACE_TEXT,
    PROFANITY_IDENTIFY_BAD_WORDS,

    //file
    PREPARE_USER_UPLOAD,
    LIST_USER_FILES,
    DELETE_USER_FILE,
    DELETE_USER_FILES,
    GET_CDN_URL,

    //group
    ACCEPT_GROUP_INVITATION,
    ADD_GROUP_MEMBER,
    APPROVE_GROUP_JOIN_REQUEST,
    CANCEL_GROUP_INVITATION,
    CREATE_GROUP,
    CREATE_GROUP_ENTITY,
    DELETE_GROUP,
    DELETE_GROUP_ENTITY,
    GET_MY_GROUPS,
    INCREMENT_GROUP_DATA,
    INCREMENT_GROUP_ENTITY_DATA,
    INVITE_GROUP_MEMBER,
    JOIN_GROUP,
    LEAVE_GROUP,
    LIST_GROUPS_PAGE,
    LIST_GROUPS_PAGE_BY_OFFSET,
    LIST_GROUPS_WITH_MEMBER,
    READ_GROUP,
    READ_GROUP_ENTITIES_PAGE,
    READ_GROUP_ENTITIES_PAGE_BY_OFFSET,
    READ_GROUP_ENTITY,
    READ_GROUP_DATA,
    READ_GROUP_MEMBERS,
    REJECT_GROUP_INVITATION,
    REJECT_GROUP_JOIN_REQUEST,
    REMOVE_GROUP_MEMBER,
    UPDATE_GROUP_DATA,
    UPDATE_GROUP_ENTITY_DATA,
    UPDATE_GROUP_MEMBER,
    UPDATE_GROUP_NAME,
    AUTO_JOIN_GROUP,

    //mail
    SEND_BASIC_EMAIL,
    SEND_ADVANCED_EMAIL,

    //tournament
    CLAIM_TOURNAMENT_REWARD,
    GET_TOURNAMENT_STATUS,
    JOIN_TOURNAMENT,
    LEAVE_TOURNAMENT,
    POST_TOURNAMENT_SCORE,
    POST_TOURNAMENT_SCORE_WITH_RESULTS,
    VIEW_CURRENT_REWARD,
    VIEW_REWARD
}
