// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: AsterMidCampInfoNotify.proto

package emu.grasscutter.net.proto;

public final class AsterMidCampInfoNotifyOuterClass {
  private AsterMidCampInfoNotifyOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface AsterMidCampInfoNotifyOrBuilder extends
      // @@protoc_insertion_point(interface_extends:AsterMidCampInfoNotify)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
     */
    java.util.List<emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo> 
        getCampListList();
    /**
     * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
     */
    emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo getCampList(int index);
    /**
     * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
     */
    int getCampListCount();
    /**
     * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
     */
    java.util.List<? extends emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfoOrBuilder> 
        getCampListOrBuilderList();
    /**
     * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
     */
    emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfoOrBuilder getCampListOrBuilder(
        int index);
  }
  /**
   * <pre>
   * CmdId: 21139
   * Obf: JGDCHLHCNFO
   * </pre>
   *
   * Protobuf type {@code AsterMidCampInfoNotify}
   */
  public static final class AsterMidCampInfoNotify extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:AsterMidCampInfoNotify)
      AsterMidCampInfoNotifyOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use AsterMidCampInfoNotify.newBuilder() to construct.
    private AsterMidCampInfoNotify(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private AsterMidCampInfoNotify() {
      campList_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new AsterMidCampInfoNotify();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private AsterMidCampInfoNotify(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
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
            case 98: {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                campList_ = new java.util.ArrayList<emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo>();
                mutable_bitField0_ |= 0x00000001;
              }
              campList_.add(
                  input.readMessage(emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo.parser(), extensionRegistry));
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
        if (((mutable_bitField0_ & 0x00000001) != 0)) {
          campList_ = java.util.Collections.unmodifiableList(campList_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.internal_static_AsterMidCampInfoNotify_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.internal_static_AsterMidCampInfoNotify_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify.class, emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify.Builder.class);
    }

    public static final int CAMP_LIST_FIELD_NUMBER = 12;
    private java.util.List<emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo> campList_;
    /**
     * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
     */
    @java.lang.Override
    public java.util.List<emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo> getCampListList() {
      return campList_;
    }
    /**
     * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
     */
    @java.lang.Override
    public java.util.List<? extends emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfoOrBuilder> 
        getCampListOrBuilderList() {
      return campList_;
    }
    /**
     * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
     */
    @java.lang.Override
    public int getCampListCount() {
      return campList_.size();
    }
    /**
     * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo getCampList(int index) {
      return campList_.get(index);
    }
    /**
     * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfoOrBuilder getCampListOrBuilder(
        int index) {
      return campList_.get(index);
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
      for (int i = 0; i < campList_.size(); i++) {
        output.writeMessage(12, campList_.get(i));
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      for (int i = 0; i < campList_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(12, campList_.get(i));
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
      if (!(obj instanceof emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify other = (emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify) obj;

      if (!getCampListList()
          .equals(other.getCampListList())) return false;
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
      if (getCampListCount() > 0) {
        hash = (37 * hash) + CAMP_LIST_FIELD_NUMBER;
        hash = (53 * hash) + getCampListList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify prototype) {
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
     * CmdId: 21139
     * Obf: JGDCHLHCNFO
     * </pre>
     *
     * Protobuf type {@code AsterMidCampInfoNotify}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:AsterMidCampInfoNotify)
        emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotifyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.internal_static_AsterMidCampInfoNotify_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.internal_static_AsterMidCampInfoNotify_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify.class, emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify.newBuilder()
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
          getCampListFieldBuilder();
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        if (campListBuilder_ == null) {
          campList_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          campListBuilder_.clear();
        }
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.internal_static_AsterMidCampInfoNotify_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify build() {
        emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify buildPartial() {
        emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify result = new emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify(this);
        int from_bitField0_ = bitField0_;
        if (campListBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0)) {
            campList_ = java.util.Collections.unmodifiableList(campList_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.campList_ = campList_;
        } else {
          result.campList_ = campListBuilder_.build();
        }
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
        if (other instanceof emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify) {
          return mergeFrom((emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify other) {
        if (other == emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify.getDefaultInstance()) return this;
        if (campListBuilder_ == null) {
          if (!other.campList_.isEmpty()) {
            if (campList_.isEmpty()) {
              campList_ = other.campList_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureCampListIsMutable();
              campList_.addAll(other.campList_);
            }
            onChanged();
          }
        } else {
          if (!other.campList_.isEmpty()) {
            if (campListBuilder_.isEmpty()) {
              campListBuilder_.dispose();
              campListBuilder_ = null;
              campList_ = other.campList_;
              bitField0_ = (bitField0_ & ~0x00000001);
              campListBuilder_ = 
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                   getCampListFieldBuilder() : null;
            } else {
              campListBuilder_.addAllMessages(other.campList_);
            }
          }
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
        emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.util.List<emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo> campList_ =
        java.util.Collections.emptyList();
      private void ensureCampListIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          campList_ = new java.util.ArrayList<emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo>(campList_);
          bitField0_ |= 0x00000001;
         }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
          emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo, emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo.Builder, emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfoOrBuilder> campListBuilder_;

      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public java.util.List<emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo> getCampListList() {
        if (campListBuilder_ == null) {
          return java.util.Collections.unmodifiableList(campList_);
        } else {
          return campListBuilder_.getMessageList();
        }
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public int getCampListCount() {
        if (campListBuilder_ == null) {
          return campList_.size();
        } else {
          return campListBuilder_.getCount();
        }
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo getCampList(int index) {
        if (campListBuilder_ == null) {
          return campList_.get(index);
        } else {
          return campListBuilder_.getMessage(index);
        }
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public Builder setCampList(
          int index, emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo value) {
        if (campListBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureCampListIsMutable();
          campList_.set(index, value);
          onChanged();
        } else {
          campListBuilder_.setMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public Builder setCampList(
          int index, emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo.Builder builderForValue) {
        if (campListBuilder_ == null) {
          ensureCampListIsMutable();
          campList_.set(index, builderForValue.build());
          onChanged();
        } else {
          campListBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public Builder addCampList(emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo value) {
        if (campListBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureCampListIsMutable();
          campList_.add(value);
          onChanged();
        } else {
          campListBuilder_.addMessage(value);
        }
        return this;
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public Builder addCampList(
          int index, emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo value) {
        if (campListBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureCampListIsMutable();
          campList_.add(index, value);
          onChanged();
        } else {
          campListBuilder_.addMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public Builder addCampList(
          emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo.Builder builderForValue) {
        if (campListBuilder_ == null) {
          ensureCampListIsMutable();
          campList_.add(builderForValue.build());
          onChanged();
        } else {
          campListBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public Builder addCampList(
          int index, emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo.Builder builderForValue) {
        if (campListBuilder_ == null) {
          ensureCampListIsMutable();
          campList_.add(index, builderForValue.build());
          onChanged();
        } else {
          campListBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public Builder addAllCampList(
          java.lang.Iterable<? extends emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo> values) {
        if (campListBuilder_ == null) {
          ensureCampListIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(
              values, campList_);
          onChanged();
        } else {
          campListBuilder_.addAllMessages(values);
        }
        return this;
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public Builder clearCampList() {
        if (campListBuilder_ == null) {
          campList_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          campListBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public Builder removeCampList(int index) {
        if (campListBuilder_ == null) {
          ensureCampListIsMutable();
          campList_.remove(index);
          onChanged();
        } else {
          campListBuilder_.remove(index);
        }
        return this;
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo.Builder getCampListBuilder(
          int index) {
        return getCampListFieldBuilder().getBuilder(index);
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfoOrBuilder getCampListOrBuilder(
          int index) {
        if (campListBuilder_ == null) {
          return campList_.get(index);  } else {
          return campListBuilder_.getMessageOrBuilder(index);
        }
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public java.util.List<? extends emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfoOrBuilder> 
           getCampListOrBuilderList() {
        if (campListBuilder_ != null) {
          return campListBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(campList_);
        }
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo.Builder addCampListBuilder() {
        return getCampListFieldBuilder().addBuilder(
            emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo.getDefaultInstance());
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo.Builder addCampListBuilder(
          int index) {
        return getCampListFieldBuilder().addBuilder(
            index, emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo.getDefaultInstance());
      }
      /**
       * <code>repeated .AsterMidCampInfo camp_list = 12;</code>
       */
      public java.util.List<emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo.Builder> 
           getCampListBuilderList() {
        return getCampListFieldBuilder().getBuilderList();
      }
      private com.google.protobuf.RepeatedFieldBuilderV3<
          emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo, emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo.Builder, emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfoOrBuilder> 
          getCampListFieldBuilder() {
        if (campListBuilder_ == null) {
          campListBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
              emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo, emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfo.Builder, emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.AsterMidCampInfoOrBuilder>(
                  campList_,
                  ((bitField0_ & 0x00000001) != 0),
                  getParentForChildren(),
                  isClean());
          campList_ = null;
        }
        return campListBuilder_;
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


      // @@protoc_insertion_point(builder_scope:AsterMidCampInfoNotify)
    }

    // @@protoc_insertion_point(class_scope:AsterMidCampInfoNotify)
    private static final emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify();
    }

    public static emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<AsterMidCampInfoNotify>
        PARSER = new com.google.protobuf.AbstractParser<AsterMidCampInfoNotify>() {
      @java.lang.Override
      public AsterMidCampInfoNotify parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new AsterMidCampInfoNotify(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<AsterMidCampInfoNotify> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<AsterMidCampInfoNotify> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.AsterMidCampInfoNotifyOuterClass.AsterMidCampInfoNotify getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_AsterMidCampInfoNotify_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_AsterMidCampInfoNotify_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\034AsterMidCampInfoNotify.proto\032\026AsterMid" +
      "CampInfo.proto\">\n\026AsterMidCampInfoNotify" +
      "\022$\n\tcamp_list\030\014 \003(\0132\021.AsterMidCampInfoB\033" +
      "\n\031emu.grasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.getDescriptor(),
        });
    internal_static_AsterMidCampInfoNotify_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_AsterMidCampInfoNotify_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_AsterMidCampInfoNotify_descriptor,
        new java.lang.String[] { "CampList", });
    emu.grasscutter.net.proto.AsterMidCampInfoOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
