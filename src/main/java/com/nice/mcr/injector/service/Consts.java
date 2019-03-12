package com.nice.mcr.injector.service;

import java.util.Random;

public class Consts {
    static final String INTERACTION_ID = "iInteractionID";
    static final String INTERACTION_LOCAL_START_TIME = "dtInteractionLocalStartTime";
    static final String INTERACTION_LOCAL_STOP_TIME = "dtInteractionLocalStopTime";
    static final String INTERACTION_GMT_START_TIME = "dtInteractionGMTStartTime";
    static final String INTERACTION_GMT_STOP_TIME = "dtInteractionGMTStopTime";
    static final String INTERACTION_DURATION = "biInteractionDuration";
    static final String INTERACTION_OPEN_REASON_ID = "iInteractionOpenReasonID";
    static final String INTERACTION_CLOSE_REASON_ID = "iInteractionCloseReasonID";
    static final String SWITCH_ID = "iSwitchID";
    static final String INITIATOR_USER_ID = "iInitiatorUserID";
    static final String OTHER_SWITCH_ID = "iOtherSwitchID";
    static final String INTERACTION_QA_TYPE_ID = "bitInteractionQATypeID";
    static final String INTERACTION_TYPE_ID = "tiInteractionTypeID";
    static final String INTERACTION_RECORDED_TYPE_ID = "tiInteractionRecordedTypeID";
    static final String IS_EVALUATED = "bitIsEvaluated";
    static final String IS_CUSTOMER_EVALUATED = "bitIsCustomerEvaluated";
    static final String MEDIA_TYPES_ID = "iMediaTypesId";
    static final String INTERACTION_TYPE_DESC = "vcInteractionDesc";
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
    static final String PBX_CALL_ID = "iPBXCallID";
    static final String EXTERNAL_CALL_ID = "iExternalCallId";
    static final String CALL_DIRECTION_TYPE_ID = "tiCallDirectionTypeID";
    static final String VECTOR_NUMBER = "vcVectorNumber";
    static final String PBX_UNIVARSAL_CALL_INTERACTION_ID = "vcPBXUniversalCallInteractionID";
    static final String COMPOUND_ID = "iCompoundID";
    static final String NDC_BUSINESS_DATA = "nvcBusinessData";
    static final String BIT_IS_PLAYBACK_CALL = "bitIsPlaybackCall";
    static final String PARTICIPANT_ID = "iParticipantID";
    static final String STATION = "nvcStation";
    static final String PHONE_NUMBER = "nvcPhoneNumber";
    static final String AGENT_ID = "nvcAgentId";
    static final String USER_ID = "iUserID";
    static final String FIRST_USER = "bitIsFirstUser";
    static final String IS_INERACTION_INITIATOR = "bitIsInteractionInitiator";
    static final String DEVICE_TYPE_ID = "tiDeviceTypeID";
    static final String DEVICE_ID = "iDeviceID";
    static final String CTI_AGENT_NAME = "nvcCTIAgentName";
    static final String DEPARTMENT = "nvcDepartement";
    static final String TRUNK_GROUP = "vcTrunkGroup";
    static final String TRUNK_NUMBER = "vcTrunkNumber";
    static final String TRUNK_LABEL = "nvcTrunkLabel";
    static final String DIALED_NUMBER = "nvcDialedNumber";
    static final String CLIENT_ID = "iClientID";
    static final String VIRTUAL_DEVICE_ID = "iVirtualDeviceID";
    static final String PARTICIPANT_TYPE_ID = "tiParticipantTypeID";
    static final String PARTICIPANT_TYPE_DESC = "vcParticipantTypeDesc";
    static final String OPEN_REASON_DESC = "vcOpenReasonDesc";
    static final String CLOSE_REASON_DESC = "vcCloseReasonDesc";
    static final String DEVICE_TYPE_DESC = "vcDeviceTypeDesc";
    static final String RECORDING_SIDE_TYPE_ID = "tiRecordingSideTypeID";
    static final String RECORDING_SIDE_DESC = "vcRecordingSideDesc";
    static final String MEDIA_DESC = "vcMediaDesc";
    static final String DIRECTION_TYPE_ID = "tiDirectionTypeID";
    static final String DIRECTION_TYPE_DESC = "vcDirectionTypeDesc";
    static final String RECORDING_ID = "iRecordingID";
    static final String LOGGER = "iLogger";
    static final String CHANNEL = "iChannel";
    static final String MML_RECORDING_HINT = "vcMmlRecordingHint";
    static final String RECORDING_GMT_START_TIME = "dtRecordingGMTStartTime";
    static final String RECORDING_GMT_STOP_TIME = "dtRecordingGMTStopTime";
    static final String RECORDING_RECORDED_TYPE_ID = "tiRecordingRecordedTypeID";
    static final String PROGRAM_ID = "iProgramID";
    static final String RECORDED_PARTICIPANT_ID = "iRecordedParticipantID";
    static final String TIME_DIFF = "biTimeDiff";
    static final String WRAPUP_TIME = "biWrapupTime";
    static final String SESSION_ID = "biSessionID";
    static final String ITEM_DATA_TYPE_ID = "tiItemDataTypeID";
    static final String ITEM_DATA_TYPE_DESC = "iItemDataTypeDesc";
    static final String CREATOR_TYPE_ID = "tiCreatorTypeID";
    static final String CREATOR_TYPE_DESC = "vcCreatorDesc";
    static final String ITEM_TYPE_ID = "iItemTypeID";
    static final String ITEM_TYPE_DESC = "nvcItemTypeDesc";
    static final String CONTACT_ID = "iContactID";
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
    static final String CONTACT_GMT_START_TIME = "dtContactGMTStartTime";
    static final String CONTACT_GMT_STOP_TIME = "dtContactGMTStopTime";
    static final String CONTACT_DURATION = "biContactDuration";
    static final String CONTACT_OPEN_REASON_ID = "iContactOpenReasonID";
    static final String CONTACT_CLOSE_REASON_ID = "iContactCloseReasonID";
    static final String TRANSFER_SITE_ID = "iTransferSiteID";
    static final String TRANSFER_CONTACT_ID = "iTransferContactID";
    static final String CONTACT_RECORDED_TYPE_ID = "tiContactRecordedTypeID";
    static final String CONTACT_QA_TYPE_ID = "bitContactQATypeID";
    static final String CONTACT_DIRECTION_TYPE_ID = "tiContactDirectionTypeID";
    static final String EXCEPTION_TYPE_ID = "iExceptionTypeID";
    static final String EXCEPTION_POSSIBLE_CAUSE = "nvcPossibleCause";
    static final String EXCEPTION_RECOMMENDED_ACTION = "nvcRecomendedAction";
    static final String EXCEPTION_NUMBER = "iExceptionNumber";
    static final String EXCEPTION_TIMESTAMP = "dtExceptionTimeStamp";
    static final String EXCEPTION_DETAIL = "vcExceptionDetail";
    static final String CREATED_BY_ID = "iCreatedByID";
    static final String CLIP_NAME = "nvcClipName";
    static final String CATEGORY_ID = "iCategoryID";
    static final String IS_PUBLISHED = "bIsPublished";
    static final String IS_PUBLIC = "bIsPublic";
    static final String CLIP_DURATION = "biClipDuration";
    static final String IS_DELETED = "bIsDeleted";
    static final String I_INTERACTION_TYPE_ID = "iInteractionTypeID";
    static final String TASK_ID = "iTaskID";
    static final String FLAG_ID = "iFlagID";
    static final String USER_SITE_ID = "iUserSiteID";
    static final String MODIFICATION_TIME = "dtModificationTime";

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

enum RecordingRecordedType {
    Unknown(0),
    Yes(1),
    No(2),
    Partial(3);

    private final int recordingRecordedTypeID;
    private RecordingRecordedType(int recordingRecordedTypeID){
        this.recordingRecordedTypeID = recordingRecordedTypeID;
    }

    public int RecorderTypeID(){
        return recordingRecordedTypeID;
    }

    public static RecordingRecordedType getRandomRecorderType() {
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
    Party_Address(3002),;

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