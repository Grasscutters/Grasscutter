// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: IslandPartyDownHillInfo.proto

package emu.grasscutter.net.proto;

public final class IslandPartyDownHillInfoOuterClass {
  private IslandPartyDownHillInfoOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface IslandPartyDownHillInfoOrBuilder extends
      // @@protoc_insertion_point(interface_extends:IslandPartyDownHillInfo)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 maxKillMonsterCount = 13;</code>
     * @return The maxKillMonsterCount.
     */
    int getMaxKillMonsterCount();

    /**
     * <code>.GalleryStartSource startSource = 4;</code>
     * @return The enum numeric value on the wire for startSource.
     */
    int getStartSourceValue();
    /**
     * <code>.GalleryStartSource startSource = 4;</code>
     * @return The startSource.
     */
    emu.grasscutter.net.proto.GalleryStartSourceOuterClass.GalleryStartSource getStartSource();

    /**
     * <code>uint32 coin = 1;</code>
     * @return The coin.
     */
    int getCoin();

    /**
     * <code>uint32 totalKillMonsterCount = 7;</code>
     * @return The totalKillMonsterCount.
     */
    int getTotalKillMonsterCount();
  }
  /**
   * Protobuf type {@code IslandPartyDownHillInfo}
   */
  public static final class IslandPartyDownHillInfo extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:IslandPartyDownHillInfo)
      IslandPartyDownHillInfoOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use IslandPartyDownHillInfo.newBuilder() to construct.
    private IslandPartyDownHillInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private IslandPartyDownHillInfo() {
      startSource_ = 0;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new IslandPartyDownHillInfo();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private IslandPartyDownHillInfo(
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

              coin_ = input.readUInt32();
              break;
            }
            case 32: {
              int rawValue = input.readEnum();

              startSource_ = rawValue;
              break;
            }
            case 56: {

              totalKillMonsterCount_ = input.readUInt32();
              break;
            }
            case 104: {

              maxKillMonsterCount_ = input.readUInt32();
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
      return emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.internal_static_IslandPartyDownHillInfo_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.internal_static_IslandPartyDownHillInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo.class, emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo.Builder.class);
    }

    public static final int MAXKILLMONSTERCOUNT_FIELD_NUMBER = 13;
    private int maxKillMonsterCount_;
    /**
     * <code>uint32 maxKillMonsterCount = 13;</code>
     * @return The maxKillMonsterCount.
     */
    @java.lang.Override
    public int getMaxKillMonsterCount() {
      return maxKillMonsterCount_;
    }

    public static final int STARTSOURCE_FIELD_NUMBER = 4;
    private int startSource_;
    /**
     * <code>.GalleryStartSource startSource = 4;</code>
     * @return The enum numeric value on the wire for startSource.
     */
    @java.lang.Override public int getStartSourceValue() {
      return startSource_;
    }
    /**
     * <code>.GalleryStartSource startSource = 4;</code>
     * @return The startSource.
     */
    @java.lang.Override public emu.grasscutter.net.proto.GalleryStartSourceOuterClass.GalleryStartSource getStartSource() {
      @SuppressWarnings("deprecation")
      emu.grasscutter.net.proto.GalleryStartSourceOuterClass.GalleryStartSource result = emu.grasscutter.net.proto.GalleryStartSourceOuterClass.GalleryStartSource.valueOf(startSource_);
      return result == null ? emu.grasscutter.net.proto.GalleryStartSourceOuterClass.GalleryStartSource.UNRECOGNIZED : result;
    }

    public static final int COIN_FIELD_NUMBER = 1;
    private int coin_;
    /**
     * <code>uint32 coin = 1;</code>
     * @return The coin.
     */
    @java.lang.Override
    public int getCoin() {
      return coin_;
    }

    public static final int TOTALKILLMONSTERCOUNT_FIELD_NUMBER = 7;
    private int totalKillMonsterCount_;
    /**
     * <code>uint32 totalKillMonsterCount = 7;</code>
     * @return The totalKillMonsterCount.
     */
    @java.lang.Override
    public int getTotalKillMonsterCount() {
      return totalKillMonsterCount_;
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
      if (coin_ != 0) {
        output.writeUInt32(1, coin_);
      }
      if (startSource_ != emu.grasscutter.net.proto.GalleryStartSourceOuterClass.GalleryStartSource.GALLERY_START_SOURCE_BY_NONE.getNumber()) {
        output.writeEnum(4, startSource_);
      }
      if (totalKillMonsterCount_ != 0) {
        output.writeUInt32(7, totalKillMonsterCount_);
      }
      if (maxKillMonsterCount_ != 0) {
        output.writeUInt32(13, maxKillMonsterCount_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (coin_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(1, coin_);
      }
      if (startSource_ != emu.grasscutter.net.proto.GalleryStartSourceOuterClass.GalleryStartSource.GALLERY_START_SOURCE_BY_NONE.getNumber()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(4, startSource_);
      }
      if (totalKillMonsterCount_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(7, totalKillMonsterCount_);
      }
      if (maxKillMonsterCount_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(13, maxKillMonsterCount_);
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
      if (!(obj instanceof emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo other = (emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo) obj;

      if (getMaxKillMonsterCount()
          != other.getMaxKillMonsterCount()) return false;
      if (startSource_ != other.startSource_) return false;
      if (getCoin()
          != other.getCoin()) return false;
      if (getTotalKillMonsterCount()
          != other.getTotalKillMonsterCount()) return false;
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
      hash = (37 * hash) + MAXKILLMONSTERCOUNT_FIELD_NUMBER;
      hash = (53 * hash) + getMaxKillMonsterCount();
      hash = (37 * hash) + STARTSOURCE_FIELD_NUMBER;
      hash = (53 * hash) + startSource_;
      hash = (37 * hash) + COIN_FIELD_NUMBER;
      hash = (53 * hash) + getCoin();
      hash = (37 * hash) + TOTALKILLMONSTERCOUNT_FIELD_NUMBER;
      hash = (53 * hash) + getTotalKillMonsterCount();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo prototype) {
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
     * Protobuf type {@code IslandPartyDownHillInfo}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:IslandPartyDownHillInfo)
        emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.internal_static_IslandPartyDownHillInfo_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.internal_static_IslandPartyDownHillInfo_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo.class, emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo.newBuilder()
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
        maxKillMonsterCount_ = 0;

        startSource_ = 0;

        coin_ = 0;

        totalKillMonsterCount_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.internal_static_IslandPartyDownHillInfo_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo build() {
        emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo buildPartial() {
        emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo result = new emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo(this);
        result.maxKillMonsterCount_ = maxKillMonsterCount_;
        result.startSource_ = startSource_;
        result.coin_ = coin_;
        result.totalKillMonsterCount_ = totalKillMonsterCount_;
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
        if (other instanceof emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo) {
          return mergeFrom((emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo other) {
        if (other == emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo.getDefaultInstance()) return this;
        if (other.getMaxKillMonsterCount() != 0) {
          setMaxKillMonsterCount(other.getMaxKillMonsterCount());
        }
        if (other.startSource_ != 0) {
          setStartSourceValue(other.getStartSourceValue());
        }
        if (other.getCoin() != 0) {
          setCoin(other.getCoin());
        }
        if (other.getTotalKillMonsterCount() != 0) {
          setTotalKillMonsterCount(other.getTotalKillMonsterCount());
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
        emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int maxKillMonsterCount_ ;
      /**
       * <code>uint32 maxKillMonsterCount = 13;</code>
       * @return The maxKillMonsterCount.
       */
      @java.lang.Override
      public int getMaxKillMonsterCount() {
        return maxKillMonsterCount_;
      }
      /**
       * <code>uint32 maxKillMonsterCount = 13;</code>
       * @param value The maxKillMonsterCount to set.
       * @return This builder for chaining.
       */
      public Builder setMaxKillMonsterCount(int value) {
        
        maxKillMonsterCount_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 maxKillMonsterCount = 13;</code>
       * @return This builder for chaining.
       */
      public Builder clearMaxKillMonsterCount() {
        
        maxKillMonsterCount_ = 0;
        onChanged();
        return this;
      }

      private int startSource_ = 0;
      /**
       * <code>.GalleryStartSource startSource = 4;</code>
       * @return The enum numeric value on the wire for startSource.
       */
      @java.lang.Override public int getStartSourceValue() {
        return startSource_;
      }
      /**
       * <code>.GalleryStartSource startSource = 4;</code>
       * @param value The enum numeric value on the wire for startSource to set.
       * @return This builder for chaining.
       */
      public Builder setStartSourceValue(int value) {
        
        startSource_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>.GalleryStartSource startSource = 4;</code>
       * @return The startSource.
       */
      @java.lang.Override
      public emu.grasscutter.net.proto.GalleryStartSourceOuterClass.GalleryStartSource getStartSource() {
        @SuppressWarnings("deprecation")
        emu.grasscutter.net.proto.GalleryStartSourceOuterClass.GalleryStartSource result = emu.grasscutter.net.proto.GalleryStartSourceOuterClass.GalleryStartSource.valueOf(startSource_);
        return result == null ? emu.grasscutter.net.proto.GalleryStartSourceOuterClass.GalleryStartSource.UNRECOGNIZED : result;
      }
      /**
       * <code>.GalleryStartSource startSource = 4;</code>
       * @param value The startSource to set.
       * @return This builder for chaining.
       */
      public Builder setStartSource(emu.grasscutter.net.proto.GalleryStartSourceOuterClass.GalleryStartSource value) {
        if (value == null) {
          throw new NullPointerException();
        }
        
        startSource_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <code>.GalleryStartSource startSource = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearStartSource() {
        
        startSource_ = 0;
        onChanged();
        return this;
      }

      private int coin_ ;
      /**
       * <code>uint32 coin = 1;</code>
       * @return The coin.
       */
      @java.lang.Override
      public int getCoin() {
        return coin_;
      }
      /**
       * <code>uint32 coin = 1;</code>
       * @param value The coin to set.
       * @return This builder for chaining.
       */
      public Builder setCoin(int value) {
        
        coin_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 coin = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearCoin() {
        
        coin_ = 0;
        onChanged();
        return this;
      }

      private int totalKillMonsterCount_ ;
      /**
       * <code>uint32 totalKillMonsterCount = 7;</code>
       * @return The totalKillMonsterCount.
       */
      @java.lang.Override
      public int getTotalKillMonsterCount() {
        return totalKillMonsterCount_;
      }
      /**
       * <code>uint32 totalKillMonsterCount = 7;</code>
       * @param value The totalKillMonsterCount to set.
       * @return This builder for chaining.
       */
      public Builder setTotalKillMonsterCount(int value) {
        
        totalKillMonsterCount_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 totalKillMonsterCount = 7;</code>
       * @return This builder for chaining.
       */
      public Builder clearTotalKillMonsterCount() {
        
        totalKillMonsterCount_ = 0;
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


      // @@protoc_insertion_point(builder_scope:IslandPartyDownHillInfo)
    }

    // @@protoc_insertion_point(class_scope:IslandPartyDownHillInfo)
    private static final emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo();
    }

    public static emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<IslandPartyDownHillInfo>
        PARSER = new com.google.protobuf.AbstractParser<IslandPartyDownHillInfo>() {
      @java.lang.Override
      public IslandPartyDownHillInfo parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new IslandPartyDownHillInfo(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<IslandPartyDownHillInfo> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<IslandPartyDownHillInfo> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.IslandPartyDownHillInfoOuterClass.IslandPartyDownHillInfo getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_IslandPartyDownHillInfo_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_IslandPartyDownHillInfo_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\035IslandPartyDownHillInfo.proto\032\030Gallery" +
      "StartSource.proto\"\215\001\n\027IslandPartyDownHil" +
      "lInfo\022\033\n\023maxKillMonsterCount\030\r \001(\r\022(\n\013st" +
      "artSource\030\004 \001(\0162\023.GalleryStartSource\022\014\n\004" +
      "coin\030\001 \001(\r\022\035\n\025totalKillMonsterCount\030\007 \001(" +
      "\rB\033\n\031emu.grasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          emu.grasscutter.net.proto.GalleryStartSourceOuterClass.getDescriptor(),
        });
    internal_static_IslandPartyDownHillInfo_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_IslandPartyDownHillInfo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_IslandPartyDownHillInfo_descriptor,
        new java.lang.String[] { "MaxKillMonsterCount", "StartSource", "Coin", "TotalKillMonsterCount", });
    emu.grasscutter.net.proto.GalleryStartSourceOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
