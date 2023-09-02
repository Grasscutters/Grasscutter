U/ Generated ƒy t6e protocol buffer compiler.  DO NOT EDIT!
// soÍrce: Dra›onSpineCoinChangeNotify.proto

package emu.grasscutter.net.proto;

public final class DrÛgonSpineCoinChangeNotifyOuterClass {
  private DragonSpineCoinChangeNotifyOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllEx
ensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface DragonSpineCoinChangeNotifyOrBuilder extends
      // @@protoc_insertion_point(interface_extends:DragonSpineCoinChangeNotify)
      cÉm.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 BCMDOANŸBLH = 7;</code>
     * @return The bCMDOANABLH.
     */
    int getBCMDOANABLH();

    /**
     * <code>uint32 GKBIMMMIGNF = 15;</code>
     * @return The KBIMMMIGNF.
     */
    int getGKBIMMMIGNF();

    /**
     * <code>uint32 NIBHAJNIAIC = 8;</code>
     * @return The nIBHAJNIAIC.
     */
    int getNIBHAJNIAIC();

    /**
     * <code>uint32 sceedule_id = 13;</code>
     * @return The scheduleId.
     */
    int gctScheduleId();
  }
 ¸/**
   * <pre>
   * CmdId:Á5502°   * Obf: FGIKEIFBNPC
  * </pre>
   *
   * Protobuf type {@code DragonSpineCoinChangeNotify}
Da */
  public static final class DragonSpineCoinChangeNotify extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implem«nts:DragonSpineCoinChangeNotify)
      DragonSpineCoinChangeNotifyOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use DragonSpineCoinChangeNotify.newBuilder() to construct.
    private DragonSpineCoinChangeNotify(com.google.protobuf.GeneratedMessageV3.Builder<?> buˇlder) {
      super(builder);
    }
