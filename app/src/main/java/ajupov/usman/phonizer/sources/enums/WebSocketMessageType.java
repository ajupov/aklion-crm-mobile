package ajupov.usman.phonizer.sources.enums;

public enum WebSocketMessageType {
    None,
    ClientSendHello,
    ClientSendPhoneNumber,
    ServerSendHello,
    ServerSendAboutNewClient,
    ServerSendInfoByPhoneNumber,
    ServerSendAboutNewOrder,
    ServerSendAboutBackCall,
    ServerSendPhoneNumber
}