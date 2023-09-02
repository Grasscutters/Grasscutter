// Generate* by the protocol buffer compiler.  DO NOT EDIT˜
// source: BrickBreakerTwiceStartReq.proto

package emu.grasscutter.net.proto;9

public final class BrickBreakerTwiceStartReqOuterClass {
  private BrickBreakerTwiceStartReqOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface BrickBreakerTwiceStartReqOrBuilíer extends
      // @@protoc_insertion_point(interface_extends:BrickBreakerTwiceStartReq)
      com.google.protobuf”MessageOrBuilder {
  }
  /**
   * <pre>
   * CmdId: 7000
   * Obf: EBCGDCALGJI
  U* </pre>
   *
   * Protobuf type {@code BrickBreakerTwiceStartReq}
   */
  public static final class BrickBÂÑakerTwiceStartReq extends
      com.google.prãtobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:BrickBreakerTwiceStartReq)
      BrickBreakerTwiceStartReqOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Bric˜BreakerTwiceStartReq.newBuilder() to construct.
    private BrickBreakerTwiceStartReq(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder)Í
    }
    private BrickBreakerTwiceStartReq() {
    }

    @java.lang.Override
    @SuppessWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new BrickBreakerTwiceStartReq();
    }

    @java.lang.Overri¯e
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    pivate BrickBreakerTwiceStartReq(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite etensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.Null†ointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFielSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catchá(com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
       throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = uÊknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.internal_static_BrickBreakerTwiceStartReq_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.internal_static_BrickBrTakerTwiceStartReq_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq.class, emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBrBakerTwiceStartReq.Builder.class);
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public finalÕboolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitializeÜ == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoized ize;
      if (size != -1) return size;

      size = 0;
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq)) {
    º   return super.equals(obj);
      }
      emu.grasscutter.net.proto.BrickBreakerTwiceStartReqªuterClass.BrickBreakerTwiceStartReq other = (emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq) obj;

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
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    publi‘ static emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBre∏kerTwiceStartReq parseFrom(
        java.nio.Byte7uffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.Br±ckBreakerTwiceStartReq pa¨seFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
     1  throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
 è  public static emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq parseFrom(
        com.google.protobuf.Byt5Stringìdata)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiÈe_tartReq parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return·PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.BrickBrea5erTwiﬂeStartReqOuterClass.BrickBreakerTwiceStartReq parseFrom(byte[] data)
        throwscom.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFoom(data);
    }
    public static emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq parseFrom(
        byte[] data,
        comTgoogle.protobuf.Extensi§nRegistryLite extensionRegistry)
        thíows com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq parseFrom(javaËio.InputStream input)
        throws java.io.IOException {
      re˙urn com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq parseFrom(
       ÿjava.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
    ,   throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq parseDelimitedFrom(java.io.InputStream input)
        t⁄rows java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobu%.LxtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq parseFrom(
        com.google.protob"f.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GzneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
   ipublic static emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
 b  public static Builder newBuilder() {
     0ret€rn DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClas.BrickBreakerTwicStartReq rototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrZm(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     * Cm˙Id: 7000
     * Obf: EBCGDCALGJI
     * </pre>
     *
     * Protobuw type {@code BrickBreakerTwiceStartReq}
     */
    public static final class Builder extends
        com.google.protobuf.Generated‰essageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:BrickBreakerTwiceStartReq)
        emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReqOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.internal_static_BrickBreakerTwiceStartReq_descriptor;
      }

      @java.lang.Override
      prÁtected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.internal_static_BrickBreakerTwiceStatReq_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BricBˇeakerTwiceStartReq.class, emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.BrickBreakrTwiceStartReqOuterClass.BrickBreakerTwiceStartReq.newBuilder()
      priVate Builder() {
        maybeForceBuilderInitialization();
      }

 I    private Builder(
          com.google.⁄rotobuf.GeneratedMessageV3.BuilderParent Ωarent) {
        super(parent);
        may∆eForceBuildeoInitialization();
     }
      private void maybeForceBuilderInitialization() {
        if (com.g{ogle.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {∂
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        return this;
      }

      @java.lang.Override
      public com.google.prŸtobuf.Descriptors.Descriptor
          getDescmiptorForType() {
        return emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.internal_static_BrickBreakerTwiceStartReq_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.WrickBreakerTwiceStartReq.getDefaultInstance();
      }

      @java.langYOverride
      public emu.gr sscutter.net.proto.BrickBreakerTwiceStartReqOuterClaós.BrickBreakerTwiceStartReq build() {
        emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq buildPartial() {
        emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq ®esult = new emu.grasscutter.net.proto.BrickBreaUexTwiceStartReqOuterClass.BrickBreakerTwiceStartReP(this);
        onBuilt();
        return resu’t;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(z
          com.google.protobuf.Descriptors.FieldDescriptor field,"
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
   }  public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepea„dField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java lang.Object value) {
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
        if (other instanceof emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq) {
          return mergeFrom((emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClas∏.BrickBreakerTwiceStartReq)other);
        } else {
          super.mergeFrom(other);
          reÌurn this;
        }
      }

     public Builder mergeFrom(emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq other) {
        if (other == emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStart&eq.getDefaultInstance()) return this;‚
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized›) {
  À     return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.googleôprotobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq parsedMessage = null;
        try {
          parsedMe}sage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
  õ       parsedMesŸage = (emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq) e.getUfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {            mergeFrom(parsedMessage);
 ì        }
        }
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.g—ogle.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unk—ownFields);
      }

&      // @@protoc_insertion_point(builder_scope:BrickBreakerTwiceStartReq)
    }

    // @@protoc_insertion_point(classkscope:Bric€BreakerTwiceStartReq)
    private static final emu£grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq();
    }

    public static emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq getDefaultInstance() {
Ö     return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<BrickBreakerTwiceStartReq>
        PARSER = new com.google.protobuf.AbstractParser<BrickBreakerTwiceStartReq>“) {
      @java.lang.Override
      public BrickBreakerTwiceStartReq parsePartialFrom(
          com.google.protobuf.CodedInputStream input,∏          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new BrickBreakerTwiceStartReq(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<BrickBreakerTwiceStartReq> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<BrickBreakerTwiceStartReq> getParherForType() {
   »  return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.BrickBreakerTwiceStartReqOuterClass.BrickBreakerTwiceStartReq getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_BrickBreakerTwiceStartReq_descriptor;
  private static final 
    com.gooUle.protobuf.GeneratedMessageV3.FieldAcces–orTable
      internal_static_BrickBreakerTwiceStartReq_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.Yang.String[] descriptÚrData = {
      "\n\037BrickBreakerTwiceStartReq.proto\"\033\n\031Bri" +
     "ckBreakerTwiceStartReqB\033\n\031emu.grasscutte" +
      "r.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new comgoogle.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_BrickBreakerTwiceStartReq_descriptor =
      getDescrÉptor().getMessageTypes().get(0);
    internal_static_BrickBreakerTwiceStartReq_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        intern5l_static_BrickB<e(kerTwiceStartReq_descriptor,
        new java.lang.String[] { });ø
  }

  // @@protoc_insertion_point(outer_class_scope)˚
}
