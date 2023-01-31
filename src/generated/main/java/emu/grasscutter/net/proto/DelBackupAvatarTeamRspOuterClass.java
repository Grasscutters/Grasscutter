// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: DelBackupAvatarTeamRsp.proto

package emu.grasscutter.net.proto;

public final class DelBackupAvatarTeamRspOuterClass {
  private DelBackupAvatarTeamRspOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface DelBackupAvatarTeamRspOrBuilder extends
      // @@protoc_insertion_point(interface_extends:DelBackupAvatarTeamRsp)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 backupAvatarTeamId = 11;</code>
     * @return The backupAvatarTeamId.
     */
    int getBackupAvatarTeamId();

    /**
     * <code>int32 retcode = 12;</code>
     * @return The retcode.
     */
    int getRetcode();
  }
  /**
   * <pre>
   *enum HMFPBAACAFA {
   *	option allow_alias= true;
   *	NONE = 0;
   *	PEPPOHPHJOJ = 1756;
   *	DCDNILFDFLB = 0;
   *	NNBKOLMPOEA = 1;
   *}
   * </pre>
   *
   * Protobuf type {@code DelBackupAvatarTeamRsp}
   */
  public static final class DelBackupAvatarTeamRsp extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:DelBackupAvatarTeamRsp)
      DelBackupAvatarTeamRspOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use DelBackupAvatarTeamRsp.newBuilder() to construct.
    private DelBackupAvatarTeamRsp(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private DelBackupAvatarTeamRsp() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new DelBackupAvatarTeamRsp();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private DelBackupAvatarTeamRsp(
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
            case 88: {

              backupAvatarTeamId_ = input.readUInt32();
              break;
            }
            case 96: {

              retcode_ = input.readInt32();
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
      return emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.internal_static_DelBackupAvatarTeamRsp_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.internal_static_DelBackupAvatarTeamRsp_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp.class, emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp.Builder.class);
    }

    public static final int BACKUPAVATARTEAMID_FIELD_NUMBER = 11;
    private int backupAvatarTeamId_;
    /**
     * <code>uint32 backupAvatarTeamId = 11;</code>
     * @return The backupAvatarTeamId.
     */
    @java.lang.Override
    public int getBackupAvatarTeamId() {
      return backupAvatarTeamId_;
    }

    public static final int RETCODE_FIELD_NUMBER = 12;
    private int retcode_;
    /**
     * <code>int32 retcode = 12;</code>
     * @return The retcode.
     */
    @java.lang.Override
    public int getRetcode() {
      return retcode_;
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
      if (backupAvatarTeamId_ != 0) {
        output.writeUInt32(11, backupAvatarTeamId_);
      }
      if (retcode_ != 0) {
        output.writeInt32(12, retcode_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (backupAvatarTeamId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(11, backupAvatarTeamId_);
      }
      if (retcode_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(12, retcode_);
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
      if (!(obj instanceof emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp other = (emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp) obj;

      if (getBackupAvatarTeamId()
          != other.getBackupAvatarTeamId()) return false;
      if (getRetcode()
          != other.getRetcode()) return false;
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
      hash = (37 * hash) + BACKUPAVATARTEAMID_FIELD_NUMBER;
      hash = (53 * hash) + getBackupAvatarTeamId();
      hash = (37 * hash) + RETCODE_FIELD_NUMBER;
      hash = (53 * hash) + getRetcode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp prototype) {
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
     *enum HMFPBAACAFA {
     *	option allow_alias= true;
     *	NONE = 0;
     *	PEPPOHPHJOJ = 1756;
     *	DCDNILFDFLB = 0;
     *	NNBKOLMPOEA = 1;
     *}
     * </pre>
     *
     * Protobuf type {@code DelBackupAvatarTeamRsp}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:DelBackupAvatarTeamRsp)
        emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRspOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.internal_static_DelBackupAvatarTeamRsp_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.internal_static_DelBackupAvatarTeamRsp_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp.class, emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp.newBuilder()
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
        backupAvatarTeamId_ = 0;

        retcode_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.internal_static_DelBackupAvatarTeamRsp_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp build() {
        emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp buildPartial() {
        emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp result = new emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp(this);
        result.backupAvatarTeamId_ = backupAvatarTeamId_;
        result.retcode_ = retcode_;
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
        if (other instanceof emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp) {
          return mergeFrom((emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp other) {
        if (other == emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp.getDefaultInstance()) return this;
        if (other.getBackupAvatarTeamId() != 0) {
          setBackupAvatarTeamId(other.getBackupAvatarTeamId());
        }
        if (other.getRetcode() != 0) {
          setRetcode(other.getRetcode());
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
        emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int backupAvatarTeamId_ ;
      /**
       * <code>uint32 backupAvatarTeamId = 11;</code>
       * @return The backupAvatarTeamId.
       */
      @java.lang.Override
      public int getBackupAvatarTeamId() {
        return backupAvatarTeamId_;
      }
      /**
       * <code>uint32 backupAvatarTeamId = 11;</code>
       * @param value The backupAvatarTeamId to set.
       * @return This builder for chaining.
       */
      public Builder setBackupAvatarTeamId(int value) {
        
        backupAvatarTeamId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 backupAvatarTeamId = 11;</code>
       * @return This builder for chaining.
       */
      public Builder clearBackupAvatarTeamId() {
        
        backupAvatarTeamId_ = 0;
        onChanged();
        return this;
      }

      private int retcode_ ;
      /**
       * <code>int32 retcode = 12;</code>
       * @return The retcode.
       */
      @java.lang.Override
      public int getRetcode() {
        return retcode_;
      }
      /**
       * <code>int32 retcode = 12;</code>
       * @param value The retcode to set.
       * @return This builder for chaining.
       */
      public Builder setRetcode(int value) {
        
        retcode_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 retcode = 12;</code>
       * @return This builder for chaining.
       */
      public Builder clearRetcode() {
        
        retcode_ = 0;
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


      // @@protoc_insertion_point(builder_scope:DelBackupAvatarTeamRsp)
    }

    // @@protoc_insertion_point(class_scope:DelBackupAvatarTeamRsp)
    private static final emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp();
    }

    public static emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<DelBackupAvatarTeamRsp>
        PARSER = new com.google.protobuf.AbstractParser<DelBackupAvatarTeamRsp>() {
      @java.lang.Override
      public DelBackupAvatarTeamRsp parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new DelBackupAvatarTeamRsp(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<DelBackupAvatarTeamRsp> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<DelBackupAvatarTeamRsp> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_DelBackupAvatarTeamRsp_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DelBackupAvatarTeamRsp_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\034DelBackupAvatarTeamRsp.proto\"E\n\026DelBac" +
      "kupAvatarTeamRsp\022\032\n\022backupAvatarTeamId\030\013" +
      " \001(\r\022\017\n\007retcode\030\014 \001(\005B\033\n\031emu.grasscutter" +
      ".net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_DelBackupAvatarTeamRsp_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_DelBackupAvatarTeamRsp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_DelBackupAvatarTeamRsp_descriptor,
        new java.lang.String[] { "BackupAvatarTeamId", "Retcode", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
