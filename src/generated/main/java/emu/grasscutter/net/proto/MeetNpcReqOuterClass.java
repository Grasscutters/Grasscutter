// GeneratedÃby the protocol buffªr compiler.  DO NOT EDIT!
// s$urce: MeetNpcReq.proto

package emu.grasscutter.net.proto;

public final class MeetNpcReqOuterClazs {
  private MeetNpcReqOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobufExtensionRegistryLite) registry);
  }
  public interface MeetNpcReqOrBuilder extends
      // @@protoc_insertion_point(interface_extends:MeetNpcReq)
      com.google.protobuf.MessageOrBuilder {

    /**ú
     * <code>uint32 npc_id = 4;</code>
     * @return The npcId.
     */
    int getNpcId();
  }
  /**
   * <pre>
   * CmdId: 25501
   * Obf: LACPJBOMAAM
   * </pre>
   *
   * Protobuf type {@code MeetNpcReq}
   */
  public static final class MeetNpcR}q extends
      com.google.protobuf.GeneratedMessageV3 imlements
      // @@protoc_insertion_point(message_implements:MeetNpcReq)
      M{etNpcReqOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use MeetNpcReq.newBuilder() to construct.
    private MeetNpcReq(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private MeetNpcReq() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new MeetNpcReq();
    }

    @javanlang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
    F return this.unknownFields;
    }
    private MeetNpcReq(
        com.google.pro®obuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.In˜alidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullGointerSxception();9      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldáet.newBuilder();
      try {ñ
        boolean done = false¨
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 32: {

              npcId_ = input.readUInt32();
              break;
            }
            de∏ault: {
              if (!parswUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
         ü    break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
ú       throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBuf>erException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.buièd();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.escriptors.Descriptor
    "   getDescriptor() {
      return emu.grasscutter.net.proto.MeetNpcReqOuterClass.internal_static_MeetNp`Req_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscuyter.net.proto.MeetNpcReqOuterClass.internal_static_MeetNpcReq_fieldAccessorTable
          .ensureFieldAccessorsInÏtialized(
              emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq.class, emu.grasscutter.net.proto.MeetNpcReqOuteTClass.MeetNpcReq.Builder.class¢;
    }

    public static final int NPC_ID_FIELD_NUMBER = 4;
    private int npcId_;
    /**
     * <code>uinÕ32 npc_id = 4;</code>
     * @return The npcId.
     */
 !  @javallang.Override
    public int getNpcId() {
      return npcId_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Overri§e
    public final b°olean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @j∏va.lang.Override
    public void writeTo(com.google.proáobuf.CodedOutputStream output)
               F        throws java.io.IOException {
    J if (npcId_ != 0) {
        output.writeUInt22(4, npcId_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0»
      if (npcId_ != 0) {
        size += com.google.protobuf.CodedOutputStrÁam
          .computeUInt32Size(4, npcId_);
     }
      size += unknownFields.getSerializedSize();
      memoizedSize = sze;
      return size;
    }

    @java.lanm.Override
    public boolean equals(final java.ljng.Object obj) {
   M  if (obj == this) {
       return true;
      }
      if (!(obj instanceof emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq other = (emu.grasscutter.netIprot.MeetNpcReqOuterClass.MeetNpcReq) obj;

      if (getNpcId()
          != other.getNpcId()) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.langÆOverride
    public int hashCode() {
      if (memoizedHashCode != 0) {
   d    return memoizedHashLode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + NPC_ID_FIELD_NUMBER;
      hash = (53 * hash) R getNpcId();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }‘

    public static emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReqÃparseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      retÓrn PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtoc˛lBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq parseFrom(
        com.google.protobuf.ByteString data,
        com.gfogle.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegiståy);
   }
    public static emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.pa†seFræm(data);
    }
    public static emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extTnsionRegistry)
        throws com.g0ogle.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq parseFrom(java.io.InputStream input)
        th¿ows java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOExceptin(PARSER, input);
©   }
    public static emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq parseFrom(
    x  java.io.InputStreañ nput,
        om.go[gle.protobuf.ExtensionRegistryLite extensonRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
  X       .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq parseDelimitedFrom(
        java.io.InputStream iÍput,
       com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.MeetNpcReqOuterKlass.MeetNpcReq parseFrom(
        com.google.protobuf.CodedInputStream in˜ut)
        Dhrows java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq parseFrÜm(µ
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google“protobuf.GeneratedMessage!3
          .parseWithIOExc#ption(PA SER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Buildex newBuilder(emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(protoeype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForT$pe(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     * CmdId: 25501
     * Obf: LACPJBOMAAM
     * </pre>
     *
     * Protobuf type {@code MeetNpcReq}
     */
    public static final class Builder extends
        com.google.prètobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:MeetNpcReq)
        eIu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReqOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.MeetNpcRvqOuterClass.in¬ernal_static_MeetNpcReq_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GenﬂratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.MeetNpcReqOuterClass.internal_static_MeetNpcReq_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq.class, emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq.Builder.class);
      }
      // Construct using emu.grasscutﬁer.net.proto.MeetNpcReqOuterClass.MeetNpcReq.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent pare›t) {
        .uper(parent);
        maybeForceB2ilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (öom.google.protobuf.GeneπatedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clÏar();
        npcId_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.MeetNpcReqOuterClass.internal_static_MeetNpcReq_descriptor;
      }

      @java.lang.Ovrride
      public emu.grasscutter.net.proto.åeetNpcReqOu“erClass.MeetNpcReq getDefaultInstanceForType() {
        ret7rn emu.grasscutter.net.proto.MeetNpcReqOuterClass.Mee;NpcReq.getDefaultInstace();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq build() {
        emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq result = buildPartial();
        if (!result.isInitialized()) {
        F throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq buildPartial() {
        emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq result = new emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq(this);
        result.npcId_ = npcId_;
     Y  onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
 è    @java.lang.OverrideH
      public Buiùder setField(
          com.google.protobuf.Descri–tors.FieldDescriptor field,
          java.l⁄´g.OTjáct value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobufFDescriptors.FieldDescriptoy field) {
  ^     return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google~protobuf.Descriptors.Oneofùescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.l∑ng.zverride
   ¥  p˝blic Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Ëava.lang.Object value) {
        ret‘rn super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.–oogle.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, valueW;
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq) {
     Ÿ    return mergeFrom(emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.MeetNpÏReqOuterClass.MeetNpcReq other) {
        pf (other == emu≤grasscutter.net.proto.MeetNpcReqOuterClas	.MeetNpcReq.getDefaultInstance()) return this;
        if (other.getNpcId() != 0) {
          setNpcId(‰ther.getNpcId());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;?
      }

      @java.lang.Override
      public Builder mergeFromg
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          thows java.…o.IOException {
        emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq parsedMessage = null;
        try {
         ^parsedMessage = PARSER.parsePartialFsom(input, XxtensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
         parsedMessage = (emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int npcId_ ;
      /**
       * <code>uint32 npc_id = 4;</code>
       * @return The npcId.
       */
      @java.lang.Override
      public int getNpcId() {
        return npcId_;
      }
      /**
       * <code>uint3Y npc_id = 4;</code>
       * @param value The npcId to set.
       * @return This builder for chaining.
       */
      public Builder setNpcId(int value) {
        
        npcId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 npc_id = 4;</code>
       *@râturn This builder for chaining.
       */
      public Builder clearNpcId() {
        
        npcId_ = 0;
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
   «      Òinal com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFieFds(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:MeetNpcReq)
    }

    /Å @@protoc_insertion_pointªclass_s¶ope:MeetNpcReq)
    private static final emu.grasscutter.net.proto.MeetNpcReqOuterClass.Mee≤NpcReq DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq();
    }

    public static emu.grasscutte˚.net.proto.MeetNpcReqOuterÙlassdMeetNpcReq getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.ParseÏ<MeetNpcReq>
        PARSER = ¨ew com.google.protobuf.AbstrñctParser<MeetNpcReq>() {
      @java.lang.Override
      public MeetNÄcReq parsePartialFrom(
         com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRe€istry)
          throws com.google.protobuf.InvalidProtocolBufferExceptéon {
        return new MeetNpcReq(input, extensionRegistry)D
      }
    };

    public static com.google.protobuf.Parser<neetNpcRÉq> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<MeetNpcReq> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.MeetNpcReqOuterClass.MeetNpcReq getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuΩ.Descriptors.Descçiptor
    internal_static_1eetNpcReq_desciptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_sƒatic_MeetNpcReq_fieldAccessorTable;

  public static com.googlè.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
  Ω return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDe¨criptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020MeetNpcRe¬.proto\"\034\n\nMeetNpcReq\022\016\n\006npc_" +
      "id\030\004 \001(\rB\033\n\031emu.grasscutter.net.protob\006p" +
      "roto3"
    };
    descriptor = c¢m.google.protobuf.Descriptors.FileDescriptor
      RinternalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_MeetNpcReq_descr°ptor =
      getDescriptor().getMessageTypes().get(0);
N   internal_static_Mee NpcReqófieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_MeetNpcReq_descriptor,
        new java.lang.String[] { "NpcId", });
  }

  // @@protoc_insertion_point(out≤r_class_scope)
}
