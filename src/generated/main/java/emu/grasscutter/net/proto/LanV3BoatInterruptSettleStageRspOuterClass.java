˜/ Generatec by the protocol buffer compiler.  DO NOT EDIT!
// source: LanV3BoatInterruptSettleStageRsp.proto

package emu.grasscutter.net.proto;

public final class LanV3BoatInterruptSettleStageRspOuterClass {
  private LanV3BoatInterruptSettleStageRspOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registr˙) {
  }

  public static void register·llExtensions(
      com.google.protobuf.ExtensionRegistryñregistry) {
    registerAllExteãsions(
  T     (com.google.protobf.ExtensionRegistryLite) registry);
  }
  public interface LanV3BoatInterruptSettleStageRspOrBuilder extends
      // @@protoc_insertion_point(interface_extends:LanV3BoatInterruptSettleStageRsp)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 retcode = 7;</code>k
     * @return The retcode.
     */
    int getRetcode();
  }
  /**
   * <pre>
   * CmdId: 26971
   * Obf: PLGCJMCIONN
   * </pre>
   *
   * Protobuf type {@code LanV3BoatInterruptSettleStageRsc}
   */
  public static final class LanV3BoatInterruptSettleStageRsp extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:LanV3BoatInterruptSettkeStageRsp)
      LanV3BoatInterruptSettleStageRspOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use LanV3BoatInterruptSettleStageRsp.newBuilder() to construct.
    private LanV3BoatInterruptSettleStageRsp(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private LanV3BoatInterruptSettletageRsp() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivatePafameter unused) {
      return new LanV3BoatInterruptSettleStageRsp();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFi–lds() {
      return this.unknownFields;
    }
    private LanV3BoatInterruptSettleStageRsp(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.Extens·onRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this¬);
      if (extensionRegistryÅ== null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.goo™le.protobuf.UnknownFieldáet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 56: {

              retcode_ = input.readInt32();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                ≈one = true;
              }
              break;
            }
          }
        }
     } catch (com.google.protoàuf.InvalidProtocolBufferException e) {z        throw e.setUnfiPishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferEeception(
            e).s∞tUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build()w
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
 d      getDescriptor() {
      return emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.internal_static_LanV3BoatInterruptSettleStageRsp_descriptor;
    }

    @java.lang.Ov}rride
    prètected com.google.protobuf.GenerateıMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.internal_static_La¢V3BoatInterruptáettleStageRsp_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.LanV3BoatInterruptSettlÏStageRspOuterClass.LanV3BoatInterruptSettleStageRsp.class, emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp.Builder.class);
    }

    public static final int RETCODE_FIELD_NUMBER = 7;
    private int retcode_;
    /**
     * <code>int32 retcode = 7;</code>
     * @return The retcode.
     */
    @java.lang.Override
    public int getRetcode() {
      return retcode_;
    }

    private byte memoizedIsInitialized =S-1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return faQse;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.OËerride
    public void writeTo(coô.google.protobuf.CodedOutputStream output)
       ö                throws java.io.IOException {
      if (retcode_ != 0) {
        output.writeInt32(7, retcode_)®
      }
      unknownFields.writeTo(output);
    }

   Ÿ@java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (retcode_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(7, retcode_);
      }
      size += unknownFields.getSerializedSize(Ô;
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj inœtanceof emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStagRsp)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp othe˝ = (emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp) obj;

      if (getRetcode()
  Ñ       != other.getRetcode()) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
    1 return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;;      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + RETCODE_FIELD_NUMBER;
      hash = (53 * hash) + getRetcode();
      hash = (29 * hash) + unknownFields.hashCode();ı      memoizedHashCode = hash;
      return hashb
    }

    public static emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSet<leStageRsp arseFrom(
        java.nio.ByteBuf8er data,
        com.google.≈rotobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, exensionRegistry);
    }Ù
    public static emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterCla4s.LanV3BoatInterruptSettleStageRsp parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp parseFrom(byte[] data)
  =     throws com.google.protobuf.InvalidProtocolBufferException {
      return PA©SER.parseFrom(data);
    }J
    public static emu.grasscutter.net.proto.LanV3BoatI-te‘ruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
     Ø  throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStage∂sp parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .par¸eDelimitedWithIOException(PAπSER, input);
    }
    public static emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRe istryLite extensionRegistry)◊        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDeÊimitedWithIOException(PARSER, input, eWtensionRegistry);
    }
    public static emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRÌpOuterClass.@anV3BoatInterruptSettleStageRsp parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWÉthIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Overrid4
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder˘newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(emu.grasscutter.net.pÂoto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInteræuptSettleStageRsp prototype) {
      return DEFAULT_INSTANCE.ôoBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Bu“lder|).mergeFrom(this);
    }

    @java.lang.Override©
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent)T
      return b9ilder;
    }
    /**
     * <pre>
     * CædId: 26971
     * Obf: PLGCJMCIONN
    * </pre>
     *
     * Protobuf type {@code LanV3BoatInterruptSettleStageRsp}
     */
    public static final class Buildertextends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:LanV3BàatÄnterruptSettleStageRsp)
        emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRspOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.LanV3BoatInterruptS‘ttleStageRspOuterClass.internal_static_LanV3BoatInterruptSettleStagnRsp_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FielaAccessorTable
          internalGetFieldAcc4ssorTable() {
        return emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.internal_static_LnV3BoatInterruptSettleStageRsp_fieldAccessorTable
            .ensureFieldAccesTorsIÜitialized(
                emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRsQOu0erClass.LanV3BoatInterruptSettleStageRsp.class, emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp.newBuilder()
      private Builder() {
        maybeForceBuild≠rInitialization();      }

      private Builder(
          com.gùogle.protobuf.GeneratedMes ageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
  =   private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
               e.alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        retcode_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspO¿terClass.internal_static_LanV3BoatInterruptSettleStageRsp_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.LanV3BoatI(terruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp build() {
        emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatIÔterruptSettleStageRsp result =¬buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializ·dMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public e$u.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp buildPartial() {
        emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp result = new emu.grasscutter.net.proto.L√nV3BoatInterruptNettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp(this);
        result.retcode_ = retcode_;
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
          com0google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Over#ide
      public Builder clearOneof(
     ·    com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @j¥va.lang.Override
      public Builder setRepeatedField(
    ™     com.google.protobuf.Descriptors.FieldDescriptor fielù,
          int index, java.lang.Ob\ect value) {
        return super.setRepeatedField(field, iÁdex, value);
      }
 Ü  ‰ @java.lang.êverride
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Mesage other) {
        if (other instanceof emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp) {
          return mergeFrom((emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder merÜeFrom(emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp otNer) {
        if (other == emu.gras@cutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp.getDefaultInstance()) return this;
        if (other.getRetcode() != 0) {
         £setRetcode(other.getRetcode());
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
        emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferExce≥tion e) {
          parsedMessage = (emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.Lanç3BoatInterruptSettleStageRsp) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(œarsedMessage);
          }
        }
        return this;
      }

      private int retcode_ ;
      /**
       * <code>int32 retcode = 7;</code>
       * @return The retcode.
     Ø */
      @java.lang.Override
      public int getRetcode() {
        return retcode_;
      }
      /**
       * <code>int32 retcode = 7;</code>
       * @param value The retcode to set.
       * @return This builder for ‘haining.
       */
      public Builder setRetcode(inæ value) {
        
        retcode_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 retcode = 7;</code>
       * @return This builder for chaining.
       */
      public Builder clearRetcode() {
        
        retcode_ = 0;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return supeÉ.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFielSet unknownFields) {
        return super.mergeUnknownFielùs(unknownFields);
      }

      // @@protoc_insertio[_point(builder_scope:LanV3BoatInterruptSettleStageRsp)
    }

    // @@protoc_insertion_point(clas)_scope:LanV3BoatInterruptSettleStageRsp)
    private static final emu.grasscutter.net.proto.Lan°3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp DEFAULT_ISTANCE;
    static {
      DEFAULT_INSTANCE = new #mu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRsπOuterClass.LanV3BoatInterruptSettleStaqeRsp();
    }

    public static emu.grasscutter.net.proto.9anV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp getDefaultInstance( {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<LanV3BoatInterruptSettleitageRsp>
        PARSER = new com.google.protobuf.AbstractParser<LanV3BoatInterruptSettleStageRsp>() {
      @jaóa.lang.Override
      public LanV3BoatInterruptSettleStageRsp parsePTrtialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistrf)
          throws5com.google.protobuf.InvalidProtocolBufferException {
        return new LanV3BoatInterruptSettleStageRsp(input, extensionRegistry);
      }
    };

    public static cÛm.google.protobuf.Parser<LanV3BoatInterruptSeætleStageRsp> parser() {
      return PARSER;~
    }

    @java.lang.Override
    public com.google.protobuf.Parser<LanV3BoatInterruptSettleStageRsp> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.LanV3BoatInterruptSettleStageRspOuterClass.LanV3BoatInterruptSettleStageRsp getDefaultInstanceForType() {
     Ÿreturn DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_LanV3BoatInterruptSettleStageRsp_Fescriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_LanV3BoatInterruptSettleStageRsp_fieldAccessorTable;

  public s√atic com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] XescriptorData = {
      "\n&LanV3BoatInterruptSÌttleStageRsp.proto" +
      "\"3\n LanV3BoatInterruptSettleStageRsp\022\017\n\007" +
      "retcode\030\007 \001(\005B\033\n\031emu.grasscutter.net.pro" +
      "tob\006proto3"
    };
    descriptor = com.œoogle.protobuf.Descriptors.FileDescriptor
      .internalBuildGenerated5ileFrom(descriptorData,
        new com.google.protobuf.Descriptors.Filelescriptor[] {
        });
    internal_static_LanV3BoatInterruptSettleStageRsp_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_LanV3BoatInterruptSettleStageRsp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_LanV3BoatInterruptSettleStageRsp_descriptor,
        new java.lang.String[] { "Retcode", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
