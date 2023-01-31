// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: HomePictureFrameInfo.proto

package emu.grasscutter.net.proto;

public final class HomePictureFrameInfoOuterClass {
  private HomePictureFrameInfoOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface HomePictureFrameInfoOrBuilder extends
      // @@protoc_insertion_point(interface_extends:HomePictureFrameInfo)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 pictureId = 1;</code>
     * @return The pictureId.
     */
    int getPictureId();

    /**
     * <code>uint32 guid = 8;</code>
     * @return The guid.
     */
    int getGuid();
  }
  /**
   * Protobuf type {@code HomePictureFrameInfo}
   */
  public static final class HomePictureFrameInfo extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:HomePictureFrameInfo)
      HomePictureFrameInfoOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use HomePictureFrameInfo.newBuilder() to construct.
    private HomePictureFrameInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private HomePictureFrameInfo() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new HomePictureFrameInfo();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private HomePictureFrameInfo(
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

              pictureId_ = input.readUInt32();
              break;
            }
            case 64: {

              guid_ = input.readUInt32();
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
      return emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.internal_static_HomePictureFrameInfo_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.internal_static_HomePictureFrameInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo.class, emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo.Builder.class);
    }

    public static final int PICTUREID_FIELD_NUMBER = 1;
    private int pictureId_;
    /**
     * <code>uint32 pictureId = 1;</code>
     * @return The pictureId.
     */
    @java.lang.Override
    public int getPictureId() {
      return pictureId_;
    }

    public static final int GUID_FIELD_NUMBER = 8;
    private int guid_;
    /**
     * <code>uint32 guid = 8;</code>
     * @return The guid.
     */
    @java.lang.Override
    public int getGuid() {
      return guid_;
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
      if (pictureId_ != 0) {
        output.writeUInt32(1, pictureId_);
      }
      if (guid_ != 0) {
        output.writeUInt32(8, guid_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (pictureId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(1, pictureId_);
      }
      if (guid_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(8, guid_);
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
      if (!(obj instanceof emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo other = (emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo) obj;

      if (getPictureId()
          != other.getPictureId()) return false;
      if (getGuid()
          != other.getGuid()) return false;
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
      hash = (37 * hash) + PICTUREID_FIELD_NUMBER;
      hash = (53 * hash) + getPictureId();
      hash = (37 * hash) + GUID_FIELD_NUMBER;
      hash = (53 * hash) + getGuid();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo prototype) {
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
     * Protobuf type {@code HomePictureFrameInfo}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:HomePictureFrameInfo)
        emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.internal_static_HomePictureFrameInfo_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.internal_static_HomePictureFrameInfo_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo.class, emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo.newBuilder()
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
        pictureId_ = 0;

        guid_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.internal_static_HomePictureFrameInfo_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo build() {
        emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo buildPartial() {
        emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo result = new emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo(this);
        result.pictureId_ = pictureId_;
        result.guid_ = guid_;
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
        if (other instanceof emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo) {
          return mergeFrom((emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo other) {
        if (other == emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo.getDefaultInstance()) return this;
        if (other.getPictureId() != 0) {
          setPictureId(other.getPictureId());
        }
        if (other.getGuid() != 0) {
          setGuid(other.getGuid());
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
        emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int pictureId_ ;
      /**
       * <code>uint32 pictureId = 1;</code>
       * @return The pictureId.
       */
      @java.lang.Override
      public int getPictureId() {
        return pictureId_;
      }
      /**
       * <code>uint32 pictureId = 1;</code>
       * @param value The pictureId to set.
       * @return This builder for chaining.
       */
      public Builder setPictureId(int value) {
        
        pictureId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 pictureId = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearPictureId() {
        
        pictureId_ = 0;
        onChanged();
        return this;
      }

      private int guid_ ;
      /**
       * <code>uint32 guid = 8;</code>
       * @return The guid.
       */
      @java.lang.Override
      public int getGuid() {
        return guid_;
      }
      /**
       * <code>uint32 guid = 8;</code>
       * @param value The guid to set.
       * @return This builder for chaining.
       */
      public Builder setGuid(int value) {
        
        guid_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 guid = 8;</code>
       * @return This builder for chaining.
       */
      public Builder clearGuid() {
        
        guid_ = 0;
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


      // @@protoc_insertion_point(builder_scope:HomePictureFrameInfo)
    }

    // @@protoc_insertion_point(class_scope:HomePictureFrameInfo)
    private static final emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo();
    }

    public static emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<HomePictureFrameInfo>
        PARSER = new com.google.protobuf.AbstractParser<HomePictureFrameInfo>() {
      @java.lang.Override
      public HomePictureFrameInfo parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new HomePictureFrameInfo(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<HomePictureFrameInfo> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<HomePictureFrameInfo> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.HomePictureFrameInfoOuterClass.HomePictureFrameInfo getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_HomePictureFrameInfo_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_HomePictureFrameInfo_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\032HomePictureFrameInfo.proto\"7\n\024HomePict" +
      "ureFrameInfo\022\021\n\tpictureId\030\001 \001(\r\022\014\n\004guid\030" +
      "\010 \001(\rB\033\n\031emu.grasscutter.net.protob\006prot" +
      "o3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_HomePictureFrameInfo_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_HomePictureFrameInfo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_HomePictureFrameInfo_descriptor,
        new java.lang.String[] { "PictureId", "Guid", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
