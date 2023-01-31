// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: AvatarBuffAddNotify.proto

package emu.grasscutter.net.proto;

public final class AvatarBuffAddNotifyOuterClass {
  private AvatarBuffAddNotifyOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface AvatarBuffAddNotifyOrBuilder extends
      // @@protoc_insertion_point(interface_extends:AvatarBuffAddNotify)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 buffId = 15;</code>
     * @return The buffId.
     */
    int getBuffId();

    /**
     * <code>uint64 avatarGuid = 7;</code>
     * @return The avatarGuid.
     */
    long getAvatarGuid();
  }
  /**
   * <pre>
   *enum FHLJCLILLFG {
   *	option allow_alias= true;
   *	NONE = 0;
   *	PEPPOHPHJOJ = 328;
   *	DCDNILFDFLB = 0;
   *	NNBKOLMPOEA = 1;
   *	EAJIABGAOCI = 1;
   *}
   * </pre>
   *
   * Protobuf type {@code AvatarBuffAddNotify}
   */
  public static final class AvatarBuffAddNotify extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:AvatarBuffAddNotify)
      AvatarBuffAddNotifyOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use AvatarBuffAddNotify.newBuilder() to construct.
    private AvatarBuffAddNotify(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private AvatarBuffAddNotify() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new AvatarBuffAddNotify();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private AvatarBuffAddNotify(
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
            case 56: {

              avatarGuid_ = input.readUInt64();
              break;
            }
            case 120: {

              buffId_ = input.readUInt32();
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
      return emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.internal_static_AvatarBuffAddNotify_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.internal_static_AvatarBuffAddNotify_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify.class, emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify.Builder.class);
    }

    public static final int BUFFID_FIELD_NUMBER = 15;
    private int buffId_;
    /**
     * <code>uint32 buffId = 15;</code>
     * @return The buffId.
     */
    @java.lang.Override
    public int getBuffId() {
      return buffId_;
    }

    public static final int AVATARGUID_FIELD_NUMBER = 7;
    private long avatarGuid_;
    /**
     * <code>uint64 avatarGuid = 7;</code>
     * @return The avatarGuid.
     */
    @java.lang.Override
    public long getAvatarGuid() {
      return avatarGuid_;
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
      if (avatarGuid_ != 0L) {
        output.writeUInt64(7, avatarGuid_);
      }
      if (buffId_ != 0) {
        output.writeUInt32(15, buffId_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (avatarGuid_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt64Size(7, avatarGuid_);
      }
      if (buffId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(15, buffId_);
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
      if (!(obj instanceof emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify other = (emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify) obj;

      if (getBuffId()
          != other.getBuffId()) return false;
      if (getAvatarGuid()
          != other.getAvatarGuid()) return false;
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
      hash = (37 * hash) + BUFFID_FIELD_NUMBER;
      hash = (53 * hash) + getBuffId();
      hash = (37 * hash) + AVATARGUID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getAvatarGuid());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify prototype) {
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
     *enum FHLJCLILLFG {
     *	option allow_alias= true;
     *	NONE = 0;
     *	PEPPOHPHJOJ = 328;
     *	DCDNILFDFLB = 0;
     *	NNBKOLMPOEA = 1;
     *	EAJIABGAOCI = 1;
     *}
     * </pre>
     *
     * Protobuf type {@code AvatarBuffAddNotify}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:AvatarBuffAddNotify)
        emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotifyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.internal_static_AvatarBuffAddNotify_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.internal_static_AvatarBuffAddNotify_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify.class, emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify.newBuilder()
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
        buffId_ = 0;

        avatarGuid_ = 0L;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.internal_static_AvatarBuffAddNotify_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify build() {
        emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify buildPartial() {
        emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify result = new emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify(this);
        result.buffId_ = buffId_;
        result.avatarGuid_ = avatarGuid_;
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
        if (other instanceof emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify) {
          return mergeFrom((emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify other) {
        if (other == emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify.getDefaultInstance()) return this;
        if (other.getBuffId() != 0) {
          setBuffId(other.getBuffId());
        }
        if (other.getAvatarGuid() != 0L) {
          setAvatarGuid(other.getAvatarGuid());
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
        emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int buffId_ ;
      /**
       * <code>uint32 buffId = 15;</code>
       * @return The buffId.
       */
      @java.lang.Override
      public int getBuffId() {
        return buffId_;
      }
      /**
       * <code>uint32 buffId = 15;</code>
       * @param value The buffId to set.
       * @return This builder for chaining.
       */
      public Builder setBuffId(int value) {
        
        buffId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 buffId = 15;</code>
       * @return This builder for chaining.
       */
      public Builder clearBuffId() {
        
        buffId_ = 0;
        onChanged();
        return this;
      }

      private long avatarGuid_ ;
      /**
       * <code>uint64 avatarGuid = 7;</code>
       * @return The avatarGuid.
       */
      @java.lang.Override
      public long getAvatarGuid() {
        return avatarGuid_;
      }
      /**
       * <code>uint64 avatarGuid = 7;</code>
       * @param value The avatarGuid to set.
       * @return This builder for chaining.
       */
      public Builder setAvatarGuid(long value) {
        
        avatarGuid_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint64 avatarGuid = 7;</code>
       * @return This builder for chaining.
       */
      public Builder clearAvatarGuid() {
        
        avatarGuid_ = 0L;
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


      // @@protoc_insertion_point(builder_scope:AvatarBuffAddNotify)
    }

    // @@protoc_insertion_point(class_scope:AvatarBuffAddNotify)
    private static final emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify();
    }

    public static emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<AvatarBuffAddNotify>
        PARSER = new com.google.protobuf.AbstractParser<AvatarBuffAddNotify>() {
      @java.lang.Override
      public AvatarBuffAddNotify parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new AvatarBuffAddNotify(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<AvatarBuffAddNotify> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<AvatarBuffAddNotify> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.AvatarBuffAddNotifyOuterClass.AvatarBuffAddNotify getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_AvatarBuffAddNotify_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_AvatarBuffAddNotify_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\031AvatarBuffAddNotify.proto\"9\n\023AvatarBuf" +
      "fAddNotify\022\016\n\006buffId\030\017 \001(\r\022\022\n\navatarGuid" +
      "\030\007 \001(\004B\033\n\031emu.grasscutter.net.protob\006pro" +
      "to3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_AvatarBuffAddNotify_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_AvatarBuffAddNotify_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_AvatarBuffAddNotify_descriptor,
        new java.lang.String[] { "BuffId", "AvatarGuid", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
