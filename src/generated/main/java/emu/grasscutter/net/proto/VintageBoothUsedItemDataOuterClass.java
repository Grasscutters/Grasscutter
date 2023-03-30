// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: VintageBoothUsedItemData.proto

package emu.grasscutter.net.proto;

public final class VintageBoothUsedItemDataOuterClass {
  private VintageBoothUsedItemDataOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface VintageBoothUsedItemDataOrBuilder extends
      // @@protoc_insertion_point(interface_extends:VintageBoothUsedItemData)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 gadget_id = 1;</code>
     * @return The gadgetId.
     */
    int getGadgetId();

    /**
     * <code>bool is_open = 5;</code>
     * @return The isOpen.
     */
    boolean getIsOpen();
  }
  /**
   * <pre>
   * Name: DJDNJCIODMP
   * </pre>
   *
   * Protobuf type {@code VintageBoothUsedItemData}
   */
  public static final class VintageBoothUsedItemData extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:VintageBoothUsedItemData)
      VintageBoothUsedItemDataOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use VintageBoothUsedItemData.newBuilder() to construct.
    private VintageBoothUsedItemData(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private VintageBoothUsedItemData() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new VintageBoothUsedItemData();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private VintageBoothUsedItemData(
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

              gadgetId_ = input.readUInt32();
              break;
            }
            case 40: {

              isOpen_ = input.readBool();
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
      return emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.internal_static_VintageBoothUsedItemData_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.internal_static_VintageBoothUsedItemData_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData.class, emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData.Builder.class);
    }

    public static final int GADGET_ID_FIELD_NUMBER = 1;
    private int gadgetId_;
    /**
     * <code>uint32 gadget_id = 1;</code>
     * @return The gadgetId.
     */
    @java.lang.Override
    public int getGadgetId() {
      return gadgetId_;
    }

    public static final int IS_OPEN_FIELD_NUMBER = 5;
    private boolean isOpen_;
    /**
     * <code>bool is_open = 5;</code>
     * @return The isOpen.
     */
    @java.lang.Override
    public boolean getIsOpen() {
      return isOpen_;
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
      if (gadgetId_ != 0) {
        output.writeUInt32(1, gadgetId_);
      }
      if (isOpen_ != false) {
        output.writeBool(5, isOpen_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (gadgetId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(1, gadgetId_);
      }
      if (isOpen_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(5, isOpen_);
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
      if (!(obj instanceof emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData other = (emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData) obj;

      if (getGadgetId()
          != other.getGadgetId()) return false;
      if (getIsOpen()
          != other.getIsOpen()) return false;
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
      hash = (37 * hash) + GADGET_ID_FIELD_NUMBER;
      hash = (53 * hash) + getGadgetId();
      hash = (37 * hash) + IS_OPEN_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getIsOpen());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData prototype) {
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
     * Name: DJDNJCIODMP
     * </pre>
     *
     * Protobuf type {@code VintageBoothUsedItemData}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:VintageBoothUsedItemData)
        emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemDataOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.internal_static_VintageBoothUsedItemData_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.internal_static_VintageBoothUsedItemData_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData.class, emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData.newBuilder()
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
        gadgetId_ = 0;

        isOpen_ = false;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.internal_static_VintageBoothUsedItemData_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData build() {
        emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData buildPartial() {
        emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData result = new emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData(this);
        result.gadgetId_ = gadgetId_;
        result.isOpen_ = isOpen_;
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
        if (other instanceof emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData) {
          return mergeFrom((emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData other) {
        if (other == emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData.getDefaultInstance()) return this;
        if (other.getGadgetId() != 0) {
          setGadgetId(other.getGadgetId());
        }
        if (other.getIsOpen() != false) {
          setIsOpen(other.getIsOpen());
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
        emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int gadgetId_ ;
      /**
       * <code>uint32 gadget_id = 1;</code>
       * @return The gadgetId.
       */
      @java.lang.Override
      public int getGadgetId() {
        return gadgetId_;
      }
      /**
       * <code>uint32 gadget_id = 1;</code>
       * @param value The gadgetId to set.
       * @return This builder for chaining.
       */
      public Builder setGadgetId(int value) {
        
        gadgetId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 gadget_id = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearGadgetId() {
        
        gadgetId_ = 0;
        onChanged();
        return this;
      }

      private boolean isOpen_ ;
      /**
       * <code>bool is_open = 5;</code>
       * @return The isOpen.
       */
      @java.lang.Override
      public boolean getIsOpen() {
        return isOpen_;
      }
      /**
       * <code>bool is_open = 5;</code>
       * @param value The isOpen to set.
       * @return This builder for chaining.
       */
      public Builder setIsOpen(boolean value) {
        
        isOpen_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool is_open = 5;</code>
       * @return This builder for chaining.
       */
      public Builder clearIsOpen() {
        
        isOpen_ = false;
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


      // @@protoc_insertion_point(builder_scope:VintageBoothUsedItemData)
    }

    // @@protoc_insertion_point(class_scope:VintageBoothUsedItemData)
    private static final emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData();
    }

    public static emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<VintageBoothUsedItemData>
        PARSER = new com.google.protobuf.AbstractParser<VintageBoothUsedItemData>() {
      @java.lang.Override
      public VintageBoothUsedItemData parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new VintageBoothUsedItemData(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<VintageBoothUsedItemData> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<VintageBoothUsedItemData> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.VintageBoothUsedItemDataOuterClass.VintageBoothUsedItemData getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_VintageBoothUsedItemData_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_VintageBoothUsedItemData_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\036VintageBoothUsedItemData.proto\">\n\030Vint" +
      "ageBoothUsedItemData\022\021\n\tgadget_id\030\001 \001(\r\022" +
      "\017\n\007is_open\030\005 \001(\010B\033\n\031emu.grasscutter.net." +
      "protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_VintageBoothUsedItemData_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_VintageBoothUsedItemData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_VintageBoothUsedItemData_descriptor,
        new java.lang.String[] { "GadgetId", "IsOpen", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
