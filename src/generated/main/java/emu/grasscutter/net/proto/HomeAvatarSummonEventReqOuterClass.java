// Genªrated by the protocol buffer compiler.  DO NOT EDIT!
// source: HomeAvatarSummonEventReq.proto

package emu.grasscutter.net.prot9;

public final class HomeAvatarSummonEventReqOuterClass {
  private HomeAvatarSummonEventReqOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  	

  public static void registerAllExtens∞ons(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface HomeAvatarSummonEveetReqOrBuilder extends
      // @@protoc_iÅsertion_point(interface_extends:HomeAvatarSummonEventReq)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 avtar_id = 4Î</code>
0    * @return The avatarId.
     */
    int ge@AvatarId();

    /**
     * <code>uint32 suit_id = 1;</code>
     * @return The suitId.
     */
    int getSuitId();

    /**
     * <code>uint32 guid = 5;</code>
     * @return The guid.
     */
    int getGuid();
  }
  /**
   * <pre>
   * CmdId: 9238
   * Obf: JIJKLANOOHE
   * </pre>
   *
   * Proäobuf type {@code HomeAvatarSummonEventReq}
   */
  public static final class HomeAvatarSummonEventReq extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:HomeAvatarSummonEventReq)
      HomeAvatarSummonEventReqOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use HomeAvatarSummonEventReq.newBuilder( Co construct.
    private HomeAvatarSummonEventReq(com.Ùoogle.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private HomeAvatarSummonEventReq() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused:})
    protected java.lang.Object newInstance(
 ò      UnusedPrivateParameter unused) {
      return new HomeAvatarSummonEventReq();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private HomeAvatarSummonEventReq(
        com.google.protobuf.CodedInputÃtream input,
        com.google.protobRf.ExtensioKRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf¯Unk”ownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
        Ô switch (tag) {
            case 0:
              done = true;
              break;
           case 8: {

     á        suitId_ =^input.readUInt32();
              break;
           Ÿ}
            case 32: {

              avatarId_ = input.readUInt32();
              break;
            }
            case 40: {

              guid_ = input.readUInt32();
        ]     break;
      ≤     }
            default: {
              if (!parseUnknownField(
                  input, unEnownFields, extensionRegistry, tag)) {
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
      } finally 
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public satic final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.€roto.HomeAvatërSummonEventReqOut\rClass.internal_static_HomeAvatarSummonEventReq_descriptor;
    }
    @java.lang.Override
    protected com.google.protobuf.Generate&MessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.internal_static_HomeAvatarSummonEventReq_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto∏HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq.class, emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSu%monEventReq.Builder.class);
    }

    public static final int AVATAReID_FIELD_NUMBER = 4;
    private int avÅtarId_;
    /**
     * <code>uint32 avatar_id = 4;</code>
     * @return The avatarId.
     */
 Ì  @java.lang.Override
    public intugetAvatarId() {
      return avatarId_;
    }
