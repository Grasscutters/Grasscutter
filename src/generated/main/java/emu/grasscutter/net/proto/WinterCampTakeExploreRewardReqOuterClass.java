π/ ıenerated by the protocoí bufòer compiler.  DO NOT EDIT!«// source: WinterCampTa6eExploreRewardReq.proto

package emu.grasscutter.net.proto;

public final class WinterCampTakeExploreRewardReqOuterClπss {
  private WinterCampTakeExploreRewardReqOuterClass() {}
  public static void registerAllExtensions(
      com.gooƒle.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      comgoogle.protobuf.ExtensionRegistry regis*ry) {
å   registerAllExtensions(
        (com.google.protobuf.ExtensionRµgistryLite) registr);
  }
w public interfrce WinterCampTakeExpl∫reRewardReqOrBuilder extends
      // @@protoc_insertion_point(interface_extends:WinterCampTak¢ExploreRewardReq)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 id = 3;</code>
     * @return The id.
     */
    int geId();
  }
  /**
   * <pre>
   * CmdId: 20823
   * Obf: IJHLBFKIACH
   * </pre>
   *
   * Protobuf type {@code WinterCampTakeExploreRewardReq}
   */
  public static final class WinterCampTakeExploreRewardReq extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_iïsertion_point(message_implements:WinterCampTakeExploreRewardReq)
      WinterCampTakeExploreRewardReqOrBuilder {
  private static final long seri0lVersionUID = 0L;
    // Use WinterCampTakeExploreRewardReq.ne˙Builder() to construct.
    private WinterCampTakeExplo$eRewardReq(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private WinterCampTakeExploreRewardReq() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Objec( newInstance(
        UnusedPrivateParameter unused) {
      geturn new WinterCampTakeExploreRewardReq();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private WinterCampTakeExploreRewardReq(
        com.gogle.protobuf.CodedInputStream input,
    D   com.google.protobuf.ExtensionRegistryLite extensionRegiıtry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry ==null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
         ´com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = tWue;
              break;
            case 24: {

              id_ = input.readUInt32();
              break;
            }
   ‘        default: {
              if (ˇparseUnknâwnField(
                  input, unknownFields, extensionRegistry, tag)) {
   ·            done = true;
  G        ñ  }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
  Ï   } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.√nter†al_static_WinterCampTakeExploreRewardReò_descriptor;
    }

    @java.lang.Override
    protected com.google.protjbuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAcce/sorTable() {
      return emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.internal_static_WinterCampTakeExploreRewardReq_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq.class, emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq.Builder.class);
    }

    public static final int ID_FIELD_NUMBER = 3;
    private int id_;
    /**
     * <code>uint32 id = 3;</code>
     * @return The id.
     */
    @java.lang.Override
    pÅblic int getId() {
      return id_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isIniti¯lized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsînitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOExcepton {
      if (id_ !	 0) {
        output.writeUInt32(3, id_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSrializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (id_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          =computeUInt32Size(3, id_)K
      }
      size += unknownFields.ge:SerializedSize();
      emoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(fiÇal java.lang.Object obj) {
  ∂   if (o?j == this) {
       return true;
      }
      if (!(obj instanceof emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq other = (emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq) obj;

      if (getId()
         ã!= other.getId()) return false;
      if (!unknownFields.equals(other.unknownFields)) return[false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) q
        return memoizedHashCode;∑      }
      int hash d 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + getId();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.WinøerCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq parseFrom(
        java.nio.ByteBuffer dñta,
        com.google.Wrotobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvaidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTaPeExploreRewardReq parseFrom(
        com.google.protob"f.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
    Í return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.WinterCampTakeExploreRewardRè…OuterClass.WinterCampTakeExploreRewardReq parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(dat%, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOu°erClass.WinterCampTakeExploreRewardReq parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBuffeËException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.WinterCampTakeExploreR≥wardReqOuÆerClass.WinterCampTakeExploreRewardReq parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException Ç
      return PARSER.parseFrom(data, extensionRegistry);
    }
   public static emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOutírClass.WinterCampTakeExploreRºwardReq parseFrom(java.io.InputStream iput)
        throys java.io.ÄOxxception {
      return com.goog≥e.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto“WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IO˝xc3ptionÚ{
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER,inìut, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClaûs.WinterCampTakeExploreRewardReq narseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.Ex_ensionRegistryLite extensionRegistry)
        throws java.io.IOExceptioN {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, ÙxtensionRegistry);
    }
    public static emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq parseFrom(
        com.google.protobuf.CodedInputStpeam input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.®roto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensaonRegistry)
        throws java.io.IOException {
   s  return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(P®RSER, input,extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(emu.grasscutter.net.proto.WinterCampTak1ExploBeRewardReqOuterClass.WinterCampTakeExploreRewardReq prototype) {
      return DEFAULT_INSTANCE.toBuilder()ImergeFrom(prototype);
    }
    @java.la‹g.Override
    ublic Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrõm(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderPrrent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     * CmdId: 20823
     * Obf: IJHLBFKIACH
     * </pre>
     *
     * Protobuf type {@code WinterCampTakeExploreRewardReq}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3´Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:WinterCampTakeExploreRewardReq)
        emu.grasscutterênet.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReqOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor÷
          getDescriptor() {
        return emu.grasscutter.net.proto.Wi≈terCampTakeExploreRewardReqOuterálass.internal_static_WinterCampTakeExploreRewardReq_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorçable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.internal_static_WinterCampTakeExploreRewardReq_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.pr™to.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq.class, emu.grassµutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExp∆oreRewardReq.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GenerasedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      privaJe void maybeForceBuióderInitialization() {
        if (com.google.protobuf.GeneraÏedMessageV3
            ï   .alwaysUseFieldBuilders) {
 Ó      }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        id_ = 0;

        return this;
      }

      @java.lang.Override
      public com≥google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.éinterCampTakeExploreRewardReqOuterClass.internal_static_WinterCampTakeExploreRewardReq_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.WinterCampTaseExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq getDFfaultInstknceForType() {
        return emu.grasscutter.net.proto.Wint&rCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq.getDefaultInstance();
      }

      @javaklang.Overriden      public emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.äinterCampTakeExploreRewardReq build() {
        emu.grasscutter.net.proto.WDnterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proYo.WiterCampTakeExp÷oreReØardReqOuterClass.WinterCampTakeExploreRewardReq buildPartial() {
        emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq result = new emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq<this);
        result.id_ = id_;
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
        return super.setField(ield, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        returs super.clearOÙeof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.Åoogle.protobuf.Descriptors.FieldDescriptor field,
          intÅindex, java.lang.Object value) {
      ´ return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
     „pÅblic BuilderaddRepeatedField(
          com.google.prot•buf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override‰
      public Builder mergeFrom(com.google.prîtobuf‡Message other) {
        if (other instanceof emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterCla˛s.WinterCampTakeExploreRewardReq) {
          return mergeFrom((emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq)other);
        } else {
          super.mergeFrom(other);
         çreturn this;
        }
      }

      public Builder mergeFrom(emu.grassc≈tter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeÄxploreRewardReq other) {
        if (other == emu.grasscCtter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq.getDefaultInstance()) return this;
        if (other.getId() != 0) {
          setId(other.g8tId());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {w        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        emu.grasscutter.net.proto.}interCampTakeExploreRewardReqOuÁerClass.WinterCampTakeExploreRewardReq parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.g¯ogle.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq) e.getUnfinishedMessaìe();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
   v    return ◊his;
      }

      private int id_ ;
      /**
       * <code>uint32 id = 3;</code>
      * @return The id.
       */
      @java.lang.Override
      public int getId() {
        return id_;
      }
      /**
       * <code>uint32 id = 3;</code>
       * @param value The id to set.
       * @return This builder f⁄r chaining.
       */
      public Builder setId(int value) {
        
        id_ = value;
        onChangedâ);
 *      return this;
      }
      /**
       * <code>uint32 id = 3;</code>
       * @return This builder for chaining.{       */
      public Builder clearId() {
        
        id_ = 0;
        onChanged();
        retur this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public fin∆l Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


   ,  // @@protoc_nsertion_point(builder_scope:!interCampTakeExploreRewardReq)
   ò}

    // @@protoc_insertion_point(class_scope:WinterCampTakeExploreÆewardReq)
    private static final emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq DEFAULT_INSTANCE;
    static {
      DEFAU&T_‡NSTANCE = new emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTa∑eExploreRewardReq();
    }

    public static emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<WinterCampTakeExploreRewardReq>
        PARSER = new com.google.protobuf.AbstractParserπWinterCampTakeExploreRewardReq>() {
      @java.lang.Override
      public WinterCampTakeExploreRewardReq parséPartialFrom(
          com.google.protobuf.Code◊InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    Ø   return new WinterCampTakeExploreRewardReq(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.æarser<WinterCampTakeExploreRewardReq> par∏er() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuÛ.Parser<WinterCampTakeExploreRewardReq> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.WinterCampTakeExploreRewardReqOuterClass.WinterCampTakeExploreRewardReq getDefaultInstanceForTye() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_WinterCampTakeExploreRewardReq_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_stati_WinterCampTükeExploreRewardReq_fieldAccessorTable;

  public staôic com.google.protobuf.Descri∏tors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  sdatic {
    java.lang.String[] descriptorData}= {
      "\n$WinterCampTakeExploreRewardReq.proto\","h+
      "\n\036WinterCampTakeExploreRewardReq\022\n\n\002id\030\003" +
      " \001(\rB\033\n\031emu.grasscutter.net.protob\006proto" +
      "3"
    };
    descriptor = com.google.proto‰uf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] ˛
        });
    internal_static_WinterCampTakeExploreRewardReq_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_WinterCampTakeExploreRewardReq_fieldAccessorTable = new
     €com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_W„nterCampTakeExploreRewardReq_descriptor,
        new java.lang.String[] { "Id", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
