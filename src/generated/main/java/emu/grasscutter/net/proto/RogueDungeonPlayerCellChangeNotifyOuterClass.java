// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RogueDungeonPlayerCellChangeNotify.proto

package emu.grasscutter.net.proto;

public final class RogueDungeonPlayerCellChangeNotifyOuterClass {
  private RogueDungeonPlayerCellChangeNotifyOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface RogueDungeonPlayerCellChangeNotifyOrBuilder extends
      // @@protoc_insertion_point(interface_extends:RogueDungeonPlayerCellChangeNotify)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 old_cell_id = 12;</code>
     * @return The oldCellId.
     */
    int getOldCellId();

    /**
     * <code>uint32 cell_id = 1;</code>
     * @return The cellId.
     */
    int getCellId();
  }
  /**
   * <pre>
   * CmdId: 6884
   * Obf: PACLNPPOHOD
   * </pre>
   *
   * Protobuf type {@code RogueDungeonPlayerCellChangeNotify}
   */
  public static final class RogueDungeonPlayerCellChangeNotify extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:RogueDungeonPlayerCellChangeNotify)
      RogueDungeonPlayerCellChangeNotifyOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use RogueDungeonPlayerCellChangeNotify.newBuilder() to construct.
    private RogueDungeonPlayerCellChangeNotify(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private RogueDungeonPlayerCellChangeNotify() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new RogueDungeonPlayerCellChangeNotify();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private RogueDungeonPlayerCellChangeNotify(
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

              cellId_ = input.readUInt32();
              break;
            }
            case 96: {

              oldCellId_ = input.readUInt32();
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
      return emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.internal_static_RogueDungeonPlayerCellChangeNotify_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.internal_static_RogueDungeonPlayerCellChangeNotify_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify.class, emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify.Builder.class);
    }

    public static final int OLD_CELL_ID_FIELD_NUMBER = 12;
    private int oldCellId_;
    /**
     * <code>uint32 old_cell_id = 12;</code>
     * @return The oldCellId.
     */
    @java.lang.Override
    public int getOldCellId() {
      return oldCellId_;
    }

    public static final int CELL_ID_FIELD_NUMBER = 1;
    private int cellId_;
    /**
     * <code>uint32 cell_id = 1;</code>
     * @return The cellId.
     */
    @java.lang.Override
    public int getCellId() {
      return cellId_;
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
      if (cellId_ != 0) {
        output.writeUInt32(1, cellId_);
      }
      if (oldCellId_ != 0) {
        output.writeUInt32(12, oldCellId_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (cellId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(1, cellId_);
      }
      if (oldCellId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(12, oldCellId_);
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
      if (!(obj instanceof emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify other = (emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify) obj;

      if (getOldCellId()
          != other.getOldCellId()) return false;
      if (getCellId()
          != other.getCellId()) return false;
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
      hash = (37 * hash) + OLD_CELL_ID_FIELD_NUMBER;
      hash = (53 * hash) + getOldCellId();
      hash = (37 * hash) + CELL_ID_FIELD_NUMBER;
      hash = (53 * hash) + getCellId();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify prototype) {
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
     * CmdId: 6884
     * Obf: PACLNPPOHOD
     * </pre>
     *
     * Protobuf type {@code RogueDungeonPlayerCellChangeNotify}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:RogueDungeonPlayerCellChangeNotify)
        emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotifyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.internal_static_RogueDungeonPlayerCellChangeNotify_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.internal_static_RogueDungeonPlayerCellChangeNotify_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify.class, emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify.newBuilder()
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
        oldCellId_ = 0;

        cellId_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.internal_static_RogueDungeonPlayerCellChangeNotify_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify build() {
        emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify buildPartial() {
        emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify result = new emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify(this);
        result.oldCellId_ = oldCellId_;
        result.cellId_ = cellId_;
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
        if (other instanceof emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify) {
          return mergeFrom((emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify other) {
        if (other == emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify.getDefaultInstance()) return this;
        if (other.getOldCellId() != 0) {
          setOldCellId(other.getOldCellId());
        }
        if (other.getCellId() != 0) {
          setCellId(other.getCellId());
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
        emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int oldCellId_ ;
      /**
       * <code>uint32 old_cell_id = 12;</code>
       * @return The oldCellId.
       */
      @java.lang.Override
      public int getOldCellId() {
        return oldCellId_;
      }
      /**
       * <code>uint32 old_cell_id = 12;</code>
       * @param value The oldCellId to set.
       * @return This builder for chaining.
       */
      public Builder setOldCellId(int value) {
        
        oldCellId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 old_cell_id = 12;</code>
       * @return This builder for chaining.
       */
      public Builder clearOldCellId() {
        
        oldCellId_ = 0;
        onChanged();
        return this;
      }

      private int cellId_ ;
      /**
       * <code>uint32 cell_id = 1;</code>
       * @return The cellId.
       */
      @java.lang.Override
      public int getCellId() {
        return cellId_;
      }
      /**
       * <code>uint32 cell_id = 1;</code>
       * @param value The cellId to set.
       * @return This builder for chaining.
       */
      public Builder setCellId(int value) {
        
        cellId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 cell_id = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearCellId() {
        
        cellId_ = 0;
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


      // @@protoc_insertion_point(builder_scope:RogueDungeonPlayerCellChangeNotify)
    }

    // @@protoc_insertion_point(class_scope:RogueDungeonPlayerCellChangeNotify)
    private static final emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify();
    }

    public static emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<RogueDungeonPlayerCellChangeNotify>
        PARSER = new com.google.protobuf.AbstractParser<RogueDungeonPlayerCellChangeNotify>() {
      @java.lang.Override
      public RogueDungeonPlayerCellChangeNotify parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new RogueDungeonPlayerCellChangeNotify(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<RogueDungeonPlayerCellChangeNotify> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<RogueDungeonPlayerCellChangeNotify> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.RogueDungeonPlayerCellChangeNotifyOuterClass.RogueDungeonPlayerCellChangeNotify getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_RogueDungeonPlayerCellChangeNotify_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_RogueDungeonPlayerCellChangeNotify_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n(RogueDungeonPlayerCellChangeNotify.pro" +
      "to\"J\n\"RogueDungeonPlayerCellChangeNotify" +
      "\022\023\n\013old_cell_id\030\014 \001(\r\022\017\n\007cell_id\030\001 \001(\rB\033" +
      "\n\031emu.grasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_RogueDungeonPlayerCellChangeNotify_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_RogueDungeonPlayerCellChangeNotify_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_RogueDungeonPlayerCellChangeNotify_descriptor,
        new java.lang.String[] { "OldCellId", "CellId", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
