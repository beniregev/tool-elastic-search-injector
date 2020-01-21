package com.nice.mcr.injector.service;

import java.util.Random;

public class Consts {

    static final String VERSION = "version";
    static final String TENANT_ID = "tenantId";
    static final String SWITCH_ID= "switchId";
    static final String CONTACT_ID= "contactId";
    static final String CONTACT_START_TIME= "contactStartTime";
    static final String CONTACT_END_TIME= "contactEndTime";
    static final String CONTACT_GMT_START_TIME= "dtContactGMTStartTime";
    static final String CONTACT_GMT_END_TIME= "dtContactGMTStopTime";
    static final String SEGMENT_ID= "segmentId";
    static final String SEGMENT_START_TIME= "segmentStartTime";
    static final String SEGMENT_END_TIME= "segmentEndTime";
    static final String SEGMENT_GMT_START_TIME= "dtSegmentGMTStartTime";
    static final String SEGMENT_GMT_END_TIME= "dtSegmentGMTStopTime";
    static final String BI_SEGMENT_DURATION = "biSegmentDuration";
    static final String USER_ID = "UserId";
    static final String CTI_USER_IDENTIFIER = "ctiUserIdentifier";
    static final String IS_INITIATOR = "isInitiator";
    static final String MEDIA_TYPE = "mediaType";
    static final String RECORDING_ID = "recordingId";
    static final String DIRECTION = "direction";
    static final String CALL_DIRECTION= "callDirection";
    static final String SESSION_ID = "sessionId";
    static final String RECORDER_ID = "recorderId";
    static final String BUSINESS_DATA= "businessData";
    static final String DNIS= "DNIS";
    static final String PBX_CALL_ID= "pbxCallId";
    static final String I_PBX_CALL_ID = "iPBXCallID";
    static final String PBX_UNIQUE_CALL_ID= "pbxUniqueCallId";
    static final String UNIQUE_DEVICE_ID = "uniqueDeviceId";
    static final String USER_GROUP_IDS = "userGroupIds";
    static final String PARTICIPANTS= "participants";
    static final String PARTICIPANT_TYPE = "participantType";
    static final String RECORDINGS= "recordings";
    static final String EXCEPTIONS= "exceptions";
    static final String RECORDING_STATUS = "recordingStatus";
    static final String RECORDING_POLICY_ID = "recordingPolicyId";


