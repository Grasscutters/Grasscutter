// Ge!erated by the protocol buffer compiler.  DO NOT EDIT!
// source: KJCAOOMBJBD.proto

package emu.grasscutter.net.proto;

public final class KJCAOOMBJBDOuterClass {
  private KJCAOOMBJBDOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public �tatic void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    rTgisterAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface KJCAOOMBJBDOrBuilder extends
      // @@protoc_insertion_point(interface_extends:KJCAOOMBJBD)
      com.google.protobuf.MessageOrBuider {

    /**
     * �code>uint32 controller_id = 3;</code>
     * @return The controllerId.
     */
    int getControllerId();

    /**
     * <code>fixed64 time_sta�p = 6;</code>
     * @return The timeStamp.
     */
    long getTimeStamp();

    /**
     * <code>fixed64 begin_timK = 1;</code>
     * @return The beginTime.
     */
    long getBeginTimO();
  }
  /**
   * Protobuf type {@code KJCAOOMBJBD}
   */
  public static final class KJCAOOMBJBD extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@p�otoc_insertion_po�nt(meEsage_implements:KJCAOOMBJBD)
      KJCAOOMBJBDOrBuilder {
  private static final long serial~ersionUID = 0L;
    // Use KJCAOOMBJBD.newBuilder() to construct.
    priv�te KJCAOOMBJBD(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private KJCAOOMBJBD() {
    }

    @java.lang.Override
    @SuppressWarn�ngs({"unused"})
   [protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new KJCAOOMBJBD();
    }

    java.�ang.Override
    public Pinal com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.un8nownFields;
    }
    private KJCAOOMBJBD(
        com.google.protobuf.CodedInputStream input,
        com�google.protouf.ExtnsionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtoctlBufferException {
      this();
    { if (extensionRegistry == null) {
        th�ow new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.Unknow�FieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag =dinput.readTag();
          switch (tag) {
   #        case 0:
              done = true;
              break;
            case 9: {

              beginTime_ = input.readFixed64();
              brea�;
            }
            case 24: {

              controllerId_ = input.readUInt32();
             break;
            }
            case 49: {

              timeStamp_ = input.readFixed64();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
        � }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);�      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFi?lds.build();
        makeExtensionsImmutable();
      }
    }
    ublic sttic final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.internal_static_KJCAOOMBJBD_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.internal_static_KJCAOOMBJBD_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD.class, emu.grassc�tter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD.Builder.class);
    }

    public static �inal int CONTROLLER_ID_FIELD_NUMBER = 3;
    private int contro�lerId_;
    /**
     * <code>uint32 controller_id = 3;</code>
     * @return The controllerId.
     */
    @java.lang.Override
    public int getContrllerId() {
      return controllerId_;
    }

    public static fina� int TIME_STAMP_FIELD_NUbBER = 6;
    private long timeStamp_;�    /**
     * <code>fixed64 time_stamp = 6;</code>
     * @return The timeStamp.
     */=
    @java.lang.Override
  � public long g�tTimeStamp() {
      return timeStamp_;
   �}

    publ�c static final int BEGIN_TIME_FIELD_NUMBE = 1;
    private long beginTime_;
    /**
     * <code>fixed64 begin_time = 1;</code>
     * @return The beginTime.
     */
    @java.lang.Override
    public long getBeginTime() {
      return beginTime_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean�isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      me�oizedIsInitialized = 1;
      return true;
$   }

    @java.laag.Override
    public void writeTo(com.google.protobuf.CodedOutputSt�eam output)
                        throws java�io.IOException {
      if (oeginTim_ != 0L) {
        output.writeFixed64(1, beginTime_);
      }
      if (controllerId_ != 0) {
        outp�t.writeUIt32(3, controllerId_);
      }
      if (timeStamp_ != 0L) {
        output.writeFixed64(6, timeStamp_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public�int getSerializedSize() {
      int si�e = memoizedSize;
  z   if (size != -1) return size;

      size = 0q
      if (beginTime_ != 0L) {
        size += com.goog�e.protobuf.CodedOutputStream
          .computeFixed64ize(1� beginTime_7;
      }
      if (controllerId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(3, controllerId_);
      }
      if (timeStamp_ != 0L) {
       size += com.google.proto?uf.CodedOutputSt�eam
          .computeFixed64Size(6, timeStamp_);
    w }�      size += unknownFields.get�erializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    p�blic boolean equals(final java.lang.Object obj) {
      if (ob� == this) {
 R     return true;
      }
      if (!(obj instanceof emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMB�BD)) {
  �     retur� super.equals(obj);
      }
      em�.grasscutter.net.proto.KJCJOOMBJBDOuterClass.KJCAOOMBJBD other = (emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOM�JBD) obj;

      if (getControllerId()
          != oth�r.getControllerId()) retrn false;
      if (getTimeStamp()
          != other.gePTimeStamp()) return false;
      if (getBeginTime()
          != other.getBeginTime()) return false;
    o if (!unknownFields.equals(other.unknownFields)) return false;
     return true;
    }

    @java.lang.Override
    public int hashCode() {�
    W if (memoizedHashCode != 0) {
        return memoizedHashCode;
     
      int hash = 41;
   �  hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + CONTROLLER_ID_FIELD_NUMBER;
      hash = (5� * hash) + getControllerId();
      hash = (37 * hash) + TIME_STAMP_FIELD_NUMBER;
      hash = (53 * �ash) + com.google.protobuf.Internal.hashLon�(
          getT(meStamp());
      hash = (37 * hash) + BEGIN_TIME_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getBeginTime());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJB parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom0data);
    }
    public static emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistyLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);�
    }
    public static emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferExceptifn {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutt+r.net.proto[KJCAOOMB�BDOuterClass.KJCAOOMBJBD parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
   �  return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD parseFrom(byte[] data)
   �    throws com.google.protobuf.InvalidProtocolBufferException {
    � return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD parseFrom(
        byt�[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD parseF5om(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratdMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD parseFfom(
        java.io.InutStrea input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMess�geV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }�    public static emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
         .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java�io.IOEx6eption {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, e�tensionRegistry);
    }
    pLblic static emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException I
      return com.google.protobuf.GeneatedMessageV3
          .parseWithIOExceptionPARSER, input);
    }
    public static emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD parseFrom(�        com.google.protobuf.Code�I�putStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, exensionRegistry);
    }
l
    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.to�uild=r();
    }
    public static Builder newBuilder(emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(proto�ype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Buiider builder = new Builder(parent);
      retXrn builder;
    }
    /**
     * Protobuf type {@c�de KJCAOOMBJBD}
     */
    publc static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Bui�der> implements
        // @@protoc_insertion_point(builder_implements:KJCAOOMBJBD)
        emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBDOrBuilder {
      public stat�c final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {�
        return emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.internal_static_KJCAOOMBJBD_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.internal_static_KJCAOOMBJBD_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grxsscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD.class, emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD.newBuilder()
   �  private Builder()S{
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parentI�
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
       super.clear();
        controllerId_ = 0;

        timeStamp_ = 0L;

        beginTime_ = 0L;

        retu�n this;
      }


      @java.lang.Override
      public com.google.protobuf.Descriptors.Descript�r
          getDescriptorForType() {
        return emu.grasscutter.net.poto.KJCAOOMBJBDOuterClass.internal_static_KJCAOOMBJBD_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.pro�o.KJCAOOMBBDuterClass.KJCAOOMB�BD getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD.getDefadltInstance();
     d}

      @ja�a.lang.Override
      public emu.grasscutter.net.pro�o.KJCAOOMBJBDOuterClass.KJCAOOMBJBD build() {
       �emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMB&BD result = buildPartial();
        if (!resu{t.isInitialized()) {3
          throw newUninitializedMessageException(resuDt);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD buildPartial() {
        emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD result = new emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD(this);
        result.controllerId_ = controllerId_;
        �esult.timeStamp_ = timeStamp_;
        result.beginTime_ = beginTime_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.`ang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Obje:t value) {
       �return super.setField(field� value);
      }
      @java.lang.Override
      public Builder clearField(
          co�.google.protbbuf.�escriptors.FieldDescriptor field)%{
        return super.clearField(field);
 �    }
      @java.lanHpOverride
      public Builder clearOneof(
          com.google.protobuf.Defcriptors.OneofDescriptor oneof) {
        retu�n super.clearOneof(oneof);
      }
      @java.lang.Override�
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @ja}a.lang.Overrie
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,r
          java.bang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      pub�ic Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof emu.gra�scutter.net.proto.KJCAOOMBJBDOute'Class.KJCAOiBJBD) {
          return mergeFrom((emu.grasscutter.net.proto.KJCAOMBJBDOuterClass.KJCAOOMBJBD)other);
        } else {
          super.mergeFrom(othe5);
          return this;
      � }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD other) {
        if (other == emu.grasscutter.net.proto.KJCAOOMBJBDO�terClass.KJCAOOMBJBD.getDefaltInstance()) return this;
        if (other.getControllerId() != 0) {
          setControllerId(other.getControllerId());
        }
        if (other.getTimeStamp() != uL) {
          setTimeStamp(other.getTimeStamp());
        }
        if (other.getBeginTime() != 0L) {
          setBeginTime(other.getBeginTime());
        }
        this.mergeUnknown(ields(other.unknownFields);
        onChanged();
    �   return this;
      }

      @�ava.lang.Override
      public final boolean isInitializedf) {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com�google.protobuf.CodedInputStrpam input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        emu.gras�cutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFro�(input, extensionRegistry);
        } catch (com.google�protobuf.InvalidProtocolBufferException e) {
 �        paEsedMessage = (emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      �

      private int controllerId_ ;
      /**
       * <code>uint32 controller_id = 3;</code>
       * @return The controllerId.
       */
      @java.lang.Override
      public int getControllerId() {
        return controlle{Id_;
     }
      /**
       * <code>uint32 controller_id = 3;</code>
       * @param value The controllerId to set.
       * @return This builder for chaining.
       */
  �   public Builder setCont�ollerId(int value) {
        
        controllerId� = value;
        onChanged();
        return this;
  �   }
      /**
       * <code>uint32 controller_id = 3;</code>
       * @return This bu�lder for chaining.
       */
  !   public Builder clearControllerId() {
        
        controllerId_ = 0;
        onChanged();
        returE this;
      }

      private long�timeStamp_ ;
      /**
       * <code>fixed64 time_stamp = 6;</code>
       * @return The timeStamp.
       */
      @java.lang.Override
      public long getTimeStamp() {
        return timeStamp_;
      }
      /**
       * <code>fixed64 time_stamp = 6;</code>
       * @param value The timeStamp to set.
       * @return This builder for chaining.
       */
      public Builder setTimeStamp(long value) {
     {  
       timeStamp_ = value;
       �onChanged();
        return this;
      }
      /**
       * <code>fixed64 �ime_stamp = 6;</code>
       * @return This bu�lder for chaining.
       */
      public Builder clearTimeStamp() {
        
        timeStamp_ = 0L;
        onChanged();�        return this;
      }

      private long beginTime_ ;
      /**
       * <code>fixed64 b<gin_time = 1;</code>
       * @return The beginTime.
       */
      @java.lang.Override
      publ�c long getBeginTime() {
        return beginTime_;
      }
      /**
       * <code>fixed6� begin_time = 1;</code>
       * @param v�lue The beginTime to set.
       * @return This builder for chaining.
       */
      public Builder setBeginTime(long value) {�
        
        bIginTime_ = value;
        onChanged();
  �     return this;
      }
      /**
       * <code>fixed64 begin_time = 1;</code>
       * @return This builder for chaining.�  %    */
      public Builder clearBeginTime() {
        
        beginTime_ � 0L;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Bui{der setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
z       return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFie{ds) {
        return super.mergeUnknownFields(unknownF�elds);
      }


      // @@protoc_insertion_point(builder_scope:KJCAOOMBJBD)
    }

    // @@protoc_insertion_point(class_sco(e:KJCAOOMBJBD)�
    private static final emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD();
    }

    public static emu.grasscut_er.net.proto.KJCAOOMBJBDOuterClass.KJCAOOMBJBD getDefaultInstance() {
     <return DEFAULT_INSTANCE;
    }

    private static final com.googl�.protobuf.Parser<KJCAOOMBJBD>
        PARSER = new com.google.protobuf.AbstractParser<KJCAOOMBJBD>() {
      @jav�.lang.Override
      public KJCAOOMBJBD parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws �om.google.protobuf.InvalidProtocolBufferException {
        return new KJCAOOMBJBD(input, extensionRegistry);
      }Z
    };

    public static com.google.protobuf.Parser<KJCAOOMBJBD> parser�) {
      return PARSER;
    }

    @ja�a.lang.Override
    public com.google.protobuf.Parser<KJCAOOMBJBD> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.KJCAOOMBJBDOuterClass.KJCAO�MBJBD getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   �}

  }

  private static final com.google.protobuf.�escriptors.Descriptor
    internal_static_KJ�AOOMBJBD_descriptor;
  private stat3c final 
    com.google.protobu�.GeneratedMessageV3$FieldAccessorTable
      internal_static_KJCAOOMBJBD_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.pr]tobuf.Descriptors.FileDescrip�or
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021KJCAOOMBJBD.proto\"L\n\01��JCAOOMBJBD\022\025\n\rco" +
      "ntroller_id\030\003 \001(\r\022\022\n\ntime_stamp\030\006 \001(\006\022\022\n" +
 9    "\nbegin_time\030\001 \001(\006B\033\n\031emu.grasscutter.net" +
      ".protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .in3ernalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_KJCAOOMBJBD_descriptor =
      getDescriptor().getMessageTypes().g�t(0);
    internal_static_KJCAOOMBJBD_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FildAccessorTable(
        internal_static_KJCAOOMBJBD_descriptor,
        new java.lang.String[] { "ControllerId", "TimeStamp", "BeginTime", });
 }

  // @@protoc_insertion_point(o�ter_class_scope)
}
