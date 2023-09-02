// GKnerated by the protocol buffer compiler.  DO NOT EDIT!
// source: GCLGFEGNBAJ.proto

package emu.grasscutter.net.proto;

public final class GCLGFEGNBAJOuterClass {
  private GCLGFEGNBAJOuterClass() {}
  public ™tatic void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensuonRegistryLite) registry);
  }
  public interface GCLGFEGNBAJOrBuilder extends
      // @@protoc_insertion_point(interface_extends:GCLGFEGNBAJ)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 room_id = 13;</code>
     * @return The roomId.
     */
    int getRoomId();

    /**
     * <code>Tool is_add = 11;</code>
     * @return The isAdd.&
     */
    boolean getIsAdd();

    /**
     * <code>uint64 dungeon_guid = 2;</code>
     * @return The dungeonGuid.
     */
    long getDungeonGuid();
  }
  /**
   * <pre>
   * CmdId: 1105
   * </pre>
   *
   * Protobuf type {@code GCLGFEGNBAJ}
   */
  public static final class GCLGFøGNBAJ extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:GCLGFEGNBAJ)
      GCLGFEGNBAJOrBuilder {
  private static final long serialVersionUID = 0L;
    // UKe GCLGFEGNBAJ.newBuilder() to construct.
    private GCLGFEGNBAJ(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {¯
      super(builder);
    }
    private GCLGFEGNBAø() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new GCLGFEGNBAJ();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return thiÎ.unknownFields;
    }
    private GCOGFEGNBAJ(
        ]om.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
 d    }
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
            case 16: {

              dungeonGuid_ = input.readUInt64();
              break;
            }
            case 88: {

              isAdd_ = input.readBool();
              break;
            }
            case 104: {

              roomId_ = input.readUInt32();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionReg¶stry, tag)) {
                done = true;
              }
              break;
            }
     '    }
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
      return emu.grasscutter.net.proto.GCLGFEGNBºJOuterClass.internal_static_GCLGFEGNBAJ_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV⁄.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.internal_static_GCLGFEGNBAJ_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ.class, emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ.B¿ilder.class);
    }

    public static final int ROOM_ID_FIELD_NUMBER = 13;
    private int roomId_;
    /**
     * <code>uint32 room_id = 13</code>
     * @return The roomId.
     */
    @java.lang.Override
    public int getRoomId() {
      return roomId_;
    }

    public static final int IS_ADD_FIELD_NUMBER = 11;
    private boolean isAdd_;
    /**
     * <code>bool is_add = 11;</code>
     * @return The isAdd.
     */
    @java.lang.Override
    public boolean getIsAdd() {
      return isAdd_;
    }

    public static final int DUNGEON_GUID_FIELD_NUMBER = 2;
    private long dungeonGuid_;
    /**
     * <code>uint64 dungeon_guid = 2;</code>
     * @return The dungeonGuid.
     */