    static final String INTERACTION_ID = "segmentId";
    static final String INTERACTION_LOCAL_START_TIME = "dtInteractionLocalStartTime";
    static final String INTERACTION_LOCAL_STOP_TIME = "dtInteractionLocalStopTime";
    static final String INTERACTION_GMT_START_TIME = "dtInteractionGMTStartTime";
    static final String INTERACTION_GMT_STOP_TIME = "dtInteractionGMTStopTime";
    static final String INTERACTION_DURATION = "biInteractionDuration";
    static final String INTERACTION_OPEN_REASON_ID = "iInteractionOpenReasonID";
    static final String INTERACTION_CLOSE_REASON_ID = "iInteractionCloseReasonID";
    static final String I_SWITCH_ID = "iSwitchID";
    static final String INITIATOR_USER_ID = "iInitiatorUserID";
    static final String OTHER_SWITCH_ID = "iOtherSwitchID";
    static final String INTERACTION_QA_TYPE_ID = "bitInteractionQATypeID";
    static final String INTERACTION_TYPE_ID = "tiInteractionTypeID";
    static final String INTERACTION_RECORDED_TYPE_ID = "tiInteractionRecordedTypeID";
    static final String IS_EVALUATED = "bitIsEvaluated";
    static final String IS_CUSTOMER_EVALUATED = "bitIsCustomerEvaluated";
    static final String MEDIA_TYPES_ID = "iMediaTypesId";
    static final String INTERACTION_DESC = "vcInteractionDesc";
    static final String INITIATOR_TYPE_ID = "siInitiatorTypeID";
    static final String INITIATOR_TYPE_DESC = "vcInitiatorTypeDesc";
    static final String EMAIL_REF_ID = "vcEmailRefID";
    static final String REPLY_EMAIL_REF_ID = "vcReplyEmailRefID";
    static final String NVC_EMAIL_SENDER = "nvcEmailSender";
    static final String NVC_FROM = "nvcFrom";
    static final String NVC_TO = "nvcTo";
    static final String NVC_SUBJECT = "nvcSubject";
    static final String NVC_CC = "nvcCC";
    static final String NVC_BCC = "nvcBcc";
    static final String CLIENT_DTMF = "vcClientDTMF";
//    static final String PBX_CALL_ID = "iPBXCallID";
    static final String EXTERNAL_CALL_ID = "iExternalCallId";
    static final String CALL_DIRECTION_TYPE_ID = "tiCallDirectionTypeID";
    static final String VECTOR_NUMBER = "vcVectorNumber";
    static final String PBX_UNIVARSAL_CALL_INTERACTION_ID = "vcPBXUniversalCallInteractionID";
    static final String COMPOUND_ID = "iCompoundID";
    static final String NVC_BUSINESS_DATA = "nvcBusinessData";
    static final String BIT_IS_PLAYBACK_CALL = "bitIsPlaybackCall";
    static final String PARTICIPANT_ID = "iParticipantID";
    static final String STATION = "nvcStation";
    static final String PHONE_NUMBER = "phoneNumber";
    static final String NVC_PHONE_NUMBER = "nvcPhoneNumber";
    static final String OS_LOGIN = "osLogin";
    static final String FIRST_USER = "bitIsFirstUser";
    static final String IS_INERACTION_INITIATOR = "bitIsInteractionInitiator";
    static final String DEVICE_TYPE_ID = "tiDeviceTypeID";
    static final String DEVICE_ID = "iDeviceID";
    static final String CTI_AGENT_NAME = "nvcCTIAgentName";
    static final String TRUNK_GROUP = "vcTrunkGroup";
    static final String TRUNK_NUMBER = "vcTrunkNumber";
    static final String TRUNK_LABEL = "nvcTrunkLabel";
    static final String NVC_DIALED_NUMBER = "nvcDialedNumber";
    static final String CLIENT_ID = "iClientID";
    static final String VIRTUAL_DEVICE_ID = "iVirtualDeviceID";
    static final String PARTICIPANT_TYPE_ID = "tiParticipantTypeID";
    static final String PARTICIPANT_TYPE_DESC = "vcParticipantTypeDesc";
    static final String OPEN_REASON_DESC = "vcOpenReasonDesc";
    static final String CLOSE_REASON_DESC = "vcCloseReasonDesc";
    static final String DEVICE_TYPE_DESC = "vcDeviceTypeDesc";
    static final String RECORDING_SIDE_TYPE_ID = "tiRecordingSideTypeID";
    static final String RECORDING_SIDE_DESC = "vcRecordingSideDesc";
    static final String VC_MEDIA_DESC = "vcMediaDesc";
    static final String DIRECTION_TYPE_ID = "tiDirectionTypeID";
    static final String VC_DIRECTION_TYPE_DESC = "vcDirectionTypeDesc";
    static final String I_RECORDING_ID = "iRecordingID";
    static final String LOGGER = "iLogger";
    static final String CHANNEL = "iChannel";
    static final String MML_RECORDING_HINT = "vcMmlRecordingHint";
    static final String RECORDING_START_TIME = "startTime";
    static final String RECORDING_END_TIME = "endTime";
    static final String RECORDING_GMT_START_TIME = "dtRecordingGMTStartTime";
    static final String RECORDING_GMT_END_TIME = "dtRecordingGMTStopTime";
    static final String RECORDING_RECORDED_TYPE_ID = "tiRecordingRecordedTypeID";
    static final String PROGRAM_ID = "iProgramID";
    static final String RECORDED_PARTICIPANT_ID = "iRecordedParticipantID";
    static final String TIME_DIFF = "biTimeDiff";
    static final String WRAPUP_TIME = "biWrapupTime";
    static final String BI_SESSION_ID = "biSessionID";
    static final String ITEM_DATA_TYPE_ID = "tiItemDataTypeID";
    static final String ITEM_DATA_TYPE_DESC = "iItemDataTypeDesc";
    static final String CREATOR_TYPE_ID = "tiCreatorTypeID";
    static final String CREATOR_DESC = "vcCreatorDesc";
    static final String ITEM_TYPE_ID = "iItemTypeID";
    static final String ITEM_TYPE_DESC = "nvcItemTypeDesc";
    static final String I_CONTACT_ID = "iContactID";
    static final String ITEM_SEQUENCE_NUMBER = "iItemSequenceNumber";
    static final String TIME_STAMP = "dtTimeStamp";
    static final String ITEM_USER_ID = "iItemUserID";
    static final String ITEM_VALUE = "nvcItemValue";
    static final String ITEM_IS_DELETED = "bitIsDeleted";
    static final String ITEM_IS_PUBLIC = "bitIsPublic";
    static final String ITEM_OFFSET = "iOffset";
    static final String RECORDED_TYPE_ID = "tiRecordedTypeID";
    static final String RECORDED_TYPE_DESC = "vcRecordedTypeDesc";
    static final String ARCHIVE_ID = "iArchiveID";
    static final String MEDIA_TYPE_ID = "tiMediaTypeId";
    static final String ARCHIVE_PATH = "vcArchivePath";
    static final String ARCHIVE_ID_HIGH = "iArchiveIdHigh";
    static final String ARCHIVE_ID_LOW = "iArchiveIdLow";
    static final String ARCHIVE_CLASS = "iArchiveClass";
    static final String SC_SERVER_ID = "vcScServerId";
    static final String SC_SITE_ID = "iSiteID";
    static final String SC_RULE_ID = "iRuleID";
    static final String SC_LOGGER_ID = "iLoggerID";
    static final String SC_LOGGER_RESOURCE = "iLoggerResource";
    static final String ARCHIVE_UNIQUE_ID = "vcArchiveUniqueId";
    static final String RETENTION_DAYS = "iRetentionDays";
    static final String COMPLETE_GMT_START_TIME = "dtContactGMTStartTime";
    static final String COMPLETE_GMT_STOP_TIME = "dtContactGMTStopTime";
    static final String COMPLETE_DURATION = "biContactDuration";
    static final String COMPLETE_OPEN_REASON_ID = "iContactOpenReasonID";
    static final String COMPLETE_CLOSE_REASON_ID = "iContactCloseReasonID";
    static final String TRANSFER_SITE_ID = "iTransferSiteID";
    static final String TRANSFER_CONTACT_ID = "iTransferContactID";
    static final String COMPLETE_RECORDED_TYPE_ID = "tiContactRecordedTypeID";
    static final String CONTACT_QA_TYPE_ID = "bitContactQATypeID";
    static final String COMPLETE_DIRECTION_TYPE_ID = "tiContactDirectionTypeID";
    static final String EXCEPTION_TYPE_ID = "iExceptionTypeID";
    static final String EXCEPTION_TYPE_DESC = "nvcExceptionDesc";
    static final String EXCEPTION_POSSIBLE_CAUSE = "nvcPossibleCause";
    static final String EXCEPTION_RECOMMENDED_ACTION = "nvcRecomendedAction";
    static final String EXCEPTION_NUMBER = "iExceptionNumber";
    static final String EXCEPTION_TIMESTAMP = "dtExceptionTimeStamp";
    static final String EXCEPTION_DETAIL = "vcExceptionDetail";
    static final String CREATED_BY_ID = "iCreatedByID";
    static final String CLIP_NAME = "nvcClipName";
    static final String CREATION_DATE = "iCreatedByID";
    static final String CATEGORY_ID = "iCategoryID";
    static final String IS_PUBLISHED = "bIsPublished";
    static final String IS_PUBLIC = "bIsPublic";
    static final String CLIP_DURATION = "biClipDuration";
    static final String IS_DELETED = "bIsDeleted";
    static final String I_INTERACTION_TYPE_ID = "iInteractionTypeID";
    static final String TASK_ID = "iTaskID";
    static final String FLAG_ID = "iFlagID";
    static final String USER_SITE_ID = "iUserSiteID";
    static final String AGENT_ID = "agentId";
    static final String I_AGENT_ID = "iAgentId";
    static final String NVC_FIRST_NAME = "nvcFirstName";
    static final String NVC_LAST_NAME = "nvcLastName";
    static final String NVC_MIDDLE_NAME = "nvcMiddleName";
    static final String EXTENSION = "nvcExtension";
    static final String SWITCH_AGENT_ID = "nvcSwitchAgentId";
    static final String LOCATION_ID = "iLocationId";
    static final String JOB_SKILL = "nvcJobSkill";
    static final String JOB_FUNCTION = "nvcJobFunction";
    static final String JOB_CLASS = "nvcJobClass";
    static final String DEPARTMENT = "nvcDepartment";
    static final String LOCATION = "nvcLocation";
    static final String ITEM_ID = "iItemId";
    static final String GRAD_SCORE = "fltGradScore";
    static final String STATUS = "status";
    static final String I_STATUS = "iStatus";
    static final String FORMATTER_NAME = "nvcFormattedName";
    static final String MANAGED_ID = "ManagerId";
    static final String GROUP_ID = "GroupId";
    static final String SURVEY_NAME = "nvcSurveyName";
    static final String TOPIC_ID = "iTopicID";
    static final String TOPIC_NAME = "nvcTopicName";
    static final String CATEGORY_SCORE = "dCategoryScore";
    static final String NVC_NAME = "nvcName";
    static final String SCORE = "iScore";
    static final String MODIFY_DATE = "dtModifyDate";
    static final String NOTIFICATION_DATE = "dtNotificationDate";
    static final String LOCK_STATUS = "tiLockStatus";

