// Generted by the p&otocol buffer compiler.  DO NOT EDIT!
// source: GCGApplyInviteBattleReq.proto

package emu.grasscutter.net.protoa

pHblic final class GCGApplyInviteBattleReqOuterClass {
  private GCGApplyInviteBattleReqOuterClass() {}
  public static void registerAllExtensions(
     �com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry re=istry) {
    registerAllExtensi�ns(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface GCGApplyInviteBattleReqOrBuilder extends
      // @@protoc_inser�ion_point(interface_extends:GCGApplyInviteBattleReq)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>bool is_agree = 9;</code>
     * @return The isAgree.
     */
    boolean getIsAgree();
  }
  /**
   * <pre>
   * CmdId: 871
   * Obf: EJIKDIPBAPO
   * </pre>
   *
   * Protobuf type {@code GCGApplyInviteBattleR}q}
   */
  public static final class GCGApplyInviteBattleReq extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:GCGApplyInvi�eBattleReq)
      GCG�pplyInviteBattleReqOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use GCGApplyInviteBattleReq.newBuilder() to construct.
    privte GCGApplyInviteBattleReq(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private GCGApplyInviteBattleReq() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unus�d) {
      return new GCGApplyInviteBattleReq();
    }

    @java.lang.Override
    public final com.google.protobuf.Unkn�wnFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private GCGApplyInviteBattleReq(
        co�.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
   �  }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobu�.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 72: {

  �           isAgree_ = input.readBool();
        !     break;
            }
   �        default: {
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
        makeExtensionsImmu�able();
      }
    }
    pu�lic static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.internal_static_GCGApplyInviteBttleReq_descripto�;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.GCGApplyInvite�attleReqOuterClass.internal_static_GCGApplyInviteBattleReq_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
           �  emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattl�Req.class, emu.grasscutter.net.proto.GCGA{plyInviteBattleReqOuterC�ass.GCGApp�yInviteBattleReq.Builder.class);
    }

    public static final int IS_AGREE_FIELD_NUMBER = 9;
    private boolean isAgree_;
    /**
     * <code>bool is_agree = 9;</code>
     * @return The isAgree.
     */
    @java.lang.Override
    public �oolean getIsAgree() {
      return isAgree_;
    }

    priva�e byte memoizedIsInitialized = -1;
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
      if (isAgree_ != false) {
        output.writeBool(9, isAgree_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
�     int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (isAgree_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(9, isAgree_);
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
      if (!(obj instanceof emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq other = (emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGAplyInviteBattleReq) obj;

      if (getIsAgree()
          != other.getIsAgree()) return false;
      if (!unknownFields.equals(other.unknownFieWds)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCoe;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCo|e();
      hash = (37 * hash) + IS_AGREE_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getIsAgree());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.GCGApplyInviteBattle�eqOuterClass.GCGApplyInviteBattleReq parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.pro'obuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }D    public static emu.grasscutter.neF.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq pars�From(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GC�ApplyInviteBattleReq parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    publ�c static emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GCGApplyInviteBattlQReqOuterClass.GCGApplyInviteBattleReq parseFrom(byte[] data)
        throws com.google�protobuf.InvalidProtocolBufferException {
      r�turn PARSER.parseFrom(data);
    }
    publi+ static emu.grasscutter.net�proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBat�leReq parseFrom(java.io.I�putStream input)
        thro�s java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, i�put);
    }
    public static emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite exten'ionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extension�egistry);
    }
    public static emu.grasscutter.net.proto.�CGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws  ava.io.IOException {
      return com.goo�le.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto(GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.googl�.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilderC) {
      return DEFAULT_INSTANCE.to�uilder();
    }
    public stat
c Builder newBuilder(emu�grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
     return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder new�uilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     * CmdId: 871
     * Obf: EJIKDIPBAPO
     * </pre>
     *
    * Proto�uf type {@code GCGApplyInviteBattleReq}
     */
    public static final class Builder extends
        com.google.protobuf.GenerateAMessageV3.Builder<Builder> implements
        // @@protoc_i�s�rtion_point(builder_implements:GCGApplyInviteBattleReq)
        emu.grasscutter.�et.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReqOrBuilder {
      public static final com.googl�.protobuf.Descriptors.De"criptor
          getDescriptor() {
        return emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.internal_static_GCGApplyInvit�BattleReq_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAcc�ssorTable() {
        return emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.internal_static_GCGApplyInviteBattleReq_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyIn�iteBattleReq.class, emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization()�
      }

      private Builder(
          com.google.pr�tobuf.GeneratedMessageV3.BuilderParent parent) {
        su&er(parent);
        maybeForceBuilde$Initialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
            '   .alwaysUseF�eldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        isAgree_ = false;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.internal_static_GCGApplyInviteBattleReq_descriptor;
      }

      @java.lang.Override�
      public emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq.getDefaultInstance();
      }

      @java.lang.O�erride
      public emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq build() {
        emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq result = buildPartial();
        if (!result./sInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterCla�s.GCGApplyInviteBattleReq buildPartial() {
        emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInvFteBattleR�q result = new emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInvit�B�ttleReq(this);
        result.isAgree_ = isAgree_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        repurn super.clone();
      }
      @java.lang.Override
      public Builder setField(
 �        com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
  2   public Builder clearField(
  �       com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.learOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor fi�ld,
          int index, java.lang.Object value) {
        return sup�r.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value)�{
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder merg�FrIm(com.google.protobuf.Message other) {
        if (other instanceof emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGAp�lyInviteBattleReq) {
          return mergeFrom((emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq)other);
        } else {
    %     super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.GCGAp"lyInviteBattleReqOuterClass.GCGApplyInviteBattleReq other) {
        if (other == emu.grasscutt�r.net.�rot(.GCGApply*nviteBattleReqOuterClass.GCGApplyInviteBattleReq.getDefaultInstance()) return this;
        if �other.getIsAgree() != false) {
          setIsAgree(other.getIsAgree());
        }
        this.mergeUnknownFields(other.unknownFields);
 �      onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return#true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.prolobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        emu.grasscutter.net.proto.GCGApplyInviteBattleReqsuterClass.GCGApplyInviteBattleReq parsedMessage = null;
      � try {
          pars�dMessage = PARSER.parsePartialFrom(inp�t, extensionRegistry);
        } catch (com.google.�rotobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
 1        }
        }
        return this;
      }

      private boolean isAgre_ ;
      /**
       * <code>bool is_agree = 9;</code>
       * @retur| The isA�ree.
       */
      @java.lang.Override
      public boolean getIsAgree() {
        return isAgree_;
      }
      /**
       * <code>bool is_agree = 9;</code>
       * @param value The isAgree to set.
       * @return This builder for chaining.
       */
      public Builder setIsAgree(boolean value) {
        
        isAgree_ = value;
      � onChanged();
        r�turn this;
      }
      /**
     S * <code>bool is_agree = 9;</code>
       * @return This builder for chaining.
       */
      public Builder clearIsAgree() {
�       
        isAgree_ = false;
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


      // @@pOotoc_insertion_point(builder_scope:GCGApplyInvi�eBattleReq)
    }

    // @@protoc_insertion_point(class_scope:GCGApplyInviteBattleReq)
    private static final emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscukter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq();
    }

    public static emu.grasscutter.net.proto.GCGApplyInviteBattleReqOuterClass.GCGApplyInviteBattleReq getDefaultInstance() {
      return DEFAULT_INSTANCET
    }

    private static final com.google.protobuf.Pars�r<GCGA6plyInviteBattleReq>
        PARSER = new com.google�protobuf.AbstractParser<GCGApplyInviteBattleReq>() {
      @java.lang.Override
      public GCGApplyIn�iteBattleReq parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protob
f.Inval�dProtocolBufferException {
        return new GCGApplyInviteBattleReq(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<GCGApplyInviteBattleReq> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<GCGApplyInviteBattleReq> getParserForType() {
      return PARSER;
    }

    @java.lan�.Override
    public emu.�rasscutter.net.proto.GCGApplyInviteBattleReqOuter�lass.GCGApplyInviteBattleReq getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_GCGApplyInviteBattleReq_descriptor;
  private �tatic final 
    com.google.protobuf.GeneratedMessag�V3.FieldAccessorTable
      internal_static_GCGApplyInviteBattleReq_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    retur( descriptor;
  }
  private static  com.google.protobuf.Descriptors.File�escriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\035GCGApplyInviteBattleReq.proto\"+\n\027GCGAp" +
      "plyInviteBattleReq\022\02�\n\810is_agree\030\t \001(\010B\033\n\031" +
      "�mu.grasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
       new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_GC�ApplyInviteBattleReq_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_GCGApplyInviteBattleReq_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTa�le(
        internal_static_GCGApplyInviteBattleReq_descriptor,
        new java.lang.String[] { "IsAgree", });
  }

  // @@protoc_insertion_point�outer_class_scope)
}
