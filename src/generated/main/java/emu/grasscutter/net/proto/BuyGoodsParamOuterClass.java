// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: BuyGoodsParam.proto

package emu.grasscutter.net.proto;

public final class BuyGoodsParamOuterClass {
  private BuyGoodsParamOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface BuyGoodsParamOrBuilder extends
      // @@protoc_insertion_point(interface_extends:BuyGoodsParam)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.ShopGoods goods = 8;</code>
     * @return Whether the goods field is set.
     */
    boolean hasGoods();
    /**
     * <code>.ShopGoods goods = 8;</code>
     * @return The goods.
     */
    emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods getGoods();
    /**
     * <code>.ShopGoods goods = 8;</code>
     */
    emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoodsOrBuilder getGoodsOrBuilder();

    /**
     * <code>uint32 buyCount = 3;</code>
     * @return The buyCount.
     */
    int getBuyCount();
  }
  /**
   * Protobuf type {@code BuyGoodsParam}
   */
  public static final class BuyGoodsParam extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:BuyGoodsParam)
      BuyGoodsParamOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use BuyGoodsParam.newBuilder() to construct.
    private BuyGoodsParam(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private BuyGoodsParam() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new BuyGoodsParam();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private BuyGoodsParam(
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

              buyCount_ = input.readUInt32();
              break;
            }
            case 66: {
              emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods.Builder subBuilder = null;
              if (goods_ != null) {
                subBuilder = goods_.toBuilder();
              }
              goods_ = input.readMessage(emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(goods_);
                goods_ = subBuilder.buildPartial();
              }

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
      return emu.grasscutter.net.proto.BuyGoodsParamOuterClass.internal_static_BuyGoodsParam_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.BuyGoodsParamOuterClass.internal_static_BuyGoodsParam_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam.class, emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam.Builder.class);
    }

    public static final int GOODS_FIELD_NUMBER = 8;
    private emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods goods_;
    /**
     * <code>.ShopGoods goods = 8;</code>
     * @return Whether the goods field is set.
     */
    @java.lang.Override
    public boolean hasGoods() {
      return goods_ != null;
    }
    /**
     * <code>.ShopGoods goods = 8;</code>
     * @return The goods.
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods getGoods() {
      return goods_ == null ? emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods.getDefaultInstance() : goods_;
    }
    /**
     * <code>.ShopGoods goods = 8;</code>
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoodsOrBuilder getGoodsOrBuilder() {
      return getGoods();
    }

    public static final int BUYCOUNT_FIELD_NUMBER = 3;
    private int buyCount_;
    /**
     * <code>uint32 buyCount = 3;</code>
     * @return The buyCount.
     */
    @java.lang.Override
    public int getBuyCount() {
      return buyCount_;
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
      if (buyCount_ != 0) {
        output.writeUInt32(3, buyCount_);
      }
      if (goods_ != null) {
        output.writeMessage(8, getGoods());
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (buyCount_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(3, buyCount_);
      }
      if (goods_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(8, getGoods());
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
      if (!(obj instanceof emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam other = (emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam) obj;

      if (hasGoods() != other.hasGoods()) return false;
      if (hasGoods()) {
        if (!getGoods()
            .equals(other.getGoods())) return false;
      }
      if (getBuyCount()
          != other.getBuyCount()) return false;
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
      if (hasGoods()) {
        hash = (37 * hash) + GOODS_FIELD_NUMBER;
        hash = (53 * hash) + getGoods().hashCode();
      }
      hash = (37 * hash) + BUYCOUNT_FIELD_NUMBER;
      hash = (53 * hash) + getBuyCount();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam prototype) {
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
     * Protobuf type {@code BuyGoodsParam}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:BuyGoodsParam)
        emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParamOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.BuyGoodsParamOuterClass.internal_static_BuyGoodsParam_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.BuyGoodsParamOuterClass.internal_static_BuyGoodsParam_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam.class, emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam.newBuilder()
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
        if (goodsBuilder_ == null) {
          goods_ = null;
        } else {
          goods_ = null;
          goodsBuilder_ = null;
        }
        buyCount_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.BuyGoodsParamOuterClass.internal_static_BuyGoodsParam_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam build() {
        emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam buildPartial() {
        emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam result = new emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam(this);
        if (goodsBuilder_ == null) {
          result.goods_ = goods_;
        } else {
          result.goods_ = goodsBuilder_.build();
        }
        result.buyCount_ = buyCount_;
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
        if (other instanceof emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam) {
          return mergeFrom((emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam other) {
        if (other == emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam.getDefaultInstance()) return this;
        if (other.hasGoods()) {
          mergeGoods(other.getGoods());
        }
        if (other.getBuyCount() != 0) {
          setBuyCount(other.getBuyCount());
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
        emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods goods_;
      private com.google.protobuf.SingleFieldBuilderV3<
          emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods, emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods.Builder, emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoodsOrBuilder> goodsBuilder_;
      /**
       * <code>.ShopGoods goods = 8;</code>
       * @return Whether the goods field is set.
       */
      public boolean hasGoods() {
        return goodsBuilder_ != null || goods_ != null;
      }
      /**
       * <code>.ShopGoods goods = 8;</code>
       * @return The goods.
       */
      public emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods getGoods() {
        if (goodsBuilder_ == null) {
          return goods_ == null ? emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods.getDefaultInstance() : goods_;
        } else {
          return goodsBuilder_.getMessage();
        }
      }
      /**
       * <code>.ShopGoods goods = 8;</code>
       */
      public Builder setGoods(emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods value) {
        if (goodsBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          goods_ = value;
          onChanged();
        } else {
          goodsBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <code>.ShopGoods goods = 8;</code>
       */
      public Builder setGoods(
          emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods.Builder builderForValue) {
        if (goodsBuilder_ == null) {
          goods_ = builderForValue.build();
          onChanged();
        } else {
          goodsBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <code>.ShopGoods goods = 8;</code>
       */
      public Builder mergeGoods(emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods value) {
        if (goodsBuilder_ == null) {
          if (goods_ != null) {
            goods_ =
              emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods.newBuilder(goods_).mergeFrom(value).buildPartial();
          } else {
            goods_ = value;
          }
          onChanged();
        } else {
          goodsBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <code>.ShopGoods goods = 8;</code>
       */
      public Builder clearGoods() {
        if (goodsBuilder_ == null) {
          goods_ = null;
          onChanged();
        } else {
          goods_ = null;
          goodsBuilder_ = null;
        }

        return this;
      }
      /**
       * <code>.ShopGoods goods = 8;</code>
       */
      public emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods.Builder getGoodsBuilder() {
        
        onChanged();
        return getGoodsFieldBuilder().getBuilder();
      }
      /**
       * <code>.ShopGoods goods = 8;</code>
       */
      public emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoodsOrBuilder getGoodsOrBuilder() {
        if (goodsBuilder_ != null) {
          return goodsBuilder_.getMessageOrBuilder();
        } else {
          return goods_ == null ?
              emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods.getDefaultInstance() : goods_;
        }
      }
      /**
       * <code>.ShopGoods goods = 8;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods, emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods.Builder, emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoodsOrBuilder> 
          getGoodsFieldBuilder() {
        if (goodsBuilder_ == null) {
          goodsBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods, emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods.Builder, emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoodsOrBuilder>(
                  getGoods(),
                  getParentForChildren(),
                  isClean());
          goods_ = null;
        }
        return goodsBuilder_;
      }

      private int buyCount_ ;
      /**
       * <code>uint32 buyCount = 3;</code>
       * @return The buyCount.
       */
      @java.lang.Override
      public int getBuyCount() {
        return buyCount_;
      }
      /**
       * <code>uint32 buyCount = 3;</code>
       * @param value The buyCount to set.
       * @return This builder for chaining.
       */
      public Builder setBuyCount(int value) {
        
        buyCount_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 buyCount = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearBuyCount() {
        
        buyCount_ = 0;
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


      // @@protoc_insertion_point(builder_scope:BuyGoodsParam)
    }

    // @@protoc_insertion_point(class_scope:BuyGoodsParam)
    private static final emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam();
    }

    public static emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<BuyGoodsParam>
        PARSER = new com.google.protobuf.AbstractParser<BuyGoodsParam>() {
      @java.lang.Override
      public BuyGoodsParam parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new BuyGoodsParam(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<BuyGoodsParam> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<BuyGoodsParam> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.BuyGoodsParamOuterClass.BuyGoodsParam getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_BuyGoodsParam_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_BuyGoodsParam_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023BuyGoodsParam.proto\032\017ShopGoods.proto\"<" +
      "\n\rBuyGoodsParam\022\031\n\005goods\030\010 \001(\0132\n.ShopGoo" +
      "ds\022\020\n\010buyCount\030\003 \001(\rB\033\n\031emu.grasscutter." +
      "net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          emu.grasscutter.net.proto.ShopGoodsOuterClass.getDescriptor(),
        });
    internal_static_BuyGoodsParam_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_BuyGoodsParam_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_BuyGoodsParam_descriptor,
        new java.lang.String[] { "Goods", "BuyCount", });
    emu.grasscutter.net.proto.ShopGoodsOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