    public static final int MINUTES_PER_DAY = 1_440;
}

enum OpenCallReason{

    Unknown(0),
    Normal_Start(1),
    Normal_End(2),
    Transfer_Start(4),
    Transfer_End(8),
    Conference(16),
    Inter_Queue(32),
    Networking(64),
    Segment(128),
    Compound(256),
    Block(512),
    Clip_Recording(1024),
    TRS(4096),
    CompoundOpenedByCallSrvr(8192),
    FakeCompound(16384),
    SplitCompound(32768),
    NoHandleEndWithoutStart(65536),
    ScreenSense(524288),
    MediaInterconnect(1048576);

    private final int openCallReasonID;

    private OpenCallReason(int openCallReasonID){
        this.openCallReasonID = openCallReasonID;
    }

    public int getOpenCallReasonID(){
        return openCallReasonID;
    }

    public static OpenCallReason getRandomReason() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}

enum CloseCallReason{
    Unknown(0),
    Normal_Start(1),
    Normal_End(2),
    Transfer_Start(4),
    Transfer_End(8),
    Conference(16),
    Inter_Queue(32),
    Networking(64),
    Segment(128),
    Compound(256),
    Block(512),
    Clip_Recording(1024),
    MaxOpenCallDurationByDriver(262144),
    ScreenSense(524288),
    MediaInterconnect(1048576);

