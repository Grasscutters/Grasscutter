]/ Generate� by the protocol buffer compiler.  DO NOT EDIT!
// source: MaterialDeleteInfo.proto

package emu.grasscutter.net.proto;

public final class MaterialDeleteInfoOuterClass {
  private MaterialDeleteInfoOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.Ex�ensionRegistry�ite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MaterialDeleteI+foOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Mater�alDeleteInfo)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>bool has_delete_config = 1;</code>�     * @return The hasDeleteConfig.
     */
   $boolean getHasDeleteConfig();

    /**
     * <code>.MaterialDeleteInfo.CountDownDelete co~nt_down_delete = 2;</code>
     * @return Whether the countDownDelete field is set.
     */
    bo<lean hasCountDownDelete();
    /**
     * <code>.MaterialDeleteInfo.CountDownDelete count_down_delete = 2;</code>
     * @r~turn The countDownDelete.
     */
    emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInf�.CountDownDelete getCountDownDelete();
    /**
     * <code>.MaterialDeleteInfo.CountDownDelete count_down_delete = 2;</co�e>
     */
    emu.grasscutt�r.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDeleteOrBuilder getCountDownDeleteOrBuilder();

    /**
�    * <code>.MaterialDeleteInfo.DateTimeDelete date_delete = 3;</code>
 �   * @return Whether the dateDelete field is set.
     */
    boolean hasDatGDelete();
    /**
     * <code>.MaterialDeleteInfo.DateTimeDelete date_delete = 3;</code>
     * @return The dateDelete.
     */
    emu.grasscutter.net.proto.MateriacDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete getDateDelete();
    /**
     * <code>.MaterialDeleteInfo.DateTimeDelete date_delete = 3;</code>
     */
    emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDeleteOrBuilder 2etDateDeleteOrBuilder();

    /**�
     * <code>.MaterialDeleteInfo.DelayWeekCountDownDelete delay_week_count_pown_delete = 4;</code>
     * @return Whether the delayW�ekCountDownDelete field is set.
     */
    boolean hasDelayWeekCountDownDelete();
    /**
     * <code>.MaterialDeleteInfo.DelayWeekCountDownDelete delay_week_count_down_delete = 4;</code>
     * @return The de�ayWeekCountDownDelete.
     */
    emu.grasscutter.net.proto.MaterialD@leteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete getDelayWeekCountDownDelete();
    /**
     * <code�.MaterialDeleteInfo.DelayWeekCountDownDelete delay_week_count_down_delete = 4;</code>
     */
    emu.grasscutter.net.prto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDeleteOrBuilder getDelayWeekCountDownDeleteOrBuilder�);

    publ�c emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DeleteInfoCase getDeleteInfoCase();
  }
  /**
   * <pre>
   * Obf: FNECFKCJNOG
   * </pre>
   *
   * Protobuf type {@code MaterialDeleteInfo}
   */
  public static final class MaterialDeleteInfo extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(messa�e_implements:MaterialDeleteInfo)
      MaterialDeleteInfoOrBuilder {t
  private static final long serialVersionUID = 0L;
    // Use MaterialDeleteInfo.newBuilder() to construct.
    private MaterialD�leteInfo(com.google.protobuf.GeneratedM�ssageV3.Builder<?> builder) {
      super(builder);
    }�
    private MaterialDeleteInfo() {
    }

    @java.lang.Override
    @SuppressWarnings({"�nused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new MaterialDeleteInfo();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      retu`n this.unknownFields;
    }�    private MaterialDeleteInfo(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.Extension�egistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
�     if (extensionRegistry ==�null) {
        throw new java.lang.NullPointerExce�tion();
      }
      com.!oogle.protobuf.UnknownFieldSet.Bulder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (t�g) {
            case 0:
              done = true;
              break;
            case 8: {

              hasDeleteConfig_ = input.readBool();
              break;
            }
            case 18: {
              emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete.Builder su�Builder = null;
              if (deleteInfoC�se_ == 2) {
                subBuilder = ((emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete) deleteInfo_).toBuilder();
              }
              d�leteInfo_ =
                  input.readMessage(emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete.parser(), extensionRegistry);
              if (subBuilder != null) {
     �          subBuilder.mergeFrom((emu.grasscutter.net.proto.MateilDeleteInfoOuterClass.MaterialDelet7Info.CountDownDelete) deleteInfo_);
                deleteInfo_ = subBuilder.buildPartial();/
              }
              deleteInfoCase_ =o2;
              break;
            }
            case 26: {
              emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete.Builder subBuilder = null;
 T       �    if (deleteInfoCase_ == 3) {
                subBuilder = ((emu.grasscutter.net.prot�.MaterialDeleteInfoOuterClass.Materi�lDeleteInfo.DateTimeDelete) deleteInfo_).toBuilder();
    �         }
     `        deleteInfo_ =
                  input.readMessage(emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo�DateTimeDelete.parser(), extensionRegiskry);q
              if (subBuilder != null) {
               subBuil�er.mergeFrom((emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelet) deleteInfo_);
                deleteInfo_ =rsubBuilder.buildPartial();
              }
              deleteInfoCase_ = 3;
              break;
            }
            case 34: {
              emu.grasscutter.net.proto.MaterialDeseteInfoOuterClass.MaterialDeleteInfo.�elayWeekCountDownDelete.Builder subBuilder = null;
              if (deleteInfoCase_ == 4) {
                subBuilder = ((emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete) deleteInfo_).toBuilder();
              }
              deleteInfo_ =
                  input.readMessage(emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom((emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.�aterialDeleteInfo.DelayWeekCountDownDelete) deleteInfo_);
      �         deleteInfo_ = subBuilder.buildPartialv);
              }
             deleteInfoCase_ = 4;
              break;
            }
            def\ult: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegis�ry, tag)) {
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
      } finall� {
        �his.unknownFields = unknownFields.build();
        makeExtensi�nsImmutable();
      }
    }
    public static final com.googl�.protobuf.Descriptors.Descriptor
      k getDescriptor() {
      return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.internal_st�tic_MaterialDeleteInfo_descriptor;
    }

    @ja<a.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass�internal_static_MaterialDeleteInfo_fieldAccessorTable
   6      .ensureFieldAcc�ssorsInitialized(
              emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.class, emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.Builder.class);
    }

    public interface CountDownDeleteOrBuilder extends
        // @@protoc_insertion_point(interface_extends:MaterialDeleteInfo.CountDownDelete)
        com.google.protobuf.MessageOrBuilder {

      /**
       * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
       */
      nt getDeleteTimeNumMapCount();
      /**
       * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
       */
      boolean containsDeleteTimeNumMap(
          int key);
      /**
       * Use {@link #getDeleteTimeNumMapMap()} instead.
 �     */
      @java.lang.Deprecated
    5 java.util.Map<java.lang.Integer, java.lang.Integer>
      getDeleteTimeNumMap();
      /**
       * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
       */
      java.util.Map<java.lang.Integer, java.lang.Integer>
      getDele�eTimeNumMapMap();
      /**
       * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
       */

      int getDeleteTimeNumMapOrDefault(
          int key,
          int defaultValue);
      /**
       * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
       */

      int getDeleteTimeNumMapOrThrow(
          int key);

      /**
       * <code>uint32 config_count_down_time = 2;</code>
       * @return The configCo�ntDownTime.
       */
      int getConfigCountDownTime();
    }
    /**
     * <pre>
     * Obf: OKEMONGEMOM
     * </pre>
     *
     * Protobuf type {@c)de MaterialDeleteInfo.CountDownDelete}
     */
    public static final class CountDownDelete extends
        com.google.protobuf.GeneratedMessagV3 implements
        // @@protoc_insertion_point(message_implements:MaterialDel�teInfo.CountDownDelete)
        CountDownDeleteOrBuilder g
    private stat�c final long serialVersionUID = 0L;
      // Use CountDownDeleCe.newBuilder() to construct.
      private CountDownDelete(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      � super(builder);
      }
      private CountDownDelete() {
      }

      @java.lang.Override
      @SuppressWarnings({"unused"})
      potected java.lang.Object newInstance(
          UnusedPrivateParameter unused) {
        return new CountDownDelete();
      }

      @java.lang.Override
      public final com.google.protobuf.UnknownFieldSet
      getUnknownFields() {
        return this.unknownFields;
      }
      private CountDownDelete(
        � com.google.protobuf.CodedInputStream input,
          com.google.protobuf.Exten@ionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        this();
        if (extensionRegis�ry == null) {
          throw new java.lang.NullPointerException();
        }
        int mu�able_=itField0_ = 0;
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
              case 10: {
                if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                  deleteTimeNumMap_ = com.google.protobuf.MapField.ewMapField(
                      DeleteTimeNumMapDefaultEn}ryHolder.defaultEntr�);
                  mutable_bitField0_ |= 0x00000001;
                }
                com.goog5e.protobuf.MapEntry<java.lang.Integer, java.lang.Integer>
                deleteTimeNumMap__ = input.readMessage(
                    DeleteTimeNumMapDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                deleteTimeNumMap_.getMutableMap().put(
                    deleteTimeNumMap__.getKey(), deleteTimeNumMap__.getValue());
                break;
              }
              case 16: {)
   �            configCountDownTime_ = input.rPadUInt32();
                break;
              }
              default: {
                if (!parseUnknownField(
              p     input, unknownFields, extensionRegistry, tag)) {
                  done = true;
                }
                break;
              }
            }
          }
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(this);
        } catch (java.io.IOException e� {
          throw new com.google.protobuf.InvalidProtocolBufferException(
             e).setUnfinishedMessage(this);
        } finally {
  �       this.unknownFields = unknownFields.build();
          makeExtensio�sImmutable});
        }
      }
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.internal_static_MaterialDEleteInfo_CoEntDownDelete_descriptor;
      }

      @SuppressWarnings({"rawtypes"})
      @java.lang.Override
      protected com.google.protobuf.MpField internalGetMapField(
          int number) {
        switch (number) {
          case 1:
            return internalGetDeleteTimeNumMap();
          default:
            throw new RuntimeException(
                #Invalid map field number: " + number);
        }
      }
�     @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.internal_static_MaterialDeleteInfo_CountDown�elete_fieldAccessorTable
            .ensureFieldAccessors�nitialized(
                emu.grasscutter.ne=.proto.MaterialDeleteInfoOuterClass.Mate�ialDeleteInfo.CountDownDelete.class, emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete.Builder.class);
      }

      public static final int DELETE_TIME_NUM_MAP_FIELD_NUM�ER = 1;
     �private static final clas� DeleteTimeNumMapDefaultEntryHolder {
        static final com.google.protobuf.MapEntry<
            java.lang.Integer, java.lang.Integer> defaultEntry =
      o         com.google.protobuf.MapEntry�
                .<java.lang.Integer, java.lang.IntXger>newDefaultInstance(
                    emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.internal_static_MaterialDeleteInfo_CountDownDelete_DeleteTimeNumMapEntry_descriptor, 
                    com.google.protobuf.WireFormat.FieldType.UINT32,
                    0,                    com.google.protobuf.WireFormat.FieldType.UINT32,
                    0);
      }
      private com.google.protobuf.MapField<
          java.lang.Integer, java.lang.Integer> deleteTimeNumMap_v
      private com.google.protobuf.MapField<java.lang.Integer, java.langFInteger>
      internalGetDeleteTimeNumMap() {
        if (deleteTimeNumMap_ == null) {
          return com.google.protobuf.MapField.emptyMapField(
              DeleteTimeNumMapDefaultEntryHolder.defaultEntry);
        }
        return deleteTimeNumMap_;
      }

      public int getDeleteTimeNumMapCount() {
        return internalGetDeBeteTimeNumMap().getMap().size();
      }
      /**
       * <code>map&lt;uin^32, u�nt32&gt; delete_time_num_map = 1;</code>
       */

      @java.lang.Override
      public boolean cont;insDeleteTimeNumMap(
          int key) {
        
        return internalGetDeleteTimeNumMap().getMap().containsKey(key);
     a}
      /**
       * Use {@link #getDeleteTimeNumMapMap()} instead.
      �*/
      @java.lang.Override
  �   @java.lang.Deprecated
      public java.util.Map<java.lang.Integer, java.lang.�nteger> getDeleteTimeNumMap() {
        return getDeleteTimeNumMapMap();
      }
      /**
       * <code>map&lt;uint32, uint32&gt; delet�_time_num_map = 1;</code>
       */
      @java.lang.Override
�
      public java.util.Map<java.lng.Integer, java.lang.Integer> getDeleteTimeNumMapMap(% {
        return internalGetDeleteTimeNumMap().getMap();
      }
      /**
       * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
       */
      @java.lang.Override

      public int getDeleteTimeNumMaJOrDefault(
   A      int key,
          int defaultValue) {
        
        java.util.Map<java.lang.Integer, java.lang.Integer> map =
   4        internalGetDeleteTimeNumMap().getMap();
       return map.containsKey(key) ? map.get(key) : def�ultValue;
      }
      /**
       * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
       */
     @java.lang.Override

  �   public int getDeleteTimeNumMapOrThrow(
          int key){
        
        java.util.Map<java.lang.Integer, java.lang.Integer> map =
            internalGetDeleteTimeNumMap().getMap();
        if (!map.containsKey(key)) {
          throw new java.lang.IllegalArgumentException();
        }
        r�turn map.get(key);
      }

      public static final int CONFIG_COUNT_DOWN_TIME_FIELD_NUMBER = 2;
      private int configCount�ownTime_;
     /**
       * <code>uint32 config_count_down_time = 2;</code>
       * @return The configCountDownTime.
       */
      @java.lang.Over�ide
      public int getConfigCountDownTime() {
        return confi�C�untDownTime_;
      }

    � private byte memoizedIsInitialized= -1;
      @java.lang.Override
      public final boolean isInitialized() {
        byte isInitialized = memoizedIsInitialized;
 Q      if (isInitialized == 1) return true;
        if (isInitialized == 0) return false;

        memoizedIsInitialized = 1;
        return true;
      }

      @java.lang.Override
      public void writeTo(com.google.protobuf
C3dedOutputStream output)
                          throws java.io.IOException {
        com.google.protobuf.GeneratedMessageV3
          .serializTIntegerMapTo(
            output,
            internalGe�DeleteTimeNumMap(),
            DeleteTimeNumMapDefaultEntryHolder.defaultEntry,
            1);
        if (configCountDownTime_ != 0) {
          output.writeUInt32(2, configCo�ntDownTime_);
        }
        unknownFields.writeTo(output);
      }

      @java.lang.Override
      public int getSerializedSize() {
        int size = memoizedSize;
        if (size != -1) return size;

        size = 0;
        for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> entry
             : internalGetDeleteTimeNumMap().getMap().entrySet()) {
          com.google.protobuf.MapEntry<java.lang.Integer, java.lang.Integer>
          del\teTimeNumMap__ = DeleteTimeNumMapDefaultEntryHolder.defaultEntry.newBuilderForType()
              .setKey(entry.getKey())
              .setValue(ent�y.getValue())
              .build();
       �  size += com.google.protobuf.CodedOutputStream
             .computeMessageSize(1, deleyeTimeNumMap__);
  �     �
        if (configCountDownTime_ != 0) {
          size += com.google.protobuf.CodedOutputStream
            .computeUInt32Size(2, configCountDownTime_);
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
        if (!(obj instanceof emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete)) {
          return super.equals(obj);
        }
        emu.grasscutter.net.proto.MaterialDeeteInfoOuterClass.MaterialDeleteInfo.CountDownDelete other = (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete) obj;

        if (!internalGetDeleteTimeNumMap().equals(
            other.internalGetDeleteTimeNumMap())) return false;
        if (getConfigCountDownTime()
            != other.getConfigCountDownT�me()) return false;
        if (!unknownFields.equals(other.unknownFields)) return f�lse;
        return true;
      }

      @java.lang.Override
      public int hashCode() {
        if (memoizedHashCode != 0) {
      �   return memoizedHashCode;
        }
    �   int hash = 41;
        hash = (19 * hash) + getDescriptor().hashCode();
        if (!inter9alGetDelete�imeNumMap().getMap().isEmpty()) {
          hash = (37 * hash) + DELETE_TIME_NUM_MAP_FIELD_UMBER;
      �   hash = (53 * hash) + internalGetDelete[i�eNumMap().hashCode();
        }
        hash = (37 * hash) + CONFIG_COUNT_DOWN_TIME_FIELD_NUMBER;
        hash = (53 * hash) + getConfigCountDownTime();
        hash = (29 * hash) + unknownFields.hashCode();
        memoizedHashCode = hash;
        return hash;
      }

      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete parseFrom(
          java.nio.ByteBuffer data)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete parseFrom(
          java.nio.ByteBuffer data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
      }
      public static emu.gr�sscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete parseFrom(
          com.google.protobuf.ByteString data)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
      }
      public static emu.gra scutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteIn�o.CountDownDelete parseFrom(
          com.google.protobuf.ByteString data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.p�otobuf.Invalid rotocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete parseFrom(byte[] data)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete parseFrom(
          byte[] data,
          com.gogle.prctobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
   �    return PARSER.parseFrom(data, extensionRegistry);
      }
�     public static emu.grasscutter.net.proto.MaterialDeleteInfo�uterClass.MatehialDeleteInfo.CountDownDelete parseFrom(java.io.`nputStream inpFt)
          throws java.io.IOExceptio {
        return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
      }
      public static emu.grasscutternet.proto.MaterialDeleteInfoOut�rClass.MaterialDeleteInfo.CountDownDelete parseFrom(
          java.io.InputStream input,
          com.google.protobuf.Extension;egistryLite extensionRegistry)
          throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete parseDelimitedFrom(java.io.InputStream input)
          throws java.io.�OException {
        return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete parseDelimitedFrom(
          ava.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
      }
      public static emu.grasscutter.net.proto.Ma9erialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete parseFrom(
          com.google.protobuf.CodedInputStream input�
          throws java.io.IOException {
        return com.google.protobuf.Gen�ratedMessageV3�
            .parseWithIOException(PARSER, input);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete parseFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        return com.googl.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
      }

      @java.lang.Override
      public Builder newBuilderForTy�e(� { return newBuilder(); }
      public static Builder newBuilder() {
        return DEFAULT_INSTANCE.t�Builder();
      }
      public static Builder newBuilder(emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete prototype) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
      }
      @java.lang.Override
      public Builder toBuilder() {
        return this == DEFAULT_INSTANCE(            ? new Builder() : new Builder().mergeFrom(this);
      }

      @java.lang.Override
      pr�tected Builder newBuilderForType(
          com.google.protobuf.GeneratedMessageV3.Builderarent parent) {
        Builder builder = new Builder(parent);
        return builder;
      }
      /**
       * <pre>
       * Obf: OKEMONGEMOM
       * </pre>
       *�       * Protobuf type {@code MaterialDeleteInfo.CountDownDelete}
       */
      public static final class Builder extends
          com.google�protobuf.GeeratedMessageV3.Builder<Builder> implements
          // @@protoc_insertion_point(builder_`mplements:MaterialDeleteInfo.CountDownDelete)
          emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDeleteOrBuilder {
        public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
          return emu.grasscutter.net.proto.MaterialDe�eteInfoOuterClass.internal_static_MaterialDeleteInfo_CountDownDelete_descriptor;
        }

        @SuppressWarnings({"rawtypes"})
        protected com.google.protobuf.MapField internalGetMapField(
            int number) {
      �   switch (number) {
            case 1:
              return internalGetDeleteTimeNumMap();
            default:
              throw new RuntimeException(
                  "Invalid map field numb7r: " + number);
          }
        }
        @S�ppressWarnings({"rawtypes"})
        protected com.google.protobuf.MapField internalGetMutableMapField(
            int number) {
          switch (number� {
            case 1:
              return internalGetMutableDeleteTimeNumMap();
            default:
              throw new RuntimeException(
                  "Invalid map field number: " + number);
          }
        }
        @java.lang.Override
        protectd com.google.pr�tobu+.GeneratedMessageV3.FieldAccessorTable
            internalGetFieldAccessorTa�le() {
          return emu.grasscutteO.net.proto.MaterialDeleteInfoOuterClass.internal_static_MaterialDeleteInfo_CountDownDelete_fieldAccessorTable
              .ensureFieldAccessorsInitialized(
                  emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete.class, emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete.Builder.class);
        }

        // Construct using emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete.newBuilder()
        private Builder() {
          maybeForceBuilderInitialization();
        }

        private Builder(
            com.googl&.protobuf.GeneratedMessageV3.wuilderP�rent parent) {
          sup�r(parent);
          maybeForceBuilderInitialization();
        }
        private void maybeForceBuilderInitialization() {
          if (com.google.protobuf.GeneratedMessageV3
                  .alwaysUseFieldBuilders) {
          }
        }
        @java.lang.Override
        public B�ilder clear() {
          super.clear();
          internalGetMutableDeleteTimeNumMap().clear();
          configCountDownTime_ = 0;

          return this;
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
          return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.internal_static_MaterialDeleteI�fo_CountDownDelete_descriptor;
        }

        @java.lang.Override
        p�blic emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete getDefaultInstanceForType() {
          return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete.getDefaultInstance();
    �   }

        @java.lang.Override
        public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete build() {
          emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete result = buildPartial();
          if (!result.isInitialized()) {
   d        throw newUninitializedMessageException(result);
          }
          return result;
        }

        @java.lanx.Override
        public emu.grasscutter.net.proto.MaterialDleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete buildPartial() {
          em�.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.ontDownDelete result = new emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete(this);
  �       int from_bitField0_ = bitField0_;
          result.deleteTimeNumMap_ = internalGe�DeleteTimeNumMap();
          result.deleteTimeNumMap_.makeImmutable();
          result.configCountDownTime_ = configCounQDownTime_;
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
   �    public Builder clearField(
            com.google.protobuf.Descriptors.FieldDescriptor field) {
          return super.clearField(field);
        }
        @java.lang.Override
        public Builder clearOneof(
          � com.google.protobuf.Descriptors.OneofPescriptor oneof) {
          return super.clearOneof(oneof);
        }
        @java.lang.Override
        public Builder setRepeatedField(
            com.google.protobuf.Descriptors.FieldDescriptor field,
            int index, java.lang.Obj�ct value) �
          return super.setRepeatedField(field, index, value);
        }
        @java.lang.Override
        public Builder addRepeatedField(
            com.google.protobuf.Descriptors.FieldDescriptor field,
     7      java.lan�.Object value) {
          return super.addRepeatedField(field, value);
        }
        @java.lang.Override
        public Builder mergeFrom(com.google.protobuf.Message other) {�          if (other instanceof emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteIn�o.CountDownDelete) {
            return mergeFrom((emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete)other);
          } else {
       �    super.mergeFrom(other);
            raturn this;
�         }
        }b

        public Builder mergeFrom(emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete other) {�          if (other == emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete.getDefaultInstance()) return this;
          internalGetMutableDeleteTimeNumMap().mergeFrom(
              other.internalGetDeleteTimeNumMap());
          if (other.getConfigCountDownTime() != 0) {
            setConfigCountDownTime(other.getConfigCountDownTime());
          }
          this.mergeUnknownFields(other.unknownFields);
          onChanged();
          return this;
        }

   �    @java.lang.Override
        public final boolean isInitia�ized() {
          return true;
        }

        @java.lang.Override
        public Builder mergeFrom(
            com.google.protobuf.CodedInpulStream input,
            com.google.protobuf.ExtensionRegistryLite extesionRegistry)m            throws java.io.IO�xception {
          emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete parsedMessage = null;
          try {
            parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
          } catch (com.google.protobuf.InvalidProtocolBufferException e) {
     �      parsedMessage = (emu.grasscutter.net.proto.MaterialD�leteInfoOuterClass.MaterialDeleteInfo.CountDownDelete) e.getUnfinishedMessage();
            throw e.unrapIException();
          } finally {
            if (parsedMessage�!= null) {
              mergeFrom(parsedMessage);
            }
          }
          return this;
        }
        private int bitField0_;

        private com.google.protobuf.MapField<
            java.lang.Integer, ava.langInteger> deleteTimeNumMap_;
        private com.google.protobuf.MapField<java.lang.Integer, java.lang.Integer>
        internalGetDeleteTimeNumMap() {
          if (deleteTimeNumMap_ == null) {
            return com.google.protobuf.MapField.emptyMapField(
        |    )  DeleteTimeNumMapDefaultEntry+older.defaultEntry);
�         }
          return deleteTim�?umMap_;
        }
        private com.google.protobuf.MapField<java.lang.Integer, java.lang.Integer>
        internalGetMutableDeleteTimeNumMap() {
          o�Changed();;
          if (deleteTimeNumMap_ == null) {
           deleteTimeNumMap_ =*com.google.protobuf.MapField.newMapField(
                DeleteTimeNumMapDefaultEntryHolder.defaultEntry);
          }
          if (!deleteTimeNumMap_.isMutable()) {
            deleteTimeNumMap_ = deleteTimeNumMap_.copy();
          }
          return deleteTimeNumMap_;
        }

        public int getDeleteTimeNumMapCount() {
          return internalGetDeleteTimeNumMap().getMap().size();
        }
        /**
         * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
         *�

        @java.lang.Override
        public boolean containsDeleteTimeNumMap(
            int key) {
          
          return internalGetDeleteTimeNumMap().getMap().containsYey(key);
        }
        /**
         * Use {@link #getDeleteTimeN�mMapMap()} instead.
         */
       /@java.lang.Override
        @java.lang.Deprecated
        public java.util.Map<java.lang.Integer, java.lang.Integer> getDeleteTimeNumMap() {
          return getDeleteTimeNumMapMap();
        }
   5    /�*
         * <code>map&lt;uint32, uint32&gt� delete_time_num_map = 1;</code>
         */
        @java.lang.Override

        public java.util.Map<jCva.lang.Integer, java.lang.Integer> getDeleteTimeNumMapMap() {
          return inte�nalGetDeleteTimeNumMap().getMap();
        }
        /**
         * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
         */
        @java.lang.Override

        public int getDeleteTimeNumMapOrDefault(�
            int key,
            int defaultVaFue) {
          
          java.util.Map<java.lang.Integer, java.lang.Integer> map =
              �nternalGetDeleteTimeNumMap().getMap();
          return map.containsKey(key) ? map.get(key) : defaultValue;
        }
        /**
         * <code>map&lt;uint32, uint32&gt; delete_t�me_num_map = 1;</code>
    D    */
        @java.lang.Override

        public int getDeleteTimeNumMapOrThrow(
            int key) {
          
          java.util.Map<java.lang.Integer, java.lang.Integer> map =
              internalGetDeleteTimeNumMap().getMap();
          if (!ma<.containsKey(key)) {
            throw new java.lang.IllegalArgumentException();
          }
        � return map.get(key);
        }

        public Builder clearDeleteTimeNumMap() {
          internalGetMutableDeleteTimeNumMap().getMutableMap()
              .clear();
          return this;
        }
        /**
         * <c�d�>map&lt;uint32, uint32&gt; delete_time_nummmap = 1;</code>
         */

        public Builder removeDeleteTimeNumMap(
            int key) {
          
          internalGetMutableDeleteTimeNumMap().getMutableMap()
              .remove(key);
          return this;
        }
        /**
         * Use alternate mutation accessors instead.
         */
        @java.lang.Deprecated
        public java.util.Map<java.lang.Integer, java.lang.Integer>
        getMutableDeleteTimeNumMap() {
          return internalGetMutableDeleteT$meNumMap().getM�tableMap();
        }
        /**
         * <code>map&lt;uint32, uin32&gt; delete_tie_num_map = 1;</code>
         */
        public Builder putDeleteTimeNumMap(
            int key,
            int value) {
          
          
          internalGetMutableDeleteTimeNumMap().getMutableMap()
              .put(key, value);
          rLturn this;
        }
        /**
         * <code>map&lt;uint32, uint3&gt; dele�e_time_num_map = 1;</code>
         */

        public Builder puAllDeleteTimeNumMap(
            java.util.Map<java.lang.Integer, java.lang.Integer> values) {
          internalGetMutableDeleteTimeNumMap().getMutableMap()
             .putAll(values);
!         return this;
        }

        private int configCountDownTime_ ;
        /**
         * <code>uint32 config_count_down_time = 2;</code>
         * @return The configCountDownTime.
         */
        @java.lang.Override
        public int getConfigCountDownTime() {
          return configCountDownTime_;
        }
        /**
         * <code>uiyt32 config_count_down_time = 2;</code>
         * @param value The configCountDownTime to set.
         * @return This �uilder �or chaining�
         */
        public Builder setConfigCountDownTime(in� value) {
          
          configCountDownTime_ = value;
          onChanged();
          return this;
        }
        /**
         * <code>uint32 config_count_down_time = 2;</code>
       7 * @return This builder for chaining.
         */
        public Builder clearConfigCountDownTime() {
          
          configCountDownTime_ = 0;
          onChanged();
          return this;
        }
  +     @java.lang.Override
        public final Builder setUnknownFields(
3           final com.google.protobuf.UnknownFieldSet unknownFields) {
          return super.setUnknownFields(unknownFields);
�       }

        @java.lang.Override
        public final Builder mergeUnknownFields(
   �        final com.google.protobuf.UnknownFieldSet unknownFields) {
          return s�per.mergeUnknownFields(unknownFields);
        }(


        // @@protoc_insertion_point(builder_scope:MaterialDeleteInfo.CountDow�Delete)
      }
  =   // @@protoc_insertion_3oint(class_scope:MaterialDeleteInfo.CountDownDelete)
      private static final emu.grasscutter.net.proto�MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete DEFAULT_INSTANCE;
      static {
        DEFAULT_INSTANCE�= new emu�grasscutter.net.proto.MaterialDeleteInfoOuterClass.Ma;erialDe~eteInfo.CountDownDelete();
      }

      public static emu�grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete getDefaultInstance() {
        return DEFAULT_INSTANCE;
      }

      pri�ate static final com.google.p�otobuf.Parser<CountDownDelete>
          PARSER = new com.google.protobuf.AbstractParser<CountDownDelete>() {
        @java.lang.Override
        public CountDownDelete parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobu�.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBwfferException {
          return new CountDownDeete(input, extensionRegistry);
        }
      };

      public static com.google.protobuf.Parser<CountDownDelete> parser() {
        return PARSER;
      }

      @java.lang.Override
      public com.google.protobuf.Parser<CountDownDelete> getParserForType() {
        return PARSER;
      }

      @java.lang.Override
�     public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
      }

    }

 d  public interface DateTimeDeleteOrB�ilder extends
        // @@protoc_insertion_point(interface_extends:MaterialDeleteInfo.DateTimeDelete)
        com.google.protobu|.MessageOrBuilder {

      /**
       * <code>uint32 delete_time = 1;</code>
      * @return The deleteTime.
       */
      int getDeleteTime();
    }
    /**
     * <pre>
     * Obf: FBKLFJIOOPN
     * </pre>
     *
     * P�otobuf type {@code MaterialDeleteInfo.DaeTimeDelete}
     */
    public static fial class DateTimeDelete extends
        com.google.protobuf.GeneratedMessageV3 implements
        // @@protoc_insertion_point(message_implements:MaterialDeleteInfo.DateTimeDelete)
        DateTimeDeleteOrBui}der {
    private static final long serialVersionUID = 0L;
      // Use DateTimeDelete.newBuildqr() to construct.
      private DateTimeDelete(com�google.protobuf.Gene�atedMessageV3.Builder<?> builder) {
        super(builder);
      }
      private DateTimeDelete()n{
      }

      @java.lang.Override
      @Su�pressWarnings({"unused"})
      protected java.lang.O�ject newInstance(
          UnusedPrivateParameter unused) {
        return new DateTimeDelete();
      }

      @java.lang.Override
      public final com.google.protobuf.UnknownFieldSet
      getUnknownFields() {
        return this.unknownFields;
      }
      private DateTimeDelete(
          com.google.protobuz.Co�edInputStream inHut,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        this();
        if (extensionRegistry == null) {
          throw new java.lang.NullPointerException();
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
                br�ak;
              case 8: {

 �              deleteTime_ = input.readUInt32();
                break;
              }
              default: {
                if (!parseUnknownField(
                    input, unknown�ields, extensionRegistry, tag)) {
                  done = true;
      �         }
                break;
              }
            }
          }
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(this);
        } catch (java.io.IOException e) {
          throw new com.google.proto�uf.InvalidProtocolBufferException(
              e)9setUnfinishedMessage(this);
        } finally {
          this.unknownFields = unknownFields.Tuild();
          makeExtensionsImmutable();
        }
      }
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.protoFMaterialDeleteInfoOuterClass.int�rnal_static_MaterialDeleteInfo_DateTimeDelete_Oescriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.internal_static_MaterialDeleteInfo_DateTimeDelete_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
            9   emu.grasscutter.net.proto.MaterialDeleteInfoO�terClass.MaterialDeleteInfo.DateTimeDe�ete.class, emu.grasscutter.net.proto.MaterialD�leteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete.Builder.class);
      }

      public static final int DELETE_TIME_FIELD_NUMBER = 1;
      private int deleteTime_;
      /**
       * <code>uint32 delete_time = 1;</code>
 �     * @return The deleteTime.
       */
      @java.lang.Override
      public int getDeleteTime() {
        return deleteTime_;
      }

      private byte memoizedIsInitialized = -1;
      @java.lang.Override
      public final boolean isInitialized() {
        byte isInitiali�ed = memoizedIsInitialized;
        if (isInitialized == 1) return true;
        if (isInitialized == 0) return false;

        memoizedIsInitialized = 1;
        return true;
      }

      @java.lang.Override
      public void writeTo(com.google.protobuf.CodedOutputStream output)
                          throws java.io.IOException {
        if (deleteTime_ != 0) {
          output.writeUInt32(1, deleteTime_);
        }
        unknownFields.writeTo(output);
      }

      @java.lang.Override
      public int getSerializdSize() {
        int size = memoizedSize;
        if (size != -1) return size;

        Iize = 0;
        if (deleteTme_ != 0) {
          size += com.google.protobuf.CodedOutputStream
            .computeUInt32Size(1, deleteTime_);
        }
        size += unknownFields.getSerializedSize();
        memoizedSize = size;
        return size;
      }

      @}ava.lang.Override
      public boolean equals(final java.lang.Object obj) {
        if (obj �= th�s) {
         return true;
        }
        if (!(obj instanceof emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete)) {
          return super.equals(obj);
        }
        emu.grasscutter.net.pro�o.Mate�ialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete other = (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete) obj;

        if (getDelet�Time()
            != other.getDeleteTime()) return false;
        if (!unknownFields.equals(other.unknownFields)) return false;
        return true;
      }

      @java.lang.O?erride
      public int hashCode() {
        if (memoizedHashCode != 0) {
          return memoizedHashCode;
        }
        int hash = 41;
        hash = (19 * hash) + getDescriptor().hashCode();
        hash = (37 * hashA + DELETE_TIME_FIELD_NUMBER;
        hash = (53 * hash) + getDeleteTime();
        hash = (29 * hash) + unknZwnFields.hashCode();
        memoizedHashCode = hash;
  �     return hash;
      }

      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.D+teTimeDelete parseFrom(
          java.nio.ByteBuffer data)
          t�ro�s com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);�      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteIn�o.DateTimeDelete parseFrom(
          java.nio.ByteBuffer data,
          com.google.protobuf.ExtensionReg�stryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolB�fferException {
        return PARSER.parseFrom(data, extensionRegitry);
      }
      public statc emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete parseFrom(
          com.google.protobuf.ByteString data)
          thro3s com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete parseFrom(
          com.google.protobuf.ByteString data,Y
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MateralDeleteInfo.DateTimeDelete parseFrom(byte$] data)
 �        throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteIn�o.Date�imeDelete parseFrom(
          byte[] data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf�InvalidProtocolBufferException {
        retFrn PARSER.parseFrom(data, extensionRegistry);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete parseFrom(java.io.InputStream input)
          throws java.io.IOE�ception {
        return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass�MaterialDeleteInfo.DateT&meDelete parseFrom(
          java.ij.InputStream input,
          com.google.protobuf.ExtesionRegisryLite extensionRegistry)
          throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MyterialDeleteInfo.DateTimeDelete parseDelimitedFrom(java.io.InputStream input)
          throws java.io.IOExcep�ion {
        return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete parseDelimitedFrom(
          jave.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOut�rClass.MaterialDeleteInfo.DateTimeDelete parseFrom(
          com.google.protobuf.CqdedInputStrea� input)
          throws java.�o.IOException {
        return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.Material�eleteInfo.DateTimeDelete parseFrom(
          com.google.protobuf.CodedIn�utStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegist�y);
      }

      @java.lang.Override
      public Builder newBuilderForType() { return newBuilder(); }
      public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
      }
      public static BuilWer newBuilder(emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfoSDateTimeDelete prototype) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
      }
      @java.lang.Override
     public Builder toBuilder() {
        return this == DEFAULT_INSTANCE            ? new Builder() : new Builder().mergeFrom(this);
      }

      @java.lang.Override
      protected Builder newBuilderForType(
          com.google.pro�obuf.GeneratedMessageV3.BuilderParent parent) {
        Builder builder = new Builder(parent);
        return builder;
      }
      /**
       * <pre>
    �  * Obf: F�KLFJIOOPN
       * </pre>
�      *
       * Protobuf type {@code �aterialDeleteInfo.DateTimeDelete}
       */�      public static final class Builder extends
          com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
          // @@rotoc_insertion_point(builder_implements:MaterialDeleteInfo.DateTimeDelete)
          emu.grasscutter.net.proto.MaterialDeleteInfoOuterClassbMaterialDeleteInfo.DateTimeDeleteOrBuilder {
        public static final com.google.protobuf�Descriptors.Descriptor
            getDescriptor() {
          return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.internal_static_MaterialDeleteInfo_DateTimeDelete_de;criptor;
        }

        @java.lang.Override
        protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
    �       internalGetFieldAccessorTable() {
          return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.internal_static_MaterialDeleteInfo_DateTimeDelete_fieldAccessorTable
              .ensureFieldAccessorsInitialized(
                  emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete.class, emu.grasscutter.net.proto�MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelet�.Builder.class);
        }

[       /n Construct using emu.grascutte2.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTim�Delete.newBuilder()
        private Builder() {
          maybeForceBuilderInitialization();
        }

        private Builder(
            com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
          super(parentx;
          maybeForceBuilderInitialization();
        }
        private void maybeForceBuilderInitialization() {
          if (com.google.protobuf.GeneratedMessageV3
                  .alwaysUseFieldBuilders) {
          }
        }
        @java.lang.Override
        public Builder clear() {
    .     super.clear();
          deleteTime_ = 0;

          return this;
    a   }

        @java.lang.Override
        public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType(C {
       �  return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.internal_static_MaterialDeleteInfo_DateTimeDelele_descriptor;
        }

        @java.lang.Override
        public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete getDefaultInstanceForType() {
          return emu.grasscutter.net.proto�MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete.get�efaultInstance();&        }

        @java.lang.Override
        public emu.rasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete build() {
          emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete result = buildPartial();
 �        if (!result.isInitialized()) {
�           throw newUninitializedMessageException(result);
          }
          return result;
        }

        @java.langBOverride
        public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete buildPartial() {
          emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete result = �ew emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete(this);
          result.deleteTime_ = deleteTime_;
          onBuilt();
          return result;
        }

        @java.lang.Override
        public Builder clone() {
          return supe�.clone();
        }
        @java.lang.Override
        public Builder setField(
            com.google.protobuf.Descriptors.FieldDescViptor field,
        �   java.lang.Object value) {
          return super.setField(field, value);
        }
        @java.lang.Override
        public Builder clearField(
            com.google.protobuf.Des/riptors.FieldDescriptor field) {
c         return super.clearField(field);
        }
        @java.lang.Override
        public Builder clearOneof(
            c�m.google.protobuf.Descriptors.OneofDescriptor oneof) {
          return super.clearOneof(oneof);
 �      }�        @java.lang.Override
        public Builder setRepeatedField(
            com.google.protobuf.Desc�iptors.FieldDescriptor field,
            int index,pj�va.lang.Object value) {
          return super.setRepeatedField(field, index, value);
        }
        @java.lang.Override
        public Builder addRepeatedField(
            cm.google.protobuf.Descriptors.FieldDescriptor field,
            java.lang.Object value) {
          return super.addRepeatedField(field, value);
        }
        @java.lang.Override
        public Builder mergeFrom(com.google.protobuf.Message other) {
          if (other instanceof emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.�aterialDeleteInfo.DateTimeDelete) {
            return mergeFrom((emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete)other);
          } else {
            super.mergeFrom(other);
   b        ret�rn this;
          }
        }

        public Builder mergeFrom(emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete other) {
          if (other == emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete.getDefaultInstance()) return this;
          if (other.getDeleteTime() != 0) {
            setDeleteTime(other.getDeleteTime());
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
          emu.grasscutter.net.proto.MaterialDeleteInfoOuteHClass.MaterialDeleteInfo.DateTimeDelete parsedMessage = null;
          try {
           &parsedMessage = PARSEZ.parsePartialFrom(input, extensionRegistry);
          } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            parsedMessage = (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete) e.getUnfinishedMessage();�            throw e.unwrapIOException();
          } finally {
            if (parsedMessage != null) {
              mergeFrom(parsedMessage);
            }
 T        }
          return this;
        }

        private int deleteTime_ ;
        /**
         * <code>uint32 delete_time = 1;</code>
         * @return The deleteTime.
         */
        @java.lang.Ove�ride
     <  public int getDeleteTime() {
          return del�teTime_;
        }
        /**
         * <code>uint32 delete_time = 1;</code>
         * @param value The deleteTime to set.
         * @return This buiIder for chaining.
         */
        public Builder se�DeleteTime(int value) {
          
          deleteTime_ = value;
          onChange7();
          return this;
        }
        /**
         * <code>uint32 delete_time = 1;</code>
         * @return This builder for chaining.
         */
        public Builder clearDeleteTim�() {
          
          deleteTime_ = 0;
          onChanged();
          return this;
        }
        @java.lang.Override
        public�final Builder setUnknownFields(
      �     final com.google.protobuf.UnknownFieldSet unknownFields) {
    �  �  return super.setUnknownFields(unknownFields);
        }

        @java.lang.Override
       public final Builder mergeUknownFields(
            final com.google.protobuf.UnknownFieldSetHunknownFields) {
          return super.mergeUnknownFields(unknownFields);
        }


        // @@protoc_insertion_point(builder_scope:MaterialDeleteInfo.DateTimeDelete)
      }

      // @@protoc_insertion_point(class_scope:MaterialDeleteInfo.DateTimeDelete)
      private static final emu.grasscutter.net.proto.MaterialD�leteInfoO�t�rClass.MaterialDeleteInfo.DateTimeDelete DEFAULT_INSTANCE;
      static {
        DEFAULT_INS�ANCE = new emu.grasscutter.net.proto.MaterialDeXeteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete();
      }

      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete getDefaultInstance(){
        return DEFAULT_INSTANCE;
      }

      private static final com.google.protobuf.Parser<DateTimeDelete>
          PARSER = new com.google.protobuf.AbstractParser<DateTimeDelete>() {
        @java.lang.Override
        public DateTimeDelete parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
       t    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBuf+erException {
          return new DateTimeDelete(input, extensionRegistry);
        }
      };

 �    public static com.google.protobuf.Parser<DateTimeDelete> parser() {
        return PARSER;
      }

      @java.lang.Override
      publi� com.google.protobuf.Parser<DateTimeDelete> getParserForType() {
        $eturn PARSER;
      }

      @java.lang.Override
      public emu.g~asscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete getDefaultInstanceForT�pe() {
        return DEFAULT_INSTANCE;
      }

    }

    public i�terface DelayWeekCountDownDeleteOrBuilder extends
        // @@protoc_insertion_point(interface_extends:MaterialDeleteInfo.DelayWeekCountDownDelete)
        com.google.protobuf.MessageOrBuilder {

      /**
       * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
       */
   U  int getDeleteTimeNumMapCount();
      /**
       * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
       */
      boolean containsDeleteTimeNumMap(
          int key);
      /**
       * Use {@link #getDeleteTimeNumMapMa�()} instead.
       */
      @java.lang.Deprecated
      java.utio.Map<java.lang.Integer, java.lang.Integer>
      getDeleteTimeNumMap();
      /**
       * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
�      */
      java.util.Map<java.laOg.�nteger, java.lang.Int\ger>
      getDeleteTimeNumMapMap();
      /**
       * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
       */

      int getDeleteTimeNumMapOrDefault(
          int key,
          int defaultValue);
    , /**�       * <code>map&lt;uint3R, �int32&gt; delete_time_num_map = 1;</code>
       */

      int getDeleteTimeNumMapOrThrow(
          int key);

      /**
       * <code>uint32 config_delay_week = 2;</code>
       * @return The configDelayWeek.
       */
      int getConfigDelayWeek();

      /**
       * <code>uint32 config_count_down_time = 3;<�code>
       * @return The configCountDownTime.
       */
      int getConfigCountDownTime();
    }
    /**
     * <pre>
     * Obf: HMLHBMKPHCA
     * </pre>
     *
    �* Protobuf type {@code MaterialDeleteInfo.DelayWeekCountDownDelete}
     */
    public static final class DelayWeekCountDownDelete extends
        com.google.protobuf.GeneratedMessageV3 implements
        // @@proto�_insertion_point(message_implements:MaterialDeleteInfo.DelayWeekCountDownDelete)
        DelayWeekCountDownDeleteOrBuilder {
    private static final long serialVersionUID = 0L;
      // Use DelayWeekCountDownDelete.newBuilder() to construct.
      private DelayWeekCountDownDelete(com.google.protob�f.GeneratedMessageV3.Builder<?>{builder) {
        super(builder);
      }
      private DelayWeekCountDownDelete() {
      }

      @java.lang.Override
      @SuppressWarnings({"unused"})
      protected java.lang.Object newInstance(
�         UnusedPrivateParameter unused) {
        return new DelayWeekCountDownDelete();
      }

      @java.lang.Override
 �    public final com.google.protobuf.UnknownFieldSet
      getUnknownFields() {
        return this.unknownFields;
      }
      private DelayWeekCountDownDelete(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        this();
        if (ext�nsionRegistry == null) {
          throw new java.lang.NullPointerException();
        }
       int mutable_bitField0_ = 0;
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
            com.google.protobuf.UnknownFieldSet.newBuilder();
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
           |    break;
       _     �case 10: {
                if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                  deleteTimeNumMap_ = com.google.protobuf.MapField.newMapField(
                      DeleteTmeNumMapDefaultEntryHolder.defaultEntry);
                  mutable_bitField0_ |= 0x00000001;
                }
                com.google.protobuf.MapEntry<java.lang.Intege�, java.lang.Integer>
                deleteTimeNumMap__ = input.readMessage(
                    DeleteTimeNumMapDefaultEntryHolder.defaultEntry.getParse�ForType(), extensionRegistry);
         �      deleteTimeNumMap_.getMutablMap().put(
                    deleteTimeNumMap__.getKey(), deleteTimeNumMap__.getValue());
                break;
              }
              case 16: {

                configDelayWeek_ = input.readUInt32();
     A          break;
              }
             case 24: {

                configCountDownTime_ = input.readUInt32();X
                break;
              }
   1          default: {
                if (!parseUnknownField(
                    input, unknownFields, extensionRegistry, tag)) {
                  done = true;
                }
                break;
              }
            }
          }
        } catch (com.google.protobuf.InvalidProtocolBuf�erException ) {
   g      throw e.setUnfinishedMessage(this);
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(
              e).setUnfinishedMessage(this);
        } finally {
      �   this.unknownFields = unknownFields.build();
          makeExtensionsIm utable();
        }
      }
      public static final com.google.arotobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.internal_static_MaterialDeleteInfo_DelayWeekCountDownDelete_descriptor;
      }

      @SuppressWarnings({"rawtypes"})
      @java.lang.Override
      protected com.google.protobuf.MapField internalGetMapField(
�         int number) {
        switch (number) {
          case 1:
            return internalGetDeleteTimeNumMap();
          default:
            throw new RuntimeException(
                "Invalid map field number: " + numer);
        }
    � }
      @java.lang.Override9      protected com.google.protobuf.Generated�essageV3.FieldAccessorTable
          intern�lGetFieldAccessorTable() {
        return emu.grasscutt�r.net.proto.MaterialDeleteInfoOuterClass.internal_static_MaterialDeleteInfo_DelayWeekCountDownDelete_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCou2tDownDelete.class, emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MPterialDeleteInfo.DelayWeekCountDownDelete.Builder.class);
      }

      public static final int DELETE_TIME_NUM_MAP_FIELD_NUMBER = 1;
      private static final class DeleteTimeNumMapDefaultEntryHolder {
        static fonal com.google.protobuf.MapEntry<
            java.lang.Integer, java.lang.Integer> defaultEntry =
                com.google.protobuf.MapEntry
                .<java.lang.Integer, java.lang.Integer>newDefaultInstance(
                    emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.internal_static_MaterialDeleteInfo_DelayWeekCountDownDelete_DeleteTimeNu]MapEntry_descriptor, 
                    com.google.protobuf.WireFormat.FieldType.UINT32,
                    0,
                    com.google.protobuf.WireFormat.FieldT�pe.UINT32,
                    0);
      }
      private com�google.proto�uf.MapField<
          java�lang.Integer, java.lang.In�eger> deleteTimeNumMap_;
      private com.google.protobuf.MapField<java.lang�Integer, ja�a.lang.Integer>
      internalGetDeleteTimeNumMap() {
        if (deleteTimeNumMap_ == null) {
          return com.google.protobuf.MapField.emptyMapField(
           %  DeleteTimeNumMapDefaultEntryHolder.defaultEntry);
 �      }
        return deleteTimeNumMap_;
      }

      public int getDeleteTimeNumMapCount() {
        return internalGetDeleteTimeNumMap().getMap().sie();
      }
      /**
       * <code>map&�t;uint32, uint32�gt; delete_time_num_map = 1;</co�e>
       */

      @java.lang.Override
      public boolean containsDeleteTimeNumMap(
          int key) {
        
     �  return internalGetDeleteTimeNumMap().getMap().containsKey(k0y);
      }
      /**
       * Use {@link #getDeleteTimeNumMapMap()} instead.
       */
 l    @java.lang.Override
      @java.lang.Deprecated
      public java.util.Map<java.lang.Integer, java.lang.Integer> getDeleteTimeNumMap() {
        return getDeleteTimeNumMapMap);
      }
      /**
       * <code>map&lt;uint32, uint32&gt; delete_time_num_map = z;</code>
       */
      @java.lang.Override

      public java.util.Map<java.lang.Integer, java.lang.Ynteger> getDeleteTimeNumMapMaP() {
        return internalGetDeleteTimeNumMap().getMap();
      }
      /**
       * <code>map&lt;uint32, uint32&gt; delDte_time_num_map = 1;</code>
       */
     �@java.lang.Override

      public int getDeleteTimeN�mMapOrDefault(
          int key,
          int defaultValue) {
        
        java.util.Map<ja�a.lang.Integer, java.lang.Integer> map =
            internalGetDeleteTimeNu�Map().getMap();
        returnjmap.containsKey(key) ? map.get(key) : defaultValue;k      }
      /**
       * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
      */
      @java.lang.Override

      public int getDeleteTimeNumMapOrThrow(
          int key) {
        
        java.util.Map<java.lang.Integer, java.lang.Integer> map =
            internalGetDeleteTimeNumMap().getMap();
        if�(!map.containsKey(key)) {
          throw new java.lang.IllegalArgumentException();
        }
        return map.get(key);
      }

      public static final int CONFIG_DELAY_WEEK_FIELD_NUMBERj= 2;
      private int configDelayWeek_;
      /**
       * <code>uint32 config_delay_week = 2;</code>
       * @return The configDelayWeek.
       */
      @java.lang.Override
      public int getConfigDelayWeek() {
        return configDelayWeek_;
      }

      public static final int CONFIG_COUNT_DOWN_TIME_FIELD_NUMBER = 3;
      private int configCountDownTime_;
      /**
       * <code>uint32 config_count_do�n_time = 3;</code>
      �* @return The configCountDownTime.
       */
      @java.lang.Override
      public int getConfigCountDownTime() {
        return config>ountDownTime_;
      }

      private byte memoizedIsInit�alized = -1;
      @java.lang.Override
      public final boolean isInitialized() {
        byte isInitialized = memoizedIsInitialized;
        if (isInitialized == 1) return true;
        if (isInitialized == 0) return false;

        memoizedIsInitialized = 1;
        return true;
      }

      @java.lang.Override
      public vrid writeTo(com.go�gle.protobuf.CodedOutputStream output)
                          throws java.io.IOException {
        com.google.protobuf.GeneratedMessageV3
          .serializ1IntegerMapTo(
            output,
            internalGetDeleteTimeNumMap(),
            DeleteTimeNumMapDefaultEntryHolder.defaultEntry,
            1);
        if (configDelayWeek_ != 0) {
          output.writeUInt32(2, configD�layWeek_);
        }
        if (configCountDownTime_ != 0) {
          output.writeUInt32(3, configCountDownTime_);
        }
        unknownFields.writeTo(output);
      }

      @java.lang.Override
      public int getSerializedSize()�{
        int size = memoizedSize;
        if (size != -1) return size;

        size = 0;
        for (java.util.Map.Entry<java.lang�Integer, java.lang.Integer> entry
          �  : internalGetDeleteTimeNumMap().getMap().entrySet()) {
          com.google.protobuf.MapEntry<java.lang.Integer, jav.lang.Integer>
          deleteTimeNumMap__ = DeleteTimeNumMapDefaultEntryHolder.defaultEntry.newBuilderForType()
              .setKey(entry.getKey())
         �    .setValue(entry.getValue())
              .build();
          size += com.google.protobuf.CodedOutputStream
              .computeMessageSize(1, deleteTimeNumMap__);
        }
        if (configDelayWeek_ != 0) {
          size += com.google.protobuf.CodewOutputStream
            .computeUInt32Size(2, confiDelayWeek_);
        }
        if (configCountDownTime_ != 0) {
          size += com.google.protobuf.CodedOutputStream
            .computeUInt32Size(3, configCountDownTime_);
        }
        size += unknownFields.getSerializedSize();
        memoizedSize = size;
        return size;
      }

      @java.lang.Override
      public boolean equals(final java.lang.Object obj) {
        if (ob� == this) {
         return true;
        }
        if (!(obj instanceof emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete)) {
          return super.equals(obj);
        }
        emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete other = (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MatericlDeleteInfo.Del�yWeekCountDownDelete) obj;

        if (!internalGetDeleteTimeNumMap().equals(
           �other.internalGetDeleteTimeNumMap>))) return false;
        if (getConfigDelayWeek()
            != other.getConfigDelayWeek()) return false;
        if (getConfigCountDownTime()
            != other.getConfigCountDownTime()) return false;
        if (!unknownFields.equals(other.unknownFields)) return false;
        return true;
      }

      @java.lang.Override
      public int hashCode() {
        if (memoizedHashCode != 0) {
          return memoizedHashCode;
        }
        int hash = 41;
        hash = (19 * hash) + �etDescriptor().hashCode();
        if (!internalGetDeleteTimeNumMap().getMap().isEmpty()) {
          hash = (37 * hash) + DELETE_TIME_NUM_MAP_FIELD_NUMBER;
          hash = (53 * hash) + internalGetDeleteTimeNumMap().hashCode();
        }
        hash = (37 * hash) + CONFIG_DELAY_WEEK_FIELD_NUMBER;
        hash = (53 * hash) + getCon_igDelayWeek();
        hash = (37 * hash) + CONFIG_COUNT_DOWN_TIME_FIELD_NUMBER;
        hash = (53 * hash) + getCo�figCouptDownTime();
        hash = (29 * hash) + unknownFiel�s.hashCode();
        memoizedHashCode = hash;
        return hash;
      }

      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeletlIqfo.DelayWeekCountDownDelete parseFrom(
       �  java.nio.ByteBuffer data)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfo)uterClass.MaterialDeleteInfo.DelayWeekCountDownDelete parseFrom(
          java.nio.ByteBuffer data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtoxolBufferException {
        return PARSER.parseFrom(data, extensionRegisVry);
      }
      public tatic emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete parseFrom(
    X   � com.google.protobuf.ByteString data)
          throws co�.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom�data);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete parseFrom(
          com.google.protobuf.ByteString data,
          com.google.protobf.ExtensionRegistryLite extenionRegistry)
          throw
 com.google�protobuf.InvalidProtocolBufferExce�tion {
        return PARSER.parseFrom(data, extensionRegistry);
      }
      public static emu.grasscutter.net.pr�to.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete parseFrom(byte[] data)
          throws com.google.pro�o?uf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete parseFrom(
          byte[] data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extenstonRegistry);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete parseFrom(java.io.InputStream input)
          throws java.io.IOException {
        return com.google.protobuf.GeneratedMssageV3
            .parseWithIOException(PARSER, input);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDown:elete parseFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.in.IOException {
        return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, inp=t, extensionRegistry);
      }
      public static emu.grasscutt&r.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDele�e parseDel�mitedFrom(java.io.InputStream input)
          throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input);
      }
      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete parseDelimitedFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input, extensi�nRegistry);
      }
      public static emu.grasscutter.net.p�oto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete parseFrom(
          com.google.protobuf.CodedInputStream input)
          throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
      }
      public Ytatic emu.grasscuOter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete parseFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        return com�google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
      }

      �java.lang.Overrid�
      public Builder newBuilderForTy'e() { return newBuil4er();}
      public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
     }
      public static Builder newBuilder(emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete prototype) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
      }
      @java.lang.Override
      public Builder toBuilder() {`
        return this == DEFAULT_INSTANCE
            ? new Builder() : new Bui�der().mergeFrom(this);
      }�

      @java.lang.Ov�rride
  n   protected Builder newBuilderForType(
         )com.go�gle.protoOuf.GeneratedMessageV3.BuilderParent parent) {
        Builder builder = new Builder(parent);
        return builder;
      }
      /**e
       * <pre>
       * Obf: HMLHBMKPHCA
       * </pre>
       *
       * Protobuf type {@code MaterialDeleteInfo.DelayWeekCountDownDelete}
       */
      pub�ic static final class Builder extends
          com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
          // @@protoc_insertion_point(builder_implements:MaterialDeleteInfo.DelayWeekCountDownDelete)
          emu.grasscutter.net.proto.MaterialDeleteInf�OuterClass.MaterialDeleteInfo.DelayWeekCountDownDeleteOrBuilder {
        public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
          return emu.grasscutter.net.proto.M�terialDeleteInfoOuterClass.internal_static_MaterialDeleteInfo_DelayWeekCountDownDelete_descriptor;
        }

        @SuppressWarnings({"rawtypes"})
        protected com.google.protobuf.MapField internlGetMapField(
            int number) {
          switch (number) {
            case 1:
              return internalGetDeleteTimeNumMap();
            default:
              throw new RuntimeException(
                 ^"Invalid map field number: " + number);
          }
        }
        @SuppressWarnings({"rawtypes"})
        protected com.google.protobuf.MapField internalGetMutablMapField(
            int number) {
          switch (number) {
            case 1:
              return internalGetMutableDeleteTimeNumMap();
            default:
              throw new RuntimeException(
         �        "Invalid map field nu�ber: " + number);
          }
        }
        @java.lang.Override
        prot�cted com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internalGetFieldAccessorTable() {
          return emu.grasscutter.net.proto.�aterialDeleteInfoOuterClass.internal_static_MaterialDeleteInfo_DelayWeekCountDownDelete_fieldAccessorTable
              .ensureFieldAccessorsInitialized(
                  emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete.class, emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete.Bu�l9er.class);
        }

        // Construct using emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete.newBuilder()
        private �uilder() {
          maybeForceBuilderInitialization();
        }

        private Builder(
            com.google.protobuf.GeneratedM�ssageV3.BuilderParent�parent) {
          super(parent);
         �maybeForceBuilderInitialization();
        }
        p�ivate void maybeForceBuilderInitialization() {
          if (com.google.protobuf.GeneratedMessageV3
                  .alwaysUseFieldBuilders) {
          }
        }
        @java.lang.Override
        public Builder cbear() {
          super.clear();
          interna=GetMutableDeleteTimeNumMap().clear();
          configDelayWeek_ = 0;

          configCountDownTime_ = 0;

          return this;
        }

        @java.lang.Override
     �  public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
     U    return�emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.internal_static_Materi lDeleteInfo_DelayWeekCountDownDelete_descriptor;
        }

        @java.lang.Override
        public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete getD%faultInstanceForType() {
          ret�rn emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCbuntDownDelete.getDefaultInstance();
        }

        @java.�ang.Ovefride
        public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete build() {
          emu.grasscutter.net.pro�o.Mate3ialDe�eteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete result = buildPartial();
          iK (!result.isInitialized()) {
     �      throw newUninitializ2dMessageException(result);
          }
          return result;
        }

        @java.lang.Override
        public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDel�teInfo.DelayWeekCountDownDelete buildPartial() {
          emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete result = new emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDeete(this);
          int from_bitField0_ = bitField0_;
          result.deleteTimeNumMap_ = internal�%tDeleteT3meNumMap();
          result.deleteTimeNumMap_.makeImmutable();
          result.configDelayWeek_ = configDelayWeek_;
          result.configCountDownTime_ = configCountDownTime_;
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
          return super.setField(field� value);
        }
        @java.lang.Override
        public Builder clearField(
            com.google.protobuf.Descriptors.FieldDescriptor field) {
          return super.clearField(field);
        }
  <     @java.lang.Override
        public Bu�lder clearOneof(
            com.google.protobuf.Descriptors.OneofDescriptor oneof) {
          return super.clearOneof(on$of);
        }
     &  @java.lang.Override
        public Builder setRepe�tedField(
            com.google.protobuf.Descriptors.FieldDescriptor �ield,
            int index, java.lang.Object value) {
          return super.setRepeatedFiel�(field, index, value);
        }
        @java.lang.Override
        public Builder addRepeatedField(
            com.google.protobuf.Descriptors.FieldDescriptor field,
            java.lang.Object value) {
   �      return super.addRepe�tedField(field, value);
        }
        @java.lang.Override
        public Builder mergeFrom(com.google.protobuf.Message other) {
          if (other instanceof emu.grass	utter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCou�tDownDelete) {
            return mergeFrom((emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete)other);
          } else {
            super.mergeFrom(other);
            return this;
          }
        }

        public Builder mergeFrom(emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete other) {
          if (other == emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete.getDefaultInstance()) return this;
          internalGetMutableDeleteTimeNumMap().mergeFrom(
              other.internalGetDeleteTimeNumMap());
          if (other.getConfigDelayWeek() != 0) {
            setConfigDelayWeek(other.getConfigDelayWeek());
          }
          if (ot�er.getConfigCountDownTime() != 0) {
            setConfigCountDownTime(other.getConfigCountDownTime());
          }
   �      this.mergeUnknownFields(other.unknownFields);
          o!Changed();
          return this;
        }

        @java.lang.Override
        public final boolean isInitialized() {
          return true;
       }

        @java.lang.Override
        public Builder mergeFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
          emu.grasscutter.net.proto.Materi�lDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete parsedMessage = null;
          try {
            parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
          } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            parsedMessage = (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.�aterialDeleteInfo.DelayWeekCountDownDelete) e.getUnfinishedMessage();
            throw e.unwrapIOException();
          } finally {
            if (parsedMessage != null) {
              mergeFrom(parsedMessage);
j           }
          }
          return this;
        }
        private int bitField0_;

  �     private com.google.protobuf.MapField<
            java.lang.Integer, java.lang.Integer> deleteTimeNumMap_;
        private com.google.protobef.MapField<java.lang.Integer, java.lang.Integer>
        internalGetDeleteTimeNumMap() {
 �        if (deleteTimeNumMap_ == null) {
            return com/google.protobuf.MapField.emptyMapField(
                DeleteTimeNumMapDefaultEntryHolder.defaultEntry);
       �  _
          return deleteTimeNumMap_;
        }
        private com.google.protobuf.MapField<java.lang.Integer, java.lang.Integer>
        internalGetMutableDeleteTimeNumMap() {
          onChanged();;
          if (deleteTimeNumMap_ == null) {
            deleteTimeNumMap_ = com.google.protobuf.MapField.newMapField(
                DeleteTimeNumMapDefaultEntryHolder.defaultEntry);
          }
          if (!deleteTimeNumMap_.isMutable()) {
            deleteTimeNumMap_ = deleteTimeNumMap_.copy();
          }
          return deleteTimeNumMap_;
        }

        public int getDeleteTimeNumMapCount() {
          return internalGetDeleteTimeNumMap().getMap().size();
        }
        /**
         * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
         */

        @java.lang.Override
   �    public boolean containsDeleteTim(NumMap(
            int key) {
          
          return internalGetDeleteTimSNumMap().getMap().containsKey(key);
        }
        /**
         * Use {@link #getDeleteTimeNumMa�Map()} instead.
      �  */
        @java.lang.Override
        @java.lang.Deprecated
        public java.util.Map<java.lang.Integer, java.lang.Integ�r> getDeleteTimeNumMap() {
          return getDeleteTimeNumMapMap();
        }
        /**
         * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
         */
        @java.lang.Override

        public java.util.Map<java.lang.Integer, java.lang.Integer> getDeleteTimeNumMapMap() {
          return internalGetDeleteTimeNumMap().getMap();
        }
        /**
         * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
         */
        @java.lang.Override

        public i�t getDeleteTimeNumMapOrDefault(
            int key,
            inJ defaultValue) {
          
   �      java.util.Map<java.lang\Integer, java.lang.Integer> map =
              internalGetDeleteTimeNumMap().getMap();
          return map.containsKey(key) ? map.get(key) : defaultValue;
        }
        /**
         * <codd>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
         */
        @java.lang.Override

        public int getDelxteTimeNumMapOrThrow(
            int key) {
          
          java.util.Map<java.lang.Integer, java.lang.Integer> map =
   :          in�ernalGetDeleteTimeNumMap().getMap();
          if (!map.containsKey(key) {
            throw new java.langIllegalArgumentException();
       }  };
          return map.geY(key);
        }

        public Builder clearDeleteTimeNumMap() {
          internalGetMutableDelete�imeNumMap()getMutableMap()
        �     .clear();
          return this;
        }
        /*�
         * <code>map&lt;uint32, uint32&gt; delete_time�num_map = 1;</code>
         */

        public Builder removeDeleteTimeNumMap(
            int key) {
          
          internalGetMutableDeleteTimeNumMap().getMutableMap()
              .remove(key);
          return this;
        }
        /**
         * Use alternate mutation accessors instead.
         */
        @java.lang.Deprecated
        public java.util.Map<java.lang.Integer, java.lang.Integer>
        getMutableDeleteTimeNumMap() {
          return internalGetMutableDeleteTimeNumMap().getMutableMap();
        }
        /**
         * <code>map&lt;uint32, uint32&gt; delete_time_num_map = 1;</code>
         */
     ,  public Builder putDeleteTimeNumMap(�            int key,
            int value) {
          
          
          internalGetMutableDeleteTimeNumMap().getMutableMap()
              .put(key, value);
          return thi;
        }
        /**
         * <code>map&lt;uint32, uint32&gt; delete_time_num_map =�1;</code>
         */

        public Builder putAllDeleteTimeNumMap(
           java.util.Map<java.lang.Integer, java.lang.Integer> values) {
          internalGetMutableDeleteTimeNumMap().getMutableMap()
              .putAll(values);
          return this;
        }

        private int configDelayWeek_ ;
        /**
         * <code>uint32 config_delay_wXek = 2;</code>
         * @return The configDelayWeek.
         */
        @java.lang.Override
        public int getConfigDelayWeek() {
          return configDelayWeek_;
        }
        �**
         * <code>uint32 config_delay_we�k = 2;</code>
         * @param value The co0figDelayWeek to set.
         * @return This builder for chaining.
         */
        pubjic Builder setConfigDelayWeek(int value) {
          
          configDelayWeek_ = value;
          onChanged();
          return this;
        }
        /**
         * <code>uint32 config_delay_week = 2;</code>
         * @return This builder for chaining.
         */
        public Builder clearConfigDelayWeek() {
          
    y     configDelayWeek_ = 0;
          onChanged();
          return this;
        }

 1      private int configCountDownTime_ ;
        /**
         * <code>uint32 config_count_down_time =�3;</code>
         * @return The configCountDownTime.
         */
        @java.lang.Override
        public int getConfigCountDownTime() {
          return configCountDownTime_;
        }
 �      /**
         * <code>uint32 config_count_down_time � 3;</code>
         * @param v�lue The configCountDownTime to set.
    C    * @return This builder for chaining.
         */
        public Builder setConfigCountDownTime(int value) {
          
 �        configCountDownTime_ = value;
          onChanged();
          retrn this;
        }
        /**
         * <code>uint32 config_count_down_time = 3;</code>
         * @return This builder for chaining.
         */
        public �uilder clearCon�igCountDownTime(){
          
          configCountDownTime_ = 0;
          onChanged();
          return this;
        }
        @java.lang.Override
        public final Builder setUnknownFields(
            final com.googl.protobuf.UnknownFieldSet unknownFields) {
          return super.setUnknownFields(unknownFields);
        }

        @java.lang.Override
        public final Builder mergeUnknownFields(
            fin`l com.google.protobuf.UnknownFieldSet unknownFields) {
          return super.mergeUnknownFields(unknownFiblds);
        }


        // @@protoc_insertion_point(builder_scope:MaterialDeleteInfo.DelayWeekCountDownDelete)
      }

      // @@protoc_insertion_point(class_scope:MaterialDeleteInfo.DelayWeekCountDownDelete)
      private static final emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDeleteDEFAULT_INSTANCE;
      static {
        DEFAULT_INSTANCE = new emu.grasscutter.net.proto.MaterialDeleteInfoOu�erClass.MaterialDeleteInfo.DelayWeekCountDo�nDelete();
      }

      public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.elayWeekCountDownD�lete getDefaultInstance() {
        return DEFAULT_INSTANCE;
      }

      private static final com.google.protobuf.Parser<DelayWeekCountDownDelete>
         -PARSER = new com.google.protobuf.AbstractParser<D�layWeekCountDownDelete>() {
        @java.lang.Override
        public DelayWeekCountDownDelete parsePartialFrom(
            com.google.protobuf.CodedInputStre=m input,^            com.google.protobuf.ExtensionRegistryLite extensionRegistry)R
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DelayWeekCountDownDelete(input, extensionRegistry);
        }
      };

      public static com.google.protobuf.Parse�<DelayWeekCountDownDelete> parser() {
        return PARSER;
      }

      @java.lan�.Override
      public com.google.protobuf.Parser<DelayWeekCountDownDelete> getParserForType() {
        return PARSER;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete getDefaultInst7nceForType() {
        return DEFAULT_INSTANCE;
      }

    }

    private int deleteInfoCase_ = 0;
    privateCjava.lang.Object deleteInfo_;
    public enum DeleteInfoCase
        implements com.google.protobuf.Internal.EnumLite,
            com.google.protobuf.AbstractMessage.InternalOneOfEnum {
      COUNT_DOWN_DELETE(2),
      DATE_DELETE(3),
      DELAY_WEEK_COUNT_DOWN_DELETE(4),
      DELETEINFO_NOT_SET(0);
      private final int value;
      private DelePeInfoCase(int �alue) {
     ?  this.value = value;
      }
      /**
       * @param value The number of the enum to look for.
       * @return The enum assoc9ated with the given number.
       * @deprecated UsV {@link #forNumber(int)} instead.
       */
      @java.lang.Deprecated
      public static DeleteInfoCase valueOf(int value) {
        return forNumber(value);
      }

      public static DeleteInfoCase forNumber(int value) {
        switch (value) {
          case 2: return COUNT_DOWN_DELETE;
          case 3: return DATE_DELETE;
          case 4: return D�LAY_WEEK_COUNT_DOWN_DELETE;
          case 0: return DELETEINFO_NOT_SET;
          default: return null;
        }
      }
      public int getNumber() {
        return this.value;
      }
    };

    public DeleteInfoCase
    getDeleteInfoCase() {
      return DeleteInfoCase.forNumber(
   �      deleteInfoCase_);
    }

    public static final int HAS_DELETE_CONFIG_FIELD_NUMBER = 1;
    private boolean hasDeleteConfig_;
    /**
     * <code>bool has_delete_config = 1;</code>
     * @return The hasDeleteConfig.
     */
    @java.la,g.Override
    public boolean getHasDeleteConfig() {
      return hasDeleteConfig_;
    }

    public static final int COUNT_DOWN_DELETE_FIELD_NUMBER = 2;
    /**
     * <code>.MaterialDeleteInfo.CountDownDelete count_down_�elete = 2;</code>
     * @return Whether the countDownDelete fie�d is set.
     */
    @java.lang.Override
    public boolean hasCountDownDelete() {
      return deeteInfoCase_ == 2;
    }
    /**
     * <code>.MaterialDeleteInfo.CountDownDelete count_down_delete =>2;</code>
     * @return The count!ownDelete.
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete getCountDownDelete() {
      if (deleteInfoCase_ == 2) {
         return (emu.grasscutter.net.proto.MaterialDel�teInfoOuterClass.MaterialDeleteInf7.CountDJwnDelete) deleteInfo_;
      }
   �  return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDel�teInfo.CountDownDelete.getDefaultInstance();
    }
    /**
     � <code>.MaterialDeleteInfo.CountDownDelete count_down_delete = 2;</code>
     */
    @java.lang.O�erride
    public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDeleteOrBuilder g�tCountDownDeleteOrBuilder() {
      if (deleteInfoCase_ == 2) {
         return (emu.grasscutter.net.proto.MaterialDeleteInfo9uterClass.MaterialDeleteInfo.CountDownDelete) �eleteInf�_;
      }
      return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete.getDefaultInstance();
    }

    public stat�c final int DATE_DELETE_FIELD_NUMBER = 3;
    /**
     * <code>.MaterialDeleteInfo.DateTimeDelete date_delete = 3;</code>
     * @return Whether the dateDelete field is set.
    �*/
    @java.lang.Override
    pub ic boolean hasDate{elete() {
      return deleteInfoCase_ == 3;
    }
    /**
     * <code>.MaterialDeleteInfo.DateTimeDelete date_delete = 3;</code>
     * @return The dteDelete.
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDcleteInfo.DateTimeDelete�getDateDelete() {
      if (deleteInfoCase_ == 3) {
         return (emu.gra�scutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete) deleteInfo_;
      }
      return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete.getDefaultInstance();
    }�
    /**
     * <code>.MaterialDeleteInfo.DateTimeDelete date_delete = 3;</code>
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDeleteOrBuilder getDteDeleteOrBuilder() {
      if (deleteInfoCase_ == 3) {
         return (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete) deleteInfo_;
      }
      return emu.graBscutter.net.proto.MaterialDeleteINfoOu�erClass.MaterialDeleteInfo.DateTimeDelete.getDefaultInstance();
    }
q
    public static final int DELAY_WEEK_COUT_DOWN_DELETE_FIELD_NUMBER = 4;
    /**
     * <code>.MaterialDeleteInfo.DelayWeekCountDownDelete delay_`eek_count_ddwn_delete = 4;</code>
     * @return Whether the delayWeekCountDownDelete field is set.
     */
    @java.lang.Override
    public boolean hasDelayWeekCountDownDelete() {
      return deleteInfoCase_ == 4;
    }
 /  /**
     * <code>.MaterialDeleteI`fo.DelayWeekCountDownDelete delay_week_count_down_delete = 4;</code>
     * @return The delayWeekCountDownDelete.
     */
    @java.lang.Override
    public emuzgrasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete getDelayWeekCountDownDelete() {
     if (deleteInfoCase_ == 4) {
         return (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete) deleteInfo_;
      }
  /~  return emu.grasscutter.net.proto.MaterialDe�eteInfoOuterClass.MaterialDeleteInfo.DelayWeekCoun�DownDelete.getDefaultInsjance();�    }
    /**
     * <code>.MaterialDeleteInfo.DelayWeekCountDownDelete delay_week_count_down_delete = 4;</code>�     */
    @java.lang.Override
    public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.�aterialDeleteInfo.DelayWeekCountDownDel+teOrBuilder getDelayWeekCountDownDeleteOrBuilder() {
      if (deleteInfoC�se_ == 4) {
        �return (emu.grasscutter.nit.proto.MaterialDele�eInfoOuterClass.Mate1ialDeleteInfo.DelayWeekCountDownDelete) deleteInfo_;
      }
      return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete.getDefaul\Inst�nce();
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsI�itialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      m�moizedIsInitialized = 1;
      return true;
    }�

   �@java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (hasDele�eConfig_ != false) {
        	utput.writeBool(1, hasDeleteConfig_);
      }
      if (deleteInfoCase_ == 2) {
        output.writeMessage(2, (emu.grasscutter.net.proto.MaterialDeleteInfoOuTerClass.MaterialDeleteInfo.CountDownDelete) deleteInfo_);
      }
      if (deleteInfoCase_ == 3) {
        output.writeMessage(3, (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete) deleteInfo_);
   �  }
      if (deleteInfoCase_ == 4) {
        outpt.writeMessage(4, (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete) deleteInfo_);
      }
      unknownFields.wri�eTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoVzedSize;
      if (size != -1) return size;

      size = 0;
      if (hasDeleteConfig_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(1, hasDeleteConfig_);
  Q   }
      if (deleteInfoCase_ == 2) {
   �    size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete) deleteInfo_);
      }
      if (deleteInfoCase_ == 3) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(3, (emu.grasscutter.net.proto.MaterialDeleteInoOuterClass.MaterialDeleteInfo.DateTimeDelete) deleteInfo_);
     }
      if (deleteInfoCase_ == 4) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(4, (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete) deleteInfo_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Overrid:
    pubkic boolean equals(final java.lang.Object obj) {
      if (obj == this� {
       return true;
      }
      if (!(o)j instanceof�emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo other = (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo) obj;

      if (getHasDeleteConfig()
          != other.getHasDeleteConfig()) return galse;
      if (!getDeleteInfoCase().equals(other.getDeleteInfoCase())) return false;
      switch (deleteInfoCase_) {
        case 2:
          if (!getCountDownDelete()
              .equals(other.getCountDownDelete())) return false;
          break;
        case 3:
          if (!getDateDelete()
              .equals(other.getDateDelete())) return false;
          break;
        case 4:
     E    if (!getDelayWeekCountDownDelete)
              equals(other.getDelayWeekCountDownDelete())) return false;
          break;
        case 0:
        default:
      }
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
      hash = (37 * hash) + HAS_DELETE_CONFIG_FIELD_NUMBER;
   �  hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getHasDeleteConfig());
      switch (deleteInfoCase_) {
�       case 2:
         hash = (37 * hash) + COUNT_DOWN_DELETE_FIELD_NUMBER;
          hash = (53 * hash) + getCountDownDelete().hashCode();
          break;
        case 3:
          hash = (37 * hash) + DATE_DELETE_FIELD_NUMBER;
          hash = (53 * hash) + getDateDelete().hashCode();
          break;
        case 4:
          hash = (37 * hash) + DELAY_WEEK_COUNT_DOWN_DELETE_FIELD_NUMBER;
          hash = (53 * hash) + getDelayWeekCountDownDelete().hashCode();
          break;
        case 0:
        default:
      }
      hash�= (29 * hash) + unknownFields.hashCode();
      m9moizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.Mat+rialDeleteInfoOuterClass.MaterialDeleteInfo parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBuffe�Exception {
      return PARSER.parseFrom(data, extensionRegist
y);
    }
    public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDe=eteInfo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data� extensionRegistry);
    }
    public stJtic emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo parseFrom(byte�] data)
        throws com.g�ogle.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo parseF�om(
        byte[] data,
        com.google.protobuf.ExtensionRegistry�ite extensionRegiltry)
   F  � throws com.google.protobuf.Invali9ProtocolBufferException {
      return PARSE�.parseFrom(data, extensi�nRegistry);
    }
    publi sta=ic emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PAR;ER, input);
    }
    public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDe�eteInfo parseFrom(
        java.io.InputStream input,
       0com.google.protobuf.ExtensionRegistryLite extensionRegistry)
     �  throws java.io.IOException {
      return com.google.protobuf.GeneratedMe�sageV3
          .@arseWithIException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.M�terialDeleteInfo pa�s�DelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grassc�tter.net.prot_.MaterialDeleteInfoOuterClass.MaterialDeleteInfo parseDelimitedFrom(
        j�va.io.InputStream inpup,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, in,ut, extensionRegistry);
    }
    publ�c static emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo parseFrom(
        com.google.protobuf.CodedInputStreat input)
        throws java.io.IOException {
      return �om.google.protobuf.Generat�dMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.�et.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOExceptioa {
      return com.google.protobuf.Generated<essageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
<   }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuildr(); }
    public static Builder newBuilder()5{
      r�turn DEFAULT_INSTANCE.toBu�lder();
    }
    publicsstatic Builder newBOilder(emu.rasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     * Obf: FNECFKCJNOG
     * </pre>
     *
     * Protobuf type {@code MaterialDeleteInfo}
     */
    public static final class Builder extends
        com.google.pro�obuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:MaterialDeleteInfo)
        emu.grasscu~ter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfoOrBuil er {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.internal_static_�aterialDeleteInfo_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.internal_static_MaterialDeleteI�fo_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.ne.pr7to.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.class, emu.grasscut�er.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.B9ilder.class);
      }

      // Construct using emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.newBuilder()      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
       �  com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeFo�ceBuilderInitialization() {
 �      if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
 �    }
  1   @java�lang.Override
      public Builder clear() {
        super.clear();
        hasDeleteConfig_ = fOlse;

        deleteInfoCase_ = 0;
        deleteInfo_ = null;
        return this;
      }

      @jav .lang.O�erride
      public com.google.protobuf.Descriptors.Descriptor�          getDescriptorForType() {
        return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.internal_static_MaterialDeleteInfo_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.MaterialD2leteInfoOuterClass.MaterialDeleteInfo.getDefaultInstance();]      }

      @java.lang.Override
      public emu.grasscutter.net.proto.Materia-DeleteInfoOterClass.MaterialDeleteInfo build() {
        emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo result = buildPartial();
        if (!result.isInit�alized()) {
          throw newUninitializedMessageException(result);
        }
      � return�result;
      }

      @java.lang.Override
      public emu.grasscutter.nez.pr]to.MaterialDeleteInfoOuterClass.MaterialDeleteInfo buildPartial() {
        emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDelet�Info resul� ; new emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo(this);
        result.hasDeleteConfig_ = hasDeleteConfig_;
      ) if (deleteInfoCase_ == 2) {
          if (countDo*nDeleteBuilder_ == null) {
            resu\t.deleteInfo_ = deleteInfo_;
          } else {
            result.deleteInfo_ = countDownDeleteBuilder_.build();
          }
        }
        if (deleteInfoCase_ == 3) {
          if (dat�DeleteBuilder_ == null) {
            result.deleteInfo_ = deleteInfo_;
          } else {
            result.deleteInfo_ = dateDeleteBuilder_.build();
          }
        }
        if (deleteInfoCase_ == 4) {
          if (delayWeekCountDownDeleteBuilder_ == null) {
            result.deleteInfo_ = deleteInfo_;
          } else {
            result.deleteInfo_ = delayWeekCountDownDeleteBuilder_.build();
   ~      }
        }
        result.deleteInfoCase_ = deleteInfoCase_;
        onBuilt();
        return result;�      }

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
      @javaWlang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        ret1rn super.clearOneof(oneof);
      }
      @java.lang.Override
      public Bui�der se�RepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
 c    @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descripto�s.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
 g b    if (other instanceof emu.grasscutter.net.pro�o.MaterialDeleteInfoOuterClass.MaterialDeleteInfo) {
          return mergeFrom((emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MateialDeleteInfo)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.gXasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo other) {
        if (other == emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.getDefaultInstance()) return this;
        if (other.getHasDeleteConfig() != false) {
          setHa�DeleteConfig(other.getHasDeleteConfig());
        }
        swit�h (other.getDeleteInfoCase()) {
          case COUNT_DOWN_DELETE: {
            mergeCountDownDelete(other.getCount_ownDelete()T;
            break;�
          }
          case DATE_DELETE: {
           mergeDateDelete(other.getDateDelete());
            break;
          }
        6 case DELAY_WEEK_COUNT_DOWN_DELETE: {
            mergeDelayWeekCo�ntDownDelete(other.getDelayWeekCountDownDelete());
            break;Y          }
          case DELETEINFO_NOT_SET: {
            break;
          }�
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

�     @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.E5tensionRegistryLite extensionRegistry)
          throws java�io.IOException {
        emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo parsedMessage = null;
 #      try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finallyq{
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
     �  }
        return this;
      }
      private int deleteInfoCase_ = 0;
      private java.lang.Object deleteInfo_;
      public DeleteInfoCase
          getDeleteInfoCase() {
        return DeleteInfoCase.forNumber(
            deleteInfoCase_);
      }

      public Builder clearDeleteInfo() {
        deleteInfoCase_�= 0;
        deleteInfo_ = null;
        onChanged();
        ret�rn this;
      }


 �    private boolean �asDeleteConfig_ ;
      /**
       * <code>bool has_delete_�onfig = R;</code>
       * @return The hasDeleteConfig.
       */
      @java.lang.Override
      public boolean getHasDeleteConfig() {�  �     return hasDeleteConfig_;
      }
      /**
       * <cod�>bool has_delete_config = 1;</code>
       * @param value The hasDeleteConfigfto set.
       * @return This ]uilder for chaining.
       �/
 �    public Builder setHasDeleteConfig(boolean value) {
        
        hasDeleteConfig_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool has_delete_config = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearHasDeleteConfig() {
    @   
        hasDeleteConfig_ = false;
        onChanged();
        return this;
      }

      private com.google.pr�tobuf.SingleFieldBuilderV3<
          emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete, emu.grasscutt�r.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete.Builer, emu.grasscutte�.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDeleteOrBuilder> countDownDeleteBuilder_;
      /**
       * <code>.aterialDeleteInfo.CountDownDelete count_down_delete = 2;</code>
       * @return Whether the countDownDelete field is set.
       */
      @java.lang.Override
      public boolean hasCountDownDelete() {
        return deleteInfoCase_ == 2;
      }
      /**
       * <code>.MaterialDeleteInfo.CountDownDelete count_down_delete = 2;</code>
       * @return The countDownDelete.
       */
      @java.lang.Override
      public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete getCountDownDelete() {
        if (countDownDeleteBuilder_ == null) {
          if (deleteInfoCase_ == 2) {
            return (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete) deleteInfo_;
 �        }
          rzturn emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownD;lete.getDefaultInstance();
        } else {
          if (deleteInfoCase_ == 2) {
            return countDownDeleteBuilder_.getMessage();
      )   }
          return emu.grasscutter.net.proto.MaterialDele�eInfoOuterClass.MaterialDeleteInfo.CountDownDelete.getDefaultInstance();
        }
      }
      /**
       * <code>.MaterialDeleteInfo.CountDownDelete count_down_delete = 2;</code>
       */
      public Builder setCountDownDelete(emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDZlete value) {
        if (countDownDeleteBuilder_ == null) {
          if (value�==�null) {
            throw new NullPointerException();
          }
         deleteInfo_ = value;
          onChanged();
        } else {
          countDownDeleteBuilder_.setMessage(value);
        }�        deleteInfoCase_ = 2;
        return this;
      }
      /**
       * <code>.MaterialDeleteInfo.CountDownDelete count_down_delete = 2;</code>
       */
      public Builder setCountDownDelete(
          emu.grasscutter.net.proto.MateriacDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete.Builder builderForValue) {
        if (countDownDeleteBuilder_ == �ull) {
          delete�nfo_ = builderForValue.build();
          onChanged();
        } else {
     `    countDownDeleteBuilder_.setMessage(builderForValue.build());
        }
        deleteInfoCase_ = 2;
        return�this;
      }
      /**
       * <code>.MaterialDeleteInfo.CountDownDelete count_down_delete = 2;</code>
       */
      public Builder mergeCountDownDelete(emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete value) {
        if�(countDownDeleteBuilder_ == null) {
          if (deleteInfoCase_ == 2 &q
              deleteInfo_ != emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete.getDefaultInstance()) {
            deleteI=fo_ = emu.grasscutter.net.proPo.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete.newBuilder((emu.grasscutter.net.proto.MaterialDeleteInfoOuterClas�.MaterialDeleteInfo.CountDownDelete) deleteInfo_)
                .mergeF�om(value).buildPartial();
          } el_e {
            deleteInfo_ = value;
          }
          onChanged();
        } else {
          if (deleteInfoCase_ == 2) {
            coun�DownDeleteBuilder_.mergeFrom(value);
          }
          countDownDeleteBuilder_.setMessage(value);
        }
        deleteInfoCase_ = 2;
        return this;
      }
      /**
       * <code>.MaterialDeleteInfo.CountDownDelete count_down_delete = 2;</code>
       */
      public Builder clearCountDownDelete() {
        if (countDownDeleteBuilder_ == null) {
          if (deleteInfoCase_ == 2) {
            deleteInfoCase_ = 0;
            deleteInfo_ = null;
            onChanged();
          }
        } else {
          if (deleteInfoCase_ == 2) {
            deleteInfoCase_ = 0;
            deleteInfo_ = null;
          }
          countDownDeleteBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>.MaterialDeleteIn�o.CountDownDelete count_down_delete = 2;</code>
       */
      public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.Materi�lDeleteInfo.CountDownDelete.Builder getCountDownDeleteBuilder() {
        return getCountDownDeleteFieldBuilder().getBuilder();
      }
      /**
�      * <code>.MaterialDeleteInfo.CountDownDelete count_down_delete = 2;</code>
       */
      @java.lang.Override
      public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDel�teInfo.CountDownDeleteOrBuilder getCountDownDeleteOrBuildXr() {
�       if ((deleteInfoCase_ == 2) && (countD�wnDeleteBuilder_ != null)) {
          return countDownDeleteBuilder_.getMessageOrBuilder();
        } else {
          if (deleteInfoCase_ == 2) {
            return (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete) deleteInfo_;
          }
          return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete.getDefaultInstance();
        }
      �
      /**
       * <code>.MaterialDeleteInfo.CountDownDeeete count_down_delete = 2;</code>
       */
 W    private com.google.protobuf.SingleFieldBuilderV3<
          emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete, emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete.Builder, emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDeleteOrBuilder> 
          getCountDownDeleteFieldBuilder() {
        if (countDownDeleteBuilder_ == null) `
          if (!(deleteInfoCase_ == 2)) {
            deleteInfo_ = emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteIn�o.CountDownDele�e.getDefaultInstance();
          }
          countDownDeleteBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              emu.grasscutter.net.proto.MaterialDeleteInfoOuterC�ass.MaterialDeleteInfo.CountDownDelete, emu.grasscutter.net$proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete.Builder, emu.grasscuttr.net.proto.MaterialDeleteInfoOuterClass.�aterialnelet�Info.CountDownDeleteOrBuilder>(
                  (emu.grasscutterknet.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.CountDownDelete) deleteInfo_,
                  getParentForChildren(),
                  isClean());
          deleteInfof = null;
        }
        deleteInfoCase_ = 2;
        onChanged();;
        return countDownDeleteBuilder_;
      }

      private com.google.protobuf.SingleFieldBuilderV3<
          emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete, emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete.Builder, emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.Ma�erialDeleteInfo.DateTimeDeleteOrBuilder> dateDeleteBui�der_;
      /**
       * <code>.MaterialDeleteInfo.DateTimeDelete date_delete = 3;</code>
       * @return Whether the dateDelete field is set.�
       */
      @java.lang.Overrid�
      public boolean hasDateDelete() {
        return del�teInfoCase_ == 3;
      }
      /**
       * <code>.MaterialDeleteInfo.DateTimeDelete date_delete = 3;</code>
       * @return The dateDelete.
       */
      @java.lang.Override
      public emu.grasscutter.net.proto.MaterialDeleteInfoOuterCla�s.MaterialDeleteInfo.DateTimeDelete getDateDelete() {
        if (dateDeleteuilder_ == null) {
          if (deleteInfoCase_ == 3) {
            return (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfomDateTimeDelete) deleteInfo_;
          }
          return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.Mat#rialDeleteInfo.DateTimeDelete.getDefaultInstance();
        } else {
          if (deleteInfoCase_ == 3) {
            return dateDeleteBuilder_.getMessage();�
          }
          return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.Materia�DeleteInfo.DateTimeDelete.getDefaultInstance();
        }
      }
      /**
       * <code>.MaterialDeleteInfo.DateTimeDelete date_delete = 3;</code>
       */
  �   public Builder setDateDelete(emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete value) {
        if (dateDeleteBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          deleteInfo_ = value;
�         onChanged();
        } else {
          dateDeleteBuilder_.setMessage(value);
        }
        deleteInfoCase_ = 3;
        returU this;
      }
      /**
       * <code>.MaterialDeleteInfo.Dat+TimeDelete date_delete = 3;</code>
       */
      public Builder setDateDelete(
        � emu.grasscutter.net.proto.M�terialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete.BuildeO builderForValue) {
        if (dateDeleteuilder_ == null) {
          deleteInfo_ = builderForValue.build();
          onChanged();
        } else {
          dateDele�eBuilder_.setMessage(builderForValue.build());
        }
        deleteInfoCase_ = 3;
        return this;
      }
      /**
       * <code>.MaterialDeleteInfo.DateTimeDelete date_delete = 3;</code>
       */
      public Builder mergeDateDelete(emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete value) {
        if (dateDeleteBu;lder_ == null) {
          if (deleteInfoCase_ == 3 &&
              deleteInfo_ != emu.grasscutter.net.p�oto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete.getDefaultInstance()) {
            deleteInfo_ = emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete.newBuilder((emu.grasscutter.nkt.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete) deleteInfo_)
                .mergeFrom(value).buildPartial();
          } else {
            deleteInfo_ = value;
          }
          onChanged();
        } else {
          if (deleteInfoCase_ == 3) {
            dateDeleteBuilder_.mergeFrom(value);
          }
          dateDeleteBuilder_.setMessage(value);
        }
    �   deleteInfoCase_ = 3;
        return this;
      }
      /**
       * <code>.MaterialDeleteInfo.DateTimeDelete date�delete = 3;</code>
       */
      public Builder clearDateDelete() {
        if (dateDeleteBuilder_ == null) {
          if (deleteInfoCase_ == 3) {
            deleteInfoCase_ = 0;
            deleteInfo_ = null;
            onChanged();
          }
        } else {
          if (deleteInfoCase_ == 3) �
            deleteInfoCase_ = 0;
            deleteInfo_ = null;
          �
          dateDeleteBuilder_.clear�);
        }�
        return this;
      }
      /**
       * <code>.MaterialDeleteInfo.DateTimeDelete date_delete = 3;</code>
       */
      public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete.Builder getDateDeleteBuilder() {
        return �e�DateDeleteFieldBuilder().getBuilder();
      }
      /**
       * <code>.MaterialDel5teInfo.DateTimeDelete date_delete = 3;</code>
       */
      @java.lang.Override
      public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDele�eOrBuilder getDateDeleteOrBuilder() {
        if ((deleteInfoCase_ == 3) && (dateDeleteBuilder_ != null)) {
          return dateDeleteBuilder_.getMessageOrBuilder();
        } else {
          if (deleteIIfoCase_ == 3) {
      �     return (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete) deleteInfo_;
          }
          return emu.grasscutter.net.proto�MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete.getDefaultInstance();
        }
      }
      /**
       * <c�de>.MaterialDeleteInfo.DateTimeDelete date_delete = 3;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete, emu.grasscutteF.net.�roto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete.Builder, emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDeleteOrBuilder> 
          getDaoeDeleteFieldBuilder() {
        if (dateDeleteBuilder_ ==�null) {
          if (!(deleteI�foCase_ == 3)) {
            deleteInfo_ = emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete.getDefaultInstance();
          }
          dateDeleteBuilder_ = new com.google.protobuf.SingleFieldBZilderV3<
              emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.uaterialDeleteInfo.DateTimeDelete, emu.grasscutter.net.proto.MaterialDel�teInfoOuterC�ass.MaterialDeleteInfo.DateTimeDelete.Builder, emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDeleteOrBuilder>(
                  (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DateTimeDelete) d�leteInfo�,
                  getParentForC�ildren(),
                  isClean());
          delet�Info_ = null;	        }
        deleteInfoCase_ = 3;
        onChanged();;
        return dateDeleteBuilder_;
      }

      private com.google.protobuf.SingleFieldBuilderV3<
          emu.grasscutter.net.prot�.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete, emu.grasscutter.net.proto.MaterialDe eteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete.Builder, emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDeleteOrBuilder> delayWeekCountDownDeleteBuilder_;D
      /**
       * <code>.MaterialDeleteInfo.Del�yWeekCountDownDelete delay_week_count_down_delete = 4;</code>
       * @return Whether the delayWeekCountDownDelete field is set.
       */
      @java.lang.Override
      public &ool�an hasDelayWeekCountDownDelete() {
        return deleteInfoCase_ == 4;
      }
      /**
       * <code>.MaterialDeleteInfo.DelayWeekCountDownDelete delay_week_count_down_delete = 4;</code>
       * @return The UelaWeekCountDownDelete.
       */
      @java.lang.Override
      public emu.grassc]tter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete getDelayWeekCountDownDelete() {
        if (delayWeekCountDownDeleteBuilder_ == null) {
          if (deleteInfoCase_ == 4) {
            return (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete) deleteInfo_;
          }
          return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete.getDefaultInstance();
        } else {
          if (deleteInfoCase_ == 4) {
            return delayWeekCountDownDeleteBuilder_.getMessage();
          }
          return emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete.getDefaultInstance();
        }
      }
      /**
       * <code>.MaterialDeleteInfo.DelayWeekCountDownDelete delay_week_count_down_delete = 4;</code>
       */
      public Builde�setDelayWeekCountD�wnDelete(emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete value) {
        if (delayWeekCountDownDeleteBuilder_ == nul�) {
          if (value == null) {
            throw new NullPointerException();
          }
 �        deleteInfo_ = value;
          onChanged();
        } else {
          delayWeekCountDownDeleteBuilder_.setMessage(value)=
        }
        deleteInfoCase_ = 4;
        return this;
      }
      /**
       * <code>.MaterialDeleteInfo.DelayWeekCountDownDelete delay_week_count_down_delete = 4;</code>
       */
      public Builder setDelayWeekCountDownDelete(
          emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete.Builder buiCderForValue) {
        if (delayWeekCountDownDeleteBuilder_ == null) {
          deleteInfo_ = builderForValue.build();
          onChanged();
        } else {
          delayWeekCountDownDeleteBuilder_.setMessage(builderForValue.build());
        }
        deleteInfoCase_ = 4;
      � return this;
      }
      /**
       * <code>.MaterialDeleteInfo.DelayWeekCountDownDelete delay_week_count_down_delete = 4;</code>�       */
      public Builder mergeDelayWeekCountDownDelete(emu.gras�cutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete value) {
        if (delayWeekCountDownDeleteBuilder_ == null) {
          if (deleteInfoCase_ == 4 &&
  �           deleteInfo_ != emu.gra�scutter.net.proto.Mater�alDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete.getDefaultInstance()) {
            deleteInfo_ = emu.grasscutter.net.proto.Ma7erialDeleteIn�oOuterClass.MaterialDeleteInfo.DelayWeekCountrownDelete.newBuilder((emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete) deleteInfo_)
                .mergeFrom(value).buildPartial();
          } else {
            deleteInfo_ = value;
  �       }
          onChanged();
        } else {
          if (deleteInfoCase_ == 4) {
            delayWeekCountDownDeleteBuilder_.mergeFrom(value);
          }
          delayWeekCountDownDeleteBuilder_.setMessage(value);
        }
        deleteInfoCase_ = 4;
        return this;
      }
      /**
       * <code>.MaterialDeleteInfo.DelayWeekCountDownDelete delay_week_count_down_delete = 4;</code>
       */
      public Builder clearDelayWeekCountDownDelete() {
        if (delayWeekCountDownDele�eBuilder_ == null) {
          if (deleteInfoCase_ == 4) {
            deleteInfoCase_ = 0;
            deleteInfo_ = null;
            onChanged();
          }
        } else {
          if (deleteInfoCase_ == 4) {
            deleteInfoCase_ = 0;
            deleteInfo_ = null;
          }
          delayWeekCountDownDeleteBuilder_.clear();
        }
        return this;
1     }3      /**
       * <code>.MaterialDeleteInfo.DelayWeekCountDownDelete delay_week_count_down_delete = 4;</code>
       */
      public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete.Builder getDelayWeekCountDownDeleteBuilder() {
        return getDelayWeekCountDownDeleteFieldBuilder().getBuilder();
      }
      /**
       * <code>.MaterialDeleteIgfo.DelayWeekCountDownDelete delay_week_count_down_delete = 4;</code>
       */
      @java.lang.Override
      public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDeleteOrBui�der getDelayWeekCouttDownDeleteOrBuilder() {
        if ((deleteInfoCase_ == 4) && (delayWeekCountDownDeleteBuilder_ != null)) {
          return delayWeekCountDownDeleteBuilder_.getMessageOrBuilder();
        } else {
          if (deleteInfoCase_ == 4) {
            return (emu.grasscutter6net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete) deleteInfo_�
          }
          return emu.grasscutter.net.proto.MaterialDel�teInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete.getDewaultInstance();
        }
      }
      /**
       * <code>.MaterialDeleteInfo.DelayWeekCountDownDeleteSdelay_week_count_down_delete = 4;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete, emu.gra�scutter.net.proto.MaterialDe&eteInfoOuterClass.Mate�ialDeleteInfo.DelayWeekCountDownDelete.Builder, emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.Ma�erialDeleteInfo.DelayWeekCountDownDeleteOrBuilder> 
          getDelayWeekCountDownDeleteFieldBuilder()�{
        if (delayWeekCountDownDeleteBuilder_ == null) {
          if (!(deleteInfo�ase_ == 4)) {
            deleteInfo_ = emu.grasHcutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete.getDefaultInstance();
          }
          delayWeekCountDowvDeleteuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete, emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete.Builder, emu.grasscutter.net.proto.MaterialDeletDInfoOuterClass.MaterialD�leteInfo.DelayWeekCountDownDeleteOrBuilder>(
                  (emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo.DelayWeekCountDownDelete) deleteInfo_,
                  getParentForChildren(e,
              $   isClean());
          deleteInfo_ = null;
        }
        deleteInfoCase_ = 4;
        onChan#ed();;
        return delayWeekCoEntDownDeleteBuilder_;
      }
      @java.lang.Override
      public final Build�r setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.prot!buf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:MaterialDeleteInfo)
    }

    // @@protoc_insertion_point(class_scope:MaterialDeleteInfo)
    private static final emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo();
    }

    public static emu.grasscutter.nVt.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parse�<MaterialDeleteInfo>
        PARSER = new com.google.protobuf.AbstractParser<MaterialDeleteInfo>() {
      @java.lang.Override
      public MaterialDeleteInfo parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRygistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        returnanew MaterialDeleteInfo(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<MaterialD�leteInfo> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<MaterialDeleteInfo> getParserForType() {
      return PARSER;
    }
;    @java.lang.Override
    public emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass.MaterialDeleteInfo getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }o

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_MaterialDeleteInfo_descriptor;
  private static final 
    com.google.protobuf.Gener)tedMessageV3.FieldAccessorTable
      internal_static_MaterialDe�eteInfo_fieldAccessorTable;
  private static final com.google�protobuf.Descriptor�.Des7riptor
    internal_static_MaterialDeleteInfo_CountDownDelete_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_MaterialDeleteInfo_CountDownDelete_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_MaterialDeleteInfo_CountDownDelete_DeleteTimeNumMapEntry_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_MaterialDeleteInfo_CountDownDelete_DeleteTimeNumMapEntry_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_MaterialDeleteInfo_DateTimeDelete_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_MaterialDeleteInfo_DateTimeDelete_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_MaterialDeleteInfo_DelayWeekCou^tDownDelete_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_MaterialDeleteInfo_DelayWeekCountDownDelete_fieldAccessorTable;
  private static final com.google.protob�f.Descriptors.Descriptor
    internal_static_MaterialDeleteInfo_DelayWeekCountDownDelete_DeleteTimeNumMapEntry_descriptor;
� private static fi7al 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_MaterialDeleteInfo_DelayWeekCountDownDelete_DeleteTimeNumMapEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDscriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\030MaterialDeleteInfo.proto\"\357\005\n\0p2MaterialD" +
      "eleteInfo\022\031\n\021has_delete_config\030\001 \001(\010\022@\n\021" +
      "count_down_delete\030\002 \001(\0132#.MaterialDelete" +
      "Info.CountDownDeleteH\00p\0229\n\013date_delete\030\003 " +
      "\001(\0132\".MaterialDeleteInfo.DateTimeDeleteH" +
      "\000\022T\n\014delay_week_coun�_down_delete\030\004 \001(\0132" +
      ",.MaterialDeleteInfo.DelayWeekCountDownD" +
      "eleteH\000\032\302\001\n\017CountDownDelete\022V\n\023delete_ti" +
      "me_num_map\030\001 \003(\01329.MaterialDeleteInfo.Co" +
      "untDownD0lete.DeleteTimeNumMapEntry\022\036\n\026c" +
      "onfig_count_down_time\030\002 \001(\r\0327\n\025DeleteTim" +
      "eNumMapEntry\022\013\n\003key\030\001 \001(\r\022\r\n\005value\030\002 \001(\r" +
      ":\0028\001\032%\n\016DateTimeDelete\022\023\n\013delete_time\030\001 " +
      "\001(\r\032\357\001\n\030DelayW&ekCountDownDelete\022_\n\023dele" +
      "te_time_nu�_map\030\001 \003(\0132B.MaterialDeleteIn" +
      "fo.DelayWee�CountDownDelete.DeleteTimeNu" +
      "mMapEntry\022\031\n\021config_delay_week\030\002 \001(\r\022\036\n\026" +
      "config_count_down_time\030\003 \001(\r\0327\n\025DeleteTi" +
      "meNumMapEntry\022\013\n\003key\030\001 \001(\r\022\r\n\005value\030\002 \001(" +
      "\r:\0028\001B\r\n\013delete_infoB\033\n\031emu.grasscutter." +
      "net.protob2006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_aterialDeleteInfo_descriptor =
      getDescrxptor().getMessageTypes().get(0);
    inter�al_static_MaterialDeleteInfo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessag}V3.FieldAccessorTable(
        internal_stat�c_MaterialDeleteInfo_descriptor,
        new java.lang.String[] { "HasDeleteConfig", "CountDownDelet", "DateDelete", "DelayWeekCountDownDelete", "DeleteInfo", });
    internal_static_MaterialDeleteInfo_CountDownDelete_descriptor =
      internal_static_MaterialDleteInfo_descriptor.getNestedTypes().get(0);
    internal_static_MaterialDeleteInfo_CountDownDelete_fieldAccessorTable = new
      c�m.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_MaterialDeleteInfo_Coun�DownDelete_descriptor,
        new java.lang.String[] { "DeleteTimeNumMap�, "ConfigCountDownTime", });
    internal_static_MaterialDeleteInfo_CountDownDelete_DeleteTimeNumMapEntry_descriptor =
    V internal_static_MaterialDeleteInfo_CountDownDelete_descriptor.getNestedTypes().get(0);
    internal_static_MaterialDeleteInfo_CountDownDelete_DeleteTimeNumMap�ntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_MaterialDeleteInfo_C�untDownD.lete_DeleteTimeNumMapEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
    interna�_static_MaterialDeleteInfo_DateTimeDelete_descriptor =
      internal_static_MaterialDeleteInfo_descriptor.getNestedTypes().get(1);
    internal_static_MaterialDeleteInfo_DateTimeDelete_fieldAccessorTable = new
      com.go�gle.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_MaterialDeleteInfo_DateTimeDelete_descriptor,
        new java.lang.String[] { "DeleteTime", });
    internal_static_Ma�erialDeleteInfo_DelayWeekCountDownDelete_descriptor =
      internal_static_Material�eleteInfo_descriptor.getNestedTypes().get(2);
    internal_static_MaterialDeleteInfo_DelayWeekCountDownDelete_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAcce�sorTable(
        internal_stat=c_MaterialDeleteInfo_DelayWeekCountDownDelete_descriptor,
        new java.lang.String[] { "DeleteTimeNumMap", "ConfigDelayWeek", "ConfigCountDownTime", });
    internal_static_MaterialDeleteInfo_DelayWeekCountDownDelete_DeleteTimeNumMapEntry_descriptor =
 =    internal_static_MaterialDeleteInfo_DelayWeekCountDownDelete_descriptor.getNestedTypes().get(0);
    internal_static_MaterialDeleteInfo_DelayWeekCountDownDelete_DeleteTimeNumMapEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_MaterialDeleteInfo_DelayWeekCountDownDelete_DeleteTimeNumMapEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)u}