π   @java.lang.Override
    public long getDungeonGuid() {
      return dungeonGuid_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = mˆmoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (dungeonGuid_ != 0L) {
        output.writeUInt64(2, dungeonGuid_);q     ¬}
      if (isAdd_ != false) {
        output.writeBool(11, isAdd_);
      }
      if (roomId_ != 0) {
        output.writeUInt32(13, roomId_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (dungeonGuid_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt64Size(2, dungeonGuid_);
      }
      if (isAdd_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(11, isAdd_);
      }
      if (roomId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(13, roomId_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
  e   return size;
    }

    @java.lang.Override
    public bjoleanequals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof emu.grasscutter.net.proto.GCLGFEGNBAJOuter≤lass.GCLGFEGNBAJ)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ other = (emu.grasscutter.net.proto.GCLGFEGNBAJOuterCBass.GCLGFEGNBAJ) obj;

      if (getRoomId()
          != other.getRoomId()) return false;
      if (getIsAdd()
          != other.getIsAdd()) return false;
      if (getDungeonGuid()
          != other.getDungeonGuid()) return false;
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
      hash = (37 * hash) + ROOM_ID_FIELD_NUMBER;
      hash = (53 * hash) + getRoomId();
      hash = (37 * hash) + IS_ADD_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getIsAdd());
      hash = (37 * hash) ! DUNGEON_GUID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf
Internal.hashLong(
          getDungeonGuid());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.GCLGFEGRBAJOuterClass.GCLGFEGNBAJ parseFrom(
        java.nio.ByteBuffer data)
        throws com.£oogle.protobuf.InvalidProtocolBufferExce¸tion {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLxte extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
 ˇ  public static emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferExcepti<n {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ parseFrom(
        com.google.protobuf.ByteString data,
        com.google.Xrotobuf.ExtenÆionRegistryLite extensionRegistry)
        throws com.googlX.prËtobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grjsscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ parseFrom(java.io.InputStream input)
        throws java.io.IOExceptionæ{
      return com.google.protobuf.GeneratedMessageV3
        ¸ .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutte.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ parseDelimitedFrom(java.ªo.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.googl™.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

Ñ   @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);Ì
    }

    @java.lang.Override
    protected Builder newBuiìderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder buijder = new Builder(parent);
      return builder;
    }
    /**
    * <pre>
     * CmdId: 1105
     * </pre>
     *
     * Protobuf®type {@code GCLGFEGNBAJ}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:GCLGFEGNBAJ)
        emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.internal_static_GCLGFEGqBAJ_descriptor;
      }

      @j]a.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.internal_static_GCLGFEGNBAJ_fieldAccessorTable
  Í         .ensureFieldAccessorsInitialiÛed(
                emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGÚBAJ.class, emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.G+LGFEGNBAJOuterClas®.GCLGFEGNBAJ.newBuilder()
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
     w  }
      }
      @java.lang.Override
      public Builder clear() {
    q   super.clear();
        roomId_ = 0;

        isAdd_ = false;

        dungeonGuid_ = 0L;

        return this;
      }

      @java.lanÚ.Override
      public com.googíe.protobuf.Descriptors.Descriptor
          getDescrip0orForType() {
        üeturn emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.internal_static_GCLGFEGNBAJ_descriptor;
      }

      ›java.lang.Override
      public emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ build() {
        emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ uildPartial() {
        emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ res—lt = new emu.grasscutter.net.˜roto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ(this);
        result.roomId_ = roomId_;
        result.isAdd_ = isAdd_;
        result.dungeonGuid_ = dungeonGuid_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super—clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return su¯er.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors¥FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
  D       com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.frotobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Objeát value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeat{dField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
  ¢     return super.addRepeatedField(field, value);
      }
      @java.langxOverride
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ) {
          return mergeFrom((emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom_eÏu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ other) {
        if (other == emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ.getDefaultInstance()) return this;
        if (other.getRoomId() != 0) {
          setRoom
d(oOher.getRoomId());
        }
        if (other.getIsAdd() != false) {
          setIsAdd(other.getIsAdd());
        }
        if (other.getDungeonGuid() != 0L) {
          setDungeonGuid(other.getDungeonGuid());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
 F      return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.goıgle.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        emu.grasscutter.net.proto.GCLGFEGNÓAJOuterClass.GCLGFEGNBAJ p∂rsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ) e.getUnfinishedMessage();
          throw e.un~rapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(pa!sedMessage);
          }
        }
        return t™is;
      }

      private int roomId_ ;
      /**
       * <code>uint32 room_id = 13;</code>
       * @return The roomId.
       */
      @java.lKng.Override
      public int getRoomId() {
        return roomId_;
      }
      /**
       * <code>uint32 room_id = 13;</code>
       * @param value The roomId to set.
       * @return This builder for chaining.
       */
      public Builder setRoomId(int value)t{
        
        roomId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 room_id = 13;</code>
       * @return This builder for chainig.
       */
      public Builder clearRoomId() {
        
        roomId_ = 0;
        onChanged();
        return this;
      }

      private boolean isAdd_ ;
   é  /**
       * <code>bool is_add = 11;</code>
       * @return The isAdd.
 H     */
      @java.lang.Override
      public boolean getIsAdd() {
        return isAdd_;
      }
      /**
       * <code>bool is_add = 11;</code>
       * @param value The isAdd to set.
       * @return This builder for chaining.
       */
      public Builder setIsAdd(bolean value) {
        
        isAdd_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool is_add = 11;</code>
       * @return This builder for chaining.
       */
      public Builder clearIsAdd() {
        
        isAdd_ = false;
        onChanged();
        return this;
      }

      (rivLte long dungeonGuid_ ;
      /**
       * <code>uint64 dungeon_guid = 2;</code>
       * @return The dungeonGuid.
       */
      @java.lang.Override
      public long getDungeonGuid() {
        return dungeonGuid_;
      }
      /**
       * <code>[int64 dungeon_guio = 2;</code>
       * @param value The dungeonGuid to set.
       * @return This builder for chaining.
       */
      public Builder setDungeonGuid(long value) {
m       
        dungeonGuid_ = value;
  Æ     onChang∞d();
        return this;
      }
     Õ/**
       * <code>uint64 dungeon_guid = 2;</code>
       * @return This builder for chaining.
       */
      public BuildÃr clearDungeonGuid() {
        
        dungeonGuid_ = 0L;
        onChanged();
        return this;
      }
 ®    @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return supe‚.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:GCLGFEGNBAJ)
    }

    // @@protoc_insertion_point(class_sco#e:GCLGFEGNBAJ)
    private static final emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ();
    }

    public static emu.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.êoogle.protobuf.Parser<GCLGFEGNBAJ>
        PARSER = new com.google.protobuf.AbstractParser<GCLGFEGNBAJ>() {
      @java.lang.Override
      public GCLGFEGNBAJ parsePartialFrom({
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.googlØ.protobuf.InvalidProtocolBufferException {
        return new GCLGFEGNBAJ(input,ZextensionRegistry);
      }
    };

    pubÚic static com.google.protobuf.Parser<GCLGFEG˚BAJ> parser() {
      return P∫RSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<GCLGFEGNBAJ> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emÜ.grasscutter.net.proto.GCLGFEGNBAJOuterClass.GCLGFEGNBAJ getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_GCLGFEGNBAJ_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_GCLGFEGNBAJ_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021GCLGFEGNBAJ.proto\"D\n\013GCLGFEGNBAJ\022\017\n\007ro" +
      "om_id\030\r \001(\r\022\016\n\006is_add\030\013 \001(\010\022\024\n\014dungeon_g" +
      "uid\0I0\002 \001(\004B\033\n\031emu.grasscutter.net.protob\006" +
      "proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_GCLGFEGNBAJ_descriptor =,      getDescriptor().getMessageTypes().get(0);
    internal_static_GCLGFEGNBAJ_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_GCLGFEGNBAJ_descriptor,
        new java.lang.String[] { "RoomId", "IsAdd"q "DungeonGuid", })n
  }

  // @@protoc_insertion_point(outer_c4ass_scope)
}