    private final int closeCallReason;
    private CloseCallReason(int openCallReasonID){
        this.closeCallReason = openCallReasonID;
    }

    public int getCloseCallReasonID(){
        return closeCallReason;
    }

    public static CloseCallReason getRandomReason() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}

enum InteractionType{
    Unknown(0),
    Clip_Recording(1),
    CTI(2),
    TRS(3),
    Email(4),
    Survey(5),
    Chat(8),
    SMS(9),
    Web(16),
    Time_Interval(32),
    Vox(64),
    ScreenSense(128);

    private final int interactionTypeID;

    private InteractionType(int interactionTypeID){
        this.interactionTypeID = interactionTypeID;
    }

    public int getRandomInteractionTypeID(){
        return interactionTypeID;
    }

    public static InteractionType getRandomInteractionType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}

enum MediaTypes{
    Unknown(0),
    Screen(1),
    Voice(2),
    Email(4),
    Chat(8),
    Web(16),
    Video(32),
    SMS(64),
    Verbatim(100),
    OriginalEmail(128),
    Survey(256),
    Survey_WebComplaint(512),
    Survey_Verbatim(1024);

    private final int mediaTypeID;
    private MediaTypes(int mediaTypeID){
        this.mediaTypeID = mediaTypeID;
    }

    public int getMediaTypeID(){
        return mediaTypeID;
    }

    public static MediaTypes getRandomMediaType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}

enum InitiatorType{
    No_Value(0),
    Rec_Prog(1),
    Supervisor(2),
    Agent(4),
    Host(8),
    QA(16),
    Total(32),
    ROD(64),
    Monitor(256),
    SEA(512),
    Clip_Recorder(1024),
    TRS(2048),
    Internal_Nice_Application(4096),
    Hold(8192);

    private final int InitiatorTypeID;
    private InitiatorType(int InitiatorTypeID){
        this.InitiatorTypeID = InitiatorTypeID;
    }

