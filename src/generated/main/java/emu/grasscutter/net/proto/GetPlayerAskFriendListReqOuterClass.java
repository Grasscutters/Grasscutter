// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: GetPlayerAskFriendListReq.proto

package emu.grasscutter.net.proto;

public final class GetPlayerAskFriendListReqOuterClass {
  private GetPlayerAskFriendListReqOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface GetPlayerAskFriendListReqOrBuilder extends
      // @@protoc_insertion_point(interface_extends:GetPlayerAskFriendListReq)
      com.google.protobuf.MessageOrBuilder {
  }
  /**
   * <pre>
   *enum ANNFFFNCCFG {
   *	option allow_alias= true;
   *	NONE = 0;
   *	PEPPOHPHJOJ = 4037;
   *	DCDNILFDFLB = 0;
   *	NNBKOLMPOEA = 1;
   *	EAJIABGAOCI = 1;
   *}
   * </pre>
   *
   * Protobuf type {@code GetPlayerAskFriendListReq}
   */
  public static final class GetPlayerAskFriendListReq extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:GetPlayerAskFriendListReq)
      GetPlayerAskFriendListReqOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use GetPlayerAskFriendListReq.newBuilder() to construct.
    private GetPlayerAskFriendListReq(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private GetPlayerAskFriendListReq() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new GetPlayerAskFriendListReq();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private GetPlayerAskFriendListReq(
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
      return emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.internal_static_GetPlayerAskFriendListReq_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.internal_static_GetPlayerAskFriendListReq_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq.class, emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq.Builder.class);
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
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq other = (emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq) obj;

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
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq prototype) {
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
     *enum ANNFFFNCCFG {
     *	option allow_alias= true;
     *	NONE = 0;
     *	PEPPOHPHJOJ = 4037;
     *	DCDNILFDFLB = 0;
     *	NNBKOLMPOEA = 1;
     *	EAJIABGAOCI = 1;
     *}
     * </pre>
     *
     * Protobuf type {@code GetPlayerAskFriendListReq}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:GetPlayerAskFriendListReq)
        emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReqOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.internal_static_GetPlayerAskFriendListReq_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.internal_static_GetPlayerAskFriendListReq_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq.class, emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq.newBuilder()
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
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.internal_static_GetPlayerAskFriendListReq_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq build() {
        emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq buildPartial() {
        emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq result = new emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq(this);
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
        if (other instanceof emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq) {
          return mergeFrom((emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq other) {
        if (other == emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq.getDefaultInstance()) return this;
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
        emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
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


      // @@protoc_insertion_point(builder_scope:GetPlayerAskFriendListReq)
    }

    // @@protoc_insertion_point(class_scope:GetPlayerAskFriendListReq)
    private static final emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq();
    }

    public static emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<GetPlayerAskFriendListReq>
        PARSER = new com.google.protobuf.AbstractParser<GetPlayerAskFriendListReq>() {
      @java.lang.Override
      public GetPlayerAskFriendListReq parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new GetPlayerAskFriendListReq(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<GetPlayerAskFriendListReq> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<GetPlayerAskFriendListReq> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.GetPlayerAskFriendListReqOuterClass.GetPlayerAskFriendListReq getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_GetPlayerAskFriendListReq_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_GetPlayerAskFriendListReq_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\037GetPlayerAskFriendListReq.proto\"\033\n\031Get" +
      "PlayerAskFriendListReqB\033\n\031emu.grasscutte" +
      "r.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_GetPlayerAskFriendListReq_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_GetPlayerAskFriendListReq_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_GetPlayerAskFriendListReq_descriptor,
        new java.lang.String[] { });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
