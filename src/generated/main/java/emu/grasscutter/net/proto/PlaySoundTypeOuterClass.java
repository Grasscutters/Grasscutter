// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PlaySoundType.proto

package emu.grasscutter.net.proto;

public final class PlaySoundTypeOuterClass {
  private PlaySoundTypeOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  /**
   * Protobuf enum {@code PlaySoundType}
   */
  public enum PlaySoundType
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>PLAY_SOUND_TYPE_NONE = 0;</code>
     */
    PLAY_SOUND_TYPE_NONE(0),
    /**
     * <code>PLAY_SOUND_TYPE_START = 1;</code>
     */
    PLAY_SOUND_TYPE_START(1),
    /**
     * <code>PLAY_SOUND_TYPE_STOP = 2;</code>
     */
    PLAY_SOUND_TYPE_STOP(2),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>PLAY_SOUND_TYPE_NONE = 0;</code>
     */
    public static final int PLAY_SOUND_TYPE_NONE_VALUE = 0;
    /**
     * <code>PLAY_SOUND_TYPE_START = 1;</code>
     */
    public static final int PLAY_SOUND_TYPE_START_VALUE = 1;
    /**
     * <code>PLAY_SOUND_TYPE_STOP = 2;</code>
     */
    public static final int PLAY_SOUND_TYPE_STOP_VALUE = 2;


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
    public static PlaySoundType valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static PlaySoundType forNumber(int value) {
      switch (value) {
        case 0: return PLAY_SOUND_TYPE_NONE;
        case 1: return PLAY_SOUND_TYPE_START;
        case 2: return PLAY_SOUND_TYPE_STOP;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<PlaySoundType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        PlaySoundType> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<PlaySoundType>() {
            public PlaySoundType findValueByNumber(int number) {
              return PlaySoundType.forNumber(number);
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
      return emu.grasscutter.net.proto.PlaySoundTypeOuterClass.getDescriptor().getEnumTypes().get(0);
    }

    private static final PlaySoundType[] VALUES = values();

    public static PlaySoundType valueOf(
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

    private PlaySoundType(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:PlaySoundType)
  }


  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023PlaySoundType.proto*^\n\rPlaySoundType\022\030" +
      "\n\024PLAY_SOUND_TYPE_NONE\020\000\022\031\n\025PLAY_SOUND_T" +
      "YPE_START\020\001\022\030\n\024PLAY_SOUND_TYPE_STOP\020\002B\033\n" +
      "\031emu.grasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