    public int getInitiatorTypeID(){
        return InitiatorTypeID;
    }

    public static InitiatorType getRandomInitiatorType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}

enum DirectionType{
    Unknown(0),
    Incoming(1),
    Outgoing(2),
    Internal(3),
    Block(4),
    Tandem(5),
    External(6);

    private final int directionTypeID;
    private DirectionType(int directionTypeID){
        this.directionTypeID = directionTypeID;
    }

    public int getDirectionTypeID(){
        return directionTypeID;
    }

    public static DirectionType getRandomDirectionType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}

enum ParticipantType{
    Unknown(0),
    Internal(1),
    External(2),
    Loggers(3);

    private final int participantTypeID;
    private ParticipantType(int participantTypeID){
        this.participantTypeID = participantTypeID;
    }

    public int getparticipantTypeID(){
        return participantTypeID;
    }

    public static ParticipantType getRandomParticipantType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}

enum DeviceType{
    Unknown(0),
    Station(1),
    Line(2),
    Button(3),
    Acd(4),
    Trunk(5),
    Operator(6),
    Station_Group(7),
    Line_Group(8),
    Button_Group(9),
    Acd_Group(10),
    Trunk_Group(11),
    Operator_Group(12),
    Vdn(13),
    Position(14),
    Agent(15),
    Ivr(16),
    Handset(17),
    Microphone(18),
    Speaker(19),
    Rnj(20),
    Intercom(21),
    Mobile(22),
    Other(200);

    private final int DeviceTypeID;
    private DeviceType(int DeviceTypeID){
        this.DeviceTypeID = DeviceTypeID;
    }

    public int DeviceTypeID(){
        return DeviceTypeID;
    }

    public static DeviceType getRandomDeviceType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}

enum RecordingSideType{
    Unknown(0),
    In(1),
    Out(2),
    Summed(3),
    Irrelevant(4),
    Stereo(5);

    private final int RecordingSideTypeID;

    private RecordingSideType(int RecordingSideTypeID){
        this.RecordingSideTypeID = RecordingSideTypeID;
    }

    public int RecordingSideTypeID(){
        return RecordingSideTypeID;
    }

    public static RecordingSideType getRandomRecordingSideType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}

enum RecordedType {
    Unknown(0),
    Yes(1),
    No(2),
    Partial(3);

    private final int recordingRecordedTypeID;
    private RecordedType(int recordingRecordedTypeID){
        this.recordingRecordedTypeID = recordingRecordedTypeID;
    }

    public int RecorderTypeID(){
        return recordingRecordedTypeID;
    }

    public static RecordedType getRandomRecordedType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}

enum ItemDataType {
    String(1),
    Number(2),
    Date(3);

    private final int itemDataTypeID;
    private ItemDataType(int itemDataTypeID){
        this.itemDataTypeID = itemDataTypeID;
    }

    public int itemDataTypeID(){
        return itemDataTypeID;
    }

    public static ItemDataType getRandomDataType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}

enum CreatorType {
    Unknown(0),
    Driver(1),
    Application(2),
    User(3);

    private final int creatorTypeID;
    private CreatorType(int creatorTypeID){
        this.creatorTypeID = creatorTypeID;
    }

    public int creatorTypeID(){
        return creatorTypeID;
    }

    public static CreatorType getRandomCreatorType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}

enum ItemType {
    Comments(1),
    Notations(2),
    Operational_Group_Id(3000),
    Emergency_Terminal_Address(3001),
    Party_Address(3002);

    private final int itemTypeID;
    private ItemType(int ItemTypeID){
        this.itemTypeID = ItemTypeID;
    }

    public int itemTypeID(){
        return itemTypeID;
    }

    public static ItemType getRandomItemType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}

