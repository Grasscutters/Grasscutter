// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ResinChangeNotify.proto

package emu.grasscutter.net.proto;

public final class ResinChangeNotifyOuterClass {
  private ResinChangeNotifyOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ResinChangeNotifyOrBuilder extends
      // @@protoc_insertion_point(interface_extends:ResinChangeNotify)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 cur_value = 14;</code>
     * @return The curValue.
     */
    int getCurValue();

    /**
     * <code>uint32 next_add_timestamp = 13;</code>
     * @return The nextAddTimestamp.
     */
    int getNextAddTimestamp();

    /**
     * <code>uint32 cur_buy_count = 12;</code>
     * @return The curBuyCount.
     */
    int getCurBuyCount();
  }
  /**
   * Protobuf type {@code ResinChangeNotify}
   */
  public static final class ResinChangeNotify extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:ResinChangeNotify)
      ResinChangeNotifyOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use ResinChangeNotify.newBuilder() to construct.
    private ResinChangeNotify(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ResinChangeNotify() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new ResinChangeNotify();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private ResinChangeNotify(
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
            case 96: {

              curBuyCount_ = input.readUInt32();
              break;
            }
            case 104: {

              nextAddTimestamp_ = input.readUInt32();
              break;
            }
            case 112: {

              curValue_ = input.readUInt32();
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
      return emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.internal_static_ResinChangeNotify_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.internal_static_ResinChangeNotify_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify.class, emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify.Builder.class);
    }

    public static final int CUR_VALUE_FIELD_NUMBER = 14;
    private int curValue_;
    /**
     * <code>uint32 cur_value = 14;</code>
     * @return The curValue.
     */
    @java.lang.Override
    public int getCurValue() {
      return curValue_;
    }

    public static final int NEXT_ADD_TIMESTAMP_FIELD_NUMBER = 13;
    private int nextAddTimestamp_;
    /**
     * <code>uint32 next_add_timestamp = 13;</code>
     * @return The nextAddTimestamp.
     */
    @java.lang.Override
    public int getNextAddTimestamp() {
      return nextAddTimestamp_;
    }

    public static final int CUR_BUY_COUNT_FIELD_NUMBER = 12;
    private int curBuyCount_;
    /**
     * <code>uint32 cur_buy_count = 12;</code>
     * @return The curBuyCount.
     */
    @java.lang.Override
    public int getCurBuyCount() {
      return curBuyCount_;
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
      if (curBuyCount_ != 0) {
        output.writeUInt32(12, curBuyCount_);
      }
      if (nextAddTimestamp_ != 0) {
        output.writeUInt32(13, nextAddTimestamp_);
      }
      if (curValue_ != 0) {
        output.writeUInt32(14, curValue_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (curBuyCount_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(12, curBuyCount_);
      }
      if (nextAddTimestamp_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(13, nextAddTimestamp_);
      }
      if (curValue_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(14, curValue_);
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
      if (!(obj instanceof emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify other = (emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify) obj;

      if (getCurValue()
          != other.getCurValue()) return false;
      if (getNextAddTimestamp()
          != other.getNextAddTimestamp()) return false;
      if (getCurBuyCount()
          != other.getCurBuyCount()) return false;
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
      hash = (37 * hash) + CUR_VALUE_FIELD_NUMBER;
      hash = (53 * hash) + getCurValue();
      hash = (37 * hash) + NEXT_ADD_TIMESTAMP_FIELD_NUMBER;
      hash = (53 * hash) + getNextAddTimestamp();
      hash = (37 * hash) + CUR_BUY_COUNT_FIELD_NUMBER;
      hash = (53 * hash) + getCurBuyCount();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify prototype) {
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
     * Protobuf type {@code ResinChangeNotify}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:ResinChangeNotify)
        emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotifyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.internal_static_ResinChangeNotify_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.internal_static_ResinChangeNotify_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify.class, emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify.newBuilder()
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
        curValue_ = 0;

        nextAddTimestamp_ = 0;

        curBuyCount_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.internal_static_ResinChangeNotify_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify build() {
        emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify buildPartial() {
        emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify result = new emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify(this);
        result.curValue_ = curValue_;
        result.nextAddTimestamp_ = nextAddTimestamp_;
        result.curBuyCount_ = curBuyCount_;
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
        if (other instanceof emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify) {
          return mergeFrom((emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify other) {
        if (other == emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify.getDefaultInstance()) return this;
        if (other.getCurValue() != 0) {
          setCurValue(other.getCurValue());
        }
        if (other.getNextAddTimestamp() != 0) {
          setNextAddTimestamp(other.getNextAddTimestamp());
        }
        if (other.getCurBuyCount() != 0) {
          setCurBuyCount(other.getCurBuyCount());
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
        emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int curValue_ ;
      /**
       * <code>uint32 cur_value = 14;</code>
       * @return The curValue.
       */
      @java.lang.Override
      public int getCurValue() {
        return curValue_;
      }
      /**
       * <code>uint32 cur_value = 14;</code>
       * @param value The curValue to set.
       * @return This builder for chaining.
       */
      public Builder setCurValue(int value) {
        
        curValue_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 cur_value = 14;</code>
       * @return This builder for chaining.
       */
      public Builder clearCurValue() {
        
        curValue_ = 0;
        onChanged();
        return this;
      }

      private int nextAddTimestamp_ ;
      /**
       * <code>uint32 next_add_timestamp = 13;</code>
       * @return The nextAddTimestamp.
       */
      @java.lang.Override
      public int getNextAddTimestamp() {
        return nextAddTimestamp_;
      }
      /**
       * <code>uint32 next_add_timestamp = 13;</code>
       * @param value The nextAddTimestamp to set.
       * @return This builder for chaining.
       */
      public Builder setNextAddTimestamp(int value) {
        
        nextAddTimestamp_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 next_add_timestamp = 13;</code>
       * @return This builder for chaining.
       */
      public Builder clearNextAddTimestamp() {
        
        nextAddTimestamp_ = 0;
        onChanged();
        return this;
      }

      private int curBuyCount_ ;
      /**
       * <code>uint32 cur_buy_count = 12;</code>
       * @return The curBuyCount.
       */
      @java.lang.Override
      public int getCurBuyCount() {
        return curBuyCount_;
      }
      /**
       * <code>uint32 cur_buy_count = 12;</code>
       * @param value The curBuyCount to set.
       * @return This builder for chaining.
       */
      public Builder setCurBuyCount(int value) {
        
        curBuyCount_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 cur_buy_count = 12;</code>
       * @return This builder for chaining.
       */
      public Builder clearCurBuyCount() {
        
        curBuyCount_ = 0;
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


      // @@protoc_insertion_point(builder_scope:ResinChangeNotify)
    }

    // @@protoc_insertion_point(class_scope:ResinChangeNotify)
    private static final emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify();
    }

    public static emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ResinChangeNotify>
        PARSER = new com.google.protobuf.AbstractParser<ResinChangeNotify>() {
      @java.lang.Override
      public ResinChangeNotify parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ResinChangeNotify(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ResinChangeNotify> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ResinChangeNotify> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ResinChangeNotify_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ResinChangeNotify_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\027ResinChangeNotify.proto\"Y\n\021ResinChange" +
      "Notify\022\021\n\tcur_value\030\016 \001(\r\022\032\n\022next_add_ti" +
      "mestamp\030\r \001(\r\022\025\n\rcur_buy_count\030\014 \001(\rB\033\n\031" +
      "emu.grasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_ResinChangeNotify_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ResinChangeNotify_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ResinChangeNotify_descriptor,
        new java.lang.String[] { "CurValue", "NextAddTimestamp", "CurBuyCount", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
