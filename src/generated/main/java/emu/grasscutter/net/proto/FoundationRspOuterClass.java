// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: FoundationRsp.proto

package emu.grasscutter.net.proto;

public final class FoundationRspOuterClass {
  private FoundationRspOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface FoundationRspOrBuilder extends
      // @@protoc_insertion_point(interface_extends:FoundationRsp)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 pointConfigId = 14;</code>
     * @return The pointConfigId.
     */
    int getPointConfigId();

    /**
     * <code>int32 retcode = 1;</code>
     * @return The retcode.
     */
    int getRetcode();

    /**
     * <code>uint32 gadgetEntityId = 10;</code>
     * @return The gadgetEntityId.
     */
    int getGadgetEntityId();

    /**
     * <code>.FoundationOpType opType = 15;</code>
     * @return The enum numeric value on the wire for opType.
     */
    int getOpTypeValue();
    /**
     * <code>.FoundationOpType opType = 15;</code>
     * @return The opType.
     */
    emu.grasscutter.net.proto.FoundationOpTypeOuterClass.FoundationOpType getOpType();

    /**
     * <code>uint32 buildingId = 8;</code>
     * @return The buildingId.
     */
    int getBuildingId();
  }
  /**
   * <pre>
   *enum KFLDKIJLBGN {
   *	option allow_alias= true;
   *	NONE = 0;
   *	PEPPOHPHJOJ = 806;
   *	DCDNILFDFLB = 0;
   *	NNBKOLMPOEA = 1;
   *}
   * </pre>
   *
   * Protobuf type {@code FoundationRsp}
   */
  public static final class FoundationRsp extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:FoundationRsp)
      FoundationRspOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use FoundationRsp.newBuilder() to construct.
    private FoundationRsp(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private FoundationRsp() {
      opType_ = 0;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new FoundationRsp();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private FoundationRsp(
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

              retcode_ = input.readInt32();
              break;
            }
            case 64: {

              buildingId_ = input.readUInt32();
              break;
            }
            case 80: {

              gadgetEntityId_ = input.readUInt32();
              break;
            }
            case 112: {

              pointConfigId_ = input.readUInt32();
              break;
            }
            case 120: {
              int rawValue = input.readEnum();

              opType_ = rawValue;
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
      return emu.grasscutter.net.proto.FoundationRspOuterClass.internal_static_FoundationRsp_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.FoundationRspOuterClass.internal_static_FoundationRsp_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp.class, emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp.Builder.class);
    }

    public static final int POINTCONFIGID_FIELD_NUMBER = 14;
    private int pointConfigId_;
    /**
     * <code>uint32 pointConfigId = 14;</code>
     * @return The pointConfigId.
     */
    @java.lang.Override
    public int getPointConfigId() {
      return pointConfigId_;
    }

    public static final int RETCODE_FIELD_NUMBER = 1;
    private int retcode_;
    /**
     * <code>int32 retcode = 1;</code>
     * @return The retcode.
     */
    @java.lang.Override
    public int getRetcode() {
      return retcode_;
    }

    public static final int GADGETENTITYID_FIELD_NUMBER = 10;
    private int gadgetEntityId_;
    /**
     * <code>uint32 gadgetEntityId = 10;</code>
     * @return The gadgetEntityId.
     */
    @java.lang.Override
    public int getGadgetEntityId() {
      return gadgetEntityId_;
    }

    public static final int OPTYPE_FIELD_NUMBER = 15;
    private int opType_;
    /**
     * <code>.FoundationOpType opType = 15;</code>
     * @return The enum numeric value on the wire for opType.
     */
    @java.lang.Override public int getOpTypeValue() {
      return opType_;
    }
    /**
     * <code>.FoundationOpType opType = 15;</code>
     * @return The opType.
     */
    @java.lang.Override public emu.grasscutter.net.proto.FoundationOpTypeOuterClass.FoundationOpType getOpType() {
      @SuppressWarnings("deprecation")
      emu.grasscutter.net.proto.FoundationOpTypeOuterClass.FoundationOpType result = emu.grasscutter.net.proto.FoundationOpTypeOuterClass.FoundationOpType.valueOf(opType_);
      return result == null ? emu.grasscutter.net.proto.FoundationOpTypeOuterClass.FoundationOpType.UNRECOGNIZED : result;
    }

    public static final int BUILDINGID_FIELD_NUMBER = 8;
    private int buildingId_;
    /**
     * <code>uint32 buildingId = 8;</code>
     * @return The buildingId.
     */
    @java.lang.Override
    public int getBuildingId() {
      return buildingId_;
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
        output.writeInt32(1, retcode_);
      }
      if (buildingId_ != 0) {
        output.writeUInt32(8, buildingId_);
      }
      if (gadgetEntityId_ != 0) {
        output.writeUInt32(10, gadgetEntityId_);
      }
      if (pointConfigId_ != 0) {
        output.writeUInt32(14, pointConfigId_);
      }
      if (opType_ != emu.grasscutter.net.proto.FoundationOpTypeOuterClass.FoundationOpType.FOUNDATION_OP_TYPE_NONE.getNumber()) {
        output.writeEnum(15, opType_);
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
          .computeInt32Size(1, retcode_);
      }
      if (buildingId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(8, buildingId_);
      }
      if (gadgetEntityId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(10, gadgetEntityId_);
      }
      if (pointConfigId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(14, pointConfigId_);
      }
      if (opType_ != emu.grasscutter.net.proto.FoundationOpTypeOuterClass.FoundationOpType.FOUNDATION_OP_TYPE_NONE.getNumber()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(15, opType_);
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
      if (!(obj instanceof emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp other = (emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp) obj;

      if (getPointConfigId()
          != other.getPointConfigId()) return false;
      if (getRetcode()
          != other.getRetcode()) return false;
      if (getGadgetEntityId()
          != other.getGadgetEntityId()) return false;
      if (opType_ != other.opType_) return false;
      if (getBuildingId()
          != other.getBuildingId()) return false;
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
      hash = (37 * hash) + POINTCONFIGID_FIELD_NUMBER;
      hash = (53 * hash) + getPointConfigId();
      hash = (37 * hash) + RETCODE_FIELD_NUMBER;
      hash = (53 * hash) + getRetcode();
      hash = (37 * hash) + GADGETENTITYID_FIELD_NUMBER;
      hash = (53 * hash) + getGadgetEntityId();
      hash = (37 * hash) + OPTYPE_FIELD_NUMBER;
      hash = (53 * hash) + opType_;
      hash = (37 * hash) + BUILDINGID_FIELD_NUMBER;
      hash = (53 * hash) + getBuildingId();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp prototype) {
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
     *enum KFLDKIJLBGN {
     *	option allow_alias= true;
     *	NONE = 0;
     *	PEPPOHPHJOJ = 806;
     *	DCDNILFDFLB = 0;
     *	NNBKOLMPOEA = 1;
     *}
     * </pre>
     *
     * Protobuf type {@code FoundationRsp}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:FoundationRsp)
        emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRspOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.FoundationRspOuterClass.internal_static_FoundationRsp_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.FoundationRspOuterClass.internal_static_FoundationRsp_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp.class, emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp.newBuilder()
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
        pointConfigId_ = 0;

        retcode_ = 0;

        gadgetEntityId_ = 0;

        opType_ = 0;

        buildingId_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.FoundationRspOuterClass.internal_static_FoundationRsp_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp build() {
        emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp buildPartial() {
        emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp result = new emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp(this);
        result.pointConfigId_ = pointConfigId_;
        result.retcode_ = retcode_;
        result.gadgetEntityId_ = gadgetEntityId_;
        result.opType_ = opType_;
        result.buildingId_ = buildingId_;
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
        if (other instanceof emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp) {
          return mergeFrom((emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp other) {
        if (other == emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp.getDefaultInstance()) return this;
        if (other.getPointConfigId() != 0) {
          setPointConfigId(other.getPointConfigId());
        }
        if (other.getRetcode() != 0) {
          setRetcode(other.getRetcode());
        }
        if (other.getGadgetEntityId() != 0) {
          setGadgetEntityId(other.getGadgetEntityId());
        }
        if (other.opType_ != 0) {
          setOpTypeValue(other.getOpTypeValue());
        }
        if (other.getBuildingId() != 0) {
          setBuildingId(other.getBuildingId());
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
        emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int pointConfigId_ ;
      /**
       * <code>uint32 pointConfigId = 14;</code>
       * @return The pointConfigId.
       */
      @java.lang.Override
      public int getPointConfigId() {
        return pointConfigId_;
      }
      /**
       * <code>uint32 pointConfigId = 14;</code>
       * @param value The pointConfigId to set.
       * @return This builder for chaining.
       */
      public Builder setPointConfigId(int value) {
        
        pointConfigId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 pointConfigId = 14;</code>
       * @return This builder for chaining.
       */
      public Builder clearPointConfigId() {
        
        pointConfigId_ = 0;
        onChanged();
        return this;
      }

      private int retcode_ ;
      /**
       * <code>int32 retcode = 1;</code>
       * @return The retcode.
       */
      @java.lang.Override
      public int getRetcode() {
        return retcode_;
      }
      /**
       * <code>int32 retcode = 1;</code>
       * @param value The retcode to set.
       * @return This builder for chaining.
       */
      public Builder setRetcode(int value) {
        
        retcode_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 retcode = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearRetcode() {
        
        retcode_ = 0;
        onChanged();
        return this;
      }

      private int gadgetEntityId_ ;
      /**
       * <code>uint32 gadgetEntityId = 10;</code>
       * @return The gadgetEntityId.
       */
      @java.lang.Override
      public int getGadgetEntityId() {
        return gadgetEntityId_;
      }
      /**
       * <code>uint32 gadgetEntityId = 10;</code>
       * @param value The gadgetEntityId to set.
       * @return This builder for chaining.
       */
      public Builder setGadgetEntityId(int value) {
        
        gadgetEntityId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 gadgetEntityId = 10;</code>
       * @return This builder for chaining.
       */
      public Builder clearGadgetEntityId() {
        
        gadgetEntityId_ = 0;
        onChanged();
        return this;
      }

      private int opType_ = 0;
      /**
       * <code>.FoundationOpType opType = 15;</code>
       * @return The enum numeric value on the wire for opType.
       */
      @java.lang.Override public int getOpTypeValue() {
        return opType_;
      }
      /**
       * <code>.FoundationOpType opType = 15;</code>
       * @param value The enum numeric value on the wire for opType to set.
       * @return This builder for chaining.
       */
      public Builder setOpTypeValue(int value) {
        
        opType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>.FoundationOpType opType = 15;</code>
       * @return The opType.
       */
      @java.lang.Override
      public emu.grasscutter.net.proto.FoundationOpTypeOuterClass.FoundationOpType getOpType() {
        @SuppressWarnings("deprecation")
        emu.grasscutter.net.proto.FoundationOpTypeOuterClass.FoundationOpType result = emu.grasscutter.net.proto.FoundationOpTypeOuterClass.FoundationOpType.valueOf(opType_);
        return result == null ? emu.grasscutter.net.proto.FoundationOpTypeOuterClass.FoundationOpType.UNRECOGNIZED : result;
      }
      /**
       * <code>.FoundationOpType opType = 15;</code>
       * @param value The opType to set.
       * @return This builder for chaining.
       */
      public Builder setOpType(emu.grasscutter.net.proto.FoundationOpTypeOuterClass.FoundationOpType value) {
        if (value == null) {
          throw new NullPointerException();
        }
        
        opType_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <code>.FoundationOpType opType = 15;</code>
       * @return This builder for chaining.
       */
      public Builder clearOpType() {
        
        opType_ = 0;
        onChanged();
        return this;
      }

      private int buildingId_ ;
      /**
       * <code>uint32 buildingId = 8;</code>
       * @return The buildingId.
       */
      @java.lang.Override
      public int getBuildingId() {
        return buildingId_;
      }
      /**
       * <code>uint32 buildingId = 8;</code>
       * @param value The buildingId to set.
       * @return This builder for chaining.
       */
      public Builder setBuildingId(int value) {
        
        buildingId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 buildingId = 8;</code>
       * @return This builder for chaining.
       */
      public Builder clearBuildingId() {
        
        buildingId_ = 0;
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


      // @@protoc_insertion_point(builder_scope:FoundationRsp)
    }

    // @@protoc_insertion_point(class_scope:FoundationRsp)
    private static final emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp();
    }

    public static emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<FoundationRsp>
        PARSER = new com.google.protobuf.AbstractParser<FoundationRsp>() {
      @java.lang.Override
      public FoundationRsp parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new FoundationRsp(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<FoundationRsp> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<FoundationRsp> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.FoundationRspOuterClass.FoundationRsp getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_FoundationRsp_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_FoundationRsp_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023FoundationRsp.proto\032\026FoundationOpType." +
      "proto\"\206\001\n\rFoundationRsp\022\025\n\rpointConfigId" +
      "\030\016 \001(\r\022\017\n\007retcode\030\001 \001(\005\022\026\n\016gadgetEntityI" +
      "d\030\n \001(\r\022!\n\006opType\030\017 \001(\0162\021.FoundationOpTy" +
      "pe\022\022\n\nbuildingId\030\010 \001(\rB\033\n\031emu.grasscutte" +
      "r.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          emu.grasscutter.net.proto.FoundationOpTypeOuterClass.getDescriptor(),
        });
    internal_static_FoundationRsp_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_FoundationRsp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_FoundationRsp_descriptor,
        new java.lang.String[] { "PointConfigId", "Retcode", "GadgetEntityId", "OpType", "BuildingId", });
    emu.grasscutter.net.proto.FoundationOpTypeOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