enum ExceptionType {
    Duplicate_call_start(1,
            "Duplicate start of call - another call with the same call key has started while this call was open.",
            "Problem with the driver or PABX reports.",
            "Check the driver or PABX. Collect the driver and CLS log files."),
    Maximum_duration_exceeded(2,
            "Call too long - the call was open for more seconds than the value of the registry parameter MaxOpenCallDuration " +
                    "and was therefore forcibly closed.",
            "Either the call was longer than the parameters value, or there is a problem with the driver or PABX reports.",
            "Check if the value of the Call Server parameter op_MaxOpenCallDuration matches the length of calls in the site."),
    Call_flushed_while_open(3,
            "Call flushed - a flush command was executed while the call was open.",
            "The driver could have gone down and come up.",
            "Check that the driver is up. Review the driver log files, look for errors. If there is no error no action is needed."),
    Call_start_not_reported(5,
            "End call without start (Default Start Time).",
            "Problem with the driver or PABX reports. Could be followed by reports with exp02 because the start and end " +
                    "call report has different information.",
            "Check the driver or PABX. Review the driver log files, look for errors. If there are errors, collect the driver and CLS log files."),
    No_available_recording_resource(7,
            "No available recording resource.",
            "There are not enough  recording channels to handle the number of recording requests in the site.",
            "Check call rate and system load vs. the number of channels. Check network connection to logger. Check Logger" +
                    " Availability at the time of the call. \n" +
                    "Could be caused by unclosed recorded calls. Check for exp02 calls. If there is a substantial amount" +
                    " of calls with this exception, check the RCM logs for Logger disconnections (Logger down) and if " +
                    "channels were allocated after connection reestablished with the Logger. Relevant for voice and screen resources."),
    Unspecified_recording_failure(9,
            "Unspecified recording failure.",
            "Unknown cause of failure. Received from the RCM.",
            "Collect the Integration and CLS log files."),
    Agent_logout_during_call(10,
            "Agent logout during the call.",
            "Problem with the driver or PABX call or logout reports.",
            "Check if it is physically possible to logout during a call. Collect the Integration and CLS log files"),
    Too_many_calls_for_agent_extension(11,
            "MaxExtentionOpenCall - too many open calls for the same agent/extension when compared to the op_MaxCallPerExt registry parameter.",
            "The agent had more simultaneous open segments than the parameter value. Could be a problem with the driver or PABX call reports.",
            "Check if the parameter value meets the requirements of the site. Look for calls with exp02 to see if there " +
                    "is a problem with the call reports."),
    Voice_recording_failed(12,
            "Voice recording failed.",
            "Error code received from the telephony server or if RCM was down on start of Call. If this appears as e12 " +
                    "check the sub-exception ID for more information.\n(Mostly caused by \"no audio\" & \"Logger down\")",
            "Collect the Integration and CLS log files.\nSub-exception 12 310 indicates the Logger is down. \n Sub-exception" +
                    " 12 1702 indicates no VoIP audio due to a configuration problem (Increasing CheckAudioDelay RCM parameter" +
                    " or disable EnableAudioCheck). This is dependant on the VoIP integration. Check if this is a compound" +
                    " scenario (transfer/conference). Check if there was hold/retrieve scenario during the call " +
                    "(indication only on Driver logs).\nCheck the configuration, the forwarding data passed to the Capture" +
                    " (RCM logs), the Capture logs, as well as the forwarding device configuration. For example, no audio " +
                    "is received if no or the wrong forwarding information is passed to Capture, or if the audio is not " +
                    "forwarded by the telephony switch/forwarding device." ),
    Screen_Logger_not_responding(13,
            "Not relevant after Release 3 SP3. Screen Logger not responding.",
            "Logger is down or network issue.",
            "Check the NiceScreen Logger and ScreenAgent. Check the RCM logs for errors when calling screen capture to start record."),
    Screen_recording_failed(14,
            "Screen recording failed.",
            "An error code that might indicate RCM was down on start of Call. If this appears as e14 check the sub-exception ID for more information.",
            "Check the NiceScreen Logger.\n" +
                    "Check the RCM logs. Sub-exception 14 310 indicates logger is down. Sub-exception 14 1002 indicates an error was received during screen recording.\n" +
                    "Either ScreenAgent was disconnected from the Interactions Center or ScreenAgent recording failed. " +
                        "Partial recording may be available until the time of the error. Sub-exception 144 indicates there was an unspecified failure."),
    Unmapped_voice_recording(15,
            "Unmapped voice recording.",
            "Problem in the voice channel configuration.",
            "Switch the logs to DEBUG. \n" +
                    "Check channels configuration. See if there is a mapping configured for this call.\n" +
                    " Check the RCM logs. Check what the RCM received in the Start request."),
    Unmapped_screen_recording(16,
            "Unmapped screen recording.",
            "The recording request was received with empty Station or IP address (depends on the screen allocation mode).\n" +
                    "Usually occurs due to screen agent not reporting its IP to the IC. Another option is that the screen agent's " +
                    "IP wasn't mapped properly in Channel Mapping.",
            "If the allocation mode is by IP address then check if the agent logged in. Check ports. \n" +
                    "Verify in RCM logs that the start request for recording the agent's screen contained the screen agent's IP."),
    Voice_recording_retry(17,
            "Recording voice succeeded only after retry (partial retry).",
            "May be a temporary failure on the Logger.",
            "Check Logger for possible reasons for temporary failures."),
    Call_Server_service_shutdown(18,
            "Call Server was down during the call.",
            "Call Server was down. ",
            "Check reason for Call Server failure. Collect CLS logs, event viewer and CPU Performance Monitor."),
    Logger_not_responding(20,
            "The Logger did not respond to the start record command.",
            "Stop record command arrived before a response for the start record request arrived. This could occur for one " +
                    "of the following reasons: \nThe call was very short 1 or 2 seconds). \n The request was for 2 medias." +
                    " \n Success in both was required (usually QA). \n One media failed immediately. \n Stop record is " +
                    "sent for both medias.",
            "No action is needed if there was indeed a short call; otherwise collect CLS and driver log files. If the " +
                    "request was for 2 medias, try to understand the recording problem with the first media. Check the " +
                    "RCM logs. Check the Call Server logs."),
    Error_in_stop_record_request(24,
            "Stop record with wrong ID. No start call request with this CLS Call ID was found.",
            "Usually happens with very short calls. The request was for 2 medias. Success in both was required (usually QA)." +
                    " One media failed immediately. Stop record was sent for both medias. Or may be an internal problem.",
            "Same as 23. "),
    Too_many_requests_for_channel(25,
            "Too many recording requests for the same Logger and channel (more than 30).",
            "May occur due to a problem with the driver or PABX call reports - calls are not closed.",
            "Collect CLS and driver log files. Check the Call Server logs."),
    RCM_service_down_during_call(26,
            "RCM was down during the call.",
            "RCM was down. ",
            "RCM was down. "),
    Error_on_complete_interaction_start(27,
            "The contact started after its segment.",
            "Problem in the driver, PABX call, or logout reports.",
            "Check the driver or PABX. Collect CLS and driver log files. Check the RCM logs for a long period in DEBUG mode."),

