// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ShareCDInfo.proto

package emu.grasscutter.net.proto;

public final class ShareCDInfoOuterClass {
  private ShareCDInfoOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ShareCDInfoOrBuilder extends
      // @@protoc_insertion_point(interface_extends:ShareCDInfo)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 shareCdId = 13;</code>
     * @return The shareCdId.
     */
    int getShareCdId();

    /**
     * <code>uint64 cdStartTime = 1;</code>
     * @return The cdStartTime.
     */
    long getCdStartTime();

    /**
     * <code>uint32 index = 11;</code>
     * @return The index.
     */
    int getIndex();
  }
  /**
   * Protobuf type {@code ShareCDInfo}
   */
  public static final class ShareCDInfo extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:ShareCDInfo)
      ShareCDInfoOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use ShareCDInfo.newBuilder() to construct.
    private ShareCDInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ShareCDInfo() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new ShareCDInfo();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private ShareCDInfo(
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
            case 8: {

              cdStartTime_ = input.readUInt64();
              break;
            }
            case 88: {

              index_ = input.readUInt32();
              break;
            }
            case 104: {

              shareCdId_ = input.readUInt32();
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
      return emu.grasscutter.net.proto.ShareCDInfoOuterClass.internal_static_ShareCDInfo_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.ShareCDInfoOuterClass.internal_static_ShareCDInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo.class, emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo.Builder.class);
    }

    public static final int SHARECDID_FIELD_NUMBER = 13;
    private int shareCdId_;
    /**
     * <code>uint32 shareCdId = 13;</code>
     * @return The shareCdId.
     */
    @java.lang.Override
    public int getShareCdId() {
      return shareCdId_;
    }

    public static final int CDSTARTTIME_FIELD_NUMBER = 1;
    private long cdStartTime_;
    /**
     * <code>uint64 cdStartTime = 1;</code>
     * @return The cdStartTime.
     */
    @java.lang.Override
    public long getCdStartTime() {
      return cdStartTime_;
    }

    public static final int INDEX_FIELD_NUMBER = 11;
    private int index_;
    /**
     * <code>uint32 index = 11;</code>
     * @return The index.
     */
    @java.lang.Override
    public int getIndex() {
      return index_;
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
      if (cdStartTime_ != 0L) {
        output.writeUInt64(1, cdStartTime_);
      }
      if (index_ != 0) {
        output.writeUInt32(11, index_);
      }
      if (shareCdId_ != 0) {
        output.writeUInt32(13, shareCdId_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (cdStartTime_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt64Size(1, cdStartTime_);
      }
      if (index_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(11, index_);
      }
      if (shareCdId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(13, shareCdId_);
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
      if (!(obj instanceof emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo other = (emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo) obj;

      if (getShareCdId()
          != other.getShareCdId()) return false;
      if (getCdStartTime()
          != other.getCdStartTime()) return false;
      if (getIndex()
          != other.getIndex()) return false;
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
      hash = (37 * hash) + SHARECDID_FIELD_NUMBER;
      hash = (53 * hash) + getShareCdId();
      hash = (37 * hash) + CDSTARTTIME_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getCdStartTime());
      hash = (37 * hash) + INDEX_FIELD_NUMBER;
      hash = (53 * hash) + getIndex();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo prototype) {
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
     * Protobuf type {@code ShareCDInfo}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:ShareCDInfo)
        emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.ShareCDInfoOuterClass.internal_static_ShareCDInfo_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.ShareCDInfoOuterClass.internal_static_ShareCDInfo_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo.class, emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo.newBuilder()
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
        shareCdId_ = 0;

        cdStartTime_ = 0L;

        index_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.ShareCDInfoOuterClass.internal_static_ShareCDInfo_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo build() {
        emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo buildPartial() {
        emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo result = new emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo(this);
        result.shareCdId_ = shareCdId_;
        result.cdStartTime_ = cdStartTime_;
        result.index_ = index_;
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
        if (other instanceof emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo) {
          return mergeFrom((emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo other) {
        if (other == emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo.getDefaultInstance()) return this;
        if (other.getShareCdId() != 0) {
          setShareCdId(other.getShareCdId());
        }
        if (other.getCdStartTime() != 0L) {
          setCdStartTime(other.getCdStartTime());
        }
        if (other.getIndex() != 0) {
          setIndex(other.getIndex());
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
        emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int shareCdId_ ;
      /**
       * <code>uint32 shareCdId = 13;</code>
       * @return The shareCdId.
       */
      @java.lang.Override
      public int getShareCdId() {
        return shareCdId_;
      }
      /**
       * <code>uint32 shareCdId = 13;</code>
       * @param value The shareCdId to set.
       * @return This builder for chaining.
       */
      public Builder setShareCdId(int value) {
        
        shareCdId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 shareCdId = 13;</code>
       * @return This builder for chaining.
       */
      public Builder clearShareCdId() {
        
        shareCdId_ = 0;
        onChanged();
        return this;
      }

      private long cdStartTime_ ;
      /**
       * <code>uint64 cdStartTime = 1;</code>
       * @return The cdStartTime.
       */
      @java.lang.Override
      public long getCdStartTime() {
        return cdStartTime_;
      }
      /**
       * <code>uint64 cdStartTime = 1;</code>
       * @param value The cdStartTime to set.
       * @return This builder for chaining.
       */
      public Builder setCdStartTime(long value) {
        
        cdStartTime_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint64 cdStartTime = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearCdStartTime() {
        
        cdStartTime_ = 0L;
        onChanged();
        return this;
      }

      private int index_ ;
      /**
       * <code>uint32 index = 11;</code>
       * @return The index.
       */
      @java.lang.Override
      public int getIndex() {
        return index_;
      }
      /**
       * <code>uint32 index = 11;</code>
       * @param value The index to set.
       * @return This builder for chaining.
       */
      public Builder setIndex(int value) {
        
        index_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 index = 11;</code>
       * @return This builder for chaining.
       */
      public Builder clearIndex() {
        
        index_ = 0;
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


      // @@protoc_insertion_point(builder_scope:ShareCDInfo)
    }

    // @@protoc_insertion_point(class_scope:ShareCDInfo)
    private static final emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo();
    }

    public static emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ShareCDInfo>
        PARSER = new com.google.protobuf.AbstractParser<ShareCDInfo>() {
      @java.lang.Override
      public ShareCDInfo parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ShareCDInfo(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ShareCDInfo> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ShareCDInfo> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.ShareCDInfoOuterClass.ShareCDInfo getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ShareCDInfo_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ShareCDInfo_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021ShareCDInfo.proto\"D\n\013ShareCDInfo\022\021\n\tsh" +
      "areCdId\030\r \001(\r\022\023\n\013cdStartTime\030\001 \001(\004\022\r\n\005in" +
      "dex\030\013 \001(\rB\033\n\031emu.grasscutter.net.protob\006" +
      "proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_ShareCDInfo_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ShareCDInfo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ShareCDInfo_descriptor,
        new java.lang.String[] { "ShareCdId", "CdStartTime", "Index", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
