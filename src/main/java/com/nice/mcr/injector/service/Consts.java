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
    static final String INTERACTION_RECORDD_TYPE_ID = "tiInteractionRecordedTypeID";
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
    static final String DAILED_NUMBER = "nvcDialedNumber";
    static final String CLIENT_ID = "iClientID";
    static final String VIRTUAL_DEVICE_ID = "iVirtualDeviceID";
    static final String PARTICIPANT_TYPE_ID = "tiParticipantTypeID";
    static final String PARTICIPANT_TYPE_DESC = "vcParticipantTypeDesc";
    static final String OPEN_REASON_TYPE = "iOpenReasonTypeID";
    static final String OPEN_REASON_DESC = "vcOpenReasonDesc";
    static final String CLOSE_REASON_TYPE = "iCloseReasonTypeID";
    static final String CLOSE_REASON_DESC = "vcCloseReasonDesc";
    static final String DEVICE_TYPE_DESC = "vcDeviceTypeDesc";
    static final String RECORDING_SIDE_TYPE_ID = "tiRecordingSideTypeID";
    static final String RECORDING_SIDE_DESC = "vcRecordingSideDesc";
    static final String MEDIA_TYPE_ID = "iMediaTypeId";
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
    static final String RECORDING_PARTICIPANT_ID = "iRecordedParticipantID";
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
    static final String ITEM_UDER_ID = "iItemUserID";
    static final String ITEM_VALUE = "nvcItemValue";
    static final String IS_DELETED = "bitIsDeleted";

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