    Error_on_complete_interaction_close(29,
            "Segment was open when the contact closed.",
            "Could be a problem with the drivers or PABX call reports.",
            "Check the driver or PABX. Collect CLS and driver log files."),
    Time_Interval_recording_aborted(30,
            "Block dummy call was closed due to a Call Server restart.",
            "Call Server restarted. ",
            "Collect CLS log files. Check why Call Server was closed."),
    Stop_on_demand_not_by_initiator(33,
            "Stop on demand was performed on the interaction recording by a client who was not the recording initiator.",
            "Stop on demand was performed on the interaction recording by a client who was not the recording initiator.",
            "No action required."),
    Invalid_call_time_report(34,
            "Time field was changed by the DB Server. Interaction was inserted with time value lower than 1970. Stop time" +
                    " was lower than start time. ",
            "Call Server error. Illegal time parameters were reported.",
            "Collect CLS log files. Check the Call Server logs.");

    private final int exceptionTypeID;
    private final String exceptionDescription;
    private final String exceptionPossibleCause;
    private final String exceptionRecommendedAction;



    private ExceptionType(int exceptionTypeID, String exceptionDescription, String exceptionPossibleCause, String exceptionRecommendedAction){
        this.exceptionTypeID = exceptionTypeID;
        this.exceptionDescription = exceptionDescription;
        this.exceptionPossibleCause = exceptionPossibleCause;
        this.exceptionRecommendedAction = exceptionRecommendedAction;
    }

    public int exceptionTypeID(){
        return exceptionTypeID;
    }

    public String exceptionDescription(){
        return exceptionDescription;
    }

    public String exceptionPossibleCause(){
        return exceptionPossibleCause;
    }

    public String exceptionRecommendedAction(){
        return exceptionRecommendedAction;
    }

    public static ExceptionType getRandomExcetionType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}