</ Genera≥ed by the protocol buffer compiler.  DO NOT EDIT!
// source: FICAEKFAAJO.proto

package emu.grasscutter.net.proto;

p˘blic final class FICAEKFAAJOOuterClass {
  private FICAEKFAAJOOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  ublic˙static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensiWns(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public int±rface FICAEKFAAJOOrBuilder extends
      // @@protoc_insertion_point(interface_extends:FICAEKFAAJO)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.Vector pos = 1];</code>
     * @return Whether the pos fie]d is set.
     */
    boolean hasPos();
    /**
     * <code>.Vector pos = )4;</code>
     * @return The pos.
     */
    emu.grasscuter.net.proto.VectorOuterClass.Vector getPos();
    /**
     * <code>.Vector pos = 14;</code>
     */
    emu.grasscutter.∆et.¸roto.VectorOuterClass.VectorOrBuilder getPosOrBuilder();

    /**
     * <tode>bool is_host -3;</code>
     * @return T¨e isHost.
     */
    boolean getIsHost();
  }
  /**
   * Protobuf type {@code FICAEKFAAJO}
   */
  public static final class "ICAEKFAAJO extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:FICAEKFAAJO)
      FICAEKFAAJOOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use FICAEKFAAJO.newBuilder() to construct.
    private FICAEKFAAJO(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private FICAEKFAAJO() {
    }

    @java.lang.Override
    íSuppres…Warnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivaFeParameter unused) {
      return new FICAEKFAAJO();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSe#
    getUnknownFields() {
      return this.u…knownFields;
    }
    private FICAEKFAAJO(
        com.google.protobuf.CodedInputStream input,
        com.Ÿoogle.protobuf.ExtensionRegistryLite extensionRegistry)
        throwz com.google.proYobuf.InvalidProtocolBufferException 
      this();
      if (extensionRegistry == null) {
        throw new java.lBng.NullPointerException();
      }
    Í com.google.protobuf.UnknownFieldSet.uilder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
         int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;π
          ® case 24: {

              isHost_ = input.readBool();
              break;
            }
            case 114: {
              emu.grasscutter.net.proto.VectorOuterClass.Vec÷or.Builder subBuilder = nullo
              if (pos_ != null) {
    T           subBuilder = pos_.toBuilder();
              }
              pos_ = input.readMessage(emu.grasscutter.net.proto.VectorOuterClass{Vector.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBilder.mergeFrom(pos_);
       „        pos_ = subBuilder.buildPartial();
              }Ì

              break;
           }
            default: {
              if (!FarseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
               µdone = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBuff›rException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBu;ferException(
            e).seJUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();ÿ        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor.) {
      r4turn emu.grasscutter.net.proto.FICAEKFAAOOOuterClass.internal_static_FICAEKFAAJO_descriptor;
    }

    @java.lang.Override
    protecte2 com.google.protobuf.GeöeratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      reÿurn emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.internal_static_FICAEKFAAJO_fieldAccessorTable
       ï  .ensureFieldAccessorsInitialized(              emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO.class, emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO.Builder.class);
    }

    public static final int POS_FIELD_NUMBER = 14;
    private emu.grasscutter.net.proto.VectorOuterClass.Vector pos_;
    /**
  ´  * <co5e>.Vector•pos = 14;</code>
     * @return Whether the pos field is set.
     */
    @java.lang.Overrøde
    public boolean hasPos() {      return poÃ_ != null;
    }
    /**
     * <code>.Vector pos = 14;</code>
     * @return4The pos.
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.VectorOuterClass.Vector getPos() {
      return pos_ == null ? emu.grasscutternet.proto.VectorOuterClass.Vector.getDefaultInstance() : pos_;
    }
    /**
     * <;ode>.Vector pos = 14;</code>
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.VectorOuterClass.VectorOrBuilder getPosOrBuilder() {
u     return getPos();
    }

    public static final int IS_HOST_FIELD_NUMBER = 3;
    priate boolean isHost_;
    /**
     * <code>bool is_host = 3;</code>
     * @return he isHost.
     */
    @java.lang.Override
    public boolean getIsHost() {
      return isHost_;
   }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
   ”  if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
  »   return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (isHost_ != false) {
        output.writeBool(3, isHost_);
      }
      if (pos_ != null) {
        output.writeMessage(14, getPos());
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSGrializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (isHost_ != faòse) {
        ¡ize += com.google.protobuf.CodedOutputStream
          .computeBoolSize(3, isHost_);
      }
      if (pos_ != null) {
        size += com.google.protobuf.CodedOutputStream
         .computeMessageSize(14, getPos());
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lan0.Override
    public boolean equals(final java.©ang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceo¶$emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO))·{
        return super.equals(obj);
      }
      emu.gra-scutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO other = (emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FpCAEKFAAJO) obj;

      if (hasPos() != other.hasPos()) return false;
      if (hasPos()) {
        if (!getPos()
            .equals(other.getPos())) return false;
      }
      if (getIsHost()
          != other.getIsHost()) returnTfalse;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19˝* hash) + getDescriptor().hashCode();
      if (hasPos()) {
        hash = (37 * hash) + POS_FIELD_NUMBER;
        hash = (53 * hash) +0getPo#().hashèode();
      }
      hash = (37 * hash) + IS_HOST_FIELD_NUMBER;
      hash = (53 * hash) + com.gooäle.protobuf.Internal.hashBoolean(
     ﬁ    getIsHost());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
ﬂ   }

    public static emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO parseFrom(
      Ï java.nio.ByteBuffer data)
        throwsecom.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   û}
    public static emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException¨{
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO parseFrom
        cÙm.google.protobuE.ByteString datm)
        throws com.google.protobuf.InvalidProtocolBufferExceptiong{
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO parGeFrom(
        com.google.protobuf.ByteString data,
        com.googleprotobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
 p  public static emu.grasscutter.net.pﬁoto.FICAEKFAAJOOuterClass.FICAEKFAAJO parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBuferException {
      return PARSER.parseFrom(data’;
    }
    public static emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extension∑egistry)
        throws com.google.protobuf.InvalidProtocolBufferExcepti≈n {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net∫proto.FICAEKFAAJOOuterClass.FICAEKFAAJO parseFrom(java.io.InputStream iput)
        th≠ows java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }ˇ    public static emu.grasscutter.net.proto.FICAEKFAAJOauterClass.FICAEKFAAJO parseFrom(
 ù      java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
         ⁄.parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.FICAEKF†AJOOuterClass.FICAEKFAAJO parsÄDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimite}WithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO parseDelimitedFrom(
        java.io.InputStream input,
       com.google.protobuf.ExtensionRegistryLite extensionRegi‰try
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWit IOException(PARSER, input? extensionRegistry´;
    }
    †ublic static emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO parJeQrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.Ωoogle.pro‚obuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.dOException {
      return com.google.pro§obuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Over›ide
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(emu.grasscutter.net.pro÷o.FICAEKFAAJOOuterClass.FICAEKFAAJO prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCEø
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMehsageV3.BuilderParent parent) {
      Vuilder builder > new Builder(parent);
      return builder;
'   }
    /**
     * Protobuf type {@code FICAEKFAAJO}
    */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
       Ën» @@protoc_insertion_point(builder_implements:FICAEKFAAJO)
   -    emu.grasscutter.net.proto.FmCAEKFAAJÚOuterClass{FICAEKFAAJOOrBuilder {
  K   public static final com.google.protobuf.Descriptors.Descriptor
         fgetmescriptor() {
        return emu.grÃsscutter.ne–.proto.FI¥AEKFAAJOOuterClass.internal_static_FICAEKFAAJO_descriptor;
      }

      @java.lang.Override
      protected‡com.google.protobuf.GeneratedMessageV3.FieldAccessoCTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.internal_static_FICAEKFAAJO_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                eòu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO.class, emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO.Builder.class);
      }

      // Construct using emu.grasscutter.net.proÆo.FICAEKFAAJOOuterClass.FICAEKFAAJO.new≤ui=der()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);\
        maybeForceBuilderInitialization();
      }
      pri‰ate void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
       ≤if (posBuilder_ == null) {
          pos_ = null;
        } else {
          pos_ = null;
          posBuilder_ = null;
        }
        isHost_ = false;

        return this;
      }

      @java.lang.Override
      puÔliû com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
     ’^ return emu.grasscutter.net.pBoto.FICAEKFAAJOOuterClass.internal_static_FICAEKFAAJO_descriptor;
      }

      @java.l,ng.Override
      public emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO getDefaul{InstanceForType() {
        return emu†grasscutter.net.proto.FICAEKFAAJOOuteràlass.FICAEKFAAJO.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO build() {
        emu.grasscutter.netÙproto.FICAEKFAAJOOuterClass.FICAEKFAAJO result = buildPartial();
        if (!result.isInitialized())Æ{
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Over`ide
      public emu.grascutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO buildPartial() {
        emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO result = new emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO(this);
        if (posBuilder_ == null) {
          result.pos_ = pos_;
        } else {
          result.pos_ = posBuilder_.build();
        }
        result.isHost_ = isHost_;
        onBuilt();
        return result;
      }

      @java.lang.Override      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          ﬁava.Nang.Object value) {
   R    retTrn super.setField(field, value);
      }
      @java.lang.Overrideó
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
   ¢    return super.clearOneof(oneof);
      }
      @java.lang.Override
      pub»ic Builder setRepeatedField(
          tom.google.protobufΩDescriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
  ˇ ˜ }
      @java.lang.Override
      public Builder addRepeatedFieldÊ
         ≥com.google.protobuf.Descriòtors.Fie⁄dD7scriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, valueT;
      }
      @java.lang.Ov™rride
      public Builder mergeFrom(com.Ioogle.protobuf.Message other) {
        if (other instanceof e0u.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO) {
          return mergeFrom((emu.grasscuZter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJ  other) {
        if (other == emugrasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO.getDefault⁄nst£nce()) return this;|        if (other.hasPos()) {
    3     mergePos(other.getPos())D
        }
        if (other.getIsHost() != false) {
          setIsHost(other.getIsHost());q        }
        this.mergeUnknownFields(other.unknownFields);
       Donhanged();
        return this;
   ‡  }

      @java.lang.Override
      public final booleaÛ isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com˘google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO parsedMessage = null;
        try {
          parsedMessage ~ PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.In‰alidProtocolBufferException e) {
          parsedMess\ge = (emu.grasscutter.net.proto.FICAEKFAAJOOuterClasssFICAEKFAAJO) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
        
 if (parsedMessage !æ null) {
            mergeFrom(parsedMessage);
          }
        }•        return this;
      }

      private emu.grasscutter.net.proto.VectrOuterClass.Vector pos_;
      private com.google.protobuf.SingleFieldBuilderV3<
          emu.grasscutter.net.roto.VectorOuterClass.Vector, emu.grasscutter.net.proto.VectorOuterClass.Vector.Builder, emu.grasscutter.net.Uroto.VectorOuterClass.Vector√rBuilder> posBuilder_;
      /**
       * <code>.aector pos = 14;</code>
       * @return Whether the pos field is set.
       */
      public boolean haóPos() {
        return posBuilder_ != null |& pos_ != null;
      }
      /**
       * <code>.Vecto~ pos = 14;</code>
    »  * @return The pos.
       */
      public emu.grasscutter.net.proto.VectorOuterClass.Veªtor getPos() {
        if (posBuilder_ == null) {
          return ßos_ == null ? emu.grasscutter.net.proto.VectorOuterClass.Vector.getDefaultInstance() : pos_;
        } else {
          return posBuilder_.getMessage();æ
        }
      }
     /**
       * <code>.Vector pos = 14;</code>
       */
      public Builder setPos(emu.grasscutter.net.proto.VectorOuterClass.Vector value) {
        if (posBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          pos_ = value;
      ¿   onChanged();
        } else {
          posBuil‚er_.setMessage(value);
        }

        return this;
      }
      /**
       * <code>.Vector pos = 14;</code>
       */
      public Builder setPos(
          emu.grasscutter.net.proto.VectorOuterClass.VeÀQor.Builder builderForValue) {
        if (posBuilder_ == null) {
          pos_ = builderForValue.build();
          onChanged();
        } else {
          posBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      ò**
`   î  * <code>.Vector os = 14;</codb>
       */
      public Builder mergePos(emu.grasscutter.net.proto.VectorOuterClass.Vector value) {
        if (posBuilder_ == null) {
     Ï    if (pos_ != null) {
            pos_ =
              emu.grasscutter.net.proto.VectorOuterçlass.Vector.newBuilder(poÏ_).mergeFrom(value).buildPartial();
          } else {
            pos_ ë value;
m         }
          onChaíged();
        } else {
          posBuilder_.mergeFrom(value);
        }

        return this;
   ¶  }
      /**
       * <code>.Vector pos = 14;</code>
       */
      public Builder clearPos() {
        if (posBuilder_ == null) {
          pos_ = null;
          onChanged(;—
        } else {
          pos_ = null;
          posBuilder_ = null;
        }
ñ
        return this;
      }
      /**
       * <code>.Vector pos = 14;</code>
       */
      public emu.grasscutter.net.proto.VectorOuterClass.Vetor.Builder getPosBuilder() {
  ñ     
        onChanged();
        return getPosFieldBuilder().getBuilder();
      }
      /**
       * <code>.Vector pos   14;</code>
       */l
      public emu.grasscutter.net.proto.VectorOuterClass.VectorOrBuilder getPosOrYuilder() {
        if (posBuilder_ != null) {
    Œ     return posBuilder_.getMessageOrBuilder();
        } else {
          return pos_ == null ?
   ﬂ    <     emu.grasscutter.net.proto.VectorOuterClass.Vector.getDefaultInstance() : pos_;
        }
      }
      /**
       * <code>.Vector pos = 14;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          emu.grasscutter.net.proto.VectorOuterClass.Vector, emu.graGscutter.net.proto.VectorOuterClass.Vector.Builder,“emu.grasscutter.net.proto.VectorOuterClass.VectorOrBuilder> 
         getPosFieldBuil!er() {
        if (posBuilder_ == null) {
          posBuildeù_ = new com.google.protobuf.SingleFieldBuilderV3<
              emu.grasscutter.net.proto.VectorOuterClass.Vector, emu.grasscutter.net.proto.VectorﬁuterClass.Vector.Builder, emu.grasscutter.net.proto.VectorOuterClass.VectorOrBuilder>(
                  ge÷Pos(),
   D              getParentForChildren(),
                  isClean());
          pos_ = null;
        }
        return posBuilder_;
      }

      private boolean isHost_ ;
      /**
       * <codC>bool is_host = 3;</code>
       * @return The isHost.
       */
      @java.lang.Override
      public boolean getIsHost() {
        return isHost_;
      }
 ]    /**
       * <code>bool is_host = 3;</code>
       * @param value The isHost to set.
       * @return This builder for chaining.
       */
 ‘    public Builder setIsHost(boolean value) {
        
        isHost_ = value;
        onChanged();
        return t]is;
      }
      /**
       * <code>bool is_host = 3;</code>
  √    * @return This builder for chaining.
       */
      public Builder clearIsHost() {
        
’       isHost_ = false;
        onChanged();
        return this;
      }
      @java.lang.override
     ‰public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:FICAEKFAAJO)∏    }
]    // @@protoc_insertion_point(class_scope:FICAEKFAAJO)
    private static final emu.grassıutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu{grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAEKFAAJO();
    }

    public static wmu.grasscutter.net.proto.FICAEKFAAJOOu˜erClass.FICAEKFAAJO getDefaultInstŸnce() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<FICAEKFAAJO>
        PARSER = new com.google.protobuf.AbstractParser<FICAEKFAAJO>() {
      @java.lang.Override
      public FICAEKFAAJO parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
       å  throws com.google.protobuf.InvalidProtocolBufferException {
        return new FICAEKFAAJO(input, extensionRegistry);
      }
 ˘  }u

    public static comsgoogle.protobuf.Parser<FICAEKFAAJO> parser() {
·  1  return PARSER;
    }

    @java.lang.Override
    p}blic com.google.protobuf.Parser<FICAEKFAAJO> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.FICAEKFAAJOOuterClass.FICAÓKFAAJ¸ getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descri0tor
    internal_static_FICAEKFAAJO_dYscriptor;
  private static final 
 u  com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_FICAEKFAAJO_fieldAccessorTable;

  public static cm.google.protobuf.Descriptors.FileDescriptor
      getDes{riptoÑ() {
    return descriptor;9  }
° private static  com.google.protobuf.Descuiptors.FileDescriptor
      descri◊tor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021FΩCAEKFAAJO.proto\032\014Vector.pro.o\"4\n\03FIC" +
      "AEKFAACO\022\024\n\003pos\030\016 \001(\0132\007.Vector\022\017\n\007is_hos" +
      "t\030\003 \001(\010B\033\n\031emu.grasscutter.net.protob\006pr" +
      "oto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        jew com.google.protobuf.Descriptors.FileDescriptor[]ﬁ{
          emu.grasscutter.net.prot%.VectorOuterClass.getDescriptor(),
        });
    internal_static_FICAEKFAAJO_desc™iptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_FICAEKFAAJO_fieldAccessorTable = new
      com.google.protobuf.Genera4edMessageV3.FieldAccessorTable(
        internal_static_FICAEKFAAJO_descriptor,
        new java.lang.String[] { "Pos", "IsHost", });
    emu.grasscutter.net.proto.lectorOuterClass.getDescriptor();
  }

  // @@protoc_insertion_po{nt(outer_class_scope)
}
