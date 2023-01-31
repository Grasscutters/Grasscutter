// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: CustomDungeonBattleRecordNotify.proto

package emu.grasscutter.net.proto;

public final class CustomDungeonBattleRecordNotifyOuterClass {
  private CustomDungeonBattleRecordNotifyOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface CustomDungeonBattleRecordNotifyOrBuilder extends
      // @@protoc_insertion_point(interface_extends:CustomDungeonBattleRecordNotify)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint64 dungeonGuid = 13;</code>
     * @return The dungeonGuid.
     */
    long getDungeonGuid();

    /**
     * <code>uint32 minCostTime = 12;</code>
     * @return The minCostTime.
     */
    int getMinCostTime();
  }
  /**
   * <pre>
   *enum HIDDKGJANKE {
   *	option allow_alias= true;
   *	NONE = 0;
   *	PEPPOHPHJOJ = 6201;
   *	DCDNILFDFLB = 0;
   *	NNBKOLMPOEA = 1;
   *	EAJIABGAOCI = 1;
   *}
   * </pre>
   *
   * Protobuf type {@code CustomDungeonBattleRecordNotify}
   */
  public static final class CustomDungeonBattleRecordNotify extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:CustomDungeonBattleRecordNotify)
      CustomDungeonBattleRecordNotifyOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use CustomDungeonBattleRecordNotify.newBuilder() to construct.
    private CustomDungeonBattleRecordNotify(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private CustomDungeonBattleRecordNotify() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new CustomDungeonBattleRecordNotify();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private CustomDungeonBattleRecordNotify(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 96: {

              minCostTime_ = input.readUInt32();
              break;
            }
            case 104: {

              dungeonGuid_ = input.readUInt64();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.internal_static_CustomDungeonBattleRecordNotify_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.internal_static_CustomDungeonBattleRecordNotify_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify.class, emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify.Builder.class);
    }

    public static final int DUNGEONGUID_FIELD_NUMBER = 13;
    private long dungeonGuid_;
    /**
     * <code>uint64 dungeonGuid = 13;</code>
     * @return The dungeonGuid.
     */
    @java.lang.Override
    public long getDungeonGuid() {
      return dungeonGuid_;
    }

    public static final int MINCOSTTIME_FIELD_NUMBER = 12;
    private int minCostTime_;
    /**
     * <code>uint32 minCostTime = 12;</code>
     * @return The minCostTime.
     */
    @java.lang.Override
    public int getMinCostTime() {
      return minCostTime_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (minCostTime_ != 0) {
        output.writeUInt32(12, minCostTime_);
      }
      if (dungeonGuid_ != 0L) {
        output.writeUInt64(13, dungeonGuid_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (minCostTime_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(12, minCostTime_);
      }
      if (dungeonGuid_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt64Size(13, dungeonGuid_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify other = (emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify) obj;

      if (getDungeonGuid()
          != other.getDungeonGuid()) return false;
      if (getMinCostTime()
          != other.getMinCostTime()) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + DUNGEONGUID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getDungeonGuid());
      hash = (37 * hash) + MINCOSTTIME_FIELD_NUMBER;
      hash = (53 * hash) + getMinCostTime();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     *enum HIDDKGJANKE {
     *	option allow_alias= true;
     *	NONE = 0;
     *	PEPPOHPHJOJ = 6201;
     *	DCDNILFDFLB = 0;
     *	NNBKOLMPOEA = 1;
     *	EAJIABGAOCI = 1;
     *}
     * </pre>
     *
     * Protobuf type {@code CustomDungeonBattleRecordNotify}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:CustomDungeonBattleRecordNotify)
        emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotifyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.internal_static_CustomDungeonBattleRecordNotify_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.internal_static_CustomDungeonBattleRecordNotify_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify.class, emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        dungeonGuid_ = 0L;

        minCostTime_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.internal_static_CustomDungeonBattleRecordNotify_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify build() {
        emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify buildPartial() {
        emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify result = new emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify(this);
        result.dungeonGuid_ = dungeonGuid_;
        result.minCostTime_ = minCostTime_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify) {
          return mergeFrom((emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify other) {
        if (other == emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify.getDefaultInstance()) return this;
        if (other.getDungeonGuid() != 0L) {
          setDungeonGuid(other.getDungeonGuid());
        }
        if (other.getMinCostTime() != 0) {
          setMinCostTime(other.getMinCostTime());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long dungeonGuid_ ;
      /**
       * <code>uint64 dungeonGuid = 13;</code>
       * @return The dungeonGuid.
       */
      @java.lang.Override
      public long getDungeonGuid() {
        return dungeonGuid_;
      }
      /**
       * <code>uint64 dungeonGuid = 13;</code>
       * @param value The dungeonGuid to set.
       * @return This builder for chaining.
       */
      public Builder setDungeonGuid(long value) {
        
        dungeonGuid_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint64 dungeonGuid = 13;</code>
       * @return This builder for chaining.
       */
      public Builder clearDungeonGuid() {
        
        dungeonGuid_ = 0L;
        onChanged();
        return this;
      }

      private int minCostTime_ ;
      /**
       * <code>uint32 minCostTime = 12;</code>
       * @return The minCostTime.
       */
      @java.lang.Override
      public int getMinCostTime() {
        return minCostTime_;
      }
      /**
       * <code>uint32 minCostTime = 12;</code>
       * @param value The minCostTime to set.
       * @return This builder for chaining.
       */
      public Builder setMinCostTime(int value) {
        
        minCostTime_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 minCostTime = 12;</code>
       * @return This builder for chaining.
       */
      public Builder clearMinCostTime() {
        
        minCostTime_ = 0;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:CustomDungeonBattleRecordNotify)
    }

    // @@protoc_insertion_point(class_scope:CustomDungeonBattleRecordNotify)
    private static final emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify();
    }

    public static emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<CustomDungeonBattleRecordNotify>
        PARSER = new com.google.protobuf.AbstractParser<CustomDungeonBattleRecordNotify>() {
      @java.lang.Override
      public CustomDungeonBattleRecordNotify parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new CustomDungeonBattleRecordNotify(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<CustomDungeonBattleRecordNotify> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<CustomDungeonBattleRecordNotify> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.CustomDungeonBattleRecordNotifyOuterClass.CustomDungeonBattleRecordNotify getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CustomDungeonBattleRecordNotify_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CustomDungeonBattleRecordNotify_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n%CustomDungeonBattleRecordNotify.proto\"" +
      "K\n\037CustomDungeonBattleRecordNotify\022\023\n\013du" +
      "ngeonGuid\030\r \001(\004\022\023\n\013minCostTime\030\014 \001(\rB\033\n\031" +
      "emu.grasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_CustomDungeonBattleRecordNotify_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_CustomDungeonBattleRecordNotify_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CustomDungeonBattleRecordNotify_descriptor,
        new java.lang.String[] { "DungeonGuid", "MinCostTime", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
