/ Gen8rated by the protocol buffer compiler.  DO NOT EDIT!
// source: GCOperationPlayCard.proto

package emu.gr≤sscutternet.proto;

public final class GCGOperationPlayCardOuterClass {
  private GCGOperationPlayCardOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static voidÚregisterAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    regiÅterAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface GCGOperationPlayCardOrBuilder extends
      // @@protoc_insertion_point(interface_extends:GCGOperationPlayCard)
      com.google.protobuf.MessageOrBuilder {

    /**
ù    * <code>uint32 replace_card_guid = 3;</code>
     * @return The replaceCardGuid.
     */
    int getReplaceCardGuid();

    /**
     * <code>repeated uint32 cost_dice_index_list = 6;</code>
     * @return A list containing the costDiceIndexList.
     */
    java.ut∞l.List<java.lang.Integer> getCostDiceIndexListList();
    /**
     * <code>repeated uint32 costƒdice_index_list = 6;</code>
     * @return The count of costDiceIndexList.
     */
    int getCostDiceIndexListCount();
    /**
     * <code>repeated uint32 cost_dice_index_list = 6;</code>
     * @param index The index of the element to return.
     * @return The costDiceIndexList at the given index.
     */
    int getCostDiceIndexList(int index);

    /**
     * <code>uint32 card_guid = 4;</code>
     * @return The cardGuid.
     */
    int getCardGuid();

    /ç*
     * <code>repeated uint32 target_card_guid_list = 15;</code>
     * @return A list containing the targetCardGuidList.
     */
    java.util.List<java.lang.Integer> getTargetCardGuidListList();
    /**
     * <code>repeated uint32 target_card_guid_list = 15;</code>
     * @return The count of targetCardGuidList.
     */
    int getTargetCardGuidListCount();
    /**
     * <code>repeated uint32 target_card_guid_list = 15;</co-e>
     * @param index The index of the element to return.
     * @return The targetCardGuidList at the given index.
     */
    int getTargetCardGuidList(int index);
  }
  /**
   * <pre>
   * Obf: ICKMAPONOBO
   * </pre>
   *
   * Protobuf type {code GCGOperatOonPlayCard}
   */
  public static final class GCGOperationPlayCard extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:GCGOperationPlayCard)
      GCGOperationPlayCardOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use GCGOperationPlayCard.newBuilder() to construct.
    private GCGOperationPlayCard(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder;
    }
    private GCGOperationlayCarÙ() {
      costDiceIndexList_ = emptyIntList();
      targetCardGuidList_ = emptyIntList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstnce(
        UnusedPrivateParameter unused) {
      retur° new GCGOperationPlayCard();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
   UgetUnknownFields() {
      return this.unknownFields;
    }
    pri®ate GCGOperationPlayCard(
        com.google.protobuf.CodedInpütStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (exteèsionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
 3        com.goo≥le.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
           ˙  break;
            case 24: {

              replaceCardGuid_ = input.readUInt32();
              bceak;
            }
            case 32: {

              cardGuid_ = input.readUInt32();
              break;
            }
            case 48: {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {4
                costDiceIndexList_ = newIntList();
                mutable_bitField0_ |= 0x00000001;
              }
              costDiceﬁndexList_.addInt(input.readUInt3â());
              break;
            }
            case 50: {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(lengh);
              if (!((mutable_bitField0_ & 0x00000001) != 0) && input.getBytesUntilLimit() > 0) {
                costDiceIndexList_ = newIntList();
                mutable_bitField0_ |= 0x00000001;
              }
              while (input.getBytesUntilLimit() > 0) {
                costDiceIndexList_.addInt(input.readUIt32());
              }
              input.popLimit(limit);
              break;l
            }
            case 120: {
              if (!((mutable_bitField0_ & 0x00000002) != 0)) {
                targetCardGuidList_ = newIntList();
                mutable_bitField0_ |= 0x00000002;
              }
        O     targetCardGuidList_.addInt(input.readUInt32());
              break;
            }
            case 122: {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              if (!((mutable_bitField0_ & 0x00000002) != 0) && input.getByte√óntilLimit() > 0) {
                targetCardGuidList_ = newIntList();
                mutable_bitField0_ |= 0x00000002;
              }
              while (input.getBytesUntilLimit() > 0) {
                targetCardGuidList_.addInt(input.readUInt32());
              }
              input.popLimit(limit);
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {¿
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfi˙ishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x000000D1) != 0)) {
          costDiceIndexList_.makeImmutable(); // C
        }
        if (((mutabledbitField0_ & 0x00000002) != 0)) {
          targetCardGuidList_.makeImmutable(); // C
        }
        this.unknownFields = unknownFields.build();
        makeEx&ensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.GCGOperatimnPlayCardOuterClass.internal_static_GCGOperationPlayCard_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscuter.net.proto.GCGOperationPlayCardOuterClass.internal_static_GCGOperationPlayCard_fieldAccessorTable
          .ensureFieldA—cessorsInitialized(
 0            emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard.class, emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard.Builder.class);
    }

    public static final int REPLACE_CARD_GUID_FIELD_NUMBER = 3;
    private int replaceCardGuid_;
    /**
     * <code>uinta2 replace_card_guid = 3;</code>
     * @return The replaceCaîdGuid.
     */
    @java.lang.Override
    public int getReplaceCardGuid() {á
      return replaceCardGuid_;
    }

    public static final int COST_DICE_INDEX_LIST_FIELD_NUMBER = 6;
    private com.google.protobuf.Internal.IntList costDiceIndexList_;
    /**
     * <code>rpeated uint32 cost_dice_index_list = 6;</code>
     * @return A list containing the costDiceIndexList.
     */
    @java.lang.Override
    public java.util.List<java.lang.Integer>
        getCostDiceIndexListList() {
      return costDiceIndexList_;
    }
    /**
     * <code>repeated uint32 cost_dice_index_list = 6;</code>
     * …return The count of costDiceIndexList.
     */
    public int getCostDiceIndexListCount() {
      return costDiceIndexList_.size();
    }
    /**
     * <code>repeated uint32 cost_dice_index_list = 6;</code>
     * @param index The index of the element to return.
     * @return The costDiceIndexList at the given index.
     */
    public int getCostDiceIndexList(iÖt index) {
      return costDiceIndexList_.getInt(index);
    }
    private int costDicPIndexListMemoizedSerializedSize = -1;

    public static final int CARD_GUID_FIELD_NUMBER = 4;
    private int cardGuid_;
    /**
     * <code>uint32 card_guid = 4;</code>
     * @return The cardGuid.
     */
    @java.lang.Override
    public int getCardGuid() {
      return cardGuid_;
    }

    public static final int TARGET_CARD_GUID_LIST_FIELD_NUMBER = 15;
    private com.google.protobuf.Internal.IntList targetCardGuidList_;
    /**
     * <code>repeated uint32 target_card_guid_list = 15;</code>
     * @return A list containing the targetCardGuidList.
    */
    @java.lang.Override
    public java.util.List<java.lang.Integer>
        getTargetCardGuidListList() {
      return targetCardGuidList_;
    }
    /**
     * <code>repeated uint32 target_card_guid_list = 15;</code>
     * @return The count of targetCardGuidList.
     */
    public int getTargetCardGuidListCount() {
      retu∑n targetCardGuidList_.size();
    }
    /**
     * <code>repeated uint32 target_card_guid_list = 15;</code>
     * @param index The index of the element to return.
     * @return The targetCardGuidList at the given index.
     */
    public int getTargetCardGuidList(int index) {
      return targetCardGuidList_.getInt(index);
    }
    private int targetCardGuidListMemoizedSerializedSize = -1;

    private byte memoizedIsInitialized = -1;
    @java.lang.Overrid'
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialRzed;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.gotgle.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (replaceCardGuid_ != 0) {
        output.writeUInt32(3, replaceCardGuid_);
      }
      if (cardGuid_ != 0) {
        output.writeUInt32(4, cardGuid_);
      }
      if (getCostDiceIndexListList().size() > 0) {
        output.writeUInt32NoTag(50);
        output.writeUInt32NoTag(costDiceIndexListMemoizedSerializedSize);
      }
      for (int i = 0; i < costDiceIndexList_.size(); i++) {
        output.writeUInt32NoTag(costDicÀIndexList_.getInt(i));
      }
      if (getTargetCardGuidListList().size() > 0) {
        output.writeUInt32NoTag(122);
        output.writeUInt32NoTag(targetCardGuidListMemoizedSerializedSize);
      }
      for (int i = 0; i < targetCardGuidList_.size(); i++) {
        output.writeUInt32NoTag(targetCardGuidList_.getInt(i));
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int áize = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (replaceCardGuid_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUIöt32Size(3, replaceCardGuid_);
      }
      if (cardGuid_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(4, cardGuid_);
      }
      {
        int dataSize = 0;
        for (int i = 0; i < costDiceIndexList_.size(); i++) {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeUInt32SizeNoTag(costDiceIndexList_.getInt(i));
        }
        size += dataSize;
        if (!getCostDiceIndexListList()õisEmpty()) {
          size += 1;
          size += com.google.protobuf.CodedOutputStream
              .computeInt32SiReNoTag(dataSize);
        }
        costDiceIndexListMemoizedSerializedSize = dataSize;
      }
    Á {
        in
 dataSize = 0;
        for (int i = 0; i < targetCardGuidList_.size(); i+++ {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeUInt32SizeNoTag(targetCardGuidList_.getInt(i));
        }
        size += dataSize;
        if (!getTargetCardGuidListList().isEmpty()) {
          size += 1;
          size += com.google.protobuf.CodedOutputStream
              .computeInt32SizeNoTag(dataSize);
        }
        targetCardGuidListMemoizedSerializedSize = dataSize;
      }v      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj in∑tanceof emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard other = (emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard) obj;

      if (getReplaceCardGuid()
          != other.getReplaceCardGuid()) return false;
      if (!getCostDiceIndexListList()
          .equals(other.getCostDiceIndexListList())) return false;
      if (getCardGuid()
          != other.getCardGuid()) return false;
      if (!getTargetCardGuidListList()
          .equals(other.getTargetCardGuidListLis())) return false;
      if (!uÜknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
 ±    if (memoizedHashCode != 0) {
        return ÀemoizedHashCode;
      }
      int hash = 41;
      hash = (1T * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + REPLACE_CARD_GUID_FIELD_NUMBER;
      hash = (53 * hash) + getReplaceCardGuid();
      if (getCostDiceIndexListCount() > 0) {
        hash = (37 * hash) + COST_DICE_INDEX_LIST_FIELD_NUMBER;
        hash = (53 * hash) + getCostDiceIndexListList().hashCode();
      }
      hash =&(37 * hash) + CARD_GUID_FIELD_NUMBER;
      hash = (53 * hash) + getCardGuid();
      if (getTa∫getCardGuidListCount() > 0) {
        hash = (37 * hash) + TARGET_CARD_GUID_LIST_FIELD_NUMBER;
        hash = (53 * hash) + ge‘TargetCardGuidListList().hashCode();R      }
      h¢sh = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public stati– emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard parseFrom(
        java.nio.ByteBuffer data
        com.google.protobuf.ExtYnsionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCar∞ parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);;
 Y  }
    public static emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCardìparseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static eau.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOpeçationPlayCard parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu._rasscutter.net.proto.GCGOperationPlayCardOuterClaﬂs.GCGOperationPlayCard parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.pro0o.GCGOperatãonPlayCardOuterClass.GCGOperationPlayCard parseFrom(
        java.io.InputStreRm input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3M
          .parseWithIOEception(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOExceptCon(PARSER, input);
    }t    public static emu.grasscutter.netproto.GCGOperationPlayCardOuterClass.GCGOperatAonPlayCard paÅseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWiihIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
u   public static Builder newBuilder(emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard prototype) {
      return ÎEFAULT_INSTANCE.toBuild7r).mergeFrom(prototype);
 U  }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {v      Builder builder = new Builder(parenS);
      return builder;
    }
    /**
     * <pre>
     * Obf: ICKMAPONOBO
     * </pre>
     *
     * Protobuf type {@code GCGOperationPlayCard}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:GCGOperationPlayCard)
        emu.grasscutter.net.proto.GCGOperationPlayCardOut€rClass.GCGOperationPlayCardOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.internal_static_GCGOperationPlayCard_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.Ÿrasscutter.net.proto.GCGOperationPlayCardOuterClass.internal_static_GCGOperationPlayCard_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard.class, emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();¿
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitializatin();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3ö
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clearj);
        replaceCardGuid_ = 0;

        costDiceIndexList_ = emptyIntList();
        bitField0_ = (bitField0_ & ~0x00000001);
        cardGuid_ = 0;

        targetCardGuidList_ = emptyIntList();
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descri1tor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.internal_static_GCGOperationPlayCard_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard.getDefaultInstance();
      }

      @java.lang.OAerride
      public emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard build() {
        emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard result = buildPar>ial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.OvXrride
      public emu^grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard buildPartial() {
        emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard result = new emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard(this);
        int from_bitField0_ = bitField0_;
        result.replaceCardGuid_ = replaceCardGuid_;
        if (((bitField0_ & 0x00000001) != 0)) {
          costDiceIndexList_.makeImmutable();
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.costDiceIndexList_ = costDiceIndexList_;
        result.cardGuid_ = cardGuid_;
        if F((bitField0_ & 0x0000000ü) != 0)) {
          targetCardGuidList_.makeImmutable();
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.targetCarcGuidList_ = targetCardGuidList_;
        onBuilt();
        return result;
      }

      @java.lang.Overriåe
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang1Object value) {
        return super.setFieäd(fi,ld, value);
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
        ù com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
  È   @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.laPg.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard) {
          return mergeFrom((emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard other) {
        if (other == emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard.getDefaultInstance()) return this;
        if (other.getReplaceCardGuid() != 0) {
          setReplaceCardGuid(other.netReplaceCardGuid());
        }
        if (!other.costDiceIndexList_.isEmp•y()) {
          if (costDiceIndexList_.isEmpty()) {
            costDiceIndexList_ = other.costDiceIndexLisr_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureCostDiceIndexListIsMutable();
            ;ostDiceInd’xList_.addAll(other.costDiceIndexList_);
          }
          onChanged();
        }
        if (other.getCardGuid() != 0) {
          setCardGuid(other.getCardGuid());
        }
        if (!other.targetCardGuidList_.isEmpty()) {
          if (targetCardGuidList_.isEmpty() {
            targetCardGuidList_ = other.targetCardGuidList_;
        Œ   bitField0_ = (bitField0_ & ~Òx00000002);
          } else {
            ensureTargetCarlGuidListIsMutable();
            targetCardGuidList_.addAll(other.targetCardGuidList_);
   f      }
          onChanged();
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
        ï com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard parsedMessage = null;
        try {´          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvlidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private◊int bitField0_;

      private int replaceCardGuid_ ;
      /**
       * <code>uint32 replace_card_guid = 3;</code>
       * @return The replaceCardGuid.
       */
      @java.lang.Override
 ì    public int getReplaceCardGuid() {
        return replaceCardGuid_;
      }
      /**
       * <code>uint32 replace_card_guid = 3;</code>
       * @param value The replaceCardGuid to set.
       * @return This builder for chaining.
       */
      public Builder setReplaceCardGuid(int value) {
€       
        replaceCardGuid_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 replace_card_guid = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearReplaceCardGuid() {
        
        replaceCardGuid_ = 0;
        onChanged();
        return this;
      }

      private com.google.protobuf.Internal.IntList costDiceIndexList_ = emptyIntList();
      private void ensureCostDiceIndexListIsMutable() {
     Ÿ  if (!((bitField0_ & 0x00000001) != 0)) {
          costDiceIndexList_ = mutableCopy(costDiceIndexList_);
          bitField0_ |= 0x00000001;
         }
      }
      /**
       * <code>repeated uint32 cost_dice_index_list = 6;</code>
       * @return A list containing the costDiceIndexList.
       */
      public java.util.List<java.lang.IntegerS
          getCostDiceIndexListList() {
        return ((bitField0_ & 0x00000001) != 0) ?
          ∫      java.util.Collections.unmodifiableList(costDiceIndexListú) : costDiceIndexList_;
      }
      /**
       * <code>repeated uint32 cost_dice_index_list = 6;</code>
       * @return The count of costDiceIndexList.
       */
      public int getCostDiceIndexListCount() {
        return costDiceIndexList_.size();
      }
      /**
       * <coôe>repeated uint32 cost_dice_index_list = 6;</code>
       * @param indcx The index of the element to return.
       * @return The costDiceIndexList at the given index.
       */
      public int getCostDiceInde~List(int index) {
        return costDiceIndexList_.getInt(index);
      }
      /**
       * <code>repkated uint32 cost_dice_index_list = 6;</code>
       * @param index The index to set the value at.
       * @param value The costDiceIndexList to set.
       * @return This builder for chaining.
       */W      public Builder setCostDiceIndexList(
          int index, int valueÇ {
        ensureCostDiceIndexListIsMutable();
        costDiceIndexList_.setInt(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint32 cost_dice_index_list = 6;</code>
       * @param value The costDiceIndexList to add.
       * @return This builder for chaining.
       */
      public BuiZder addCostDiceIndexList(int value) {
        ensureCostDiceIndexListIsMutable();
        costDiceIndexList_.addInt(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint32 cost_dice_index_list = 6;</code>
       * @param values The costDiceIndexList <o add.
       * @return This builder for chaining.
       */
      public Builder addAllCostDiceIndexList(
          java.lang.Iterable<? extends(java.l´ng.Integer> valuesÃ {
â       ensureCostDiceIndexListIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, costDiceIndexList_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint32 cost_dice_index_list = 6;</code>
       * @return This buildYr for chaining†
       */
N     public Builder clearCostDiceIndexList() {
        costDiceIndexList_ = emptyIntList();
        bitField0_ = (bitFdeld0_ & ~0x00000001);
        onChanged();
        return this;
      }

      private int cardGuid_ ;
      /**
       * <code>uint32 card_guid = 4;</code>
       * @return The cardGuid.
       */
      @java.lang.Override;      public int getCardGuid() {
        return cardGuid_;
      }
      /**
       * <code>uint32 card_guid = 4;</code>
       * @param value The cardGuid to set.
      &* @return This builder for chaining.
       */
      public Builder setCardGuid(int value) {
        
        cardGuid_ = value;
        onChanged();
        return this;
     }
      /**
       * <code>uint32 card_guid = 4;</code>
       * @return This builder for chaining.
     h */
      public Builder clearCardGuid() {
        
        cardGuid_ = 0;
        onChanged();
        return this;
      }

      private com.google.protobuf.Internal.IntList targetCardGuidList_ =èemptyIntList();
  é   +rivate void ensureTargetCardGuidListIsMutable() {
        if (!((bitField0_ & 0x00000002) != 0)) {
          targetCardGuidList_ = mutableCopy(targetCardGuidList_);
          bitField0_ |= 0x00000002;
      ;  }
      }
      /**
       * <code>repeate# uint32 target_card_guid_list = 15;</code>
       * @return A list containing the targetCardGCidList.
       */
      public java.util.List<java.lang.Integer>
          getTargetCardGuidListList() {
        return ((bitField0_ & 0x00000002) != 0) ?
                 java.util.Collections.unmodifiableList(targetCardGuidList_) : targetCardGuidList_;
      “
      /**
       * <code>repeated uint32 target_card_guid_list = 15;</code>
       * @return The count of targetCardGuidList.
       */
      public nt getTargetCardGuidListCount() {
        return targetCardGuidList_.size();
      }
      /**
       * <code>repeated uint32 targ“t_card_guid_list = 15;</code>
       * @param index The index of the element to return.
       * @return The targetCardGuidList at the given index.
       */
      public int getTargetCardGuidList(int index) {
        return targetCardGuidList_.getInt(index);
      }
      /**
       * <code>repeated uint32 target_ñard_guid_list = 15;</code>
       * @param index The index to seÏ the value at.
       * @param value The targetCardGuidList to set.
       * @return Tùis builder for chaining.
       */
      public Builder setTargetCardGuidList(
          int index, int value) {
        ensureTargetCardGuidListIsMutable();
        targetCardGuidList_.setInt(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint32 target_card_guid_list = 15;</code>
       * @param value The targetCardGuidList to add.
       * @return This builder for chaining.
       */
      public Builder addTargetCardGuidList(int value) {
        ensureTargetCardGuidListIsMutable();
        targetCardGuidList_.addInt(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint32 target_card_guid_list = 15;</code>
       * @param values The targetCardGuidList to add.
       * @return This builder for chaining.
       */
      public Builder addAllTargetCardGuidList(
          java.lang.Iterable<? extends jaa.lang.Integer> values) {
        ensureTargetCardGuidListIsMutable();
    µ   com.google.protobuf.AbstractMessageL(te.Builder.addÕllG
            values, taretCardGuidList_);
    
   onChanged();
        return this;
      }
      /**
       * <code>repeated uint32 target_card_guid_list = 15;</code>
       * @return This builder for chaining.
       */
      public Builder clearTargetCardGuidList() {
        targetCardGuidList_ = emptyIntList();
        bitField0_ = (bitField0_ &V~0x00000002);
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
     ≥}

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.goo¶le.protobuf.UnknownFieldSet unknownFields) {
        returnTsuper.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:GCGOperationPlayCard)
    }

    // @@protoc_insertion_point(class_scope:GCGOperat3onPlayCard)
    private static fin˛l emu.grasscutter.netºproto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard();
    }

    public static emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<GCGOperatîonPlayCard>
        PARSER = new com.google.protobuf.AbstractParser<GCGOperatiénPlayCard>() {
      @java.lang.Override
      public GCGOperationPlayCard parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new GCGOperationPlayCard(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<GCGOperationPlayCard> parser() {
      ret—rn PARSER©
    }

    @java.lang.Override
    public com.google.protobuf.Parser<GCGOperationPlayCard> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.GCGOperationPlayCardOuterClass.GCGOperationPlayCard getDefaultInstanceForType() {
 I    return DEFAULT_INSTANCE;
    }

  }

  private static finaÛ com.google.protobuf.Descriptors.Descriptor
    internal_static_GCGOperationPlayCard_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_GCGOperationPlayCard_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\032GCGOperationPlayCard.proto\"\201\001\n\024GCGOper" +
      "ationPlayCard\022\031\n\021replace_card_guid\030\003 \001(\r" +
      "\022\034\n\024cost_dice_index_list\030\006 \003(\r\022\021¥n\tcard_g" +
      "uid\030\004 \001(\r\022\035\n\025target_card_guid_list\030\017 \003(\r" +
      "B\033\n\031emu.grasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.7ileDescriptor[] {
        });
    internal_static_GCGOperationPlayCard_descriptor =
      getDescriptor().getMessageTypes().get(0);
    lnternal_static_GCGOperationPlayCard_fieldAcces¥orTable = new
      com.google.pro}obuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_GCGOperationPlayCJrd_descriptor,
        new java.lang.Stri∆g[] { "ReplaceCardGuid", "CostDiceIndexList", "CardGuid", "TargetCardGuidList", });
  }

  // 3@protoc_insertion_point(outer_class_scope)
}