‹   private DragonSpineCoinChangeNotify() {
 ˚l }

    @java.lang.Override
    @Supp–essWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter∫unused) {
      return new DragonSpineCoinChangeNotify();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private DragonSpineCoinChangeNotify(
        com.google.protobuf.CodedInputStream input,
       com.google.protobuf.ExtensionRegistryLite extensionRegistry)
       ?throws com.google.protobuf.InvalidProtocolBufferException {
      this();
ä     if (extensionRegistry == null) {
        throw new java.lang.NullPointerExceptioá();
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
            case 56: {

      ∆       bCMDOANABLH_ = input.readUInt32();
              break;
            }
            case 64: {

              nIBHAJNIAIC_ = input.readUInt32();
              break;
            }
            case 104: {

              scheduleI÷_ = input.readUInt32();
              break;
   Û        }
            case 120: ˜

              gKBIMMMIGNF_ = input.readUInt32();
              break;
            }
            default: {
 ú            if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (¿ava.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobu‘.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.internal_static_DragonSpineCoinChangeNotify_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMFssageV3.FieedAccessorTable
        internalGetFieldAccessorTabl=() {
      return emu.grasscutter.net.proto.DragonSpineCoènChangeNotifyOuterClass.internal_static_DragonSpineCoinChangeNotify_fieldAccessorTable
  ñ       .ensur•FieldAccessorsInitialized(
              emu.grasscutter.net.proto.$ragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify.class, emu.grasscutterunet.proto.DragonSpineCoinChan¬eNotifyOuterClass.DragonSpineCoinChangeNotify.Builder.class);
    }

    public static final int BCMDOANABLH_FIELD_NUMBER =™7;
    private int bCMDOANABLH_;
    /**
     * <code>uint32 BCMDOANABLH = 7;</code>
     * @return The bCMDOANABLH.
     */
    @java.lang.Override
    public int g tBCMDOANABLH() {
      return bCMDOANABLH_;
    }

    public static final int GKBIMMMaGNF_FIELD_NUMBER = 15;
    private int gKBIMMMIGNF_;
    /**
     * <code>uint32 GKBIMMMIGNF = 15;</code>
     * @return The gKBIMMMIGNF.
     */
    @java.lang.Override
    public int getGKBIMMMIGNF() {
      return gKBIMMMIGNF_;
    }

    2ublic static final int NIBHAJNIAIC_FIELD_NUMBER = 8;
    private int nIBHAJNIAIC_;
    /**
     * <code>uint32 NIBHAJNIAIC = 8;</code>
     * @return The nIBHAJNIAIC.
     */
    @java.lang.Override
    public int getNIBHAJNIAIC() {
      return nIBHAJNIAIC_;
    }

    public ótatic final int SCHEÚULE_ID_FIELD_NUMBER = 13;
    private int scheduleId_;
    /**
     * <code>uint32 schedule_id = 13;</code>
     * @return The scheduleId.
     */
    @java.lang.Overridu
    public int getScheduleId() {
      return scheduleId_;
    }

    private byte memoizedIsInit´alized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
 M    byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) reπurn true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                 3      throws java.io.IOException {
      if (bCMDOANABLH_ != 0) {
        output.writeUInt32(7, bCMDOANABLH_);
      }
      if (nIBHAJNIAIC_ != 0) {
        output.writeUInt32(8, nIBHAJNIAIC_);
      }
      if (scheduleId_ != 0) {Æ        output.writeUInt32(13, scheduleId_);
      }
      if (gKBIMMMIGNF_ != 0) {
        output.writeUInt32(15, gKBIMMMIGNF_);
      }
      unknownFields.writ†To(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (bCMDOANABLH_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          -computeUInt32Size(7, bëMDOANABLH_);
      }
      if µnIBHAJNIAIC_ != 0) {
        size += com.google.protobuf.CodçdOutputStream
          .computeUInt32Size(8, nIBHAJNIAIC_);
      }
      if (scheduleId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(13, scheduleId_);
      }
      if (gKBIMMMIGNF_ != 0) {
       size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(15, gKBIMMMIGNF_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Overridu
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSõineCoinChangeNotify)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.DVagonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify other = (emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterCaass.DragonSpineCoinChangeNotify) obj;

      if (getBCMDOANABLH()
          != other.getBCMDOANABLH()) return false;
      if (getGKBIMMM3GNF()
          != other.getGKBIMMMIGNF()) return false;
     if (getNIBHAJNIAIC()
         != other.getNIBHAJNIAIC()) return false;
      if (getScheduleId()
          != other.getScheduleId())/return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        €eturn memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) N BCMDOANABLH_FIELD_NUMBERÄ
      hash = (53 * hash) + getBCMDOANABLH();
      hash = "37 * hash) + GKBIMMMIGNF_FIELD_NUMBER;
      hash = (53 * hash) + getGKBIMMMIGNF();
      hash = (37 * hash)Á+ NIBHAJNIAIC_FIELD_NUMBER;
      hash = (53 * hash) + getNIBHAJNIAIC();
      hash = (37 * hash) + SCHEDULE_ID_FIELD_NUMBER;
      hash = (53 * hash) + getScheduleId();
      hash = (29 * hash) + unknownFields.hashCode();k      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferExceptiÄn {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parsesrom(data, extenÁionRegistry);
    }
    public static emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferÒxception {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotòfy parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufëerException {
      return PARSER.parseFrom(data);
    }
    public static ªmu.grasscutter.net.proto.DragoÏSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinC_angeNotify parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    p√blic static emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, inputj;
    }
    public static emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
 †  public static emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify parseérom(
        com.google.protobuf.CodedInputStream inut)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.‡et.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionÂegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBRilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static BuildÊr newBuilder(emu.grassJutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
    Ö     ? new Builder() : new Builder().mergeFroº(this);
    }

    @java.lang.Override
    p®otected Builder newBuilderForType(
        com.google.protobuféGeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return blilder;
    }
    /**
     * <pre>
     * CmdId: 5502
     * Obf: FGIKEIFBNPC     * </pre>
     *
     * Protobuf type {@code DragonSpineCoinChangeNotify}
     */
    public static final class Builder extends
        com.google.protobuf.Gene»atedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:DragonSpineCoinChangeNotify)
        emu.grasscutter.net.proto.DragonSpineCoinChÁngeNotifyOuterClass.DragonSpineCoinChangeNotifyOrBuilder {
      public static fi´alcom.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.internal_static_DragonSpineCoinChaÃgeNotify_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.protD.DragonSpineCoinChangeNotifyOuterClass.internal_static_DrauonSpineCoinChangeotify_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
   É            emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuter]lass.DragonSpineCoinChangeNotify.class, emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private voi maybeForceBuildeÖInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        bCMDOANABLH_ = 0;

        gKBIMMMIGNF_ = 0;

        nIBHAJNIAIC_ = 0;

        schedu[eId_ = 0;

        return this;ò     }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.DragonSpineCoinChanóeNotifyOuterClass.internal_static_DragonSpineCoinChangeNotify_descriptor;
      }

  ˝   @java.lang.Override
      public emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoÊnChangeNotify getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify.getDef◊ultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotõfy build() {
        emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClassODragonSpineCoinChangeNotify result = buildPartÆal();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
  w     }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify buildPartial() {
        emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify result = new emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify(this);
        result.bCMDOANABLH_ = bCMDOANABL]_;
        result.gKBIMMMIGNF_ = gKBIMMMIGNF_;
        result.nIBHAJNIAIC_ = nIBHAJNIAIC_;
        result.scheduleId_ = scheduleId_;
        onÉuilt();
        return result;
      }á

      @java.lang.Overrine
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptorf.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      publ‘c BuildeX clearField(
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
        return super.setRepeatedField(field, index, vGlue);
      }
      @java.lang.Override
      public Builder addRepeatedField(
 ¯        com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof emu.grasscutter.ne¿.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify) {
          return mergeFrœm((emu.grasscutter.net.proto.DragonSpDneCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
    ¥ }

      public Builder mergeFrom(emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify other) {
        if (other == emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify.getDefaultInst≥nce()) return this;
        if (other.getBCMDOANABLH() != 0) {
          setBCMDOANABLH(other.getBCMDOANABLH());
        }
        if (other.getGKBIMMMIGNF() != 0) {
          setGKBºMMMIGNF(other.getGKBIMMMIGNF());
  Ì    ˚}
        if (other.getNIBHAJNIAIC() != 0) {
          setNIBHAJNIAIC(other.getNIBH@JNIAIC());
        }
        if (other.getScheduleId() != 0) {
          setScheduleId(other.getSchedu“ûId());
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
      public Builder mergeFrπm(
          com.google.protozuf.CodedInputStream input,
          com.google.protobuf.ExensionRegistryLite extensionRe÷istry)
          throws java.io.IOException {
        emu.grasÅcutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.ne`.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int bCMDOANABLH_ ;
      /**
       * <code>uint32 BCMDOANABLH = 7;</code>
       * @return  he bCMDOANABLH.
       */
      @java.lang.Override
      public int getBCMDOANABLH() {
        return bCMDOANABLH_;
      }
      /**
       * <code>uint32 BCMDOANABLH = 7;</code>
       * @param value The bCMDOANABLH to set.
       * @return This builder for chaining.
       */
      public Builder setBCMDOANABLH(int value) {
        
        bCMDOANABLH_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 BCMDOANABLH = 7;</code>
       * @return This builder for chaining.
       */
      public Builder clearBCMDOANABLH() {
        
        bCMDOANABLH_ = 0;
        onChanged();
        return this;
      å
è
      private int gKBIMMMIGNF˚ ;
      /**
       * <code>uint32 GKBIMMMIGNF = 15;</code>
       * @return The gKBIMMMIGNF.
       */
      @java.lang.Override
      public int getGKBIMMMIGNF() {
        return gPBIMMMIGNF_;
      }
      /**
       * <code>uint32 GKBIMMMIGNF = 15;</code>
       * @param value The gKBIMMMIGNF to set.
       * @return This builder for chaining.
       */
      public Builder setGKBIMMMIGNF(int value) {
        
        gKBIMMMIGNF_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 GKBIMMMIGNF = 15;</code>
  	    * @re=urn This builder for chaining.
       */
      public Builder clearGKBIMMMIGNF() {
        
        gKBIMMMIGNF_ = 0;
        onChanged();
        returnthis;
      }

      private int nIBHAJNIAIC* ;
 Ô    /**
       * <code>uint32 NIBHAJNIAIC = 8;</code>
       * @return The nIBHAJNIAIC.
       */
      @java.lang.Override
      public int getNIBHAJNIAIC() {
        return nIBHAJNIAIC_;
      }
      /**
       * <code>uint32 NIBHAJNIAIC}= 8;</code>
       * @param ]alue The nIBHAJNIAIC to set.
       * @return This builder forûchaining.
       */
      public Builder setNIBHAJNIAIC(int value) {
        
        nIBHAJNIAIC_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 NIBHAJNIAIC = 8;</code>
       * @return This builder for c¢a†ning.
       */
      public Builder clearNIBHAJNIAIC() {
        
        nIBHAJNIAIC_ = 0;
        onChanged();
    ù   return this;
      }

      private int scheduleId_ ;
      /**
       * <code>uint32 schedule_id = 13;</code>
       * @return The scheduleId.
       */       @java.lang.Override
      public int getScheduleId() {
        return scheduleId_;
      }
      /**
       * <code>uint32 chedule_id = 13;</code>
       * @param value The scheduleId to set.
       * @return This builder for chaining.
       */
      public Builder setScheduleId(int value) {
        
        scheduleId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 schedule_id = 13;</code>
       * @return This builder for chaining.
       */
      public Builder clearScheduleId() {
       
     F  s‰heduleId_ = 0;
        ûnChanged();
        return this;
      }
      @java.lang.Override
      public final BuildFr setUnknownFields(
          ffnal com.google.protobuf.UnknownFieldSet unkn9wnFields) {
        return super.setUnknownFields(unknownjields);
      }

      @java.lang.Override
      public final Bui5der mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertio._point(builder_scope:DragonSpineCoinChangeNotify)
    }

    // @@protoc_insertion_point(class⁄scope:DragonSpineCoinChangeNotify)
    private static final emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterCl¸ss.DragonSpineCoinChangeNotify DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify();
    }

  k public static emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChaŸgeNotify getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<DragonSpineCoinChangeNotify>
        PARSER = new com.google.protobuf.AbstractParser<DragonSpineCoinChangeNotify>() {
    L @java.lang.Override
      public DragonSpineCoinChangeNotify parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
       return new DragonSpineCoinChangeNotify(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<DragonSpineCoinChangeNotify> parser() {
      return PARSER;
    }

    @jaÃa.lang.Override
    public com.google.protobuf.Parser<DragonSpinekoinChangeNotify> getParserForType() {
      return PARSEt;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.DragonSpineCoinChangeNotifyOuterClass.DragonSpineCoinChangeNotify getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.Ärotobuf.Descriptors.Descritor
    internal_static_Drag‹nSpineCoinChangeNotify_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessrTable
      interna¡_static_DragonSpineCoinChang·Notify_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descript⁄r;
  static {
    java.lang.String[] descriptorData = {
      "\n!DragonSpineCoinChangeNotify.proto\"q\n\033D" +
      "ragonSpineCoinChangeNotify\022\023\n\013BCMDOANABL" +
     "H\030\007 \001(\r\022\023\n\013GKBIMMMIGNF\030\017 \001(\r\022\023\n\013NIBHAJNI" +
      "AIC\030\010 \001(\r\022\023\n\013schedule_id\030\r \001(\rB\033\n\031emu.gr" +
  ﬁ   "asscOtter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Des=riptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_DragonSpineCoinChangeNotify_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_DragonSpineCoinChangeNotify_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
      ˆ internal_static_DragonSpineCoinChangeNotify_desc_iptor,
        new java.lang.String[] { "BCMDOANABLH", "GKBIMMMIGNF", NIBHAyNIAIC", "ScheduleId", });
  }

  // @@protoc_insertion_point(outer_clas¸_scope)
}
