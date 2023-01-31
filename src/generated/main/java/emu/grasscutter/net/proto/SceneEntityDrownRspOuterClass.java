// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SceneEntityDrownRsp.proto

package emu.grasscutter.net.proto;

public final class SceneEntityDrownRspOuterClass {
  private SceneEntityDrownRspOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface SceneEntityDrownRspOrBuilder extends
      // @@protoc_insertion_point(interface_extends:SceneEntityDrownRsp)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 entityId = 15;</code>
     * @return The entityId.
     */
    int getEntityId();

    /**
     * <code>int32 retcode = 13;</code>
     * @return The retcode.
     */
    int getRetcode();
  }
  /**
   * <pre>
   *enum BDPGJMMMEBO {
   *	option allow_alias= true;
   *	NONE = 0;
   *	PEPPOHPHJOJ = 275;
   *	DCDNILFDFLB = 0;
   *	NNBKOLMPOEA = 1;
   *}
   * </pre>
   *
   * Protobuf type {@code SceneEntityDrownRsp}
   */
  public static final class SceneEntityDrownRsp extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:SceneEntityDrownRsp)
      SceneEntityDrownRspOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use SceneEntityDrownRsp.newBuilder() to construct.
    private SceneEntityDrownRsp(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private SceneEntityDrownRsp() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new SceneEntityDrownRsp();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private SceneEntityDrownRsp(
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
            case 104: {

              retcode_ = input.readInt32();
              break;
            }
            case 120: {

              entityId_ = input.readUInt32();
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
      return emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.internal_static_SceneEntityDrownRsp_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.internal_static_SceneEntityDrownRsp_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp.class, emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp.Builder.class);
    }

    public static final int ENTITYID_FIELD_NUMBER = 15;
    private int entityId_;
    /**
     * <code>uint32 entityId = 15;</code>
     * @return The entityId.
     */
    @java.lang.Override
    public int getEntityId() {
      return entityId_;
    }

    public static final int RETCODE_FIELD_NUMBER = 13;
    private int retcode_;
    /**
     * <code>int32 retcode = 13;</code>
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
      if (retcode_ != 0) {
        output.writeInt32(13, retcode_);
      }
      if (entityId_ != 0) {
        output.writeUInt32(15, entityId_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (retcode_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(13, retcode_);
      }
      if (entityId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(15, entityId_);
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
      if (!(obj instanceof emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp other = (emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp) obj;

      if (getEntityId()
          != other.getEntityId()) return false;
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
      hash = (37 * hash) + ENTITYID_FIELD_NUMBER;
      hash = (53 * hash) + getEntityId();
      hash = (37 * hash) + RETCODE_FIELD_NUMBER;
      hash = (53 * hash) + getRetcode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp prototype) {
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
     *enum BDPGJMMMEBO {
     *	option allow_alias= true;
     *	NONE = 0;
     *	PEPPOHPHJOJ = 275;
     *	DCDNILFDFLB = 0;
     *	NNBKOLMPOEA = 1;
     *}
     * </pre>
     *
     * Protobuf type {@code SceneEntityDrownRsp}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:SceneEntityDrownRsp)
        emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRspOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.internal_static_SceneEntityDrownRsp_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.internal_static_SceneEntityDrownRsp_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp.class, emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp.newBuilder()
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
        entityId_ = 0;

        retcode_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.internal_static_SceneEntityDrownRsp_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp build() {
        emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp buildPartial() {
        emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp result = new emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp(this);
        result.entityId_ = entityId_;
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
        if (other instanceof emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp) {
          return mergeFrom((emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp other) {
        if (other == emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp.getDefaultInstance()) return this;
        if (other.getEntityId() != 0) {
          setEntityId(other.getEntityId());
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
        emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int entityId_ ;
      /**
       * <code>uint32 entityId = 15;</code>
       * @return The entityId.
       */
      @java.lang.Override
      public int getEntityId() {
        return entityId_;
      }
      /**
       * <code>uint32 entityId = 15;</code>
       * @param value The entityId to set.
       * @return This builder for chaining.
       */
      public Builder setEntityId(int value) {
        
        entityId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 entityId = 15;</code>
       * @return This builder for chaining.
       */
      public Builder clearEntityId() {
        
        entityId_ = 0;
        onChanged();
        return this;
      }

      private int retcode_ ;
      /**
       * <code>int32 retcode = 13;</code>
       * @return The retcode.
       */
      @java.lang.Override
      public int getRetcode() {
        return retcode_;
      }
      /**
       * <code>int32 retcode = 13;</code>
       * @param value The retcode to set.
       * @return This builder for chaining.
       */
      public Builder setRetcode(int value) {
        
        retcode_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 retcode = 13;</code>
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


      // @@protoc_insertion_point(builder_scope:SceneEntityDrownRsp)
    }

    // @@protoc_insertion_point(class_scope:SceneEntityDrownRsp)
    private static final emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp();
    }

    public static emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<SceneEntityDrownRsp>
        PARSER = new com.google.protobuf.AbstractParser<SceneEntityDrownRsp>() {
      @java.lang.Override
      public SceneEntityDrownRsp parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new SceneEntityDrownRsp(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<SceneEntityDrownRsp> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<SceneEntityDrownRsp> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_SceneEntityDrownRsp_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_SceneEntityDrownRsp_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\031SceneEntityDrownRsp.proto\"8\n\023SceneEnti" +
      "tyDrownRsp\022\020\n\010entityId\030\017 \001(\r\022\017\n\007retcode\030" +
      "\r \001(\005B\033\n\031emu.grasscutter.net.protob\006prot" +
      "o3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_SceneEntityDrownRsp_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_SceneEntityDrownRsp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_SceneEntityDrownRsp_descriptor,
        new java.lang.String[] { "EntityId", "Retcode", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
