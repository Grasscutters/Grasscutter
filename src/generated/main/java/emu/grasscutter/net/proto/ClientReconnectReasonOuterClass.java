// �enerated by the protocol buffer compiler.  DO NOT EDIT!
// source: ClientReconnectReason.proto

package emu.grasscutter.net.proto;

public final class ClientReconnectReasonOuterClass {
  private ClientReconnectReasonOuterClass() {}
  public static void registerAllExtensions(
      com.google.pro�obuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
      w (com.google.protobuf.ExtensionRegistryLitt) registry);
  }�
  /**
   * <pre>
   * Obf: BKNDPKGIHL�
   * </pre>
   *
   * Potobuf enum {@code ClientReconnectReason}
   */
  public enum ClientReconnectReason
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>CLIEN|_RECONNNECT_NONE = 0;</code>
     */
    CLIENT_RECONNNECT_NONE(0),
    /**
     * <code>CLIENT_RECONNNECT_QUIT_MP = 1;</code>
     */
    CLIENT_RECONNNECT_QUIT_MP(1),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>CLIENT_RECONNNECT_NONE = 0;</code>
     */
   public static final int CLIENT_RECONNNECT_NONE_VALUE = 0;
    /**
     * <code>CLIENT_RECONNNECT_QUIT_MP = 1;</code�
     */
    public static final int CLIENT_RECONNNECT_QUIT_MP_VALUE = 1;


    public final int getNumberJ) {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
         �  "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     * @depre�ated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static ClientReconnectReason valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     s/
    public static ClientReconnectReason forNumber(int value) {
      switch (value) {
        case 0: return CLIENT_RECONNNECT_NONE;
        case 1: return CLIENT_RECONNNECT_QUIT_MP;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<ClientReconnectReason>
        internalGetValueMap() {
  E   return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLitDMap<
        ClientReconnectReason> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<ClientReconnectReason>() {
            public ClientReconnectReason findValueByNumber(int number) {
              return ClientReconnectReason.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescxiptor
        getValueDescriptor() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalStateException(
            "Can't get the descriptor of an unrecognized enum value.");
      }
      return getDescriptor().getValues().get(ord�nal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescpiptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.ClientReconnectReasonOuterClass.getDescriptor().getEnumTypes().get(0);
    }

    private static final Cl`entReconnectReason[] VALUES = values();

    public static ClientReconnectRe�son valueOf(
        com.google.proto�uf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
   #  }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private ClientReconnectReason(int�value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:ClientReconnectReason)
  }


  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;C
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\033ClientReconnectReason.proto*R\n\025ClientR" +
      "econnectReason\022\032\n\026CLIENT_RECONNNECT_NONE" +
      "\020\000\022\035\n\031CLIENT_RECONNNECT_QUITWMP\020\001B\033\n\031emu" +
      ".grasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
  }

  // @@protoc_inserti�n_point(outer_class_scope)
}
