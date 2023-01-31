// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: CmdId.proto

package emu.grasscutter.net.proto;

public final class CmdIdOuterClass {
  private CmdIdOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  /**
   * Protobuf enum {@code CmdId}
   */
  public enum CmdId
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>SUCC = 0;</code>
     */
    SUCC(0),
    /**
     * <code>FAIL = 1;</code>
     */
    FAIL(1),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>SUCC = 0;</code>
     */
    public static final int SUCC_VALUE = 0;
    /**
     * <code>FAIL = 1;</code>
     */
    public static final int FAIL_VALUE = 1;


    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static CmdId valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static CmdId forNumber(int value) {
      switch (value) {
        case 0: return SUCC;
        case 1: return FAIL;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<CmdId>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        CmdId> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<CmdId>() {
            public CmdId findValueByNumber(int number) {
              return CmdId.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalStateException(
            "Can't get the descriptor of an unrecognized enum value.");
      }
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.CmdIdOuterClass.getDescriptor().getEnumTypes().get(0);
    }

    private static final CmdId[] VALUES = values();

    public static CmdId valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private CmdId(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:CmdId)
  }


  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\013CmdId.proto*\033\n\005CmdId\022\010\n\004SUCC\020\000\022\010\n\004FAIL" +
      "\020\001B\033\n\031emu.grasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
