// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: AssociateInferenceWordRsp.proto

package emu.grasscutter.net.proto;

public final class AssociateInferenceWordRspOuterClass {
  private AssociateInferenceWordRspOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface AssociateInferenceWordRspOrBuilder extends
      // @@protoc_insertion_point(interface_extends:AssociateInferenceWordRsp)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 Unk3300_FDGLBLJOKOK = 14;</code>
     * @return The unk3300FDGLBLJOKOK.
     */
    int getUnk3300FDGLBLJOKOK();

    /**
     * <code>uint32 Unk3300_EPMGHELECNH = 5;</code>
     * @return The unk3300EPMGHELECNH.
     */
    int getUnk3300EPMGHELECNH();

    /**
     * <code>uint32 pageId = 11;</code>
     * @return The pageId.
     */
    int getPageId();

    /**
     * <code>int32 retcode = 13;</code>
     * @return The retcode.
     */
    int getRetcode();
  }
  /**
   * <pre>
   *enum CPCLGHEALEH {
   *	option allow_alias= true;
   *	NONE = 0;
   *	PEPPOHPHJOJ = 490;
   *	DCDNILFDFLB = 0;
   *	NNBKOLMPOEA = 1;
   *}
   * </pre>
   *
   * Protobuf type {@code AssociateInferenceWordRsp}
   */
  public static final class AssociateInferenceWordRsp extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:AssociateInferenceWordRsp)
      AssociateInferenceWordRspOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use AssociateInferenceWordRsp.newBuilder() to construct.
    private AssociateInferenceWordRsp(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private AssociateInferenceWordRsp() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new AssociateInferenceWordRsp();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private AssociateInferenceWordRsp(
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
            case 40: {

              unk3300EPMGHELECNH_ = input.readUInt32();
              break;
            }
            case 88: {

              pageId_ = input.readUInt32();
              break;
            }
            case 104: {

              retcode_ = input.readInt32();
              break;
            }
            case 112: {

              unk3300FDGLBLJOKOK_ = input.readUInt32();
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
      return emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.internal_static_AssociateInferenceWordRsp_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.internal_static_AssociateInferenceWordRsp_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp.class, emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp.Builder.class);
    }

    public static final int UNK3300_FDGLBLJOKOK_FIELD_NUMBER = 14;
    private int unk3300FDGLBLJOKOK_;
    /**
     * <code>uint32 Unk3300_FDGLBLJOKOK = 14;</code>
     * @return The unk3300FDGLBLJOKOK.
     */
    @java.lang.Override
    public int getUnk3300FDGLBLJOKOK() {
      return unk3300FDGLBLJOKOK_;
    }

    public static final int UNK3300_EPMGHELECNH_FIELD_NUMBER = 5;
    private int unk3300EPMGHELECNH_;
    /**
     * <code>uint32 Unk3300_EPMGHELECNH = 5;</code>
     * @return The unk3300EPMGHELECNH.
     */
    @java.lang.Override
    public int getUnk3300EPMGHELECNH() {
      return unk3300EPMGHELECNH_;
    }

    public static final int PAGEID_FIELD_NUMBER = 11;
    private int pageId_;
    /**
     * <code>uint32 pageId = 11;</code>
     * @return The pageId.
     */
    @java.lang.Override
    public int getPageId() {
      return pageId_;
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
      if (unk3300EPMGHELECNH_ != 0) {
        output.writeUInt32(5, unk3300EPMGHELECNH_);
      }
      if (pageId_ != 0) {
        output.writeUInt32(11, pageId_);
      }
      if (retcode_ != 0) {
        output.writeInt32(13, retcode_);
      }
      if (unk3300FDGLBLJOKOK_ != 0) {
        output.writeUInt32(14, unk3300FDGLBLJOKOK_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (unk3300EPMGHELECNH_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(5, unk3300EPMGHELECNH_);
      }
      if (pageId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(11, pageId_);
      }
      if (retcode_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(13, retcode_);
      }
      if (unk3300FDGLBLJOKOK_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(14, unk3300FDGLBLJOKOK_);
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
      if (!(obj instanceof emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp other = (emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp) obj;

      if (getUnk3300FDGLBLJOKOK()
          != other.getUnk3300FDGLBLJOKOK()) return false;
      if (getUnk3300EPMGHELECNH()
          != other.getUnk3300EPMGHELECNH()) return false;
      if (getPageId()
          != other.getPageId()) return false;
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
      hash = (37 * hash) + UNK3300_FDGLBLJOKOK_FIELD_NUMBER;
      hash = (53 * hash) + getUnk3300FDGLBLJOKOK();
      hash = (37 * hash) + UNK3300_EPMGHELECNH_FIELD_NUMBER;
      hash = (53 * hash) + getUnk3300EPMGHELECNH();
      hash = (37 * hash) + PAGEID_FIELD_NUMBER;
      hash = (53 * hash) + getPageId();
      hash = (37 * hash) + RETCODE_FIELD_NUMBER;
      hash = (53 * hash) + getRetcode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp prototype) {
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
     *enum CPCLGHEALEH {
     *	option allow_alias= true;
     *	NONE = 0;
     *	PEPPOHPHJOJ = 490;
     *	DCDNILFDFLB = 0;
     *	NNBKOLMPOEA = 1;
     *}
     * </pre>
     *
     * Protobuf type {@code AssociateInferenceWordRsp}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:AssociateInferenceWordRsp)
        emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRspOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.internal_static_AssociateInferenceWordRsp_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.internal_static_AssociateInferenceWordRsp_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp.class, emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp.newBuilder()
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
        unk3300FDGLBLJOKOK_ = 0;

        unk3300EPMGHELECNH_ = 0;

        pageId_ = 0;

        retcode_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.internal_static_AssociateInferenceWordRsp_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp build() {
        emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp buildPartial() {
        emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp result = new emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp(this);
        result.unk3300FDGLBLJOKOK_ = unk3300FDGLBLJOKOK_;
        result.unk3300EPMGHELECNH_ = unk3300EPMGHELECNH_;
        result.pageId_ = pageId_;
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
        if (other instanceof emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp) {
          return mergeFrom((emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp other) {
        if (other == emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp.getDefaultInstance()) return this;
        if (other.getUnk3300FDGLBLJOKOK() != 0) {
          setUnk3300FDGLBLJOKOK(other.getUnk3300FDGLBLJOKOK());
        }
        if (other.getUnk3300EPMGHELECNH() != 0) {
          setUnk3300EPMGHELECNH(other.getUnk3300EPMGHELECNH());
        }
        if (other.getPageId() != 0) {
          setPageId(other.getPageId());
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
        emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int unk3300FDGLBLJOKOK_ ;
      /**
       * <code>uint32 Unk3300_FDGLBLJOKOK = 14;</code>
       * @return The unk3300FDGLBLJOKOK.
       */
      @java.lang.Override
      public int getUnk3300FDGLBLJOKOK() {
        return unk3300FDGLBLJOKOK_;
      }
      /**
       * <code>uint32 Unk3300_FDGLBLJOKOK = 14;</code>
       * @param value The unk3300FDGLBLJOKOK to set.
       * @return This builder for chaining.
       */
      public Builder setUnk3300FDGLBLJOKOK(int value) {
        
        unk3300FDGLBLJOKOK_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 Unk3300_FDGLBLJOKOK = 14;</code>
       * @return This builder for chaining.
       */
      public Builder clearUnk3300FDGLBLJOKOK() {
        
        unk3300FDGLBLJOKOK_ = 0;
        onChanged();
        return this;
      }

      private int unk3300EPMGHELECNH_ ;
      /**
       * <code>uint32 Unk3300_EPMGHELECNH = 5;</code>
       * @return The unk3300EPMGHELECNH.
       */
      @java.lang.Override
      public int getUnk3300EPMGHELECNH() {
        return unk3300EPMGHELECNH_;
      }
      /**
       * <code>uint32 Unk3300_EPMGHELECNH = 5;</code>
       * @param value The unk3300EPMGHELECNH to set.
       * @return This builder for chaining.
       */
      public Builder setUnk3300EPMGHELECNH(int value) {
        
        unk3300EPMGHELECNH_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 Unk3300_EPMGHELECNH = 5;</code>
       * @return This builder for chaining.
       */
      public Builder clearUnk3300EPMGHELECNH() {
        
        unk3300EPMGHELECNH_ = 0;
        onChanged();
        return this;
      }

      private int pageId_ ;
      /**
       * <code>uint32 pageId = 11;</code>
       * @return The pageId.
       */
      @java.lang.Override
      public int getPageId() {
        return pageId_;
      }
      /**
       * <code>uint32 pageId = 11;</code>
       * @param value The pageId to set.
       * @return This builder for chaining.
       */
      public Builder setPageId(int value) {
        
        pageId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 pageId = 11;</code>
       * @return This builder for chaining.
       */
      public Builder clearPageId() {
        
        pageId_ = 0;
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


      // @@protoc_insertion_point(builder_scope:AssociateInferenceWordRsp)
    }

    // @@protoc_insertion_point(class_scope:AssociateInferenceWordRsp)
    private static final emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp();
    }

    public static emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<AssociateInferenceWordRsp>
        PARSER = new com.google.protobuf.AbstractParser<AssociateInferenceWordRsp>() {
      @java.lang.Override
      public AssociateInferenceWordRsp parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new AssociateInferenceWordRsp(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<AssociateInferenceWordRsp> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<AssociateInferenceWordRsp> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.AssociateInferenceWordRspOuterClass.AssociateInferenceWordRsp getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_AssociateInferenceWordRsp_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_AssociateInferenceWordRsp_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\037AssociateInferenceWordRsp.proto\"v\n\031Ass" +
      "ociateInferenceWordRsp\022\033\n\023Unk3300_FDGLBL" +
      "JOKOK\030\016 \001(\r\022\033\n\023Unk3300_EPMGHELECNH\030\005 \001(\r" +
      "\022\016\n\006pageId\030\013 \001(\r\022\017\n\007retcode\030\r \001(\005B\033\n\031emu" +
      ".grasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_AssociateInferenceWordRsp_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_AssociateInferenceWordRsp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_AssociateInferenceWordRsp_descriptor,
        new java.lang.String[] { "Unk3300FDGLBLJOKOK", "Unk3300EPMGHELECNH", "PageId", "Retcode", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