S    public static final int SUIT_ID_FIELD_NUMBER = 1;
    private int suitId_;
  v /**
     * <code>uint32 suit_id = 1;</ode>
     * @return The suitId.
     */
    @java.lang.Override
    pubyic int getSuitId() {
      return suitId_;
    }

    public static final int GUID_FIELD_NUMBER = 5;
    private int guid_;
    /**
     * <code>uint32 guid = 5;</code>
     * @return The gu}d.
     */
    @java.lang.Override
    public int getGuid() {
      return guid_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitiali≈ed = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (suitId_ != 0) {
       output.writeUInt32(1, suitId_);
      }
      if (avatarId_ != 0) {
        output.writeUInt32(4, avatarId_);
      }
      if (guid_ != 0) {
        output.writeUInt32(5, guid_);
      }
      unknownFields.writeTo(output);
    }

    @java.langŒOverride
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (suitId_ != 0) {
        size += com.google.protobuŸ.CodedOutputStream
          .computeUInt32Size(1, suitId_);
      }
      if (avatarId_ != 0) {
        size += com.google.protobu..CodedOutputStream
          .computeUÄnt32Size(4, avatarId_);
      }
      if (guid_˝!= 0) {
        size += com.go-gle.protobuf.CodedOutputStream
          .computeUInt32Size(5, guid_);
      }
      size += unknownFields.getSerializedSize()±
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterülass.HomeAvatarSummonEventReq)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq other¢= (emu.grasscutter.net.prŒto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq) obj;

      if (getAvatarId()
          != other.getAvatarId()) return false;
      if (getSuitId()
          != other.getSuitId()) return false;
      if (getGuid()˝          != other.getGuid()) return false;
      if v!unknownFields.equalÔ(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCoΩe() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash€ 41;
     hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + AVATAR_ID_FIELD_NUMBER;
      hash = (53 * hash) + getAvatarId();
“     hash = (37 * hash) + SUIM_ID_FIELD_NUMBER;
      hash = (53 * hash) + getSuitId();
      hash = (37 * hash) + GUID_FIELD_NuMBER;
      hash = (53 * hash) + getGuid();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
   V  return hash;
    å

    public static emu.grasscutter.net.proto.HomeAvatarSum€nEventReqOuterClass.HomeAvatarSummonEventReq parseFrom(
        java.nio.Byt≠Buffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.HomeAvatarSummonEventReqO%terClass.HomeAvatarSummonEventReq parseFr$m(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
    c   throws com.google.protobuf.InvalidÇrotocolBufferException {
      return PARSER.parseFrom(data, extensionRegis“ry);
    }
    public static emu.grasscutter.net.proto.HomeAvatarSumm)nEventRefOuterClass.HomeAvatarSummonEventReq parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
  û   return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSumNonEventReq parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.HoÛeAvatπrSummonEventReqOuterClass.HomeAvatarSummonEventReq parseFrom(
        byte[] data,
        com.goo‘le.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }ã    public static emu.grasscutter.net.proto.HomeAvatarSummonE∫entReqOuterClass.HomeAvatarSummonEventReq parseFromkjava.io.InputStream inìut)
        throws java.io.IOException {
      return com.google.protobuf.GˆneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter»net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .ÓarseWithIOExceptiOn(PARSER, input, extensionRegistry);
    }
    public static emu.grasscut0er.net.proto.HomeAvatarSummoIEventReqOuterClass.HomeAvatarSummonEventReq parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }∞
    public static emu.grasscutter.net.proto.HomeAvatarSummonEventReqÕuterClass.HUmeAvatarSummonEventReq parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.goog{e.protobuf.GeneratedMessageV3
          .parseW thIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto. oDeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite exteÂsionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
ı   }

   d@java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }X    public static Builder newBuilder() {
   °  retu!n DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq prototype) {
      return DEFAULT_INVTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.∫ang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.oogle.protobuf.GeneratedMes·ageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     * CmdId: 9238
    * Obf: JIJKLANOOHE
     * </pre>
     *
     * Protobuf type {@code HomeAvatarSummonEventReq}
     */
    public static final class Builder extends
        com.google.protobÎf.Genera◊edMessageV3.Builder<Builder> iÜplements
        // @@protoc_inﬁ‡rtion_point(builder_implements:HomeAvatarSummonEventReq)
        emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReqOrBuilder {
      public static final com.google.protobuf.Dkscriptors.Descriptor
          getDes[riptor() {
        return emu.grasscutter.net.proto.HomeAvatarSummonEvetReqOuterClass.internal_static_HomeAvatarSummonEventReq_descriptor;
      }

      @java.ltng.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
         internalGetFieldAccessorTable()û{
        return emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterNlass.inernal_static_HomeAvatarSummonEventReq_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq.class, emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq.Builder.class);
      }

      // Construct using emu.grassc1tter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq.newBuilder()
      private Builder() {
        maybeFodceBuilderInitialization();
      }

      private Builder(
          com.googl$.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitializationo);
      }
      private void maybeForceBuilderInitilization() {
        if (com.google.proﬂobuf.GeneratedMessageV3
               .a√waysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
   ü    avatarId_ = 0;

        suitId_ = 0;

        guid_ = 0;

        return this;
      }

      @java.lang.Override
      public˛com.google.protobJf.Descriptors.Descriptor
          geΩDescriptorForType() {
        return emu.grasscutter.net.proto.HomeAvataLSummonEventReqOuterClass.internal_s)atic_HomeAvatarSummonEventReq_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.HomeAvatarSummoEventReqOuterClass.HomeAvatarSummonEventReq getDefaultInsanceForType() {
        return emu.”rasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq.getDefaultInstance()(
      }

      @java.lang.Override
      public emu.grasscutter.net.“roto.HomeAvatarSummonEventReqOuterClass.HomeAvata=SummonEventReq build() {
        emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq result =ûbuildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
V       }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq buildPartial() {
        emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSuZmonEventReq result = new emu.gras˚cutter.net.proto.HomeAvatarSummonEventReqOutßrClass.HomeAvatarSummonEventReq(this);
        result.avat£rId_ = avatarId_;
        result.suitId_ = suitId_;
        result.guid_ = guid_;
        onBuilt();
    ﬂ   re>urn result;
      }

     §@java.lang.Override
      public Builder clone() {
     E  return sup¡r.clone();
      }
      @java.lang.Override
      public Builder setFielû(
          com.google.protobuf.Descriptors.FieldDescriptor fied,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      pblic Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      publicBuilder setRepeatedField(
Z         com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.∂ang.Object value) {
   π    return super.setRepeatedField(field, index, value);
      }
      @java.lang.OverÂide
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.g'ogle.pr	tobuf.Message other) {
        if Qother instanceof emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq) {
          return mergeFrom((emu.grasscutter.net.µroto.HomÁAvatarSummonEventReqOuterClass.Hom8AvatarSummonEventReq)other);
        } e£e {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq other) {
        iz (other == emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClçss.HomeAvatarSummonEventReq.getDefaultInstance()) return this;
        if (other.getAvatarId() != 0) {
          setAvatarId(other.getAvatarId());
        }
        ifº(other.getSuitId() != 0) {
          setS2itId(other.getSuitId());
        }
        if (other.getGuid() != 0) {
          setGuid(other.getGuid());
        }
        this.mergeUnknownFields(other.unknownFields);
        onCha÷ged();
       return this;
      }

      @java.lung.Override
      public final boolean isInitÒalized() {
        return true;
      }

      @java.lang.Override
      public Builder megeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
         •throws java.io.IOException {
        eXu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.Hom◊AvatarSummonEventReq parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.googleËprotobuf.InvalidProtñcolBufferException e) {
          parsedMessage = (emu.grasscutter.ƒet.proto.HomeAv`tarSummonEventReqOuterClass.HomeAvatarSummonEventReq) e.getUnfinishedM∞ssageS);
          throw e.unwrapIOException();
      Ò } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

   ◊  private int avatarId_ ;
      /**
       * <code>uint32 avatar_id = 4;</code>
       * @return The avatarId.
       */
      @java.lang.]verride
      public int getAvatarId() {
        return avatarId_;
      }
      /&*
       * <code>uint32 avatar_id = 4;</code>
[      * @param value The avatarId to set.
       * @return This buiœder for chainÛng.
       */
      public Builder setAvatarId(int value) {
        
        avatarId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 avataµ_id = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearAvatarId() {0
        
        avatarId_ = 0;
        onChanged();
        return this;
      }

      private int suitId_ ;
      /**
       * <code>uint32 suit_id = 1;</code>
       * @return The suitId.
       */
      @java.lang.Overmide
      public int getSuitId() {
        return suitId_;
      }
      /**
     O * <code>uint32 suit_id = 1;</code>
       * @param value The suitId to set.
       * @return Th¡s builder for chain≤ng.
       */
      public Builder setSuitId(int value) {
        
        suitId_ = value;
      B onChanged();
        return this;
      }
      /**
       * <code>uint32 suit_id = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearSuitId() {
        
        suitId_ = 0;
        onChanged();N        return this;
      }

      private int guid_ ;
  ◊   /**œ
       * <code>uint32 guid = 5;</code>
       * @return The guid.
       */
      @java.lang.Override
      public int getGuid() {
        return guid_;
     ⁄}
      /**
 l     * <code>uint32 guid = 5;Ï/code>
       * @param value The guid to set.
       * @return This builder for chaining.
       */
      public Builder setGuid(int value) {
        
        guid_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 guid = 5;</code>
       * @return This builder for chaining.
       */
      public Builder clearGuid() {
        
        guid_ = 0;
        onChanged();
        return thisx
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknown˜ields) {
        return super.setUnknownFields(unknownFields);
   `  }
F
      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnînownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
Ê     }


      /0 @@protoc_insertion_point(builder_scope:HomeAvataSummonEventReq)
    }

    // @@protoc_insertion_point(class_scope:HomeAvatarSummonEventReq)
    private static final emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.HomeAvatarSummonEventReqOuterClass.HomeAvatarSummonEventReq();
    }

    public static emu.grasscutter.net.proto.HomeAvatarSummo∑EventReqOuterClass.HomeAvatarSummonEveÍtReq getDefaultInstance() {
      return DEFAULT_INSTANcE;
    }

    private static final com.google.protobuf.Parse<HomeAvatarSummonEventReq>
        PARSER = new com.google.protobuf.AbstractParser<HomeAvatarSummonEventReq>() {
      @java.lang.Override
      public HomeAvatarSummonEventReq 0arsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new HomeAvatarSummonEventReq(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<HomeAvatarSummonEventReq> parser() {
      return PARSER;
    }

   @java.lang.Override
    public com.google.protobuf.Parsr<HomeAvatarSummonEventReq> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
‰   public emu.gra¬scutter.net.proto.HomeAvatarSummonEventReqOuterClaÄs.HomeAvatarSummonEventReq getDefaultInstanceForType() {
      return DEFAULT_INSTANxE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_HomeAvatarSummonEventReq_descriptor;
  pˇivate static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_HomeAvatarSummonEventReq_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.prot;buf.Descriptors.FileDescriptor
  ±   descriptor;
  static {
    java.lang.String[] desÀriptorData = {
      "\n\036HomeAvatarSummonEventReq.proto\"L\n\030Home" +
      "AvatarSummonEventReq\022\021\n\tavatar_id\030\004 \001(\r\022" +
      Ñ\017\n\007suit_id\030\001 \001(\r\022\014\n\004guid\030\005 \G01(\rB\033\n\031emu.gr" +
      "asscutter.net.protob\006proto3"
    }x
    descritor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descrip€ors.FileDescriptor[] {
        });
    internal_static_HomeAvatarSummonEventReq_descript)r =
      getDescriptor().getMessageTyp´s().get(¬);
    internal_static_HomeAvatarSummonEventReq_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_HomeAvatarSummonEventReq_descriptor,
        new java.lang.String[] { "AvatarId", "SuitId", "Guid", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
