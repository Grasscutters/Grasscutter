// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ExpeditionRecallRsp.proto

package emu.grasscutter.net.proto;

public final class ExpeditionRecallRspOuterClass {
  private ExpeditionRecallRspOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ExpeditionRecallRspOrBuilder extends
      // @@protoc_insertion_point(interface_extends:ExpeditionRecallRsp)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 pathId = 3;</code>
     * @return The pathId.
     */
    int getPathId();

    /**
     * <code>int32 retcode = 8;</code>
     * @return The retcode.
     */
    int getRetcode();
  }
  /**
   * <pre>
   *enum NOCOAJDGING {
   *	option allow_alias= true;
   *	NONE = 0;
   *	PEPPOHPHJOJ = 2156;
   *	DCDNILFDFLB = 0;
   *	NNBKOLMPOEA = 1;
   *}
   * </pre>
   *
   * Protobuf type {@code ExpeditionRecallRsp}
   */
  public static final class ExpeditionRecallRsp extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:ExpeditionRecallRsp)
      ExpeditionRecallRspOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use ExpeditionRecallRsp.newBuilder() to construct.
    private ExpeditionRecallRsp(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ExpeditionRecallRsp() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new ExpeditionRecallRsp();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private ExpeditionRecallRsp(
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
            case 24: {

              pathId_ = input.readUInt32();
              break;
            }
            case 64: {

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
      return emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.internal_static_ExpeditionRecallRsp_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.internal_static_ExpeditionRecallRsp_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp.class, emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp.Builder.class);
    }

    public static final int PATHID_FIELD_NUMBER = 3;
    private int pathId_;
    /**
     * <code>uint32 pathId = 3;</code>
     * @return The pathId.
     */
    @java.lang.Override
    public int getPathId() {
      return pathId_;
    }

    public static final int RETCODE_FIELD_NUMBER = 8;
    private int retcode_;
    /**
     * <code>int32 retcode = 8;</code>
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
      if (pathId_ != 0) {
        output.writeUInt32(3, pathId_);
      }
      if (retcode_ != 0) {
        output.writeInt32(8, retcode_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (pathId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(3, pathId_);
      }
      if (retcode_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(8, retcode_);
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
      if (!(obj instanceof emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp other = (emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp) obj;

      if (getPathId()
          != other.getPathId()) return false;
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
      hash = (37 * hash) + PATHID_FIELD_NUMBER;
      hash = (53 * hash) + getPathId();
      hash = (37 * hash) + RETCODE_FIELD_NUMBER;
      hash = (53 * hash) + getRetcode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp prototype) {
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
     *enum NOCOAJDGING {
     *	option allow_alias= true;
     *	NONE = 0;
     *	PEPPOHPHJOJ = 2156;
     *	DCDNILFDFLB = 0;
     *	NNBKOLMPOEA = 1;
     *}
     * </pre>
     *
     * Protobuf type {@code ExpeditionRecallRsp}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:ExpeditionRecallRsp)
        emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRspOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.internal_static_ExpeditionRecallRsp_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.internal_static_ExpeditionRecallRsp_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp.class, emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp.newBuilder()
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
        pathId_ = 0;

        retcode_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.internal_static_ExpeditionRecallRsp_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp build() {
        emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp buildPartial() {
        emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp result = new emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp(this);
        result.pathId_ = pathId_;
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
        if (other instanceof emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp) {
          return mergeFrom((emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp other) {
        if (other == emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp.getDefaultInstance()) return this;
        if (other.getPathId() != 0) {
          setPathId(other.getPathId());
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
        emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int pathId_ ;
      /**
       * <code>uint32 pathId = 3;</code>
       * @return The pathId.
       */
      @java.lang.Override
      public int getPathId() {
        return pathId_;
      }
      /**
       * <code>uint32 pathId = 3;</code>
       * @param value The pathId to set.
       * @return This builder for chaining.
       */
      public Builder setPathId(int value) {
        
        pathId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 pathId = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearPathId() {
        
        pathId_ = 0;
        onChanged();
        return this;
      }

      private int retcode_ ;
      /**
       * <code>int32 retcode = 8;</code>
       * @return The retcode.
       */
      @java.lang.Override
      public int getRetcode() {
        return retcode_;
      }
      /**
       * <code>int32 retcode = 8;</code>
       * @param value The retcode to set.
       * @return This builder for chaining.
       */
      public Builder setRetcode(int value) {
        
        retcode_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 retcode = 8;</code>
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


      // @@protoc_insertion_point(builder_scope:ExpeditionRecallRsp)
    }

    // @@protoc_insertion_point(class_scope:ExpeditionRecallRsp)
    private static final emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp();
    }

    public static emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ExpeditionRecallRsp>
        PARSER = new com.google.protobuf.AbstractParser<ExpeditionRecallRsp>() {
      @java.lang.Override
      public ExpeditionRecallRsp parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ExpeditionRecallRsp(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ExpeditionRecallRsp> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ExpeditionRecallRsp> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.ExpeditionRecallRspOuterClass.ExpeditionRecallRsp getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ExpeditionRecallRsp_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ExpeditionRecallRsp_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\031ExpeditionRecallRsp.proto\"6\n\023Expeditio" +
      "nRecallRsp\022\016\n\006pathId\030\003 \001(\r\022\017\n\007retcode\030\010 " +
      "\001(\005B\033\n\031emu.grasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_ExpeditionRecallRsp_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ExpeditionRecallRsp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ExpeditionRecallRsp_descriptor,
        new java.lang.String[] { "PathId", "Retcode", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
